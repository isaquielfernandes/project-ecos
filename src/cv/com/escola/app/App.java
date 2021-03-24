package cv.com.escola.app;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    public static Stage palco;
    private static Scene cena;
    private static AnchorPane page;

    private final Screen screen = Screen.getPrimary();
    private final Rectangle2D windows = screen.getVisualBounds();

    @Override
    public void start(final Stage stage) {
        try {
            palco = stage;
            page = FXMLLoader.load(App.class.getResource("/cv/com/escola/view/app/app.fxml"));
            cena = new Scene(page);

            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("SGEC");
            stage.setMaximized(false);
            stage.setX(windows.getMinX());
            stage.setY(windows.getMinY());
            stage.setWidth(windows.getWidth());
            stage.setHeight(windows.getHeight());
            stage.setFullScreen(false);

            stage.getIcons().addAll(new Image(App.class.getResourceAsStream("/cv/com/escola/view/img/servidor.png")));

            stage.setScene(cena);
            stage.show();

        } catch (IOException ex) {
            System.out.println("Erro ao inicializar aplicação!\n" + ex.getLocalizedMessage());
        }
    }

    public static void main(String[] args) {
        Application.launch(App.class, (java.lang.String[]) null);
    }

}
