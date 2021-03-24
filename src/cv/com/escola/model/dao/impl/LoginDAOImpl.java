package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.TipoUsuario;
import cv.com.escola.model.entity.Usuario;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.LoginDAO;
import cv.com.escola.model.util.Criptografia;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Tempo;
import java.sql.SQLException;

public class LoginDAOImpl extends DAO implements LoginDAO {

    public LoginDAOImpl() {
        super();
    }

    @Override
    public boolean autenticarUsername(String nome) {
        try {
            String sql = "SELECT login FROM "+ db +".tb_usuario WHERE login=? AND status = 1 ";
            stm = conector.prepareStatement(sql);
            stm.setString(1, nome);
            rs = stm.executeQuery();

            if (rs.next()) {
                return nome.equals(rs.getString(1));
            }
            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao autenticar nome usuário na base de dados! \n" + ex);
        }
        return false;
    }

    @Override
    public boolean autenticarSenha(String nome, String senha) {
        String chave = Criptografia.converter(senha);
        try {
            String sql = "SELECT login, senha FROM "+ db +".tb_usuario WHERE login=? AND senha=? ";
            stm = conector.prepareStatement(sql);
            stm.setString(1, nome);
            stm.setString(2, chave);
            rs = stm.executeQuery();

            while (rs.next()) {
                return rs.getString(1).equals(nome) && rs.getString(2).equals(chave);
            }
            stm.close();
            rs.close();
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao autenticar senha usuário na base de dados! \n" + ex);
        }

        return false;
    }

    @Override
    public Usuario usuarioLogado(String login) {
        Usuario user = null;
        try {
            String sql = "SELECT usuario.id_usuario, usuario.nome, usuario.login, usuario.senha, usuario.email, usuario.status, usuario.data_criacao, usuario.descricao, tipo.id_tipo_usuario, tipo.nome "
                    + "FROM "+ db +".tb_usuario AS usuario , "+ db +".tb_tipo_usuario AS tipo "
                    + "WHERE usuario.login=? "
                    + "AND tipo.id_tipo_usuario = usuario.fk_tipo_usuario";

            stm = conector.prepareStatement(sql);
            stm.setString(1, login);
            rs = stm.executeQuery();

            while (rs.next()) {
                TipoUsuario tipo = new TipoUsuario(rs.getInt(9), rs.getString(10));
                user = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), 
                        rs.getString(4), rs.getString(5), (rs.getInt(6) == 1), null, rs.getString(8), tipo);
                user.setDataCriacao(Tempo.toDate(rs.getTimestamp(7)));
            }
            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar usuário logado na base de dados! \n" + ex);
        }
        return user;
    }
}
