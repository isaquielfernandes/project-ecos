/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv.com.escola.controller;

import cv.com.escola.model.util.Mensagem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Isaquiel Fernandes
 */
public class AnaliseController extends AnchorPane implements Initializable{

    
    @SuppressWarnings("LeakingThisInConstructor")
    public AnaliseController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/cv/com/escola/view/analise.fxml"));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(AnaliseController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("erro ao caregar a tela analise, \n" + ex);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
}
