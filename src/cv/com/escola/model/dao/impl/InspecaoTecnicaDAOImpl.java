package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.InspecaoTecnica;
import cv.com.escola.model.entity.Veiculo;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.InspecaoTecnicaDAO;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InspecaoTecnicaDAOImpl extends DAO implements InspecaoTecnicaDAO {

    public InspecaoTecnicaDAOImpl() {
        super();
    }

    @Override
    public void create(InspecaoTecnica inspecao) {
        StringBuilder insertQuery = new StringBuilder();
        insertQuery.append("INSERT INTO ").append(db).append(".tb_inspecao_tecnica ");
        insertQuery.append("(veiculo, tipoInspecao, dataInspecao, duracao, resultado, validade) VALUES (?,?,?,?,?,?)");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    insertQuery.toString()
            )) {
                mapToSave(pstmt, inspecao);
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    private void mapToSave(final PreparedStatement pstmt, InspecaoTecnica inspecao) throws SQLException {
        pstmt.setLong(1, inspecao.getVeiculo().getCodigo());
        pstmt.setString(2, inspecao.getTipoDeInspeccao());
        pstmt.setDate(3, java.sql.Date.valueOf(inspecao.getDataDeInspeccao()));
        pstmt.setInt(4, inspecao.getMesDeDuracao());
        pstmt.setString(5, inspecao.getResultado());
        pstmt.setString(6, inspecao.getValidade());
        if (inspecao.getId() != 0 )
            pstmt.setLong(7, inspecao.getId());
    }

    @Override
    public void update(InspecaoTecnica inspecao) {
        StringBuilder updateQury = new StringBuilder();
        updateQury.append("UPDATE ").append(db).append(".`tb_inspecao_tecnica` SET `veiculo` = ?, `tipoInspecao` = ?, `dataInspecao` = ?, `duracao` = ?, `resultado` = ?, `validade` = ? WHERE `id` = ?");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    updateQury.toString()
            )) {
                mapToSave(pstmt, inspecao);
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    @Override
    public void delete(Long idInspecao) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(db).append(".`tb_inspecao_tecnica` WHERE id=?");
        remove(query.toString(), idInspecao);
    }

    @Override
    public List<InspecaoTecnica> findAll() {
        List<InspecaoTecnica> dadosInspecao = new ArrayList<>();
        try (Connection conn = HikariCPDataSource.getInstance().getConnection()) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ").append(db).append(".inspecao_tecnica_view");

            preparedStatement = conn.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Veiculo veiculo = new Veiculo(
                        rs.getLong(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7));

                InspecaoTecnica inspecao = new InspecaoTecnica(
                        rs.getLong(1), rs.getString(8), rs.getString(11), rs.getDate(9).toLocalDate(), veiculo, rs.getInt(10), rs.getString(12));
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
