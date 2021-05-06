
package cv.com.escola;

import cv.com.escola.model.util.Criptografia;
import java.text.MessageFormat;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NewMain {
    
    public static void main(String[] args) {
        
        log.info(MessageFormat.format("hash: {0}", Criptografia.converter("test")));
        log.info(MessageFormat.format("hash: {0}", String.format("%07d", 30)));
    }
    
}
