package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.EscolaConducao;
import cv.com.escola.model.util.Campo;
import static cv.com.escola.model.util.ValidationFields.checkEmptyFields;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;

public class ConfiguracaoController implements Initializable {

    private List<EscolaConducao> listaEmpresa;
    private int idEmpresa = 0;
    private Image image;
    private File file;
    private File fileAssinatura;

    //@FXML
    private ToggleGroup menu;
    @FXML
    private Label lbTitulo;
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
    private ImageView logo;
    @FXML
    private ImageView assinatura;
    @FXML
    private TextField txtID;
    @FXML
    private Button btSalvar;
    @FXML
    private Label legenda;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sincronizarDataBase();

        logo.setOnMouseClicked(event -> uploadLogo());
        assinatura.setOnMouseClicked(event -> uploadFileAssinatura());
    }

    private void sincronizarDataBase() {
        listaEmpresa = DAOFactory.daoFactury().empresaDAO().findAll();
    }

    private void limparCampos() {
        Campo.limpar(txtEndereco, txtNomeEscola, txtCidade, txtEmail, txtNif, txtContato);
        Campo.limpar(txtDescricao);
        logo.setImage(null);
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean vazio = checkEmptyFields(txtCidade, txtNomeEscola, txtNif, txtEmail,
                txtContato, txtEndereco);

        String cidade = txtCidade.getText();
        String nomeEscola = txtNomeEscola.getText();
        String nif = txtNif.getText();
        String endereco = txtEndereco.getText();
        String email = txtEmail.getText();
        String contato = txtContato.getText();
        String descricao = txtDescricao.getText();
        idEmpresa = Integer.valueOf(txtID.getText().trim().isEmpty() ? "0" : txtID.getText());
        
    }

    public void uploadLogo() {
        file = filechooser();
        if (file == null) {
            return;
        }

        if (file.length() < 6000000) {
            image = new Image(file.getAbsoluteFile().toURI().toString(),
                    logo.getFitWidth(), logo.getFitHeight(), true, true);
            logo.setImage(image);
        } else {
            alearta();
        }
    }

    public void uploadFileAssinatura() {
        fileAssinatura = filechooser();
        if (fileAssinatura == null) {
            return;
        }

        if (fileAssinatura.length() < 6000000) {
            image = new Image(fileAssinatura.getAbsoluteFile().toURI().toString(),
                    assinatura.getFitWidth(), assinatura.getFitHeight(), true, true);
            assinatura.setImage(image);
        } else {
            alearta();
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

    private void alearta() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Permissao");
        alert.setHeaderText("Permisao negada");
        alert.setContentText("Your Image file is too big to upload \n please choise another image");
        alert.initStyle(StageStyle.UNDECORATED);
    }
}
