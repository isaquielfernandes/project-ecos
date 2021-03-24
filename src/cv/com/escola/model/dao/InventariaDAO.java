package cv.com.escola.model.dao;

import cv.com.escola.model.entity.Inventario;

public interface InventariaDAO extends CrudDAO<Inventario, Integer>{

    boolean isNumSerie(String nome, int id);
    void report();
    int total();
    
}
