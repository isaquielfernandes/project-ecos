package cv.com.escola.app;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Registrar extends Application {

    public static Stage palco;
    private Scene cena;
    private AnchorPane page;

    private final Screen screen = Screen.getPrimary();
    private final Rectangle2D windows = screen.getVisualBounds();
    private static final int COUNT_LIMIT = 10;

    @Override
    @SuppressWarnings("UseSpecificCatch")
    public void start(final Stage stage) {
        try {
            palco = stage;
            page = FXMLLoader.load(Registrar.class.getResource("/cv/com/escola/view/registrar.fxml"));
            cena = new Scene(page);

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
            System.out.println("Erro ao inicializar aplicação!" + ex);
        }
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void init() throws Exception {

        // Perform some heavy lifting (i.e. database start, check for application updates, etc. )
        for (int i = 1; i <= COUNT_LIMIT; i++) {
            double progress = (double) i / 10;
            //System.out.println("progress: " +  progress);            
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
            Thread.sleep(2000);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
