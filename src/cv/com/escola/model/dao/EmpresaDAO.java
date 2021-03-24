package cv.com.escola.model.dao;

import cv.com.escola.model.entity.Empresa;

public interface EmpresaDAO extends CrudDAO<Empresa, Integer>{

    void editarSemLogo(Empresa empresa);
    int total();
    
}
