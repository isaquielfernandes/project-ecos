package cv.com.escola.app;

import cv.com.escola.EcosApp;
import cv.com.escola.controller.SplashScreenController;
import javafx.application.Preloader;
import static javafx.application.Preloader.StateChangeNotification.Type.BEFORE_START;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyPreloader extends Preloader {

    private Stage preloaderStage;
    private Scene scene;

    @Override
    public void init() throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getResource("/cv/com/escola/view/app/splashScreen.fxml"));
        scene = new Scene(root1);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;

        // Set preloader scene and show stage.
        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.getIcons().addAll(new Image(EcosApp.class.getResourceAsStream("/cv/com/escola/view/img/servidor.png")));
        preloaderStage.show();

    }

    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification info) {
        if (info instanceof ProgressNotification) {
            SplashScreenController.getLabel().setText("Loading " + ((ProgressNotification) info).getProgress() * 100 + "%");
            SplashScreenController.getStatProgressBar().setProgress(((ProgressNotification) info).getProgress());
        }
    }

    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {
        StateChangeNotification.Type type = info.getType();

        if (type == BEFORE_START) {
            preloaderStage.hide();
        }
    }
}
