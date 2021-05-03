package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.util.Mensagem;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
@Slf4j
public class DashVendaController extends AnchorPane implements Initializable {

    @FXML
    private AnchorPane anchorPane;
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
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String ano = String.valueOf(LocalDate.now().getYear());
        lbVendasNoAno.setText(DAOFactory.daoFactory().orderDAO().totalAnual(ano) + "$00");
    }

    @SuppressWarnings({"LeakingThisInConstructor", "CallToPrintStackTrace"})
    public DashVendaController() {
        try {
            GenericFXXMLLoader.loadFXML(this, "dashVenda");
        } catch (IOException ex) {
            log.error(ex.getMessage());
            Mensagem.erro("Erro ao carregar tela dashboard venda!");
        }
    }
}
