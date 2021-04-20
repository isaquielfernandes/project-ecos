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

    public ClienteDAOImpl() {
        super();
    }

    @Override
    public void create(Cliente cliente) {
        final StringBuilder query = new StringBuilder();

        query.append("INSERT INTO ").append(db).append(".`tb_clientes` (`nomeCliente`,`nif`,");
        query.append("`contato`,`tipoCliente`,`descricao`,`endereco`,`codigoPostal`,");
        query.append("`localidade`) VALUES (?,?,?,?,?,?,?,?);");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    query.toString()
            )) {
                pstmt.setString(1, cliente.getNomeCliente());
                pstmt.setString(2, cliente.getNif());
                pstmt.setString(3, cliente.getContato());
                pstmt.setString(4, cliente.getTipoCliente());
                pstmt.setString(5, cliente.getDescricao());
                pstmt.setString(6, cliente.getEndereco());
                pstmt.setString(7, cliente.getCodigoPostal());
                pstmt.setString(8, cliente.getLocalidade());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        });
    }

    @Override
    public void update(Cliente cliente) {
        final StringBuilder query = new StringBuilder();
        
        query.append("UPDATE ").append(db)
                    .append(".`tb_clientes` SET `nomeCliente` = ?,`nif` = ?,");
        query.append("`contato` = ?,`tipoCliente` = ?,`descricao` = ?,`endereco` = ?,");
        query.append("`codigoPostal` = ?,`localidade` = ? WHERE `id_clientes` = ?;");
        
        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    query.toString()
            )) {
                pstmt.setString(1, cliente.getNomeCliente());
                pstmt.setString(2, cliente.getNif());
                pstmt.setString(3, cliente.getContato());
                pstmt.setString(4, cliente.getTipoCliente());
                pstmt.setString(5, cliente.getDescricao());
                pstmt.setString(6, cliente.getEndereco());
                pstmt.setString(7, cliente.getCodigoPostal());
                pstmt.setString(8, cliente.getLocalidade());
                pstmt.setInt(9, cliente.getIdCliente());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        });
    }

    @Override
    public void delete(Integer idClinte) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sql = "DELETE FROM " + db + ".tb_clientes WHERE id_clientes=?";
            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setInt(1, idClinte);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao excluir cliente na base de dados!");
        }
    }

    @Override
    public List<Cliente> findAll() {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sql = "SELECT * from " + db + ".tb_clientes";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);
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
            throw new DataAccessException("FIND: ", ex);
        }
        return clientes;
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        String sql = "SELECT * FROM " + db + ".tb_clientes WHERE id_clientes=?";
        Cliente retorno = new Cliente();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            preparedStatement = conector.prepareStatement(sql);
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
            throw new DataAccessException("FIND: ", ex);
        }
        return retorno;
    }
}
