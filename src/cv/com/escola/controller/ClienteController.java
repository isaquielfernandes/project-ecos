package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Cliente;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Combo;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Filtro;
import cv.com.escola.model.util.Grupo;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import cv.com.escola.model.util.Nota;
import static cv.com.escola.model.util.ValidationFields.checkEmptyFields;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import net.sf.jasperreports.engine.JRException;


public class ClienteController extends AnchorPane implements Initializable {

    private List<Cliente> listaCliente;
    private int idCliente = 0;
    private Desktop desktop = Desktop.getDesktop();

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private ToggleButton btAdcionar;
    @FXML
    private ToggleGroup menu;
    @FXML
    private ToggleButton btEdit;
    @FXML
    private ToggleButton btApagar;
    @FXML
    private ToggleButton btImpr;
    @FXML
    private ToggleButton btAtualizar;
    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtNumBI;
    @FXML
    private ComboBox<String> cbTipoCliente;
    @FXML
    private TextField txtContacto;
    @FXML
    private TextField txtLocalidade;
    @FXML
    private TextField txtCodigoPostal;
    @FXML
    private TextField txtEndereco;
    @FXML
    private TextField txtID;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableView<Cliente> tbCliente;
    @FXML
    private TableColumn<Cliente, Integer> colCodigo;
    @FXML
    private TableColumn<Cliente, String> colTipoCliente;
    @FXML
    private TableColumn<Cliente, String> colNome;
    @FXML
    private TableColumn<Cliente, String> colContato;
    @FXML
    private TableColumn<Cliente, String> colCNI;
    @FXML
    private TableColumn<Cliente, String> colDescricao;
    @FXML
    private TableColumn<Cliente, Button> colAction;
    private TableColumn<Cliente, Button> colEditar;
    @FXML
    private Button btSalvar;
    @FXML
    private Button btEditar;
    @FXML
    private Button btExcluir;
    @FXML
    private Button btImprimir;
    @FXML
    private Label legenda;

