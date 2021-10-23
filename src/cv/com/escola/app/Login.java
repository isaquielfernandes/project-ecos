package cv.com.escola.app;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Login extends Application {

    private static final Logger LOG = Logger.getLogger(Login.class.getName());
    
    private static Stage palco;
    private final Screen screen = Screen.getPrimary();
    private final Rectangle2D windows = screen.getVisualBounds();
    
    
    @Override
    public void start(final Stage stage) {
        try {
            setPalco(stage);
            AnchorPane page = FXMLLoader.load(Login.class.getResource("/cv/com/escola/view/login/login.fxml"));
            Scene cena = new Scene(page);

            stage.initStyle(StageStyle.UNDECORATED);
            stage.setMaximized(false);

            stage.getIcons().addAll(new Image(Login.class.getResourceAsStream("/cv/com/escola/view/img/servidor.png")));

            stage.setScene(cena);
            stage.show();

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Erro ao inicializar aplicacao!" , ex);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPalco() {
        return Login.palco;
    }

    private static void setPalco(Stage palco) {
        Login.palco = palco;
    }
}
