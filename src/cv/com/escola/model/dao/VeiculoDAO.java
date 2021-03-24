package cv.com.escola.model.dao;

import cv.com.escola.model.entity.Veiculo;

public interface VeiculoDAO extends CrudDAO<Veiculo, Long>{

    int total();
    
}
