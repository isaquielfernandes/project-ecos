package cv.com.escola.model.util;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import cv.com.escola.controller.AlunoController;
import cv.com.escola.controller.ArtigoController;
import cv.com.escola.controller.CargoSalarioController;
import cv.com.escola.controller.CategoriaController;
import cv.com.escola.controller.ClienteController;
import cv.com.escola.controller.ConfigEmpresaController;
import cv.com.escola.controller.CursoController;
import cv.com.escola.controller.DadosIniciaisController;
import cv.com.escola.controller.ExameController;
import cv.com.escola.controller.ExameResultadoController;
import cv.com.escola.controller.HomeController;
import cv.com.escola.controller.OrganizacaoController;
import cv.com.escola.controller.UsuarioController;
import cv.com.escola.controller.ListaBenificiosController;
import cv.com.escola.controller.VeiculoController;
import cv.com.escola.controller.InspeccaoTecnicaController;
import cv.com.escola.controller.MenuVeiculoController;
import cv.com.escola.controller.RelatorioEscolarController;
import cv.com.escola.controller.SeguroController;
import cv.com.escola.controller.RelatoriosGeraisController;
import cv.com.escola.controller.RegistroVendaController;
import cv.com.escola.controller.DashVendaController;
import cv.com.escola.controller.InscricaoController;
import cv.com.escola.controller.InstrutorController;
import javafx.scene.layout.FlowPane;

/**
 * Principal classe para controle e carregamento dos modulos da aplicação, cada
 * menu e alguns submenus da aplicação representa um modulo na aplicação
 */
public class Modulo {

    private static AlunoController alunoController;
    private static CategoriaController categoria;
    private static UsuarioController usuario;
    private static OrganizacaoController organizacao;
    private static ExameController exame;
    private static DadosIniciaisController empresa;
    private static ListaBenificiosController benificio;
    private static CargoSalarioController cargoSalario;
    private static ConfigEmpresaController configEmpresa;
    private static HomeController home;
    private static VeiculoController veiculoController;
    private static CursoController cursoController;
    private static ExameResultadoController exameResultadoController;
    private static InspeccaoTecnicaController inspeccaoTecnicaController;
    private static MenuVeiculoController menuVeiculoController;
    private static JasperViewerFX jasperViewerFX;
    private static SeguroController seguroController;
    private static RelatorioEscolarController relatorioEscolarController;
    private static ArtigoController artigoController;
    private static ClienteController clienteController;
    private static RelatoriosGeraisController relatoriosGeraisController;
    private static RegistroVendaController registroVendaController;
    private static DashVendaController dashVendaController;
    private static InstrutorController instrutorController;
    private static InscricaoController inscricaoController;

    private Modulo() {
    }

    public static void getJasperViewerFX(AnchorPane box) {
        jasperViewerFX = jasperViewerFX == null ? new JasperViewerFX() : jasperViewerFX;
    }

    public static void getCurso(AnchorPane box) {
        cursoController = cursoController == null ? new CursoController() : cursoController;
        config(box, cursoController);
    }

    public static void getVeiculo(AnchorPane box) {
        veiculoController = veiculoController == null ? new VeiculoController() : veiculoController;
        config(box, veiculoController);
    }

    public static void getCategoria(AnchorPane box) {
        categoria = categoria == null ? new CategoriaController() : categoria;
        config(box, categoria);
    }

    public static void getUsuario(AnchorPane box) {
        usuario = usuario == null ? new UsuarioController() : usuario;
        config(box, usuario);
    }

    public static void getAluno(AnchorPane box) {
        alunoController = alunoController == null ? new AlunoController() : alunoController;
        config(box, alunoController);

    }

    public static void getOrganizacao(AnchorPane box) {
        organizacao = organizacao == null ? new OrganizacaoController() : organizacao;
        config(box, organizacao);
    }

    public static void getExame(AnchorPane box) {
        exame = exame == null ? new ExameController() : exame;
        config(box, exame);
    }

