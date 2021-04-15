package cv.com.escola.controller;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.util.Mensagem;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class DashVendaController extends AnchorPane implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Label lbVendasNoAno;
    @FXML
    private Label lbTotalDeExame1;
    @FXML
    private Label lbVendasNoMes;
    @FXML
    private Label lbTotalDeExame11;
    @FXML
    private Label lbTickedMedio;
    @FXML
    private Label lbTotalDeExame12;
    @FXML
    private Label lbTotalCurso;
    @FXML
    private Label lbTotalDeExame13;
    @FXML
    private Label lbTotalFornecedor;
    @FXML
    private Label lbTotalDeExame14;
    @FXML
    private Label lbTotalCliente;
    @FXML
    private Label lbTotalDeExame15;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String ano = String.valueOf(LocalDate.now().getYear());
        lbVendasNoAno.setText(DAOFactory.daoFactury().orderDAO().totalAnual(ano) + "$00");
    }    
    @SuppressWarnings({"LeakingThisInConstructor", "CallToPrintStackTrace"})
    public DashVendaController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/cv/com/escola/view/dashVenda.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, "context", ex); 
            Mensagem.erro("Erro ao carregar tela dashboard venda! \n" + ex);
        }
    }
}
