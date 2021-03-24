package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Empresa;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.EmpresaDAO;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Tempo;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Isaquiel Fernandes
 */
public class EmpresaDAOImpl extends DAO implements EmpresaDAO {

    public EmpresaDAOImpl() {
        super();
    }

    @Override
    public void create(Empresa empresa) {
        try {
            String inserir = "insert into " + db + ".tb_empresa (`nome`,`cidade`,`nif`,`endereco`,`email`,`contato`,`descricao`,`logo`,`assinatura`) VALUES (?,?,?,?,?,?,?,?,?);";

            stm = conector.prepareStatement(inserir);

            stm.setString(1, empresa.getNome());
            stm.setString(2, empresa.getCidade());
            stm.setString(3, empresa.getNif());
            stm.setString(4, empresa.getEndereco());
            stm.setString(5, empresa.getEmail());
            stm.setString(6, empresa.getContato());
            stm.setString(7, empresa.getDescricao());
            if (empresa.getFileLogo() != null) {
                empresa.setFileInputStreamLogo(new FileInputStream(empresa.getFileLogo()));
                stm.setBinaryStream(8, empresa.getFileInputStreamLogo(), empresa.getFileLogo().length());
            } else {
                stm.setBinaryStream(8, null);
            }

            if (empresa.getFileLogo() != null) {
                empresa.setFileInputStreamAssinatura(new FileInputStream(empresa.getFileCarimboAssinatura()));
                stm.setBinaryStream(9, empresa.getFileInputStreamAssinatura(), empresa.getFileCarimboAssinatura().length());
            } else {
                stm.setBinaryStream(9, null);
            }
            stm.executeUpdate();
            stm.close();
            Mensagem.info("Configuracao da empresa cadastrada com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(EmpresaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao inserir dados da empresa na base de dados! \n" + ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmpresaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Empresa empresa) {
        try {
            String sql = "UPDATE " + db + ".`tb_empresa` SET `nome` = ?, "
                    + "`cidade` = ?, `nif` = ?, `endereco` = ?, `email` = ?, `contato` = ?, "
                    + "`descricao` = ?, `logo` = ?, `assinatura` = ? WHERE `id_empresa` = ?";

            stm = conector.prepareStatement(sql);

            stm.setString(1, empresa.getNome());
            stm.setString(2, empresa.getCidade());
            stm.setString(3, empresa.getNif());
            stm.setString(4, empresa.getEndereco());
            stm.setString(5, empresa.getEmail());
            stm.setString(6, empresa.getContato());
            stm.setString(7, empresa.getDescricao());
            
            if (empresa.getFileLogo() != null) {
                empresa.setFileInputStreamLogo(new FileInputStream(empresa.getFileLogo()));
                stm.setBinaryStream(8, empresa.getFileInputStreamLogo(), empresa.getFileLogo().length());
            } else {
                stm.setBlob(8, empresa.getLogo());
            }

            if (empresa.getFileCarimboAssinatura()!= null) {
                empresa.setFileInputStreamAssinatura(new FileInputStream(empresa.getFileCarimboAssinatura()));
                stm.setBinaryStream(9, empresa.getFileInputStreamAssinatura(), empresa.getFileCarimboAssinatura().length());
            } else {
                stm.setBlob(9, empresa.getAssinatura());
            }

            stm.setInt(10, empresa.getIdEmpresa());

            stm.executeUpdate();
            stm.close();
            Mensagem.info("Empresa atualizada com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(EmpresaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao editar dados da empresa na base de dados! \n" + ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmpresaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editarSemLogo(Empresa empresa) {
        try {
            String sql = "UPDATE " + db + ".`tb_empresa` SET `razao_social` = ?, "
                    + "`nome` = ?, `nif` = ?, `cidade` = ?, `estado` = ?, `pais` = ?, "
                    + "`ano_vigencia` = ?, `descricao` = ? WHERE `id_empresa` = ?";

            stm = conector.prepareStatement(sql);

            stm.setString(1, empresa.getCidade());
            stm.setString(2, empresa.getNome());
            stm.setString(3, empresa.getNif());
            stm.setString(4, empresa.getEndereco());
            stm.setString(5, empresa.getEmail());
            stm.setString(6, empresa.getContato());
            stm.setTimestamp(7, Tempo.toTimestamp(empresa.getAnoVigencia()));
            stm.setString(8, empresa.getDescricao());

            stm.setInt(9, empresa.getIdEmpresa());

            stm.executeUpdate();
            stm.close();
            Mensagem.info("Empresa atualizada com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(EmpresaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao editar dados da empresa na base de dados! \n" + ex);
        }
    }

    @Override
    public void delete(Integer idEmpresa) {
        try {
            String sql = "DELETE FROM " + db + ".tb_empresa WHERE id_empresa=?";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, idEmpresa);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir emprasa na base de dados! \n" + ex);
        }
    }

    @Override
    public List<Empresa> findAll() {
        List<Empresa> dadosEmpresa = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + db + ".tb_empresa";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Empresa empresa = new Empresa(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
                empresa.setLogo(rs.getBlob(9));
                if (empresa.getLogo() != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(empresa.getLogo().getBytes(1, (int) empresa.getLogo().length()));
                    empresa.setImageLogo(new Image(byteArrayInputStream));
                } else {
                    empresa.setImageLogo(new Image("/cv/com/escola/img/sem_foto_0.gif"));
                }
                empresa.setAssinatura(rs.getBlob(10));
                if (empresa.getAssinatura() != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(empresa.getAssinatura().getBytes(1, (int) empresa.getAssinatura().length()));
                    empresa.setImageAssinatura(new Image(byteArrayInputStream));
                } else {
                    empresa.setImageAssinatura(new Image("/cv/com/escola/img/sem_foto_0.gif"));
                }
                dadosEmpresa.add(empresa);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar empresa na base de dados! \n" + ex);
        }
        return dadosEmpresa;
    }

    // buscar se existe registro de empresa na database  
    @Override
    public int total() {
        try {
            String sql = "SELECT COUNT(*) FROM " + db + ".tb_empresa";
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            stm.close();
            rs.close();
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar total de empresa na base de dados");
        }
        return 0;
    }
}
