
package cv.com.escola;

import cv.com.escola.model.util.Criptografia;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewMain {

    private static final Logger LOG = Logger.getLogger(NewMain.class.getName());
    public static void main(String[] args) {
        
        LOG.log(Level.INFO, Criptografia.converter("ecos"));
    }
    
}
