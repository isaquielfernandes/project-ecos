package cv.com.escola.model.dao.impl;

import cv.com.escola.model.dao.BenificioDAO;
import cv.com.escola.model.entity.Benificio;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DataAccessException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;

public class BenificioDAOImpl extends DAO implements BenificioDAO {
    
    public BenificioDAOImpl(){
        super();
    }
    
    @Override
    public void create(Benificio benificio){
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("insert into ").append(db).append(".tb_benificio (nome_benificio, descricao) VALUES (?, ?)");
            preparedStatement = conector.prepareStatement(query.toString());           
            preparedStatement.setString(1, benificio.getNomeBenificio());
            preparedStatement.setString(2, benificio.getDescricao());
            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
    }
    
    @Override
    public void update(Benificio benificio){
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("UPDATE ").append(db).append(".tb_benificio SET nome_benificio=?, descricao=? WHERE id_benificio =?");
            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setString(1, benificio.getNomeBenificio());
            preparedStatement.setString(2, benificio.getDescricao());
            
            preparedStatement.setInt(3, benificio.getIdBenificio());
            
            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
    }
    
    @Override
    public void delete(Integer idBenificio) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("DELETE FROM ").append(db).append(".tb_benificio WHERE id_benificio=?");
            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setInt(1, idBenificio);

            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
    }
    
    @Override
    public List<Benificio> findAll() {
        List<Benificio> dadosBenificio = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ").append(db).append(".tb_benificio ORDER BY nome_benificio");
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Benificio benificio = new Benificio(rs.getInt(1), rs.getString(2), rs.getString(3));
                dadosBenificio.add(benificio);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return dadosBenificio;
    }
    
    @Override
    public List<Benificio> combo() {
        List<Benificio> dadosBenificio = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT id_benificio, nome_benificio FROM ").append(db).append(".tb_benificio ORDER BY nome_benificio");
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Benificio benificio = new Benificio(rs.getInt(1), rs.getString(2));
                dadosBenificio.add(benificio);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return dadosBenificio;
    }
    
    @Override
    public boolean isBanificio(String nome, int id) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT nome_benificio FROM ").append(db).append(".tb_benificio WHERE nome_benificio =? AND id_benificio !=? ");
            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setString(1, nome);
            preparedStatement.setInt(2, id);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString(1).equalsIgnoreCase(nome);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.ERROR.toString(), ex);
        }
        return false;
    }
}
