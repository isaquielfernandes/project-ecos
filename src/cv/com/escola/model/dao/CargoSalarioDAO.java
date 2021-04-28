package cv.com.escola.model.dao;

import cv.com.escola.model.entity.CargoSalario;
import java.util.List;

public interface CargoSalarioDAO extends CrudDAO<CargoSalario, Integer> {
    
    List<CargoSalario> combo();
    boolean isCargoSalario(String nome, int id);
    
}
