package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Proprietario;
import cv.com.escola.model.entity.Veiculo;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Combo;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Filtro;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class VeiculoController extends AnchorPane implements Initializable {

    private List<Veiculo> listaVeiculo;
    private long idVeiculo;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private ToggleGroup menu;
    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextField txtPlaca;
    @FXML
    private TextField txtAno_fabrico;
    @FXML
    private TextField txtNome_propretario;
    @FXML
    private TextField txtEmail_propretario;
    @FXML
    private TextField txtAno_Modelo;
    @FXML
    private TextField txtLavor;
    @FXML
    private TextArea txtEspecificacao;
    @FXML
    private TextField txtFabricante;
    @FXML
    private TextField txtIlha;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtTelefone_propretario;
    @FXML
    private DatePicker dtCadastro;
    @FXML
    private ComboBox<String> cbTipoCombustivel;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableView<Veiculo> tbVeiculo;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colPlaca;
    @FXML
    private TableColumn<Veiculo, String> colIlha;
    @FXML
    private TableColumn<?, ?> colFabricante;
    @FXML
    private TableColumn<?, ?> colModelo;
    @FXML
    private TableColumn<?, ?> colAno_fabrico;
    @FXML
    private TableColumn<?, ?> colAno_Modelo;
    @FXML
    private TableColumn<Veiculo, String> colPropretario;
    @FXML
    private TableColumn<?, ?> colValor;
    @FXML
    private TableColumn<?, ?> colTipo_Combustivel;
    @FXML
    private TableColumn<?, ?> colData_Cadastro;
    @FXML
    private TableColumn<?, ?> colEspecificacao;
    @FXML
    private TableColumn<?, ?> colData_Modificacao;
    @FXML
    private Button btSalvar;
    @FXML
    private Button btEditar;
    @FXML
    private Button btExcluir;
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
        dtCadastro.setValue(LocalDate.now());
        telaCadastro(null);
        Grupo.notEmpty(menu);
        sincronizaBase();
        combos();
        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaVeiculo));
        });
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public VeiculoController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/cv/com/escola/view/veiculo.fxml"));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(VeiculoController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela veiculo" + ex);
        }
    }

    public void sincronizaBase() {
        listaVeiculo = DAOFactory.daoFactury().veiculoDAO().findAll();
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbVeiculo.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idVeiculo = 0;
    }

    /**
     * Preencher combos tela
     */
    private void combos() {
        Combo.popular(cbTipoCombustivel, "Dissel", "Gasolina", "Bio-Combustivel", "Alcool");
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Veiculo", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampo();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("Editar Veiculo", "Campos obrigatorio", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tebela();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Excluir Veiculo", "Quantidade de veiculo encontrado", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tebela();
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean emptyFields = checkEmptyFields(txtPlaca, txtAno_fabrico, txtAno_Modelo, txtModelo, txtFabricante, txtIlha, txtLavor, txtNome_propretario, dtCadastro);
        
        String placa = txtPlaca.getText();
        String ilha = txtIlha.getText();
        String fabricante = txtFabricante.getText();
        String modelo = txtModelo.getText();
        int anoFabricacao = Integer.parseInt(txtAno_fabrico.getText().trim().isEmpty() ? "0" : txtAno_fabrico.getText());
        int anoModelo = Integer.parseInt(txtAno_Modelo.getText().trim().isEmpty() ? "0" : txtAno_Modelo.getText());
        String valor = txtLavor.getText();
        String tipoCombustivel = cbTipoCombustivel.getSelectionModel().getSelectedItem();

        Proprietario proprietario = new Proprietario(txtNome_propretario.getText(), txtTelefone_propretario.getText(), txtEmail_propretario.getText());

        LocalDate dataCadastro = dtCadastro.getValue();
        String especificacao = txtEspecificacao.getText();
        Veiculo veiculos = new Veiculo(placa, ilha);

        if (emptyFields){
            Veiculo veiculo = new Veiculo(idVeiculo, placa, ilha, fabricante, modelo, anoFabricacao, anoModelo, txtLavor.getText(), tipoCombustivel, proprietario, dataCadastro, especificacao);
            if (idVeiculo == 0) {
                if (DAOFactory.daoFactury().veiculoDAO().findAll().contains(veiculos)) {
                    Nota.alerta("Veiculo já cadastrada!");
                } else {
                    DAOFactory.daoFactury().veiculoDAO().create(veiculo);
                    //Mensagem.info("Veiculo cadastrada com sucesso!"); 
                }
            } else {
                DAOFactory.daoFactury().veiculoDAO().update(veiculo);
                //Mensagem.info("Veiculo atualizada com sucesso!");
            }

            telaCadastro(null);
            sincronizaBase();
        }

    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            Veiculo veiculo = tbVeiculo.getSelectionModel().getSelectedItem();
            veiculo.getClass();
            telaCadastro(null);

            txtPlaca.setText(veiculo.getPlaca());
            txtIlha.setText(veiculo.getCidade());
            txtFabricante.setText(veiculo.getFabricante());
            txtModelo.setText(veiculo.getModelo());
            txtAno_fabrico.setText(String.valueOf(veiculo.getAnoFabricacao()));
            txtAno_Modelo.setText(String.valueOf(veiculo.getAnoModelo()));
            txtLavor.setText("" + veiculo.getChassi());
            cbTipoCombustivel.setValue(veiculo.getTipoCombustivel());
            txtNome_propretario.setText(veiculo.getProprietario().getNomePropretario());
            txtEmail_propretario.setText(veiculo.getProprietario().getEmailPropertario());
            txtTelefone_propretario.setText(veiculo.getProprietario().getTelefonePropretario());
            dtCadastro.setValue(veiculo.getDataCadastro());
            txtEspecificacao.setText(veiculo.getEspecificacoes());

            lbTitulo.setText("Editar veiculo");
            menu.selectToggle(menu.getToggles().get(1));

            idVeiculo = veiculo.getCodigo();
        } catch (NullPointerException ex) {
            Nota.alerta("Selecione uma veiculo na tabela para edição!");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        try {
            Veiculo veiculo = tbVeiculo.getSelectionModel().getSelectedItem();
            Dialogo.Resposta resposta = Mensagem.confirmar("Excluir Veiculo ->> " + veiculo.getPlaca());

            if (resposta == Dialogo.Resposta.YES) {
                DAOFactory.daoFactury().veiculoDAO().delete(veiculo.getCodigo());
                sincronizaBase();
                tebela();
            }
            tbVeiculo.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione veiculo na tabela para exclusão!");
        }
    }

    private void limparCampo() {
        Campo.limpar(txtAno_Modelo, txtLavor, txtAno_fabrico, txtFabricante, txtModelo, txtPlaca,
                txtIlha, txtNome_propretario, txtTelefone_propretario, txtEmail_propretario);
        Campo.limpar(txtEspecificacao);
        Campo.limpar(dtCadastro);
        Campo.limpar(cbTipoCombustivel);
    }

    private void tebela() {
        ObservableList data = FXCollections.observableArrayList(listaVeiculo);
        colId.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colIlha.setCellValueFactory((TableColumn.CellDataFeatures<Veiculo, String> obj) -> new SimpleStringProperty(obj.getValue().getCidade()));
        colFabricante.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colAno_fabrico.setCellValueFactory(new PropertyValueFactory<>("anoFabricacao"));
        colAno_Modelo.setCellValueFactory(new PropertyValueFactory<>("anoModelo"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("chassi"));
        colTipo_Combustivel.setCellValueFactory(new PropertyValueFactory<>("tipoCombustivel"));
        colPropretario.setCellValueFactory((TableColumn.CellDataFeatures<Veiculo, String> obj) -> new SimpleStringProperty(obj.getValue().getProprietario().getNomePropretario()));
        colData_Cadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));

        colEspecificacao.setCellValueFactory(new PropertyValueFactory<>("especificacao"));

        tbVeiculo.setItems(data);
    }

    private void filtro(String novo, ObservableList<Veiculo> listaVeiculo) {
        FilteredList<Veiculo> dadosFiltrados = new FilteredList<>(listaVeiculo, veivulo -> true);
        dadosFiltrados.setPredicate((veiculo) -> {
            if (novo == null || novo.isEmpty()) {
                return true;
            } else if (veiculo.getPlaca().toLowerCase().startsWith(novo.toUpperCase())) {
                return true;
            } else if (veiculo.getProprietario().getNomePropretario().toLowerCase().startsWith(novo.toUpperCase())) {
                return true;
            } else if (veiculo.getFabricante().toLowerCase().startsWith(novo.toUpperCase())) {
                return true;
            } else if (veiculo.getModelo().toLowerCase().startsWith(novo.toLowerCase())) {
                return true;
            }
            return false;
        });
        SortedList<Veiculo> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbVeiculo.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de veiculo cadastrado");

        tbVeiculo.setItems(dadosOrdenados);
    }

}
