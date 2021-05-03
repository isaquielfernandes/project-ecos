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
    private TextField txtAnoFabrico;
    @FXML
    private TextField txtNomePropretario;
    @FXML
    private TextField txtEmailPropretario;
    @FXML
    private TextField txtAnoModelo;
    @FXML
    private TextField txtValor;
    @FXML
    private TextArea txtEspecificacao;
    @FXML
    private TextField txtFabricante;
    @FXML
    private TextField txtIlha;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtTelefonePropretario;
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
    private TableColumn<?, ?> colAnoFabrico;
    @FXML
    private TableColumn<?, ?> colAnoModelo;
    @FXML
    private TableColumn<Veiculo, String> colPropretario;
    @FXML
    private TableColumn<?, ?> colValor;
    @FXML
    private TableColumn<?, ?> colTipoCombustivel;
    @FXML
    private TableColumn<?, ?> colDataCadastro;
    @FXML
    private TableColumn<?, ?> colEspecificacao;
    @FXML
    private TableColumn<?, ?> colDataModificacao;
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
        txtPesquisar.textProperty().addListener((obs, old, novo) -> 
            filtro(novo, FXCollections.observableArrayList(listaVeiculo))
        );
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public VeiculoController() {
        try {
            GenericFXXMLLoader.loadFXML(this, "veiculo");
        } catch (IOException ex) {
            Logger.getLogger(VeiculoController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela veiculo");
        }
    }

    public void sincronizaBase() {
        listaVeiculo = DAOFactory.daoFactory().veiculoDAO().findAll();
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
        boolean emptyFields = checkEmptyFields(txtPlaca, txtAnoFabrico, txtAnoModelo, txtModelo, txtFabricante, txtIlha, txtValor, txtNomePropretario, dtCadastro);
        
        String placa = txtPlaca.getText();
        String ilha = txtIlha.getText();
        String fabricante = txtFabricante.getText();
        String modelo = txtModelo.getText();
        int anoFabricacao = Integer.parseInt(txtAnoFabrico.getText().trim().isEmpty() ? "0" : txtAnoFabrico.getText());
        int anoModelo = Integer.parseInt(txtAnoModelo.getText().trim().isEmpty() ? "0" : txtAnoModelo.getText());
        String valor = txtValor.getText();
        String tipoCombustivel = cbTipoCombustivel.getSelectionModel().getSelectedItem();

        Proprietario proprietario = new Proprietario(txtNomePropretario.getText(), txtTelefonePropretario.getText(), txtEmailPropretario.getText());

        LocalDate dataCadastro = dtCadastro.getValue();
        String especificacao = txtEspecificacao.getText();
        Veiculo veiculos = new Veiculo(placa, ilha);

        if (emptyFields){
            Veiculo veiculo = new Veiculo(idVeiculo, placa, ilha, fabricante, modelo, anoFabricacao, anoModelo, valor, tipoCombustivel, proprietario, dataCadastro, especificacao);
            if (idVeiculo == 0) {
                if (DAOFactory.daoFactory().veiculoDAO().findAll().contains(veiculos)) {
                    Nota.alerta("Veiculo já cadastrada!");
                } else {
                    DAOFactory.daoFactory().veiculoDAO().create(veiculo);
                    Mensagem.info("Veiculo cadastrada com sucesso!"); 
                }
            } else {
                DAOFactory.daoFactory().veiculoDAO().update(veiculo);
                Mensagem.info("Veiculo atualizada com sucesso!");
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
            txtAnoFabrico.setText(String.valueOf(veiculo.getAnoFabricacao()));
            txtAnoModelo.setText(String.valueOf(veiculo.getAnoModelo()));
            txtValor.setText("" + veiculo.getChassi());
            cbTipoCombustivel.setValue(veiculo.getTipoCombustivel());
            txtNomePropretario.setText(veiculo.getProprietario().getNomePropretario());
            txtEmailPropretario.setText(veiculo.getProprietario().getEmailPropertario());
            txtTelefonePropretario.setText(veiculo.getProprietario().getTelefonePropretario());
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
                DAOFactory.daoFactory().veiculoDAO().delete(veiculo.getCodigo());
                sincronizaBase();
                tebela();
            }
            tbVeiculo.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione veiculo na tabela para exclusão!");
        }
    }

    private void limparCampo() {
        Campo.limpar(txtAnoModelo, txtValor, txtAnoFabrico, txtFabricante, txtModelo, txtPlaca,
                txtIlha, txtNomePropretario, txtTelefonePropretario, txtEmailPropretario);
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
        colAnoFabrico.setCellValueFactory(new PropertyValueFactory<>("anoFabricacao"));
        colAnoModelo.setCellValueFactory(new PropertyValueFactory<>("anoModelo"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("chassi"));
        colTipoCombustivel.setCellValueFactory(new PropertyValueFactory<>("tipoCombustivel"));
        colPropretario.setCellValueFactory((TableColumn.CellDataFeatures<Veiculo, String> obj) -> new SimpleStringProperty(obj.getValue().getProprietario().getNomePropretario()));
        colDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));

        colEspecificacao.setCellValueFactory(new PropertyValueFactory<>("especificacao"));

        tbVeiculo.setItems(data);
    }

    private void filtro(String novo, ObservableList<Veiculo> listaVeiculo) {
        FilteredList<Veiculo> dadosFiltrados = new FilteredList<>(listaVeiculo, veivulo -> true);
        dadosFiltrados.setPredicate(veiculo -> 
                veiculo.getPlaca().toLowerCase().startsWith(novo.toUpperCase()) || 
                veiculo.getProprietario().getNomePropretario().toLowerCase().startsWith(novo.toUpperCase()) || 
                veiculo.getFabricante().toLowerCase().startsWith(novo.toUpperCase()) || 
                veiculo.getModelo().toLowerCase().startsWith(novo.toLowerCase()) 
        );
        SortedList<Veiculo> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbVeiculo.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de veiculo cadastrado");

        tbVeiculo.setItems(dadosOrdenados);
    }

}
