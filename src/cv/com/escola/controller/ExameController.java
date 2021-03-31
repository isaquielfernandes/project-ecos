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
import java.util.Random;
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
import javafx.fxml.FXMLLoader;
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


public class ExameController extends AnchorPane implements Initializable {

    private List<Categoria> listaCategoria;
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
    private TextField txtId_Aluno;
    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<Aluno> tbAluno;
    @FXML
    private TableColumn<Aluno, String> colAlunoID;
    @FXML
    private TableColumn<Aluno, String> colAlunoNome;
    @FXML
    private ComboBox<String> cbTipo_Exame;
    @FXML
    private ComboBox<Aluno> cbCliente;
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
    private TextField txtNome_Aluno;
    @FXML
    private TextField txtAtestado_Medico;
    @FXML
    private TextField txtRegistro_Criminal;
    @FXML
    private ComboBox<Categoria> cbExame_De;
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
    private Hyperlink hlinkRegistro_Criminal;
    @FXML
    private Hyperlink hlinkAtestadoMedico;

    private File file = null;
    private File copyFile = null;
    private FileChooser fileChooser = new FileChooser();
    private FileChooser.ExtensionFilter extensionFilterJPG = new FileChooser.ExtensionFilter("JPG Files(*.jpg)", "*.JPG");
    private FileChooser.ExtensionFilter extensionFilterPNG = new FileChooser.ExtensionFilter("PNG Files(*.PNG)", "*.PNG");
    private FileChooser.ExtensionFilter extensionFilterPDF = new FileChooser.ExtensionFilter("PDF Files(*.pdf)", "*.PDF");
    private String path = System.getProperty("user.home");
    private Random rand = new Random();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
    private String n = LocalDateTime.now().format(formatter);//rand.nextInt(999999999) + 1;
    private String sep = File.separator;
    private File diretorioR = new File("public/img/exame/registro Criminal/");
    private File diretorioA = new File("public/img/exame/atestado medico/");

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabelaAluno() {
        ObservableList data = FXCollections.observableArrayList(listaAluno);

        colAlunoID.setCellValueFactory((TableColumn.CellDataFeatures<Aluno, String> obj) -> 
                new SimpleStringProperty(Integer.toString(obj.getValue().getIdAluno())));
        colAlunoNome.setCellValueFactory((TableColumn.CellDataFeatures<Aluno, String> obj) -> new SimpleStringProperty(obj.getValue().getNome()));

        tbAluno.setItems(data);
        cbCliente.setItems(data);
    }

