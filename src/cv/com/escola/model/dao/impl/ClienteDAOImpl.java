package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Cliente;
import cv.com.escola.model.dao.ClienteDAO;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DaoException;
import cv.com.escola.model.util.Mensagem;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClienteDAOImpl extends DAO implements ClienteDAO{

    public ClienteDAOImpl() {
        super();
        conector = ConnectionManager.getInstance().getConnection();
    }
    
    @Override
    public void create(Cliente cliente ){
        try {
            String sql = "INSERT INTO "+ db +".`tb_clientes` (`nomeCliente`,`nif`,"
                    + "`contato`,`tipoCliente`,`descricao`,`endereco`,`codigoPostal`,"
                    + "`localidade`) VALUES (?,?,?,?,?,?,?,?);";

            preparedStatement = conector.prepareStatement(sql);

            preparedStatement.setString(1, cliente.getNomeCliente());
            preparedStatement.setString(2,cliente.getNif());
            preparedStatement.setString(3, cliente.getContato());
            preparedStatement.setString(4, cliente.getTipoCliente());
            preparedStatement.setString(5, cliente.getDescricao());
            preparedStatement.setString(6, cliente.getEndereco());
            preparedStatement.setString(7, cliente.getCodigoPostal());
            preparedStatement.setString(8, cliente.getLocalidade());

            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
            Mensagem.info("Cliente cadastrada com sucesso!");
        } catch (SQLException ex) {
            throw new DaoException("Erro ao cadastrar cliente na base de dados!");
        }
    }
    
    @Override
    public void update(Cliente cliente ){
        try {
            String sql = "UPDATE "+ db +".`tb_clientes` SET `nomeCliente` = ?,`nif` = ?,"
                    + "`contato` = ?,`tipoCliente` = ?,`descricao` = ?,`endereco` = ?,"
                    + "`codigoPostal` = ?,`localidade` = ? WHERE `id_clientes` = ?;";

            preparedStatement = conector.prepareStatement(sql);

            preparedStatement.setString(1, cliente.getNomeCliente());
            preparedStatement.setString(2,cliente.getNif());
            preparedStatement.setString(3, cliente.getContato());
            preparedStatement.setString(4, cliente.getTipoCliente());
            preparedStatement.setString(5, cliente.getDescricao());
            preparedStatement.setString(6, cliente.getEndereco());
            preparedStatement.setString(7, cliente.getCodigoPostal());
            preparedStatement.setString(8, cliente.getLocalidade());
            
            preparedStatement.setInt(9, cliente.getIdCliente());
            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
            Mensagem.info("Cliente atualizada com sucesso!");
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar dados cliente na base de dados! \n" + ex);
        }
    }
    
    @Override
    public void delete(Integer idClinte) {
        try {
            String sql = "DELETE FROM "+ db +".tb_clientes WHERE id_clientes=?";
            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setInt(1, idClinte);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DaoException("Erro ao excluir cliente na base de dados!");
        }
    }
    
    @Override
    public List<Cliente> findAll() {
        List<Cliente> clientes = new ArrayList<>();
        try {
            String sql = "SELECT * from "+ db +".tb_clientes";
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
            throw new DaoException("Erro ao consultar cliente na base de dados!");
        }
        return clientes;
    }
    
    @Override
    public Cliente buscar(Cliente cliente) {
        String sql = "SELECT * FROM "+ db +".tb_clientes WHERE id_clientes=?";
        Cliente retorno = new Cliente();
        try {
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
            Logger.getLogger(ClienteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
