package cv.com.escola.model.dao.impl;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Cliente;
import cv.com.escola.model.entity.Item;
import cv.com.escola.model.entity.Usuario;
import cv.com.escola.model.entity.Venda;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.OrderDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.dao.exception.ReportException;
import cv.com.escola.model.util.Print;
import cv.com.escola.model.util.Tempo;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Level;

@Slf4j
public class OrderDAOImpl extends DAO implements OrderDAO {

    private static final StringBuilder INSERT_ITEM = new StringBuilder();
    private static final StringBuilder INSERT_VENDA = new StringBuilder();
    private List<Item> itemDeVenda = new ArrayList<>();
    private static final String SELECT_FROM = "SELECT * FROM ";
    private static final String INSERT_INTO = "INSERT INTO ";
    private static final String MES = "mes";
    private static final String ANO = "ano";
    private static final String DIA = "dia";
    private static final String SUM = "sum";
    private static final String COUNT = "count";

    public OrderDAOImpl() {
        super();
    }

    @Override
    public void create(Venda venda) {
        INSERT_VENDA.append(INSERT_INTO).append(db).append(".tb_vendas (data, valor_total, ");
        INSERT_VENDA.append("pago, cliente_fk, id_user, meioDePag, desconto, ");
        INSERT_VENDA.append("num_fatura, precoTotal) VALUES (?,?,?,?,?,?,?,?,?);");

        INSERT_ITEM.append(INSERT_INTO).append(db)
                .append(".tb_item_venda(quantidade, valor, id_artigo, id_venda) VALUES (?,?,?,?);");

        transact(connection -> {
            try (PreparedStatement orderStatement = connection.prepareStatement(
                        INSERT_VENDA.toString(), Statement.RETURN_GENERATED_KEYS);
                    
                PreparedStatement itemStatement = connection.prepareStatement(
                        INSERT_ITEM.toString()
            )) {
                int index = 0;
                orderStatement.setTimestamp(++index, Tempo.toTimestamp(venda.getData()));
                orderStatement.setBigDecimal(++index, venda.getValor());
                orderStatement.setBoolean(++index, venda.isPago());
                orderStatement.setInt(++index, venda.getCliente().getIdCliente());
                orderStatement.setInt(++index, venda.getUsuario().getId());
                orderStatement.setString(++index, venda.getMeioDePagamento());
                orderStatement.setBigDecimal(++index, venda.getDesconto());
                orderStatement.setString(++index, venda.getNumFatura());
                orderStatement.setBigDecimal(++index, venda.getValorTotal());

                int rowAffected = orderStatement.executeUpdate();
                ResultSet resultSet = orderStatement.getGeneratedKeys();

                int getLastId = 0;
                if (resultSet.next()) {
                    getLastId = resultSet.getInt(1);
                    log.info(MessageFormat.format("ID: {0}", getLastId));
                }

                if (rowAffected == 1) {
                    for (Item item : venda.getItens()) {
                        itemStatement.setInt(1, item.getQuantidade());
                        itemStatement.setBigDecimal(2, item.getValorUnitario());
                        itemStatement.setLong(3, item.getArtigo().getId());
                        itemStatement.setInt(4, getLastId);
                        itemStatement.executeUpdate();
                    }
                } 
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    @Override
    public void update(Venda venda) {
        StringBuilder updateQuery = new StringBuilder();
        updateQuery.append("UPDATE ").append(db).append(".tb_vendas SET data = ?, valor_total = ?,");
        updateQuery.append("pago = ?, cliente_fk = ?, id_user = ?, meioDePag = ?, ");
        updateQuery.append("desconto = ?, num_fatura = ?, precoTotal = ? WHERE id_vendas = ?;");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    updateQuery.toString()
            )) {
                mapToSave(pstmt, venda);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    private void mapToSave(final PreparedStatement pstmt, Venda venda) throws SQLException {
        int index = 0;
        pstmt.setTimestamp(++index, Tempo.toTimestamp(venda.getData()));
        pstmt.setBigDecimal(++index, venda.getValor());
        pstmt.setBoolean(++index, venda.isPago());
        pstmt.setInt(++index, venda.getCliente().getIdCliente());
        pstmt.setInt(++index, venda.getUsuario().getId());
        pstmt.setString(++index, venda.getMeioDePagamento());
        pstmt.setBigDecimal(++index, venda.getDesconto());
        pstmt.setString(++index, venda.getNumFatura());
        pstmt.setBigDecimal(++index, venda.getValorTotal());

        if (venda.getIdVenda() != 0) {
            pstmt.setInt(++index, venda.getIdVenda());
        }
        pstmt.executeUpdate();
    }

    @Override
    public void delete(Integer idVenda) {
        StringBuilder deleteVenda = new StringBuilder();
        deleteVenda.append("DELETE FROM ").append(db).append(".tb_vendas WHERE id_vendas=?;");
        remove(deleteVenda.toString(), idVenda);
    }

    @Override
    public List<Venda> findAll() {
        final StringBuilder query = new StringBuilder();
        query.append(SELECT_FROM).append(db).append(".venda_view order by num_fatura desc;");
        List<Venda> vendas = new ArrayList<>();
        try (Connection conector = HikariCPDataSource.getConnection()) {
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    mapResultSet(resultSet, vendas);
                }
            }
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return vendas;
    }

    @Override
    public ObservableList<Venda> listar(int limit, int offset) {
        List<Venda> vendas = FXCollections.observableArrayList();
        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append(SELECT_FROM).append(db).append(".venda_view ORDER BY data desc, num_fatura desc LIMIT ? OFFSET ?  ");
        try (Connection conector = HikariCPDataSource.getConnection();
                PreparedStatement pstmt = conector.prepareStatement(selectQuery.toString());) {
            pstmt.setInt(1, limit);
            pstmt.setInt(2, offset);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    mapResultSet(resultSet, vendas);
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return FXCollections.observableArrayList(vendas);
    }

    private void mapResultSet(ResultSet resultSet, List<Venda> vendas) throws SQLException {
        Cliente cliente = new Cliente(resultSet.getInt(8), resultSet.getString(9), resultSet.getString(10),
                resultSet.getString(11), resultSet.getString(12), resultSet.getString(13), resultSet.getString(14),
                resultSet.getString(15), resultSet.getString(16));

        Usuario usuario = new Usuario(resultSet.getInt(17), resultSet.getString(18));

        Venda venda = new Venda(resultSet.getInt(1), resultSet.getDate(2).toLocalDate(),
                resultSet.getBigDecimal(3), resultSet.getBoolean(4), resultSet.getString(5),
                resultSet.getBigDecimal(6), resultSet.getString(7), cliente, usuario, resultSet.getBigDecimal(19));
        if (itemDeVenda.isEmpty()) {
            itemDeVenda = DAOFactory.daoFactory().itemDAO().listarItensPorVenda(venda);
        }
        venda.setItens(FXCollections.observableArrayList(itemDeVenda));
        vendas.add(venda);
    }

    @Override
    public int count() {
        try (Connection conector = HikariCPDataSource.getConnection()) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT COUNT(*) FROM ").append(db).append(".tb_vendas");
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return 0;
    }

    //buscar o ultimo id de venda realizada no ano
    @Override
    public int ultimoRegisto(int ano) {
        int j = 0;
        try (Connection conector = HikariCPDataSource.getConnection()) {
            final StringBuilder query = new StringBuilder();
            query.append("select ifNull(count(id_vendas), 0) as max_id from ").append(db).append(".tb_vendas where extract(year from data)=").append(ano).append(";");
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                j = rs.getInt(1);
            }
            rs.close();
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return j;
    }

    //Buscar Ultima venda realizada
    @Override
    public Venda buscarUltimaVenda() {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT max(id_vendas) as last_id FROM ").append(db).append(".tb_vendas;");
        Venda retorno = new Venda();

        try (Connection conector = HikariCPDataSource.getConnection()) {
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    retorno.setIdVenda(resultSet.getInt("last_id"));
                }
            }
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return retorno;
    }

    @SuppressWarnings("UnusedAssignment")
    @Override
    public Venda buscar(Venda venda) {
        final StringBuilder query = new StringBuilder();
        query.append(SELECT_FROM).append(db).append(".venda_view where ").append(db).append(".venda_view.id_vendas=?");
        Venda retorno = null;
        try (Connection conector = HikariCPDataSource.getConnection()) {
            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setInt(1, venda.getIdVenda());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Cliente cliente = new Cliente(resultSet.getInt(8), resultSet.getString(9), resultSet.getString(10),
                            resultSet.getString(11), resultSet.getString(12), resultSet.getString(13), resultSet.getString(14),
                            resultSet.getString(15), resultSet.getString(16));

                    Usuario usuario = new Usuario(resultSet.getInt(17), resultSet.getString(18));

                    retorno = new Venda(resultSet.getInt(1), resultSet.getDate(2).toLocalDate(),
                            resultSet.getBigDecimal(3), resultSet.getBoolean(4), resultSet.getString(5),
                            resultSet.getBigDecimal(6), resultSet.getString(7), cliente, usuario, resultSet.getBigDecimal(19));

                    retorno = venda;
                }
            }
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return retorno;
    }

    @Override
    public int ultimoRegisto() {
        int j = 0;
        final StringBuilder query = new StringBuilder();
        query.append("select max(id_vendas) from ").append(db).append(".tb_vendas");
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    j = resultSet.getInt(1);
                }
            }
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return j;
    }

