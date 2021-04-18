package cv.com.escola.model.dao;

import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.db.DBProperties;
import cv.com.escola.model.dao.exception.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public abstract class DAO {

    protected Connection conector = ConnectionManager.getInstance().begin();
    protected ResultSet rs;
    protected PreparedStatement preparedStatement;
    protected DBProperties dBProperties = new DBProperties();
    protected String db = dBProperties.loadPropertiesFile();
    protected String user = dBProperties.loadPropertiesFileUser();
    protected String pass = dBProperties.loadPropertiesFilePass();
    

    public DAO() {

    }

    @SuppressWarnings("UseSpecificCatch")
    public void transact(Consumer<Connection> callback) {
        Connection connection = null;
        try {
            connection = ConnectionManager.getInstance().begin();
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
                    throw new DataAccessException(e);
                }
            }
        }
    }

}
