package cv.com.escola.model.dao;

import cv.com.escola.model.dao.db.ConnectionManager;
import cv.com.escola.model.dao.db.DBProperties;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class DAO {

    protected Connection conector = ConnectionManager.getInstance().begin();
    protected ResultSet rs;
    protected PreparedStatement stm;
    protected DBProperties dBProperties = new DBProperties();
    protected String db = dBProperties.loadPropertiesFile();
    protected String user = dBProperties.loadPropertiesFileUser();
    protected String pass = dBProperties.loadPropertiesFilePass();

    public DAO() {
        
    }

}
