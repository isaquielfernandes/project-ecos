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

import cv.com.escola.model.entity.Cliente;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Combo;
import static cv.com.escola.model.util.ValidationFields.checkEmptyFields;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class ClienteDialogController implements Initializable {

    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtNumBI;
    @FXML
    private TextField txtContacto;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtEndereco;
    @FXML
    private TextField txtCodigoPostal;
    @FXML
    private TextField txtLocalidade;
    @FXML
    private RadioButton rbFisica;
    @FXML
    private ToggleGroup pessoa;
    @FXML
    private RadioButton rbJuridica;
    @FXML
    private ComboBox cbTipoCliente;
    @FXML
    private Label lbMensagen;
    @FXML
    private Label lbClose;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Cliente cliente;
    private String radioButton;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        combos();
        lbClose.setOnMouseClicked((event) -> {
            dialogStage.close();
        });
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        this.txtNome.setText(cliente.getNomeCliente());
        this.txtNumBI.setText(cliente.getNif());
        this.txtContacto.setText(cliente.getContato());
        this.txtDescricao.setText(cliente.getDescricao());
        this.cbTipoCliente.setValue(cliente.getTipoCliente());
        
        this.txtCodigoPostal.setText(cliente.getCodigoPostal());
        this.txtLocalidade.setText(cliente.getLocalidade());
        this.txtEndereco.setText(cliente.getEndereco());
        //this.txtID.setText(cliente.getIdDoCliente());
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean emptyFields = checkEmptyFields(txtNome, txtNumBI, cbTipoCliente);
        if(emptyFields){
            cliente.setNomeCliente(txtNome.getText());
            cliente.setContato(txtContacto.getText());
            cliente.setDescricao(txtDescricao.getText());
            cliente.setLocalidade(txtLocalidade.getText());
            cliente.setEndereco(txtEndereco.getText());
            cliente.setNif(txtNumBI.getText());
            cliente.setCodigoPostal(txtCodigoPostal.getText());
            cliente.setTipoCliente(cbTipoCliente.getValue().toString());
            
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }
    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limparCampos() {
        Campo.limpar(txtNumBI, txtNome, txtContacto, txtCodigoPostal, txtEndereco, txtLocalidade, txtID);
        Campo.limpar(txtDescricao);
    }
    /**
     * Preencher combos tela
     */
    private void combos() {
        Combo.popular(cbTipoCliente,"Fisica", "Juridica");
    }
}
