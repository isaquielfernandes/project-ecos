/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Empresa;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Filtro;
import cv.com.escola.model.util.Grupo;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import cv.com.escola.model.util.Nota;
import static cv.com.escola.model.util.ValidationFields.checkEmptyFields;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class DadosIniciaisController extends AnchorPane implements Initializable {

    private List<Empresa> listaEmpresa;
    private int idEmpresa = 0;
    private Image image;
    private File fileLogo;
    private File fileAssinatura;
    private FileOutputStream fileOutput;
    private FileInputStream fileInput;
    private byte[] userImage;
    private String imgPath;
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
    private TableView<Empresa> tbEmpresa;
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
    private ImageView imageViewBarnner;
    @FXML
    private ImageView imageViewAssinatura;
    @FXML
    private TextField txtID;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarBase();
        txtID.setText(String.valueOf(idEmpresa));

        imageViewBarnner.setOnMouseClicked((event) -> {
            try {
                uploadLogo();
            } catch (IOException ex) {
                Logger.getLogger(DadosIniciaisController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        imageViewAssinatura.setOnMouseClicked((event) -> {
            try {
                uploadFileAssinatura();
            } catch (IOException ex) {
                Logger.getLogger(DadosIniciaisController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaEmpresa));
        });
    }

    public void uploadLogo() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fotografia", "*.png", "*.jpg"));

        fileLogo = fileChooser.showOpenDialog(null);

        if (fileLogo != null) {
            if (fileLogo.length() < 6000000) {
                try {
                    fileInput = new FileInputStream(fileLogo);
                    image = new Image(fileLogo.getAbsoluteFile().toURI().toString(), imageViewBarnner.getFitWidth(), imageViewBarnner.getFitHeight(), true, true);
                    imageViewBarnner.setImage(image);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Mensagem.info("Permissão", "Sua imagem file é muito grande para upload \n Por favor escolha outra image");
            }

        }
    }

    public void uploadFileAssinatura() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fotografia", "*.png", "*.jpg"));

        fileAssinatura = fileChooser.showOpenDialog(null);

        if (fileAssinatura != null) {
            if (fileAssinatura.length() < 6000000) {
                try {
                    fileInput = new FileInputStream(fileAssinatura);
                    image = new Image(fileAssinatura.getAbsoluteFile().toURI().toString(), imageViewAssinatura.getFitWidth(), imageViewAssinatura.getFitHeight(), true, true);
                    imageViewAssinatura.setImage(image);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(DadosIniciaisController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Mensagem.info("Permissão", "Sua imagem file é muito grande para upload \n Por favor escolha outra image");
            }

        }
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public DadosIniciaisController() {

        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/cv/com/escola/view/dadosIniciais.fxml"));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(DadosIniciaisController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela dados inicial da empresa! \n" + ex);
        }
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
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

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limparCampos() {
        Campo.limpar(txtCidade, txtNomeEscola, txtContato, txtEmail, txtNif, txtEndereco);
        Campo.limpar(txtDescricao);
        imageViewBarnner.setImage(null);
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Empresa", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        //limparCampos();
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

        if (vazio) {
            Empresa empresa = new Empresa(idEmpresa, nomeEscola, cidade, endereco, nif, contato, email, descricao, fileLogo);
            empresa.setFileCarimboAssinatura(fileAssinatura);

            if (idEmpresa == 0) {
                DAOFactory.daoFactury().empresaDAO().create(empresa);
            } else {
                DAOFactory.daoFactury().empresaDAO().update(empresa);
            }
            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            Empresa empresa = tbEmpresa.getSelectionModel().getSelectedItem();
            empresa.getClass();
            telaCadastro(null);

            txtID.setText(String.valueOf(empresa.getIdEmpresa()));
            txtCidade.setText(empresa.getCidade());
            txtNomeEscola.setText(empresa.getNome());
            txtNif.setText(empresa.getNif());
            txtEndereco.setText(empresa.getEndereco());
            txtEmail.setText(empresa.getEmail());
            txtContato.setText(empresa.getContato());
            txtDescricao.setText(empresa.getDescricao());
            imageViewBarnner.setImage(empresa.getImageLogo());
            imageViewAssinatura.setImage(empresa.getImageAssinatura());

            lbTitulo.setText("Editar Empresa");
            menu.selectToggle(menu.getToggles().get(1));

            idEmpresa = empresa.getIdEmpresa();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione uma despesa na tabela para edição!");
        }
    }

    private void selectFristOnTableView(Empresa empresa) {

        empresa = tbEmpresa.getSelectionModel().getSelectedItem();

        if (empresa != null) {
            txtID.setText(String.valueOf(empresa.getIdEmpresa()));
            txtCidade.setText(empresa.getCidade());
            txtNomeEscola.setText(empresa.getNome());
            txtNif.setText(empresa.getNif());
            txtEndereco.setText(empresa.getEndereco());
            txtEmail.setText(empresa.getEmail());
            txtContato.setText(empresa.getContato());
            txtDescricao.setText(empresa.getDescricao());
            imageViewBarnner.setImage(empresa.getImageLogo());
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
    private void filtro(String valor, ObservableList<Empresa> listaEmpresa) {

        FilteredList<Empresa> dadosFiltrados = new FilteredList<>(listaEmpresa, empresa -> true);
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

        SortedList<Empresa> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbEmpresa.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de empresa encontradas");

        tbEmpresa.setItems(dadosOrdenados);
    }
}
