package cv.com.escola.controller;

import cv.com.escola.model.entity.TipoUsuario;
import cv.com.escola.model.entity.Usuario;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public abstract class UserCommunField extends AnchorPane {
    
    protected List<Usuario> listaUsuario;
    protected int idUsuario = 0;
    @FXML
    protected Label lbTitulo;
    @FXML
    protected TextField txtPesquisar;
    @FXML
    protected ToggleGroup menu;
    @FXML
    protected GridPane telaCadastro;
    @FXML
    protected TextField txtNome;
    @FXML
    protected ComboBox<String> cbStatus;
    @FXML
    protected ComboBox<TipoUsuario> cbPermissaoUsuario;
    @FXML
    protected TextField txtLogin;
    @FXML
    protected TextField txtEmail;
    @FXML
    protected PasswordField txtSenha;
    @FXML
    protected PasswordField txtConfirmarSenha;
    @FXML
    protected TextArea txtDescricao;
    @FXML
    protected AnchorPane telaEdicao;
    @FXML
    protected TableView<Usuario> tbUsuario;
    @FXML
    protected TableColumn<Usuario, Integer> colId;
    @FXML
    protected TableColumn<Usuario, String> colNome;
    @FXML
    protected TableColumn<Usuario, String> colLogin;
    @FXML
    protected TableColumn<Usuario, String> colEmail;
    @FXML
    protected TableColumn<Usuario, String> colStatus;
    @FXML
    protected TableColumn<Usuario, String> colTipo;
    @FXML
    protected TableColumn<Usuario, String> colDescricao;
    @FXML
    protected Button btSalvar;
    @FXML
    protected Button btEditar;
    @FXML
    protected Button btExcluir;
    @FXML
    protected Label legenda;
    @FXML
    protected AnchorPane anchorPane;
    
}
