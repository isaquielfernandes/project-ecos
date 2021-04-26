package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Seguro;
import cv.com.escola.model.entity.Veiculo;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Combo;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Grupo;
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
public class SeguroController extends AnchorPane implements Initializable {

    private List<Seguro> listaSeguroAuto;
    private List<Veiculo> listaVeiculo;
    private int idSeguroAuto;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private ToggleGroup menu;
    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextField txtProprietario;
    @FXML
    private DatePicker dataEmissao;
    @FXML
    private ComboBox<Veiculo> cbVeiculo;
    @FXML
    private DatePicker datePickerDesde;
    @FXML
    private DatePicker datePickerAte;
    @FXML
    private TextField txtCompania;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableView<Seguro> tbSeguro;
    @FXML
    private TableColumn<Seguro, Long> colId;
    @FXML
    private TableColumn<Seguro, String> colMatricula;
    @FXML
    private TableColumn<Seguro, String> colMarca;
    @FXML
    private TableColumn<Seguro, String> colModelo;
    @FXML
    private TableColumn<Seguro, LocalDate> colDataMatricula;
    @FXML
    private TableColumn<Seguro, Integer> colAnoFabrico;
    @FXML
    private TableColumn<Seguro, String> colProprietario;
    @FXML
    private TableColumn<Seguro, String> colRamo;
    @FXML
    private TableColumn<Seguro, LocalDate> colDataEmissao;
    @FXML
    private TableColumn<Seguro, LocalDate> colDesde;
    @FXML
    private TableColumn<Seguro, LocalDate> colValidade;
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
        cbVeiculo.setOnMouseClicked(event -> {
            sincronizaBase();
            combos();
        });
        selectComboBoxVeiculo(null);
        cbVeiculo.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectComboBoxVeiculo(newValue)
        );
    }
    
    public void selectComboBoxVeiculo(Veiculo veiculo){
        if (veiculo != null) {
            txtProprietario.setText(veiculo.getProprietario().getNomePropretario());
        } else {
            txtProprietario.clear();
        }
    }
    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Seguro Auto", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampo();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("Editar Seguro Auto", "Campos obrigatorio", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Excluir Seguro Auto", "Quantidade de seguro encontrado", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaPrint(ActionEvent event) {
        configTela("Imprimir Seguro Auto", "Quantidade de seguro encontrado", 3);
        Modulo.visualizacao(true, telaEdicao, btImprimir, txtPesquisar);
        tabela();
    }

    @FXML
    @SuppressWarnings("element-type-mismatch")
    private void salvar(ActionEvent event) {
        try {
            boolean emptyFields = checkEmptyFields(txtProprietario, dataEmissao, datePickerDesde, datePickerAte);
            
            String compania = txtCompania.getText();
            LocalDate desde = datePickerDesde.getValue();
            LocalDate ate = datePickerAte.getValue();
            LocalDate emissao = dataEmissao.getValue();
            Veiculo veiculo = cbVeiculo.getValue();

            if (emptyFields) {
                Seguro seguro = new Seguro((long) idSeguroAuto, compania, veiculo, desde, ate, emissao);

                if (idSeguroAuto == 0) {
                    if (DAOFactory.daoFactury().seguroAutoDAO().findAll().contains(seguro)) {
                        Nota.alerta("Veiculo já cadastrada!");
                    } else {
                        DAOFactory.daoFactury().seguroAutoDAO().create(seguro);
                    }
                } else {
                    DAOFactory.daoFactury().seguroAutoDAO().update(seguro);
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
            Seguro seguroAuto = tbSeguro.getSelectionModel().getSelectedItem();
            seguroAuto.getClass();
            
            telaCadastro(null);
            cbVeiculo.setValue(seguroAuto.getVeiculo());
            txtProprietario.setText(seguroAuto.getVeiculo().getProprietario().getNomePropretario());
            dataEmissao.setValue(seguroAuto.getEmissao());
            datePickerDesde.setValue(seguroAuto.getDeste());
            datePickerAte.setValue(seguroAuto.getValidade());
            txtCompania.setText(seguroAuto.getCompania());
            
            lbTitulo.setText("Editar Seguro Auto");
            menu.selectToggle(menu.getToggles().get(1));
            
            idSeguroAuto = (int) seguroAuto.getId();
            
        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um seguro auto na tabela para edição!");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        try {
            Seguro veiculo = tbSeguro.getSelectionModel().getSelectedItem();
            Dialogo.Resposta resposta = Mensagem.confirmar("Excluir Seguro Auto : Veiculo -> " + veiculo.getVeiculo().getPlaca() + " Dia :" + veiculo.getEmissao());

            if (resposta == Dialogo.Resposta.YES) {
                DAOFactory.daoFactury().inspecaoTecnicaDAO().delete(veiculo.getId());
                sincronizaBase();
                tabela();
            }
            tbSeguro.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione seguro na tabela para exclusão!");
        }
    }

    @FXML
    private void imprimir(ActionEvent event) {
        throw new UnsupportedOperationException();
    }

    private void combos() {
        Combo.popular(cbVeiculo, listaVeiculo);
    }

    private void limparCampo() {
        Campo.limpar(txtCompania, txtProprietario);
        Campo.limpar(dataEmissao, datePickerDesde, datePickerAte);
        Campo.limpar(cbVeiculo);
    }

    public void sincronizaBase() {
        listaSeguroAuto = DAOFactory.daoFactury().seguroAutoDAO().findAll();
        listaVeiculo = DAOFactory.daoFactury().veiculoDAO().findAll();
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btImprimir, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbSeguro.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idSeguroAuto = 0;
    }

    private void tabela() {

        ObservableList data = FXCollections.observableArrayList(listaSeguroAuto);

        colId.setCellValueFactory((CellDataFeatures<Seguro, Long> obj) -> obj.getValue().idProperty().asObject());
        colDataEmissao.setCellValueFactory((CellDataFeatures<Seguro, LocalDate> obj) -> obj.getValue().emissaoProperty());
        colDesde.setCellValueFactory((CellDataFeatures<Seguro, LocalDate> p) -> p.getValue().desteProperty());
        colValidade.setCellValueFactory((CellDataFeatures<Seguro, LocalDate> obj) -> obj.getValue().validadeProperty());
        colRamo.setCellValueFactory((CellDataFeatures<Seguro, String> obj) -> obj.getValue().companiaProperty());
        
        colMatricula.setCellValueFactory((CellDataFeatures<Seguro, String> obj) -> obj.getValue().veiculoProperty().getValue().placaProperty());
        colAnoFabrico.setCellValueFactory((CellDataFeatures<Seguro, Integer> obj) -> obj.getValue().veiculoProperty().getValue().anoFabricacaoProperty().asObject());
        colMarca.setCellValueFactory((CellDataFeatures<Seguro, String> obj) -> obj.getValue().veiculoProperty().getValue().fabricanteProperty());
        colModelo.setCellValueFactory((CellDataFeatures<Seguro, String> obj) -> obj.getValue().veiculoProperty().getValue().modeloProperty());
        colProprietario.setCellValueFactory((CellDataFeatures<Seguro, String> obj) -> obj.getValue().veiculoProperty().getValue().getProprietario().nomePropretarioProperty());
        
        tbSeguro.setItems(data);
    }
    
    @SuppressWarnings("LeakingThisInConstructor")
    public SeguroController() {
        try {
            GenericFXXMLLoader.loadFXML(this, "seguroAuto");
        } catch (IOException ex) {
            Logger.getLogger(SeguroController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela seguro auto");
        }
    }
}
