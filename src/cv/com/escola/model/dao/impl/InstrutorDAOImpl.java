package cv.com.escola.model.dao.impl;

import cv.com.escola.model.dao.InstrutorDAO;
import cv.com.escola.model.entity.Instrutor;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Tempo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Isaquiel Fernandes
 */
public class InstrutorDAOImpl extends DAO implements InstrutorDAO{
    
    private final String sep = File.separator;
    private final File diretorio = new File("public/img/instrutor/");
    
    public InstrutorDAOImpl(){
        super();
    }
    
    @Override
    public void create(Instrutor instrutor ){
        try {
            String sql = "INSERT INTO "+ db +".`tb_instrutor` (`nome`, `admissao`, `email`, `telefone`, `movel`, `foto`, `pai`, `mae`, `grauAcademico`, `tipoSanguineo`, `morada`, `cidadeIlha`, `numeroDeIndentificacao`, `naturalidade`, `nacionalidade`, `nascimento`, `cartaConducao`, `banco`, `agencia`,`numDeConta`, `obsercacao`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

            stm = conector.prepareStatement(sql);

            stm.setString(1, instrutor.getNome());
            stm.setTimestamp(2, Tempo.toTimestamp(instrutor.getAdmissao()));
            stm.setString(3, instrutor.getEmail());
            stm.setString(4, instrutor.getContactoTelefonico());
            stm.setString(5, instrutor.getContactoMovel());
            stm.setString(6, instrutor.getFoto());
            stm.setString(7, instrutor.getNomeDoPai());
            stm.setString(8, instrutor.getNomeDaMae());
            stm.setString(9, instrutor.getGrauAcademico());
            stm.setString(10, instrutor.getTipoSanguineo());
            stm.setString(11, instrutor.getMorada());
            stm.setString(12, instrutor.getCidadeIlha());
            stm.setString(13, instrutor.getNumeroDeIndentificacao());
            stm.setString(14, instrutor.getNaturalidade());
            stm.setString(15, instrutor.getNacionalidade());
            stm.setTimestamp(16, Tempo.toTimestamp(instrutor.getNascimento()));
            stm.setString(17, instrutor.getCartaConducao());
            stm.setString(18, instrutor.getBanco());
            stm.setString(19, instrutor.getAgencia());
            stm.setString(20, instrutor.getNumDeConta());
            stm.setString(21, instrutor.getObservacao());

            stm.executeUpdate();
            stm.close();
            Mensagem.info("Instrutor cadastrada com sucesso!");
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao cadastrar Instrutor na base de dados! \n" + ex);
        }
    }
    
    @Override
    public void update(Instrutor instrutor ){
        try {
            String sql = "UPDATE "+ db +".`tb_instrutor` SET `nome` = ?, `admissao` = ?, `email` = ?, `telefone` = ?, `movel` = ?, `foto` = ?, `pai` = ?, `mae` = ?, `grauAcademico` = ?, `tipoSanguineo` = ?, `morada` = ?, `cidadeIlha` = ?, `numeroDeIndentificacao` = ?, `naturalidade` = ?, `nacionalidade` = ?, `nascimento` = ?, `cartaConducao` = ?, `banco` = ?, `agencia` = ?, `numDeConta` = ?, `obsercacao` = ? WHERE `id` = ?;";

            stm = conector.prepareStatement(sql);

            stm.setString(1, instrutor.getNome());
            stm.setTimestamp(2, Tempo.toTimestamp(instrutor.getAdmissao()));
            stm.setString(3, instrutor.getEmail());
            stm.setString(4, instrutor.getContactoTelefonico());
            stm.setString(5, instrutor.getContactoMovel());
            stm.setString(6, instrutor.getFoto());
            stm.setString(7, instrutor.getNomeDoPai());
            stm.setString(8, instrutor.getNomeDaMae());
            stm.setString(9, instrutor.getGrauAcademico());
            stm.setString(10, instrutor.getTipoSanguineo());
            stm.setString(11, instrutor.getMorada());
            stm.setString(12, instrutor.getCidadeIlha());
            stm.setString(13, instrutor.getNumeroDeIndentificacao());
            stm.setString(14, instrutor.getNaturalidade());
            stm.setString(15, instrutor.getNacionalidade());
            stm.setTimestamp(16, Tempo.toTimestamp(instrutor.getNascimento()));
            stm.setString(17, instrutor.getCartaConducao());
            stm.setString(18, instrutor.getBanco());
            stm.setString(19, instrutor.getAgencia());
            stm.setString(20, instrutor.getNumDeConta());
            stm.setString(21, instrutor.getObservacao());
            
            stm.setLong(22, instrutor.getId());
            stm.executeUpdate();
            stm.close();
            Mensagem.info("Instrutor atualizada com sucesso!");
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar dados instrutor na base de dados! \n" + ex);
        }
    }
    
    @Override
    public void delete(Long idInstrutor) {
        try {
            String sql = "DELETE FROM "+ db +".tb_instrutor WHERE id=?";

            stm = conector.prepareStatement(sql);

            stm.setLong(1, idInstrutor);
            stm.execute();

            stm.close();
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir instrutor na base de dados! \n" + ex);
        }
    }
    
    @Override
    public List<Instrutor> findAll() {
        List<Instrutor> instrutores = new ArrayList<>();
        ImageView img;
        try {
            String sql = "SELECT * from "+ db +".tb_instrutor";
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);
            while (rs.next()) {

                Instrutor instrutor = new Instrutor(
                        rs.getLong(1), rs.getString(2), rs.getDate(3).toLocalDate(), 
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9), 
                        rs.getString(10), rs.getString(11), rs.getString(12), 
                        rs.getString(13), rs.getString(14), rs.getString(15), 
                        rs.getString(16), rs.getDate(17).toLocalDate(), rs.getString(18), 
                        rs.getString(19), rs.getString(20), rs.getString(21), rs.getString(22));
                if(instrutor.getFoto() != null){
                    Image image = new Image(new FileInputStream(diretorio.getPath() + sep + instrutor.getFoto()));
                    img = new ImageView(image);
                    img.setPreserveRatio(true);//setting the fit height and width of the image view 
                    img.setFitHeight(68);
                    img.setFitWidth(68);
                    instrutor.setImgView(img);
                }else {
                    img = new ImageView("/cv/com/escola/img/sem_foto_0.gif");
                    img.setPreserveRatio(true);//setting the fit height and width of the image view 
                    img.setFitHeight(68);
                    img.setFitWidth(68);
                    instrutor.setImgView(img);
                }
                instrutores.add(instrutor);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar instrutor na base de dados! \n" + ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InstrutorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return instrutores;
    }
    
    @Override
    public Instrutor buscar(Instrutor instrutor) {
        String sql = "SELECT * FROM "+ db +".tb_instrutor WHERE id=?";
        Instrutor retorno = new Instrutor();
        try {
            stm = conector.prepareStatement(sql);
            stm.setLong(1, instrutor.getId());
            rs = stm.executeQuery();
            if (rs.next()) {
                instrutor.setNome(rs.getString("nome"));
                instrutor.setNumeroDeIndentificacao(rs.getString("numeroDeIndentificacao"));
                instrutor.setContactoMovel(rs.getString("movel"));
                instrutor.setEmail(rs.getString("email"));
                retorno = instrutor;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InstrutorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
