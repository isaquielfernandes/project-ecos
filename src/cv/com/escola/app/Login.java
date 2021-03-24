package cv.com.escola.app;

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
            page = FXMLLoader.load(Login.class.getResource("/cv/com/escola/view/login/login.fxml"));
            cena = new Scene(page);

            stage.initStyle(StageStyle.UNDECORATED);

            stage.setX(windows.getMinX());
            stage.setY(windows.getMinY());
            stage.setWidth(windows.getWidth());
            stage.setHeight(windows.getHeight());
            stage.setMaximized(true);

            stage.getIcons().addAll(new Image(Login.class.getResourceAsStream("/cv/com/escola/view/img/servidor.png")));

            stage.setScene(cena);
            stage.show();

        } catch (Exception ex) {
            System.out.println("Erro ao inicializar aplicação 11!" + ex);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
