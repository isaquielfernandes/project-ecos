package cv.com.escola.model.dao;

import java.util.List;

public interface CrudDAO<T, ID> {
    
    List<T> findAll();
    void delete(ID id);
    //T findById(ID id);
    void create(T t);
    void update(T t);
    default int count() {
        return (int) this.findAll().stream().count();
    }
    
}
