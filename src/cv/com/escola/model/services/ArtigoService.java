package cv.com.escola.model.services;

import cv.com.escola.model.dao.ArtigoDAO;

public class ArtigoService {
    
    private final ArtigoDAO artigoDAO;

    public ArtigoService(ArtigoDAO artigoDAO) {
        this.artigoDAO = artigoDAO;
    }
    
}
