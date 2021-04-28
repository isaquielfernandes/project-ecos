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
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class MenuVeiculoController extends SubMenusConfig implements Initializable {

    private static MenuVeiculoController instance;
    @FXML
    private Label lbTitulo;
    @FXML
    private ToggleGroup menuPlano;
    @FXML
    private AnchorPane boxMenuVeiculo;

    @FXML
    private void btTelaInspecaoTecnica(ActionEvent event) {
        Modulo.getInspeccaoTecnica(boxMenuVeiculo); 
    }

    @FXML
    private void btTelaSeguro(ActionEvent event) {
        Modulo.getSeguro(boxMenuVeiculo);
    }

    @FXML
    private void btTelaVeiculo(ActionEvent event) {
        Modulo.getVeiculo(boxMenuVeiculo);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btTelaVeiculo(null);
    }    

    @SuppressWarnings("LeakingThisInConstructor")
    public MenuVeiculoController() {
        try {
            GenericFXXMLLoader.loadFXML(this, "menuVeiculo");
        } catch (IOException ex) {
            Logger.getLogger(MenuVeiculoController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela menu veículo");
        }
    }
    
    /**
     * Obter componente para exibição dos modulos da aplicação
     *
     * @return
     */
    public AnchorPane getBoxConteudo() {
        return boxMenuVeiculo;
    }

    public static MenuVeiculoController getInstance() {
        return instance;
    }  
    
}
