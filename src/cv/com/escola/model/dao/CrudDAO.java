package cv.com.escola.model.dao;

import java.util.List;

public interface CrudDAO<T, E>  {
    
    public List<T> findAll();
    
    public void delete(E id);
    
    public void create(T t);
    
    public void update(T t);
    
    default int count() {
        return (int) this.findAll().stream().count();
    }
    
}
