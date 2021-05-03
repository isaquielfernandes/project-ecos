
package cv.com.escola;

import cv.com.escola.model.util.Criptografia;
import java.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewMain.class);
    
    public static void main(String[] args) {
        
        LOGGER.debug(MessageFormat.format("hash: {0}", Criptografia.converter("test")));
    }
    
}
