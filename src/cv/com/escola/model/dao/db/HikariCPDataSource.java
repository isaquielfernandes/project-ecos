
package cv.com.escola.model.dao.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HikariCPDataSource {
    
    private static HikariCPDataSource instance = new HikariCPDataSource();
    private final static HikariConfig CONFIG = new HikariConfig();
    private static final String ID_TIME_ZONE = java.util.TimeZone.getDefault().getID();
    private static final String SERVER_CONFIG = "?serverTimezone=" + ID_TIME_ZONE + "";
    private static final String USER = DBProperties.loadPropertiesFileUser();
    private static final  String PWD = DBProperties.loadPropertiesFilePass();
    
    static {
        CONFIG.setDriverClassName("com.mysql.cj.jdbc.Driver");
        CONFIG.setJdbcUrl("jdbc:mysql://localhost:3306/dbescola" + SERVER_CONFIG);
        CONFIG.setUsername(USER);
        CONFIG.setPassword(PWD);
        CONFIG.addDataSourceProperty("cachePrepStmts", "true");
        CONFIG.setAutoCommit(false);
        CONFIG.setMaximumPoolSize(18);
        CONFIG.addDataSourceProperty("prepStmtCacheSize", "150");
        CONFIG.addDataSourceProperty("prepStmtCacheSqlLimit", "248");
    }
    
    public HikariCPDataSource(){}
    
    public static Connection getConnection() {
        try {
            return dataSource().getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(HikariCPDataSource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static HikariDataSource dataSource() {
        return new HikariDataSource(CONFIG);
    }

    public static HikariCPDataSource getInstance() {
        if (instance == null) {
            instance = new HikariCPDataSource();
        }
        return instance;
    }
}
