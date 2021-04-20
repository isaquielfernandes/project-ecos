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
import org.apache.log4j.Level;

public class ArtigoDAOImpl extends DAO implements ArtigoDAO {

    public ArtigoDAOImpl() {
        super();
    }

    @Override
    public void create(Artigo artigo) {
        final StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(db).append(".tb_artigo (nomeArtigo, preco, descricao)");
        query.append(" VALUES (?,?,?)");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    query.toString()
            )) {
                pstmt.setString(1, artigo.getNomeArtigo());
                pstmt.setBigDecimal(2, artigo.getPreco());
                pstmt.setString(3, artigo.getDescricao());
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                throw new DataAccessException(Level.ERROR.toString(), ex);
            }
        });
    }

    @Override
    public void update(Artigo artigo) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ").append(db)
                .append(".tb_artigo SET nomeArtigo=?, preco=?, descricao=? ");
        query.append(" WHERE id_artigo =?;");
        
        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    query.toString()
            )) {
                pstmt.setString(1, artigo.getNomeArtigo());
                pstmt.setBigDecimal(2, artigo.getPreco());
                pstmt.setString(3, artigo.getDescricao());
                pstmt.setLong(4, artigo.getIdArtigo());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        });
    }

    @Override
    public void delete(Integer idArtigo) {
        final StringBuilder query = new StringBuilder();
            query.append("DELETE FROM ").append(db).append(".tb_artigo WHERE id_artigo=?");
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setInt(1, idArtigo);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
    }

    @Override
    public List<Artigo> findAll() {
        List<Artigo> artigosList = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT * from ").append(db).append(".tb_artigo;");
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                artigosList.add(mapRowToObject(rs));
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
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
            preparedStatement.setInt(1, (int) artigo.getIdArtigo());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                retorno.setIdArtigo(rs.getInt("id_artigo"));
                retorno.setNomeArtigo(rs.getString("nomeArtigo"));
                retorno.setPreco(rs.getBigDecimal("preco"));
            }
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return retorno;
    }

    public Artigo mapRowToObject(ResultSet resultSet) throws SQLException {
        Artigo artigo = new Artigo();
        artigo.setIdArtigo(resultSet.getLong(1));
        artigo.setNomeArtigo(resultSet.getString(2));
        artigo.setPreco(resultSet.getBigDecimal(3));
        artigo.setDescricao(resultSet.getString(4));
        return artigo;
    }
}
