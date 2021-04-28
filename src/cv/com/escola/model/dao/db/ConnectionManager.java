package cv.com.escola.model.dao.db;

import cv.com.escola.model.dao.exception.DataAccessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager extends LoadProperties {

    private static final String ID_TIME_ZONE = java.util.TimeZone.getDefault().getID();
    private static final String UNI_CODE = "?serverTimezone=" + ID_TIME_ZONE + "";
    private static ConnectionManager instance = new ConnectionManager();
    private Connection connection = null;

    private ConnectionManager() {
        
    }

    public Connection begin() {
        loadPropertiesFile();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url + UNI_CODE, user, pass);
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException| SQLException ex) {
            throw new DataAccessException(ex);
        }
        return connection;
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

}
