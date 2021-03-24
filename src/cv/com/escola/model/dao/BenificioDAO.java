package cv.com.escola.model.dao;

import cv.com.escola.model.entity.Benificio;
import java.util.List;

public interface BenificioDAO extends CrudDAO<Benificio, Integer>{

    List<Benificio> combo();
    boolean isBanificio(String nome, int id);
    
}
