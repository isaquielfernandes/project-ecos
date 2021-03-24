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
import cv.com.escola.model.entity.Cliente;
import cv.com.escola.model.util.Nota;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class ClientePesquisarController implements Initializable {

    @FXML
    private TextField txtBuscarCliente;
    @FXML
    private ComboBox<Cliente> cbCliente;
    @FXML
    private TableView<Cliente> tbCliente;
    @FXML
    private TableColumn<Cliente, Integer> colClienteId;
    @FXML
    private TableColumn<Cliente, String> colClienteNome;
    private List<Cliente> listaCliente;
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Cliente cliente;
    private final Screen screen = Screen.getPrimary();
    private final Rectangle2D windows = screen.getVisualBounds();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sincronizarBase();
        viewAllCliente();
        txtBuscarCliente.textProperty().addListener((obs, old, novo) -> {
            filtros(novo, FXCollections.observableArrayList(listaCliente));
        });
        tbCliente.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> listViewClienteSelected(newValue));
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaCliente = DAOFactory.daoFactury().clienteDAO().findAll();
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtros(String valor, ObservableList<Cliente> listaCliente) {

        FilteredList<Cliente> dadosFiltrados = new FilteredList<>(listaCliente, clientes -> true);
        dadosFiltrados.setPredicate(clientes -> {
            if (clientes == null || valor.isEmpty()) {
                return true;
            } else if (clientes.getNomeCliente().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }
            return false; //To change body of generated lambdas, choose Tools | Templates.

        });

        SortedList<Cliente> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbCliente.comparatorProperty());
        tbCliente.setItems(dadosOrdenados);
    }

    @FXML
    private void adcionarClientes(ActionEvent event) {

        cliente = tbCliente.getSelectionModel().getSelectedItem();
        buttonConfirmarClicked = true;
        dialogStage.close();
    }

    // setando dados de item na tableView
    public void viewAllCliente() {
        ObservableList dadosCliente = FXCollections.observableArrayList(listaCliente);
        colClienteId.setCellValueFactory((TableColumn.CellDataFeatures<Cliente, Integer> obj) -> obj.getValue().idClienteProperty().asObject());
        colClienteNome.setCellValueFactory((TableColumn.CellDataFeatures<Cliente, String> p) -> new ReadOnlyObjectWrapper(p.getValue().nomeClienteProperty().get()));

        tbCliente.setItems(dadosCliente);
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
    }
    
    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    private void onMouseClickedTbCliente(MouseEvent event) {
        if (event.getClickCount() == 2) {
            listViewClienteSelected(cliente);
            buttonConfirmarClicked = true;
            dialogStage.close();

        }
    }

    // Setando dados do combobox no caixa de texto
    public void listViewClienteSelected(Cliente cliente) {
        cliente = tbCliente.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            cbCliente.setValue(cliente);
        } else {
            cbCliente.setValue(null);
        }
    }
}
