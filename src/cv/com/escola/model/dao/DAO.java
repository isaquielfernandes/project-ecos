package cv.com.escola.model.dao;

import com.zaxxer.hikari.HikariDataSource;
import cv.com.escola.model.dao.db.DBProperties;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public abstract class DAO {
    
    protected final JdbcTemplate jdbcTemplate;
    protected ResultSet rs;
    protected PreparedStatement preparedStatement = null;
    protected String db = DBProperties.loadPropertiesDB();
    protected String user = DBProperties.loadPropertiesFileUser();
    protected String pass = DBProperties.loadPropertiesFilePass();
    
    protected DAO() {
        super();
        jdbcTemplate = new JdbcTemplate(dataSource());
    }

    private static HikariDataSource dataSource() {
        return HikariCPDataSource.dataSource();
    }
    
    protected void transact(Consumer<Connection> callback) {
        transact(callback, null);
    }
    
    protected void transact(Consumer<Connection> callback, Consumer<Connection> before) {
        Connection connection = null;
        try {
            connection = getConnection();
            if(before != null) 
                before.accept(connection);
            connection.setAutoCommit(false);
            callback.accept(connection);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            rollBack(connection);
            throw new DataAccessException(e);
        } finally {
            close(connection);
        }
    }

    private static Connection getConnection() {
        return HikariCPDataSource.getConnection();
    }
    
    private void rollBack(Connection connection) throws DataAccessException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        }
    }
    
    protected void close(Connection connection) throws DataAccessException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        }
    }
    
    protected void update(String sql, Object[] params) {
        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    sql
            )) {
                pstmt.setQueryTimeout(1);
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }
    
    protected int update(Connection connection, String sql, Object[] params){
        try {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setQueryTimeout(1);
                for(int idx = 0; idx < params.length; idx++) {
                    statement.setObject(idx + 1, params[idx]);
                }
                return statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException();
        }
    }
    
    protected void executeStatement(Connection connection, String sql) {
        try {
            try (Statement statement = connection.createStatement()) {
                statement.setQueryTimeout(1);
                statement.execute(sql);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    
    protected void remove(String query, Object id) {
        transact((Connection connection) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(
                    query
            )) {
                pstmt.setQueryTimeout(1);
                pstmt.setObject(1, id);
                log.debug(MessageFormat.format("SQL: {0}.", query));
                pstmt.execute();
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        });
    }
    
    protected void read(Consumer<Connection> callback) {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            callback.accept(connection);
            connection.setTransactionIsolation(0);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            close(connection);
        }
    }
    
    protected int count(String sql) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setQueryTimeout(1);
            try (ResultSet resultSet = statement.executeQuery();) {
                if (!resultSet.next()) {
                    throw new IllegalArgumentException("There was no row to be selected!");
                }
                return ((Number) resultSet.getObject(1)).intValue();
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }
    
    protected List<Map<String, Object>> parseResultSet(ResultSet resultSet) {
        List<Map<String, Object>> rows = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                rows.add(row);
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
        return rows;
    }
}
