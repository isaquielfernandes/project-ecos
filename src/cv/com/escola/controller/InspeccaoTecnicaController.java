package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.InspecaoTecnica;
import cv.com.escola.model.entity.Veiculo;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Combo;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Grupo;
import cv.com.escola.model.util.Mascara;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import cv.com.escola.model.util.Nota;
import static cv.com.escola.model.util.ValidationFields.checkEmptyFields;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class InspeccaoTecnicaController extends AnchorPane implements Initializable {

    private List<InspecaoTecnica> listaInspecaoTecnicao;
    private List<Veiculo> listaVeiculo;
    private int idInspecaoTecnica;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private ToggleGroup menu;
    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextField txtInspeccao;
    @FXML
    private ComboBox<String> cbResultado;
    @FXML
    private TextField txtDuracao;
    @FXML
    private DatePicker dataDeInspeccao;
    @FXML
    private ComboBox<Veiculo> cbVeiculo;
    @FXML
    private Label lbValidade;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableView<InspecaoTecnica> tbInspeccao;
    @FXML
    private TableColumn<InspecaoTecnica, Long> colId;
    @FXML
    private TableColumn<InspecaoTecnica, String> colMatricula;
    @FXML
    private TableColumn<InspecaoTecnica, String> colMarca;
    @FXML
    private TableColumn<InspecaoTecnica, String> colModelo;
    @FXML
    private TableColumn<InspecaoTecnica, String> colDataMatricula;
    @FXML
    private TableColumn<InspecaoTecnica, Integer> colAnoFabrico;
    @FXML
    private TableColumn<InspecaoTecnica, String> colTipoInspeccao;
    @FXML
    private TableColumn<InspecaoTecnica, String> colResultado;
    @FXML
    private TableColumn<InspecaoTecnica, LocalDate> colDataInspecao;
    @FXML
    private TableColumn<InspecaoTecnica, Integer> colDuracaoEmMes;
    @FXML
    private TableColumn<InspecaoTecnica, String> colValidade;
    @FXML
    private Button btSalvar;
    @FXML
    private Button btEditar;
    @FXML
    private Button btExcluir;
    @FXML
    private Button btImprimir;
    @FXML
    private Label legenda;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sincronizaBase();
        telaCadastro(null);
        Grupo.notEmpty(menu);
        combos();
        Mascara.numerico(txtDuracao);
        cbVeiculo.setOnMouseClicked(event -> {
            sincronizaBase();
            combos();
        });
        txtDuracao.setOnKeyReleased(event -> {
            if(!txtDuracao.getText().trim().isEmpty()){
                lbValidade.setText(dataDeInspeccao.getValue().plusMonths(Long.parseLong(txtDuracao.getText())) + "");
            }else {
                lbValidade.setText(null);
            }
        });
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Inspecção Tecnica", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampo();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("Editar Inspecção Tecnica", "Campos obrigatorio", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Excluir Inspecção Tecnica", "Quantidade de veiculo encontrado", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaPrint(ActionEvent event) {
        configTela("Imprimir Inspecção Tecnica", "Quantidade de veiculo encontrado", 3);
        Modulo.visualizacao(true, telaEdicao, btImprimir, txtPesquisar);
        tabela();
    }

    @FXML
    @SuppressWarnings("element-type-mismatch")
    private void salvar(ActionEvent event) {
        try {
            boolean emptyFields = checkEmptyFields(txtInspeccao, txtDuracao, dataDeInspeccao);
            
            String tipoInspecao = txtInspeccao.getText();
            LocalDate dataInspecao = dataDeInspeccao.getValue();
            Veiculo veiculo = cbVeiculo.getValue();
            String resultado = cbResultado.getValue();
            Integer duracao = Integer.valueOf(txtDuracao.getText().trim().isEmpty() ? "0" : txtDuracao.getText());
            LocalDate validade = dataInspecao.plusMonths(duracao);

            if (emptyFields) {
                InspecaoTecnica inspecao = new InspecaoTecnica(idInspecaoTecnica, tipoInspecao, resultado, dataInspecao, veiculo, duracao, validade.toString());

                if (idInspecaoTecnica == 0) {
                    if (DAOFactory.daoFactury().inspecaoTecnicaDAO().findAll().contains(inspecao)) {
                        Nota.alerta("Veiculo já cadastrada!");
                    } else {
                        DAOFactory.daoFactury().inspecaoTecnicaDAO().create(inspecao);
                    }
                } else {
                    DAOFactory.daoFactury().inspecaoTecnicaDAO().update(inspecao);
                }

                telaCadastro(null);
                sincronizaBase();
            }
        } catch (NullPointerException ex) {
            Nota.alerta("Erro! Campo vazio");
        }

    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            InspecaoTecnica inspecao = tbInspeccao.getSelectionModel().getSelectedItem();
            inspecao.getClass();
            
            telaCadastro(null);
            cbVeiculo.setValue(inspecao.getVeiculo());
            txtInspeccao.setText(inspecao.getTipoDeInspeccao());
            dataDeInspeccao.setValue(inspecao.getDataDeInspeccao());
            txtDuracao.setText(String.valueOf(inspecao.getMesDeDuracao()));
            cbResultado.setValue(inspecao.getResultado());
            lbValidade.setText(inspecao.getValidade());
            
            lbTitulo.setText("Editar Inspecao Tecnica");
            menu.selectToggle(menu.getToggles().get(1));
            
            idInspecaoTecnica = (int) inspecao.getId();
            
        } catch (NullPointerException ex) {
            Nota.alerta("Selecione uma inspecao tecnica na tabela para edição!");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        try {
            InspecaoTecnica veiculo = tbInspeccao.getSelectionModel().getSelectedItem();
            Dialogo.Resposta resposta = Mensagem.confirmar("Excluir Inspecao Tecnica : Veiculo -> " + veiculo.getVeiculo().getPlaca() + " Dia :" + veiculo.getDataDeInspeccao());

            if (resposta == Dialogo.Resposta.YES) {
                DAOFactory.daoFactury().inspecaoTecnicaDAO().delete(veiculo.getId());
                sincronizaBase();
                tabela();
            }
            tbInspeccao.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione inspecao na tabela para exclusão!");
        }
    }

    @FXML
    private void imprimir(ActionEvent event) {
    }

    private void combos() {
        Combo.popular(cbResultado, "Aprovado", "Reprovado");
        Combo.popular(cbVeiculo, listaVeiculo);
        
    }

    private void limparCampo() {
        Campo.limpar(txtDuracao, txtInspeccao);
        Campo.limpar(dataDeInspeccao);
        Campo.limpar(cbResultado, cbVeiculo);
        lbValidade.setText(null);
    }

    public void sincronizaBase() {
        listaInspecaoTecnicao = DAOFactory.daoFactury().inspecaoTecnicaDAO().findAll();
        listaVeiculo = DAOFactory.daoFactury().veiculoDAO().findAll();
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btImprimir, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbInspeccao.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idInspecaoTecnica = 0;
    }

    private void tabela() {

        ObservableList data = FXCollections.observableArrayList(listaInspecaoTecnicao);

        colId.setCellValueFactory((CellDataFeatures<InspecaoTecnica, Long> obj) -> obj.getValue().idProperty().asObject());
        colTipoInspeccao.setCellValueFactory((CellDataFeatures<InspecaoTecnica, String> obj) -> obj.getValue().tipoDeInspeccaoProperty());
        colDataInspecao.setCellValueFactory((CellDataFeatures<InspecaoTecnica, LocalDate> obj) -> obj.getValue().dataDeInspeccaoProperty());
        colDuracaoEmMes.setCellValueFactory((CellDataFeatures<InspecaoTecnica, Integer> p) -> p.getValue().mesDeDuracaoProperty().asObject());
        colResultado.setCellValueFactory((CellDataFeatures<InspecaoTecnica, String> obj) -> obj.getValue().resultadoProperty());
        colValidade.setCellValueFactory((CellDataFeatures<InspecaoTecnica, String> obj) -> obj.getValue().validadeProperty());
        
        colMatricula.setCellValueFactory((CellDataFeatures<InspecaoTecnica, String> obj) -> obj.getValue().veiculoProperty().getValue().placaProperty());
        colAnoFabrico.setCellValueFactory((CellDataFeatures<InspecaoTecnica, Integer> obj) -> obj.getValue().veiculoProperty().getValue().anoFabricacaoProperty().asObject());
        colMarca.setCellValueFactory((CellDataFeatures<InspecaoTecnica, String> obj) -> obj.getValue().veiculoProperty().getValue().fabricanteProperty());
        colModelo.setCellValueFactory((CellDataFeatures<InspecaoTecnica, String> obj) -> obj.getValue().veiculoProperty().getValue().modeloProperty());
        
        tbInspeccao.setItems(data);
    }
    
    @SuppressWarnings("LeakingThisInConstructor")
    public InspeccaoTecnicaController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/cv/com/escola/view/inspeccaoTecnica.fxml"));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(InspeccaoTecnicaController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela inspecção tecnica" + ex);
        }
    }
}
