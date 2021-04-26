package cv.com.escola.model.dao;

import cv.com.escola.model.entity.Aluno;
import cv.com.escola.model.entity.Pagina;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlunoDAO extends CrudDAO<Aluno, Integer> {

    default List<Aluno> autoCompletion() {
        return this.findAll().stream().sorted().collect(Collectors.toList());
    }

    default int quantidade(String filtro) {
        return (int) this.findAll().stream()
                .filter(aluno -> aluno.getNome().equals(filtro)).count();
    }

    public Page<Aluno> findAll(Pageable page);

    ObservableList<Aluno> listar(int limit, int offset);

    ObservableList<Aluno> searchStudent(Pagina pagina, String query);

    int totalSearchStudent(String param);

    void report();

    void reportRequiremento(String biFiltro);

}
