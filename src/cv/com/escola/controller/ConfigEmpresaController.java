package cv.com.escola.controller;

import cv.com.escola.model.util.Animacao;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class ConfigEmpresaController extends AnchorPane implements Initializable {

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
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/cv/com/escola/view/configEmpresa.fxml"));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ConfigEmpresaController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela config empresa! \n" + ex);
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
        Modulo.getCargo_Salario(anchorPaneEmpresa);
    }

    @FXML
    private void menuRegistroBenificio(ActionEvent event) {
        Modulo.getListaBenificio(anchorPaneEmpresa);
    }
    
    public AnchorPane getBoxConteudo() {
        return anchorPaneEmpresa;
    }

    /**
     * Exibir e ocultar submenus
     *
     * @param menu
     * @param box
     * @param submenus
     */
    public void submenus(ToggleButton menu, VBox box, ToggleButton... submenus) {
        if (box.getChildren().isEmpty()) {
            box.getChildren().addAll(submenus);
            Animacao.fade(box);
            estilo(menu, "menu-grupo");
        } else {
            desativarSubmenus(box);
            estilo(menu, "menu-grupo-inativo");
        }
    }

    /**
     * Desativar e esconder todos submenus
     *
     * @param boxes
     */
    public void desativarSubmenus(VBox... boxes) {
        for (VBox box : boxes) {
            box.getChildren().clear();
        }
    }

    /**
     * Aplicar estilo para mostrar/ocultar submenus
     *
     * @param no
     * @param estilo
     */
    public void estilo(Node no, String estilo) {
        no.getStyleClass().remove(3);
        no.getStyleClass().add(estilo);
    }
}
