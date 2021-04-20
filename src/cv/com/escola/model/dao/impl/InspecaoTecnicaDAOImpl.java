package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.InspecaoTecnica;
import cv.com.escola.model.entity.Veiculo;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.InspecaoTecnicaDAO;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InspecaoTecnicaDAOImpl extends DAO implements InspecaoTecnicaDAO {

    public InspecaoTecnicaDAOImpl() {
        super();
    }

    @Override
    public void create(InspecaoTecnica inspecao) {
        try(Connection conn = HikariCPDataSource.getConnection();) {
            final StringBuilder query = new StringBuilder();
            query.append("INSERT INTO ").append(db).append(".`tb_inspecao_tecnica` ");
            query.append("(`veiculo`, `tipoInspecao`, `dataInspecao`, `duracao`, `resultado`, `validade`) VALUES (?,?,?,?,?,?)");

            preparedStatement = conn.prepareStatement(query.toString());

            mapToSave(inspecao);

            preparedStatement.executeUpdate();
            conn.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    private void mapToSave(InspecaoTecnica inspecao) throws SQLException {
        preparedStatement.setLong(1, inspecao.getVeiculo().getCodigo());
        preparedStatement.setString(2, inspecao.getTipoDeInspeccao());
        preparedStatement.setDate(3, java.sql.Date.valueOf(inspecao.getDataDeInspeccao()));
        preparedStatement.setInt(4, inspecao.getMesDeDuracao());
        preparedStatement.setString(5, inspecao.getResultado());
        preparedStatement.setString(6, inspecao.getValidade());
    }

    @Override
    public void update(InspecaoTecnica inspecao) {
        try (Connection conn = HikariCPDataSource.getConnection();) {
            final StringBuilder query = new StringBuilder();
            query.append("UPDATE ").append(db).append(".`tb_inspecao_tecnica` SET `veiculo` = ?, `tipoInspecao` = ?, `dataInspecao` = ?, `duracao` = ?, `resultado` = ?, `validade` = ? WHERE `id` = ?");

            preparedStatement = conn.prepareStatement(query.toString());

            mapToSave(inspecao);

            preparedStatement.setLong(7, inspecao.getId());
            preparedStatement.executeUpdate();
            conn.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void delete(Long idInspecao) {
        try (Connection conn = HikariCPDataSource.getConnection();) {
            final StringBuilder query = new StringBuilder();
            query.append("DELETE FROM ").append(db).append(".`tb_inspecao_tecnica` WHERE id=?");
            preparedStatement = conn.prepareStatement(query.toString());
            preparedStatement.setLong(1, idInspecao);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public List<InspecaoTecnica> findAll() {
        List<InspecaoTecnica> dadosInspecao = new ArrayList<>();
        try (Connection conn = HikariCPDataSource.getConnection();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ").append(db).append(".inspecao_tecnica_view");

            preparedStatement = conn.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Veiculo veiculo = new Veiculo(
                        rs.getLong(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7));
                
                InspecaoTecnica inspecao = new InspecaoTecnica(
                        rs.getLong(1), rs.getString(8), rs.getString(11), rs.getDate(9).toLocalDate(), veiculo, rs.getInt(10), rs.getString(12) );
                dadosInspecao.add(inspecao);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return dadosInspecao;
    }
}
