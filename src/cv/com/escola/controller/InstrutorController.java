package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Instrutor;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Combo;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import cv.com.escola.model.util.Nota;
import static cv.com.escola.model.util.ValidationFields.checkEmptyFields;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class InstrutorController extends AnchorPane implements Initializable {

    private List<Instrutor> listInstrutor;
    private int idInstrutor;
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
    private TextField txtNomeCompleto;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtNacionalidade;
    @FXML
    private TextField txtBiNif;
    @FXML
    private DatePicker dataNascimento;
    @FXML
    private TextField txtContato;
    @FXML
    private TextField txtAreaDoc;
    @FXML
    private DatePicker dtAdmisao;
    @FXML
    private TextField txtNomeDoPai;
    @FXML
    private TextField txtNomeDaMae;
    @FXML
    private ComboBox<String> cbHabilitacao;
    @FXML
    private TextField txtTipoSanguineo;
    @FXML
    private TextField txtZonaRua;
    @FXML
    private TextField txtCelular;
    @FXML
    private TextField txtCidadeIlha;
    @FXML
    private TextArea taObservacao;
    @FXML
    private Hyperlink hlAnexarDoc;
    @FXML
    private ImageView imgFoto;
    @FXML
    private ListView<Instrutor> listViewFuncionario;
    @FXML
    private TextField txtBuscar;
    @FXML
    private TextField txtNaturalidade;
    @FXML
    private TextField txtCartaConducao;
    @FXML
    private TextField txtBanco;
    @FXML
    private TextField txtContaCorente;
    @FXML
    private TextField txtAgencia;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableView<Instrutor> tbInstrutor;
    @FXML
    private TableColumn<Instrutor, ImageView> colFoto;
    @FXML
    private TableColumn<Instrutor, Long> colId;
    @FXML
    private TableColumn<Instrutor, String> colNome;
    @FXML
    private TableColumn<Instrutor, String> colEndereco;
    @FXML
    private TableColumn<Instrutor, String> colCelular;
    @FXML
    private TableColumn<Instrutor, String> colTelefone;
    @FXML
    private TableColumn<Instrutor, String> colEmail;
    @FXML
    private TableColumn<Instrutor, String> colHabilitacao;
    @FXML
    private TableColumn<Instrutor, String> colNif;
    @FXML
    private TableColumn<Instrutor, String> colTipoSaguineo;
    @FXML
    private TableColumn<Instrutor, String> colCidade;
    @FXML
    private TableColumn<Instrutor, String> colNomePai;
    @FXML
    private TableColumn<Instrutor, String> colNomeMae;
    @FXML
    private TableColumn<Instrutor, String> colNacionalidade;
    @FXML
    private TableColumn<Instrutor, String> colNaturalidade;
    @FXML
    private TableColumn<Instrutor, LocalDate> colDataNascimento;
    @FXML
    private TableColumn<Instrutor, LocalDate> colDataAdmisao;
    @FXML
    private TableColumn<Instrutor, String> colCarta;
    @FXML
    private TableColumn<Instrutor, String> colBanco;
    @FXML
    private TableColumn<Instrutor, String> colAgencia;
    @FXML
    private TableColumn<Instrutor, String> colContaCorente;
    @FXML
    private TableColumn<Instrutor, String> colObservacao;
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

    private File file = null;
    private File copyFile = null;
    private FileChooser fileChooser = new FileChooser();
    private FileChooser.ExtensionFilter extensionFilterJPG = new FileChooser.ExtensionFilter("JPG Files(*.jpg)", "*.JPG");
    private FileChooser.ExtensionFilter extensionFilterPNG = new FileChooser.ExtensionFilter("PNG Files(*.PNG)", "*.PNG");
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
    private String n = LocalDateTime.now().format(formatter);
    private String sep = File.separator;
    private File diretorio = new File("public/img/instrutor/");
    private Image img = new Image("/cv/com/escola/view/img/sem_foto_0.gif");
    
    private static final String QUANTIDADE_DE_INSTRUTOR_ENCONTRADOS = "Quantidade de Instrutor encontrados";

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sincronizarBase();
        combos();
        telaEdicao(null);
        colFoto.setVisible(false);
        imgFoto.setOnMouseClicked(event -> uploadImage());

        colNome.setCellFactory(column -> new TableCell<Instrutor, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                    }
                }
            }
        );
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public InstrutorController() {
        try {
            GenericFXXMLLoader.loadFXML(this, "instrutor");
        } catch (IOException ex) {
            Logger.getLogger(InstrutorController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela instrutor!");
        }
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listInstrutor = DAOFactory.daoFactury().instrutorDAO().findAll();
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btImprimir, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbInstrutor.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));
        idInstrutor = 0;
    }

    /**
     * Preencher combos tela
     */
    private void combos() {
        Combo.popular(cbHabilitacao, "Fundamental incompleto", "Fundamental completo", "Médio incompleto", "Médio completo", "Superior incompleto", "Superior completo", "Pòs-graduação completo", "Pòs-graduação incompleto", "Mestrado incompleto", "Mestrado completo", "Doutorado incompleto", "Doutorado completo");
    }

    public void listView() {
        ObservableList data = FXCollections.observableList(listInstrutor);
        listViewFuncionario.setItems(data);
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listInstrutor);

        colFoto.setCellValueFactory(new PropertyValueFactory<>("imgView"));
        colDataAdmisao.setCellValueFactory((CellDataFeatures<Instrutor, LocalDate> obj) -> new SimpleObjectProperty(obj.getValue().getAdmissao()));
        colNome.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getNome()));
        colNomePai.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getNomeDoPai()));
        colNomeMae.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getNomeDaMae()));
        colHabilitacao.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getGrauAcademico()));
        colTipoSaguineo.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getTipoSanguineo()));
        colEmail.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getEmail()));
        colEndereco.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getMorada()));
        colCidade.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getCidadeIlha()));
        colTelefone.setCellValueFactory((TableColumn.CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getContactoTelefonico()));
        colCelular.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getContactoMovel()));
        colNif.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getNumeroDeIndentificacao()));
        colNacionalidade.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getNacionalidade()));
        colNaturalidade.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getNaturalidade()));
        colDataNascimento.setCellValueFactory((CellDataFeatures<Instrutor, LocalDate> obj) -> new SimpleObjectProperty(obj.getValue().getNascimento()));
        colCarta.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getCartaConducao()));
        colBanco.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getBanco()));
        colAgencia.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getAgencia()));
        colContaCorente.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getNumDeConta()));
        colObservacao.setCellValueFactory((CellDataFeatures<Instrutor, String> obj) -> new SimpleStringProperty(obj.getValue().getObservacao()));

        tbInstrutor.setItems(data);
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limparCampos() {
        Campo.limpar(txtEmail, txtAgencia, txtBiNif, txtCartaConducao, txtCidadeIlha, txtBanco, txtNomeCompleto, txtNomeDaMae, txtNomeDoPai, txtCelular, txtTelefone, txtZonaRua, txtContaCorente, txtNaturalidade, txtNacionalidade, txtTipoSanguineo);
        Campo.limpar(taObservacao);

        dataNascimento.getEditor().setText("");
        dtAdmisao.getEditor().setText("");
        imgFoto.setImage(img);
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Instrutor", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampos();
        listView();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("INSTRUTOR", QUANTIDADE_DE_INSTRUTOR_ENCONTRADOS, 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Excluir Instrutor", QUANTIDADE_DE_INSTRUTOR_ENCONTRADOS, 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    public void telaAtualizar(MouseEvent event) {
        sincronizarBase();
    }

    @FXML
    public void telaImprimir(ActionEvent event) {
        configTela("Imprimir Dados do Instrutor", QUANTIDADE_DE_INSTRUTOR_ENCONTRADOS, 3);
        Modulo.visualizacao(true, telaEdicao, btImprimir, txtPesquisar);
        tabela();
    }

    @FXML
    private void anexarDocOnAction(ActionEvent event) {
        throw new UnsupportedOperationException();
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean emptyFields = checkEmptyFields(txtNomeCompleto, txtNomeDoPai, txtNomeDaMae, txtEmail, txtZonaRua, txtCidadeIlha, txtCelular, txtBiNif,
                txtNacionalidade, dtAdmisao, dataNascimento, cbHabilitacao, txtCartaConducao);

        String nome = txtNomeCompleto.getText();
        LocalDate dataAdmisao = dtAdmisao.getValue();
        String nomePai = txtNomeDoPai.getText();
        String nomeMae = txtNomeDaMae.getText();
        String escolaridade = cbHabilitacao.getValue();
        String tipoSanguineo = txtTipoSanguineo.getText();

        String email = txtEmail.getText();
        String endereco = txtZonaRua.getText();
        String cidade = txtCidadeIlha.getText();
        String telefone = txtTelefone.getText();
        String celular = txtCelular.getText();

        String nif = txtBiNif.getText();
        String nacionalidade = txtNacionalidade.getText();
        String naturalidade = txtNaturalidade.getText();
        LocalDate datNascimento = dataNascimento.getValue();
        String cartaConducao = txtCartaConducao.getText();

        String banco = txtBanco.getText();
        String agencia = txtAgencia.getText();
        String contaCorenta = txtContaCorente.getText();
        String observacao = taObservacao.getText();

        if (emptyFields) {
            Instrutor instrutor = new Instrutor((long) idInstrutor, nome, dataAdmisao, email, telefone, celular, copyImage(), nomePai, nomeMae,
                    escolaridade, tipoSanguineo, endereco, cidade, nif, naturalidade,
                    nacionalidade, datNascimento, cartaConducao, banco, agencia,
                    contaCorenta, observacao);

            if (idInstrutor == 0) {
                DAOFactory.daoFactury().instrutorDAO().create(instrutor);
                listView();
            } else {
                DAOFactory.daoFactury().instrutorDAO().update(instrutor);
                listView();
            }

            telaCadastro(null);
            sincronizarBase();

        }

    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            Instrutor instrutor = tbInstrutor.getSelectionModel().getSelectedItem();
            instrutor.getClass();

            telaCadastro(null);

            txtNomeCompleto.setText(instrutor.getNome());
            dtAdmisao.setValue(instrutor.getAdmissao());

            txtNomeDoPai.setText(instrutor.getNomeDoPai());
            txtNomeDaMae.setText(instrutor.getNomeDaMae());
            cbHabilitacao.setValue(instrutor.getGrauAcademico());
            txtTipoSanguineo.setText(instrutor.getTipoSanguineo());

            txtEmail.setText(instrutor.getEmail());
            txtZonaRua.setText(instrutor.getMorada());
            txtCidadeIlha.setText(instrutor.getCidadeIlha());
            txtTelefone.setText(instrutor.getContactoTelefonico());
            txtCelular.setText(instrutor.getContactoMovel());

            txtBiNif.setText(instrutor.getNumeroDeIndentificacao());
            txtNacionalidade.setText(instrutor.getNacionalidade());
            txtNaturalidade.setText(instrutor.getNaturalidade());
            dataNascimento.setValue(instrutor.getNascimento());
            txtCartaConducao.setText(instrutor.getCartaConducao());

            txtBanco.setText(instrutor.getBanco());
            txtAgencia.setText(instrutor.getAgencia());
            txtContaCorente.setText(instrutor.getNumDeConta());
            taObservacao.setText(instrutor.getObservacao());

            lbTitulo.setText("Editar Instrutor");
            menu.selectToggle(menu.getToggles().get(1));

            idInstrutor = (int) instrutor.getId();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um instrutor na tabela para edição!");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        try {
            Instrutor instrutor = tbInstrutor.getSelectionModel().getSelectedItem();

            Dialogo.Resposta response = Mensagem.confirmar("Excluir Instrutor " + instrutor.getNome() + " ?");

            if (response == Dialogo.Resposta.YES) {
                DAOFactory.daoFactury().instrutorDAO().delete(instrutor.getId());
                File diretorios = new File("public/img/instrutor/" + sep + instrutor.getFoto());
                if (diretorios.exists()) {
                    Files.delete(diretorios.toPath());
                }
                sincronizarBase();
                tabela();
            }
            tbInstrutor.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione instrutor na tabela para exclusão!");
        } catch (IOException ex) {
            Logger.getLogger(InstrutorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void imprimir(ActionEvent event) {
        throw new UnsupportedOperationException();
    }

    public String copyImage() {
        try {
            if (!diretorio.exists()) {
                diretorio.mkdirs();
            }
            copyFile = new File(diretorio.getPath() + sep + n + "-" + file.getName());
            Files.copy(Paths.get(file.getPath()), Paths.get(copyFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(ArtigoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return copyFile.getName();
    }

    public void uploadImage() {
        try {
            fileChooser.getExtensionFilters().addAll(extensionFilterJPG, extensionFilterPNG);
            fileChooser.setTitle("Upload Image");
            file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imgFoto.setImage(image);
            }
        } catch (IOException ex) {
            Logger.getLogger(InstrutorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public File getCopyFile() {
        return copyFile;
    }

    public void setCopyFile(File copyFile) {
        this.copyFile = copyFile;
    }
}
