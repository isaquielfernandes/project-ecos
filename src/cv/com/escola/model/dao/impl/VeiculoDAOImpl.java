package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Proprietario;
import cv.com.escola.model.entity.Veiculo;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.VeiculoDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Tempo;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class VeiculoDAOImpl extends DAO implements VeiculoDAO {

    public VeiculoDAOImpl() {
        super();
        conector = ConnectionManager.getInstance().getConnection();
    }

    @Override
    public void create(Veiculo veiculo) {
        try {
            String inserir = "insert into "+ db +".tb_veiculo (placa, ilha, fabricante, modelo,"
                    + "anoFabricacao, anoModelo, chassi, tipoCombustivel, nomeProprietario,"
                    + "contatoProprietario, emailProprietario, dataCadastro, especificacao) "
                    + "VALUES (?, ?, ?,?, ?,?,?,?,?,?,?,?,?)";

            preparedStatement = conector.prepareStatement(inserir);

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
            Mensagem.info("Veiculo cadastrada com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao inserir veiculo na base de dados! \n" + ex);
        }
    }

    @Override
    public void update(Veiculo veiculo) {
        try {
            String update = "UPDATE "+ db +".tb_veiculo set placa=?, ilha=?, fabricante=?, modelo=?,"
                    + "anoFabricacao=?, anoModelo=?, chassi=?, tipoCombustivel=?, nomeProprietario=?,"
                    + "contatoProprietario=?, emailProprietario=?, dataModificacao=?, especificacao=? "
                    + "WHERE  codigo =?";

            preparedStatement = conector.prepareStatement(update);

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
            Mensagem.info("Veiculo atualizada com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao atualizar veiculo na base de dados! \n" + ex);
        }
    }

    @Override
    public void delete(Long idVeiculo) {
        try {
            String sql = "DELETE FROM "+ db +".tb_veiculo WHERE codigo=?";
            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setLong(1, idVeiculo);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao excluir veiculo na base de dados! \n" + ex);
        }
    }

    @Override
    public List<Veiculo> findAll() {
        List<Veiculo> dadosVeiculo = new ArrayList<>();
        try {
            String sql = "SELECT * FROM "+ db +".tb_veiculo";

            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);

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
            Mensagem.erro("Erro ao consultar veiculo na base de dados! \n" + ex);
        }
        return dadosVeiculo;
    }
    
    @Override
    public int total() {
        try {
            String sql = "SELECT COUNT(*) FROM "+ db +".tb_veiculo";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar total de veiculo na base de dados");
        }
        return 0;
    }
}
