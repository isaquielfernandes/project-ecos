package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.CargoSalario;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Filtro;
import cv.com.escola.model.util.Grupo;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import cv.com.escola.model.util.Nota;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class CargoSalarioController extends AnchorPane implements Initializable {

    private int idCargoSalario;
    private List<CargoSalario> cargoSalarios;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private ToggleGroup menu;
    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextField txtNomeCargo;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private TextField txtSalario;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableView<CargoSalario> tbCargoSalario;
    @FXML
    private TableColumn<CargoSalario, Integer> colId;
    @FXML
    private TableColumn<CargoSalario, String> colCargo;
    @FXML
    private TableColumn<CargoSalario, Double> colSalario;
    @FXML
    private TableColumn<CargoSalario, String> colDescricao;
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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtSalario.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER){
                salvar(null);
            }
        });
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarBase();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> 
            filtro(novo, FXCollections.observableArrayList(cargoSalarios))
        );
    }   
    @SuppressWarnings("LeakingThisInConstructor")
    public CargoSalarioController() {
        try {
            GenericFXXMLLoader.loadFXML(this, "cargosalario");
        } catch (IOException ex) {
            Logger.getLogger(CargoSalarioController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela Cargo Salario!");
        }
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        cargoSalarios = DAOFactory.daoFactury().getCargoSalarioDAO().findAll();
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbCargoSalario.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idCargoSalario = 0;
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limparCampos() {
        Campo.limpar(this.txtNomeCargo, this.txtSalario);
        Campo.limpar(txtDescricao);
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Cadastrar cargo & salario", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampos();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("Editar cargo & salario", "Quantidade de cargo encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Excluir cargo & salario", "Quantidade de cargo encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean vazio = Campo.noEmpty(this.txtNomeCargo, this.txtSalario);

        String cargo = txtNomeCargo.getText();
        double salario = Double.parseDouble(txtSalario.getText());
        String descricao = txtDescricao.getText();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else if (DAOFactory.daoFactury().getCargoSalarioDAO().isCargoSalario(cargo, idCargoSalario)) {
            Nota.alerta("Cargo já cadastrada!");
        } else {
            CargoSalario cargoSalario = new CargoSalario(idCargoSalario, cargo, salario, descricao);

            if (idCargoSalario == 0) {
                DAOFactory.daoFactury().getCargoSalarioDAO().create(cargoSalario);
                Mensagem.info("Cargo cadastrada com sucesso!");
            } else {
                DAOFactory.daoFactury().getCargoSalarioDAO().update(cargoSalario);
                Mensagem.info("Cargo atualizada com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            CargoSalario cargoSalario = tbCargoSalario.getSelectionModel().getSelectedItem();
            cargoSalario.getClass();

            telaCadastro(null);

            txtNomeCargo.setText(cargoSalario.getNomeCargo());
            txtSalario.setText(Double.toString(cargoSalario.getSalario()));
            txtDescricao.setText(cargoSalario.getDescricao());

            lbTitulo.setText("Editar Cargo & salario");
            menu.selectToggle(menu.getToggles().get(1));

            idCargoSalario = cargoSalario.getIdcargoSalario();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione uma cargo & salario na tabela para edição!");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        try {
            CargoSalario cargo = tbCargoSalario.getSelectionModel().getSelectedItem();
            Dialogo.Resposta response = Mensagem.confirmar("Excluir cargo " + cargo.getNomeCargo()+ " ?");
            if (response == Dialogo.Resposta.YES) {
                DAOFactory.daoFactury().getCargoSalarioDAO().delete(cargo.getIdcargoSalario());
                sincronizarBase();
                tabela();
            }
            tbCargoSalario.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione cargo na tabela para exclusão!");
        }
    }
    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(cargoSalarios);

        colId.setCellValueFactory((CellDataFeatures<CargoSalario, Integer> obj) -> obj.getValue().idCargoSalarioProperty().asObject());
        colCargo.setCellValueFactory((CellDataFeatures<CargoSalario, String> obj) -> obj.getValue().nomeCargoProperty());
        colSalario.setCellValueFactory((CellDataFeatures<CargoSalario, Double> obj) -> obj.getValue().salarioProperty().asObject());
        colDescricao.setCellValueFactory((CellDataFeatures<CargoSalario, String> obj) -> obj.getValue().descricaoProperty());
        tbCargoSalario.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtro(String valor, ObservableList<CargoSalario> cargoSalarios) {
        FilteredList<CargoSalario> dadosFiltrados = new FilteredList<>(cargoSalarios, cargo -> true);
        dadosFiltrados.setPredicate(cargo -> 
            cargo.getNomeCargo().toLowerCase().startsWith(valor.toLowerCase())
        );
        SortedList<CargoSalario> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbCargoSalario.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de cargos encontrados");
        tbCargoSalario.setItems(dadosOrdenados);
    }
}
