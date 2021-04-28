package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Organizacao;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.OrganizacaoDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.Tempo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrganizacaoDAOImpl extends DAO implements OrganizacaoDAO {

    public OrganizacaoDAOImpl() {
        super();
    }

    @Override
    public void create(Organizacao organizacao) {
        StringBuilder insert = new StringBuilder();
        insert.append("INSERT INTO ").append(db).append(".tb_organizacao(nome, sigla, email, fax, telefone, logradouro, bairro, cidade, estado, pais, descricao, data_cadastro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    insert.toString()
            )) {
                mapToSave(pstmt, organizacao);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    @Override
    public void update(Organizacao organizacao) {
        StringBuilder update = new StringBuilder();
        update.append("UPDATE ").append(db).append(".tb_organizacao SET nome=?, sigla=?, email=?, fax=?, telefone=?, logradouro=?, bairro=?, cidade=?, estado=?, pais=?, descricao=? WHERE id_orgao=? ");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    update.toString()
            )) {
                mapToSave(pstmt, organizacao);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    private void mapToSave(final PreparedStatement pstmt, Organizacao organizacao) throws SQLException {
        pstmt.setString(1, organizacao.getNome());
        pstmt.setString(2, organizacao.getSigla());
        pstmt.setString(3, organizacao.getEmail());
        pstmt.setString(4, organizacao.getFax());
        pstmt.setString(5, organizacao.getTelefone());
        pstmt.setString(6, organizacao.getLogradouro());
        pstmt.setString(7, organizacao.getBairro());
        pstmt.setString(8, organizacao.getCidade());
        pstmt.setString(9, organizacao.getEstado());
        pstmt.setString(10, organizacao.getPais());
        pstmt.setString(11, organizacao.getDescricao());

        if (organizacao.getId() != 0) {
            pstmt.setInt(12, organizacao.getId());
        }

        pstmt.executeUpdate();
    }

    @Override
    public void delete(Integer id) {
        StringBuilder delete = new StringBuilder();
        delete.append("DELETE FROM ").append(db).append(".tb_organizacao WHERE id_orgao =?");
        remove(delete.toString(), id);
    }

    @Override
    public List<Organizacao> findAll() {
        List<Organizacao> dados = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(db).append(".tb_organizacao");
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Organizacao organizacao = new Organizacao(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12), Tempo.toDate(resultSet.getTimestamp(13)));
                    dados.add(organizacao);
                }
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return dados;
    }

    @Override
    public List<Organizacao> combo() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT id_orgao, nome FROM ").append(db).append(".tb_organizacao ORDER BY nome ");
        List<Organizacao> dados = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    dados.add(new Organizacao(resultSet.getInt(1), resultSet.getString(2)));
                }
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return dados;
    }
}