    @SuppressWarnings("LeakingThisInConstructor")
    public ClienteController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/cv/com/escola/view/cliente.fxml"));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela cliente! \n" + ex);
        }
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Cliente", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampos();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("Editar Cliente", "Quantidade de clientes encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        sincronizarBase();
        tabela();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Excluir Cliente", "Quantidade de Clientes encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaImprimir(ActionEvent event) {
        configTela("Imprimir dados do Cliente", "Cliente", 3);
        Modulo.visualizacao(true, telaEdicao, btImprimir, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaAtualizar(MouseEvent event) {
        sincronizarBase();
        tabela();
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean emptyFields = checkEmptyFields(txtNome, cbTipoCliente);

        String idDoCliente = txtID.getText();
        String nome = txtNome.getText();
        String nif = txtNumBI.getText();
        String contato = txtContacto.getText();
        String descricao = txtDescricao.getText();
        String endereco = txtEndereco.getText();
        String codigoPostal = txtCodigoPostal.getText();
        String localidade = txtLocalidade.getText();
        String tipoCliente = cbTipoCliente.getValue();

        if (emptyFields) {
            Cliente cliente = new Cliente(idCliente, nome, nif, contato, tipoCliente, descricao, endereco, codigoPostal, localidade);

            if (idCliente == 0) {
                DAOFactory.daoFactury().clienteDAO().create(cliente);
            } else {
                DAOFactory.daoFactury().clienteDAO().update(cliente);
            }
            telaCadastro(null);
            sincronizarBase();
        }

    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            Cliente cliente = tbCliente.getSelectionModel().getSelectedItem();
            cliente.getClass(); 
            telaCadastro(null);

            txtNome.setText(cliente.getNomeCliente());
            txtNumBI.setText(cliente.getNif());
            txtContacto.setText(cliente.getContato());
            txtDescricao.setText(cliente.getDescricao());
            cbTipoCliente.setValue(cliente.getTipoCliente());
            txtCodigoPostal.setText(cliente.getCodigoPostal());
            txtLocalidade.setText(cliente.getLocalidade());
            txtEndereco.setText(cliente.getEndereco());
            //txtID.setText(cliente.getIdDoCliente());

            lbTitulo.setText("Editar Cliente");
            menu.selectToggle(menu.getToggles().get(1));

            idCliente = cliente.getIdCliente();
        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um cliente na tabela para edição!");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        try {
            Cliente cliente = tbCliente.getSelectionModel().getSelectedItem();

            Dialogo.Resposta response = Mensagem.confirmar("Excluir Cliente:: " + cliente.getNomeCliente()+ "?");

            if (response == Dialogo.Resposta.YES) {
                DAOFactory.daoFactury().clienteDAO().delete(cliente.getIdCliente());
                sincronizarBase();
                tabela();
            }
            tbCliente.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione cliente na tabela para exclusão!");
        }
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {

        ObservableList data = FXCollections.observableArrayList(listaCliente);

        colCodigo.setCellValueFactory((CellDataFeatures<Cliente, Integer> obj) -> obj.getValue().idClienteProperty().asObject());
        colNome.setCellValueFactory((CellDataFeatures<Cliente, String> obj) -> obj.getValue().nomeClienteProperty());
        colContato.setCellValueFactory((CellDataFeatures<Cliente, String> obj) -> obj.getValue().contatoProperty());
        colTipoCliente.setCellValueFactory((CellDataFeatures<Cliente, String> obj) -> obj.getValue().tipoClienteProperty());
        colCNI.setCellValueFactory((CellDataFeatures<Cliente, String> obj) -> obj.getValue().numCNIProperty());
        colDescricao.setCellValueFactory((CellDataFeatures<Cliente, String> obj) -> obj.getValue().descricaoProperty());
        //colAction.setCellValueFactory(new PropertyValueFactory<>("remove"));
        //colEditar.setCellValueFactory(new PropertyValueFactory<>("editar"));

        tbCliente.setItems(data);
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaCliente = DAOFactory.daoFactury().clienteDAO().findAll();
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, btImprimir, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbCliente.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idCliente = 0;
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limparCampos() {
        Campo.limpar(txtNumBI, txtNome, txtContacto, txtCodigoPostal, txtEndereco, txtLocalidade, txtID);
        Campo.limpar(txtDescricao);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        telaCadastro(null);
        Grupo.notEmpty(menu);
        sincronizarBase();
        combos();
        
        colEditar = new TableColumn();
        colEditar.setMinWidth(100);
        colEditar.setMaxWidth(100);
        tbCliente.getColumns().add(colEditar);
        
        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaCliente));
        });
        btAtualizar.setTooltip(new Tooltip("Atualizar registros"));
        btAdcionar.setTooltip(new Tooltip("Adicionar registros"));
        btApagar.setTooltip(new Tooltip("Apagar registros"));
        btImpr.setTooltip(new Tooltip("Imprimir registros"));
        btEdit.setTooltip(new Tooltip("Editar registros"));

    }
    /**
     * Preencher combos tela
     */
    private void combos() {
        Combo.popular(cbTipoCliente,"Fisica", "Juridica");
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtro(String valor, ObservableList<Cliente> listaAluno) {

        FilteredList<Cliente> dadosFiltrados = new FilteredList<>(listaAluno, cliente -> true);
        dadosFiltrados.setPredicate(cliente -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (cliente.getNomeCliente().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (cliente.getNif().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (cliente.getContato().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }

            return false;
        });

        SortedList<Cliente> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbCliente.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de cliente encontradas");

        tbCliente.setItems(dadosOrdenados);
    }

    @FXML
    private void imprimir(ActionEvent event) throws JRException {

        try {
            Cliente cliente = tbCliente.getSelectionModel().getSelectedItem();
            Dialogo.Resposta response = Mensagem.confirmar("Imprimir Requiremento para:: " + cliente.getNomeCliente()+ " ?");
            if (response == Dialogo.Resposta.YES) {
                //ControleDAO.getBanco().getClienteDAO().reportRequiremento(cliente.getNumBI());
            }
            tbCliente.getSelectionModel().clearSelection();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione aluno na tabela para imprimir o requiremento!");
        }
    }

}
