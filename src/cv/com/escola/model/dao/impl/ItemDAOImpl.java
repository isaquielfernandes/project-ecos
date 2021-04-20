package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Artigo;
import cv.com.escola.model.entity.Cliente;
import cv.com.escola.model.entity.Item;
import cv.com.escola.model.entity.Usuario;
import cv.com.escola.model.entity.Venda;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.ItemDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl extends DAO implements ItemDAO {

    public ItemDAOImpl() {
        super();
    }

    @Override
    public void create(Item item) {
        final StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(db)
                .append(".tb_item_venda(quantidade, valor, id_artigo, id_venda)VALUES(?,?,?,?)");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    query.toString()
            )) {
                pstmt.setInt(1, item.getQuantidade());
                pstmt.setBigDecimal(2, item.getValorUnitario());
                pstmt.setLong(3, item.getArtigo().getIdArtigo());
                pstmt.setInt(4, item.getVenda().getIdVenda());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        });
    }

    @Override
    public void update(Item item) {
        final StringBuilder UPDATE_QUERY = new StringBuilder();
        UPDATE_QUERY.append("UPDATE ").append(db)
                .append(".tb_item_venda SET quantidade=?, valor=?, id_artigo=? where id_item_venda=? and id_venda =?");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    UPDATE_QUERY.toString()
            )) {
                pstmt.setInt(1, item.getQuantidade());
                pstmt.setBigDecimal(2, item.getValorUnitario());
                pstmt.setLong(3, item.getArtigo().getIdArtigo());
                pstmt.setInt(4, item.getVenda().getIdVenda());
                pstmt.setLong(5, item.getIdItem());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        });
    }

    @Override
    public void delete(Integer idVenda) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM ").append(db).append(".tb_item_venda WHERE id_venda=?");
            preparedStatement = conector.prepareStatement(query.toString());

            preparedStatement.setInt(1, idVenda);
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao excluir item na base de dados! \n" + ex);
        }
    }

    @Override
    public List<Item> findAll() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(db).append(".item_de_venda_view;");
        List<Item> retorno = new ArrayList<>();
        try (Connection conector = HikariCPDataSource.getConnection();) {
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Item itemDeVenda = new Item();
                Artigo artigos = new Artigo(rs.getLong(2), rs.getString(3), rs.getBigDecimal(5),
                        rs.getString(4));

                Cliente cliente = new Cliente(rs.getInt(15), rs.getString(16), rs.getString(17),
                        rs.getString(18), rs.getString(19), rs.getString(20), rs.getString(21),
                        rs.getString(22));

                Usuario usuario = new Usuario(rs.getInt(23), rs.getString(24));

                Venda vendas = new Venda(rs.getInt(8), rs.getDate(10).toLocalDate(),
                        rs.getBigDecimal(13), rs.getBoolean(11), rs.getString(12),
                        rs.getBigDecimal(14), rs.getString(9), cliente, usuario, rs.getBigDecimal(25));

                itemDeVenda.setIdItem(rs.getLong(1));
                itemDeVenda.setQuantidade(rs.getInt(6));
                itemDeVenda.setValorUnitario(rs.getBigDecimal(7));
                itemDeVenda.setArtigo(artigos);
                itemDeVenda.setVenda(vendas);

                retorno.add(itemDeVenda);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("FIND: ", ex);
        }
        return retorno;
    }

    @Override
    public List<Item> listarItensPorVenda(Venda venda) {
        StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ").append(db).append(".item_de_venda_view where item_de_venda_view.id_vendas = ?;");
        List<Item> retorno = new ArrayList<>();
        try (Connection conector = HikariCPDataSource.getConnection();) {
            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setInt(1, venda.getIdVenda());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Item itemDeVenda = new Item();
                Artigo artigos = new Artigo(rs.getLong(2), rs.getString(3), rs.getBigDecimal(5),
                        rs.getString(4));

                Cliente cliente = new Cliente(rs.getInt(15), rs.getString(16), rs.getString(17),
                        rs.getString(18), rs.getString(19), rs.getString(20), rs.getString(21),
                        rs.getString(22));

                Usuario usuario = new Usuario(rs.getInt(23), rs.getString(24));

                Venda vendas = new Venda(rs.getInt(8), rs.getDate(10).toLocalDate(),
                        rs.getBigDecimal(13), rs.getBoolean(11), rs.getString(12),
                        rs.getBigDecimal(14), rs.getString(9), cliente, usuario, rs.getBigDecimal(25));

                itemDeVenda.setIdItem(rs.getLong(1));
                itemDeVenda.setQuantidade(rs.getInt(6));
                itemDeVenda.setValorUnitario(rs.getBigDecimal(7));
                itemDeVenda.setArtigo(artigos);
                itemDeVenda.setVenda(vendas);

                retorno.add(itemDeVenda);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("FIND: ", ex);
        }
        return retorno;
    }
}
