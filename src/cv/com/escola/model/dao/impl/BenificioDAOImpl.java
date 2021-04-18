package cv.com.escola.model.dao.impl;

import cv.com.escola.model.dao.BenificioDAO;
import cv.com.escola.model.entity.Benificio;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.exception.DataAccessException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BenificioDAOImpl extends DAO implements BenificioDAO {
    
    public BenificioDAOImpl(){
        super();
    }
    
    @Override
    public void create(Benificio benificio){
        try {
            String sql = "insert into "+ db +".tb_benificio (nome_benificio, descricao) VALUES (?, ?)";
            preparedStatement = conector.prepareStatement(sql);           
            preparedStatement.setString(1, benificio.getNomeBenificio());
            preparedStatement.setString(2, benificio.getDescricao());
            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(BenificioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException("Erro ao savar benificio na base de dados!");
        }
    }
    
    @Override
    public void update(Benificio benificio){
        try {
            String sql = "UPDATE "+ db +".tb_benificio SET nome_benificio=?, descricao=? WHERE id_benificio =?";
            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setString(1, benificio.getNomeBenificio());
            preparedStatement.setString(2, benificio.getDescricao());
            
            preparedStatement.setInt(3, benificio.getIdBenificio());
            
            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(BenificioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException("Erro ao editar benificio na base de dados!");
        }
    }
    
    @Override
    public void delete(Integer idBenificio) {
        try {
            String sql = "DELETE FROM "+ db +".tb_benificio WHERE id_benificio=?";

            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setInt(1, idBenificio);

            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao excluir benificio na base de dados!");
        }
    }
    
    @Override
    public List<Benificio> findAll() {
        List<Benificio> dadosBenificio = new ArrayList<>();
        try {
            String sql = "SELECT * FROM "+ db +".tb_benificio ORDER BY nome_benificio";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                Benificio benificio = new Benificio(rs.getInt(1), rs.getString(2), rs.getString(3));
                dadosBenificio.add(benificio);
            }
            preparedStatement.close();
            rs.close();

        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao consultar benificio na base de dados!");
        }
        return dadosBenificio;
    }
    
    @Override
    public List<Benificio> combo() {
        List<Benificio> dadosBenificio = new ArrayList<>();
        try {
            String sql = "SELECT id_benificio, nome_benificio FROM "+ db +".tb_benificio ORDER BY nome_benificio";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                Benificio benificio = new Benificio(rs.getInt(1), rs.getString(2));
                dadosBenificio.add(benificio);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao consultar benificio na base de dados!");
        }
        return dadosBenificio;
    }
    
    @Override
    public boolean isBanificio(String nome, int id) {
        try {
            String sql = "SELECT nome_benificio FROM "+ db +".tb_benificio WHERE nome_benificio =? AND id_benificio !=? ";
            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            preparedStatement.setInt(2, id);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString(1).toLowerCase().trim().equals(nome.toLowerCase().trim().toLowerCase());
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao validar benificio na base de dados!");
        }
        return false;
    }
}
