package cv.com.escola.controller;

import com.jfoenix.controls.JFXTimePicker;
import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Aluno;
import cv.com.escola.model.entity.Categoria;
import cv.com.escola.model.entity.Exame;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Combo;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Filtro;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import cv.com.escola.model.util.Nota;
import static cv.com.escola.model.util.ValidationFields.checkEmptyFields;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExameController extends AnchorPane implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExameController.class);
    private static final String QUANTIDADE_DE_EXAMES_ENCONTRADOS = "Quantidade de exames encontrados";
    private static final String UPLOAD_FILE = "Upload File";
    private List<Exame> listaExame;
    private List<Aluno> listaAluno;
    private long idMarcar;

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private ToggleGroup menu;
    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextField txtIdAluno;
    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<Aluno> tbAluno;
    @FXML
    private TableColumn<Aluno, String> colAlunoID;
    @FXML
    private TableColumn<Aluno, String> colAlunoNome;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private ComboBox<Aluno> cbAluno;
    @FXML
    private ComboBox<String> cbTipoExame;
    @FXML
    private DatePicker dtExame;
    @FXML
    private DatePicker datePickerDia;
    @FXML
    private JFXTimePicker tpHora;
    @FXML
    private TextField txtBuscarAluno;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private TextField txtNomeAluno;
    @FXML
    private TextField txtAtestadoMedico;
    @FXML
    private TextField txtRegistroCriminal;
    @FXML
    private ComboBox<Categoria> cbCategoria;
    @FXML
    private AnchorPane telaEdicao;
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
    private Hyperlink hlinkRegistroCriminal;
    @FXML
    private Hyperlink hlinkAtestadoMedico;

    private File file = null;
    private File copyFile = null;
    private FileChooser fileChooser = new FileChooser();
    private FileChooser.ExtensionFilter extensionFilterJPG = new FileChooser.ExtensionFilter("JPG Files(*.jpg)", "*.JPG");
    private FileChooser.ExtensionFilter extensionFilterPNG = new FileChooser.ExtensionFilter("PNG Files(*.PNG)", "*.PNG");
    private FileChooser.ExtensionFilter extensionFilterPDF = new FileChooser.ExtensionFilter("PDF Files(*.pdf)", "*.PDF");
    private String path = System.getProperty("user.home");
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
    private String n = LocalDateTime.now().format(formatter);
    private String sep = File.separator;
    private File diretorioR = new File(path + "/public/img/exame/registro Criminal/");
    private File diretorioA = new File(path +"/public/img/exame/atestado medico/");

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabelaAluno() {
        ObservableList data = FXCollections.observableArrayList(listaAluno);
        colAlunoID.setCellValueFactory((TableColumn.CellDataFeatures<Aluno, String> obj) -> 
                new SimpleStringProperty(Integer.toString(obj.getValue().getId())));
        colAlunoNome.setCellValueFactory((TableColumn.CellDataFeatures<Aluno, String> obj) -> new SimpleStringProperty(obj.getValue().getNome()));
        tbAluno.setItems(data);
        cbAluno.setItems(data);
    }

    public void showAlunoSelected(Aluno aluno) {
        if (aluno != null) {
            txtIdAluno.setText(String.valueOf(aluno.getId()));
            txtNomeAluno.setText(aluno.getNome());
        } else {
            txtIdAluno.setText("");
            txtNomeAluno.setText("");
        }
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtroAluno(String valor, ObservableList<Aluno> listaAluno) {
        FilteredList<Aluno> dadosFiltrados = new FilteredList<>(listaAluno, aluno -> true);
        dadosFiltrados.setPredicate(aluno -> aluno.getNome().toLowerCase().startsWith(valor.toLowerCase()) ||
            aluno.getNumBI().toLowerCase().startsWith(valor.toLowerCase()) 
        );
        SortedList<Aluno> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbAluno.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de aluno encontradas");
        tbAluno.setItems(dadosOrdenados);
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public ExameController() {
        try {
            GenericFXXMLLoader.loadFXML(this, "exame");
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
            Mensagem.erro("erro ao caregar a tela marca exame");
        }
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaExame = DAOFactory.daoFactory().exameDAO().findAll();
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar, cbTipoExame, datePickerDia, btImprimir);

        legenda.setText(msg);
        tbExame.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idMarcar = 0;
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limparCampos() {
        Campo.limpar(txtIdAluno, txtNomeAluno, txtRegistroCriminal, txtAtestadoMedico);
        Campo.limpar(txtDescricao);
        cbAluno.setValue(null);
        cbCategoria.setValue(null);
        cbTipo.setValue(null);
        dtExame.setValue(null);
    }

    /**
     * Preencher combos tela
     */
    private void combos() {
        Combo.popular(cbTipo, "", "TEORICA", "PRATICA", "TECNICA", "CAP");
        Combo.popular(cbTipoExame, "", "TEORICA", "PRATICA", "TECNICA", "CAP");
        Combo.popular(cbCategoria, DAOFactory.daoFactory().categoriaDAO().findAll());
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Exame", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampos();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("Editar Exame", QUANTIDADE_DE_EXAMES_ENCONTRADOS, 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Excluir Exame", QUANTIDADE_DE_EXAMES_ENCONTRADOS, 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaImprimir(ActionEvent event) {
        configTela("Lista de Exame", QUANTIDADE_DE_EXAMES_ENCONTRADOS, 3);
        Modulo.visualizacao(true, telaEdicao, txtPesquisar, cbTipoExame, datePickerDia, btImprimir);
        tabela();
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean vazio = checkEmptyFields(txtIdAluno, cbTipo, cbCategoria, dtExame, cbAluno);

        Aluno aluno = cbAluno.getValue();
        String tipoDeExame = cbTipo.getValue();
        Categoria cat = cbCategoria.getValue();
        LocalDate dia = dtExame.getValue();
        LocalTime hora = tpHora.getValue();
        String descricao = txtDescricao.getText();

        if (vazio) {
            Exame exame = new Exame(idMarcar, tipoDeExame, dia, hora, descricao, cat, aluno);
            exame.setRegistroCriminal(copyRegistroCriminal());
            exame.setAtestadoMedico(copyAtestadoMedico());

            if (idMarcar == 0) {
                DAOFactory.daoFactory().exameDAO().create(exame);
                Mensagem.info("Exame marcada com sucesso!");
            } else {
                DAOFactory.daoFactory().exameDAO().update(exame);
                Mensagem.info("Exame atualizada com sucesso!");
            }
            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            Exame exame = tbExame.getSelectionModel().getSelectedItem();
            exame.getClass();
            telaCadastro(null);

            cbAluno.setValue(exame.getAluno());
            txtIdAluno.setText(exame.getAluno().getId().toString());
            cbTipo.setValue(exame.getTipoExame());
            cbCategoria.setValue(exame.getCategoria());
            dtExame.setValue(exame.getDataExame());
            tpHora.setValue(exame.getHoraDeExame());
            txtDescricao.setText(exame.getDescricao());

            lbTitulo.setText("Editar Marcar Exame");
            menu.selectToggle(menu.getToggles().get(1));

            idMarcar = exame.getIdExame();
        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um exame na tabela para edição!");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        try {
            Exame exame = tbExame.getSelectionModel().getSelectedItem();
            Dialogo.Resposta response = Mensagem.confirmar("Excluir Exame " + exame.getTipoExame() + " ?");
            if (response == Dialogo.Resposta.YES) {
                DAOFactory.daoFactory().exameDAO().delete(exame.getIdExame());
                sincronizarBase();
                tabela();
            }
            tbExame.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione exame na tabela para exclusão!");
        }
    }

    @FXML
    public void imprimir(ActionEvent event) {
        imprimir();
    }

    //@FXML
    public void filtro() {
        if (cbTipoExame.getValue() != null && datePickerDia.getValue() != null) {
            findByTipoDeExame();
        }else {
            Mensagem.alerta("Campo vazio");
        }
    }

    public void imprimir() {
        String tipo = cbTipoExame.getValue();
        Date dia = Date.valueOf(datePickerDia.getValue());

        Dialogo.Resposta responses = Mensagem.confirmar("Deseja imprimir lista de candidato para o exame?");
        if (responses == Dialogo.Resposta.YES) {
            if (cbTipoExame.getValue() != null && datePickerDia.getEditor().getText() != null) {
                DAOFactory.daoFactory().exameDAO().reportListCandidadtoParaExame(tipo, dia);
            } else {
                Mensagem.alerta(":::");
            }
        }
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
        sincronizarBase();
        combos();

        // **************************************************************************
        listaAluno = DAOFactory.daoFactory().alunoDAO().findAll();
        tabelaAluno();
        txtBuscar.textProperty().addListener((obs, old, novo) -> 
            filtroAluno(novo, FXCollections.observableArrayList(listaAluno)));
        
        // Limpa os detalhes do aluno.
        showAlunoSelected(null);
        // Detecta mudanças de seleção e mostra os detalhes do aluno quando houver mudança.
        cbAluno.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAlunoSelected(newValue));

        cbAluno.setOnMouseClicked(event -> 
            Combo.popular(cbAluno, DAOFactory.daoFactory().alunoDAO().findAll())
        );
        // ****************************************************************************

        cbCategoria.setOnMouseClicked(event -> 
            Combo.popular(cbCategoria, DAOFactory.daoFactory().categoriaDAO().findAll())
        );

        hlinkAtestadoMedico.setOnMouseClicked(event -> {
            fileChooser.getExtensionFilters().addAll(extensionFilterJPG, extensionFilterPNG, extensionFilterPDF);
            fileChooser.setTitle(UPLOAD_FILE);
            file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                String filePath = file.getPath();
                txtAtestadoMedico.setText(filePath);
            }
        });

        hlinkRegistroCriminal.setOnMouseClicked(event -> {
            fileChooser.getExtensionFilters().addAll(extensionFilterJPG, extensionFilterPNG, extensionFilterPDF);
            fileChooser.setTitle(UPLOAD_FILE);
            file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                String filePath = file.getPath();
                txtRegistroCriminal.setText(filePath);
            }
        });

        tpHora._24HourViewProperty();
        txtPesquisar.textProperty().addListener((obs, old, novo) -> 
            filtro(novo, FXCollections.observableArrayList(listaExame))
        );

        datePickerDia.valueProperty().addListener((observable, oldValue, newValue) -> 
            filtroPorData(newValue, FXCollections.observableArrayList(listaExame))
        );
    }
    

    private void tabela() {
        ObservableList exameMarcado = FXCollections.observableArrayList(listaExame);

        colId.setCellValueFactory((TableColumn.CellDataFeatures<Exame, String> obj) -> 
                new SimpleStringProperty(Long.toString(obj.getValue().getIdExame())));
        colAluno.setCellValueFactory((TableColumn.CellDataFeatures<Exame, String> obj) -> 
                new SimpleStringProperty(obj.getValue().getAluno().getNome()));
        colCategoria.setCellValueFactory((TableColumn.CellDataFeatures<Exame, String> obj) -> 
                new SimpleStringProperty(obj.getValue().getCategoria().nomeProperty().get()));
        colData.setCellValueFactory((TableColumn.CellDataFeatures<Exame, String> obj) -> 
                new SimpleStringProperty(obj.getValue().getDataExame().toString()));
        colHora.setCellValueFactory((TableColumn.CellDataFeatures<Exame, String> obj) -> 
                new SimpleStringProperty(obj.getValue().getHoraDeExame().toString()));
        colDescricao.setCellValueFactory((TableColumn.CellDataFeatures<Exame, String> obj) -> 
                new SimpleStringProperty(obj.getValue().getDescricao()));
        colTipo.setCellValueFactory((TableColumn.CellDataFeatures<Exame, String> obj) -> 
                new SimpleStringProperty(obj.getValue().getTipoExame()));

        tbExame.setItems(exameMarcado);
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
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtroPorData(LocalDate valor, ObservableList<Exame> listaExame) {
        FilteredList<Exame> dadosFiltrados = new FilteredList<>(listaExame, exame -> true);
        dadosFiltrados.setPredicate(exame -> 
                exame.getDataExame().toString().toLowerCase().startsWith(valor.toString().toLowerCase()));
        SortedList<Exame> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbExame.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de exame marcado encontradas");

        tbExame.setItems(dadosOrdenados);
    }

    private String copyRegistroCriminal() {
        try {
            if (file != null) {
                if (!diretorioR.exists()) {
                    diretorioR.mkdirs();
                }
                copyFile = new File(diretorioR.getPath() + sep + n + "-" + file.getName());
            } else {
                return null;
            }
            Files.copy(Paths.get(file.getPath()), Paths.get(copyFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
        return copyFile.getName();
    }

    private String copyAtestadoMedico() {
        try {
            if (file != null) {
                if (!diretorioA.exists()) {
                    diretorioA.mkdirs();
                }
                copyFile = new File(diretorioA.getPath() + sep + n + "-" + file.getName());
            } else {
                return null;
            }
            Files.copy(Paths.get(file.getPath()), Paths.get(copyFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
        return copyFile.getName();
    }

    private void findByTipoDeExame() {
        listaExame.stream()
                .filter(e -> e.getTipoExame().equalsIgnoreCase(cbTipoExame.getValue())
                || e.getDataExame().equals(datePickerDia.getValue()))
                .forEach(e -> {
                    LOGGER.info(e.toString());
                    LOGGER.info("=============================================");
                });
    }
}
