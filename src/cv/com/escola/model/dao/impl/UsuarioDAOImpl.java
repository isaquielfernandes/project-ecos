package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.TipoUsuario;
import cv.com.escola.model.entity.Usuario;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.UsuarioDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Tempo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl extends DAO implements UsuarioDAO {
    
    private static final String INSERT_INTO = "INSERT INTO ";
    
    public UsuarioDAOImpl() {
        super();
    }
    
    @Override
    public void create(Usuario usuario) {
        StringBuilder query = new StringBuilder();
        query.append(INSERT_INTO).append(db).append(".tb_usuario ( nome, login, senha, email, status, descricao, data_criacao, fk_tipo_usuario ) VALUES (?, ?, ?, ?, ?, ?, now(),?)");
        
        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    query.toString()
            )) {
                mapToSave(pstmt, usuario);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }
    
    @Override
    public void createUserAdminAndUserType() {
        StringBuilder insertTipoDeUser = new StringBuilder();
        insertTipoDeUser.append(INSERT_INTO).append(db).append(".tb_tipo_usuario VALUES (1,'administrador'),(2,'normal');");
        
        StringBuilder insertUser = new StringBuilder();
        insertUser.append(INSERT_INTO).append(db).append(".tb_usuario VALUES (1,'Admin','admin','21232F297A57A5A743894A0E4A801FC3','admin@gmail.com',1,'',sysdate(),1);");
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            if (totalTipoUsuario() == 0 && totalUsuario() == 0) {
                //Inserindo tipo de usuario na tabela
                preparedStatement = conector.prepareStatement(insertTipoDeUser.toString());
                preparedStatement.executeUpdate();

                //Inserindo usuario administrador
                preparedStatement = conector.prepareStatement(insertUser.toString());
                preparedStatement.executeUpdate();
                conector.commit();
                Mensagem.info("Esta na hora de iniciar a aplicacao", "Sucesso");
            } else {
                Mensagem.info("Usuario ja esta registrado na base de dados");
            }
            
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }
    
    @Override
    public void update(Usuario usuario) {
        StringBuilder updateQuery = new StringBuilder();
        updateQuery.append("UPDATE ").append(db).append(".tb_usuario SET nome =?, login =?, senha =?, email =?, status =?, descricao =?, fk_tipo_usuario =? WHERE id_usuario =?");
        
        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    updateQuery.toString()
            )) {
                mapToSave(pstmt, usuario);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }
    
    private void mapToSave(final PreparedStatement pstmt, Usuario usuario) throws SQLException {
        pstmt.setString(1, usuario.getNome());
        pstmt.setString(2, usuario.getLogin());
        pstmt.setString(3, usuario.getSenha());
        pstmt.setString(4, usuario.getEmail());
        pstmt.setInt(5, usuario.isStatus() ? 1 : 0);
        pstmt.setString(6, usuario.getDescricao());
        pstmt.setInt(7, usuario.getTipoUsuario().getId());
        
        if (usuario.getId() != 0) {
            pstmt.setInt(8, usuario.getId());
        }
        
        pstmt.executeUpdate();
    }
    
    @Override
    public void delete(Integer idUsuario) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(db).append(".tb_usuario WHERE id_usuario=?");
        remove(query.toString(), idUsuario);
    }
    
    @Override
    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        final StringBuilder query = new StringBuilder();
        query.append("SELECT ").append(db).append(".tb_usuario.*, ").append(db).append(".tb_tipo_usuario.nome FROM ").append(db).append(".tb_usuario, ").append(db).append(".tb_tipo_usuario ");
        query.append("WHERE ").append(db).append(".tb_usuario.fk_tipo_usuario = ").append(db).append(".tb_tipo_usuario.id_tipo_usuario ");
        
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TipoUsuario tipo = new TipoUsuario(resultSet.getInt(9), resultSet.getString(10));
                    Usuario usuario = new Usuario(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), (resultSet.getInt(6) == 1), Tempo.toDate(resultSet.getTimestamp(8)), resultSet.getString(7), tipo);
                    usuarios.add(usuario);
                }
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        
        return usuarios;
    }
    
    @Override
    public List<TipoUsuario> usuariosTipo() {
        List<TipoUsuario> tipos = new ArrayList<>();
        final StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(db).append(".tb_tipo_usuario ");
        
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TipoUsuario tipo = new TipoUsuario(resultSet.getInt(1), resultSet.getString(2));
                    tipos.add(tipo);
                }
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return tipos;
    }
    
    @Override
    public boolean isUsuario(int id, String nome) {
        String login = nome.replace(" ", "").trim().toLowerCase();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT login FROM ").append(db).append(".tb_usuario WHERE login = ? AND id_usuario != ? ");
            
            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setString(1, nome);
            preparedStatement.setInt(2, id);
            rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                String usuario = rs.getString(1);
                return usuario.trim().equalsIgnoreCase(login);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        
        return false;
    }
    
    @Override
    public int totalUsuario() {
        int total = 0;
        final StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(id_usuario) FROM ").append(db).append(".tb_usuario");
        
        total = count(query.toString());
        
        return total;
    }
    
    @Override
    public int totalTipoUsuario() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(id_tipo_usuario) FROM ").append(db).append(".tb_tipo_usuario");
        return count(query.toString());
    }
}
