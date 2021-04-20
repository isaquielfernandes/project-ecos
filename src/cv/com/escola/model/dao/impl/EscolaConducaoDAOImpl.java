package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.EscolaConducao;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.EmpresaDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.Tempo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EscolaConducaoDAOImpl extends DAO implements EmpresaDAO {

    public EscolaConducaoDAOImpl() {
        super();
    }

    @Override
    public void create(EscolaConducao empresa) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            String sqlInsert = "insert into " + db + ".tb_empresa (`nome`,`cidade`,"
                    + "`nif`,`endereco`,`email`,`contato`,`descricao`,`logo`,`assinatura`) "
                    + "VALUES (?,?,?,?,?,?,?,?,?);";

            preparedStatement = conector.prepareStatement(sqlInsert);
            map(empresa);
            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("CREATE: " + ex);
        }
    }

    @Override
    public void update(EscolaConducao empresa) {
        try (Connection conn = HikariCPDataSource.getConnection();) {
            String sql = "UPDATE " + db + ".`tb_empresa` SET `nome` = ?, "
                    + "`cidade` = ?, `nif` = ?, `endereco` = ?, `email` = ?, `contato` = ?, "
                    + "`descricao` = ?, `logo` = ?, `assinatura` = ? WHERE `id_empresa` = ?";

            preparedStatement = conn.prepareStatement(sql);
            map(empresa);
            preparedStatement.setInt(10, empresa.getIdEmpresa());
            preparedStatement.executeUpdate();
            conn.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("UPDATE: " + ex);
        }
    }

    private void map(EscolaConducao empresa) throws SQLException {
        preparedStatement.setString(1, empresa.getNome());
        preparedStatement.setString(2, empresa.getCidade());
        preparedStatement.setString(3, empresa.getNif());
        preparedStatement.setString(4, empresa.getEndereco());
        preparedStatement.setString(5, empresa.getEmail());
        preparedStatement.setString(6, empresa.getContato());
        preparedStatement.setString(7, empresa.getDescricao());
        preparedStatement.setBinaryStream(8, empresa.getLogo());
        preparedStatement.setBinaryStream(9, empresa.getAssinatura());
    }

    @Override
    public void editarSemLogo(EscolaConducao empresa) {
        try (Connection conn = HikariCPDataSource.getConnection();) {
            String sql = "UPDATE " + db + ".`tb_empresa` SET `razao_social` = ?, "
                    + "`nome` = ?, `nif` = ?, `cidade` = ?, `estado` = ?, `pais` = ?, "
                    + "`ano_vigencia` = ?, `descricao` = ? WHERE `id_empresa` = ?";

            preparedStatement = conn.prepareStatement(sql);

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
            throw new DataAccessException("UPDATE: ", ex.getCause());
        }
    }

    @Override
    public void delete(Integer idEmpresa) {
        try (Connection conn = HikariCPDataSource.getConnection();) {
            String sql = "DELETE FROM " + db + ".tb_empresa WHERE id_empresa=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, idEmpresa);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("DELETE: ", ex.getCause());
        }
    }

    @Override
    public List<EscolaConducao> findAll() {
        List<EscolaConducao> dadosEmpresa = new ArrayList<>();
        try (Connection conn = HikariCPDataSource.getConnection();) {
            String sql = "SELECT * FROM " + db + ".tb_empresa";
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);
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
            throw new DataAccessException("FIND: ", ex);
        }
        return dadosEmpresa;
    }
  
    @Override
    public int total() {
        try (Connection conn = HikariCPDataSource.getConnection();) {
            String sql = "SELECT COUNT(*) FROM " + db + ".tb_empresa";
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException("Erro ao consultar empresa na base de dados! \n", ex);
        }
        return 0;
    }
}
