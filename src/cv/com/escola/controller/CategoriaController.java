package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Categoria;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class CategoriaController extends AnchorPane implements Initializable {

    private List<Categoria> listaCategoria;
    private int idCategoria = 0;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private ToggleGroup menu;
    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextField txtCategoria;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableView<Categoria> tbCategoria;
    @FXML
    private TableColumn<Categoria, Integer> colId;
    @FXML
    private TableColumn<Categoria, String> colCategoria;
    @FXML
    private TableColumn<Categoria, String> colDescricao;
    @FXML
    private Button btSalvar;
    @FXML
    private Button btEditar;
    @FXML
    private Button btExcluir;
    @FXML
    private Label legenda;

    @SuppressWarnings("LeakingThisInConstructor")
    public CategoriaController() {
        try {
            GenericFXXMLLoader.loadFXML(this, "categoria");
        } catch (IOException ex) {
            Logger.getLogger(CategoriaController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela categoria!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtCategoria.setOnKeyReleased(key -> {
            if (key.getCode() == KeyCode.ENTER) {
                salvar(null);
            }
        });
        txtDescricao.setOnKeyReleased(key -> {
            if (key.getCode() == KeyCode.ENTER) {
                salvar(null);
            }
        });
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarBase();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> 
                filtroPesquisa(novo, FXCollections.observableArrayList(listaCategoria)));
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Categoria", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampos();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("Editar Categoria", "Quantidade de categorias encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Excluir Categoria", "Quantidade de categoria encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean vazio = Campo.noEmpty(this.txtCategoria);

        String categ = txtCategoria.getText();
        String descricao = txtDescricao.getText();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else {
            Categoria categoria = new Categoria(idCategoria, categ, descricao);

            if (idCategoria == 0) {
                DAOFactory.daoFactury().categoriaDAO().create(categoria);
                Mensagem.info("Categoria cadastrado com sucesso!");
            } else {
                DAOFactory.daoFactury().categoriaDAO().update(categoria);
                Mensagem.info("Categoria atualizado com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            Categoria categ = tbCategoria.getSelectionModel().getSelectedItem();
            categ.getClass();

            telaCadastro(null);

            txtCategoria.setText(categ.getNome());
            txtDescricao.setText(categ.getDescricao());

            lbTitulo.setText("Editar Categoria");
            menu.selectToggle(menu.getToggles().get(1));

            idCategoria = categ.getId();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione uma categoria na tabela para edição!");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        try {
            Categoria categoria = this.tbCategoria.getSelectionModel().getSelectedItem();
            Dialogo.Resposta responta = Mensagem.confirmar("Excluir Categoria " + categoria.getNome());
            if (responta == Dialogo.Resposta.YES) {
                DAOFactory.daoFactury().categoriaDAO().delete(categoria.getId());
                sincronizarBase();
                tabela();
            }
            tbCategoria.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Mensagem.alerta("Por favor! selecione uma categoria para a remoção da tabela!");
        }
    }

    @FXML
    private void telaPrint(ActionEvent even) {
        DAOFactory.daoFactury().categoriaDAO().report();
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaCategoria);

        colId.setCellValueFactory(new PropertyValueFactory<>("id_categoria"));
        colCategoria.setCellValueFactory((TableColumn.CellDataFeatures<Categoria, String> obj) -> new SimpleStringProperty(obj.getValue().getNome()));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tbCategoria.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtroPesquisa(String valor, ObservableList<Categoria> listaCategoria) {

        FilteredList<Categoria> dadosFiltrados = new FilteredList<>(listaCategoria, categoria -> true);
        dadosFiltrados.setPredicate(categoria -> 
            categoria.getNome().toLowerCase().startsWith(valor.toLowerCase())
        );

        SortedList<Categoria> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbCategoria.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de Categoria encontrados");
        tbCategoria.setItems(dadosOrdenados);
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaCategoria = DAOFactory.daoFactury().categoriaDAO().findAll();
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbCategoria.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idCategoria = 0;
    }

    private void limparCampos() {
        Campo.limpar(txtCategoria);
        Campo.limpar(txtDescricao);
    }

}
