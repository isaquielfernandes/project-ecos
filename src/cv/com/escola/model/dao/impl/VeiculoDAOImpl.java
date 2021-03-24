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

/**
 *
 * @author Isaquiel Fernandes
 */
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

            stm = conector.prepareStatement(inserir);

            stm.setString(1, veiculo.getPlaca());
            stm.setString(2, veiculo.getCidade());
            stm.setString(3, veiculo.getFabricante());
            stm.setString(4, veiculo.getModelo());
            stm.setInt(5, veiculo.getAnoFabricacao());
            stm.setInt(6, veiculo.getAnoModelo());
            stm.setString(7, veiculo.getChassi());
            stm.setString(8, veiculo.getTipoCombustivel());
            stm.setString(9, veiculo.getProprietario().getNomePropretario());
            stm.setString(10, veiculo.getProprietario().getTelefonePropretario());
            stm.setString(11, veiculo.getProprietario().getEmailPropertario());
            stm.setTimestamp(12, Tempo.toTimestamp(veiculo.getDataCadastro()));
            stm.setString(13, veiculo.getEspecificacoes());

            stm.executeUpdate();
            stm.close();
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

            stm = conector.prepareStatement(update);

            stm.setString(1, veiculo.getPlaca());
            stm.setString(2, veiculo.getCidade());
            stm.setString(3, veiculo.getFabricante());
            stm.setString(4, veiculo.getModelo());
            stm.setInt(5, veiculo.getAnoFabricacao());
            stm.setInt(6, veiculo.getAnoModelo());
            stm.setString(7, veiculo.getChassi());
            stm.setString(8, veiculo.getTipoCombustivel());
            stm.setString(9, veiculo.getProprietario().getNomePropretario());
            stm.setString(10, veiculo.getProprietario().getTelefonePropretario());
            stm.setString(11, veiculo.getProprietario().getEmailPropertario());
            stm.setTimestamp(12, Tempo.toTimestamp(LocalDateTime.now()));
            stm.setString(13, veiculo.getEspecificacoes());

            stm.setLong(14, veiculo.getCodigo());
            stm.executeUpdate();
            stm.close();
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
            stm = conector.prepareStatement(sql);
            stm.setLong(1, idVeiculo);
            stm.execute();
            stm.close();
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

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

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
            stm.close();
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
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            stm.close();
            rs.close();
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar total de veiculo na base de dados");
        }
        return 0;
    }
}
