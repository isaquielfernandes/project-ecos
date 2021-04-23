package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Exame;
import cv.com.escola.model.entity.ExameResultado;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Combo;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Filtro;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ExameResultadoController extends AnchorPane implements Initializable {

    private static final String QUANTIDADE_DE_RESULTDO_DE_EXAME_ENCONTRAD = "Quantidade de resultdo de exame encontrados";
    private List<Exame> listaExame;
    private List<ExameResultado> listaExameResultado;
    private long idResultado;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private ToggleGroup menu;
    @FXML
    private ToggleButton btTelaCadastroOnMouseCliker;
    @FXML
    private GridPane telaCadastro;
    @FXML
    private ComboBox<String> cbResultadoExame;
    @FXML
    private TextField txtBuscarAluno;
    @FXML
    private TableView<Exame> tbExame;
    @FXML
    private TableColumn<Exame, String> colId;
    @FXML
    private TableColumn<Exame, String> colAluno;
    @FXML
    private TableColumn<Exame, String> colCategoria;
    @FXML
    private TableColumn<Exame, String> colTipo;
    @FXML
    private TableColumn<Exame, String> colHora;
    @FXML
    private TableColumn<Exame, String> colData;
    @FXML
    private TableColumn<Exame, String> colDescricao;
    @FXML
    private TextField txtAluno;
    @FXML
    private TextField txtIDExame;
    @FXML
    private DatePicker dataPickerDia;
    @FXML
    private TextField txtTipoExame;
    @FXML
    private AnchorPane telaEdicao;
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
    @FXML
    private TableView<ExameResultado> tbExameResultado;
    @FXML
    private TableColumn<ExameResultado, Long> colIdR;
    @FXML
    private TableColumn<ExameResultado, String> colAlunoR;
    @FXML
    private TableColumn<ExameResultado, String> colCategoriaR;
    @FXML
    private TableColumn<ExameResultado, String> colTipoR;
    @FXML
    private TableColumn<ExameResultado, String> colDataR;
    @FXML
    private TableColumn<ExameResultado, String> colHoraR;
    @FXML
    private TableColumn<ExameResultado, String> colResultado;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sincronizarBase();
        telaCadastro(null);
        combos();
        btTelaCadastroOnMouseCliker.setOnMouseClicked(event -> {
            sincronizarBase();
            tabela();
        });

        selectedTableAluno(null);
        // Detecta mudanças de seleção e mostra os detalhes do aluno quando houver mudança.
        tbExame.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectedTableAluno(newValue));

        txtBuscarAluno.textProperty().addListener((obs, old, novo)
            -> filtro(novo, FXCollections.observableArrayList(listaExame)));

    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Lançar Resultado de Exame", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        tabela();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("Editar Resultado de Exame", QUANTIDADE_DE_RESULTDO_DE_EXAME_ENCONTRAD, 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabelaResultadoDeExame();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Excluir Resultado de Exame", QUANTIDADE_DE_RESULTDO_DE_EXAME_ENCONTRAD, 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabelaResultadoDeExame();
    }

    @FXML
    private void telaImprimir(ActionEvent event) {
        configTela("Imprimsão", QUANTIDADE_DE_RESULTDO_DE_EXAME_ENCONTRAD, 3);
        Modulo.visualizacao(true, telaEdicao, btImprimir, txtPesquisar);
        tabelaResultadoDeExame();
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean vazio = Campo.noEmpty(txtIDExame);

        Exame exame = tbExame.getSelectionModel().getSelectedItem();
        String resultado = cbResultadoExame.getValue();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else {
            ExameResultado exameResultado = new ExameResultado(idResultado, exame, resultado);

            if (idResultado == 0) {
                DAOFactory.daoFactury().exameResultadoDAO().create(exameResultado);
                Mensagem.info("Exame marcada com sucesso!");
            } else {
                DAOFactory.daoFactury().exameResultadoDAO().update(exameResultado);
                Mensagem.info("Exame atualizada com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            ExameResultado exame = tbExameResultado.getSelectionModel().getSelectedItem();
            exame.getClass();
            telaCadastro(null);

            txtIDExame.setText(String.valueOf(exame.getExame().getIdExame()));
            txtAluno.setText(exame.getExame().getAluno().getNome());
            txtTipoExame.setText(exame.getExame().getTipoExame());
            dataPickerDia.setValue(exame.getExame().getDataExame());
            cbResultadoExame.setValue(exame.getResultado());

            lbTitulo.setText("Editar Resultado Exame");
            menu.selectToggle(menu.getToggles().get(1));

            idResultado = exame.getIdExameResultado();
        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um exame na tabela para edição!");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        try {
            ExameResultado resultado = tbExameResultado.getSelectionModel().getSelectedItem();
            Dialogo.Resposta response = Mensagem.confirmar("Excluir Resultado de exame do aluno " + resultado.getExame().getAluno().getNome() + " ?");
            if (response == Dialogo.Resposta.YES) {
                DAOFactory.daoFactury().exameResultadoDAO().delete(resultado.getIdExameResultado());
                Mensagem.info("Resultado de exame apagado com sucesso!");
                sincronizarBase();
                tabela();
            }
            tbExameResultado.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione resultado de exame na tabela para exclusão!");
        }
    }

    @FXML
    private void imprimir(ActionEvent event) {
        imprimir();
    }

    private void imprimir() {
        try {
            ExameResultado aluno = tbExameResultado.getSelectionModel().getSelectedItem();
            Dialogo.Resposta response = Mensagem.confirmar("Imprimir Ficha Aula Pratica para:: " + aluno.getExame().getAluno().getNome() + " ?");
            if (response == Dialogo.Resposta.YES) {
                DAOFactory.daoFactury().exameResultadoDAO().reportFichaAulaPratica(aluno.getExame().getAluno().getIdAluno(), aluno.getExame().getAluno().getNome());
            }
            tbExameResultado.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Nota.alerta("Selecione aluno na tabela para imprimir a ficha da aula pratica! ");
        }
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public ExameResultadoController() {
        try {
            GenericFXXMLLoader.loadFXML(this, "exameResultado");
        } catch (IOException ex) {
            Logger.getLogger(ExameResultadoController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("erro ao caregar a tela resultado de exame");
        }
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaExame = DAOFactory.daoFactury().exameDAO().findAll();
        listaExameResultado = DAOFactory.daoFactury().exameResultadoDAO().findAll();
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btImprimir, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbExameResultado.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idResultado = 0;
    }

    private void tabela() {
        ObservableList exameMarcado = FXCollections.observableArrayList(listaExame);

        colId.setCellValueFactory((TableColumn.CellDataFeatures<Exame, String> obj)
                -> new SimpleStringProperty(Long.toString(obj.getValue().getIdExame())));
        colAluno.setCellValueFactory((TableColumn.CellDataFeatures<Exame, String> obj)
                -> new SimpleStringProperty(obj.getValue().getAluno().getNome()));
        colCategoria.setCellValueFactory((TableColumn.CellDataFeatures<Exame, String> obj)
                -> new SimpleStringProperty(obj.getValue().getCategoria().getNome()));
        colData.setCellValueFactory((TableColumn.CellDataFeatures<Exame, String> obj)
                -> new SimpleStringProperty(obj.getValue().getDataExame().toString()));
        colHora.setCellValueFactory((TableColumn.CellDataFeatures<Exame, String> obj)
                -> new SimpleStringProperty(obj.getValue().getHoraDeExame().toString()));
        colDescricao.setCellValueFactory((TableColumn.CellDataFeatures<Exame, String> obj)
                -> new SimpleStringProperty(obj.getValue().getDescricao()));
        colTipo.setCellValueFactory((TableColumn.CellDataFeatures<Exame, String> obj)
                -> new SimpleStringProperty(obj.getValue().getTipoExame()));

        tbExame.setItems(exameMarcado);
    }

    private void tabelaResultadoDeExame() {
        ObservableList resultadoExame = FXCollections.observableArrayList(listaExameResultado);

        colIdR.setCellValueFactory((CellDataFeatures<ExameResultado, Long> obj)
                -> obj.getValue().idExameResultadoProperty().asObject());
        colAlunoR.setCellValueFactory((CellDataFeatures<ExameResultado, String> obj)
                -> new SimpleStringProperty(obj.getValue().getExame().getAluno().getNome()));
        colCategoriaR.setCellValueFactory((CellDataFeatures<ExameResultado, String> obj)
                -> new SimpleStringProperty(obj.getValue().getExame().getCategoria().getNome()));
        colDataR.setCellValueFactory((CellDataFeatures<ExameResultado, String> obj)
                -> new SimpleStringProperty(obj.getValue().getExame().getDataExame().toString()));
        colHoraR.setCellValueFactory((CellDataFeatures<ExameResultado, String> obj)
                -> new SimpleStringProperty(obj.getValue().getExame().getHoraDeExame().toString()));
        colTipoR.setCellValueFactory((CellDataFeatures<ExameResultado, String> obj)
                -> new SimpleStringProperty(obj.getValue().getExame().getTipoExame()));
        colResultado.setCellValueFactory((CellDataFeatures<ExameResultado, String> obj)
                -> new SimpleStringProperty(obj.getValue().resultadoProperty().get()));

        tbExameResultado.setItems(resultadoExame);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtro(String valor, ObservableList<Exame> listaExame) {
        FilteredList<Exame> dadosFiltrados = new FilteredList<>(listaExame, exame -> true);
        dadosFiltrados.setPredicate(exame -> exame.getAluno().getNome().toLowerCase().startsWith(valor.toLowerCase()) ||
            exame.getTipoExame().toLowerCase().startsWith(valor.toLowerCase()) ||
            exame.getDataExame().toString().toLowerCase().startsWith(valor.toUpperCase())
        );
        SortedList<Exame> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbExame.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de exame marcado encontradas");
        tbExame.setItems(dadosOrdenados);
    }

    /**
     * Preencher combos tela
     */
    private void combos() {
        Combo.popular(cbResultadoExame, "", "FALTOU", "APROVADO", "REPROVADO");
    }

    public void selectedTableAluno(Exame exame) {
        if (exame != null) {
            txtIDExame.setText(String.valueOf(exame.getIdExame()));
            txtAluno.setText(exame.getAluno().getNome());
            txtTipoExame.setText(exame.getTipoExame());
            dataPickerDia.setValue(exame.getDataExame());
        } else {
            txtIDExame.clear();
            txtAluno.clear();
            txtTipoExame.clear();
            dataPickerDia.setValue(null);
        }
    }
}
