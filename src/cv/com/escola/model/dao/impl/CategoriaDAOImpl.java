package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Categoria;
import cv.com.escola.model.dao.CategoriaDAO;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.Print;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class CategoriaDAOImpl extends DAO implements CategoriaDAO {

    private static final StringBuilder UPDATE_QUERY = new StringBuilder();
    
    public CategoriaDAOImpl() {
        super();
    }

    @Override
    public void create(Categoria categoria) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(db)
                .append(".tb_categoria ( categoria, descricao) VALUES (?, ?)");
        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    query.toString()
            )) {
                pstmt.setString(1, categoria.getNome());
                pstmt.setString(2, categoria.getDescricao());
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    @Override
    public void update(Categoria categoria) {
        UPDATE_QUERY.append("UPDATE ").append(db)
                .append(".tb_categoria SET categoria=?, descricao=? WHERE id_categoria =?");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    UPDATE_QUERY.toString()
            )) {
                pstmt.setString(1, categoria.getNome());
                pstmt.setString(2, categoria.getDescricao());
                pstmt.setInt(3, categoria.getId());
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    @Override
    public void delete(Integer idCategoria) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(db)
                .append(".tb_categoria WHERE id_categoria=?");
        remove(query.toString(), idCategoria);
    }

    @Override
    public List<Categoria> findAll() {
        List<Categoria> categorias = new ArrayList<>();
        try (Connection connection = HikariCPDataSource.getInstance().getConnection()) {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * from ").append(db).append(".tb_categoria");
            preparedStatement = connection.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery(query.toString())) {
                while (resultSet.next()) {
                    Categoria categ = new Categoria(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                    categorias.add(categ);
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return categorias;
    }

    @Override
    public void report() {
        try (Connection connection = HikariCPDataSource.getInstance().getConnection()) {
            URL url = getClass().getResource("/cv/com/escola/reports/Categoria.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);

            Print jasperViewer = new Print();
            jasperViewer.viewReport("Categoria", jasperPrint);
        } catch (JRException | SQLException ex) {
            Logger.getLogger(CategoriaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
