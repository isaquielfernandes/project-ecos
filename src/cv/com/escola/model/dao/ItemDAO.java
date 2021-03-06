package cv.com.escola.model.dao;

import cv.com.escola.model.entity.Item;
import cv.com.escola.model.entity.Venda;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item, Integer>{

    public void create(List<Item> items);
    
    public List<Item> listarItensPorVenda(Venda venda);
    
}
