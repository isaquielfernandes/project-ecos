package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.CargoSalario;
import cv.com.escola.model.dao.CargoSalarioDAO;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CargoSalarioDAOImpl extends DAO implements CargoSalarioDAO {
    
    public CargoSalarioDAOImpl(){
        super();
    }
    
    @Override
    public void create(CargoSalario cargo_salario){
        try(Connection conn = HikariCPDataSource.getConnection();) {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO ").append(db)
                    .append(".tb_cargo_salario (cargo, salario, descricao) VALUES (?, ?, ?)");
            
            preparedStatement = conn.prepareStatement(query.toString());
            
            preparedStatement.setString(1, cargo_salario.getNomeCargo());
            preparedStatement.setDouble(2, cargo_salario.getSalario());
            preparedStatement.setString(3, cargo_salario.getDescricao());
            
            preparedStatement.executeUpdate();
            conn.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }
    
    @Override
    public void update(CargoSalario cargo_salario){
        try(Connection conn = HikariCPDataSource.getConnection();) {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE ").append(db)
                    .append(".tb_cargo_salario SET cargo=?, salario=?, descricao=? WHERE id_cargo_salario =?");
            
            preparedStatement = conn.prepareStatement(query.toString());
            preparedStatement.setString(1, cargo_salario.getNomeCargo());
            preparedStatement.setDouble(2, cargo_salario.getSalario());
            preparedStatement.setString(3, cargo_salario.getDescricao());
            
            preparedStatement.setInt(4, cargo_salario.getIdcargoSalario());
            
            preparedStatement.executeUpdate();
            conn.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void delete(Integer idCargo_salario) {
        try(Connection conn = HikariCPDataSource.getConnection();) {
            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM ").append(db).append(".tb_cargo_salario WHERE id_cargo_salario=?");

            preparedStatement = conn.prepareStatement(query.toString());
            preparedStatement.setInt(1, idCargo_salario);

            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }
   
    @Override
    public List<CargoSalario> findAll() {
        List<CargoSalario> dadosDesignacao = new ArrayList<>();
        try(Connection conn = HikariCPDataSource.getConnection();) {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ").append(db).append(".tb_cargo_salario ORDER BY cargo");
            preparedStatement = conn.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CargoSalario cargoSalario = new CargoSalario(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4));
                dadosDesignacao.add(cargoSalario);
            }

            preparedStatement.close();
            rs.close();

        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return dadosDesignacao;
    }

    @Override
    public List<CargoSalario> combo() {
        List<CargoSalario> dadosCargoSalario = new ArrayList<>();
        try(Connection conn = HikariCPDataSource.getConnection();) {
            StringBuilder query = new StringBuilder();
            query.append("SELECT id_cargo_salario, cargo FROM ").append(db).append(".tb_cargo_salario ORDER BY cargo");

            preparedStatement = conn.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CargoSalario cargo = new CargoSalario(rs.getInt(1), rs.getString(2));
                dadosCargoSalario.add(cargo);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return dadosCargoSalario;
    }

    @Override
    public boolean isCargo_salario(String nome, int id) {
        try(Connection conn = HikariCPDataSource.getConnection();) {
            StringBuilder query = new StringBuilder();
            query.append("SELECT cargo FROM ").append(db).append(".tb_cargo_salario WHERE cargo =? AND id_cargo_salario !=? ");
            preparedStatement = conn.prepareStatement(query.toString());
            preparedStatement.setString(1, nome);
            preparedStatement.setInt(2, id);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getString(1).toLowerCase().trim().equals(nome.toLowerCase().trim().toLowerCase());
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }

        return false;
    }

    @Override
    public int count() {
        try (Connection conn = HikariCPDataSource.getConnection();) {
            StringBuilder query = new StringBuilder();
            query.append("SELECT COUNT(*) FROM ").append(db).append(".tb_cargo_salario");
            preparedStatement = conn.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return 0;
    }
}
