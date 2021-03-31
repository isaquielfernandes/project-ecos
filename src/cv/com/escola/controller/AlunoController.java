package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.dao.exception.DaoException;
import cv.com.escola.model.entity.Aluno;
import cv.com.escola.model.entity.AlunoBuilder;
import cv.com.escola.model.entity.Pagina;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Combo;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Filtro;
import cv.com.escola.model.util.Grupo;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import cv.com.escola.model.util.Nota;
import cv.com.escola.model.util.Tempo;
import static cv.com.escola.model.util.ValidationFields.checkEmptyFields;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

public class AlunoController extends AnchorPane implements Initializable {

    private List<Aluno> listaAluno;
    private int idAluno = 0;

    private Image image;
    final String defualtImage = "/cv/com/escola/view/img/avater.jpg";
    private InputStream fileInput;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private ToggleGroup menu;
    @FXML
    private ToggleButton btAdcionar;
    @FXML
    private ToggleButton btEdit;
    @FXML
    private ToggleButton btApagar;
    @FXML
    private ToggleButton btImpr;
    @FXML
    private ToggleButton btAtualizar;
    @FXML
    private AnchorPane telaCadastro;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtNumBI;
    @FXML
    private TextField txtResidencia;
    @FXML
    private TextField txtConselho;
    @FXML
    private TextField txtNatural;
    @FXML
    private DatePicker dtNascimento;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private DatePicker dtEmissao;
    @FXML
    private ImageView imgView;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtNomeDaMae;
    @FXML
    private TextField txtNomeDoPai;
    @FXML
    private TextField txtProfessao;
    @FXML
    private TextField txtLocalDeEmisao;
    @FXML
    private TextField txtFreguesia;
    @FXML
    private ComboBox<String> cbEstadoCivil;
    @FXML
    private Button btFoto;
    @FXML
    private TextField txtContacto;
    @FXML
    private ComboBox<String> cbxHabilitacao;
    @FXML
    private Hyperlink hlAnexarFoto;
    @FXML
    private TextField txtNacionalidade;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableView<Aluno> tbAluno;
    @FXML
    private TableColumn colFoto;
    @FXML
    private TableColumn<Aluno, Integer> colId;
    @FXML
    private TableColumn<Aluno, String> colNome;
    @FXML
    private TableColumn<Aluno, LocalDate> colDataNacimento;
    @FXML
    private TableColumn<Aluno, LocalDate> colDataEmisao;
    @FXML
    private TableColumn<Aluno, String> colHabilitacao;
    @FXML
    private TableColumn<Aluno, String> colContacto;
    @FXML
    private TableColumn<Aluno, String> colResidencia;
    @FXML
    private TableColumn<Aluno, String> colConselho;
    @FXML
    private TableColumn<Aluno, String> colFreguesia;
    @FXML
    private TableColumn<Aluno, String> colNaturalidade;
    @FXML
    private TableColumn<Aluno, String> colNacionalidade;
    @FXML
    private TableColumn<Aluno, String> colPassaport;
    @FXML
    private TableColumn<Aluno, LocalDate> colDataCadastro;
    @FXML
    private TableColumn<Aluno, String> colEmail;
    @FXML
    private TableColumn<Aluno, String> colDescricao;
    @FXML
    private TableColumn<Aluno, String> colProfessao;
    @FXML
    private TableColumn<Aluno, String> colEstadoCivil;
    @FXML
    private TableColumn<Aluno, String> colLocalDeEmisao;
    @FXML
    private TableColumn<Aluno, String> colNomeDaMae;
    @FXML
    private TableColumn<Aluno, String> colNomeDoPai;
    @FXML
    private Button btSalvar;
    @FXML
    private Button btEditar;
    @FXML
    private Button btExcluir;
    @FXML
    private Button btImprimir;
    @FXML
    private ToggleButton btView;
    @FXML
    private Label legenda;
    @FXML
    private FlowPane flowPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private BorderPane telaView;
    @FXML
    private TextField txtBuscar;
    @FXML
    private Button btProximo;
    @FXML
    private Button btAnterior;
    @FXML
    private Label lblTotalAluno;
    @FXML
    private Label lblShowingAluno;
    @FXML
    private Pagination pagination;
    //@FXML
    private VBox vBox;

