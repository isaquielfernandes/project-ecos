package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Auditoria;
import cv.com.escola.model.entity.Usuario;
import cv.com.escola.model.dao.AuditoriaDAO;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.Tempo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuditoriaDAOImpl extends DAO implements AuditoriaDAO {

    public AuditoriaDAOImpl() {
        super();
    }

    @Override
    public void inserir(Auditoria log) {
        final StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(db).append(".tb_auditoria (acao, data, descricao, fk_usuario) VALUES (?, ?, ?, ?);");

        transact((Connection connection) -> {
            try (PreparedStatement auditoriaStatement = connection.prepareStatement(
                    query.toString()
            )) {
                auditoriaStatement.setString(1, log.getAcao());
                auditoriaStatement.setTimestamp(2, Tempo.toTimestamp(log.getData()));
                auditoriaStatement.setString(3, log.getDescricao());
                auditoriaStatement.setLong(4, log.getUser().getId());
                auditoriaStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    @Override
    public void excluir(int id) {
        final StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(db).append(".tb_auditoria WHERE id_auditoria = ?");
        remove(query.toString(), id);
    }

    @Override
    public List<Auditoria> logsUsuario(int id) {
        List<Auditoria> dados = new ArrayList<>();
        final StringBuilder query = new StringBuilder();
        query.append("SELECT log.id_auditoria, log.acao, log.data, log.descricao, ");
        query.append("usuario.nome FROM ").append(db).append(".tb_auditoria AS log, ").append(db).append(".tb_usuario AS usuario ");
        query.append("WHERE log.fk_usuario = usuario.id_usuario AND log.fk_usuario = ? ");

        try (Connection conector = HikariCPDataSource.getConnection();) {
            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setInt(1, id);
            mapResultSet(dados, preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return dados;
    }

    @Override
    public List<Auditoria> logs() {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT log.id_auditoria, log.acao, log.data, log.descricao , usuario.nome ");
        query.append("FROM ").append(db).append(".tb_auditoria AS log, ").append(db).append(".tb_usuario AS usuario WHERE log.fk_usuario = usuario.id_usuario ");

        List<Auditoria> dados = new ArrayList<>();
        try (Connection conector = HikariCPDataSource.getConnection();) {
            PreparedStatement pstmt = conector.prepareStatement(query.toString());
            mapResultSet(dados, pstmt);
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return dados;
    }

    private void mapResultSet(List<Auditoria> dados, PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Auditoria log = new Auditoria(resultSet.getInt(1), resultSet.getString(2), Tempo.toDate(resultSet.getTimestamp(3)), resultSet.getString(4), null);
                log.setUser(new Usuario(resultSet.getInt(5), resultSet.getString(6)));
                dados.add(log);
            }
            statement.close();
        }
    }
}
