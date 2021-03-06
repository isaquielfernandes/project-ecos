package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Artigo;
import cv.com.escola.model.util.Actions;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Filtro;
import cv.com.escola.model.util.Grupo;
import static cv.com.escola.model.util.Mascara.decimal;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import cv.com.escola.model.util.Nota;
import java.io.IOException;
import java.math.BigDecimal;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class ArtigoController extends AnchorPane implements Initializable {

    private static final String NOT_SUPPORTED_YET = "Not supported yet.";
    private List<Artigo> listaArtigo;
    private long idArtigo = 0;

    @FXML
    private GridPane telaCadastro;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private ToggleButton btAdcionar;
    @FXML
    private ToggleGroup menu;
    @FXML
    private ToggleButton btEdit;
    @FXML
    private ToggleButton btApagar;
    @FXML
    private ToggleButton btImpr;
    @FXML
    private ToggleButton btAtualizar;
    @FXML
    private TextField txtNome;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtPreco;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableView<Artigo> tbArtigo;
    @FXML
    private TableColumn<Artigo, Long> colCodigo;
    @FXML
    private TableColumn<Artigo, String> colNome;
    @FXML
    private TableColumn<Artigo, String> colDescricao;
    @FXML
    private TableColumn<Artigo, BigDecimal> colPreco;
    @FXML
    private TableColumn colActions;
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


    public ArtigoController() {
        try {
            GenericFXXMLLoader.loadFXML(this, "artigo");
        } catch (IOException ex) {
            Logger.getLogger(ArtigoController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela artigo!");
        }
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Cadastrar artigo", "Campos obrigat??rios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampos();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("Editar artigo", "Quantidade de artigo encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Excluir artigo", "Quantidade de artigo encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaImprimir(ActionEvent event) {
        configTela("Imprimir artigo", "Artigos", 3);
        Modulo.visualizacao(true, telaEdicao, btImprimir, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaAtualizar(ActionEvent event) {
        sincronizarBase();
        tabela();
    }

    @FXML
    @SuppressWarnings("element-type-mismatch")
    private void salvar(ActionEvent event) {
        boolean vazio = Campo.noEmpty(txtNome, txtPreco);

        String nome = txtNome.getText();
        String descricao = txtDescricao.getText();
        BigDecimal preco = new BigDecimal(txtPreco.getText().trim().isEmpty() ? "0" : txtPreco.getText());
        final boolean anyMatch = DAOFactory.daoFactory().artigoDAO().findAll().stream().anyMatch(a -> a.getId() == idArtigo);

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else if (anyMatch) {
            Nota.alerta("Artigo ja Cadastrado!");
        } else {
            Artigo artigo = new Artigo(idArtigo, nome, preco, descricao);

            if (idArtigo == 0) {
                DAOFactory.daoFactory().artigoDAO().create(artigo);
                Mensagem.info("Artigo cadastrada com sucesso!");
            } else {
                DAOFactory.daoFactory().artigoDAO().update(artigo);
                Mensagem.info("Artigo atualizada com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
        }

    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            Artigo artigo = tbArtigo.getSelectionModel().getSelectedItem();
            artigo.getClass();
            telaCadastro(null);

            txtCodigo.setText(String.valueOf(artigo.getId()));
            txtNome.setText(artigo.getNome());
            txtPreco.setText(artigo.getPreco().toString());
            txtDescricao.setText(artigo.getDescricao());

            lbTitulo.setText("Editar Artigo");
            menu.selectToggle(menu.getToggles().get(1));

            idArtigo = artigo.getId();
        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um artigo na tabela para edi????o!");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        try {
            Artigo artigo = tbArtigo.getSelectionModel().getSelectedItem();
            removeObject(artigo);
            tbArtigo.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione artigo na tabela para exclus??o!");
        }
    }

    private void removeObject(Artigo artigo) {
        Dialogo.Resposta response = Mensagem.confirmar("Excluir Artigo:: " + artigo.getNome() + " ?");
        if (response == Dialogo.Resposta.YES) {
            DAOFactory.daoFactory().artigoDAO().delete((int) artigo.getId());
            sincronizarBase();
            tabela();
        }
    }

    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaArtigo);
        colCodigo.setCellValueFactory((CellDataFeatures<Artigo, Long> obj)
                -> obj.getValue().idProperty().asObject());
        colNome.setCellValueFactory((CellDataFeatures<Artigo, String> obj)
                -> obj.getValue().nomeArtigoProperty());
        colDescricao.setCellValueFactory((CellDataFeatures<Artigo, String> obj)
                -> obj.getValue().descricaoProperty());
        colPreco.setCellValueFactory((CellDataFeatures<Artigo, BigDecimal> obj)
                -> obj.getValue().precoProperty());
        colActions.setCellFactory(actions);
        tbArtigo.setItems(data);
    }

    private void sincronizarBase() {
        listaArtigo = DAOFactory.daoFactory().artigoDAO().findAll();
    }

    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, btImprimir, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbArtigo.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idArtigo = 0;
    }

    private void limparCampos() {
        Campo.limpar(txtCodigo, txtNome, txtPreco);
        Campo.limpar(txtDescricao);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        decimal(txtPreco);
        txtPreco.setOnKeyReleased(key -> {
            if (key.getCode() == KeyCode.ENTER) {
                salvar(null);
            }
        });

        txtCodigo.setText(idArtigo + "");
        telaCadastro(null);
        Grupo.notEmpty(menu);
        sincronizarBase();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> 
            filtro(novo, FXCollections.observableArrayList(listaArtigo))
        );
        
        btAtualizar.setTooltip(new Tooltip("Atualizar registros"));
        btAdcionar.setTooltip(new Tooltip("Adicionar registros"));
        btApagar.setTooltip(new Tooltip("Apagar registros"));
        btImpr.setTooltip(new Tooltip("Imprimir"));
        btEdit.setTooltip(new Tooltip("Editar registros"));

        colPreco.setCellFactory(column -> new TableCell<Artigo, BigDecimal>() {
                @Override
                protected void updateItem(BigDecimal item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setStyle("-fx-alignment: center-right;-fx-padding: 0 5 0 0;");
                        setText(item + " $00");
                    }
                }
            }
        );
    }
    
    private void filtro(String valor, ObservableList<Artigo> listaArtigo) {
        FilteredList<Artigo> dadosFiltrados = new FilteredList<>(listaArtigo, artigo -> true);
        dadosFiltrados.setPredicate(artigo -> artigo.getNome().toLowerCase().startsWith(valor.toLowerCase()) ||
            artigo.getPreco().toString().toLowerCase().startsWith(valor.toLowerCase()));

        SortedList<Artigo> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbArtigo.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de artigos encontradas");
        tbArtigo.setItems(dadosOrdenados);
    }

    @FXML
    private void imprimir(ActionEvent event) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    Callback<TableColumn<Artigo, String>, TableCell<Artigo, String>> actions = (TableColumn<Artigo, String> param) -> {
        final TableCell<Artigo, String> cell;
        cell = new TableCell<Artigo, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(new Actions() {

                        @Override
                        protected void editButton(ActionEvent actionEvent) {
                            param.getColumns().size();
                        }

                        @Override
                        protected void deleteButton(ActionEvent actionEvent) {
                            throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
                        }
                    });
                }
            }
        };
        return cell;
    };
}
