package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Categoria;
import cv.com.escola.model.dao.CategoriaDAO;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DaoException;
import cv.com.escola.model.util.Mensagem;
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

    private Connection connection;
    
    public CategoriaDAOImpl() {
        super();
    }

    @Override
    public void create(Categoria categoria) {
        try {
            String sql = "INSERT INTO "+ db +".tb_categoria ( categoria, descricao)"
                    + " VALUES (?, ?)";
            connection = ConnectionManager.getInstance().begin();
            stm = connection.prepareStatement(sql);
            stm.setString(1, categoria.getNome());
            stm.setString(2, categoria.getDescricao());
            stm.executeUpdate();

        } catch (SQLException ex) {
            throw new DaoException("Erro ao inserir categoria na base de dados!");
        } finally  {
            ConnectionManager.close(stm, connection);
        }
    }

    @Override
    public void update(Categoria categoria) {
        try {
            String sql = "UPDATE "+ db +".tb_categoria SET categoria=?, descricao=?"
                    + " WHERE id_categoria =?";
            connection = ConnectionManager.getInstance().begin();
            stm = connection.prepareStatement(sql);
            stm.setString(1, categoria.getNome());
            stm.setString(2, categoria.getDescricao());
            stm.setInt(3, categoria.getId_categoria());
            stm.executeUpdate();
            
        } catch (SQLException ex) {
            throw new DaoException("Erro ao atualizar categoria na base de dados!n");
        } finally  {
            ConnectionManager.close(stm, connection);
        }
    }

    @Override
    public void delete(Integer idCategoria) {
        try {
            String sql = "DELETE FROM "+ db +".tb_categoria WHERE id_categoria=?";
            connection = ConnectionManager.getInstance().begin();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, idCategoria);
            stm.execute();
        } catch (SQLException ex) {
            throw new DaoException("Erro ao excluir categoria na base de dados!");
        }finally  {
            ConnectionManager.close(stm, connection);
        }
    }

    @Override
    public List<Categoria> findAll() {
        List<Categoria> categorias = new ArrayList<>();
        try {
            String sql = "SELECT * from "+ db +".tb_categoria";
            connection = ConnectionManager.getInstance().begin();
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                Categoria categ = new Categoria(rs.getInt(1), rs.getString(2), rs.getString(3));
                categorias.add(categ);
            }
        } catch (SQLException ex) {
            throw new DaoException("Erro ao consultar categoria na base de dados!");
        } finally  {
            ConnectionManager.close(stm, rs, connection);
        }
        return categorias;
    }

    @Override
    public void report() {
        try {
            connection = ConnectionManager.getInstance().begin();
            URL url = getClass().getResource("/cv/com/escola/reports/Categoria.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            //null: caso não existem filtros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conector);
            
            Print jasperViewer = new Print();
            jasperViewer.viewReport("Categoria", jasperPrint);
        } catch (JRException ex) {
            Logger.getLogger(CategoriaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally  {
            ConnectionManager.close(connection);
        }
    }
}
