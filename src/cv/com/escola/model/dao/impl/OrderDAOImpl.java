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

    private List<Item> itemDeVenda;
    private static final String SELECT_FROM = "SELECT * FROM ";
    private static final String INSERT_INTO = "INSERT INTO ";
    
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
        try (Connection conector = HikariCPDataSource.getConnection();) {
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
        List<Venda> retorno = new ArrayList<>();
        try (Connection conector = HikariCPDataSource.getConnection();) {
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getInt(8), rs.getString(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14),
                        rs.getString(15), rs.getString(16));

                Usuario usuario = new Usuario(rs.getInt(17), rs.getString(18));

                Venda vendas = new Venda(rs.getInt(1), rs.getDate(2).toLocalDate(),
                        rs.getBigDecimal(3), rs.getBoolean(4), rs.getString(5),
                        rs.getBigDecimal(6), rs.getString(7), cliente, usuario, rs.getBigDecimal(19));

                itemDeVenda = DAOFactory.daoFactury().itemDAO().listarItensPorVenda(vendas);
                vendas.setItens(FXCollections.observableArrayList(itemDeVenda));
                retorno.add(vendas);
            }
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return retorno;
    }

    @Override
    public ObservableList<Venda> listar(int quantidade, int pagina) {
        ObservableList retorno = FXCollections.observableArrayList();
        final StringBuilder query = new StringBuilder();
        query.append(SELECT_FROM).append(db).append(".venda_view order by num_fatura desc limit ").append(quantidade * pagina).append(",").append(quantidade).append(";");
        try (Connection conector = HikariCPDataSource.getConnection();) {
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getInt(8), rs.getString(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14),
                        rs.getString(15), rs.getString(16));

                Usuario usuario = new Usuario(rs.getInt(17), rs.getString(18));

                Venda vendas = new Venda(rs.getInt(1), rs.getDate(2).toLocalDate(),
                        rs.getBigDecimal(3), rs.getBoolean(4), rs.getString(5),
                        rs.getBigDecimal(6), rs.getString(7), cliente, usuario, rs.getBigDecimal(19));

                itemDeVenda = DAOFactory.daoFactury().itemDAO().listarItensPorVenda(vendas);
                vendas.setItens(FXCollections.observableArrayList(itemDeVenda));
                retorno.add(vendas);
            }
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return retorno;
    }

    @Override
    public int count() {
        try (Connection conector = HikariCPDataSource.getConnection();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT COUNT(*) FROM ").append(db).append(".tb_vendas");
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return 0;
    }

    //buscar o ultimo id de venda realizada no ano
    @Override
    public int ultimoRegisto(int ano) {
        int j = 0;
        try (Connection conector = HikariCPDataSource.getConnection();) {
            final StringBuilder query = new StringBuilder();
            query.append("select ifNull(count(id_vendas), 0) as max_id from ").append(db).append(".tb_vendas where extract(year from data)=").append(ano).append(";");
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                j = rs.getInt(1);
            }
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

        try (Connection conector = HikariCPDataSource.getConnection();) {
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                retorno.setIdVenda(rs.getInt("last_id"));
                return retorno;
            }
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
        try (Connection conector = HikariCPDataSource.getConnection();) {
            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setInt(1, venda.getIdVenda());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente(rs.getInt(8), rs.getString(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14),
                        rs.getString(15), rs.getString(16));

                Usuario usuario = new Usuario(rs.getInt(17), rs.getString(18));

                retorno = new Venda(rs.getInt(1), rs.getDate(2).toLocalDate(),
                        rs.getBigDecimal(3), rs.getBoolean(4), rs.getString(5),
                        rs.getBigDecimal(6), rs.getString(7), cliente, usuario, rs.getBigDecimal(19));

                retorno = venda;
            }
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
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                j = rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return j;
    }

    @Override
    public Map<Integer, ArrayList> listarQuantidadeVendaPorMes() {
        final StringBuilder query = new StringBuilder();
        query.append("select count(id_vendas) as count, extract(year from data) as ano, extract(month from data) as mes from ").append(db).append(".tb_vendas group by ano, mes order by ano, mes;");
        Map<Integer, ArrayList> retorno = new HashMap();
        try (Connection conector = HikariCPDataSource.getConnection();) {
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ArrayList linha = new ArrayList();
                if (!retorno.containsKey(rs.getInt("ano"))) {
                    linha.add(rs.getInt("mes"));
                    linha.add(rs.getInt("count"));
                    retorno.put(rs.getInt("ano"), linha);
                } else {
                    ArrayList linhaNova = retorno.get(rs.getInt("ano"));
                    linhaNova.add(rs.getInt("mes"));
                    linhaNova.add(rs.getInt("count"));
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return retorno;
    }

    @Override
    public Map<Integer, ArrayList> listarQuantidadeVendaPorDia(String mes, String ano) {
        final StringBuilder query = new StringBuilder();
        query.append("select count(id_vendas) as count, extract(month from data) as mes, extract(day from data) as dia from ").append(db).append(".tb_vendas where extract(month from data) = ").append(mes).append(" and extract(year from data) = ").append(ano).append(" group by dia order by dia;");
        Map<Integer, ArrayList> retorno = new HashMap();
        try (Connection conector = HikariCPDataSource.getConnection();) {
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ArrayList linha = new ArrayList();
                if (!retorno.containsKey(rs.getInt("mes"))) {
                    linha.add(rs.getInt("dia"));
                    linha.add(rs.getInt("count"));
                    retorno.put(rs.getInt("mes"), linha);
                } else {
                    ArrayList linhaNova = retorno.get(rs.getInt("mes"));
                    linhaNova.add(rs.getInt("dia"));
                    linhaNova.add(rs.getInt("count"));
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return retorno;
    }

    @Override
    public Map<Integer, ArrayList> listarValorTotalVendaPorMes() {
        final StringBuilder query = new StringBuilder();
        query.append("select sum(precoTotal) as sum, extract(year from data) as ano, extract(month from data) as mes from ").append(db).append(".tb_vendas group by ano, mes order by ano, mes;");
        Map<Integer, ArrayList> retorno = new HashMap();
        try (Connection conector = HikariCPDataSource.getConnection();) {
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ArrayList linha = new ArrayList();
                if (!retorno.containsKey(rs.getInt("ano"))) {
                    linha.add(rs.getInt("mes"));
                    linha.add(rs.getBigDecimal("sum"));
                    retorno.put(rs.getInt("ano"), linha);
                } else {
                    ArrayList linhaNova = retorno.get(rs.getInt("ano"));
                    linhaNova.add(rs.getInt("mes"));
                    linhaNova.add(rs.getBigDecimal("sum"));
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return retorno;
    }

    @Override
    public Map<Integer, ArrayList> listarValorTotalVendaPorMes(String ano) {
        final StringBuilder query = new StringBuilder();
        query.append("select sum(precoTotal) as sum, extract(year from data) as ano, extract(month from data) as mes from ").append(db).append(".tb_vendas where extract(year from data) = ").append(ano).append(" group by ano, mes order by ano, mes ;");
        Map<Integer, ArrayList> retorno = new HashMap();
        try (Connection conector = HikariCPDataSource.getConnection();) {
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ArrayList linha = new ArrayList();
                if (!retorno.containsKey(rs.getInt("ano"))) {
                    linha.add(rs.getInt("mes"));
                    linha.add(rs.getBigDecimal("sum"));
                    retorno.put(rs.getInt("ano"), linha);
                } else {
                    ArrayList linhaNova = retorno.get(rs.getInt("ano"));
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
        try (Connection conector = HikariCPDataSource.getConnection();) {
            final StringBuilder query = new StringBuilder();
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
        try (Connection conector = HikariCPDataSource.getConnection();) {
            HashMap filtro = new HashMap();
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
