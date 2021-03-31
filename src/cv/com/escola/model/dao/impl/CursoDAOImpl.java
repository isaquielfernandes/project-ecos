package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Categoria;
import cv.com.escola.model.entity.Curso;
import cv.com.escola.model.dao.CursoDAO;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DaoException;
import cv.com.escola.model.util.Mensagem;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CursoDAOImpl extends DAO implements CursoDAO {

    public CursoDAOImpl() {
        super();
        conector = ConnectionManager.getInstance().getConnection();
    }

    @Override
    public void create(Curso curso) {
        try {
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
            Logger.getLogger(CursoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException("Erro ao cadastrar curso na base de dados!");
        }
    }

    @Override
    public void update(Curso curso) {
        try {
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
            Mensagem.info("Curso atualizado com sucesso");
        } catch (SQLException ex) {
            Logger.getLogger(CursoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException("Erro ao atualizar curso na base de dados! \n");
        }
    }

    @Override
    public void delete(Long IdCurso) {
        try {
            String sql = "DELETE FROM "+ db +".tb_curso WHERE codigo=?";
            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setLong(1, IdCurso);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(CursoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException("Erro ao excluir curso na base de dados!");
        }
    }

    @Override
    public List<Curso> findAll() {
        List<Curso> cursos = new ArrayList<>();
        try {
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
            Logger.getLogger(CursoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException("Erro ao consultar cursos na base de dados!");
        }
        return cursos;
    }
}
