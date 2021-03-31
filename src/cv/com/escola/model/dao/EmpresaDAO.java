package cv.com.escola.model.dao;

import cv.com.escola.model.entity.EscolaConducao;

public interface EmpresaDAO extends CrudDAO<EscolaConducao, Integer>{

    void editarSemLogo(EscolaConducao empresa);
    int total();
    
}
