package cv.com.escola.controller;

import cv.com.escola.app.App;
import cv.com.escola.app.Configuracao;
import cv.com.escola.app.Login;
import cv.com.escola.app.Registrar;
import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Usuario;
import cv.com.escola.model.util.Campo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController implements Initializable {
    
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(LoginController.class);
    
    public static Usuario usuarioLogado = null;
    @FXML
    private PasswordField psSenha;
    @FXML
    private Label lbErroLogin;
    @FXML
    private TextField txtUsuario;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        acessar(psSenha);
        focus(txtUsuario);
    }

    @FXML
    private void linkRegistrar(ActionEvent event) {
        new Registrar().start(new Stage());
        Login.palco.close();
    }

    @FXML
    public void logar(ActionEvent event) {
        String login = txtUsuario.getText();
        String senha = psSenha.getText();
        if (DAOFactory.daoFactury().loginDAO().autenticarUsername(login)) {
            if (DAOFactory.daoFactury().loginDAO().autenticarSenha(login, senha)) {
                usuarioLogado = DAOFactory.daoFactury().loginDAO().usuarioLogado(login);
                if (DAOFactory.daoFactury().empresaDAO().total() == 0) {
                    new Configuracao().start(new Stage());
                    Login.palco.close();
                } else {
                    new App().start(new Stage());
                    Login.palco.close();
                }
            } else {
                lbErroLogin.setText("Senha incorreta, verifique os valores!");
                Campo.erroLogin(psSenha);
            }
        } else {
            lbErroLogin.setText("Usuário não existe ou inativo!");
            Campo.erroLogin(txtUsuario);
        }
    }
    
    @FXML
    public void minimizar(ActionEvent event) {
        Login.palco.setIconified(true);
    }

    @FXML
    public void fechar(ActionEvent event) {
        Login.palco.close();
    }

    private void acessar(PasswordField senha) {
        senha.setOnKeyReleased((KeyEvent key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                logar(null);
            }
        });
    }

    private void focus(TextField user) {
        user.setOnKeyReleased((KeyEvent key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                psSenha.setFocusTraversable(true);
            }
        });
    }
    
    private void createTypeUser() {
        if (DAOFactory.daoFactury().usuarioDAO().totalTipoUsuario() == 0) {
            DAOFactory.daoFactury().usuarioDAO().createUserAdminAndUserType();
            LOGGER.info(" Tipo de User criado com sucesso!");
        }
    }

}
