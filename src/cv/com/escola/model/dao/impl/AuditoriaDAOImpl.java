package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Auditoria;
import cv.com.escola.model.entity.Usuario;
import cv.com.escola.model.dao.AuditoriaDAO;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.Tempo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuditoriaDAOImpl extends DAO implements AuditoriaDAO{
    public AuditoriaDAOImpl() {
        super();
    }

    @Override
    public void inserir(Auditoria log) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sql = "INSERT INTO "+ db +".tb_auditoria (acao, data, descricao, fk_usuario) VALUES (?, ?, ?, ?);";
            preparedStatement = conector.prepareStatement(sql);
            
            preparedStatement.setString(1, log.getAcao());
            preparedStatement.setTimestamp(2, Tempo.toTimestamp(log.getData()));
            preparedStatement.setString(3, log.getDescricao());
            preparedStatement.setInt(4, log.getUser().getId());
            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("CREATE: ", ex);
        }
    }

    @Override
    public void excluir(int id) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sql = "DELETE FROM "+ db +".tb_auditoria WHERE id_auditoria = ?";
            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("DELETE: ", ex);
        }
    }

    @Override
    public List<Auditoria> logsUsuario(int id) {
        List<Auditoria> dados = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sql = "SELECT log.id_auditoria, log.acao, log.data, log.descricao, "
                    + "usuario.nome FROM "+ db +".tb_auditoria AS log, "+ db +".tb_usuario AS usuario "
                    +  "WHERE log.fk_usuario = usuario.id_usuario AND log.fk_usuario = ? ";

            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery(sql);

            while (rs.next()) {
                Auditoria logs = new Auditoria(rs.getInt(1), rs.getString(2), Tempo.toDate(rs.getTimestamp(3)), rs.getString(4), null);
                logs.setUser(new Usuario(rs.getInt(5), rs.getString(6)));
                dados.add(logs);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException("FIND: ", ex);
        }
        return dados;
    }

    @Override
    public List<Auditoria> logs() {
        List<Auditoria> dados = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sql = "SELECT log.id_auditoria, log.acao, log.data, log.descricao , usuario.nome "
                    + "FROM "+ db +".tb_auditoria AS log, "+ db +".tb_usuario AS usuario WHERE log.fk_usuario = usuario.id_usuario ";

            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                Auditoria log = new Auditoria(rs.getInt(1), rs.getString(2), Tempo.toDate(rs.getTimestamp(3)), rs.getString(4), null);
                log.setUser(new Usuario(rs.getInt(5), rs.getString(6)));
                dados.add(log);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException("FIND: ", ex);
        }
        return dados;
    }
}
