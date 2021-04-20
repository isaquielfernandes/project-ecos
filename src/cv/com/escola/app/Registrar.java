package cv.com.escola.app;

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


public class Registrar extends Application {

    private static final Logger LOG = Logger.getLogger(Registrar.class.getName());

    private static Stage palco;
    private final Screen screen = Screen.getPrimary();
    private final Rectangle2D windows = screen.getVisualBounds();

    @Override
    @SuppressWarnings("UseSpecificCatch")
    public void start(final Stage stage) {
        try {
            palco = stage;
            AnchorPane page = FXMLLoader.load(Registrar.class.getResource("/cv/com/escola/view/registrar.fxml"));
            Scene cena = new Scene(page);

            stage.initStyle(StageStyle.DECORATED);

            stage.setX(windows.getMinX());
            stage.setY(windows.getMinY());
            stage.setWidth(windows.getWidth());
            stage.setHeight(windows.getHeight());
            stage.setMaximized(true);

            stage.getIcons().addAll(new Image(Registrar.class.getResourceAsStream("/cv/com/escola/view/img/servidor.png")));

            stage.setScene(cena);
            stage.show();

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Erro ao inicializar aplicacao!" , ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static void fechar() {
        palco.close();
    }
}
