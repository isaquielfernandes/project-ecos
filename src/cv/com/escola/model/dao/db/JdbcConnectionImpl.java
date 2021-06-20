/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv.com.escola.model.dao.db;

import java.sql.Connection;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Isaquiel
 */
@Slf4j
public class JdbcConnectionImpl implements JdbcConnection {

    private final Connection jdbcConnection;
    private final boolean wasInitiallyAutoCommit;

    public JdbcConnectionImpl(Connection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
        boolean wasAutoCommit;
        try {
            wasAutoCommit = jdbcConnection.getAutoCommit();
            if (!wasAutoCommit) {
                jdbcConnection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            wasAutoCommit = false;
        }

        this.wasInitiallyAutoCommit = wasAutoCommit;
    }

    @Override
    public Connection obtainConnection() throws SQLException {
        return jdbcConnection;
    }

    @Override
    public void releaseConnection(Connection connection) throws SQLException {
        if (!wasInitiallyAutoCommit) {
            try {
                if (jdbcConnection.getAutoCommit()) {
                    jdbcConnection.setAutoCommit(false);
                }
            } catch (SQLException e) {
                log.info("Was unable to reset JDBC connection to no longer be in auto-commit mode");
            }
        }
    }

}