    public static void getExameResultado(AnchorPane box) {
        exameResultadoController = exameResultadoController == null ? new ExameResultadoController() : exameResultadoController;
        config(box, exameResultadoController);
    }

    public static void getEmpresa(AnchorPane box) {
        empresa = empresa == null ? new DadosIniciaisController() : empresa;
        config(box, empresa);
    }

    public static void getListaBenificio(AnchorPane box) {
        benificio = benificio == null ? new ListaBenificiosController() : benificio;
        config(box, benificio);
    }

    public static void getConfigEmpresa(AnchorPane box) {
        configEmpresa = configEmpresa == null ? new ConfigEmpresaController() : configEmpresa;
        config(box, configEmpresa);
    }

    public static void getCargo_Salario(AnchorPane box) {
        cargoSalario = cargoSalario == null ? new CargoSalarioController() : cargoSalario;
        config(box, cargoSalario);
    }

    public static void getHome(AnchorPane box) {
        home = home == null ? new HomeController() : home;
        config(box, home);
    }

    public static void getInspeccaoTecnica(AnchorPane box) {
        inspeccaoTecnicaController = inspeccaoTecnicaController == null ? new InspeccaoTecnicaController() : inspeccaoTecnicaController;
        config(box, inspeccaoTecnicaController);
    }

    public static void getMenuVeiculo(AnchorPane box) {
        menuVeiculoController = menuVeiculoController == null ? new MenuVeiculoController() : menuVeiculoController;
        config(box, menuVeiculoController);
    }

    public static void getSeguro(AnchorPane box) {
        seguroController = seguroController == null ? new SeguroController() : seguroController;
        config(box, seguroController);
    }

    public static void getRelatorioEscolar(AnchorPane box) {
        relatorioEscolarController = relatorioEscolarController == null ? new RelatorioEscolarController() : relatorioEscolarController;
        config(box, relatorioEscolarController);
    }

    public static void getArtigo(AnchorPane box) {
        artigoController = artigoController == null ? new ArtigoController() : artigoController;
        config(box, artigoController);
    }

    public static void getCliente(AnchorPane box) {
        clienteController = clienteController == null ? new ClienteController() : clienteController;
        config(box, clienteController);
    }

    public static void getRelatoriosGerais(AnchorPane box) {
        relatoriosGeraisController = relatoriosGeraisController == null ? new RelatoriosGeraisController() : relatoriosGeraisController;
        config(box, relatoriosGeraisController);
    }

    public static void getRegistroVenda(AnchorPane box) {
        registroVendaController = registroVendaController == null ? new RegistroVendaController() : registroVendaController;
        config(box, registroVendaController);
    }

    public static void getDashVenda(AnchorPane box) {
        dashVendaController = dashVendaController == null ? new DashVendaController() : dashVendaController;
        config(box, dashVendaController);
    }

    public static void getInstrutor(AnchorPane box) {
        instrutorController = instrutorController == null ? new InstrutorController() : instrutorController;
        config(box, instrutorController);
    }

    public static void getInscricao(AnchorPane box) {
        inscricaoController = inscricaoController == null ? new InscricaoController() : inscricaoController;
        config(box, inscricaoController);
    }

    /**
     * Configuração da tela de conteúdo para limpar painel e adicionar nova
     * tela, redimensionado seu tamanho para preencher a tela
     *
     * @param box
     * @param conteudo
     */
    public static void config(AnchorPane box, AnchorPane conteudo) {
        box.getChildren().clear();
        box.getChildren().add(conteudo);
        Resize.margin(conteudo, 0);
    }

    public static void configFlowPane(FlowPane box, AnchorPane conteudo) {
        box.getChildren().clear();
        box.getChildren().add(conteudo);
        Resize.margin(conteudo, 0);
    }

    /**
     * Auxiliar na visualização de elementos da tela como: subenus, subtelas e
     * etc...
     *
     * @param valor
     * @param no
     */
    public static void visualizacao(boolean valor, Node... no) {
        for (Node elemento : no) {
            elemento.setVisible(valor);
        }
    }
}
