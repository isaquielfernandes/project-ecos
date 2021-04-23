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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Level;

public class OrderDAOImpl extends DAO implements OrderDAO {

    private List<Item> itemDeVenda = new ArrayList<>();
    private static final String SELECT_FROM = "SELECT * FROM ";
    private static final String INSERT_INTO = "INSERT INTO ";
    private static final String MES = "mes";
    private static final String ANO = "ano";
    private static final String COUNT = "count";

    public OrderDAOImpl() {
        super();
    }

    @Override
    public void create(Venda venda) {
        final StringBuilder query = new StringBuilder();
        query.append(INSERT_INTO).append(db).append(".`tb_vendas`(`data`, `valor_total`, ");
        query.append("`pago`, `cliente_fk`, `id_user`, `meioDePag`, `desconto`, ");
        query.append("`num_fatura`, precoTotal) VALUES (?,?,?,?,?,?,?,?,?);");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    query.toString()
            )) {
                pstmt.setTimestamp(1, Tempo.toTimestamp(venda.getData()));
                pstmt.setBigDecimal(2, venda.getValor());
                pstmt.setBoolean(3, venda.isPago());
                pstmt.setInt(4, venda.getCliente().getIdCliente());
                pstmt.setInt(5, venda.getUsuario().getId());
                pstmt.setString(6, venda.getMeioDePagamento());
                pstmt.setBigDecimal(7, venda.getDesconto());
                pstmt.setString(8, venda.getNumFatura());
                pstmt.setBigDecimal(9, venda.getValorTotal());

                pstmt.execute();
            } catch (SQLException ex) {
                throw new DataAccessException(Level.ERROR.toString(), ex);
            }
        });
    }

    @Override
    public void update(Venda venda) {
        final StringBuilder query = new StringBuilder();
        query.append("UPDATE ").append(db).append(".`tb_vendas` SET `data` = ?,`valor_total` = ?,");
        query.append("`pago` = ?,`cliente_fk` = ?, `id_user` = ?, `meioDePag` = ?, ");
        query.append("`desconto` = ?, `num_fatura` = ?, precoTotal=? WHERE `id_vendas` = ?;");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    query.toString()
            )) {
                pstmt.setTimestamp(1, Tempo.toTimestamp(venda.getData()));
                pstmt.setBigDecimal(2, venda.getValor());
                pstmt.setBoolean(3, venda.isPago());
                pstmt.setInt(4, venda.getCliente().getIdCliente());
                pstmt.setInt(5, venda.getUsuario().getId());
                pstmt.setString(6, venda.getMeioDePagamento());
                pstmt.setBigDecimal(7, venda.getDesconto());
                pstmt.setString(8, venda.getNumFatura());
                pstmt.setBigDecimal(9, venda.getValorTotal());
                pstmt.setInt(10, venda.getIdVenda());

                pstmt.execute();
            } catch (SQLException ex) {
                throw new DataAccessException(Level.ERROR.toString(), ex);
            }
        });
    }

    @Override
    public void delete(Integer idVenda) {
        try (Connection conector = HikariCPDataSource.getInstance().getConnection()) {
            final StringBuilder query = new StringBuilder();
            query.append("DELETE FROM ").append(db).append(".tb_vendas WHERE id_vendas=?");
            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setInt(1, idVenda);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
    }

    @Override
    public List<Venda> findAll() {
        final StringBuilder query = new StringBuilder();
        query.append(SELECT_FROM).append(db).append(".venda_view order by num_fatura desc;");
        List<Venda> vendas = new ArrayList<>();
        try (Connection conector = HikariCPDataSource.getInstance().getConnection()) {
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    mapResultSet(resultSet, vendas);
                }
            }
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return vendas;
    }

    @Override
    public ObservableList<Venda> listar(int quantidade, int pagina) {
        List<Venda> vendas = FXCollections.observableArrayList();
        final StringBuilder query = new StringBuilder();
        query.append(SELECT_FROM).append(db).append(".venda_view ORDER BY data desc, num_fatura desc limit ").append(quantidade * pagina).append(",").append(quantidade).append(";");
        try (Connection conector = HikariCPDataSource.getInstance().getConnection()) {
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    mapResultSet(resultSet, vendas);
                }
            }
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
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
            itemDeVenda = DAOFactory.daoFactury().itemDAO().listarItensPorVenda(venda);
        }
        venda.setItens(FXCollections.observableArrayList(itemDeVenda));
        vendas.add(venda);
    }

    @Override
    public int count() {
        try (Connection conector = HikariCPDataSource.getInstance().getConnection()) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT COUNT(*) FROM ").append(db).append(".tb_vendas");
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            rs.close();
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return 0;
    }

    //buscar o ultimo id de venda realizada no ano
    @Override
    public int ultimoRegisto(int ano) {
        int j = 0;
        try (Connection conector = HikariCPDataSource.getInstance().getConnection()) {
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

        try (Connection conector = HikariCPDataSource.getInstance().getConnection()) {
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    retorno.setIdVenda(resultSet.getInt("last_id"));
                }
            }
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return retorno;
    }

    @SuppressWarnings("UnusedAssignment")
    @Override
    public Venda buscar(Venda venda) {
        final StringBuilder query = new StringBuilder();
        query.append(SELECT_FROM).append(db).append(".venda_view where ").append(db).append(".venda_view.id_vendas=?");
        Venda retorno = null;
        try (Connection conector = HikariCPDataSource.getInstance().getConnection()) {
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
        try (Connection conector = HikariCPDataSource.getInstance().getConnection()) {
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
        try (Connection conector = HikariCPDataSource.getInstance().getConnection()) {
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ArrayList<Number> linha = new ArrayList<>();
                if (!retorno.containsKey(rs.getInt("mes"))) {
                    linha.add(rs.getInt("dia"));
                    linha.add(rs.getInt(COUNT));
                    retorno.put(rs.getInt("mes"), linha);
                } else {
                    ArrayList<Number> linhaNova = retorno.get(rs.getInt("mes"));
                    linhaNova.add(rs.getInt("dia"));
                    linhaNova.add(rs.getInt(COUNT));
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return retorno;
    }

    @Override
    public Map<Integer, ArrayList<Number>> listarValorTotalVendaPorMes() {
        final StringBuilder query = new StringBuilder();
        query.append("select sum(precoTotal) as sum, extract(year from data) as ano, extract(month from data) as mes from ").append(db).append(".tb_vendas group by ano, mes order by ano, mes;");
        Map<Integer, ArrayList<Number>> retorno = new HashMap<>();
        try (Connection conector = HikariCPDataSource.getInstance().getConnection()) {
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ArrayList<Number> linha = new ArrayList<>();
                if (!retorno.containsKey(rs.getInt("ano"))) {
                    linha.add(rs.getInt("mes"));
                    linha.add(rs.getBigDecimal("sum"));
                    retorno.put(rs.getInt("ano"), linha);
                } else {
                    ArrayList<Number> linhaNova = retorno.get(rs.getInt("ano"));
                    linhaNova.add(rs.getInt("mes"));
                    linhaNova.add(rs.getBigDecimal("sum"));
                }
            }
            rs.close();
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return retorno;
    }

    @Override
    public Map<Integer, ArrayList<Number>> listarValorTotalVendaPorMes(String ano) {
        final StringBuilder query = new StringBuilder();
        query.append("select sum(precoTotal) as sum, extract(year from data) as ano, extract(month from data) as mes from ").append(db).append(".tb_vendas where extract(year from data) = ").append(ano).append(" group by ano, mes order by ano, mes ;");
        Map<Integer, ArrayList<Number>> retorno = new HashMap<>();
        try (Connection conector = HikariCPDataSource.getInstance().getConnection()) {
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ArrayList<Number> linha = new ArrayList<>();
                if (!retorno.containsKey(rs.getInt("ano"))) {
                    linha.add(rs.getInt("mes"));
                    linha.add(rs.getBigDecimal("sum"));
                    retorno.put(rs.getInt("ano"), linha);
                } else {
                    ArrayList<Number> linhaNova = retorno.get(rs.getInt("ano"));
                    linhaNova.add(rs.getInt("mes"));
                    linhaNova.add(rs.getBigDecimal("sum"));
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return retorno;
    }

    // total de vendas por ano
    @Override
    public BigDecimal totalAnual(String ano) {
        final StringBuilder query = new StringBuilder();
        try (Connection conector = HikariCPDataSource.getInstance().getConnection()) {
            query.append("select sum(precoTotal) as sum from ").append(db).append(".tb_vendas where extract(year from data) = ").append(ano).append(";");
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal(1);
            }
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public void reportReciboFatura(int biFiltro) {
        try (Connection conector = HikariCPDataSource.getInstance().getConnection()) {
            HashMap filtro = new HashMap<>();
            filtro.put("id_venda", biFiltro);

            URL url = getClass().getResource("/cv/com/escola/reports/reciboFatura.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, conector);//null: caso não existam filtros

            Print jasperViewer = new Print();//(jasperPrint, false);//false: não deixa fechar a aplicação principal
            jasperViewer.viewReport("Recibo/Fatura", jasperPrint);

        } catch (JRException ex) {
            throw new ReportException(Level.ERROR.toString(), ex);
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
    }
}
