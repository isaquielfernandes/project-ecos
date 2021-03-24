package cv.com.escola.model.dao;

import cv.com.escola.model.entity.Aluno;
import cv.com.escola.model.entity.Pagina;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;

public interface AlunoDAO extends CrudDAO<Aluno, Integer>{

    default List<Aluno> autoCompletion() {
        return this.findAll().stream().sorted().collect(Collectors.toList());
    }
    
    default int quantidade(String filtro) {
        return (int) this.findAll().stream()
                .filter(aluno -> aluno.getNome().equals(filtro) ).count();
    }
    
    void editarSemFoto(Aluno aluno);
    ObservableList<Aluno> listar(int quantidade, int pagina);
    void report();
    void reportRequiremento(String biFiltro);
    ObservableList<Aluno> searchStudent(Pagina pagina, String query);
    ObservableList<Aluno> student(int pagina);
    int totalSearchStudent(String param);
    
}
