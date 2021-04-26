package cv.com.escola.controller;

import cv.com.escola.model.util.Mensagem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
@Slf4j
public class HomeController extends AnchorPane implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       log.debug("iniciando ...");
    }    
    @SuppressWarnings("LeakingThisInConstructor")
    public HomeController(){
        try {
           GenericFXXMLLoader.loadFXML(this, "home");
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("erro ao caregar tela home");
        }
    }
}
