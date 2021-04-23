package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Benificio;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Filtro;
import cv.com.escola.model.util.Grupo;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import cv.com.escola.model.util.Nota;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class ListaBenificiosController extends AnchorPane implements Initializable {

    private List<Benificio> listaBenificios;
    private int idBenificio;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private ToggleGroup menu;
    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextField txtBenificio;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private ListView<Benificio> listaBenificio;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableView<Benificio> tbBenificios;
    @FXML
    private TableColumn<Benificio, Integer> colId;
    @FXML
    private TableColumn<Benificio, String> colBenificios;
    @FXML
    private TableColumn<Benificio, String> colDescricao;
    @FXML
    private Button btSalvar;
    @FXML
    private Button btEditar;
    @FXML
    private Button btExcluir;
    @FXML
    private Label legenda;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarBase();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> 
            filtro(novo, FXCollections.observableArrayList(listaBenificios))
        );
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public ListaBenificiosController() {
        try {
            GenericFXXMLLoader.loadFXML(this, "listaBenificios");
        } catch (IOException ex) {
            Logger.getLogger(ListaBenificiosController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela Benificio! \n" + ex);
        }
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaBenificios = DAOFactory.daoFactury().benificioDAO().findAll();
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbBenificios.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idBenificio = 0;
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limparCampos() {
        Campo.limpar(this.txtBenificio);
        Campo.limpar(txtDescricao);
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Benificio", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampos();
        listView();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("Editar Benificio", "Quantidade de benificuios encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Excluir Benificio", "Quantidade de benificios encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean vazio = Campo.noEmpty(this.txtBenificio);

        String benificio = txtBenificio.getText();
        String descricao = txtDescricao.getText();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else if (DAOFactory.daoFactury().benificioDAO().isBanificio(benificio, idBenificio)) {
            Nota.alerta("Benificio já cadastrada!");
        } else {
            Benificio aux = new Benificio(idBenificio, benificio, descricao);

            if (idBenificio == 0) {
                DAOFactory.daoFactury().benificioDAO().create(aux);
                Mensagem.info("Despesa cadastrada com sucesso!");
            } else {
                DAOFactory.daoFactury().benificioDAO().update(aux);
                Mensagem.info("Despesa atualizada com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            Benificio benificio = tbBenificios.getSelectionModel().getSelectedItem();
            benificio.getClass();

            telaCadastro(null);

            txtBenificio.setText(benificio.getNomeBenificio());
            txtDescricao.setText(benificio.getDescricao());

            lbTitulo.setText("Editar Despesa");
            menu.selectToggle(menu.getToggles().get(1));

            idBenificio = benificio.getIdBenificio();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione uma benificio na tabela para edição!");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        try {
            Benificio benificio = tbBenificios.getSelectionModel().getSelectedItem();

            Dialogo.Resposta response = Mensagem.confirmar("Excluir benificio " + benificio.getNomeBenificio() + " ?");

            if (response == Dialogo.Resposta.YES) {
                DAOFactory.daoFactury().benificioDAO().delete(benificio.getIdBenificio());
                sincronizarBase();
                tabela();
            }

            tbBenificios.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione benificio na tabela para exclusão!");
        }
    }

    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaBenificios);

        colId.setCellValueFactory((TableColumn.CellDataFeatures<Benificio, Integer> obj) -> obj.getValue().idBenificioProperty().asObject());
        colBenificios.setCellValueFactory((TableColumn.CellDataFeatures<Benificio, String> obj) -> obj.getValue().nomeBenificioProperty());
        colDescricao.setCellValueFactory((TableColumn.CellDataFeatures<Benificio, String> obj) -> obj.getValue().descricaoProperty());

        tbBenificios.setItems(data);
    }
    
    private void listView() {
        ObservableList data = FXCollections.observableArrayList(DAOFactory.daoFactury().benificioDAO().combo());
        listaBenificio.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtro(String valor, ObservableList<Benificio> listaBenificios) {
        FilteredList<Benificio> dadosFiltrados = new FilteredList<>(listaBenificios, benificio -> true);
        dadosFiltrados.setPredicate(benificio -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (benificio.getNomeBenificio().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }
            return false;
        });

        SortedList<Benificio> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbBenificios.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de benificio encontradas");

        tbBenificios.setItems(dadosOrdenados);
    }

}
