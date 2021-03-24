package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Aluno;
import cv.com.escola.model.entity.Curso;
import cv.com.escola.model.entity.Matricula;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.MatriculaDAO;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Tempo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatriculaDAOImpl extends DAO implements MatriculaDAO {

    public MatriculaDAOImpl() {
        super();
    }

    @Override
    public void create(Matricula incriver) {
        try {
            String sql = "INSERT INTO `" + db + "`.`tb_matricula` (`data`, `aluno_id`, `curso_id`, `turma`, `periodo`, `obs`) VALUES (?,?,?,?,?,?);";

            stm = conector.prepareStatement(sql);
            stm.setTimestamp(1, Tempo.toTimestamp(incriver.getData()));
            stm.setLong(2, incriver.getAluno().getIdAluno());
            stm.setLong(3, incriver.getCursoPretendido().getCodigo());
            stm.setString(4, incriver.getTurma());
            stm.setString(5, incriver.getPeriodo());
            stm.setString(6, incriver.getObservacao());

            stm.executeUpdate();
            stm.close();
            Mensagem.info("Inscricao feita com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(MatriculaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao registrar inscricao na base de dados" + ex);
        }
    }

    @Override
    public void update(Matricula incriver) {
        try {
            String sql = "UPDATE `" + db + "`.`tb_matricula` SET `data` = ?, `aluno_id` = ?, `curso_id` = ?, `turma` = ?, `periodo` = ?, `obs` = ? WHERE `id_matricula` = ?;";

            stm = conector.prepareStatement(sql);

            stm.setTimestamp(1, Tempo.toTimestamp(incriver.getData()));
            stm.setLong(2, incriver.getAluno().getIdAluno());
            stm.setLong(3, incriver.getCursoPretendido().getCodigo());
            stm.setString(4, incriver.getTurma());
            stm.setString(5, incriver.getPeriodo());
            stm.setString(6, incriver.getObservacao());

            stm.setLong(7, incriver.getId());

            stm.executeUpdate();
            stm.close();
            Mensagem.info("Inscricao atualizada com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(MatriculaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao atualizar registrar inscricao na base de dados" + ex);
        }
    }

    @Override
    public void delete(Long idMatricula) {
        try {
            String sql = "";
            stm = conector.prepareStatement(sql);
            stm.setLong(1, idMatricula);
            stm.execute();
            stm.close();
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir inscricao na base de dados!\n" + ex);
        }
    }

    @Override
    public List<Matricula> findAll() {
        List<Matricula> inscricao = new ArrayList();
        try {
            String sql = "SELECT m.id_matricula, m.data, a.id_aluno, a.nome, c.codigo, c.nome_curso, m.turma, m.periodo, m.obs FROM "+ db +".tb_matricula as m, "+ db +".tb_aluno as a, "+ db +".tb_curso as c where m.aluno_id = a.id_aluno and m.curso_id = c.codigo order by m.data desc;";
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);
            while(rs.next()){
                Aluno aluno = new Aluno(rs.getInt(3), rs.getString(4));
                Curso curso = new Curso(rs.getLong(5), rs.getString(6));
                Matricula matricula = new Matricula(rs.getLong(1), rs.getDate(2).toLocalDate(), aluno, curso, rs.getString(7), rs.getString(8), rs.getString(9));
                inscricao.add(matricula);
            }
            stm.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(MatriculaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inscricao;
    }
}
