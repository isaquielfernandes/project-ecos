package cv.com.escola.model.dao;

import cv.com.escola.model.entity.Usuario;

public interface LoginDAO {

    boolean autenticarSenha(String userName, String senha);
    boolean autenticarUsername(String userName);
    Usuario usuarioLogado(String login);
    
}
