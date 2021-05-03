package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Categoria;
import cv.com.escola.model.entity.Curso;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Combo;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Filtro;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import cv.com.escola.model.util.Nota;
import static cv.com.escola.model.util.ValidationFields.checkEmptyFields;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
public class CursoController extends AnchorPane implements Initializable {

    private List<Curso> listaCurso;
    private long codigoCurso;
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
    private TextField txtNomeCurso;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private ComboBox<Categoria> cbCategoria;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableView<Curso> tbCurso;
    @FXML
    private TableColumn<Curso, String> colId;
    @FXML
    private TableColumn<Curso, String> colCurso;
    @FXML
    private TableColumn<Curso, String> colCategoria;
    @FXML
    private TableColumn<Curso, String> colDuracao;
    @FXML
    private TableColumn<Curso, String> colDescricao;
    @FXML
    private Button btSalvar;
    @FXML
    private Button btEditar;
    @FXML
    private Button btExcluir;
    @FXML
    private Label legenda;
    @FXML
    private TextField txtDuracao;

    
    @SuppressWarnings("LeakingThisInConstructor")
    public CursoController() {
        try {
            GenericFXXMLLoader.loadFXML(this, "curso");
        } catch (IOException ex) {
            Logger.getLogger(CursoController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("erro ao caregar tela curso!");
        }
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaCurso = DAOFactory.daoFactory().cursosDAO().findAll();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        telaCadastro(null);
        combos();
        sincronizarBase();
        cbCategoria.setOnMouseClicked(event -> 
            Combo.popular(cbCategoria, DAOFactory.daoFactory().categoriaDAO().findAll())
        );
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Curso", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampos();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("Editar Curso", "Quantidade de cursos encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Excluir curso", "Quantidade de cursos encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean vazio = checkEmptyFields(txtNomeCurso, txtDuracao);

        String curso = txtNomeCurso.getText();
        Categoria categoria = cbCategoria.getValue();
        Integer duracao = Integer.parseInt(txtDuracao.getText().trim().isEmpty() ? "1" : txtDuracao.getText());
        String descricao = txtDescricao.getText();

        if (vazio) {
            Curso cursos = new Curso(codigoCurso, curso, duracao, descricao, categoria);
            if (codigoCurso == 0) {
                if (DAOFactory.daoFactory().cursosDAO().findAll().contains(cursos)) {
                    Nota.alerta("Curso ja se encrotra cadastrado!");
                } else {
                    DAOFactory.daoFactory().cursosDAO().create(cursos);
                }
            } else {
                DAOFactory.daoFactory().cursosDAO().update(cursos);
            }
            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        Curso curso = tbCurso.getSelectionModel().getSelectedItem();
        curso.getClass();

        telaCadastro(null);
        txtNomeCurso.setText(curso.getNome());
        cbCategoria.setValue(curso.getCategoria());
        txtDuracao.setText(String.valueOf(curso.getDuracao()));
        txtDescricao.setText(curso.getDescricao());

        lbTitulo.setText("Editar Curso");
        menu.selectToggle(menu.getToggles().get(1));

        codigoCurso = curso.getCodigo();
    }

    @FXML
    private void excluir(ActionEvent event) {
        try {
            Curso curso = tbCurso.getSelectionModel().getSelectedItem();

            Dialogo.Resposta response = Mensagem.confirmar("Excluir curso " + curso.getNome() + " ?");

            if (response == Dialogo.Resposta.YES) {
                DAOFactory.daoFactory().cursosDAO().delete(curso.getCodigo());
                sincronizarBase();
                tabela();
            }
            tbCurso.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione curso na tabela para exclusão!");
        }
    }

    public void combos() {
        Combo.popular(cbCategoria, DAOFactory.daoFactory().categoriaDAO().findAll());
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limparCampos() {
        Campo.limpar(txtNomeCurso, txtDuracao);
        Campo.limpar(txtDescricao);
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbCurso.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        codigoCurso = 0;
    }

    private void tabela() {
        ObservableList dados = FXCollections.observableArrayList(listaCurso);
        colId.setCellValueFactory((TableColumn.CellDataFeatures<Curso, String> obj) -> new SimpleStringProperty(Long.toString(obj.getValue().getCodigo())));
        colCurso.setCellValueFactory((TableColumn.CellDataFeatures<Curso, String> obj) -> new SimpleStringProperty(obj.getValue().getNome()));
        colCategoria.setCellValueFactory((TableColumn.CellDataFeatures<Curso, String> obj) -> new SimpleStringProperty(obj.getValue().getCategoria().getNome()));
        colDuracao.setCellValueFactory((TableColumn.CellDataFeatures<Curso, String> obj) -> new SimpleStringProperty(Long.toString(obj.getValue().getDuracao())));
        colDescricao.setCellValueFactory((TableColumn.CellDataFeatures<Curso, String> obj) -> new SimpleStringProperty(obj.getValue().getDescricao()));

        tbCurso.setItems(dados);
    }

    public void filtro(String valor, ObservableList<Curso> listaCurso) {
        FilteredList<Curso> dadosFiltrados = new FilteredList<>(listaCurso, curso -> true);
        dadosFiltrados.setPredicate(curso -> 
                curso.getNome().toLowerCase().startsWith(valor.toLowerCase()) || 
                curso.getCategoria().getNome().toLowerCase().startsWith(valor.toLowerCase())
        );
        SortedList<Curso> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbCurso.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de cursos encontradas");

        tbCurso.setItems(dadosOrdenados);
    }
}
