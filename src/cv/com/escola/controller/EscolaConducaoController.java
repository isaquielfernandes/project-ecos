package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.EscolaConducao;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Filtro;
import cv.com.escola.model.util.Grupo;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import cv.com.escola.model.util.Nota;
import static cv.com.escola.model.util.ValidationFields.checkEmptyFields;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

public class EscolaConducaoController extends AnchorPane implements Initializable {

    private List<EscolaConducao> listaEmpresa;
    private int idEmpresa = 0;
    private Image imagelogo;
    private Image imageAssinatura;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private ToggleGroup menu;
    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextField txtCidade;
    @FXML
    private TextField txtNomeEscola;
    @FXML
    private TextField txtEndereco;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtContato;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private TextField txtNif;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableView<EscolaConducao> tbEmpresa;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colCidade;
    @FXML
    private TableColumn<?, ?> colNome;
    @FXML
    private TableColumn<?, ?> colNif;
    @FXML
    private TableColumn<?, ?> colEndereco;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private TableColumn<?, ?> colContacto;
    @FXML
    private TableColumn<?, ?> colDescricao;
    @FXML
    private Button btSalvar;
    @FXML
    private Button btEditar;
    @FXML
    private Button btExcluir;
    @FXML
    private Label legenda;
    @FXML
    private ImageView imageViewLogo;
    @FXML
    private ImageView imageViewAssinatura;
    @FXML
    private TextField txtID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarDataBase();
        txtID.setText(String.valueOf(idEmpresa));

        imageViewLogo.setOnMouseClicked((event) -> {
            uploadLogo();
        });

