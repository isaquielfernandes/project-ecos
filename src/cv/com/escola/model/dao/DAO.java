package cv.com.escola.model.dao;

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


@Slf4j
public abstract class DAO {

    protected Connection connect = null;
    protected ResultSet rs;
    protected PreparedStatement preparedStatement = null;
    protected String db = DBProperties.loadPropertiesDB();
    protected String user = DBProperties.loadPropertiesFileUser();
    protected String pass = DBProperties.loadPropertiesFilePass();
    
    public DAO() {

    }

    protected void transact(Consumer<Connection> callback) {
        Connection connection = null;
        try {
            connection = HikariCPDataSource.getInstance().getConnection();
            callback.accept(connection);
            connection.commit();
        } catch (SQLException e) {
            rollBack(connection);
            throw new DataAccessException(e);
        } finally {
            close(connection);
        }
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

    protected void close(Connection connection) throws DataAccessException{
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        }
    }

    protected int update(Connection connection, String sql, Object[] params) {
        try {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setQueryTimeout(1);
                for(int i=0; i < params.length; i++)
                    statement.setObject(i + 1, params[i]);
                return statement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }
    
    protected void executeStatement(Connection connection, String sql) {
        try {
            try(Statement statement = connection.createStatement()) {
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
    
    protected int count(Connection connection, String sql) {
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setQueryTimeout(1);
            try (ResultSet resultSet = statement.executeQuery();) {
                if(!resultSet.next())
                    throw new IllegalArgumentException("There was no row to be selected!");
                return ((Number) resultSet.getObject(1)).intValue();
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }
    
    protected List<Map<String, String>> parseResultSet(ResultSet resultSet) {
        List<Map<String, String>> rows = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, String> row = new LinkedHashMap<>();
                for (int i = 0; i < columnCount; i++)
                    row.put(metaData.getColumnName(i), resultSet.getString(i));
                rows.add(row);
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
        return rows;
    }
}
