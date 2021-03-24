package cv.com.escola.model.dao.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadProperties {
    
    protected Properties properties = new Properties();
    protected File file;
    protected InputStream inputStream;
    protected String url;
    protected String user;
    protected String pass;

    public LoadProperties() {
    }

    public void loadPropertiesFile() {
        try {
            file = new File("database.properties");
            if (file.exists()) {
                inputStream = new FileInputStream(file);
                properties.load(inputStream);
                url = "jdbc:mysql://" + properties.getProperty("host") + ":" + properties.getProperty("port") + "/";
                user = properties.getProperty("user");
                pass = properties.getProperty("password");
            } else {
                DBProperties.mkDbProperties();
                inputStream = new FileInputStream(file);
                properties.load(inputStream);
                url = "jdbc:mysql://" + properties.getProperty("host") + ":" + properties.getProperty("port") + "/";
                user = properties.getProperty("user");
                pass = properties.getProperty("password");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
