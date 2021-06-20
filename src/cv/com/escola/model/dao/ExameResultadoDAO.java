package cv.com.escola.model.dao;

import cv.com.escola.model.entity.ExameResultado;

public interface ExameResultadoDAO extends CrudDAO<ExameResultado, Long>{

    public void gerarFichaDeAulaPratica(Integer id, String nome);
    
}
