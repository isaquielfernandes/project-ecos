package cv.com.escola.model.dao.impl;

import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.FluxoCaixaDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.util.Mensagem;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 *
 * @author Isaquiel Fernandes
 */
public class FluxoCaixaDAOImpl extends DAO implements FluxoCaixaDAO{
    
    public FluxoCaixaDAOImpl(){
        super();
        conector = ConnectionManager.getInstance().getConnection();
    }
    
    //Total das receitas por mes num dado ano
    @Override
    public BigDecimal totalPorMesDasReceitas(String mes, String ano){
        try{
            String sql = "SELECT ifnull(sum(Valor) , 0) AS 'Total a Receber' FROM "+ db +".lancamento_rec_view "
                + "where `lancamento_rec_view`.`Data Pagamento` is not null and "
                + "extract(month from `lancamento_rec_view`.`Data Lançamento`) = "+ mes +" "
                + "and extract(year from `lancamento_rec_view`.`Data Lançamento`) = "+ano+"";
            
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();
            if(rs.next())
                return rs.getBigDecimal(1);
        } catch (SQLException ex){
            Mensagem.erro("Erro ao fazer consultar na base de dados\n!"+ ex);
        }
        return BigDecimal.ZERO;
    }
    //Receitas total anual
    @Override
    public BigDecimal totalPorAnoDasReceitas(String ano){
        try{
            String sql = "SELECT ifnull(sum(Valor) , 0) AS 'Total a Receber' FROM "
            + ""+ db +".lancamento_rec_view where `lancamento_rec_view`.`Data Pagamento` "
            + "is not null and extract(year from `lancamento_rec_view`.`Data Lançamento`) = " + ano + "";
            
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();
            if(rs.next())
                return rs.getBigDecimal(1);
        } catch (SQLException ex){
            Mensagem.erro("Erro ao fazer consultar na base de dados\n!"+ ex);
        }
        return BigDecimal.ZERO;
    }
    //Despesas total anual
    @Override
    public BigDecimal totalPorAnoDasDespesas(String ano){
        try{
            String sql = "SELECT ifnull(sum(Valor) , 0) AS 'Total Mensal' FROM "+ db +".lancamento_desp_view where "
            + "`Data Pagamento` is not null and "
            + " extract(year from `Data Lançamento`) = " + ano + "";
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();
            if(rs.next())
                return rs.getBigDecimal(1);
        } catch (SQLException ex){
            Mensagem.erro("Erro ao fazer consultar na base de dados\n!"+ ex);
        }
        return BigDecimal.ZERO;
    }
    //Despesas total por mes num dado ano
    @Override
    public BigDecimal totalPorMesDasDespesas(String mes, String ano){
        try{
            String sql = "SELECT ifnull(sum(Valor) , 0) AS 'Total Mensal' FROM "+ db +".lancamento_desp_view where "
            + "`Data Pagamento` is not null and  extract(month from `Data Lançamento`) = "+ mes +" and"
            + " extract(year from `Data Lançamento`) = "+ano+"";
            
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();
            if(rs.next())
                return rs.getBigDecimal(1);
        } catch (SQLException ex){
            Mensagem.erro("Erro ao fazer consultar na base de dados\n!"+ ex);
        }
        return BigDecimal.ZERO;
    }
}
