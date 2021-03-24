package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.InspecaoTecnica;
import cv.com.escola.model.entity.Veiculo;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.InspecaoTecnicaDAO;
import cv.com.escola.model.util.Mensagem;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InspecaoTecnicaDAOImpl extends DAO implements InspecaoTecnicaDAO {

    public InspecaoTecnicaDAOImpl() {
        super();
    }

    @Override
    public void create(InspecaoTecnica inspecao) {
        try {
            String inserir = "INSERT INTO "+ db +".`tb_inspecao_tecnica` "
                    + "(`veiculo`, `tipoInspecao`, `dataInspecao`, `duracao`, `resultado`, `validade`) VALUES (?,?,?,?,?,?)";

            stm = conector.prepareStatement(inserir);

            stm.setLong(1, inspecao.getVeiculo().getCodigo());
            stm.setString(2, inspecao.getTipoDeInspeccao());
            stm.setDate(3, java.sql.Date.valueOf(inspecao.getDataDeInspeccao()));
            stm.setInt(4, inspecao.getMesDeDuracao());
            stm.setString(5, inspecao.getResultado());
            stm.setString(6, inspecao.getValidade());

            stm.executeUpdate();
            stm.close();
            Mensagem.info("Inspecção de veículo cadastrada com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(InspecaoTecnicaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao inserir dados da inspecção de veiculo na base de dados! \n" + ex);
        }
    }

    @Override
    public void update(InspecaoTecnica inspecao) {
        try {
            String update = "UPDATE "+ db +".`tb_inspecao_tecnica` SET `veiculo` = ?, `tipoInspecao` = ?, `dataInspecao` = ?, `duracao` = ?, `resultado` = ?, `validade` = ? WHERE `id` = ?";

            stm = conector.prepareStatement(update);

            stm.setLong(1, inspecao.getVeiculo().getCodigo());
            stm.setString(2, inspecao.getTipoDeInspeccao());
            stm.setDate(3, java.sql.Date.valueOf(inspecao.getDataDeInspeccao()));
            stm.setInt(4, inspecao.getMesDeDuracao());
            stm.setString(5, inspecao.getResultado());
            stm.setString(6, inspecao.getValidade());

            stm.setLong(7, inspecao.getId());
            stm.executeUpdate();
            stm.close();
            Mensagem.info("Inspecção de Veiculo atualizada com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(InspecaoTecnicaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao atualizar Inspecção de veiculo na base de dados! \n" + ex);
        }
    }

    @Override
    public void delete(Long idInspecao) {
        try {
            String sql = "DELETE FROM "+ db +".`tb_inspecao_tecnica` WHERE id=?";
            stm = conector.prepareStatement(sql);
            stm.setLong(1, idInspecao);
            stm.execute();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(InspecaoTecnicaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao excluir inspecao de veiculo na base de dados! \n" + ex);
        }
    }

    @Override
    public List<InspecaoTecnica> findAll() {

        List<InspecaoTecnica> dadosInspecao = new ArrayList<>();

        try {
            String sql = "SELECT * FROM "+ db +".inspecao_tecnica_view";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Veiculo veiculo = new Veiculo(
                        rs.getLong(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7));
                
                InspecaoTecnica inspecao = new InspecaoTecnica(
                        rs.getLong(1), rs.getString(8), rs.getString(11), rs.getDate(9).toLocalDate(), veiculo, rs.getInt(10), rs.getString(12) );
                dadosInspecao.add(inspecao);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar da inspecao do veiculo na base de dados! \n" + ex);
        }
        return dadosInspecao;
    }
}
