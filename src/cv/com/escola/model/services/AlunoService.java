package cv.com.escola.model.services;

import cv.com.escola.model.dao.AlunoDAO;
import cv.com.escola.model.entity.Aluno;

public class AlunoService {

    private final AlunoDAO alunoDAO;

    public AlunoService(AlunoDAO alunoDAO) {
        this.alunoDAO = alunoDAO;
    }

    public Aluno save(Aluno aluno) {
        alunoDAO.create(aluno);
        return aluno;
    }

    public Aluno update(Aluno aluno) {
        if (aluno.idAlunoProperty().get() == 0) {
            alunoDAO.update(aluno);
        }
        return aluno;
    }

    public void delete(int id) {
        alunoDAO.delete(id);
    }

}
