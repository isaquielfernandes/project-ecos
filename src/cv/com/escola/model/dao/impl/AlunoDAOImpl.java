package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Aluno;
import cv.com.escola.model.entity.Pagina;
import cv.com.escola.model.dao.AlunoDAO;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.util.Print;
import cv.com.escola.model.util.Tempo;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class AlunoDAOImpl extends DAO implements AlunoDAO {

    private final JdbcTemplate jdbcTemplate;
    private static final StringBuilder INSERT_QUERY = new StringBuilder();

    public AlunoDAOImpl() {
        super();
        jdbcTemplate = new JdbcTemplate(HikariCPDataSource.getInstance().dataSource());
    }

    @Override
    public void create(Aluno aluno) {
        INSERT_QUERY.append("INSERT INTO ").append(db).append(".tb_aluno ( nome, dataNascimento, numBI, dataEmisao, resedencia, conselho,");
        INSERT_QUERY.append("naturalidade, email, contato, habilitacaoLit, nacionalidade, ");
        INSERT_QUERY.append("foto, fotocopiaBI, descricao, data_cadastro, nomeDaMae, nomeDoPai, professao, estadoCivil, localDeEmisao, freguesia) ");
        INSERT_QUERY.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(),?,?,?,?,?,?)");
        
        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    INSERT_QUERY.toString()
            )) {
                mapToSave(aluno, pstmt);
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    @Override
    public void update(Aluno aluno) {
        StringBuilder updateQuery = new StringBuilder();
        updateQuery.append("UPDATE ").append(db).append(".tb_aluno SET nome=?, dataNascimento=?, numBI=?, dataEmisao=?, resedencia=?, conselho=?,");
        updateQuery.append("naturalidade=?, email=?, contato=?, habilitacaoLit=?, nacionalidade=?, ");
        updateQuery.append("foto=?, fotocopiaBI=?, descricao=?, nomeDaMae=?, nomeDoPai=?, professao=?, estadoCivil=?, localDeEmisao=?, freguesia=? WHERE id_aluno =?");
        
        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    updateQuery.toString()
            )) {
                mapToSave(aluno, pstmt);
                pstmt.setInt(21, aluno.getIdAluno());
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }

    @Override
    public void delete(Integer idAluno) {
        final StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(db).append(".tb_aluno WHERE id_aluno=?");
        remove(query.toString(), idAluno);
    }

    @Override
    public List<Aluno> findAll() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(db).append(".tb_aluno order by nome");
        return jdbcTemplate.query(query.toString(), new AlunoMapper());
    }

    @Override
    public ObservableList<Aluno> listar(int limit, int offset) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(db).append(".tb_aluno LIMIT ? OFFSET ? ");

        ObservableList listaAluno = FXCollections.observableArrayList();
        try (Connection conn = HikariCPDataSource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(query.toString());) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    listaAluno.add(mapRowToObject(resultSet));
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return listaAluno;
    }

    @Override
    public List<Aluno> autoCompletion() {
        List<Aluno> alunos = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT id_aluno, nome, numBI from ").append(db).append(".tb_aluno ORDER BY nome");
            preparedStatement = conector.prepareStatement(query.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setIdAluno(resultSet.getInt("id_aluno"));
                    aluno.setNome(resultSet.getString("nome"));
                    aluno.setNumBI(resultSet.getString("numBI"));
                    alunos.add(aluno);
                }
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return alunos;
    }

    @Override
    public int totalSearchStudent(String param) {
        int total = 0;
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            preparedStatement = conector.
                    prepareStatement("select count(id_aluno) from " + db + ".tb_aluno "
                            + "where nome like ? or contato like ? or numBI like ?");
            preparedStatement.setString(1, param + "%");
            preparedStatement.setString(2, param + "%");
            preparedStatement.setString(3, param + "%");
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return total;
    }

    @Override
    public ObservableList<Aluno> searchStudent(Pagina pagina, String params) {
        ObservableList<Aluno> alunos = FXCollections.observableArrayList();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            preparedStatement = conector.prepareStatement("select * from " + db
                    + ".tb_aluno where nome like ? or contato like ? or email like ? "
                    + "or numBI like ? limit " + pagina.getInicio() + "," + pagina.getFim());
            preparedStatement.setString(1, params + "%");
            preparedStatement.setString(2, params + "%");
            preparedStatement.setString(3, params + "%");
            preparedStatement.setString(4, params + "%");
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                alunos.add(mapRowToObject(rs));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return alunos;
    }

    @Override
    public void report() {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            URL url = getClass().getResource("/cv/com/escola/reports/Aluno.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conector);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException | SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void reportRequiremento(String biFiltro) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            HashMap<String, Object> filtro = new HashMap<>();
            filtro.put("bi", biFiltro);

            URL url = getClass().getResource("/cv/com/escola/reports/Requiremento.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, conector);
            Print jasperViewer = new Print();
            jasperViewer.viewReport("Requiremento", jasperPrint);
        } catch (JRException | SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    private void mapToSave(Aluno aluno, PreparedStatement ps) throws SQLException {
        ps.setString(1, aluno.getNome());
        ps.setTimestamp(2, Tempo.toTimestamp(aluno.getDataNascimento()));
        ps.setString(3, aluno.getNumBI());
        ps.setTimestamp(4, Tempo.toTimestamp(aluno.getDataEmisao()));
        ps.setString(5, aluno.getResidencia());
        ps.setString(6, aluno.getConselho());
        ps.setString(7, aluno.getNatural());
        ps.setString(8, aluno.getEmail());
        ps.setString(9, aluno.getContato());
        ps.setString(10, aluno.getHabilitacaoLit());
        ps.setString(11, aluno.getNacionalidade());
        ps.setBlob(12, aluno.getFoto());
        ps.setBlob(13, aluno.getFotocopiaBI());
        ps.setString(14, aluno.getDescricao());
        ps.setString(15, aluno.getNomeDaMae());
        ps.setString(16, aluno.getNomeDoPai());
        ps.setString(17, aluno.getProfessao());
        ps.setString(18, aluno.getEstadoCivil());
        ps.setString(19, aluno.getLocalDeEmisao());
        ps.setString(20, aluno.getFreguesia());
    }

    private Aluno mapRowToObject(ResultSet resultSet) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setIdAluno(resultSet.getInt("id_aluno"));
        aluno.setNome(resultSet.getString("nome"));
        aluno.setDataNascimento(Tempo.toDate(resultSet.getTimestamp("dataNascimento")));
        aluno.setNumBI(resultSet.getString("numBI"));
        aluno.setDataEmisao(Tempo.toDate(resultSet.getTimestamp("dataEmisao")));
        aluno.setResidencia(resultSet.getString("resedencia"));
        aluno.setConselho(resultSet.getString("conselho"));
        aluno.setNatural(resultSet.getString("naturalidade"));
        aluno.setEmail(resultSet.getString("email"));
        aluno.setContato(resultSet.getString("contato"));
        aluno.setHabilitacaoLit(resultSet.getString("habilitacaoLit"));
        aluno.setNacionalidade(resultSet.getString("nacionalidade"));
        aluno.setFoto(resultSet.getBinaryStream("foto"));
        aluno.setFotocopiaBI(resultSet.getBinaryStream("fotocopiaBI"));
        aluno.setDescricao(resultSet.getString("descricao"));
        aluno.setNomeDaMae(resultSet.getString("nomeDaMae"));
        aluno.setNomeDoPai(resultSet.getString("nomeDoPai"));
        aluno.setProfessao(resultSet.getString("professao"));
        aluno.setEstadoCivil(resultSet.getString("estadoCivil"));
        aluno.setLocalDeEmisao(resultSet.getString("localDeEmisao"));
        aluno.setFreguesia(resultSet.getString("freguesia"));
        aluno.setDataCadastro(Tempo.toDate(resultSet.getTimestamp("data_cadastro")));
        return aluno;
    }

    @Override
    public Page<Aluno> findAll(Pageable page) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class AlunoMapper implements RowMapper<Aluno> {

        @Override
        public Aluno mapRow(ResultSet rs, int rowNum) throws SQLException {
            return mapRowToObject(rs);
        }
    }

}
