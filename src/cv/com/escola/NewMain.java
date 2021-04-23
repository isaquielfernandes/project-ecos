
package cv.com.escola;

import cv.com.escola.model.util.Criptografia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewMain {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(NewMain.class);
    
    public static void main(String[] args) {
        
        LOGGER.info(Criptografia.converter("ecos"));
    }
    
}
