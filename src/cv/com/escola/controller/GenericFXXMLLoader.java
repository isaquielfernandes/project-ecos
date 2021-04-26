package cv.com.escola.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class GenericFXXMLLoader {

    private static final String URL_FXML = "/cv/com/escola/view/";

    private GenericFXXMLLoader() {
        
    }
    
    public static void loadFXML(AnchorPane anchorPane, String fxmlName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GenericFXXMLLoader.class.getResource(URL_FXML + fxmlName + ".fxml"));
        fxmlLoader.setRoot(anchorPane);
        fxmlLoader.setController(anchorPane);
        fxmlLoader.load();
    }
}
