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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;

public class ItemDAOImpl extends DAO implements ItemDAO {

    private static final StringBuilder INSERT_QUERY = new StringBuilder();
    private static final StringBuilder UPDATE_QUERY = new StringBuilder();
    private static final StringBuilder DELETE_QUERY = new StringBuilder();

    public ItemDAOImpl() {
        super();
    }

    @Override
    public void create(Item item) {
        INSERT_QUERY.append("INSERT INTO ").append(db)
                .append(".tb_item_venda(quantidade, valor, id_artigo, id_venda) VALUES (?,?,?,?)");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    INSERT_QUERY.toString()
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
            } catch (SQLException ex) {
                throw new DataAccessException(Level.ERROR.toString(), ex);
            }
        });
    }

    @Override
    public void delete(Integer idVenda) {
        DELETE_QUERY.append("DELETE FROM ").append(db).append(".tb_item_venda WHERE id_venda=?");
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            preparedStatement = conector.prepareStatement(DELETE_QUERY.toString());
            preparedStatement.setInt(1, idVenda);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
    }

    @Override
    public List<Item> findAll() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(db).append(".item_de_venda_view;");
        List<Item> itens = new ArrayList<>();
        try (Connection conector = HikariCPDataSource.getConnection();) {
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    mapResultSet(itens, resultSet);
                }
            }
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return itens;
    }

    @Override
    public List<Item> listarItensPorVenda(Venda idVenda) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(db).append(".item_de_venda_view where item_de_venda_view.id_vendas = ?;");
        List<Item> itens = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setInt(1, idVenda.getIdVenda());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    mapResultSet(itens, resultSet);
                }
            }
            preparedStatement.closeOnCompletion();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return itens;
    }

    private void mapResultSet(List<Item> itens, ResultSet set) throws SQLException {
        Artigo artigo = new Artigo(set.getLong(2), set.getString(3), set.getBigDecimal(5),
                set.getString(4));

        Cliente cliente = new Cliente(set.getInt(15), set.getString(16), set.getString(17),
                set.getString(18), set.getString(19), set.getString(20), set.getString(21),
                set.getString(22));

        Usuario usuario = new Usuario(set.getInt(23), set.getString(24));

        Venda venda = new Venda(set.getInt(8), set.getDate(10).toLocalDate(),
                set.getBigDecimal(13), set.getBoolean(11), set.getString(12),
                set.getBigDecimal(14), set.getString(9), cliente, usuario, set.getBigDecimal(25));

        Item item = new Item(set.getLong(1), artigo, set.getInt(6), set.getBigDecimal(7), venda);
        itens.add(item);
    }

}
