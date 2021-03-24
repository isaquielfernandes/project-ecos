package cv.com.escola.model.dao;

import cv.com.escola.model.entity.Auditoria;
import java.util.List;

public interface AuditoriaDAO {

    void excluir(int id);
    void inserir(Auditoria log);
    List<Auditoria> logs();
    List<Auditoria> logsUsuario(int id);
    
}
