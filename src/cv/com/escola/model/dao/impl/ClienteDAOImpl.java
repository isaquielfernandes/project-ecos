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

            stm = conector.prepareStatement(sql);

            stm.setString(1, cliente.getNomeCliente());
            stm.setString(2,cliente.getNif());
            stm.setString(3, cliente.getContato());
            stm.setString(4, cliente.getTipoCliente());
            stm.setString(5, cliente.getDescricao());
            stm.setString(6, cliente.getEndereco());
            stm.setString(7, cliente.getCodigoPostal());
            stm.setString(8, cliente.getLocalidade());

            stm.executeUpdate();
            stm.close();
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

            stm = conector.prepareStatement(sql);

            stm.setString(1, cliente.getNomeCliente());
            stm.setString(2,cliente.getNif());
            stm.setString(3, cliente.getContato());
            stm.setString(4, cliente.getTipoCliente());
            stm.setString(5, cliente.getDescricao());
            stm.setString(6, cliente.getEndereco());
            stm.setString(7, cliente.getCodigoPostal());
            stm.setString(8, cliente.getLocalidade());
            
            stm.setInt(9, cliente.getIdCliente());
            stm.executeUpdate();
            stm.close();
            Mensagem.info("Cliente atualizada com sucesso!");
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar dados cliente na base de dados! \n" + ex);
        }
    }
    
    @Override
    public void delete(Integer idClinte) {
        try {
            String sql = "DELETE FROM "+ db +".tb_clientes WHERE id_clientes=?";
            stm = conector.prepareStatement(sql);
            stm.setInt(1, idClinte);
            stm.execute();
            stm.close();
        } catch (SQLException ex) {
            throw new DaoException("Erro ao excluir cliente na base de dados!");
        }
    }
    
    @Override
    public List<Cliente> findAll() {
        List<Cliente> clientes = new ArrayList<>();
        try {
            String sql = "SELECT * from "+ db +".tb_clientes";
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt(1), rs.getString(2), rs.getString(3), 
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9));
                clientes.add(cliente);
            }
            stm.close();
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
            stm = conector.prepareStatement(sql);
            stm.setInt(1, cliente.getIdCliente());
            rs = stm.executeQuery();
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
