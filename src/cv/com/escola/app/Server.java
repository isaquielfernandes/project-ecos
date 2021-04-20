package cv.com.escola.app;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Server extends Application {

    private static final Logger LOG = Logger.getLogger(Server.class.getName());

    private static Stage palco;
    private double initX;
    private double initY;

    @Override
    @SuppressWarnings("UseSpecificCatch")
    public void start(final Stage stage) {
        try {
            palco = stage;
            AnchorPane page = FXMLLoader.load(Server.class.getResource("/cv/com/escola/view/login/Server.fxml"));
            Scene cena = new Scene(page);

            stage.initStyle(StageStyle.UNDECORATED);
            stage.setMaximized(false);
            stage.centerOnScreen();

            //when mouse button is pressed, save the initial position of screen
            page.setOnMousePressed((MouseEvent me) -> {
                initX = me.getScreenX() - stage.getX();
                initY = me.getScreenY() - stage.getY();
            });

            //when screen is dragged, translate it accordingly
            page.setOnMouseDragged((MouseEvent me) -> {
                stage.setX(me.getScreenX() - initX);
                stage.setY(me.getScreenY() - initY);
            });

            stage.getIcons().addAll(new Image(Server.class.getResourceAsStream("/cv/com/escola/view/img/servidor.png")));

            stage.setScene(cena);
            stage.show();

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Erro ao inicializar aplicacao!" , ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static void close() {
        palco.close();
    }
}
