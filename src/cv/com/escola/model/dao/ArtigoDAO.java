package cv.com.escola.model.dao;

import cv.com.escola.model.entity.Artigo;

public interface ArtigoDAO extends CrudDAO<Artigo, Integer>{

    Artigo buscar(Artigo artigo);
    
}
