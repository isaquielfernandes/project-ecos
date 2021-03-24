package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Artigo;
import cv.com.escola.model.entity.Cliente;
import cv.com.escola.model.entity.Item;
import cv.com.escola.model.entity.Usuario;
import cv.com.escola.model.entity.Venda;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.ItemDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.util.Mensagem;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Isaquiel Fernandes
 */
public class ItemDAOImpl extends DAO implements ItemDAO{

    public ItemDAOImpl() {
        super();
        conector = ConnectionManager.getInstance().getConnection();
    }
    
    @Override
    public void create(Item item){
        try {
            String sql = "INSERT INTO "+ db +".tb_item_venda(quantidade, valor, id_artigo, id_venda)VALUES(?,?,?,?)";
            stm = conector.prepareStatement(sql);
            
            stm.setInt(1, item.getQuantidade());
            stm.setBigDecimal(2, item.getValorUnitario());
            stm.setLong(3, item.getArtigo().getIdArtigo());
            stm.setInt(4, item.getVenda().getIdVenda());
            
            stm.executeUpdate();
            stm.close();
            //Mensagem.info("Item de venda cadastrada com sucesso.", "Sucesso");
        } catch (SQLException ex) {
            Logger.getLogger(ItemDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao inserir item na base de dados! \n" + ex);
        }
    }
    
    @Override
    public void update(Item item){
        try {
            String sql = "UPDATE "+ db +".tb_item_venda SET quantidade=?, valor=?, id_artigo=? where id_item_venda=? and id_venda =?";
            stm = conector.prepareStatement(sql);
            
            stm.setInt(1, item.getQuantidade());
            stm.setBigDecimal(2, item.getValorUnitario());
            stm.setLong(3, item.getArtigo().getIdArtigo());
            stm.setInt(4, item.getVenda().getIdVenda());
            
            stm.setLong(5, item.getIdItem());
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(ItemDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao atualizar item na base de dados! \n" + ex);
        }
    }
    
    @Override
    public void delete(Integer idVenda) {
        try {
            String sql = "DELETE FROM "+ db +".tb_item_venda WHERE id_venda=?";
            stm = conector.prepareStatement(sql);
            
            stm.setInt(1, idVenda);
            stm.execute();

            stm.close();
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir item na base de dados! \n" + ex);
        }
    }
    
    @Override
    public List<Item> findAll() {
        String sql = "SELECT * FROM "+ db +".item_de_venda_view;";
        List<Item> retorno = new ArrayList<>();
        try {
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Item itemDeVenda = new Item();
                Artigo artigos = new Artigo(rs.getLong(2), rs.getString(3), rs.getBigDecimal(5), 
                        rs.getString(4));
                
                Cliente cliente = new Cliente(rs.getInt(15), rs.getString(16), rs.getString(17), 
                        rs.getString(18), rs.getString(19), rs.getString(20), rs.getString(21), 
                        rs.getString(22));
                
                Usuario user = new Usuario(rs.getInt(23), rs.getString(24));

                Venda vendas = new Venda(rs.getInt(8), rs.getDate(10).toLocalDate(),
                        rs.getBigDecimal(13), rs.getBoolean(11), rs.getString(12),
                        rs.getBigDecimal(14), rs.getString(9), cliente, user, rs.getBigDecimal(25));
                
                itemDeVenda.setIdItem(rs.getLong(1));
                itemDeVenda.setQuantidade(rs.getInt(6));
                itemDeVenda.setValorUnitario(rs.getBigDecimal(7));
                itemDeVenda.setArtigo(artigos);
                itemDeVenda.setVenda(vendas);
                
                retorno.add(itemDeVenda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
      
    @Override
    public List<Item> listarItensPorVenda(Venda venda) {
        String sql = "SELECT * FROM "+ db +".item_de_venda_view where item_de_venda_view.id_vendas = ?;";
        List<Item> retorno = new ArrayList<>();
        try {
            stm = conector.prepareStatement(sql);
            stm.setInt(1, venda.getIdVenda());
            rs = stm.executeQuery();
            while (rs.next()) {
                Item itemDeVenda = new Item();
                Artigo artigos = new Artigo(rs.getLong(2), rs.getString(3), rs.getBigDecimal(5), 
                        rs.getString(4));
                
                Cliente cliente = new Cliente(rs.getInt(15), rs.getString(16), rs.getString(17), 
                        rs.getString(18), rs.getString(19), rs.getString(20), rs.getString(21), 
                        rs.getString(22));
                
                Usuario user = new Usuario(rs.getInt(23), rs.getString(24));

                Venda vendas = new Venda(rs.getInt(8), rs.getDate(10).toLocalDate(),
                        rs.getBigDecimal(13), rs.getBoolean(11), rs.getString(12),
                        rs.getBigDecimal(14), rs.getString(9), cliente, user, rs.getBigDecimal(25));
                
                itemDeVenda.setIdItem(rs.getLong(1));
                itemDeVenda.setQuantidade(rs.getInt(6));
                itemDeVenda.setValorUnitario(rs.getBigDecimal(7));
                itemDeVenda.setArtigo(artigos);
                itemDeVenda.setVenda(vendas);
                
                retorno.add(itemDeVenda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
