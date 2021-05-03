package cv.com.escola.controller;

import cv.com.escola.app.Login;
import cv.com.escola.app.Server;
import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Mensagem;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class ServerController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerController.class);

    @FXML
    private TextField tfHost;
    @FXML
    private TextField thPort;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Button btnConnect;
    @FXML
    private Button btnIniciar;
    @FXML
    private Button btnTest;
    @FXML
    private Button btnCriar;
    @FXML
    private Label lablServerStatus;
    @FXML
    private TextField tfDBName;
    @FXML
    private TextField tfUserName;

    private static final String DATABASE_PROPERTIES = "database.properties";
    private final Properties properties = new Properties();
    private InputStream inputStream;
    private OutputStream output = null;

    private Connection con;

    private String url;
    private String user;
    private String pass;
    private static final String ID_TIME_ZONE = java.util.TimeZone.getDefault().getID();
    private static final String UNICODE = "?serverTimezone=" + ID_TIME_ZONE + "";
    private static final String PASSWORD = "password";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkSQLStatus();
    }

    @FXML
    private void btnConnectOnAction(ActionEvent event) {
        mkDbProperties();
    }

    @FXML
    private void btnIniciarOnAction(ActionEvent event) {
        new Login().start(new Stage());
        Server.getPalco().close();
    }

    @FXML
    private void onActionTeste(ActionEvent event) {
        checkSQLStatus();
    }

    @FXML
    private void onActionCriarDataBase(ActionEvent event) {

        Dialogo.Resposta resInsert = Mensagem.confirmar("Criar usuario admin");
        if (resInsert == Dialogo.Resposta.YES) {
            DAOFactory.daoFactory().usuarioDAO().createUserAdminAndUserType();
        }

    }

    public void getDataFromFile() {
        try {
            inputStream = new FileInputStream(DATABASE_PROPERTIES);

            properties.load(inputStream);
            log.debug("Host : " + properties.getProperty("host"));
            tfHost.setText(properties.getProperty("host"));
            tfDBName.setText(properties.getProperty("db"));
            tfUserName.setText(properties.getProperty("user"));
            pfPassword.setText(properties.getProperty(PASSWORD));
            thPort.setText(properties.getProperty("port"));
            inputStream.close();
        } catch (FileNotFoundException ex) {
            LOGGER.error(ex.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void mkDbProperties() {
        try {
            output = new FileOutputStream(DATABASE_PROPERTIES);

            properties.setProperty("host", tfHost.getText().trim());
            properties.setProperty("port", thPort.getText().trim());
            properties.setProperty("db", tfDBName.getText().trim());
            properties.setProperty("user", tfUserName.getText().trim());
            properties.setProperty(PASSWORD, pfPassword.getText().trim());
            properties.store(output, null);
            output.close();

            loadPropertiesFile();
            if (dbConnect()) {
                con.close();
                Mensagem.info("Sucesso", "Configuração guardada com sucesso");
                btnCriar.setDisable(false);
                btnIniciar.setDisable(false);
            } else {
                Mensagem.erro("Tente novamente", "Erro: de Coneção com o Servidor");
            }
        } catch (FileNotFoundException | SQLException ex) {
            LOGGER.error(ex.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void mkDbPropertiesReset() {
        try {
            output = new FileOutputStream(DATABASE_PROPERTIES);
            clearField();
            properties.setProperty("host", null);
            properties.setProperty("port", null);
            properties.setProperty("db", null);
            properties.setProperty("user", null);
            properties.setProperty(PASSWORD, null);
            properties.store(output, null);
            output.close();
        } catch (FileNotFoundException ex) {
            LOGGER.error(ex.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void clearField() {
        tfHost.clear();
        thPort.clear();
        tfDBName.clear();
        tfUserName.clear();
        pfPassword.clear();
    }

    public void checkSQLStatus() {
        try {
            String host = tfHost.getText();
            int port = Integer.parseInt(thPort.getText());
            if (port == 3306 || port == 3307 || port == 3308 || port == 8887) {
                socketConnection(host, port);
            } else {
                Mensagem.erro("Nao foi possivel conectar com o servidor na port: " + port, "Erro de conexao");
            }
        } catch (FileNotFoundException ex) {
            LOGGER.error(ex.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void socketConnection(String host, int port) throws IOException {
        try (Socket socket = new Socket(host, port);) {
            if (socket.isConnected()) {
                lablServerStatus.setText("Server is running on port: " + port);
            }
        } catch (ConnectException ex) {
            LOGGER.error("porta nao encontrada: ", ex);
        }
    }

    public void loadPropertiesFile() {
        try {
            inputStream = new FileInputStream(DATABASE_PROPERTIES);
            properties.load(inputStream);
            url = "jdbc:mysql://" + properties.getProperty("host") + ":" + properties.getProperty("port") + "/";
            user = properties.getProperty("user");
            pass = properties.getProperty(PASSWORD);
        } catch (IOException ex) {
            LOGGER.error("erro ao caregar arquivo de configuracao!", ex);
        }
    }

    private boolean dbConnect() {
        loadPropertiesFile();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url + UNICODE, user, pass);
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return false;
    }

    public static void esperar(long milesegundos) {
        try {
            Thread.sleep(milesegundos);
        } catch (InterruptedException e) {
            LOGGER.warn("Interrupted!: ", e);
            Thread.currentThread().interrupt();
        }
    }
}
