package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Proprietario;
import cv.com.escola.model.entity.Seguro;
import cv.com.escola.model.entity.Veiculo;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.SeguroAutoDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeguroAutoDAOImpl extends DAO implements SeguroAutoDAO {

    public SeguroAutoDAOImpl() {
        super();
    }

    @Override
    public void create(Seguro seguro) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(db).append(".tb_seguro ");
        query.append("(compania, veiculo_seguro, desde, ate, emissao) VALUES (?,?,?,?,?)");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    query.toString()
            )) {
                mapToSave(pstmt, seguro);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    @Override
    public void update(Seguro seguro) {
        StringBuilder updateQuery = new StringBuilder();
        updateQuery.append("UPDATE ").append(db).append(".`tb_seguro` SET `compania` = ?, `veiculo_seguro` = ?, `desde` = ?, `ate` = ?, `emissao` = ? WHERE `id` = ?");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    updateQuery.toString()
            )) {
                mapToSave(pstmt, seguro);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    private void mapToSave(final PreparedStatement pstmt, Seguro seguro) throws SQLException {
        pstmt.setString(1, seguro.getCompania());
        pstmt.setLong(2, seguro.getVeiculo().getCodigo());
        pstmt.setDate(3, java.sql.Date.valueOf(seguro.getDeste()));
        pstmt.setDate(4, java.sql.Date.valueOf(seguro.getValidade()));
        pstmt.setDate(5, java.sql.Date.valueOf(seguro.getEmissao()));

        if (seguro.getId() != 0) {
            pstmt.setLong(6, seguro.getId());
        }
        pstmt.executeUpdate();
    }

    @Override
    public void delete(Long idSeuro) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(db).append(".tb_seguro WHERE id = ?");
        remove(query.toString(), idSeuro);
    }

    @Override
    public List<Seguro> findAll() {
        List<Seguro> dadosSeguro = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ").append(db).append(".seguro_auto_view");
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();

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
            throw new DataAccessException(ex);
        }
        return dadosSeguro;
    }
}
