
package cv.com.escola.model.dao.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HikariCPDataSource {
    
    private static HikariCPDataSource instance = new HikariCPDataSource();
    private static final HikariConfig CONFIG = new HikariConfig();
    private static final String ID_TIME_ZONE = java.util.TimeZone.getDefault().getID();
    private static final String SERVER_CONFIG = "?serverTimezone=" + ID_TIME_ZONE + "";
    private static final String USER = DBProperties.loadPropertiesFileUser();
    private static final  String PWD = DBProperties.loadPropertiesFilePass();
    
    protected final ExecutorService executorService = Executors.newSingleThreadExecutor( r -> {
        Thread thread = new Thread(r);
        thread.setName("DB");
        return thread;
    });
    
    static {
        int cpuCores = Runtime.getRuntime().availableProcessors();
        CONFIG.setDriverClassName("com.mysql.cj.jdbc.Driver");
        CONFIG.setJdbcUrl("jdbc:mysql://localhost:3306/dbescola" + SERVER_CONFIG);
        CONFIG.setUsername(USER);
        CONFIG.setPassword(PWD);
        CONFIG.addDataSourceProperty("cachePrepStmts", "true");
        CONFIG.setAutoCommit(false);
        CONFIG.setMaximumPoolSize(cpuCores * 4);
        CONFIG.addDataSourceProperty("prepStmtCacheSize", "150");
        CONFIG.addDataSourceProperty("prepStmtCacheSqlLimit", "248");
    }
    
    private HikariCPDataSource(){}
    
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
