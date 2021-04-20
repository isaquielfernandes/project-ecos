package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Proprietario;
import cv.com.escola.model.entity.Seguro;
import cv.com.escola.model.entity.Veiculo;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.SeguroAutoDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DataAccessException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class SeguroAutoDAOImpl extends DAO implements SeguroAutoDAO {

    public SeguroAutoDAOImpl() {
        super();
    }

    @Override
    public void create(Seguro seguro) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String inserir = "INSERT INTO "+ db +".`tb_seguro` "
                    + "(`compania`, `veiculo_seguro`, `desde`, `ate`, `emissao`) VALUES (?,?,?,?,?)";

            preparedStatement = conector.prepareStatement(inserir);

            preparedStatement.setString(1, seguro.getCompania());
            preparedStatement.setLong(2, seguro.getVeiculo().getCodigo());
            preparedStatement.setDate(3, java.sql.Date.valueOf(seguro.getDeste()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(seguro.getValidade()));
            preparedStatement.setDate(5, java.sql.Date.valueOf(seguro.getEmissao()));

            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.SEVERE.getName(), ex);
        }
    }

    @Override
    public void update(Seguro seguro) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String update = "UPDATE "+ db +".`tb_seguro` SET `compania` = ?, `veiculo_seguro` = ?, `desde` = ?, `ate` = ?, `emissao` = ? WHERE `id` = ?";

            preparedStatement = conector.prepareStatement(update);

            preparedStatement.setString(1, seguro.getCompania());
            preparedStatement.setLong(2, seguro.getVeiculo().getCodigo());
            preparedStatement.setDate(3, java.sql.Date.valueOf(seguro.getDeste()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(seguro.getValidade()));
            preparedStatement.setDate(5, java.sql.Date.valueOf(seguro.getEmissao()));

            preparedStatement.setLong(6, seguro.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.SEVERE.getName(), ex);
        }
    }

    @Override
    public void delete(Long idSeuro) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sql = "DELETE FROM "+ db +".`tb_seguro` WHERE `id` = ?";
            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setLong(1, idSeuro);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.SEVERE.getName(), ex);
        }
    }

    @Override
    public List<Seguro> findAll() {
        List<Seguro> dadosSeguro = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sql = "SELECT * FROM "+ db +".seguro_auto_view";

            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);

            while (rs.next()) {
                Veiculo veiculo = new Veiculo(
                        rs.getLong(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), new Proprietario(rs.getString(8)), rs.getString(9));
                
                Seguro seguro = new Seguro(
                        rs.getLong(1), rs.getString(2), veiculo, rs.getDate(10).toLocalDate(), rs.getDate(11).toLocalDate(), rs.getDate(12).toLocalDate());
                dadosSeguro.add(seguro);
            }

            preparedStatement.close();
            rs.close();

        } catch (SQLException ex) {
            throw new DataAccessException(Level.SEVERE.getName(), ex);
        }
        return dadosSeguro;
    }
}
