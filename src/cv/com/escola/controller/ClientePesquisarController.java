package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Cliente;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    private Stage dialogStage = null;
    private boolean buttonConfirmarClicked = false;
    private Cliente cliente = null;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        sincronizarBase();
        viewAllCliente();
        txtBuscarCliente.textProperty().addListener((obs, old, novo) -> 
            filtros(novo, FXCollections.observableArrayList(listaCliente)));
        
        tbCliente.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> listViewClienteSelected(newValue));
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaCliente = DAOFactory.daoFactory().clienteDAO().findAll();
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtros(String valor, ObservableList<Cliente> listaCliente) {
        FilteredList<Cliente> dadosFiltrados = new FilteredList<>(listaCliente, clientes -> true);
        dadosFiltrados.setPredicate(clientes -> 
            clientes.getNomeCliente().toLowerCase().startsWith(valor.toLowerCase())
        );
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
            listViewClienteSelected(tbCliente.getSelectionModel().getSelectedItem());
            buttonConfirmarClicked = true;
            dialogStage.close();

        }
    }

    // Setando dados do combobox no caixa de texto
    public void listViewClienteSelected(Cliente clienteSelected) {
        if (clienteSelected != null) {
            cbCliente.setValue(clienteSelected);
        } else {
            cbCliente.setValue(null);
        }
    }
}
