package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Aluno;
import cv.com.escola.model.entity.Curso;
import cv.com.escola.model.entity.Matricula;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.MatriculaDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.Tempo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAOImpl extends DAO implements MatriculaDAO {

    public MatriculaDAOImpl() {
        super();
    }

    @Override
    public void create(Matricula incriver) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sql = "INSERT INTO `" + db + "`.`tb_matricula` (`data`, `aluno_id`, `curso_id`, `turma`, `periodo`, `obs`) VALUES (?,?,?,?,?,?);";

            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Tempo.toTimestamp(incriver.getData()));
            preparedStatement.setLong(2, incriver.getAluno().getIdAluno());
            preparedStatement.setLong(3, incriver.getCursoPretendido().getCodigo());
            preparedStatement.setString(4, incriver.getTurma());
            preparedStatement.setString(5, incriver.getPeriodo());
            preparedStatement.setString(6, incriver.getObservacao());

            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("INSERT: ", ex);
        }
    }

    @Override
    public void update(Matricula incriver) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sql = "UPDATE `" + db + "`.`tb_matricula` SET `data` = ?, `aluno_id` = ?, `curso_id` = ?, `turma` = ?, `periodo` = ?, `obs` = ? WHERE `id_matricula` = ?;";

            preparedStatement = conector.prepareStatement(sql);

            preparedStatement.setTimestamp(1, Tempo.toTimestamp(incriver.getData()));
            preparedStatement.setLong(2, incriver.getAluno().getIdAluno());
            preparedStatement.setLong(3, incriver.getCursoPretendido().getCodigo());
            preparedStatement.setString(4, incriver.getTurma());
            preparedStatement.setString(5, incriver.getPeriodo());
            preparedStatement.setString(6, incriver.getObservacao());
            preparedStatement.setLong(7, incriver.getId());
            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("UPDATE: ", ex);
        }
    }

    @Override
    public void delete(Long idMatricula) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sql = "";
            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setLong(1, idMatricula);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("DELETE: ", ex);
        }
    }

    @Override
    public List<Matricula> findAll() {
        List<Matricula> inscricao = new ArrayList();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sql = "SELECT m.id_matricula, m.data, a.id_aluno, a.nome, c.codigo, c.nome_curso, m.turma, m.periodo, m.obs FROM "+ db +".tb_matricula as m, "+ db +".tb_aluno as a, "+ db +".tb_curso as c where m.aluno_id = a.id_aluno and m.curso_id = c.codigo order by m.data desc;";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);
            while(rs.next()){
                Aluno aluno = new Aluno(rs.getInt(3), rs.getString(4));
                Curso curso = new Curso(rs.getLong(5), rs.getString(6));
                Matricula matricula = new Matricula(rs.getLong(1), rs.getDate(2).toLocalDate(), aluno, curso, rs.getString(7), rs.getString(8), rs.getString(9));
                inscricao.add(matricula);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException("FIND: ", ex);
        }
        return inscricao;
    }
}
