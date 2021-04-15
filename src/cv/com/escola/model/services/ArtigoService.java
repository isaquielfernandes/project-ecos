package cv.com.escola.model.services;

import cv.com.escola.model.dao.ArtigoDAO;
import cv.com.escola.model.entity.Artigo;
import java.util.List;

public class ArtigoService {

    private final ArtigoDAO artigoDAO;

    public ArtigoService(ArtigoDAO artigoDAO) {
        this.artigoDAO = artigoDAO;
    }

    public List<Artigo> findAll() {
        return artigoDAO.findAll();
    }

    public Artigo save(Artigo artigo) {
        artigoDAO.create(artigo);
        return artigo;
    }

    public Artigo update(int id, Artigo artigo) {
        if (id != 0) {
            artigoDAO.update(artigo);
        }
        return artigo;
    }

    public void delete(int id) {
        artigoDAO.delete(id);
    }

}
