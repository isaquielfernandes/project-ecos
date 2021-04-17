
package cv.com.escola;

import cv.com.escola.model.util.Criptografia;
import lombok.extern.slf4j.Slf4j;

 @Slf4j
public class NewMain {

    public static void main(String[] args) {
        log.debug(Criptografia.converter("admin"));
    }
    
}
