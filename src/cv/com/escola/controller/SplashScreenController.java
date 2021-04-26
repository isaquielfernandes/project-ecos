package cv.com.escola.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author Isaquiel Fernandes
 */
public class SplashScreenController implements Initializable {
    
    @FXML
    private Label progress;
    private static Label label;
    @FXML
    private ProgressBar progressBar;
    private static ProgressBar statProgressBar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       setLabel(progress);
       setStatProgressBar(progressBar);
    }    

    public Label getProgress() {
        return progress;
    }

    public void setProgress(Label progress) {
        this.progress = progress;
    }

    public static Label getLabel() {
        return label;
    }

    public static void setLabel(Label label) {
        SplashScreenController.label = label;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public static ProgressBar getStatProgressBar() {
        return statProgressBar;
    }

    public static void setStatProgressBar(ProgressBar statProgressBar) {
        SplashScreenController.statProgressBar = statProgressBar;
    }

}
