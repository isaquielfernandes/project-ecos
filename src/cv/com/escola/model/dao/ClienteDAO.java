package cv.com.escola.model.dao;

import cv.com.escola.model.entity.Cliente;

public interface ClienteDAO extends CrudDAO<Cliente, Integer>{

    Cliente buscar(Cliente cliente);
    
}
