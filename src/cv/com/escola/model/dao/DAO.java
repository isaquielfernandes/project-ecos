package cv.com.escola.model.dao;

import cv.com.escola.model.dao.db.DBProperties;
import cv.com.escola.model.dao.db.HikariCPDataSource;
import cv.com.escola.model.dao.exception.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DAO {
    
    static {
        Thread.currentThread().setName("MAIN: ");
    }
    
    protected static final Logger LOG = Logger.getLogger(DAO.class.getName());
    protected ResultSet rs;
    protected PreparedStatement preparedStatement;
    protected String db = DBProperties.loadPropertiesDB();
    protected String user = DBProperties.loadPropertiesFileUser();
    protected String pass = DBProperties.loadPropertiesFilePass();
    
    public DAO() {

    }

    @SuppressWarnings("UseSpecificCatch")
    protected void transact(Consumer<Connection> callback) {
        Connection connection = null;
        try {
            connection = HikariCPDataSource.getInstance().getConnection();
            callback.accept(connection);
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DataAccessException(ex);
                }
            }
            throw (e instanceof DataAccessException
                    ? (DataAccessException) e : new DataAccessException(e));
        } finally {
            close(connection);
        }
    }

    private void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.log(Level.FINER, e.getMessage());
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
    
    protected boolean delete(String sql, Object id) {
        try (Connection connection = HikariCPDataSource.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setQueryTimeout(1);
                statement.setObject(1, id);
                return statement.execute();
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }
    
    protected int count(Connection connection, String sql) {
        try {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setQueryTimeout(1);
                ResultSet resultSet = statement.executeQuery();
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
