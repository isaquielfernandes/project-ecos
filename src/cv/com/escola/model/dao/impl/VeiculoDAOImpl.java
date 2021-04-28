package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Proprietario;
import cv.com.escola.model.entity.Veiculo;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.VeiculoDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.Tempo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAOImpl extends DAO implements VeiculoDAO {

    public VeiculoDAOImpl() {
        super();
    }

    @Override
    public void create(Veiculo veiculo) {
        StringBuilder query = new StringBuilder();
        query.append("insert into ").append(db).append(".tb_veiculo (placa, ilha, fabricante, modelo,");
        query.append("anoFabricacao, anoModelo, chassi, tipoCombustivel, nomeProprietario,");
        query.append("contatoProprietario, emailProprietario, dataCadastro, especificacao) ");
        query.append("VALUES (?, ?, ?,?, ?,?,?,?,?,?,?,?,?)");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    query.toString()
            )) {
                mapToSave(pstmt, veiculo);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    @Override
    public void update(Veiculo veiculo) {
        StringBuilder updateQuery = new StringBuilder();
        updateQuery.append("UPDATE ").append(db).append(".tb_veiculo set placa=?, ilha=?, fabricante=?, modelo=?,");
        updateQuery.append("anoFabricacao=?, anoModelo=?, chassi=?, tipoCombustivel=?, nomeProprietario=?,");
        updateQuery.append("contatoProprietario=?, emailProprietario=?, dataModificacao=?, especificacao=? ");
        updateQuery.append("WHERE  codigo =?");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    updateQuery.toString()
            )) {
                mapToSave(pstmt, veiculo);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    private void mapToSave(final PreparedStatement pstmt, Veiculo veiculo) throws SQLException {
        pstmt.setString(1, veiculo.getPlaca());
        pstmt.setString(2, veiculo.getCidade());
        pstmt.setString(3, veiculo.getFabricante());
        pstmt.setString(4, veiculo.getModelo());
        pstmt.setInt(5, veiculo.getAnoFabricacao());
        pstmt.setInt(6, veiculo.getAnoModelo());
        pstmt.setString(7, veiculo.getChassi());
        pstmt.setString(8, veiculo.getTipoCombustivel());
        pstmt.setString(9, veiculo.getProprietario().getNomePropretario());
        pstmt.setString(10, veiculo.getProprietario().getTelefonePropretario());
        pstmt.setString(11, veiculo.getProprietario().getEmailPropertario());
        pstmt.setTimestamp(12, Tempo.toTimestamp(LocalDateTime.now()));
        pstmt.setString(13, veiculo.getEspecificacoes());

        if (veiculo.getCodigo() != 0) {
            pstmt.setLong(14, veiculo.getCodigo());
        }
        pstmt.executeUpdate();
    }

    @Override
    public void delete(Long idVeiculo) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(db).append(".tb_veiculo WHERE codigo=?");
        remove(query.toString(), idVeiculo);
    }

    @Override
    public List<Veiculo> findAll() {
        List<Veiculo> dadosVeiculo = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ").append(db).append(".tb_veiculo");

            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Proprietario proprietario = new Proprietario(rs.getString(10), rs.getString(11), rs.getString(12));
                Veiculo veiculo = new Veiculo(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getString(9),
                        proprietario,
                        Tempo.toDate(rs.getTimestamp(13)),
                        rs.getString(15));
                dadosVeiculo.add(veiculo);
            }
            preparedStatement.close();
            rs.close();

        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return dadosVeiculo;
    }

    @Override
    public int total() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(*) FROM ").append(db).append(".tb_veiculo");
        return count(query.toString());
    }
}
