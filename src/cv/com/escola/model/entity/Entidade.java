package cv.com.escola.model.entity;

import java.io.Serializable;


public interface Entidade<T> extends Serializable{
    
    public T getId();
}
