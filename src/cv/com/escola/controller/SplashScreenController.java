/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv.com.escola.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author Isaquiel Fernandes
 */
public class SplashScreenController implements Initializable {
    
    @FXML
    private Label progress;
    public static Label label;
    @FXML
    private ProgressBar progressBar;
    public static ProgressBar statProgressBar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       label = progress ;
       statProgressBar = progressBar;
    }    
    
}
