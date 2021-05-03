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
import java.sql.PreparedStatement;
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

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    query.toString()
            )) {
                mapToSave(pstmt, instrutor);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    @Override
    public void update(Instrutor instrutor) {
        final StringBuilder updateQuery = new StringBuilder();
        updateQuery.append("UPDATE ").append(db).append(".`tb_instrutor` SET `nome` = ?, `admissao` = ?, `email` = ?, `telefone` = ?, `movel` = ?, `foto` = ?, `pai` = ?, `mae` = ?, `grauAcademico` = ?, `tipoSanguineo` = ?, `morada` = ?, `cidadeIlha` = ?, `numeroDeIndentificacao` = ?, `naturalidade` = ?, `nacionalidade` = ?, `nascimento` = ?, `cartaConducao` = ?, `banco` = ?, `agencia` = ?, `numDeConta` = ?, `obsercacao` = ? WHERE `id` = ?;");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    updateQuery.toString()
            )) {
                mapToSave(pstmt, instrutor);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    private void mapToSave(final PreparedStatement pstmt, Instrutor instrutor) throws SQLException {
        pstmt.setString(1, instrutor.getNome());
        pstmt.setTimestamp(2, Tempo.toTimestamp(instrutor.getAdmissao()));
        pstmt.setString(3, instrutor.getEmail());
        pstmt.setString(4, instrutor.getContactoTelefonico());
        pstmt.setString(5, instrutor.getContactoMovel());
        pstmt.setString(6, instrutor.getFoto());
        pstmt.setString(7, instrutor.getNomeDoPai());
        pstmt.setString(8, instrutor.getNomeDaMae());
        pstmt.setString(9, instrutor.getGrauAcademico());
        pstmt.setString(10, instrutor.getTipoSanguineo());
        pstmt.setString(11, instrutor.getMorada());
        pstmt.setString(12, instrutor.getCidadeIlha());
        pstmt.setString(13, instrutor.getNumeroDeIndentificacao());
        pstmt.setString(14, instrutor.getNaturalidade());
        pstmt.setString(15, instrutor.getNacionalidade());
        pstmt.setTimestamp(16, Tempo.toTimestamp(instrutor.getNascimento()));
        pstmt.setString(17, instrutor.getCartaConducao());
        pstmt.setString(18, instrutor.getBanco());
        pstmt.setString(19, instrutor.getAgencia());
        pstmt.setString(20, instrutor.getNumDeConta());
        pstmt.setString(21, instrutor.getObservacao());

        if (instrutor.getId() != 0) {
            pstmt.setLong(22, instrutor.getId());
        }
        pstmt.executeUpdate();
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
        try (Connection conn = HikariCPDataSource.getConnection()) {
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
        try (Connection conn = HikariCPDataSource.getConnection()) {
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
