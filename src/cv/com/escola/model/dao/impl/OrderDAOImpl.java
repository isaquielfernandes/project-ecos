package cv.com.escola.model.dao.impl;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Cliente;
import cv.com.escola.model.entity.Item;
import cv.com.escola.model.entity.Usuario;
import cv.com.escola.model.entity.Venda;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.OrderDAO;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Nota;
import cv.com.escola.model.util.Print;
import cv.com.escola.model.util.Tempo;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.jdbc.core.JdbcTemplate;


public class OrderDAOImpl extends DAO implements OrderDAO {

    private final JdbcTemplate jdbcTemplate;
    private List<Item> itemDeVenda;
    
    public OrderDAOImpl() {
        super();
        jdbcTemplate = new JdbcTemplate();
    }

    @Override
    public void create(Venda venda) {
        try {
            String sql = "INSERT INTO " + db + ".`tb_vendas`(`data`, `valor_total`, "
                    + "`pago`, `cliente_fk`, `id_user`, `meioDePag`, `desconto`, "
                    + "`num_fatura`, precoTotal) VALUES (?,?,?,?,?,?,?,?,?);";

            preparedStatement = conector.prepareStatement(sql);

            preparedStatement.setTimestamp(1, Tempo.toTimestamp(venda.getData()));
            preparedStatement.setBigDecimal(2, venda.getValor());
            preparedStatement.setBoolean(3, venda.isPago());
            preparedStatement.setInt(4, venda.getCliente().getIdCliente());
            preparedStatement.setInt(5, venda.getUsuario().getId());
            preparedStatement.setString(6, venda.getMeioDePagamento());
            preparedStatement.setBigDecimal(7, venda.getDesconto());
            preparedStatement.setString(8, venda.getNumFatura());
            preparedStatement.setBigDecimal(9, venda.getValorTotal());

            preparedStatement.execute();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao inserir venda na base de dados! \n" + ex);
        }
    }

    //Atualizar uma venda
    @Override
    public void update(Venda venda) {
        String sql = "UPDATE " + db + ".`tb_vendas` SET `data` = ?,`valor_total` = ?,"
                + "`pago` = ?,`cliente_fk` = ?, `id_user` = ?, `meioDePag` = ?, "
                + "`desconto` = ?, `num_fatura` = ?, precoTotal=? WHERE `id_vendas` = ?;";
        try {
            preparedStatement = conector.prepareStatement(sql);

            preparedStatement.setDate(1, Date.valueOf(venda.getData()));
            preparedStatement.setBigDecimal(2, venda.getValor());
            preparedStatement.setBoolean(3, venda.isPago());
            preparedStatement.setInt(4, venda.getCliente().getIdCliente());
            preparedStatement.setInt(5, venda.getUsuario().getId());
            preparedStatement.setString(6, venda.getMeioDePagamento());
            preparedStatement.setBigDecimal(7, venda.getDesconto());
            preparedStatement.setString(8, venda.getNumFatura());
            preparedStatement.setBigDecimal(9, venda.getValorTotal());

            preparedStatement.setInt(10, venda.getIdVenda());

            preparedStatement.execute();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao atualizar venda na base de dados! \n" + ex);
        }
    }

    @Override
    public void delete(Integer idVenda) {
        try {
            String sql = "DELETE FROM " + db + ".tb_vendas WHERE id_vendas=?";
            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setInt(1, idVenda);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir venda na base de dados! \n" + ex);
        }
    }

