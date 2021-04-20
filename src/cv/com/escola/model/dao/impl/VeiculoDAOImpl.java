package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Proprietario;
import cv.com.escola.model.entity.Veiculo;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.VeiculoDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.Tempo;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class VeiculoDAOImpl extends DAO implements VeiculoDAO {

    public VeiculoDAOImpl() {
        super();
    }

    @Override
    public void create(Veiculo veiculo) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("insert into ").append(db).append(".tb_veiculo (placa, ilha, fabricante, modelo,");
            query.append("anoFabricacao, anoModelo, chassi, tipoCombustivel, nomeProprietario,");
            query.append("contatoProprietario, emailProprietario, dataCadastro, especificacao) ");
            query.append("VALUES (?, ?, ?,?, ?,?,?,?,?,?,?,?,?)");

            preparedStatement = conector.prepareStatement(query.toString());

            preparedStatement.setString(1, veiculo.getPlaca());
            preparedStatement.setString(2, veiculo.getCidade());
            preparedStatement.setString(3, veiculo.getFabricante());
            preparedStatement.setString(4, veiculo.getModelo());
            preparedStatement.setInt(5, veiculo.getAnoFabricacao());
            preparedStatement.setInt(6, veiculo.getAnoModelo());
            preparedStatement.setString(7, veiculo.getChassi());
            preparedStatement.setString(8, veiculo.getTipoCombustivel());
            preparedStatement.setString(9, veiculo.getProprietario().getNomePropretario());
            preparedStatement.setString(10, veiculo.getProprietario().getTelefonePropretario());
            preparedStatement.setString(11, veiculo.getProprietario().getEmailPropertario());
            preparedStatement.setTimestamp(12, Tempo.toTimestamp(veiculo.getDataCadastro()));
            preparedStatement.setString(13, veiculo.getEspecificacoes());

            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("CREATE: ", ex);
        }
    }

    @Override
    public void update(Veiculo veiculo) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("UPDATE ").append(db).append(".tb_veiculo set placa=?, ilha=?, fabricante=?, modelo=?,");
            query.append("anoFabricacao=?, anoModelo=?, chassi=?, tipoCombustivel=?, nomeProprietario=?,");
            query.append("contatoProprietario=?, emailProprietario=?, dataModificacao=?, especificacao=? ");
            query.append("WHERE  codigo =?");

            preparedStatement = conector.prepareStatement(query.toString());

            preparedStatement.setString(1, veiculo.getPlaca());
            preparedStatement.setString(2, veiculo.getCidade());
            preparedStatement.setString(3, veiculo.getFabricante());
            preparedStatement.setString(4, veiculo.getModelo());
            preparedStatement.setInt(5, veiculo.getAnoFabricacao());
            preparedStatement.setInt(6, veiculo.getAnoModelo());
            preparedStatement.setString(7, veiculo.getChassi());
            preparedStatement.setString(8, veiculo.getTipoCombustivel());
            preparedStatement.setString(9, veiculo.getProprietario().getNomePropretario());
            preparedStatement.setString(10, veiculo.getProprietario().getTelefonePropretario());
            preparedStatement.setString(11, veiculo.getProprietario().getEmailPropertario());
            preparedStatement.setTimestamp(12, Tempo.toTimestamp(LocalDateTime.now()));
            preparedStatement.setString(13, veiculo.getEspecificacoes());

            preparedStatement.setLong(14, veiculo.getCodigo());
            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("UPDATE: ", ex);
        }
    }

    @Override
    public void delete(Long idVeiculo) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("DELETE FROM ").append(db).append(".tb_veiculo WHERE codigo=?");
            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setLong(1, idVeiculo);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("DELETE: ", ex);
        }
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
            throw new DataAccessException(Level.SEVERE.getName(), ex);
        }
        return dadosVeiculo;
    }

    @Override
    public int total() {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT COUNT(*) FROM ").append(db).append(".tb_veiculo");
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException(Level.SEVERE.getName(), ex);
        }
        return 0;
    }
}
