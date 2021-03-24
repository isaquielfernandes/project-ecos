package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Inventario;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.InventariaDAO;
import cv.com.escola.model.util.JasperViewerFX;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Tempo;
import java.net.URL;
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
import net.sf.jasperreports.view.JasperViewer;

public class InventarioDAOImpl extends DAO implements InventariaDAO {

    public InventarioDAOImpl() {
        super();
    }

    @Override
    public void create(Inventario inventario) {
        try {
            String sql = "insert into " + db + ".tb_inventario ( num_serie, fk_categoria, item, responsavel, "
                    + "fk_area, local, data_compra, meses_desde_compra, valor, estado_consrvacao, "
                    + "vida_util_ano, valor_atual, depreciacao ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            stm = conector.prepareStatement(sql);

            stm.setString(1, inventario.getNumSerie());
            stm.setString(2, inventario.getCategoria());
            stm.setString(3, inventario.getItem());
            stm.setString(4, inventario.getResponsavel());
            stm.setString(5, inventario.getArea());
            stm.setString(6, inventario.getLocal());
            stm.setTimestamp(7, Tempo.toTimestamp(inventario.getDataCompra()));
            stm.setInt(8, inventario.getMesesDesdeCompra());
            stm.setDouble(9, inventario.getValor());
            stm.setString(10, inventario.getEstadoConservacao());
            stm.setInt(11, inventario.getVidaUtilAno());
            stm.setDouble(12, inventario.getValorAtual());
            stm.setString(13, inventario.getDepreciacao());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao lançar inventario na base de dados! \n" + ex);
        }
    }

    @Override
    public void update(Inventario inventario) {
        try {
            String sql = "UPDATE " + db + ".tb_inventario SET num_serie =?, fk_categoria =?, item =?, responsavel =?, "
                    + "fk_area =?, local =?, data_compra =?, meses_desde_compra =?, valor =?, estado_consrvacao =?, "
                    + "vida_util_ano =?, valor_atual =?, depreciacao =? WHERE id_inventario =?";

            stm = conector.prepareStatement(sql);

            stm.setString(1, inventario.getNumSerie());
            stm.setString(2, inventario.getCategoria());
            stm.setString(3, inventario.getItem());
            stm.setString(4, inventario.getResponsavel());
            stm.setString(5, inventario.getArea());
            stm.setString(6, inventario.getLocal());
            stm.setTimestamp(7, Tempo.toTimestamp(inventario.getDataCompra()));
            stm.setInt(8, inventario.getMesesDesdeCompra());
            stm.setDouble(9, inventario.getValor());
            stm.setString(10, inventario.getEstadoConservacao());
            stm.setInt(11, inventario.getVidaUtilAno());
            stm.setDouble(12, inventario.getValorAtual());
            stm.setString(13, inventario.getDepreciacao());
            stm.setInt(14, inventario.getIdInventario());
            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao editar item do inventario na base de dados! \n" + ex);
        }
    }

    @Override
    public void delete(Integer idInventario) {
        try {
            String sql = "DELETE FROM " + db + ".tb_inventario WHERE id_inventario=?";
            stm = conector.prepareStatement(sql);
            stm.setInt(1, idInventario);
            stm.execute();
            stm.close();
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir item do inventario na base de dados! \n" + ex);
        }
    }

    @Override
    public List<Inventario> findAll() {
        List<Inventario> inventario = new ArrayList<>();
        try {

            String sql = "SELECT * from " + db + ".tb_inventario";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Inventario item = new Inventario(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), Tempo.toDate(rs.getTimestamp(8)),
                        rs.getInt(9), rs.getDouble(10), rs.getString(11),
                        rs.getInt(12), rs.getDouble(13), rs.getString(14),
                        Tempo.toDate(rs.getTimestamp(15)));
                inventario.add(item);
            }

            stm.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(InventarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao listar item do inventario na base de dados! \n" + ex);
        }
        return inventario;
    }

    @Override
    public boolean isNumSerie(String nome, int id) {
        try {
            String sql = "SELECT num_serie FROM " + db + ".tb_inventario WHERE num_serie =? AND id_inventario !=? ";

            stm = conector.prepareStatement(sql);
            stm.setString(1, nome);
            stm.setInt(2, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getString(1).toLowerCase().trim().equals(nome.toLowerCase().trim().toLowerCase());
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao validar numero de serie do item num inventario na base de dados! \n" + ex);
        }

        return false;
    }

    @Override
    public int total() {
        try {
            String sql = "SELECT COUNT(*) FROM " + db + ".tb_inventario";
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar total de item de inventario na base de dados");
        }
        return 0;
    }

    @Override
    public void report() {
        try {
            URL url = getClass().getResource("/cv/com/escola/reports/inventario.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            //null: caso não existem filtros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conector);

            JasperViewerFX jasperViewer = new JasperViewerFX();
            jasperViewer.viewReport("Inventario", jasperPrint);
        } catch (JRException ex) {
            Logger.getLogger(InventarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao imprimir inventario! \n" + ex);
        }
    }
}
