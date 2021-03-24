/*
 * Copyright (C) 2019 Isaquiel Fernandes.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
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
    private AnchorPane AnchorPane;
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
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/cv/com/escola/view/relatoriosGerais.fxml"));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(RelatoriosGeraisController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela Relatorio Gerais! \n" + ex);
        }
    }
    /**
     * Obter instancia do controler
     *
     * @return
     */
    public static RelatoriosGeraisController getInstance() {
        return instance;
    }
    /**
     * Obter componente para exibição dos modulos da aplicação
     *
     * @return
     */
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
    }

    @FXML
    private void onActionDRE(ActionEvent event) {
    }
    
}
