package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Aluno;
import cv.com.escola.model.entity.Pagina;
import cv.com.escola.model.dao.AlunoDAO;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.exception.DaoException;
import cv.com.escola.model.dao.exception.NotFoundException;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Print;
import cv.com.escola.model.util.Tempo;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class AlunoDAOImpl extends DAO implements AlunoDAO {

    public AlunoDAOImpl() {
        super();
    }

    @Override
    public void create(Aluno aluno) {
        String sql = "INSERT INTO " + db + ".tb_aluno ( nome, dataNascimento, numBI, dataEmisao, resedencia, conselho,"
                + "naturalidade, email, contato, habilitacaoLit, nacionalidade, "
                + "foto, fotocopiaBI, descricao, data_cadastro, nomeDaMae, nomeDoPai, professao, estadoCivil, localDeEmisao, freguesia) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(),?,?,?,?,?,?)";
        try {
            preparedStatement = conector.prepareStatement(sql);
            mapToSave(aluno, preparedStatement);
            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
            Mensagem.info("Aluno cadastrada com sucesso!");
        } catch (SQLException ex) {
            throw new DaoException("Erro ao salvar aluno na base de dados! \n", ex);
        }
    }

    @Override
    public void update(Aluno aluno) {
        try {
            String sql = "UPDATE " + db + ".tb_aluno SET nome=?, dataNascimento=?, numBI=?, dataEmisao=?, resedencia=?, conselho=?,"
                    + "naturalidade=?, email=?, contato=?, habilitacaoLit=?, nacionalidade=?, "
                    + "foto=?, fotocopiaBI=?, descricao=?, nomeDaMae=?, nomeDoPai=?, professao=?, estadoCivil=?, localDeEmisao=?, freguesia=? WHERE id_aluno =?";

            preparedStatement = conector.prepareStatement(sql);
            mapToSave(aluno, preparedStatement);
            preparedStatement.setInt(21, aluno.getIdAluno());
            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DaoException("Erro ao atualizar aluno na base de dados! \n" + ex);
        }
    }

    @Override
    public void delete(Integer idAluno) {
        try {
            String sql = "DELETE FROM " + db + ".tb_aluno WHERE id_aluno=?";
            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setInt(1, idAluno);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DaoException("Erro ao excluir aluno na base de dados!");
        }
    }

    @Override
    public List<Aluno> findAll() {
        List<Aluno> alunos = new ArrayList<>();
        try {
            String sql = "SELECT * from " + db + ".tb_aluno order by nome";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                Aluno aluno = new Aluno();
                alunos.add(map(aluno, rs));
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DaoException("Erro ao consultar aluno na base de dados! \n" + ex);
        }
        return alunos;
    }

    @Override
    public ObservableList<Aluno> listar(int quantidade, int pagina) {
        ObservableList listaAluno = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from " + db + ".tb_aluno limit " + quantidade * pagina + "," + quantidade + "";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);

            while (rs.next()) {
                Aluno aluno = new Aluno();
                listaAluno.add(map(aluno, rs));
            }
            preparedStatement.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException("Erro ao consultar aluno na base de dados! \n" + ex);
        }
        return listaAluno;
    }

    @Override
    public List<Aluno> autoCompletion() {
        List<Aluno> alunos = new ArrayList<>();
        try {
            String sql = "SELECT id_aluno, nome, numBI from " + db + ".tb_aluno ORDER BY nome";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setIdAluno(rs.getInt("id_aluno"));
                aluno.setNome(rs.getString("nome"));
                aluno.setNumBI(rs.getString("numBI"));
                alunos.add(aluno);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DaoException("Erro ao consultar aluno na base de dados!");
        }
        return alunos;
    }

    @Override
    public int totalSearchStudent(String param) {
        int total = 0;
        try {
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
            Logger.getLogger(AlunoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    @Override
    public ObservableList<Aluno> student(int pagina) {
        ObservableList<Aluno> listData = FXCollections.observableArrayList();
        try {
            String sql = "select * from " + db + ".tb_aluno limit " + pagina;
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Aluno aluno = new Aluno();
                listData.add(map(aluno, rs));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listData;
    }

    @Override
    public ObservableList<Aluno> searchStudent(Pagina pagina, String query) {
        ObservableList<Aluno> alunos = FXCollections.observableArrayList();
        try {
            preparedStatement = conector.prepareStatement("select * from " + db + 
                    ".tb_aluno where nome like ? or contato like ? or email like ? "
                    + "or numBI like ? limit " + pagina.getInicio() + "," + pagina.getFim());
            preparedStatement.setString(1, query + "%");
            preparedStatement.setString(2, query + "%");
            preparedStatement.setString(3, query + "%");
            preparedStatement.setString(4, query + "%");
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Aluno aluno = new Aluno();
                alunos.add(map(aluno, rs));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return alunos;
    }

    @Override
    public void report() {
        try {
            URL url = getClass().getResource("/cv/com/escola/reports/Aluno.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conector);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(AlunoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao imprimir lista de alunos! \n" + ex);
        }
    }

    @Override
    public void reportRequiremento(String biFiltro) {
        try {
            HashMap filtro = new HashMap();
            filtro.put("bi", biFiltro);

            URL url = getClass().getResource("/cv/com/escola/reports/Requiremento.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, conector);
            Print jasperViewer = new Print();
            jasperViewer.viewReport("Requiremento", jasperPrint);
        } catch (JRException ex) {
            Logger.getLogger(AlunoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao imprimir requirimento! \n" + ex.getMessage());
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

    private Aluno map(Aluno aluno, ResultSet resultSet) throws SQLException {
        aluno.setIdAluno(resultSet.getInt("id_aluno"));
        aluno.setNome(resultSet.getString("nome"));
        aluno.setDataNascimento(Tempo.toDate(resultSet.getTimestamp("dataNascimento")));
        aluno.setNumBI(resultSet.getString("numBI"));
        aluno.setDataEmisao(Tempo.toDate(rs.getTimestamp("dataEmisao")));
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
        aluno.setNomeDoPai(rs.getString("nomeDoPai"));
        aluno.setProfessao(resultSet.getString("professao"));
        aluno.setEstadoCivil(resultSet.getString("estadoCivil"));
        aluno.setLocalDeEmisao(resultSet.getString("localDeEmisao"));
        aluno.setFreguesia(resultSet.getString("freguesia"));
        aluno.setDataCadastro(Tempo.toDate(resultSet.getTimestamp("data_cadastro")));
        return aluno;
    }
}
