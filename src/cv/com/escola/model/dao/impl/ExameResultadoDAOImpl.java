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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        try (Connection connection = HikariCPDataSource.getConnection();) {
            String sql = "INSERT INTO "+ db +".`tb_exame_resultado` (`fk_exame`, `resultado`) VALUES (?, ?);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, resultado.getExame().getIdExame());
            preparedStatement.setString(2, resultado.getResultado());
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExameResultadoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void update(ExameResultado resultado) {
        try(Connection connection = HikariCPDataSource.getConnection();) {
            String sql = "UPDATE "+ db +".`tb_exame_resultado` SET "
                    + "`fk_exame` = ?, `resultado` = ?"
                    + " WHERE `id_exame_resultado` = ? AND `fk_exame` = ?";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, resultado.getExame().getIdExame());
            preparedStatement.setString(2, resultado.getResultado());

            preparedStatement.setLong(3, resultado.getIdExameResultado());
            preparedStatement.setLong(4, resultado.getExame().getIdExame());
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void delete(Long idExameResultado) {
        try(Connection connection = HikariCPDataSource.getConnection();) {
            String sql = "DELETE FROM "+ db +".`tb_exame_resultado` WHERE id_exame_resultado =?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idExameResultado);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public List<ExameResultado> findAll() {
        List<ExameResultado> dadosExame = new ArrayList<>();
        try (Connection connection = HikariCPDataSource.getConnection();) {
            String sql = "SELECT * FROM "+ db +".resultado_de_exame_view order by Dia desc ";
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                Exame marcado = new Exame(rs.getInt(2), rs.getString(3), 
                        Tempo.toDate(rs.getTimestamp(4)), rs.getTime(5).toLocalTime(), 
                        rs.getString(6), new Categoria(rs.getInt(7), rs.getString(8)), 
                        new Aluno(rs.getInt(9), rs.getString(10)));
                ExameResultado resultado = new ExameResultado(rs.getLong(1), marcado, rs.getString(11));
                dadosExame.add(resultado);
            }
            preparedStatement.close();
            rs.close();

        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        return dadosExame;
    }

    @Override
    public void reportFichaAulaPratica(Integer id, String nome) {
        try (Connection connection = HikariCPDataSource.getConnection();) {
            HashMap filtro = new HashMap();
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
