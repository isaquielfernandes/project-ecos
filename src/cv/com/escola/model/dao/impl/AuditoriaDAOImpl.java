package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Auditoria;
import cv.com.escola.model.entity.Usuario;
import cv.com.escola.model.dao.AuditoriaDAO;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.exception.DaoException;
import cv.com.escola.model.util.Tempo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuditoriaDAOImpl extends DAO implements AuditoriaDAO{
    public AuditoriaDAOImpl() {
        super();
    }

    @Override
    public void inserir(Auditoria log) {
        try {
            String sql = "INSERT INTO "+ db +".tb_auditoria (acao, data, descricao, fk_usuario) VALUES (?, ?, ?, ?);";
            stm = conector.prepareStatement(sql);
            
            stm.setString(1, log.getAcao());
            stm.setTimestamp(2, Tempo.toTimestamp(log.getData()));
            stm.setString(3, log.getDescricao());
            stm.setInt(4, log.getUser().getId());
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            throw new DaoException("Erro ao inserir logs na base de dados!");
        }
    }

    @Override
    public void excluir(int id) {
        try {
            String sql = "DELETE FROM "+ db +".tb_auditoria WHERE id_auditoria = ?";
            stm = conector.prepareStatement(sql);
            stm.setInt(1, id);
            stm.execute();
            stm.close();
        } catch (SQLException ex) {
            throw new DaoException("Erro ao excluir logs na base de dados!");
        }
    }

    @Override
    public List<Auditoria> logsUsuario(int id) {
        List<Auditoria> dados = new ArrayList<>();
        try {
            String sql = "SELECT log.id_auditoria, log.acao, log.data, log.descricao, "
                    + "usuario.nome FROM "+ db +".tb_auditoria AS log, "+ db +".tb_usuario AS usuario "
                    +  "WHERE log.fk_usuario = usuario.id_usuario AND log.fk_usuario = ? ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Auditoria logs = new Auditoria(rs.getInt(1), rs.getString(2), Tempo.toDate(rs.getTimestamp(3)), rs.getString(4), null);
                logs.setUser(new Usuario(rs.getInt(5), rs.getString(6)));
                dados.add(logs);
            }
            stm.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DaoException("Erro ao consultar logs na base de dados!");
        }
        return dados;
    }

    @Override
    public List<Auditoria> logs() {
        List<Auditoria> dados = new ArrayList<>();
        try {
            String sql = "SELECT log.id_auditoria, log.acao, log.data, log.descricao , usuario.nome "
                    + "FROM "+ db +".tb_auditoria AS log, "+ db +".tb_usuario AS usuario WHERE log.fk_usuario = usuario.id_usuario ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                Auditoria log = new Auditoria(rs.getInt(1), rs.getString(2), Tempo.toDate(rs.getTimestamp(3)), rs.getString(4), null);
                log.setUser(new Usuario(rs.getInt(5), rs.getString(6)));
                dados.add(log);
            }
            stm.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DaoException("Erro ao consultar logs na base de dados!");
        }
        return dados;
    }
}
