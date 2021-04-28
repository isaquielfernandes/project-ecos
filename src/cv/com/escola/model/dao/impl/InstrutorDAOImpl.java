package cv.com.escola.model.dao.impl;

import cv.com.escola.model.dao.InstrutorDAO;
import cv.com.escola.model.entity.Instrutor;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.Tempo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InstrutorDAOImpl extends DAO implements InstrutorDAO {

    private static final String SEP = File.separator;
    private final File diretorio = new File("public/img/instrutor/");

    public InstrutorDAOImpl() {
        super();
    }

    @Override
    public void create(Instrutor instrutor) {
        final StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(db).append(".`tb_instrutor` (`nome`, `admissao`, `email`, `telefone`, `movel`, `foto`, `pai`, `mae`, `grauAcademico`, `tipoSanguineo`, `morada`, `cidadeIlha`, `numeroDeIndentificacao`, `naturalidade`, `nacionalidade`, `nascimento`, `cartaConducao`, `banco`, `agencia`,`numDeConta`, `obsercacao`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");

        try (Connection conn = HikariCPDataSource.getInstance().getConnection()) {
            preparedStatement = conn.prepareStatement(query.toString());

            mapToSave(instrutor);

            preparedStatement.executeUpdate();
            conn.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    private void mapToSave(Instrutor instrutor) throws SQLException {
        preparedStatement.setString(1, instrutor.getNome());
        preparedStatement.setTimestamp(2, Tempo.toTimestamp(instrutor.getAdmissao()));
        preparedStatement.setString(3, instrutor.getEmail());
        preparedStatement.setString(4, instrutor.getContactoTelefonico());
        preparedStatement.setString(5, instrutor.getContactoMovel());
        preparedStatement.setString(6, instrutor.getFoto());
        preparedStatement.setString(7, instrutor.getNomeDoPai());
        preparedStatement.setString(8, instrutor.getNomeDaMae());
        preparedStatement.setString(9, instrutor.getGrauAcademico());
        preparedStatement.setString(10, instrutor.getTipoSanguineo());
        preparedStatement.setString(11, instrutor.getMorada());
        preparedStatement.setString(12, instrutor.getCidadeIlha());
        preparedStatement.setString(13, instrutor.getNumeroDeIndentificacao());
        preparedStatement.setString(14, instrutor.getNaturalidade());
        preparedStatement.setString(15, instrutor.getNacionalidade());
        preparedStatement.setTimestamp(16, Tempo.toTimestamp(instrutor.getNascimento()));
        preparedStatement.setString(17, instrutor.getCartaConducao());
        preparedStatement.setString(18, instrutor.getBanco());
        preparedStatement.setString(19, instrutor.getAgencia());
        preparedStatement.setString(20, instrutor.getNumDeConta());
        preparedStatement.setString(21, instrutor.getObservacao());
    }

    @Override
    public void update(Instrutor instrutor) {
        try (Connection conn = HikariCPDataSource.getInstance().getConnection()) {
            final StringBuilder query = new StringBuilder();
            query.append("UPDATE ").append(db).append(".`tb_instrutor` SET `nome` = ?, `admissao` = ?, `email` = ?, `telefone` = ?, `movel` = ?, `foto` = ?, `pai` = ?, `mae` = ?, `grauAcademico` = ?, `tipoSanguineo` = ?, `morada` = ?, `cidadeIlha` = ?, `numeroDeIndentificacao` = ?, `naturalidade` = ?, `nacionalidade` = ?, `nascimento` = ?, `cartaConducao` = ?, `banco` = ?, `agencia` = ?, `numDeConta` = ?, `obsercacao` = ? WHERE `id` = ?;");

            preparedStatement = conn.prepareStatement(query.toString());

            mapToSave(instrutor);

            preparedStatement.setLong(22, instrutor.getId());
            preparedStatement.executeUpdate();
            conn.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void delete(Long idInstrutor) {
        final StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(db).append(".tb_instrutor WHERE id=?");
        remove(query.toString(), idInstrutor);
    }

    @Override
    public List<Instrutor> findAll() {
        List<Instrutor> instrutores = new ArrayList<>();
        ImageView img;
        try (Connection conn = HikariCPDataSource.getInstance().getConnection()) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT * from ").append(db).append(".tb_instrutor");
            preparedStatement = conn.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                Instrutor instrutor = new Instrutor(
                        rs.getLong(1), rs.getString(2), rs.getDate(3).toLocalDate(),
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9),
                        rs.getString(10), rs.getString(11), rs.getString(12),
                        rs.getString(13), rs.getString(14), rs.getString(15),
                        rs.getString(16), rs.getDate(17).toLocalDate(), rs.getString(18),
                        rs.getString(19), rs.getString(20), rs.getString(21), rs.getString(22));
                if (instrutor.getFoto() != null) {
                    Image image = new Image(new FileInputStream(diretorio.getPath() + SEP + instrutor.getFoto()));
                    img = new ImageView(image);
                    img.setPreserveRatio(true);//setting the fit height and width of the image view 
                    img.setFitHeight(68);
                    img.setFitWidth(68);
                    instrutor.setImgView(img);
                } else {
                    img = new ImageView("/cv/com/escola/img/sem_foto_0.gif");
                    img.setPreserveRatio(true);//setting the fit height and width of the image view 
                    img.setFitHeight(68);
                    img.setFitWidth(68);
                    instrutor.setImgView(img);
                }
                instrutores.add(instrutor);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InstrutorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return instrutores;
    }

    @Override
    public Instrutor buscar(Instrutor instrutor) {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(db).append(".tb_instrutor WHERE id=?");
        Instrutor retorno = new Instrutor();
        try (Connection conn = HikariCPDataSource.getInstance().getConnection()) {
            preparedStatement = conn.prepareStatement(query.toString());
            preparedStatement.setLong(1, instrutor.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    instrutor.setNome(resultSet.getString("nome"));
                    instrutor.setNumeroDeIndentificacao(resultSet.getString("numeroDeIndentificacao"));
                    instrutor.setContactoMovel(resultSet.getString("movel"));
                    instrutor.setEmail(resultSet.getString("email"));
                    retorno = instrutor;
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return retorno;
    }
}
