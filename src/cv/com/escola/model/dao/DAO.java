package cv.com.escola.model.dao;

import cv.com.escola.model.dao.db.DBProperties;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DAO {
    
    private static final Logger LOG = Logger.getLogger(DAO.class.getName());
    protected ResultSet rs;
    protected PreparedStatement preparedStatement;
    protected String db = DBProperties.loadPropertiesDB();
    protected String user = DBProperties.loadPropertiesFileUser();
    protected String pass = DBProperties.loadPropertiesFilePass();
    
    public DAO() {

    }

    @SuppressWarnings("UseSpecificCatch")
    public void transact(Consumer<Connection> callback) {
        Connection connection = null;
        try {
            connection = HikariCPDataSource.getConnection();
            callback.accept(connection);
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DataAccessException(ex);
                }
            }
            throw (e instanceof DataAccessException
                    ? (DataAccessException) e : new DataAccessException(e));
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOG.log(Level.FINER, e.getMessage());
                }
            }
        }
    }

}
