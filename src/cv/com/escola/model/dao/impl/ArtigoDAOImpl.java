package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Artigo;
import cv.com.escola.model.dao.DAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import cv.com.escola.model.dao.ArtigoDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ArtigoDAOImpl extends DAO implements ArtigoDAO {
      
    public ArtigoDAOImpl() {
        super();
    }

    @Override
    public void create(Artigo artigo) {
        final StringBuilder insert = new StringBuilder();
        insert.append("INSERT INTO ").append(db).append(".tb_artigo (nomeArtigo, preco, descricao)");
        insert.append(" VALUES (?,?,?)");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    insert.toString()
            )) {
                mapToSave(pstmt, artigo);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    private void mapToSave(final PreparedStatement pstmt, Artigo artigo) throws SQLException {
        pstmt.setString(1, artigo.getNome());
        pstmt.setBigDecimal(2, artigo.getPreco());
        pstmt.setString(3, artigo.getDescricao());
        if (artigo.getId() != 0) {
            pstmt.setLong(4, artigo.getId());
        }
        pstmt.executeUpdate();
    }

    @Override
    public void update(Artigo artigo) {
        StringBuilder update = new StringBuilder();
        update.append("UPDATE ").append(db).append(".tb_artigo SET nomeArtigo=?, preco=?, descricao=? ");
        update.append(" WHERE id_artigo = ?;");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    update.toString()
            )) {
                mapToSave(pstmt, artigo);
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        });
    }

    @Override
    public void delete(Integer idArtigo) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(db).append(".tb_artigo WHERE id_artigo=?");
        remove(query.toString(), idArtigo);
    }

    @Override
    public List<Artigo> findAll() {
        List<Artigo> artigosList = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT * from ").append(db).append(".tb_artigo;");
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    artigosList.add(mapRowToObject(resultSet));
                }
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return artigosList;
    }

    @Override
    public Artigo buscar(Artigo artigo) {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT id_artigo, nomeArtigo, preco FROM ").append(db).append(".tb_artigo WHERE id_artigo=?");
        Artigo retorno = new Artigo();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setInt(1, (int) artigo.getId());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                retorno.setId(rs.getInt("id_artigo"));
                retorno.setNome(rs.getString("nomeArtigo"));
                retorno.setPreco(rs.getBigDecimal("preco"));
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return retorno;
    }

    public Artigo mapRowToObject(ResultSet resultSet) throws SQLException {
        Artigo artigo = new Artigo();
        artigo.setId(resultSet.getLong(1));
        artigo.setNome(resultSet.getString(2));
        artigo.setPreco(resultSet.getBigDecimal(3));
        artigo.setDescricao(resultSet.getString(4));
        return artigo;
    }
}
