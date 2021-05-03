package cv.com.escola.controller;

import cv.com.escola.model.util.Mensagem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
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
public class RelatoriosGeraisController extends SubMenusConfig implements Initializable {

    private static RelatoriosGeraisController instance;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane anchorPaneRelGeral;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        log.debug("loading ...");
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

    @FXML
    private void onActionFluxoDeCaixa(ActionEvent event) {
        throw new UnsupportedOperationException();
    }

    @FXML
    private void onActionDRE(ActionEvent event) {
        throw new UnsupportedOperationException();
    }
    
}
