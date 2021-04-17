package cv.com.escola;

import cv.com.escola.app.Login;
import cv.com.escola.app.Server;
import java.io.File;
import javafx.application.Application;
import javafx.stage.Stage;

public final class EcosApp extends Application {

    @Override
    @SuppressWarnings("UseSpecificCatch")
    public void start(final Stage stage) {
        File arq = new File("database.properties");
        if (arq.exists()) {
            new Login().start(new Stage());
        } else {
            new Server().start(new Stage());
        }
    }

    @Override
    public void init() throws Exception {
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
