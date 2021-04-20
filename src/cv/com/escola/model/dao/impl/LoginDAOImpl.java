package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.TipoUsuario;
import cv.com.escola.model.entity.Usuario;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.LoginDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.Criptografia;
import cv.com.escola.model.util.Tempo;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginDAOImpl extends DAO implements LoginDAO {

    public LoginDAOImpl() {
        super();
    }

    @Override
    public boolean autenticarUsername(String nome) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT login FROM ").append(db).append(".tb_usuario WHERE login=? AND status = 1 ");
            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setString(1, nome);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return nome.equals(rs.getString(1));
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao autenticar nome usuário na base de dados! \n" + ex);
        }
        return false;
    }

    @Override
    public boolean autenticarSenha(String nome, String senha) {
        String chave = Criptografia.converter(senha);
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT login, senha FROM ").append(db).append(".tb_usuario WHERE login=? AND senha=? ");
            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, chave);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                return rs.getString(1).equals(nome) && rs.getString(2).equals(chave);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao autenticar senha usuário na base de dados! \n" + ex);
        }

        return false;
    }

    @Override
    public Usuario usuarioLogado(String login) {
        Usuario usuaruio = null;
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT u.id_usuario, u.nome, u.login, u.senha, u.email, u.status, u.data_criacao, u.descricao, tipo.id_tipo_usuario, tipo.nome ");
            query.append("FROM ").append(db).append(".tb_usuario AS u , ").append(db).append(".tb_tipo_usuario AS tipo ");
            query.append("WHERE u.login=? ");
            query.append("AND tipo.id_tipo_usuario = u.fk_tipo_usuario");

            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setString(1, login);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                TipoUsuario tipo = new TipoUsuario(rs.getInt(9), rs.getString(10));
                usuaruio = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), (rs.getInt(6) == 1), null, rs.getString(8), tipo);
                usuaruio.setDataCriacao(Tempo.toDate(rs.getTimestamp(7)));
            }
            preparedStatement.close();
            rs.close();

        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao consultar usuário logado na base de dados! \n" + ex);
        }
        return usuaruio;
    }
}
