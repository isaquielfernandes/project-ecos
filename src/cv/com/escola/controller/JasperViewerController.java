/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv.com.escola.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class JasperViewerController extends AnchorPane implements Initializable {

    @FXML
    private Button print;
    @FXML
    private Button save;
    @FXML
    private Button backPage;
    @FXML
    private Button firstPage;
    @FXML
    private TextField txtPage;
    @FXML
    private Label lblReportPages;
    @FXML
    private Button nextPage;
    @FXML
    private Button lastPage;
    @FXML
    private Button zoomOut;
    @FXML
    private Button zoomIn;
    @FXML
    private ImageView report;
    

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
    public JasperViewerController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/cv/com/escola/view/jasperViewer.fxml"));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(JasperViewerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
