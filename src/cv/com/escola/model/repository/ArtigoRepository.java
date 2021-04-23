package cv.com.escola.model.repository;

import cv.com.escola.model.entity.Artigo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtigoRepository extends CrudRepository<Artigo, Long> {

}
