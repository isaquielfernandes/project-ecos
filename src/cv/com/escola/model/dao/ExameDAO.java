package cv.com.escola.model.dao;

import cv.com.escola.model.entity.Exame;
import java.sql.Date;

public interface ExameDAO extends CrudDAO<Exame, Long>{

    void report();
    void reportListCandidadtoParaExame(String tipoExame, Date dia);
    int total();
    
}
