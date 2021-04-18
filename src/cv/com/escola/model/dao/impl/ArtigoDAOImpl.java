package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Artigo;
import cv.com.escola.model.dao.DAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import cv.com.escola.model.dao.ArtigoDAO;
import cv.com.escola.model.dao.exception.DataAccessException;
import java.sql.ResultSet;

public class ArtigoDAOImpl extends DAO implements ArtigoDAO {

    public ArtigoDAOImpl() {
        super();
    }

    @Override
    public void create(Artigo artigo) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO ").append(db)
                    .append(".tb_artigo (nomeArtigo, preco, descricao)");
            query.append(" VALUES (?,?,?)");

            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setString(1, artigo.getNomeArtigo());
            preparedStatement.setBigDecimal(2, artigo.getPreco());
            preparedStatement.setString(3, artigo.getDescricao());

            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao cadastrar artigo na base de dados!");
        }
    }

    @Override
    public void update(Artigo artigo) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE ").append(db)
                 .append(".tb_artigo SET nomeArtigo=?, preco=?, descricao=? ");
            query.append(" WHERE id_artigo =?;");

            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setString(1, artigo.getNomeArtigo());
            preparedStatement.setBigDecimal(2, artigo.getPreco());
            preparedStatement.setString(3, artigo.getDescricao());
            
            preparedStatement.setInt(4, (int) artigo.getIdArtigo());

            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao atualizar artigo na base de dados! \n");
        }
    }

    @Override
    public void delete(Integer idArtigo) {
        try {
            String sql = "DELETE FROM "+ db +".tb_artigo WHERE id_artigo=?";

            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setInt(1, idArtigo);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao excluir artigo na base de dados! \n" + ex);
        }
    }

    @Override
    public List<Artigo> findAll() {
        List<Artigo> artigosList = new ArrayList<>();
        try {
            String sql = "SELECT * from "+ db +".tb_artigo;";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                artigosList.add(mapRowToObject(rs));
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao consultar artigos na base de dados!");
        }
        return artigosList;
    }
    
    @Override
    public Artigo buscar(Artigo artigo) {
        String sql = "SELECT id_artigo, nomeArtigo, preco FROM "+ db +".tb_artigo WHERE id_artigo=?";
        Artigo retorno = new Artigo();
        try {
            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setInt(1, (int)artigo.getIdArtigo());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                retorno.setIdArtigo(rs.getInt("id_artigo"));
                retorno.setNomeArtigo(rs.getString("nomeArtigo"));
                retorno.setPreco(rs.getBigDecimal("preco"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArtigoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException("Erro ao consultar artigos na base de dados!");
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
