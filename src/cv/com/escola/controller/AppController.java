package cv.com.escola.controller;

import com.jfoenix.controls.JFXButton;
import cv.com.escola.app.App;
import cv.com.escola.app.Login;
import cv.com.escola.app.Server;
import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Aluno;
import cv.com.escola.model.entity.InspecaoTecnica;
import cv.com.escola.model.entity.Seguro;
import cv.com.escola.model.util.Animacao;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Grupo;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import cv.com.escola.model.util.Nota;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppController implements Initializable {

    private static AppController instance;

    private int count = 0;
    private int countAlert = 0;
    private Long diasQueFaltam;
    private Desktop desktop;

    @FXML
    private ToggleButton btRegistro;
    @FXML
    private ToggleGroup grupoMenus;
    @FXML
    private VBox boxRegistro;
    @FXML
    private ToggleGroup grupoRegistro;
    @FXML
    private ToggleButton btAluno;
    @FXML
    private ToggleButton btVeiculo;
    @FXML
    private Label lbUser;
    @FXML
    private AnchorPane boxConteudo;
    @FXML
    private Label lbMensagem;
    @FXML
    private VBox boxNotas;
    @FXML
    private ToggleButton btCategoria;
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
    private ToggleButton btExame;
    @FXML
    private ToggleGroup grupoExame;
    @FXML
    private VBox boxExame;
    @FXML
    private ToggleButton btEmpresa;
    @FXML
    private AnchorPane menus;
    @FXML
    private ToggleButton sideMenuToogleBtn;
    @FXML
    private ToggleButton btCurso;
    @FXML
    private ToggleButton btRelatorioGeral;
    @FXML
    private ToggleButton btDashBoards;
    @FXML
    private ToggleButton btHome;
    @FXML
    private ToggleButton btProcesso;
    @FXML
    private ToggleButton btRelatorios;
    @FXML
    private ToggleButton btInstrutor;
    @FXML
    private VBox boxRelatorio;
    @FXML
    private ToggleGroup grupoRelatorio;
    @FXML
    private ToggleButton btMarcarExame;
    @FXML
    private ToggleButton btResultadoExame;
    @FXML
    private ToggleGroup grupoBar;
    @FXML
    private VBox boxNotificacao;
    @FXML
    private VBox boxNotes;
    @FXML
    private ToggleButton tbMais;
    @FXML
    private ToggleButton tbUserLogado;
    @FXML
    private VBox vboxUtilizador;
    @FXML
    private JFXButton btnPerfil;
    @FXML
    private JFXButton btnConfig;
    @FXML
    private JFXButton btnLogOut;
    @FXML
    private ToggleButton btInscricao;
    @FXML
    private ToggleButton tbAlerta;
    @FXML
    private VBox boxAniversario;
    @FXML
    private ScrollPane vBoxAlerta;
    @FXML
    private VBox boxAlerta;
    @FXML
    private VBox boxNotifi;
    @FXML
    private Label lblAniversarioCount;
    @FXML
    private Label lblMenssageCount;
    @FXML
    private Label lblNotificCount;
    @FXML
    private TabPane boxImprisao;
    @FXML
    private Label lblClose;
    @FXML
    private ToggleButton tbPrint;
    @FXML
    private ToggleButton btBackup;
    @FXML
    private ToggleButton btRestoure;
    @FXML
    private ToggleButton btArtigo;
    @FXML
    private ScrollPane leftSideBarScrollPane;
    @FXML
    private HBox addHBox;
    @FXML
    private AnchorPane addAnchorPane;
    @FXML
    private ToggleButton btServer;
    @FXML
    private ToggleButton btLancamento;
    @FXML
    private ToggleGroup grupoProcesso;
    @FXML
    private VBox boxProcesso;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        desativarSubmenus();
        Grupo.notEmpty(grupoMenus, grupoRegistro, grupoRelatorio,
                grupoUtilidades, grupoExame, grupoProcesso);
        menuDashboard(null);
        lbUser.setText(LoginController.usuarioLogado.getNome().toUpperCase());
        notificacoes();
        aniversarioNoMes();
        //alertaAuto();
    }

    public VBox boxNotas() {
        return boxNotas;
    }

    public VBox boxNotific() {
        return boxNotifi;
    }

    public VBox boxAniversario() {
        return boxAniversario;
    }

    public VBox boxAlerta() {
        return boxAlerta;
    }

    public TabPane boxImprisao() {
        this.lblClose.setVisible(true);
        this.boxImprisao.setVisible(true);
        this.tbPrint.setSelected(true);
        return boxImprisao;
    }

    public VBox boxNotificacao() {
        return boxNotificacao;
    }

    public AnchorPane getBoxConteudo() {
        return boxConteudo;
    }

    public void submenus(ToggleButton menu, VBox box, ToggleButton... submenus) {
        if (box.getChildren().isEmpty()) {
            box.getChildren().addAll(submenus);
            Animacao.fade(box);
            estilo(menu, "menu-grupo-inativo");
            estilo(menu, "menu-grupo");
        } else {
            desativarSubmenus(box);
            estilo(menu, "menu-grupo");
            estilo(menu, "menu-grupo-inativo");
        }
    }

    public void desativarSubmenus(VBox... boxes) {
        for (VBox box : boxes) {
            box.getChildren().clear();
        }
    }

    public void estilo(Node no, String estilo) {
        no.getStyleClass().remove(3);
        no.getStyleClass().add(estilo);
    }

    public static AppController getInstance() {
        return instance;
    }

    @FXML
    private void menuDashboard(MouseEvent event) {
        esconder();
        Modulo.getRelatorioEscolar(boxConteudo);
    }

    @FXML
    private void menuRegistro(ActionEvent event) {
        submenus(btRegistro, boxRegistro, btAluno, btInscricao, btCategoria, btCurso, btInstrutor, btVeiculo, btArtigo);
    }

    @FXML
    private void subMenuAluno(ActionEvent event) {
        esconder();
        Modulo.getAluno(boxConteudo);
    }

    @FXML
    private void subMenuVeiculo(ActionEvent event) {
        esconder();
        Modulo.getMenuVeiculo(boxConteudo);
    }

    @FXML
    private void subMenuArtigo(ActionEvent event) {
        esconder();
        Modulo.getArtigo(boxConteudo);
    }

    @FXML
    private void subMenuCategoria(ActionEvent event) {
        esconder();
        Modulo.getCategoria(boxConteudo);
    }

    @FXML
    private void subMenuCurso(ActionEvent event) {
        esconder();
        Modulo.getCurso(boxConteudo);
    }

    @FXML
    private void menuSair(ActionEvent event)  {
        Dialogo.Resposta responses = Mensagem.confirmar("Deseja mesno Sair do sistema?");
        if (responses == Dialogo.Resposta.YES) {
            try {
                new App().stop();
                Platform.exit();
            } catch (Exception ex) {
                Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void menuUtilitario(ActionEvent event) {
        submenus(btUtilitarios, boxUtilitarios, btUsuarios, btOrganizacao, btEmpresa, btBackup, btRestoure, btServer);
    }

    @FXML
    private void subUsuarios(ActionEvent event) {
        esconder();
        Modulo.getUsuario(boxConteudo);
    }

    @FXML
    private void subOrganizacao(ActionEvent event) {
        esconder();
        Modulo.getOrganizacao(boxConteudo);
    }

    @FXML
    private void subEmpresa(ActionEvent event) {
        esconder();
        Modulo.getConfigEmpresa(boxConteudo);
    }

    @FXML
    private void menuExame(ActionEvent event) {
        submenus(btExame, boxExame, btMarcarExame, btResultadoExame);
    }

    @FXML
    private void sideMenuToogleBtnOnCLick(ActionEvent event) {
        throw new UnsupportedOperationException();
    }

    @FXML
    private void subMenuRelatorioGeral(ActionEvent event) {
        throw new UnsupportedOperationException();
    }

    @FXML
    private void subMenuDashBoards(ActionEvent event) {
        esconder();
        Modulo.getRelatorioEscolar(boxConteudo);
    }

    @FXML
    private void menuProcesso(ActionEvent event) {
        submenus(btProcesso, boxProcesso, btLancamento);
    }

    @FXML
    private void menuRelatorios(ActionEvent event) {
        submenus(btRelatorios, boxRelatorio, btRelatorioGeral, btDashBoards);
    }

    @FXML
    private void subMenuInstrutor(ActionEvent event) {
        esconder();
        Modulo.getInstrutor(boxConteudo);
    }

    @FXML
    private void subMenuMarcarExame(ActionEvent event) {
        esconder();
        Modulo.getExame(boxConteudo);
    }

    @FXML
    private void subMenuResultadoExame(ActionEvent event) {
        esconder();
        Modulo.getExameResultado(boxConteudo);
    }

    @FXML
    private void telaMais(ActionEvent event) {
        esconder();
        vboxUtilizador.setVisible(false);
        vBoxAlerta.setVisible(false);
        if (tbMais.isSelected()) {
            boxNotes.setVisible(true);
            Animacao.fade(boxNotes);
        } else {
            Animacao.fade(boxNotes);
            boxNotes.setVisible(false);
        }
    }

    @FXML
    private void telaUserLogado(ActionEvent event) {
        esconder();
        boxNotes.setVisible(false);
        vBoxAlerta.setVisible(false);
        if (tbUserLogado.isSelected()) {
            vboxUtilizador.setVisible(true);
            Animacao.fade(vboxUtilizador);
        } else {
            Animacao.fade(vboxUtilizador);
            vboxUtilizador.setVisible(false);
        }
    }

    @FXML
    private void telaAlerta(ActionEvent event) {
        esconder();
        boxNotes.setVisible(false);
        vboxUtilizador.setVisible(false);
        if (tbAlerta.isSelected()) {
            vBoxAlerta.setVisible(true);
            Animacao.fade(vBoxAlerta);
        } else {
            Animacao.fade(vBoxAlerta);
            vBoxAlerta.setVisible(false);
        }
    }

    @FXML
    private void subMenuInscricao(ActionEvent event) {
        esconder();
        Modulo.getInscricao(boxConteudo);
    }

    @FXML
    private void perfilDoUsuario(ActionEvent event) {
        throw new UnsupportedOperationException();
    }

    @FXML
    private void configuracao(ActionEvent event) {
        esconder();
        Modulo.getUsuario(boxConteudo);
    }

    @FXML
    private void closeBoxImprisao(MouseEvent event) {
        esconder();
    }

    @FXML
    private void telaPrint(ActionEvent event) {
        vboxUtilizador.setVisible(false);
        vBoxAlerta.setVisible(false);
        boxNotes.setVisible(false);
        if (tbPrint.isSelected()) {
            boxImprisao.setVisible(true);
            lblClose.setVisible(true);
        } else {
            boxImprisao.setVisible(false);
            lblClose.setVisible(false);
        }
    }

    @FXML
    private void subBackup(ActionEvent event) {
        try {
            desktop.mail();
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void subRestoure(ActionEvent event) {
        throw new UnsupportedOperationException();
    }

    @FXML
    private void subServer(ActionEvent event) {
        Dialogo.Resposta responses = Mensagem.confirmar("Deseja fazer alterar aconfiguracao do servidor?");
        if (responses == Dialogo.Resposta.YES) {
            try {
                new Server().start(new Stage());
                new App().stop();
                Platform.exit();
            } catch (Exception ex) {
                Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void subMenuLancamento(ActionEvent event) {
        esconder();
        Modulo.getRegistroVenda(boxConteudo);
    }

    @FXML
    private void terminarSeccao(ActionEvent event) {
        try {
            new App().stop();
            Platform.exit();
            new Login().start(new Stage());
        } catch (Exception ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void notificacoes() {
        Integer dia = LocalDate.now().getDayOfMonth();
        if (dia == 1 || dia == 16 || dia == 26) {
            Nota.alertaNotific("Aviso:\nFaça backup");
            count++;
        }
    }

    private void aniversarioNoMes() {
        List<Aluno> listaAluno = DAOFactory.daoFactury().alunoDAO().findAll();
        listaAluno.forEach(aluno -> {
            if (aluno.getDataNascimento().getMonthValue() == LocalDate.now().getMonthValue()) {
                Nota.alertaAniversario("Nome: " + aluno.getNome() + "\nNascido em: " + aluno.getDataNascimento());
                count++;
            }
        });
        lblAniversarioCount.setText("" + count);
    }

    public void alertaAuto() {
        List<Seguro> listaSeguroAuto = DAOFactory.daoFactury().seguroAutoDAO().findAll();
        List<InspecaoTecnica> listaInspecaoTecnicao = DAOFactory.daoFactury().inspecaoTecnicaDAO().findAll();

        listaSeguroAuto.parallelStream()
                .forEach(seguro -> {
                    diasQueFaltam = DAYS.between(LocalDate.now(), seguro.getValidade());

                    if (diasQueFaltam >= 0 && diasQueFaltam < 10) {
                        Nota.alertaAlerta("Seguro: " + seguro.getVeiculo().getPlaca() + " \nFalta(m) " + diasQueFaltam + " dia(s) para o vencimento do seguro.");
                        countAlert++;
                    } else if (diasQueFaltam < 0 && diasQueFaltam > -5) {
                        Nota.alertaAlerta("Seguro vencido. Por favor reguralize sua situação");
                        countAlert++;
                    }
                });

        listaInspecaoTecnicao.parallelStream()
                .forEach(inspecao -> {
                    diasQueFaltam = DAYS.between(LocalDate.now(), inspecao.getDataDeInspeccao().plusMonths(inspecao.getMesDeDuracao()));
                    if (diasQueFaltam >= 0 && diasQueFaltam < 10) {
                        Nota.alertaAlerta("IT: " + inspecao.getVeiculo().getPlaca() + "\nFalta(m) " + diasQueFaltam + " dia(s) para o vencimento da inpesção.");
                        countAlert++;
                    } else if (diasQueFaltam < 0 && diasQueFaltam > -5) {
                        Nota.alertaAlerta("Seguro vencido. Por favor reguralize sua situação");
                        countAlert++;
                    }
                });
        lblNotificCount.setText(countAlert + "");
    }

    private void esconder() {
        this.boxImprisao.setVisible(false);
        this.lblClose.setVisible(false);
        this.tbPrint.setSelected(false);
    }

}
