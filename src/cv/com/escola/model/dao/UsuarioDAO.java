package cv.com.escola.model.dao;

import cv.com.escola.model.entity.TipoUsuario;
import cv.com.escola.model.entity.Usuario;
import java.util.List;

public interface UsuarioDAO extends CrudDAO<Usuario, Integer>{

    void createUserAdminAndUserType();
    boolean isUsuario(int id, String nome);
    int totalTipoUsuario();
    int totalUsuario();
    List<TipoUsuario> usuariosTipo();
    
}