    @Override
    public List<Venda> findAll() {
        String sql = "SELECT * FROM " + db + ".venda_view order by num_fatura desc;";
        List<Venda> retorno = new ArrayList<>();
        try {
            preparedStatement = conector.prepareStatement(sql);
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
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    @Override
    public ObservableList<Venda> listar(int quantidade, int pagina) {
        ObservableList retorno = FXCollections.observableArrayList();
        String sql = "SELECT * FROM " + db + ".venda_view order by num_fatura desc limit "+ quantidade * pagina +","+ quantidade +";";
        try {
            preparedStatement = conector.prepareStatement(sql);
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
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    @Override
    public int count() {
        try {
            String query = "SELECT COUNT(*) FROM " + db + ".tb_vendas";
            preparedStatement = conector.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao consultar total de vendas na base de dados", ex);
        }
        return 0;
    }
    
    //buscar o ultimo id de venda realizada no ano
    @Override
    public int ultimoRegisto(int ano) {
        int j = 0;
        try {
            String SQL = "select ifNull(count(id_vendas), 0) as max_id from " + db + ".tb_vendas where extract(year from data)=" + ano + ";";
            preparedStatement = conector.prepareStatement(SQL);
            rs = preparedStatement.executeQuery(SQL);
            if (rs.next()) {
                j = rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao consultar registro na base de dados!", ex);
        }
        return j;
    }

    //Buscar Ultima venda realizada
    @Override
    public Venda buscarUltimaVenda() {
        String sql = "SELECT max(id_vendas) as last_id FROM " + db + ".tb_vendas;";
        Venda retorno = new Venda();

        try {
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                retorno.setIdVenda(rs.getInt("last_id"));
                return retorno;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    @SuppressWarnings("UnusedAssignment")
    @Override
    public Venda buscar(Venda venda) {
        String sql = "SELECT * FROM " + db + ".venda_view where " + db + ".venda_view.id_vendas=?";
        Venda retorno = null;
        try {
            preparedStatement = conector.prepareStatement(sql);
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
            Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    @Override
    public int ultimoRegisto() {
        int j = 0;
        String SQL = "select max(id_vendas) from " + db + ".tb_vendas";
        try {
            preparedStatement = conector.prepareStatement(SQL);
            rs = preparedStatement.executeQuery(SQL);
            if (rs.next()) {
                j = rs.getInt(1);
            }
        } catch (SQLException e) {
            Mensagem.erro("Erro ao consultar registro na base de dados!", "SQLException");
        }
        return j;
    }
    
    @Override
    public Map<Integer, ArrayList> listarQuantidadeVendaPorMes(){
        String sql = "select count(id_vendas) as count, extract(year from data) as ano, extract(month from data) as mes from " + db + ".tb_vendas group by ano, mes order by ano, mes;";
        Map<Integer, ArrayList> retorno = new HashMap();
        try{
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                ArrayList linha = new ArrayList();
                if(!retorno.containsKey(rs.getInt("ano"))){
                    linha.add(rs.getInt("mes"));
                    linha.add(rs.getInt("count"));
                    retorno.put(rs.getInt("ano"), linha);
                }else {
                    ArrayList linhaNova = retorno.get(rs.getInt("ano"));
                    linhaNova.add(rs.getInt("mes"));
                    linhaNova.add(rs.getInt("count"));
                }
            }
            return retorno;
        }catch (SQLException ex){
            Nota.erro("Erro: " + ex);
        }
        return retorno;
    }
    
    @Override
    public Map<Integer, ArrayList> listarQuantidadeVendaPorDia(String mes, String ano) {
        String sql = "select count(id_vendas) as count, extract(month from data) as mes, extract(day from data) as dia from " + db + ".tb_vendas where extract(month from data) = " + mes + " and extract(year from data) = " + ano + " group by dia order by dia;";
        Map<Integer, ArrayList> retorno = new HashMap();
        try {
            preparedStatement = conector.prepareStatement(sql);
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
            return retorno;
        } catch (SQLException ex) {
            Nota.erro("Erro: " + ex);
        }
        return retorno;
    }
    
    @Override
    public Map<Integer, ArrayList> listarValorTotalVendaPorMes(){
        String sql = "select sum(precoTotal) as sum, extract(year from data) as ano, extract(month from data) as mes from " + db + ".tb_vendas group by ano, mes order by ano, mes;";
        Map<Integer, ArrayList> retorno = new HashMap();
        try{
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                ArrayList linha = new ArrayList();
                if(!retorno.containsKey(rs.getInt("ano"))){
                    linha.add(rs.getInt("mes"));
                    linha.add(rs.getBigDecimal("sum"));
                    retorno.put(rs.getInt("ano"), linha);
                }else {
                    ArrayList linhaNova = retorno.get(rs.getInt("ano"));
                    linhaNova.add(rs.getInt("mes"));
                    linhaNova.add(rs.getBigDecimal("sum"));
                }
            }
            return retorno;
        }catch (SQLException ex){
            Nota.erro("Erro: " + ex);
        }
        return retorno;
    }
    @Override
    public Map<Integer, ArrayList> listarValorTotalVendaPorMes(String ano){
        String sql = "select sum(precoTotal) as sum, extract(year from data) as ano, extract(month from data) as mes from " + db + ".tb_vendas where extract(year from data) = "+ ano +" group by ano, mes order by ano, mes ;";
        Map<Integer, ArrayList> retorno = new HashMap();
        try{
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                ArrayList linha = new ArrayList();
                if(!retorno.containsKey(rs.getInt("ano"))){
                    linha.add(rs.getInt("mes"));
                    linha.add(rs.getBigDecimal("sum"));
                    retorno.put(rs.getInt("ano"), linha);
                }else {
                    ArrayList linhaNova = retorno.get(rs.getInt("ano"));
                    linhaNova.add(rs.getInt("mes"));
                    linhaNova.add(rs.getBigDecimal("sum"));
                }
            }
            return retorno;
        }catch (SQLException ex){
            Nota.erro("Erro: " + ex);
        }
        return retorno;
    }
    
    // total de vendas por ano
    @Override
    public BigDecimal totalAnual(String ano){
        try{
            String sql = "select sum(precoTotal) as sum from " + db + ".tb_vendas where extract(year from data) = "+ ano +";";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            if(rs.next())
                return rs.getBigDecimal(1);
        } catch (SQLException ex){
            throw new DataAccessException("Erro ao consultar na base de dados\n!"+ ex);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public void reportReciboFatura(int biFiltro) {
        try {
            HashMap filtro = new HashMap();
            filtro.put("id_venda", biFiltro);

            URL url = getClass().getResource("/cv/com/escola/reports/reciboFatura.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, conector);//null: caso não existam filtros

            Print jasperViewer = new Print();//(jasperPrint, false);//false: não deixa fechar a aplicação principal
            jasperViewer.viewReport("Recibo/Fatura", jasperPrint);       

        } catch (JRException ex) {
            Logger.getLogger(AlunoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao imprimir Recibo/Fatura! \n" + ex);
        } 
    }
}
