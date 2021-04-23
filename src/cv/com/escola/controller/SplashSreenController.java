package cv.com.escola.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class SplashSreenController implements Initializable {

    @FXML
    private ProgressBar progresso;
    @FXML
    private Label txtComponente;
    SplashSreenController sSc = this;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public SplashSreenController() {
        this.progresso.setCursor(Cursor.WAIT);
        this.startThread();
    }
    
    public void startThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        thread.start();
    }

    public ProgressBar getProgresso() {
        return progresso;
    }

    public void setProgresso(ProgressBar progresso) {
        this.progresso = progresso;
    }

    public Label getTxtComponente() {
        return txtComponente;
    }

    public void setTxtComponente(Label txtComponente) {
        this.txtComponente = txtComponente;
    }
    
}
