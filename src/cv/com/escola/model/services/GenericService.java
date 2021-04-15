package cv.com.escola.model.services;

public interface GenericService<T, ID> {

    public T save(T t);

    public T update(ID id, T t);

    public void delete();
}
