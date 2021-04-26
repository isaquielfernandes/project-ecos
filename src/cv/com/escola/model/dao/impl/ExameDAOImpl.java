package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Aluno;
import cv.com.escola.model.entity.Categoria;
import cv.com.escola.model.entity.Exame;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.ExameDAO;
import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.exception.DataAccessException;
import cv.com.escola.model.dao.exception.ReportException;
import cv.com.escola.model.util.Print;
import cv.com.escola.model.util.Tempo;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ExameDAOImpl extends DAO implements ExameDAO {

    public ExameDAOImpl() {
        super();
    }

    @Override
    public void create(Exame marcar) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("INSERT INTO ").append(db).append(".tb_exame (tipo_exame, dia, hora, descricao,");
            query.append("fk_categoria, fk_aluno, registroCriminal, atestadoMedico) ");
            query.append("VALUES(?,?,?,?,?,?,?,?)");

            preparedStatement = conector.prepareStatement(query.toString());

            preparedStatement.setString(1, marcar.getTipoExame());
            preparedStatement.setTimestamp(2, Tempo.toTimestamp(marcar.getDataExame()));
            preparedStatement.setString(3, marcar.getHoraDeExame().toString());
            preparedStatement.setString(4, marcar.getDescricao());
            preparedStatement.setInt(5, marcar.getCategoria().getId());
            preparedStatement.setInt(6, marcar.getAluno().getIdAluno());
            preparedStatement.setString(7, marcar.getRegistroCriminal());
            preparedStatement.setString(8, marcar.getAtestadoMedico());

            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("CREATE: ", ex);
        }
    }

    @Override
    public void update(Exame marcar) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("UPDATE ").append(db).append(".tb_exame SET tipo_exame=?, dia=?, hora=?, descricao=?,fk_categoria=?, fk_aluno=? WHERE id_exame=?");
            preparedStatement = conector.prepareStatement(query.toString());

            preparedStatement.setString(1, marcar.getTipoExame());
            preparedStatement.setTimestamp(2, Tempo.toTimestamp(marcar.getDataExame()));
            preparedStatement.setString(3, marcar.getHoraDeExame().toString());
            preparedStatement.setString(4, marcar.getDescricao());
            preparedStatement.setInt(5, marcar.getCategoria().getId());
            preparedStatement.setInt(6, marcar.getAluno().getIdAluno());

            preparedStatement.setLong(7, marcar.getIdExame());
            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException("UPDATE: ", ex);
        }
    }

    @Override
    public void delete(Long idExame) {
        final StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(db).append(".`tb_exame` WHERE id_exame =?");
        remove(query.toString(), idExame);
    }

    @Override
    public List<Exame> findAll() {
        List<Exame> dadosExame = new ArrayList<>();
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ").append(db).append(".exame_view order by Dia desc");
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Exame marcado = new Exame(rs.getInt(1), rs.getString(2),
                        Tempo.toDate(rs.getTimestamp(3)), rs.getTime(4).toLocalTime(),
                        rs.getString(5), new Categoria(rs.getInt(6), rs.getString(7)),
                        new Aluno(rs.getInt(8), rs.getString(9)));
                dadosExame.add(marcado);
            }
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DataAccessException("FIND: ", ex);
        }
        return dadosExame;
    }

    @Override
    public int total() {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            final StringBuilder query = new StringBuilder();
            query.append("SELECT COUNT(*) FROM ").append(db).append(".tb_exame");
            preparedStatement = conector.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("FIND: ", ex);
        }
        return 0;
    }

    @Override
    public void report() {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("de", Date.valueOf(LocalDate.of(2018, 10, 1)));
            parametros.put("ate", Date.valueOf(LocalDate.of(2018, 10, 31)));

            JasperReport jasperReport = JasperCompileManager.compileReport("/cv/com/escola/reports/Exame.jrxml");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conector);

            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setSize(900, 500);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ExameDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            throw new DataAccessException("UPDATE: ", ex);
        }
    }

    @Override
    public void reportListCandidadtoParaExame(String tipoExame, Date dia) {
        try (Connection conector = ConnectionManager.getInstance().begin();) {
            Map<String, Object> filtro = new HashMap<>();
            filtro.put("tipoExame", tipoExame);
            filtro.put("dia", dia);

            URL url = getClass().getResource("/cv/com/escola/reports/Exame.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, conector);
            Print jasperViewer = new Print();
            jasperViewer.viewReport("Exame", jasperPrint);

        } catch (JRException ex) {
            throw new ReportException("IMPRIMIR: ", ex);
        } catch (SQLException ex) {
            throw new DataAccessException("IMPRIMIR: ", ex);
        }
    }
}
