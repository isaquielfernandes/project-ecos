package cv.com.escola.model.dao.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadProperties {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadProperties.class);

    protected Properties properties;
    protected File file;
    protected String url;
    protected String user;
    protected String pass;

    public LoadProperties() {
        properties = new Properties();
    }

    public void loadPropertiesFile() {
        file = new File("database.properties");
        try (InputStream inputStream = new FileInputStream(file);) {
            if (file.exists()) {
                properties.load(inputStream);
                url = "jdbc:mysql://" + properties.getProperty("host") + ":" + properties.getProperty("port") + "/";
                user = properties.getProperty("user");
                pass = properties.getProperty("password");
            } else {
                DBProperties.mkDbProperties();
                properties.load(inputStream);
                url = "jdbc:mysql://" + properties.getProperty("host") + ":" + properties.getProperty("port") + "/";
                user = properties.getProperty("user");
                pass = properties.getProperty("password");
            }
        } catch (FileNotFoundException ex) {
           LOGGER.error(ex.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    
}
