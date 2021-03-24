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
    
    private static final Properties PROPERTIES = new Properties();
    private static InputStream inputStream;
    private static OutputStream output = null;

    public static void mkDbProperties() {
        try {
            output = new FileOutputStream("database.properties");
            PROPERTIES.setProperty("host", "localhost");
            PROPERTIES.setProperty("port", "3306");
            PROPERTIES.setProperty("db", "dbescola");
            PROPERTIES.setProperty("user", "root");
            PROPERTIES.setProperty("password", "");
            PROPERTIES.store(output, null);
            output.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBProperties.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String loadPropertiesFile() {
        try {
            inputStream = new FileInputStream("database.properties");
            PROPERTIES.load(inputStream);
            return PROPERTIES.getProperty("db");
        } catch (IOException e) {
            System.out.println("err");
        }
        return "";
    }
    
    public String loadPropertiesFileUser() {
        try {
            inputStream = new FileInputStream("database.properties");
            PROPERTIES.load(inputStream);
            return PROPERTIES.getProperty("user");
        } catch (IOException e) {
            System.out.println("err");
        }
        return "";
    }
    
    public String loadPropertiesFilePass() {
        try {
            inputStream = new FileInputStream("database.properties");
            PROPERTIES.load(inputStream);
            return PROPERTIES.getProperty("password");
        } catch (IOException e) {
            System.out.println("err");
        }
        return "";
    }
    
    public String loadPropertiesFileHost() {
        try {
            inputStream = new FileInputStream("database.properties");
            PROPERTIES.load(inputStream);
            return PROPERTIES.getProperty("host");
        } catch (IOException e) {
            System.out.println("err");
        }
        return "";
    }
    
    public String loadPropertiesFilePort() {
        try {
            inputStream = new FileInputStream("database.properties");
            PROPERTIES.load(inputStream);
            return PROPERTIES.getProperty("port");
        } catch (IOException e) {
            System.out.println("err");
        }
        return "";
    }
    
}
