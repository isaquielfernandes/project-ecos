package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.TipoUsuario;
import cv.com.escola.model.entity.Usuario;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.UsuarioDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Tempo;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UsuarioDAOImpl extends DAO implements UsuarioDAO {

    public UsuarioDAOImpl() {
        super();
        conector = ConnectionManager.getInstance().getConnection();
    }

    @Override
    public void create(Usuario usuario) {
        try {
            String sql = "INSERT INTO " + db + ".tb_usuario ( nome, login, senha, email, status, descricao, data_criacao, fk_tipo_usuario ) VALUES (?, ?, ?, ?, ?, ?, now(),?)";

            preparedStatement = conector.prepareStatement(sql);

            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getLogin());
            preparedStatement.setString(3, usuario.getSenha());
            preparedStatement.setString(4, usuario.getEmail());
            preparedStatement.setInt(5, usuario.isStatus() ? 1 : 0);
            preparedStatement.setString(6, usuario.getDescricao());
            preparedStatement.setInt(7, usuario.getTipoUsuario().getId());

            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir usuário na base de dados! \n" + ex);
        }
    }

    @Override
    public void createUserAdminAndUserType() {
        LocalDate agora = LocalDate.now();
        try {
            if (totalTipoUsuario() == 0 && totalUsuario() == 0) {
                //Inserindo tipo de usuario na tabela
                preparedStatement = conector.prepareStatement("INSERT INTO " + db + ".`tb_tipo_usuario` VALUES (1,'administrador'),(2,'normal');");
                preparedStatement.executeUpdate();

                //Inserindo usuario administrador
                preparedStatement = conector.prepareStatement("INSERT INTO " + db + ".`tb_usuario` VALUES (1,'Admin','admin','21232F297A57A5A743894A0E4A801FC3','admin@gmail.com',1,'',sysdate(),1);");
                preparedStatement.executeUpdate();
                conector.commit();
                Mensagem.info("Esta na hora de iniciar a aplicacao", "Sucesso");
            } else {
                Mensagem.info("Usuario ja esta registrado na base de dados");
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Usuario usuario) {
        try {
            String sql = "UPDATE " + db + ".tb_usuario SET nome =?, login =?, senha =?, email =?, status =?, descricao =?, fk_tipo_usuario =? WHERE id_usuario =?";

            preparedStatement = conector.prepareStatement(sql);

            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getLogin());
            preparedStatement.setString(3, usuario.getSenha());
            preparedStatement.setString(4, usuario.getEmail());
            preparedStatement.setInt(5, usuario.isStatus() ? 1 : 0);
            preparedStatement.setString(6, usuario.getDescricao());
            preparedStatement.setInt(7, usuario.getTipoUsuario().getId());

            preparedStatement.setInt(8, usuario.getId());

            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar usuário na base de dados! \n" + ex);
        }
    }

    @Override
    public void delete(Integer idUsuario) {
        try {
            String sql = "DELETE FROM " + db + ".tb_usuario WHERE id_usuario=?";

            preparedStatement = conector.prepareStatement(sql);

            preparedStatement.setInt(1, idUsuario);
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir usuário na base de dados! \n" + ex);
        }
    }

    /**
     * Consultar todos usuários cadastrados na base de dados
     *
     * @return
     */
    @Override
    public List<Usuario> findAll() {

        List<Usuario> usuarios = new ArrayList<>();

        try {
            String sql = "SELECT " + db + ".tb_usuario.*, " + db + ".tb_tipo_usuario.nome FROM " + db + ".tb_usuario, " + db + ".tb_tipo_usuario "
                    + "WHERE " + db + ".tb_usuario.fk_tipo_usuario = " + db + ".tb_tipo_usuario.id_tipo_usuario ";

            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);

            while (rs.next()) {
                TipoUsuario tipo = new TipoUsuario(rs.getInt(9), rs.getString(10));
                Usuario user = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6) == 1 ? true : false, Tempo.toDate(rs.getTimestamp(8)), rs.getString(7), tipo);
                usuarios.add(user);
            }

            preparedStatement.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar usuários na base de dados! \n" + ex);
        }

        return usuarios;
    }

    @Override
    public List<TipoUsuario> usuariosTipo() {
        List<TipoUsuario> tipos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + db + ".tb_tipo_usuario ";

            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                TipoUsuario tipo = new TipoUsuario(rs.getInt(1), rs.getString(2));
                tipos.add(tipo);
            }
            preparedStatement.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar usuários na base de dados! \n" + ex);
        }

        return tipos;
    }

    /**
     * Verificar se usuário já está registrado na base de dados atravês do login
     *
     * @param id
     * @param nome
     * @return
     */
    @Override
    public boolean isUsuario(int id, String nome) {

        String login = nome.replaceAll(" ", "").trim().toLowerCase();

        try {
            String sql = "SELECT login FROM " + db + ".tb_usuario WHERE login = ? AND id_usuario != ? ";

            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            preparedStatement.setInt(2, id);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String usuario = rs.getString(1);
                return usuario.trim().equalsIgnoreCase(login);
            }

            preparedStatement.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao verificar na base de dados login usuário \n" + ex);
        }

        return false;
    }

    @Override
    public int totalUsuario() {
        int total = 0;
        try {
            String sql = "SELECT COUNT(id_usuario) FROM " + db + ".tb_usuario";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    @Override
    public int totalTipoUsuario() {
        int total = 0;
        try {
            String sql = "SELECT COUNT(id_tipo_usuario) FROM " + db + ".tb_tipo_usuario";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
}
