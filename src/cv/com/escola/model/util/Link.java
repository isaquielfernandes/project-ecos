package cv.com.escola.model.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Isaquiel Fernandes
 */
public class Link {
     private Link() {
    }

    /**
     * Auxilia exibição de endereços web no desktop padrão
     * @param link
     */
    public static void endereco(String link) {
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (URISyntaxException | IOException ex) {
            Mensagem.erro("Não possível exibir www.fapce.edu.br !");
        }
    }
}
