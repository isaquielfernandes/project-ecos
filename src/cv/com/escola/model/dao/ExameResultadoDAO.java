package cv.com.escola.model.dao;

import cv.com.escola.model.entity.ExameResultado;

public interface ExameResultadoDAO extends CrudDAO<ExameResultado, Long>{

    void reportFichaAulaPratica(Integer id, String nome);
    
}
