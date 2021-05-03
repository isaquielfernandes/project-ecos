package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.EscolaConducao;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.EmpresaDAO;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.Tempo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EscolaConducaoDAOImpl extends DAO implements EmpresaDAO {

    public EscolaConducaoDAOImpl() {
        super();
    }

    @Override
    public void create(EscolaConducao empresa) {
        final StringBuilder query = new StringBuilder();
        query.append("insert into ").append(db).append(".tb_empresa (`nome`,`cidade`,");
        query.append("`nif`,`endereco`,`email`,`contato`,`descricao`,`logo`,`assinatura`) ");
        query.append("VALUES (?,?,?,?,?,?,?,?,?);");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    query.toString()
            )) {
                mapToSave(pstmt, empresa);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    @Override
    public void update(EscolaConducao empresa) {
        final StringBuilder updateQuery = new StringBuilder();
        updateQuery.append("UPDATE ").append(db).append(".`tb_empresa` SET `nome` = ?, ");
        updateQuery.append("`cidade` = ?, `nif` = ?, `endereco` = ?, `email` = ?, `contato` = ?, ");
        updateQuery.append("`descricao` = ?, `logo` = ?, `assinatura` = ? WHERE `id_empresa` = ?");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    updateQuery.toString()
            )) {
                mapToSave(pstmt, empresa);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    private void mapToSave(final PreparedStatement pstmt, EscolaConducao escola) throws SQLException {
        pstmt.setString(1, escola.getNome());
        pstmt.setString(2, escola.getCidade());
        pstmt.setString(3, escola.getNif());
        pstmt.setString(4, escola.getEndereco());
        pstmt.setString(5, escola.getEmail());
        pstmt.setString(6, escola.getContato());
        pstmt.setString(7, escola.getDescricao());
        pstmt.setBinaryStream(8, escola.getLogo());
        pstmt.setBinaryStream(9, escola.getAssinatura());
        if (escola.getIdEmpresa() != 0) {
            pstmt.setInt(10, escola.getIdEmpresa());
        }
        pstmt.executeUpdate();
    }

    @Override
    public void editarSemLogo(EscolaConducao empresa) {
        try (Connection conn = HikariCPDataSource.getConnection()) {
            final StringBuilder query = new StringBuilder();
            query.append("UPDATE ").append(db).append(".`tb_empresa` SET `razao_social` = ?, ");
            query.append("`nome` = ?, `nif` = ?, `cidade` = ?, `estado` = ?, `pais` = ?, ");
            query.append("`ano_vigencia` = ?, `descricao` = ? WHERE `id_empresa` = ?");

            preparedStatement = conn.prepareStatement(query.toString());

            preparedStatement.setString(1, empresa.getCidade());
            preparedStatement.setString(2, empresa.getNome());
            preparedStatement.setString(3, empresa.getNif());
            preparedStatement.setString(4, empresa.getEndereco());
            preparedStatement.setString(5, empresa.getEmail());
            preparedStatement.setString(6, empresa.getContato());
            preparedStatement.setTimestamp(7, Tempo.toTimestamp(empresa.getAnoVigencia()));
            preparedStatement.setString(8, empresa.getDescricao());

            preparedStatement.setInt(9, empresa.getIdEmpresa());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void delete(Integer idEmpresa) {
        final StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(db).append(".tb_empresa WHERE id_empresa=?");
        remove(query.toString(), idEmpresa);
    }

    @Override
    public List<EscolaConducao> findAll() {
        List<EscolaConducao> dadosEmpresa = new ArrayList<>();
        try (Connection conn = HikariCPDataSource.getConnection()) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ").append(db).append(".tb_empresa");
            preparedStatement = conn.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                EscolaConducao ec = new EscolaConducao(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8));
                ec.setLogo(rs.getBinaryStream(9));
                ec.setAssinatura(rs.getBinaryStream(10));
                dadosEmpresa.add(ec);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return dadosEmpresa;
    }

    @Override
    public int total() {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(*) FROM ").append(db).append(".tb_empresa");
        return count(query.toString());
    }
}