    public void showAlunoSelected(Aluno aluno) {
        aluno = cbCliente.getSelectionModel().getSelectedItem();
        if (aluno != null) {
            txtId_Aluno.setText(String.valueOf(aluno.getIdAluno()));
            txtNome_Aluno.setText(aluno.getNome());
        } else {
            txtId_Aluno.setText("");
            txtNome_Aluno.setText("");
        }
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtroAluno(String valor, ObservableList<Aluno> listaAluno) {
        FilteredList<Aluno> dadosFiltrados = new FilteredList<>(listaAluno, aluno -> true);
        dadosFiltrados.setPredicate((aluno) -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (aluno.getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (aluno.getNumBI().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }

            return false;
        });
        SortedList<Aluno> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbAluno.comparatorProperty());
        //Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de aluno encontradas");

        tbAluno.setItems(dadosOrdenados);
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public ExameController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/cv/com/escola/view/exame.fxml"));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ExameController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("erro ao caregar a tela marca exame, \n" + ex);
        }
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaExame = DAOFactory.daoFactury().exameDAO().findAll();
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
        Campo.limpar(txtId_Aluno, txtNome_Aluno, txtRegistro_Criminal, txtAtestado_Medico);
        Campo.limpar(txtDescricao);
        cbCliente.setValue(null);
        cbExame_De.setValue(null);
        cbTipo_Exame.setValue(null);
        dtExame.setValue(null);
    }

    /**
     * Preencher combos tela
     */
    private void combos() {
        Combo.popular(cbTipo_Exame, "", "TEORICA", "PRATICA", "TECNICA", "CAP");
        Combo.popular(cbTipoExame, "", "TEORICA", "PRATICA", "TECNICA", "CAP");
        Combo.popular(cbExame_De, DAOFactory.daoFactury().categoriaDAO().findAll());
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Exame", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampos();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("Editar Exame", "Quantidade de exames encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Excluir Exame", "Quantidade de exames encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaImprimir(ActionEvent event) {
        configTela("Lista de Exame", "Quantidade de exames encontrados", 3);
        Modulo.visualizacao(true, telaEdicao, txtPesquisar, cbTipoExame, datePickerDia, btImprimir);
        tabela();
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean vazio = checkEmptyFields(txtId_Aluno, cbTipo_Exame, cbExame_De, dtExame, cbCliente);

        Aluno aluno = cbCliente.getValue();
        String tipo_exame = cbTipo_Exame.getValue();
        Categoria cat = cbExame_De.getValue();
        LocalDate dia = dtExame.getValue();
        LocalTime hora = tpHora.getValue();
        String descricao = txtDescricao.getText();

        if (vazio) {
            Exame exame = new Exame(idMarcar, tipo_exame, dia, hora, descricao, cat, aluno);
            exame.setRegistroCriminal(copyRegistroCriminal());
            exame.setAtestadoMedico(copyAtestadoMedico());

            if (idMarcar == 0) {
                DAOFactory.daoFactury().exameDAO().create(exame);
                Mensagem.info("Exame marcada com sucesso!");
            } else {
                DAOFactory.daoFactury().exameDAO().update(exame);
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

            cbCliente.setValue(exame.getAluno());
            txtId_Aluno.setText(exame.getAluno().getIdAluno().toString());
            cbTipo_Exame.setValue(exame.getTipoExame());
            cbExame_De.setValue(exame.getCategoria());
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
                DAOFactory.daoFactury().exameDAO().delete(exame.getIdExame());
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
    public void filtro(ActionEvent event) {
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
                DAOFactory.daoFactury().exameDAO().reportListCandidadtoParaExame(tipo, dia);
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
        listaAluno = DAOFactory.daoFactury().alunoDAO().findAll();
        tabelaAluno();
        txtBuscar.textProperty().addListener((obs, old, novo) -> {
            filtroAluno(novo, FXCollections.observableArrayList(listaAluno));
        });
        // Limpa os detalhes do aluno.
        showAlunoSelected(null);
        // Detecta mudanças de seleção e mostra os detalhes do aluno quando houver mudança.
        cbCliente.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAlunoSelected(newValue));

        cbCliente.setOnMouseClicked((event) -> {
            Combo.popular(cbCliente, DAOFactory.daoFactury().alunoDAO().findAll());
        });
        // ****************************************************************************

        cbExame_De.setOnMouseClicked((event) -> {
            Combo.popular(cbExame_De, DAOFactory.daoFactury().categoriaDAO().findAll());
        });

        hlinkAtestadoMedico.setOnMouseClicked((event) -> {
            fileChooser.getExtensionFilters().addAll(extensionFilterJPG, extensionFilterPNG, extensionFilterPDF);
            fileChooser.setTitle("Upload File");
            file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                String filePath = file.getPath();
                txtAtestado_Medico.setText(filePath);
            }
        });

        hlinkRegistro_Criminal.setOnMouseClicked((event) -> {
            fileChooser.getExtensionFilters().addAll(extensionFilterJPG, extensionFilterPNG, extensionFilterPDF);
            fileChooser.setTitle("Upload File");
            file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                String filePath = file.getPath();
                txtRegistro_Criminal.setText(filePath);
            }
        });

        tpHora._24HourViewProperty();
        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaExame));
        });

        datePickerDia.valueProperty().addListener((observable, oldValue, newValue) -> {
            filtroPorData(newValue, FXCollections.observableArrayList(listaExame));
        });
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
        dadosFiltrados.setPredicate(exame -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (exame.getAluno().getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (exame.getTipoExame().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (exame.getDataExame().toString().toLowerCase().startsWith(valor.toUpperCase())) {
                return true;
            }

            return false;
        });

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
        dadosFiltrados.setPredicate(exame -> {

            if (valor == null) {
                return true;
            } else if (exame.getDataExame().toString().toLowerCase().startsWith(valor.toString().toLowerCase())) {
                return true;
            }
            return false;
        });

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
            Logger.getLogger(ArtigoController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ArtigoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return copyFile.getName();
    }

    //caregando file
    private void uploadFile() {
        fileChooser.getExtensionFilters().addAll(extensionFilterJPG, extensionFilterPNG, extensionFilterPDF);
        fileChooser.setTitle("Upload File");
        file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            String imagePath = file.getPath();
        }
    }

    private void findByTipoDeExame() {
        listaExame.stream()
                .filter(e -> e.getTipoExame().equalsIgnoreCase(cbTipoExame.getValue())
                || e.getDataExame().equals(datePickerDia.getValue()))
                .forEach(e -> {
                    System.out.println(e.toString());
                    System.out.println("=============================");
                });
    }
}
