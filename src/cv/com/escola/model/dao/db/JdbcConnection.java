package cv.com.escola.model.dao.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Isaquiel
 */
public interface JdbcConnection {
    
    Connection obtainConnection() throws SQLException;
    
    void releaseConnection(Connection connection) throws SQLException;
}
