package cv.com.escola.model.dao.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Component
public class HikariCPDataSource {

    private static final HikariDataSource DS;
    private static final HikariConfig CONFIG = new HikariConfig();
    private static final String ID_TIME_ZONE = TimeZone.getDefault().getID();
    private static final String SERVER_CONFIG = "?serverTimezone=" + ID_TIME_ZONE + "";
    private static final String USER = DBProperties.loadPropertiesFileUser();
    private static final String PWD = DBProperties.loadPropertiesFilePass();
    private static final String DB_NAME = DBProperties.loadPropertiesDB();
    private static final String HOST = DBProperties.loadPropertiesFileHost();
    private static final String PORT = DBProperties.loadPropertiesFilePort();

    protected final ExecutorService executorService = Executors.newSingleThreadExecutor(r -> {
        Thread thread = new Thread(r);
        thread.setName("DB: ");
        return thread;
    });

    static {
        int cpuCores = Runtime.getRuntime().availableProcessors();
        CONFIG.setDriverClassName("com.mysql.cj.jdbc.Driver");
        CONFIG.setJdbcUrl("jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + SERVER_CONFIG);
        CONFIG.setUsername(USER);
        CONFIG.setPassword(PWD);
        CONFIG.addDataSourceProperty("cachePrepStmts", "true");
        CONFIG.setMaximumPoolSize(cpuCores * 4);
        CONFIG.addDataSourceProperty("cachePrepStmts" , "true" );
        CONFIG.addDataSourceProperty("prepStmtCacheSize", "150");
        CONFIG.addDataSourceProperty("prepStmtCacheSqlLimit", "248");
        DS = new HikariDataSource(CONFIG);
    }

    private HikariCPDataSource() {}

    public static Connection getConnection() {
        try {
            return DS.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(HikariCPDataSource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static HikariDataSource dataSource() {
        return DS;
    }

}
