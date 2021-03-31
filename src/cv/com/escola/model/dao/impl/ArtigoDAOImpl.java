package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Artigo;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.util.Mensagem;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import cv.com.escola.model.dao.ArtigoDAO;
import cv.com.escola.model.dao.exception.DaoException;

public class ArtigoDAOImpl extends DAO implements ArtigoDAO {

    public ArtigoDAOImpl() {
        super();
        conector = ConnectionManager.getInstance().getConnection();
    }

    @Override
    public void create(Artigo artigo) {
        try {
            String sql = "INSERT INTO "+ db +".tb_artigo (nomeArtigo, preco, descricao) VALUES (?,?,?)";

            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setString(1, artigo.getNomeArtigo());
            preparedStatement.setBigDecimal(2, artigo.getPreco());
            preparedStatement.setString(3, artigo.getDescricao());

            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
            Mensagem.info("Artigo cadastrada com sucesso!"); 
        } catch (SQLException ex) {
            throw new DaoException("Erro ao cadastrar artigo na base de dados!");
        }
    }

    @Override
    public void update(Artigo artigo) {
        try {
            String sql = "UPDATE "+ db +".tb_artigo SET nomeArtigo=?, preco=?, descricao=? WHERE id_artigo =?";

            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setString(1, artigo.getNomeArtigo());
            preparedStatement.setBigDecimal(2, artigo.getPreco());
            preparedStatement.setString(3, artigo.getDescricao());
            
            preparedStatement.setInt(4, (int) artigo.getIdArtigo());

            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
            Mensagem.info("Artigo atualizada com sucesso!");
        } catch (SQLException ex) {
            throw new DaoException("Erro ao atualizar artigo na base de dados! \n");
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
            Mensagem.erro("Erro ao excluir artigo na base de dados! \n" + ex);
        }
    }

    @Override
    public List<Artigo> findAll() {
        List<Artigo> artigosList = new ArrayList<>();
        try {
            String sql = "SELECT * from "+ db +".tb_artigo";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                Artigo artigos = new Artigo(rs.getLong(1), rs.getString(2), rs.getBigDecimal(3), rs.getString(4));
                artigosList.add(artigos);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DaoException("Erro ao consultar artigos na base de dados!");
        }
        return artigosList;
    }
    
    @Override
    public Artigo buscar(Artigo artigo) {
        String sql = "SELECT * FROM "+ db +".tb_artigo WHERE id_artigo=?";
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
        }
        return retorno;
    }
}
