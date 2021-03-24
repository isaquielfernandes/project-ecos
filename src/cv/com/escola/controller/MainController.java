package cv.com.escola.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author user
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane menus;
    @FXML
    private HBox addHBox;
    @FXML
    private ToggleButton btHome;
    @FXML
    private ScrollPane leftSideBarScrollPane;
    @FXML
    private ToggleButton btRegistro;
    @FXML
    private ToggleGroup grupoMenus;
    @FXML
    private VBox boxRegistro;
    @FXML
    private ToggleButton btAluno;
    @FXML
    private ToggleGroup grupoRegistro;
    @FXML
    private ToggleButton btInscricao;
    @FXML
    private ToggleButton btCategoria;
    @FXML
    private ToggleButton btCurso;
    @FXML
    private ToggleButton btInstrutor;
    @FXML
    private ToggleButton btVeiculo;
    @FXML
    private ToggleButton btArtigo;
    @FXML
    private ToggleButton btExame;
    @FXML
    private VBox boxExame;
    @FXML
    private ToggleButton btMarcarExame;
    @FXML
    private ToggleGroup grupoExame;
    @FXML
    private ToggleButton btResultadoExame;
    @FXML
    private ToggleButton btProcesso;
    @FXML
    private VBox boxProcesso;
    @FXML
    private ToggleButton btLancamento;
    @FXML
    private ToggleGroup grupoProcesso;
    @FXML
    private ToggleButton btRelatorios;
    @FXML
    private VBox boxRelatorio;
    @FXML
    private ToggleButton btRel_Geral;
    @FXML
    private ToggleGroup grupoRelatorio;
    @FXML
    private ToggleButton btDashBoards;
    @FXML
    private ToggleButton btUtilitarios;
    @FXML
    private VBox boxUtilitarios;
    @FXML
    private ToggleButton btUsuarios;
    @FXML
    private ToggleGroup grupoUtilidades;
    @FXML
    private ToggleButton btOrganizacao;
    @FXML
    private ToggleButton btEmpresa;
    @FXML
    private ToggleButton btBackup;
    @FXML
    private ToggleButton btRestoure;
    @FXML
    private ToggleButton btServer;
    @FXML
    private AnchorPane addAnchorPane;
    @FXML
    private ToggleButton sideMenuToogleBtn;
    @FXML
    private ToggleButton tbPrint;
    @FXML
    private ToggleGroup grupoBar;
    @FXML
    private ToggleButton tbAlerta;
    @FXML
    private Label lbUser;
    @FXML
    private ToggleButton tbUserLogado;
    @FXML
    private ToggleButton tbMais;
    @FXML
    private Label lblAniversarioCount;
    @FXML
    private Label lblMenssageCount;
    @FXML
    private Label lblNotificCount;
    @FXML
    private AnchorPane boxConteudo;
    @FXML
    private Label lbMensagem;
    @FXML
    private TabPane boxImprisao;
    @FXML
    private Label lblClose;
    @FXML
    private VBox boxNotes;
    @FXML
    private VBox boxNotifi;
    @FXML
    private VBox boxAniversario;
    @FXML
    private VBox vboxUtilizador;
    @FXML
    private JFXButton btnPerfil;
    @FXML
    private JFXButton btnConfig;
    @FXML
    private JFXButton btnLogOut;
    @FXML
    private ScrollPane vBoxAlerta;
    @FXML
    private VBox boxAlerta;
    @FXML
    private VBox boxNotas;
    @FXML
    private VBox boxNotificacao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void menuDashboard(MouseEvent event) {
    }

    @FXML
    private void menuRegistro(ActionEvent event) {
    }

    @FXML
    private void subMenuAluno(ActionEvent event) {
    }

    @FXML
    private void subMenuInscricao(ActionEvent event) {
    }

    @FXML
    private void subMenuCategoria(ActionEvent event) {
    }

    @FXML
    private void subMenuCurso(ActionEvent event) {
    }

    @FXML
    private void subMenuInstrutor(ActionEvent event) {
    }

    @FXML
    private void subMenuVeiculo(ActionEvent event) {
    }

    @FXML
    private void subMenuArtigo(ActionEvent event) {
    }

    @FXML
    private void menuExame(ActionEvent event) {
    }

    @FXML
    private void subMenuMarcarExame(ActionEvent event) {
    }

    @FXML
    private void subMenuResultadoExame(ActionEvent event) {
    }

    @FXML
    private void menuProcesso(ActionEvent event) {
    }

    @FXML
    private void subMenuLancamento(ActionEvent event) {
    }

    @FXML
    private void menuRelatorios(ActionEvent event) {
    }

    @FXML
    private void subMenuRel_Geral(ActionEvent event) {
    }

    @FXML
    private void subMenuDashBoards(ActionEvent event) {
    }

    @FXML
    private void menuUtilitario(ActionEvent event) {
    }

    @FXML
    private void subUsuarios(ActionEvent event) {
    }

    @FXML
    private void subOrganizacao(ActionEvent event) {
    }

    @FXML
    private void subEmpresa(ActionEvent event) {
    }

    @FXML
    private void subBackup(ActionEvent event) {
    }

    @FXML
    private void subRestoure(ActionEvent event) {
    }

    @FXML
    private void subServer(ActionEvent event) {
    }

    @FXML
    private void menuSair(ActionEvent event) {
    }

    @FXML
    private void sideMenuToogleBtnOnCLick(ActionEvent event) {
    }

    @FXML
    private void telaPrint(ActionEvent event) {
    }

    @FXML
    private void telaAlerta(ActionEvent event) {
    }

    @FXML
    private void telaUserLogado(ActionEvent event) {
    }

    @FXML
    private void telaMais(ActionEvent event) {
    }

    @FXML
    private void closeBoxImprisao(MouseEvent event) {
    }

    @FXML
    private void perfilDoUsuario(ActionEvent event) {
    }

    @FXML
    private void configuracao(ActionEvent event) {
    }

    @FXML
    private void terminarSeccao(ActionEvent event) {
    }
    
}
