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

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.util.Mensagem;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class DashVendaController extends AnchorPane implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Label lbVendasNoAno;
    @FXML
    private Label lbTotalDeExame1;
    @FXML
    private Label lbVendasNoMes;
    @FXML
    private Label lbTotalDeExame11;
    @FXML
    private Label lbTickedMedio;
    @FXML
    private Label lbTotalDeExame12;
    @FXML
    private Label lbTotalCurso;
    @FXML
    private Label lbTotalDeExame13;
    @FXML
    private Label lbTotalFornecedor;
    @FXML
    private Label lbTotalDeExame14;
    @FXML
    private Label lbTotalCliente;
    @FXML
    private Label lbTotalDeExame15;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String ano = String.valueOf(LocalDate.now().getYear());
        lbVendasNoAno.setText(DAOFactory.daoFactury().orderDAO().totalAnual(ano) + "$00");
    }    
    @SuppressWarnings({"LeakingThisInConstructor", "CallToPrintStackTrace"})
    public DashVendaController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/cv/com/escola/view/dashVenda.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela dashboard venda! \n" + ex);
            ex.printStackTrace();
        }
    }
}
