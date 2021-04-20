package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Categoria;
import cv.com.escola.model.entity.Curso;
import cv.com.escola.model.dao.CursoDAO;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.Mensagem;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAOImpl extends DAO implements CursoDAO {

    public CursoDAOImpl() {
        super();
    }

    @Override
    public void create(Curso curso) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sql = "INSERT INTO "+ db +".tb_curso (nome_curso, duracao, descricao, fk_categoria)VALUES(?,?,?,?)";
            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setString(1, curso.getCurso());
            preparedStatement.setInt(2, curso.getDuracao());
            preparedStatement.setString(3, curso.getDescricao());
            preparedStatement.setInt(4, curso.getCategoria().getId_categoria());

            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
            Mensagem.info("Curso cadastrado com sucesso");
        } catch (SQLException ex) {
            throw new DataAccessException("INSERT: ", ex);
        }
    }

    @Override
    public void update(Curso curso) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sql = "UPDATE "+ db +".tb_curso SET nome_curso=?, duracao=?, descricao=?, fk_categoria=? WHERE codigo =?";
            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setString(1, curso.getCurso());
            preparedStatement.setInt(2, curso.getDuracao());
            preparedStatement.setString(3, curso.getDescricao());
            preparedStatement.setInt(4, curso.getCategoria().getId_categoria());

            preparedStatement.setLong(5, curso.getCodigo());

            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("UPDATE: ", ex);
        }
    }

    @Override
    public void delete(Long IdCurso) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sql = "DELETE FROM "+ db +".tb_curso WHERE codigo=?";
            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setLong(1, IdCurso);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("FIND: ", ex);
        }
    }

    @Override
    public List<Curso> findAll() {
        List<Curso> cursos = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sql = "SELECT c.*, cat.categoria FROM "+ db +".tb_curso c INNER JOIN "
                    + ""+ db +".tb_categoria cat ON c.fk_categoria = cat.id_categoria";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Categoria cat = new Categoria(rs.getInt(5), rs.getString(6));
                Curso curso = new Curso(rs.getLong(1), rs.getString(2), rs.getInt(3), rs.getString(4), cat);
                cursos.add(curso);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException("FIND: ", ex);
        }
        return cursos;
    }
}
