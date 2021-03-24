package cv.com.escola.model.dao;

import cv.com.escola.model.entity.Organizacao;
import java.util.List;

public interface OrganizacaoDAO extends CrudDAO<Organizacao, Integer>{

    List<Organizacao> combo();
    
}