        imageViewAssinatura.setOnMouseClicked((event) -> {
            uploadFileAssinatura();
        });

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaEmpresa));
        });
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public EscolaConducaoController() {

        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/cv/com/escola/view/dadosIniciais.fxml"));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(EscolaConducaoController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela dados inicial da empresa! \n" + ex);
        }
    }

    private void sincronizarDataBase() {
        listaEmpresa = DAOFactory.daoFactury().empresaDAO().findAll();
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbEmpresa.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idEmpresa = 0;
    }

    private void limparCampos() {
        Campo.limpar(txtCidade, txtNomeEscola, txtContato, txtEmail, txtNif, txtEndereco);
        Campo.limpar(txtDescricao);
        imageViewLogo.setImage(null);
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Empresa", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("Editar Empresa", "Quantidade de empresa encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Excluir Empresa", "Quantidade de empresa encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean vazio = checkEmptyFields(txtCidade, txtNomeEscola, txtNif, txtEmail, txtContato, txtEndereco);

        String nomeEscola = txtNomeEscola.getText();
        String cidade = txtCidade.getText();
        String endereco = txtEndereco.getText();
        String nif = txtNif.getText();
        String contato = txtContato.getText();
        String email = txtEmail.getText();
        String descricao = txtDescricao.getText();
        idEmpresa = Integer.valueOf(txtID.getText().trim().isEmpty() ? "0" : txtID.getText());

        InputStream inputStreamLogo = converterImageFileToInputStream(imageViewLogo);
        InputStream inputStreamAssinatura = converterImageFileToInputStream(imageViewAssinatura);

        if (vazio) {
            EscolaConducao escolaConducao = new EscolaConducao(idEmpresa, nomeEscola, cidade, endereco, nif, contato, email, descricao);
            escolaConducao.setLogo(inputStreamLogo);
            escolaConducao.setAssinatura(inputStreamAssinatura);

            if (idEmpresa == 0) {
                DAOFactory.daoFactury().empresaDAO().create(escolaConducao);
                Mensagem.info("Configuracao da empresa cadastrada com sucesso!");
            } else {
                DAOFactory.daoFactury().empresaDAO().update(escolaConducao);
                Mensagem.info("Empresa atualizada com sucesso!");
            }
            telaCadastro(null);
            sincronizarDataBase();
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        TableViewSelectionModel<EscolaConducao> selectionModel = tableViewSelectionModel();
        if (!selectionModel.isEmpty()) {
            EscolaConducao escolaConducao = empresaSelected(selectionModel);
            final File fileLogo = writeFile(escolaConducao.getLogo(), "logo");
            final File fileAssinatura = writeFile(escolaConducao.getAssinatura(), "assinatura");
            telaCadastro(null);
            imagelogo = new Image(fileLogo.getAbsoluteFile().toURI().toString());
            imageViewLogo.setImage(imagelogo);
            imageAssinatura = new Image(fileAssinatura.getAbsoluteFile().toURI().toString());
            imageViewAssinatura.setImage(imageAssinatura);
            telaCadastro(null);

            txtID.setText(String.valueOf(escolaConducao.getIdEmpresa()));
            txtCidade.setText(escolaConducao.getCidade());
            txtNomeEscola.setText(escolaConducao.getNome());
            txtNif.setText(escolaConducao.getNif());
            txtEndereco.setText(escolaConducao.getEndereco());
            txtEmail.setText(escolaConducao.getEmail());
            txtContato.setText(escolaConducao.getContato());
            txtDescricao.setText(escolaConducao.getDescricao());

            lbTitulo.setText("Editar Empresa");
            menu.selectToggle(menu.getToggles().get(1));

            idEmpresa = escolaConducao.getIdEmpresa();

        } else {
            Nota.alerta("Selecione uma despesa na tabela para edição!");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {

        ObservableList data = FXCollections.observableArrayList(listaEmpresa);

        colId.setCellValueFactory(new PropertyValueFactory<>("idEmpresa"));
        colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNif.setCellValueFactory(new PropertyValueFactory<>("nif"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContacto.setCellValueFactory(new PropertyValueFactory<>("contato"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tbEmpresa.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtro(String valor, ObservableList<EscolaConducao> listaEmpresa) {

        FilteredList<EscolaConducao> dadosFiltrados = new FilteredList<>(listaEmpresa, empresa -> true);
        dadosFiltrados.setPredicate(empresa -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (empresa.getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (empresa.getEndereco().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }
            return false;
        });

        SortedList<EscolaConducao> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbEmpresa.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de empresa encontradas");

        tbEmpresa.setItems(dadosOrdenados);
    }

    public void uploadLogo() {
        File selectedFile = filechooser();
        if (selectedFile == null) {
            return;
        }
        final int maxImageSize = 6000000;
        if (selectedFile.length() < maxImageSize) {
            imagelogo = new Image(selectedFile.getAbsoluteFile().toURI().toString(),
                    imageViewLogo.getFitWidth(), imageViewLogo.getFitHeight(), true, true);
            imageViewLogo.setImage(imagelogo);
        } else {
            alearta();
        }
    }

    public void uploadFileAssinatura() {
        File selectedFile = filechooser();
        if (selectedFile == null) {
            return;
        }

        final int maxImageSize = 6000000;
        if (selectedFile.length() < maxImageSize) {
            imageAssinatura = new Image(selectedFile.getAbsoluteFile().toURI().toString(),
                    imageViewAssinatura.getFitWidth(), imageViewAssinatura.getFitHeight(), true, true);
            imageViewAssinatura.setImage(imageAssinatura);
        } else {
            alearta();
        }
    }

    private void alearta() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Permissao");
        alert.setHeaderText("Permisao negada");
        alert.setContentText("Your Image file is too big to upload \n please choise another image");
        alert.initStyle(StageStyle.UNDECORATED);
    }

    private File writeFile(InputStream inputStream, String fileName) {
        String fileSeparator = File.separator;
        final String pathDiretorio = System.getProperty("user.home")
                + fileSeparator + "Documents/ecos/config/";
        File directory = new File(pathDiretorio);
        String filePath = pathDiretorio + fileSeparator + fileName + ".jpg";
        File newFile = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(newFile);) {
            byte[] buffer = new byte[1024];
            if (inputStream != null) {
                while (inputStream.read(buffer) > 0) {
                    fileOutputStream.write(buffer);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return newFile;
    }

    private InputStream converterImageFileToInputStream(ImageView imageView) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
            ImageIO.write(bufferedImage, "jpg", outputStream);
            byte[] stream = outputStream.toByteArray();
            return new ByteArrayInputStream(stream);
        } catch (IOException ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private File filechooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image File", "*.png", "*.jpg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            return selectedFile;
        }
        return null;
    }

    private TableViewSelectionModel<EscolaConducao> tableViewSelectionModel() {
        final TableViewSelectionModel<EscolaConducao> selectionModel = tbEmpresa.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        return selectionModel;
    }

    private EscolaConducao empresaSelected(TableViewSelectionModel<EscolaConducao> selectionModel) {
        EscolaConducao escolaSelected = selectionModel.getSelectedItem();
        escolaSelected.getClass();
        return escolaSelected;
    }
}
