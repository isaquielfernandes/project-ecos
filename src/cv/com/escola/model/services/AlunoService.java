package cv.com.escola.model.services;

import cv.com.escola.model.dao.AlunoDAO;

public class AlunoService {
    
    private final AlunoDAO alunoDAO;

    public AlunoService(AlunoDAO alunoDAO) {
        this.alunoDAO = alunoDAO;
    }
    
}
