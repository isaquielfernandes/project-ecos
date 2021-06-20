package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Aluno;
import cv.com.escola.model.entity.Categoria;
import cv.com.escola.model.entity.Exame;
import cv.com.escola.model.entity.ExameResultado;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.ExameResultadoDAO;
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class ExameResultadoDAOImpl extends DAO implements ExameResultadoDAO {

    public ExameResultadoDAOImpl() {
        super();
    }

    @Override
    public void create(ExameResultado resultado) {
        StringBuilder insert = new StringBuilder();
        insert.append("INSERT INTO ").append(db)
                .append(".tb_exame_resultado (fk_exame, resultado) VALUES (?,?);");

        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    insert.toString()
            )) {
                mapToSave(pstmt, resultado);
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        });
    }

    @Override
    public void update(ExameResultado resultado) {
        StringBuilder update = new StringBuilder();
        update.append("UPDATE ")
                .append(db)
                .append(".`tb_exame_resultado` SET ")
                .append(" resultado = ?")
                .append(" WHERE id_exame_resultado = ? ");
        
        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    update.toString()
            )) {
                mapToSave(pstmt, resultado);
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        });
    }

    private void mapToSave(final PreparedStatement pstmt, ExameResultado resultado) throws SQLException {
        pstmt.setLong(1, resultado.getExame().getIdExame());
        pstmt.setString(2, resultado.getResultado());
        if (resultado.getIdExameResultado() != 0) {
            pstmt.setLong(3, resultado.getIdExameResultado());
        }
        pstmt.executeUpdate();
    }

    @Override
    public void delete(Long idExameResultado) {
        final StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(db)
                .append(".`tb_exame_resultado` WHERE id_exame_resultado =?");
        remove(query.toString(), idExameResultado);
    }

    @Override
    public List<ExameResultado> findAll() {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(db).append(".resultado_de_exame_view order by Dia desc ");
            
        List<ExameResultado> dadosExame = new ArrayList<>();
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query.toString());
        ) { 
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Exame marcado = new Exame(resultSet.getInt(2), resultSet.getString(3),
                            Tempo.toDate(resultSet.getTimestamp(4)), resultSet.getTime(5).toLocalTime(),
                            resultSet.getString(6), new Categoria(resultSet.getInt(7), resultSet.getString(8)),
                            new Aluno(resultSet.getInt(9), resultSet.getString(10)));
                    ExameResultado resultado = new ExameResultado(resultSet.getLong(1), marcado, resultSet.getString(11));
                    dadosExame.add(resultado);
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return dadosExame;
    }

    @Override
    public void gerarFichaDeAulaPratica(Integer id, String nome) {
        try (Connection connection = HikariCPDataSource.getConnection()) {
            Map<String, Object> filtro = new HashMap<>();
            filtro.put("id", id);
            URL url = getClass().getResource("/cv/com/escola/reports/fechaAulaPratica.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, connection);
            Print jasperViewer = new Print();
            jasperViewer.viewReport("Ficha De Aula Pratica: " + nome, jasperPrint);
        } catch (JRException | SQLException ex) {
            Logger.getLogger(ExameResultadoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
