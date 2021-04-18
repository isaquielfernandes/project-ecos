
package cv.com.escola.model.dao.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class HikariCPDataSource extends LoadProperties {
    
    private static HikariCPDataSource instance = new HikariCPDataSource();
    private static HikariDataSource ds;
    
    private HikariCPDataSource(){}
    
    public Connection getConnection() throws SQLException {
        return dataSource().getConnection();
    }
    
    public HikariDataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/dbescola");
        config.setUsername(user);
        config.setPassword(pass);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
        return ds;
    }

    public static HikariCPDataSource getInstance() {
        if (instance == null) {
            instance = new HikariCPDataSource();
        }
        return instance;
    }
}