    private final int QUANTIDADE_PAGINA = 20;
    private ObservableList<Aluno> alunos;
    private boolean studantDefault = true;
    int totalAluno;
    int totalBuscarAluno;

    ObservableList<Aluno> student = FXCollections.observableArrayList();
    Pagina paginas = new Pagina();

    @SuppressWarnings("LeakingThisInConstructor")
    public AlunoController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/cv/com/escola/view/aluno.fxml"));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela aluno! \n" + ex);
        }
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Aluno", "Campos obrigatórios", 1);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampos();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("Editar Aluno", "Quantidade de alunos encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        mapTableAluno();
        atualizarGrade(0);
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Excluir aluno", "Quantidade de alunos encontrados", 3);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
    }

    @FXML
    private void telaImprimir(ActionEvent event) {
        configTela("Imprimir dados do aluno", "Aluno", 4);
        Modulo.visualizacao(true, telaEdicao, btImprimir, txtPesquisar);
    }

    @FXML
    private void telaAtualizar(MouseEvent event) {
        sincronizarDataBase();
    }

    @FXML
    private void telaVisualizacao(MouseEvent event) {
        configTela("Alunos", "Quantidade de alunos encontrados", 0);
        Modulo.visualizacao(true, telaView);
    }

    @FXML
    private void adcionarFoto(ActionEvent event) {
        File selectedFile = chooseFile();
        final long maxImageSize = 6000000L;
        if (selectedFile != null) {
            if (selectedFile.length() < maxImageSize) {
                image = new Image(selectedFile.getAbsoluteFile().toURI().toString());
                imgView.setImage(image);
            } else {
                Mensagem.alerta("A imagem é muito grande para fazer upload.\n "
                        + "Por favor escolha outra imagem", "Permição");
            }
        }
    }

    private void setFileInputStream() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imgView.getImage(), null);
            ImageIO.write(bufferedImage, "jpg", outputStream);
            byte[] stream = outputStream.toByteArray();
            fileInput = new ByteArrayInputStream(stream);
            fileInput.close();
        } catch (IOException ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private File chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fotografia File", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        return selectedFile;
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean emptyFields = checkEmptyFields(txtNome, txtResidencia, txtNatural,
                txtNumBI, txtContacto, txtConselho, txtNacionalidade, txtFreguesia,
                txtLocalDeEmisao, dtNascimento, dtEmissao);
        setFileInputStream();

        String nome = txtNome.getText();
        LocalDate dataNascimento = dtNascimento.getValue();
        String numBI = txtNumBI.getText();
        LocalDate dataEmisao = dtEmissao.getValue();
        String residencia = txtResidencia.getText();
        String conselho = txtConselho.getText();
        String natural = txtNatural.getText();
        String email = txtEmail.getText();
        String contato = txtContacto.getText();
        String tipo = cbxHabilitacao.getValue();
        String pais = txtNacionalidade.getText();
        String descricao = txtDescricao.getText();
        String freg = txtFreguesia.getText();
        String professao = txtProfessao.getText();
        String mae = txtNomeDaMae.getText();
        String pai = txtNomeDoPai.getText();
        String estadoCivil = cbEstadoCivil.getValue();
        String localEmissao = txtLocalDeEmisao.getText();

        if (emptyFields) {
            Aluno aluno = new AlunoBuilder().setIdAluno(idAluno).setNome(nome)
                    .setDataNascimento(dataNascimento).setNumBI(numBI).setDataEmisao(dataEmisao)
                    .setResidencia(residencia).setConselho(conselho).setNatural(natural)
                    .setEmail(email).setContato(contato).setHabilitacaoLit(tipo)
                    .setNacionalidade(pais).setDescricao(descricao).setNomeDaMae(mae)
                    .setNomeDoPai(pai).setProfessao(professao).setEstadoCivil(estadoCivil)
                    .setLocalDeEmisao(localEmissao).setFreguesia(freg).createAluno();
            aluno.setFoto(fileInput);
            try {
                if (idAluno == 0) {
                    DAOFactory.daoFactury().alunoDAO().create(aluno);
                    Mensagem.info("Aluno salvo com sucesso");
                } else {
                    DAOFactory.daoFactury().alunoDAO().update(aluno);
                    Mensagem.info("Aluno atualizado com sucesso");
                }
            } catch (DaoException e) {
                Mensagem.erro(e.getMessage());
            }
            telaCadastro(null);
            sincronizarDataBase();
        }

    }

    @FXML
    private void editar(ActionEvent event) {
        TableViewSelectionModel<Aluno> selectionModel = tableViewSelectionModel();
        if (!selectionModel.isEmpty()) {
            Aluno aluno = alunoSelected(selectionModel);
            final File file = writeBinaryIntoFile(aluno);
            telaCadastro(null);
            setFieldToUpdate(aluno);
            image = new Image(file.getAbsoluteFile().toURI().toString());
            imgView.setImage(image);
        } else {
            Nota.alerta("Voce precisa Selecionar um Aluno na Tabela!");
        }
    }

    private void setFieldToUpdate(Aluno aluno) {
        txtNome.setText(aluno.getNome());
        dtNascimento.setValue(aluno.getDataNascimento());
        txtNumBI.setText(aluno.getNumBI());
        dtEmissao.setValue(aluno.getDataEmisao());
        txtResidencia.setText(aluno.getResidencia());
        txtConselho.setText(aluno.getConselho());
        txtNatural.setText(aluno.getNatural());
        txtEmail.setText(aluno.getEmail());
        txtContacto.setText(aluno.getContato());
        cbxHabilitacao.setValue(aluno.getHabilitacaoLit());
        txtNacionalidade.setText(aluno.getNacionalidade());
        txtDescricao.setText(aluno.getDescricao());
        txtNomeDaMae.setText(aluno.getNomeDaMae());
        txtNomeDoPai.setText(aluno.getNomeDoPai());
        txtProfessao.setText(aluno.getProfessao());
        cbEstadoCivil.setValue(aluno.getEstadoCivil());
        txtLocalDeEmisao.setText(aluno.getLocalDeEmisao());
        txtFreguesia.setText(aluno.getFreguesia());
        lbTitulo.setText("Editar Aluno");
        menu.selectToggle(menu.getToggles().get(1));
        idAluno = aluno.getIdAluno();
    }

    private File writeBinaryIntoFile(Aluno aluno) {
        String fileSeparator = File.separator;
        final String pathDiretorio = System.getProperty("user.home") + fileSeparator
                + "Documents/ecos/aluno/";
        File directorio = new File(pathDiretorio);
        String filePath = pathDiretorio + fileSeparator + aluno.getNumBI() + ".jpg";
        File file = new File(filePath);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);) {
            byte[] buffer = new byte[1024];
            final InputStream foto = aluno.getFoto();
            if (foto != null) {
                while (foto.read(buffer) > 0) {
                    fileOutputStream.write(buffer);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return file;
    }

    @FXML
    private void excluir(ActionEvent event) {
        TableViewSelectionModel<Aluno> selectionModel = tableViewSelectionModel();
        if (!selectionModel.isEmpty()) {
            Aluno aluno = alunoSelected(selectionModel);
            Dialogo.Resposta response = Mensagem.confirmar("Excluir Aluno: " + aluno.getNome() + " ?");
            if (response == Dialogo.Resposta.YES) {
                DAOFactory.daoFactury().alunoDAO().delete(aluno.getIdAluno());
                sincronizarDataBase();
                mapTableAluno();
            }
            tbAluno.getSelectionModel().clearSelection();
        } else {
            Nota.alerta("Voce precisa Selecionar um Aluno na Tabela!");
        }
    }

    private Aluno alunoSelected(TableViewSelectionModel<Aluno> selectionModel) {
        Aluno alunoSelected = selectionModel.getSelectedItem();
        alunoSelected.getClass();
        return alunoSelected;
    }

    private TableViewSelectionModel<Aluno> tableViewSelectionModel() {
        final TableViewSelectionModel<Aluno> selectionModel = tbAluno.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        return selectionModel;
    }

    private void sincronizarDataBase() {
        listaAluno = DAOFactory.daoFactury().alunoDAO().findAll();
    }

    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, btImprimir,
                telaCadastro, telaEdicao, telaView, txtPesquisar);
        legenda.setText(msg);
        tbAluno.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idAluno = 0;
    }

    private void limparCampos() {
        Aluno aluno = new Aluno();
        Campo.limpar(txtEmail, txtNumBI, txtResidencia, txtNome, txtNacionalidade,
                txtContacto, txtConselho, txtNatural, txtNomeDaMae, txtNomeDoPai,
                txtProfessao, txtLocalDeEmisao, txtFreguesia, txtNomeDoPai, txtNomeDaMae);
        Campo.limpar(txtDescricao);
        imgView.setImage(new Image(defualtImage));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mapTableAluno();
        telaVisualizacao(null);
        Tempo.blockDataPosterior(LocalDate.now().plusYears(100), dtNascimento);

        scrollPane.viewportBoundsProperty().addListener(
                (ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) -> {
                    flowPane.setPrefWidth(newValue.getWidth());
                });

        sincronizarDataBase();
        loadAlunos();
        pagination.setPageFactory((Integer pagina) -> {
            atualizarGrade(pagina);
            return tbAluno;
        });

        imgView.setOnMouseClicked((event) -> {
            adcionarFoto(null);
        });

        hlAnexarFoto.setOnMouseClicked((event) -> {
            adcionarFoto(null);
        });

        Grupo.notEmpty(menu);
        combos();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaAluno));
        });

        tooltip();

        tbAluno.setOnMouseClicked((event) -> {
            if (tbAluno.getSelectionModel().getSelectedItem() != null) {
                if (event.getClickCount() == 2) {
                    imprimir(null);
                }
            }
        });

        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        colDataNacimento.setCellFactory(column -> {
            return new TableCell<Aluno, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(myDateFormatter.format(item));
                        if (item.getMonthValue() == LocalDate.now().getMonthValue()) {
                            setTextFill(Color.CHOCOLATE);
                            setStyle("-fx-background-color: yellow;");
                        } else {
                            setTextFill(Color.BLACK);
                            setStyle("");
                        }
                    }
                }
            };
        });

        atualizarGrade(0);
    }

    private void tooltip() {
        btAtualizar.setTooltip(new Tooltip("Atualizar registros"));
        btAdcionar.setTooltip(new Tooltip("Adicionar registros"));
        btApagar.setTooltip(new Tooltip("Apagar registros"));
        btImpr.setTooltip(new Tooltip("Imprimir registros"));
        btEdit.setTooltip(new Tooltip("Editar registros"));
    }

    private void mapTableAluno() {
        colId.setCellValueFactory((CellDataFeatures<Aluno, Integer> obj) -> obj.getValue().idAlunoProperty().asObject());
        colNome.setCellValueFactory((CellDataFeatures<Aluno, String> obj) -> obj.getValue().nomeProperty());
        colDataNacimento.setCellValueFactory((CellDataFeatures<Aluno, LocalDate> obj) -> obj.getValue().dataNascimentoProperty());
        colPassaport.setCellValueFactory((CellDataFeatures<Aluno, String> obj) -> obj.getValue().numBIProperty());
        colDataEmisao.setCellValueFactory((CellDataFeatures<Aluno, LocalDate> obj) -> obj.getValue().dataEmisaoProperty());
        colResidencia.setCellValueFactory((CellDataFeatures<Aluno, String> obj) -> obj.getValue().residenciaProperty());
        colConselho.setCellValueFactory((CellDataFeatures<Aluno, String> p) -> p.getValue().conselhoProperty());
        colFreguesia.setCellValueFactory((CellDataFeatures<Aluno, String> p) -> p.getValue().freguesiaProperty());
        colNaturalidade.setCellValueFactory((CellDataFeatures<Aluno, String> obj) -> obj.getValue().naturalProperty());
        colEmail.setCellValueFactory((CellDataFeatures<Aluno, String> obj) -> obj.getValue().emailProperty());
        colContacto.setCellValueFactory((CellDataFeatures<Aluno, String> obj) -> obj.getValue().contatoProperty());
        colHabilitacao.setCellValueFactory((CellDataFeatures<Aluno, String> obj) -> obj.getValue().habilitacaoLitProperty());
        colNacionalidade.setCellValueFactory((CellDataFeatures<Aluno, String> obj) -> obj.getValue().nacionalidadeProperty());
        colDescricao.setCellValueFactory((CellDataFeatures<Aluno, String> obj) -> obj.getValue().descricaoProperty());
        colEstadoCivil.setCellValueFactory((CellDataFeatures<Aluno, String> obj) -> obj.getValue().estadoCivilProperty());
        colNomeDaMae.setCellValueFactory((CellDataFeatures<Aluno, String> obj) -> obj.getValue().nomeDaMaeProperty());
        colNomeDoPai.setCellValueFactory((CellDataFeatures<Aluno, String> obj) -> obj.getValue().nomeDoPaiProperty());
        colLocalDeEmisao.setCellValueFactory((CellDataFeatures<Aluno, String> obj) -> obj.getValue().localDeEmisaoProperty());
        colProfessao.setCellValueFactory((CellDataFeatures<Aluno, String> obj) -> obj.getValue().professaoProperty());
        colDataCadastro.setCellValueFactory((CellDataFeatures<Aluno, LocalDate> obj) -> obj.getValue().dataCadastroProperty());
        colFoto.setCellValueFactory(new PropertyValueFactory<>("foto"));
    }

    /**
     * Preencher combos tela
     */
    private void combos() {
        Combo.popular(cbxHabilitacao, "Basico incompleto", "Basico completo",
                "Médio incompleto", "Médio completo", "Superior incompleto",
                "Superior completo", "Pòs-graduação completo", "Pòs-graduação incompleto",
                "Mestrado incompleto", "Mestrado completo", "Doutorado incompleto",
                "Doutorado completo");
        Combo.popular(cbEstadoCivil, "Solteiro(a)", "Casado(a)", "Viuvo(a)", "Disvorciado(a)");
    }

    private void filtro(String valor, ObservableList<Aluno> listaAluno) {
        FilteredList<Aluno> dadosFiltrados = new FilteredList<>(listaAluno, aluno -> true);
        dadosFiltrados.setPredicate(aluno -> {
            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (aluno.getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (aluno.getEmail().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (aluno.getNumBI().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }
            return false;
        });
        SortedList<Aluno> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbAluno.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de aluno encontradas");
        tbAluno.setItems(dadosOrdenados);
    }

    // validação de e-mail
    private boolean emailValidate() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(txtEmail.getText());
        if (m.find() && m.group().equals(txtEmail.getText()) || txtEmail.getText().isEmpty()) {
            return true;
        } else {
            Mensagem.erro("Por favor! digite um e-mail valido", "Erro de Validaçao");
            return false;
        }
    }

    // validação de numero de BI
    private boolean biValidate() {
        Pattern p = Pattern.compile("([A-Z]{0,1})*[0-9]{6,7}");
        Matcher m = p.matcher(txtNumBI.getText());
        if (m.find() && m.group().equals(txtNumBI.getText())) {
            return true;
        } else {
            Mensagem.erro("Por favor! digite um numero de Nº BI/Passaporte valido.", "Erro de Validação");
            return false;
        }
    }

    // validação de numero de telefone
    private boolean foneValidate() {
        Pattern p = Pattern.compile("[5,9][0-9]{6}");
        Matcher m = p.matcher(txtContacto.getText());
        if (m.find() && m.group().equals(txtContacto.getText())) {
            return true;
        } else {
            Mensagem.erro("Por favor! digite um numero de telefone valido", "Erro de Validação");
            return false;
        }
    }

    @FXML
    private void imprimir(ActionEvent event) {
        TableViewSelectionModel<Aluno> selectionModel = tableViewSelectionModel();
        if (!selectionModel.isEmpty()) {
            Aluno aluno = alunoSelected(selectionModel);
            Dialogo.Resposta response = Mensagem.confirmar("Imprimir Requiremento para:: " + aluno.getNome() + " ?");
            if (response == Dialogo.Resposta.YES) {
                DAOFactory.daoFactury().alunoDAO().reportRequiremento(aluno.getNumBI());
            }
            tbAluno.getSelectionModel().clearSelection();
        } else {
            Nota.alerta("Voce precisa Selecionar um Aluno na Tabela!");
        }
    }

    @FXML
    private void onKeyReleasedBuscarAluno(KeyEvent event) {
        if (txtBuscar.getText().isEmpty() || txtBuscar.getText().trim().length() == 0) {
            studantDefault = true;
            loadAlunos();
        } else {
            searchAluno(txtBuscar.getText().trim());
        }
    }

    @FXML
    private void onActionBuscar(ActionEvent event) {
        if (txtBuscar.getText() == null || txtBuscar.getText().trim().length() == 0) {
            studantDefault = true;
            loadAlunos();

        } else {
            searchAluno(txtBuscar.getText().trim());
        }
    }

    @FXML
    private void onMouseClickedAnterior(MouseEvent event) {
        if (paginas.getInicio() > 0) {
            paginas.setInicio(paginas.getInicio() - paginas.getPerPag());
            paginas.setFim(paginas.getInicio() + paginas.getPerPag());
            if (studantDefault) {
                loadAlunos();
            } else {
                searchAluno(txtBuscar.getText().trim());
            }
        } else {
            onMouseClickedProximo(event);
        }
    }

    @FXML
    private void onMouseClickedProximo(MouseEvent event) {
        paginas.setInicio(paginas.getInicio() + paginas.getPerPag());
        paginas.setFim(paginas.getInicio() + paginas.getPerPag());
        if (studantDefault) {
            loadAlunos();
            if (!student.isEmpty()) {
                paginas.setInicio(1);
                paginas.setFim(10);
                loadAlunos();
            }
        } else {
            searchAluno(txtBuscar.getText().trim());
            if (student.isEmpty()) {
                paginas.setInicio(1);
                paginas.setFim(10);
                searchAluno(txtBuscar.getText().trim());
            }
        }

    }

    private void loadAlunos() {
        studantDefault = true;
        flowPane.getChildren().clear();
        totalAluno = DAOFactory.daoFactury().alunoDAO().count();
        listaAluno.parallelStream()
                .limit(30)
                .forEachOrdered((aluno) -> {
                    AnchorPane card = new AlunoCard(aluno);
                    flowPane.getChildren().add(card);
                });

//        Pagination pag = new Pagination();
//        pag.setPageCount(21);
//        pag.setCurrentPageIndex(0);
//        pag.setMaxPageIndicatorCount(3);
//        pag.setPageFactory((Integer pageIndex) -> {
//            FlowPane flow = new FlowPane();
//            listaAluno.stream()
//                    .parallel()
//                    .limit(20)
//                    .skip(20)
//                    .forEachOrdered((aluno) -> {
//                        AnchorPane card = new AlunoCard(aluno);
//                        flow.getChildren().add(card);
//                    });
//            return flow;
//        });
//        vBox.getChildren().add(pag);
    }

    private void searchAluno(String query) {
        studantDefault = false;
        flowPane.getChildren().clear();
        listaAluno.stream()
                .filter((a) -> a.getNome().startsWith(query))
                .forEach((aluno) -> {
            AnchorPane card = new AlunoCard(aluno);
            flowPane.getChildren().add(card);
        });
    }

    private void atualizarGrade(int pagina) {
        totalAluno = DAOFactory.daoFactury().alunoDAO().count();
        pagination.setPageCount((int) Math.ceil(((double) totalAluno)) / QUANTIDADE_PAGINA);
        alunos = DAOFactory.daoFactury().alunoDAO().listar(QUANTIDADE_PAGINA, pagina);
        tbAluno.setItems(alunos);
    }

    private static void esperar(long milesegundos) {
        try {
            Thread.sleep(milesegundos);
        } catch (InterruptedException e) {

        }
    }
}
