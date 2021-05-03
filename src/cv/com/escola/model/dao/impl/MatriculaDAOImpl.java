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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAOImpl extends DAO implements MatriculaDAO {

    public MatriculaDAOImpl() {
        super();
    }

    @Override
    public void create(Matricula matricula) {
        StringBuilder insertQuery = new StringBuilder();
        insertQuery.append("INSERT INTO `").append(db).append("`.tb_matricula (data, aluno_id, curso_id, turma, periodo`, `obs`) VALUES (?,?,?,?,?,?);");
        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    insertQuery.toString()
            )) {
                mspToSave(pstmt, matricula);
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        });
    }

    @Override
    public void update(Matricula matricula) {
        StringBuilder updateQuery = new StringBuilder();
        updateQuery.append("UPDATE ").append(db).append(".tb_matricula SET data = ?, aluno_id = ?, curso_id = ?, turma = ?, periodo = ?, obs = ? WHERE id_matricula = ?;");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    updateQuery.toString()
            )) {
                mspToSave(pstmt, matricula);
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        });
    }

    private void mspToSave(final PreparedStatement pstmt, Matricula matricula) throws SQLException {
        pstmt.setTimestamp(1, Tempo.toTimestamp(matricula.getData()));
        pstmt.setLong(2, matricula.getAluno().getId());
        pstmt.setLong(3, matricula.getCursoPretendido().getCodigo());
        pstmt.setString(4, matricula.getTurma());
        pstmt.setString(5, matricula.getPeriodo());
        pstmt.setString(6, matricula.getObservacao());
        if (matricula.getId() != 0) {
            pstmt.setLong(7, matricula.getId());
        }
        pstmt.executeUpdate();
    }

    @Override
    public void delete(Long idMatricula) {
        String sql = "DELETE FOM tb_matricula WHERE id_matricula = ?;";
        remove(sql, idMatricula);
    }

    @Override
    public List<Matricula> findAll() {
        List<Matricula> inscricao = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT m.id_matricula, m.data, a.id_aluno, a.nome, c.codigo, c.nome_curso, m.turma, m.periodo, m.obs FROM ").append(db).append(".tb_matricula as m, ").append(db).append(".tb_aluno as a, ").append(db).append(".tb_curso as c where m.aluno_id = a.id_aluno and m.curso_id = c.codigo order by m.data desc;");
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Aluno aluno = new Aluno(rs.getInt(3), rs.getString(4));
                Curso curso = new Curso(rs.getLong(5), rs.getString(6));
                Matricula matricula = new Matricula(rs.getLong(1), rs.getDate(2).toLocalDate(), aluno, curso, rs.getString(7), rs.getString(8), rs.getString(9));
                inscricao.add(matricula);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return inscricao;
    }
}
