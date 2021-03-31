package cv.com.escola.model.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConnectionManager extends LoadProperties {

    private final String idTimezone = java.util.TimeZone.getDefault().getID();
    private final String unicode = "?serverTimezone=" + idTimezone + "";
    private static ConnectionManager instance = new ConnectionManager();
    private Connection connection;

    private ConnectionManager() {
        
    }

    public Connection begin() {
        loadPropertiesFile();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url + unicode, user, pass);
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
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

    public static void close(final Connection conector) {
        /*try {
            conector.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    public static void close(final PreparedStatement stm) {
        try {
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void close(final PreparedStatement stm, final Connection conector) {
        try {
            stm.close();
            //conector.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void close(final PreparedStatement stm, final ResultSet rs, final Connection conector) {
        try {
            stm.close();
            rs.close();
            //conector.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
