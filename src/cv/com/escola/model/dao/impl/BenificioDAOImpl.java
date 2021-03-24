package cv.com.escola.model.dao.impl;

import cv.com.escola.model.dao.BenificioDAO;
import cv.com.escola.model.entity.Benificio;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.exception.DaoException;
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
            stm = conector.prepareStatement(sql);           
            stm.setString(1, benificio.getNomeBenificio());
            stm.setString(2, benificio.getDescricao());
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(BenificioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException("Erro ao savar benificio na base de dados!");
        }
    }
    
    @Override
    public void update(Benificio benificio){
        try {
            String sql = "UPDATE "+ db +".tb_benificio SET nome_benificio=?, descricao=? WHERE id_benificio =?";
            stm = conector.prepareStatement(sql);
            stm.setString(1, benificio.getNomeBenificio());
            stm.setString(2, benificio.getDescricao());
            
            stm.setInt(3, benificio.getIdBenificio());
            
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(BenificioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException("Erro ao editar benificio na base de dados!");
        }
    }
    
    @Override
    public void delete(Integer idBenificio) {
        try {
            String sql = "DELETE FROM "+ db +".tb_benificio WHERE id_benificio=?";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, idBenificio);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            throw new DaoException("Erro ao excluir benificio na base de dados!");
        }
    }
    
    @Override
    public List<Benificio> findAll() {
        List<Benificio> dadosBenificio = new ArrayList<>();
        try {
            String sql = "SELECT * FROM "+ db +".tb_benificio ORDER BY nome_benificio";
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                Benificio benificio = new Benificio(rs.getInt(1), rs.getString(2), rs.getString(3));
                dadosBenificio.add(benificio);
            }
            stm.close();
            rs.close();

        } catch (SQLException ex) {
            throw new DaoException("Erro ao consultar benificio na base de dados!");
        }
        return dadosBenificio;
    }
    
    @Override
    public List<Benificio> combo() {
        List<Benificio> dadosBenificio = new ArrayList<>();
        try {
            String sql = "SELECT id_benificio, nome_benificio FROM "+ db +".tb_benificio ORDER BY nome_benificio";
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                Benificio benificio = new Benificio(rs.getInt(1), rs.getString(2));
                dadosBenificio.add(benificio);
            }
            stm.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DaoException("Erro ao consultar benificio na base de dados!");
        }
        return dadosBenificio;
    }
    
    @Override
    public boolean isBanificio(String nome, int id) {
        try {
            String sql = "SELECT nome_benificio FROM "+ db +".tb_benificio WHERE nome_benificio =? AND id_benificio !=? ";
            stm = conector.prepareStatement(sql);
            stm.setString(1, nome);
            stm.setInt(2, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString(1).toLowerCase().trim().equals(nome.toLowerCase().trim().toLowerCase());
            }
            stm.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DaoException("Erro ao validar benificio na base de dados!");
        }
        return false;
    }
}
