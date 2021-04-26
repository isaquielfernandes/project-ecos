package cv.com.escola.app;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Configuracao extends Application {

    private static final Logger LOG = Logger.getLogger(Configuracao.class.getName());
    private static  Stage palco;
    
    @Override
    public void start(final Stage stage) {
        try {
            setPalco(stage);
            AnchorPane page = FXMLLoader.load(Configuracao.class.getResource("/cv/com/escola/view/configuracao.fxml"));
            Scene cena = new Scene(page);

            stage.initStyle(StageStyle.DECORATED);
            stage.setMaximized(false);

            stage.getIcons().addAll(new Image(Configuracao.class.getResourceAsStream("/cv/com/escola/img/servidor.png")));

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
        return palco;
    }

    public static void setPalco(Stage palco) {
        Configuracao.palco = palco;
    }
      
}
