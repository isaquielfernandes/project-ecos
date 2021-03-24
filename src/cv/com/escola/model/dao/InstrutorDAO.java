package cv.com.escola.model.dao;

import cv.com.escola.model.dao.CrudDAO;
import cv.com.escola.model.entity.Instrutor;

public interface InstrutorDAO extends CrudDAO<Instrutor, Long>{

    Instrutor buscar(Instrutor instrutor);
    
}
