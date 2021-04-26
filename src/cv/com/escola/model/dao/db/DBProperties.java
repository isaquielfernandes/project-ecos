package cv.com.escola.model.dao.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBProperties {

    private static final Logger LOG = Logger.getLogger(DBProperties.class.getName());
    private static final String DATABASE_PROPERTIES = "database.properties";
    private static final Properties PROPERTIES = new Properties();

    private DBProperties() {
        
    }
    
    public static void mkDbProperties() {
        try (OutputStream output = new FileOutputStream(DATABASE_PROPERTIES);) {
            PROPERTIES.setProperty("host", "localhost");
            PROPERTIES.setProperty("port", "3306");
            PROPERTIES.setProperty("db", "dbescola");
            PROPERTIES.setProperty("user", "root");
            PROPERTIES.setProperty("password", "");
            PROPERTIES.store(output, null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBProperties.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            Logger.getLogger(DBProperties.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static String loadPropertiesDB() {
        try {
            loadProperties();
            return PROPERTIES.getProperty("db");
        } catch (IOException e) {
            LOG.log(Level.FINER, e.getMessage());
        }
        return "";
    }

    private static void loadProperties() throws IOException {
        try (InputStream inputStream = new FileInputStream(DATABASE_PROPERTIES);) {
            PROPERTIES.load(inputStream);
        }
    }

    public static String loadPropertiesFileUser() {
        return PROPERTIES.getProperty("user");
    }

    public static String loadPropertiesFilePass() {
        try {
            loadProperties();
            return PROPERTIES.getProperty("password");
        } catch (IOException e) {
            LOG.log(Level.FINER, e.getMessage());
        }
        return "";
    }

    public static String loadPropertiesFileHost() {
        try {
            loadProperties();
            return PROPERTIES.getProperty("host");
        } catch (IOException e) {
            LOG.log(Level.FINER, e.getMessage());
        }
        return "";
    }

    public static String loadPropertiesFilePort() {
        try {
            loadProperties();
            return PROPERTIES.getProperty("port");
        } catch (IOException e) {
            LOG.log(Level.FINER, e.getMessage());
        }
        return "";
    }

}
