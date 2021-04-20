package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Inventario;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.InventariaDAO;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.JasperViewerFX;
import cv.com.escola.model.util.Tempo;
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

public class InventarioDAOImpl extends DAO implements InventariaDAO {

    public InventarioDAOImpl() {
        super();
    }

    @Override
    public void create(Inventario inventario) {
        try (Connection conn = HikariCPDataSource.getConnection();){
            String sql = "insert into " + db + ".tb_inventario ( num_serie, fk_categoria, item, responsavel, "
                    + "fk_area, local, data_compra, meses_desde_compra, valor, estado_consrvacao, "
                    + "vida_util_ano, valor_atual, depreciacao ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, inventario.getNumSerie());
            preparedStatement.setString(2, inventario.getCategoria());
            preparedStatement.setString(3, inventario.getItem());
            preparedStatement.setString(4, inventario.getResponsavel());
            preparedStatement.setString(5, inventario.getArea());
            preparedStatement.setString(6, inventario.getLocal());
            preparedStatement.setTimestamp(7, Tempo.toTimestamp(inventario.getDataCompra()));
            preparedStatement.setInt(8, inventario.getMesesDesdeCompra());
            preparedStatement.setDouble(9, inventario.getValor());
            preparedStatement.setString(10, inventario.getEstadoConservacao());
            preparedStatement.setInt(11, inventario.getVidaUtilAno());
            preparedStatement.setDouble(12, inventario.getValorAtual());
            preparedStatement.setString(13, inventario.getDepreciacao());

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void update(Inventario inventario) {
        try (Connection conn = HikariCPDataSource.getConnection();){
            String sql = "UPDATE " + db + ".tb_inventario SET num_serie =?, fk_categoria =?, item =?, responsavel =?, "
                    + "fk_area =?, local =?, data_compra =?, meses_desde_compra =?, valor =?, estado_consrvacao =?, "
                    + "vida_util_ano =?, valor_atual =?, depreciacao =? WHERE id_inventario =?";

            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, inventario.getNumSerie());
            preparedStatement.setString(2, inventario.getCategoria());
            preparedStatement.setString(3, inventario.getItem());
            preparedStatement.setString(4, inventario.getResponsavel());
            preparedStatement.setString(5, inventario.getArea());
            preparedStatement.setString(6, inventario.getLocal());
            preparedStatement.setTimestamp(7, Tempo.toTimestamp(inventario.getDataCompra()));
            preparedStatement.setInt(8, inventario.getMesesDesdeCompra());
            preparedStatement.setDouble(9, inventario.getValor());
            preparedStatement.setString(10, inventario.getEstadoConservacao());
            preparedStatement.setInt(11, inventario.getVidaUtilAno());
            preparedStatement.setDouble(12, inventario.getValorAtual());
            preparedStatement.setString(13, inventario.getDepreciacao());
            preparedStatement.setInt(14, inventario.getIdInventario());
            preparedStatement.executeUpdate();
            conn.commit();
            preparedStatement.close();

        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void delete(Integer idInventario) {
        try (Connection conn = HikariCPDataSource.getConnection();) {
            String sql = "DELETE FROM " + db + ".tb_inventario WHERE id_inventario=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, idInventario);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public List<Inventario> findAll() {
        List<Inventario> inventario = new ArrayList<>();
        try (Connection conn = HikariCPDataSource.getConnection();) {

            String sql = "SELECT * from " + db + ".tb_inventario";

            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);

            while (rs.next()) {
                Inventario item = new Inventario(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), Tempo.toDate(rs.getTimestamp(8)),
                        rs.getInt(9), rs.getDouble(10), rs.getString(11),
                        rs.getInt(12), rs.getDouble(13), rs.getString(14),
                        Tempo.toDate(rs.getTimestamp(15)));
                inventario.add(item);
            }

            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException("FIND: ", ex);
        }
        return inventario;
    }

    @Override
    public boolean isNumSerie(String nome, int id) {
        try (Connection conn = HikariCPDataSource.getConnection();) {
            String sql = "SELECT num_serie FROM " + db + ".tb_inventario WHERE num_serie =? AND id_inventario !=? ";

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            preparedStatement.setInt(2, id);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getString(1).toLowerCase().trim().equals(nome.toLowerCase().trim().toLowerCase());
            }

            preparedStatement.close();
            rs.close();

        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }

        return false;
    }

    @Override
    public int total() {
        try (Connection conn = HikariCPDataSource.getConnection();){
            String sql = "SELECT COUNT(*) FROM " + db + ".tb_inventario";
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return 0;
    }

    @Override
    public void report() {
        try (Connection conn = HikariCPDataSource.getConnection();) {
            URL url = getClass().getResource("/cv/com/escola/reports/inventario.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            //null: caso não existem filtros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);

            JasperViewerFX jasperViewer = new JasperViewerFX();
            jasperViewer.viewReport("Inventario", jasperPrint);
        } catch (JRException ex) {
            Logger.getLogger(InventarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            throw new DataAccessException("FIND: ", ex);
        }
    }
}