    @Override
    public Map<Integer, ArrayList<Number>> listarQuantidadeVendaPorMes() {
        final StringBuilder query = new StringBuilder();
        query.append("select count(id_vendas) as count, extract(year from data) as ano, extract(month from data) as mes from ").append(db).append(".tb_vendas group by ano, mes order by ano, mes;");
        Map<Integer, ArrayList<Number>> retorno = new HashMap<>();
        try (Connection conector = HikariCPDataSource.getConnection()) {
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ArrayList<Number> linha = new ArrayList<>();
                    if (!retorno.containsKey(resultSet.getInt(ANO))) {
                        linha.add(resultSet.getInt(MES));
                        linha.add(resultSet.getInt(COUNT));
                        retorno.put(resultSet.getInt(ANO), linha);
                    } else {
                        ArrayList<Number> linhaNova = retorno.get(resultSet.getInt(ANO));
                        linhaNova.add(resultSet.getInt(MES));
                        linhaNova.add(resultSet.getInt(COUNT));
                    }
                }
            }
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return retorno;
    }

    @Override
    public Map<Integer, ArrayList<Number>> listarQuantidadeVendaPorDia(String mes, String ano) {
        final StringBuilder query = new StringBuilder();
        query.append("select count(id_vendas) as count, extract(month from data) as mes, extract(day from data) as dia from ").append(db).append(".tb_vendas where extract(month from data) = ").append(mes).append(" and extract(year from data) = ").append(ano).append(" group by dia order by dia;");
        Map<Integer, ArrayList<Number>> retorno = new HashMap<>();
        try (Connection conector = HikariCPDataSource.getConnection()) {
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ArrayList<Number> linha = new ArrayList<>();
                if (!retorno.containsKey(rs.getInt(MES))) {
                    linha.add(rs.getInt(DIA));
                    linha.add(rs.getInt(COUNT));
                    retorno.put(rs.getInt(MES), linha);
                } else {
                    ArrayList<Number> linhaNova = retorno.get(rs.getInt(MES));
                    linhaNova.add(rs.getInt(DIA));
                    linhaNova.add(rs.getInt(COUNT));
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return retorno;
    }

    @Override
    public Map<Integer, ArrayList<Number>> listarValorTotalVendaPorMes() {
        final StringBuilder query = new StringBuilder();
        query.append("select sum(precoTotal) as sum, extract(year from data) as ano, extract(month from data) as mes from ").append(db).append(".tb_vendas group by ano, mes order by ano, mes;");
        Map<Integer, ArrayList<Number>> retorno = new HashMap<>();
        try (Connection conector = HikariCPDataSource.getConnection()) {
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    mapObjectToReport(resultSet, retorno);
                }
            }
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return retorno;
    }

    @Override
    public Map<Integer, ArrayList<Number>> listarValorTotalVendaPorMes(String ano) {
        final StringBuilder query = new StringBuilder();
        query.append("select sum(precoTotal) as sum, extract(year from data) as ano, extract(month from data) as mes from ").append(db).append(".tb_vendas where extract(year from data) = ").append(ano).append(" group by ano, mes order by ano, mes ;");
        Map<Integer, ArrayList<Number>> retorno = new HashMap<>();
        try (Connection conector = HikariCPDataSource.getConnection()) {
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    mapObjectToReport(resultSet, retorno);
                }
            }
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return retorno;
    }

    // total de vendas por ano
    @Override
    public BigDecimal totalAnual(String ano) {
        final StringBuilder query = new StringBuilder();
        query.append("select sum(precoTotal) as sum from ").append(db).append(".tb_vendas where extract(year from data) = ").append(ano).append(";");

        try (Connection conector = HikariCPDataSource.getConnection()) {
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal(1);
                }
            }
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public void reportReciboFatura(int biFiltro) {
        try (Connection conector = HikariCPDataSource.getConnection()) {
            Map<String, Object> filtro = new HashMap<>();
            filtro.put("id_venda", biFiltro);

            URL url = getClass().getResource("/cv/com/escola/reports/reciboFatura.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, conector);//null: caso não existam filtros

            Print jasperViewer = new Print();
            jasperViewer.viewReport("Recibo/Fatura", jasperPrint);

        } catch (JRException ex) {
            throw new ReportException(ex);
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    private void mapObjectToReport(final ResultSet resultSet, Map<Integer, ArrayList<Number>> retorno) throws SQLException {
        ArrayList<Number> linha = new ArrayList<>();
        if (!retorno.containsKey(resultSet.getInt(ANO))) {
            linha.add(resultSet.getInt(MES));
            linha.add(resultSet.getBigDecimal(SUM));
            retorno.put(resultSet.getInt(ANO), linha);
        } else {
            ArrayList<Number> linhaNova = retorno.get(resultSet.getInt(ANO));
            linhaNova.add(resultSet.getInt(MES));
            linhaNova.add(resultSet.getBigDecimal(SUM));
        }
    }

}
