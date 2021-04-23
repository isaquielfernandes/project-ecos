package cv.com.escola.controller;

import cv.com.escola.model.util.Animacao;
import cv.com.escola.model.util.Mensagem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class RelatoriosGeraisController extends AnchorPane implements Initializable {

    private static RelatoriosGeraisController instance;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane anchorPaneRelGeral;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @SuppressWarnings("LeakingThisInConstructor")
    public RelatoriosGeraisController(){
        try {
            GenericFXXMLLoader.loadFXML(this, "relatoriosGerais");
        } catch (IOException ex) {
            Logger.getLogger(RelatoriosGeraisController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela Relatorio Gerais");
        }
    }
  
    public static RelatoriosGeraisController getInstance() {
        return instance;
    }
   
    public AnchorPane getBoxConteudo() {
        return anchorPaneRelGeral;
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

    @FXML
    private void onActionFluxoDeCaixa(ActionEvent event) {
        throw new UnsupportedOperationException();
    }

    @FXML
    private void onActionDRE(ActionEvent event) {
        throw new UnsupportedOperationException();
    }
    
}
