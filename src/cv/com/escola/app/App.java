package cv.com.escola.app;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class App extends Application {
    
    private static final Logger LOG = Logger.getLogger(App.class.getName());
    private static Scene scene;
    private final Screen screen = Screen.getPrimary();
    private final Rectangle2D windows = screen.getVisualBounds();

    @Override
    public void start(final Stage stage) {
        try {
            
            AnchorPane page = FXMLLoader.load(App.class.getResource("/cv/com/escola/view/app/app.fxml"));
            scene = new Scene(page);

            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("ESCOLA DE CONDUÇÂO O SINAL");
            stage.setMaximized(false);
            stage.setX(windows.getMinX());
            stage.setY(windows.getMinY());
            stage.setWidth(windows.getWidth());
            stage.setHeight(windows.getHeight());
            stage.setFullScreen(false);

            stage.getIcons().addAll(new Image(App.class
                    .getResourceAsStream("/cv/com/escola/view/img/servidor.png")));

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Erro ao inicializar aplicacao!" , ex);
        }
    }

    public static void main(String[] args) {
        Application.launch(App.class, (java.lang.String[]) null);
    }
    
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

}
