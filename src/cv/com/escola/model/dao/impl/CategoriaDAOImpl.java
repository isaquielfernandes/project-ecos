package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Categoria;
import cv.com.escola.model.dao.CategoriaDAO;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.Print;
import java.net.URL;
import java.sql.Connection;
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
    
    public CategoriaDAOImpl() {
        super();
    }

    @Override
    public void create(Categoria categoria) {
        try (Connection connection = HikariCPDataSource.getConnection();) {
            StringBuilder query = new StringBuilder(); 
            query.append("INSERT INTO ")
                    .append(db)
                    .append(".tb_categoria ( categoria, descricao) VALUES (?, ?)");
            preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.setString(1, categoria.getNome());
            preparedStatement.setString(2, categoria.getDescricao());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao inserir categoria na base de dados!");
        }
    }

    @Override
    public void update(Categoria categoria) {
        try (Connection connection = HikariCPDataSource.getConnection();) {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE ")
                    .append(db)
                    .append(".tb_categoria SET categoria=?, descricao=? WHERE id_categoria =?");
            preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.setString(1, categoria.getNome());
            preparedStatement.setString(2, categoria.getDescricao());
            preparedStatement.setInt(3, categoria.getId_categoria());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao atualizar categoria na base de dados!n");
        } 
    }

    @Override
    public void delete(Integer idCategoria) {
        try (Connection connection = HikariCPDataSource.getConnection();) {
            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM ").append(db)
                    .append(".tb_categoria WHERE id_categoria=?");
            preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.setInt(1, idCategoria);
            preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DataAccessException("DELETE: ", ex);
        }
    }

    @Override
    public List<Categoria> findAll() {
        List<Categoria> categorias = new ArrayList<>();
        try (Connection connection = HikariCPDataSource.getConnection();) {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * from ").append(db).append(".tb_categoria");
            preparedStatement = connection.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery(query.toString());
            while (rs.next()) {
                Categoria categ = new Categoria(rs.getInt(1), rs.getString(2), rs.getString(3));
                categorias.add(categ);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("FIND: ", ex);
        }
        return categorias;
    }

    @Override
    public void report() {
        try (Connection connection = HikariCPDataSource.getConnection();) {
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
