package cv.com.escola;

import com.sun.javafx.application.LauncherImpl;
import cv.com.escola.app.Login;
import cv.com.escola.app.Server;
import java.io.File;
import javafx.application.Application;
import javafx.application.Preloader;
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
    @SuppressWarnings("SleepWhileInLoop")
    public void init() throws Exception {
        final int COUNT_LIMIT = 10;
        // Perform some heavy lifting (i.e. database start, check for application updates, etc. )
        for (int i = 1; i <= COUNT_LIMIT; i++) {
            double progress = (double) i / 10;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
            Thread.sleep(1000);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
