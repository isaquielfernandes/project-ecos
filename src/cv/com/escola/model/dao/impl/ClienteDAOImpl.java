package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Cliente;
import cv.com.escola.model.dao.ClienteDAO;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl extends DAO implements ClienteDAO {

    private static final StringBuilder INSERT_QUERY = new StringBuilder();
    private static final StringBuilder UPDATE_QUERY = new StringBuilder();

    public ClienteDAOImpl() {
        super();
    }

    @Override
    public void create(Cliente cliente) {
        INSERT_QUERY.append("INSERT INTO ").append(db).append(".`tb_clientes` (`nomeCliente`,`nif`,");
        INSERT_QUERY.append("`contato`,`tipoCliente`,`descricao`,`endereco`,`codigoPostal`,");
        INSERT_QUERY.append("`localidade`) VALUES (?,?,?,?,?,?,?,?);");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    INSERT_QUERY.toString()
            )) {
                map(pstmt, cliente);
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        });
    }

    private void map(final PreparedStatement pstmt, Cliente cliente) throws SQLException {
        pstmt.setString(1, cliente.getNomeCliente());
        pstmt.setString(2, cliente.getNif());
        pstmt.setString(3, cliente.getContato());
        pstmt.setString(4, cliente.getTipoCliente());
        pstmt.setString(5, cliente.getDescricao());
        pstmt.setString(6, cliente.getEndereco());
        pstmt.setString(7, cliente.getCodigoPostal());
        pstmt.setString(8, cliente.getLocalidade());
        if (cliente.getIdCliente() != 0) {
            pstmt.setInt(9, cliente.getIdCliente());
        }
        pstmt.executeUpdate();
    }

    @Override
    public void update(Cliente cliente) {
        UPDATE_QUERY.append("UPDATE ").append(db)
                .append(".`tb_clientes` SET `nomeCliente` = ?,`nif` = ?,");
        UPDATE_QUERY.append("`contato` = ?,`tipoCliente` = ?,`descricao` = ?,`endereco` = ?,");
        UPDATE_QUERY.append("`codigoPostal` = ?,`localidade` = ? WHERE `id_clientes` = ?;");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    UPDATE_QUERY.toString()
            )) {
                map(pstmt, cliente);
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        });
    }

    @Override
    public void delete(Integer idClinte) {
        final StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(db).append(".tb_clientes WHERE id_clientes=?");
        remove(query.toString(), idClinte);
    }

    @Override
    public List<Cliente> findAll() {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT * from ").append(db).append(".tb_clientes");
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9));
                clientes.add(cliente);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return clientes;
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(db).append(".tb_clientes WHERE id_clientes=?");
        Cliente retorno = new Cliente();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            preparedStatement = conector.prepareStatement(query.toString());
            preparedStatement.setInt(1, cliente.getIdCliente());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                cliente.setNomeCliente(rs.getString("nomeCliente"));
                cliente.setNif(rs.getString("cni"));
                cliente.setContato(rs.getString("contato"));
                cliente.setTipoCliente(rs.getString("tipoCliente"));
                retorno = cliente;
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return retorno;
    }
}
