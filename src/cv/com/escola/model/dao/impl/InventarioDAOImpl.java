package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Inventario;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.InventariaDAO;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.entity.InventarioBuilder;
import cv.com.escola.model.util.JasperViewerFX;
import cv.com.escola.model.util.Tempo;
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

public class InventarioDAOImpl extends DAO implements InventariaDAO {

    private static final StringBuilder INSERT_QUERY = new StringBuilder();
    private static final StringBuilder UPDATE_QUERY = new StringBuilder();

    public InventarioDAOImpl() {
        super();
    }

    @Override
    public void create(Inventario inventario) {
        INSERT_QUERY.append("insert into ").append(db).append(".tb_inventario ( num_serie, fk_categoria, item, responsavel, ");
        INSERT_QUERY.append("fk_area, local, data_compra, meses_desde_compra, valor, estado_consrvacao, ");
        INSERT_QUERY.append("vida_util_ano, valor_atual, depreciacao ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    INSERT_QUERY.toString()
            )) {
                mapToSave(pstmt, inventario);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    private void mapToSave(final PreparedStatement statement, Inventario inventario) throws SQLException {
        statement.setString(1, inventario.getNumSerie());
        statement.setString(2, inventario.getCategoria());
        statement.setString(3, inventario.getItem());
        statement.setString(4, inventario.getResponsavel());
        statement.setString(5, inventario.getArea());
        statement.setString(6, inventario.getLocal());
        statement.setTimestamp(7, Tempo.toTimestamp(inventario.getDataCompra()));
        statement.setInt(8, inventario.getMesesDesdeCompra());
        statement.setDouble(9, inventario.getValor());
        statement.setString(10, inventario.getEstadoConservacao());
        statement.setInt(11, inventario.getVidaUtilAno());
        statement.setDouble(12, inventario.getValorAtual());
        statement.setString(13, inventario.getDepreciacao());
        if (inventario.getIdInventario() != 0) {
            statement.setInt(14, inventario.getIdInventario());
        }
        statement.executeUpdate();
    }

    @Override
    public void update(Inventario inventario) {
        UPDATE_QUERY.append("UPDATE ").append(db)
                .append(".tb_inventario SET num_serie =?, fk_categoria =?, item =?, responsavel =?, fk_area =?, local =?, data_compra =?, meses_desde_compra =?, valor =?, estado_consrvacao =?, vida_util_ano =?, valor_atual =?, depreciacao =? WHERE id_inventario =?");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    UPDATE_QUERY.toString()
            )) {
                mapToSave(pstmt, inventario);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    @Override
    public void delete(Integer idInventario) {
        final StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(db).append(".tb_inventario WHERE id_inventario=?");
        remove(query.toString(), idInventario);
    }

    @Override
    public List<Inventario> findAll() {
        List<Inventario> inventario = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT * from ").append(db).append(".tb_inventario");
        try (Connection conn = HikariCPDataSource.getConnection()) {
            preparedStatement = conn.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Inventario item = new InventarioBuilder().setIdInventario(resultSet.getInt(1)).setNumSerie(resultSet.getString(2)).setCategoria(resultSet.getString(3)).setItem(resultSet.getString(4)).setResponsavel(resultSet.getString(5)).setArea(resultSet.getString(6)).setLocal(resultSet.getString(7)).setDataDeCompra(Tempo.toDate(resultSet.getTimestamp(8))).setMesesDesdeCompra(resultSet.getInt(9)).setValor(resultSet.getDouble(10)).setEstadoDeConservacao(resultSet.getString(11)).setVidaUtil(resultSet.getInt(12)).setValorAtual(resultSet.getDouble(13)).setDepreciacao(resultSet.getString(14)).setDataCadastro(Tempo.toDate(resultSet.getTimestamp(15))).createInventario();
                    inventario.add(item);
                }
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return inventario;
    }

    @Override
    public boolean isNumSerie(String nome, int id) {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT num_serie FROM ").append(db).append(".tb_inventario WHERE num_serie =? AND id_inventario !=? ");

        try (Connection conn = HikariCPDataSource.getConnection()) {
            preparedStatement = conn.prepareStatement(query.toString());
            preparedStatement.setString(1, nome);
            preparedStatement.setInt(2, id);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    return set.getString(1).equalsIgnoreCase(nome);
                }
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return false;
    }

    @Override
    public int total() {
        StringBuilder length = new StringBuilder();
        length.append("SELECT COUNT(*) FROM ").append(db).append(".tb_inventario");
        return count(length.toString());
    }

    @Override
    public void report() {
        try (Connection conn = HikariCPDataSource.getConnection()) {
            URL url = getClass().getResource("/cv/com/escola/reports/inventario.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            //null: caso não existem filtros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);

            JasperViewerFX jasperViewer = new JasperViewerFX();
            jasperViewer.viewReport("Inventario", jasperPrint);
        } catch (JRException ex) {
            Logger.getLogger(InventarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }
}
