package cv.com.escola.model.dao;

import cv.com.escola.model.entity.Categoria;

public interface CategoriaDAO extends CrudDAO<Categoria, Integer> {

    void report();
}
