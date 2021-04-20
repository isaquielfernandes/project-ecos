package cv.com.escola.app;

import com.sun.javafx.application.LauncherImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Configuracao extends Application {

    private static final Logger LOG = Logger.getLogger(Configuracao.class.getName());
    public static Stage palco;
    private static final int COUNT_LIMIT = 10;
    
    
    @Override
    @SuppressWarnings("UseSpecificCatch")
    public void start(final Stage stage) {
        try {
            palco = stage;
            AnchorPane page = FXMLLoader.load(Configuracao.class.getResource("/cv/com/escola/view/configuracao.fxml"));
            Scene cena = new Scene(page);

            stage.initStyle(StageStyle.DECORATED);
            stage.setMaximized(false);

            stage.getIcons().addAll(new Image(Configuracao.class.getResourceAsStream("/cv/com/escola/img/servidor.png")));

            stage.setScene(cena);
            stage.show();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Erro ao inicializar aplicacao!" , ex);
        }
    }
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void init() throws Exception {       
        
        // Perform some heavy lifting (i.e. database start, check for application updates, etc. )
        for (int i = 1; i <= COUNT_LIMIT; i++) {
            double progress =(double) i/10;        
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
            Thread.sleep(2000);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
