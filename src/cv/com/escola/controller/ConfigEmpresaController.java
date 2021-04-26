package cv.com.escola.controller;

import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;


public class ConfigEmpresaController extends SubMenusConfig implements Initializable {

    private static ConfigEmpresaController instance;
    @FXML
    private ToggleGroup grupoEmpresa;
    @FXML
    private AnchorPane anchorPaneEmpresa;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuConfigEmpresa(null);
    }  
    
    @SuppressWarnings("LeakingThisInConstructor")
    public ConfigEmpresaController(){
        try {
            GenericFXXMLLoader.loadFXML(this, "configEmpresa");
        } catch (IOException ex) {
            Logger.getLogger(ConfigEmpresaController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela config empresa!");
        }
    }

    public static ConfigEmpresaController getInstance() {
        return instance;
    }

    @FXML
    private void menuConfigEmpresa(ActionEvent event) {
        Modulo.getEmpresa(anchorPaneEmpresa);
    }

    @FXML
    private void menuRegistroCargoSalario(ActionEvent event) {
        Modulo.getCargoSalario(anchorPaneEmpresa);
    }

    @FXML
    private void menuRegistroBenificio(ActionEvent event) {
        Modulo.getListaBenificio(anchorPaneEmpresa);
    }
    
    public AnchorPane getBoxConteudo() {
        return anchorPaneEmpresa;
    }

}
