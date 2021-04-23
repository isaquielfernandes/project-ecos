package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Artigo;
import cv.com.escola.model.entity.Cliente;
import cv.com.escola.model.entity.Item;
import cv.com.escola.model.entity.Usuario;
import cv.com.escola.model.entity.Venda;
import cv.com.escola.model.util.Actions;
import cv.com.escola.model.util.Animacao;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Combo;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Filtro;
import cv.com.escola.model.util.GerarCodigo;
import cv.com.escola.model.util.GraficoPie;
import cv.com.escola.model.util.Grupo;
import cv.com.escola.model.util.Mascara;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import cv.com.escola.model.util.Nota;
import cv.com.escola.model.util.Tempo;
import static cv.com.escola.model.util.ValidationFields.checkEmptyFields;
import static cv.com.escola.model.util.Meses.retornaNomeMes;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.table.TableRowExpanderColumn;

@Slf4j
public class RegistroVendaController extends AnchorPane implements Initializable, Itens {

    private static final String NOT_SUPPORTED_YET = "Not supported yet.";
    private List<Venda> listVendas;
    private List<Item> listaDeItem;
    private List<Artigo> listaProduto;
    private Item item;
    private List<Cliente> listaCliente;
    private int idVenda;
    private int idItens;
    private int idItemVenda = 0;
    private int quantity;
    private int cod;
    private static final int QUANTIDADE_PAGINA = 50;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane telaRelatorio;
    @FXML
    private Label lbTitulo;
    @FXML
    private DatePicker dataPickerAnoMes;
    @FXML
    private Button btRecibo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private ToggleGroup menu;
    @FXML
    private Button btEditar;
    @FXML
    private Button btExcluir;
    @FXML
    private Button btSalvar;
    @FXML
    private Label labelNumRecibo;
    @FXML
    private Label legenda;
    @FXML
    private TableView<Item> tbItens;
    @FXML
    private TableColumn<Item, Long> colCodigo;
    @FXML
    private TableColumn<Item, Artigo> colNomeArtigo;
    @FXML
    private TableColumn<Item, String> colDescricao;
    @FXML
    private TableColumn<Item, BigDecimal> colPrecoUnitario;
    @FXML
    private TableColumn<Item, Integer> colQuantidade;
    @FXML
    private TableColumn<Item, BigDecimal> colSubTotal;
    @FXML
    private TableColumn colActions;
    private TableColumn<Item, Button> colEdtitar;
    @FXML
    private TableView<Item> tbItems;
    @FXML
    private TableColumn<Item, Long> colItemsID;
    @FXML
    private TableColumn<Item, String> colItemsNomeArtigo;
    @FXML
    private TableColumn<Item, String> colItemsDescricao;
    @FXML
    private TableColumn<Item, Integer> colItemsQuantidade;
    @FXML
    private TableColumn<Item, BigDecimal> colItemsPrecoUnitario;
    @FXML
    private TableColumn<Item, BigDecimal> colItemsSubTotal;
    @FXML
    private DatePicker dataVenda;
    @FXML
    private DatePicker dateTextFieldMes;
    @FXML
    private DatePicker dtRelatorio;
    @FXML
    private ComboBox<Cliente> cbCliente;
    @FXML
    private ComboBox<String> cbFormaPag;
    @FXML
    private ComboBox<Artigo> cbArtigo;
    @FXML
    private TextField txtQuantia;
    @FXML
    private Button btnApagarItem;
    @FXML
    private TextField txtPrecoDeVenda;
    @FXML
    private TextField txtCustoItem;
    @FXML
    private TextField txtTotalDeItens;
    @FXML
    private TextField txtValorSubTotal;
    @FXML
    private TextField txtValorTotal;
    @FXML
    private TextField txtDesconto;
    @FXML
    private TextField txtUser;
    @FXML
    private RadioButton rbSituacao;
    @FXML
    private Button btnAdcionar;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableView<Venda> tbVendas;
    @FXML
    private TableColumn<Venda, Integer> colIdVenda;
    @FXML
    private TableColumn<Venda, String> colNumFatura;
    @FXML
    private TableColumn<Venda, LocalDate> colDataVenda;
    @FXML
    private TableColumn<Venda, String> colCliente;
    @FXML
    private TableColumn<Venda, String> colFormaPag;
    @FXML
    private TableColumn<Venda, Boolean> colSituacao;
    @FXML
    private TableColumn<Venda, String> colUser;
    @FXML
    private TableColumn<Venda, BigDecimal> colDesconto;
    @FXML
    private TableColumn<Venda, BigDecimal> colValorTotal;
    @FXML
    private TableColumn<Venda, BigDecimal> colSubTotalV;
    @FXML
    private GridPane telaCadastro;
    @FXML
    private Pagination pagination;
    private ListView<Cliente> listCliente;
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis categoryAxis;
    @FXML
    private NumberAxis numberAxis;
    @FXML
    private AreaChart<String, Number> areaChart;
    @FXML
    private CategoryAxis categoryAxisArea;
    @FXML
    private NumberAxis numberAxisArea;
    @FXML
    private StackedBarChart<String, BigDecimal> stackedBarChart;
    @FXML
    private CategoryAxis categoryAxisStacked;
    @FXML
    private NumberAxis numberAxisStacked;
    @FXML
    private StackedAreaChart stackedAreaChart;
    @FXML
    private NumberAxis numberAxisStackArea;
    @FXML
    private LineChart lineChartDia;
    @FXML
    private CategoryAxis categoryAxisDia;
    @FXML
    private HBox hBox;
    @FXML
    private PieChart pieChart;
    @FXML
    private ListView<Artigo> listViewProduto;
    @FXML
    private TextField pesquisarProduto;

    private ObservableList<String> observableListMeses = FXCollections.observableArrayList();
    private ObservableList<Item> dadoItemVenda = FXCollections.observableArrayList();
    private String ano;
    private double initX;
    private double initY;

    // Obtém an array com nomes dos meses em Inglês.
    String[] arrayMeses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabela();
        viewAll();
        tabelaItemsVenda();
        item = new Item();

        Tempo.blockDataAnterior(LocalDate.now().minusYears(7), dtRelatorio);
        dtRelatorio.setValue(LocalDate.now());
        ano = String.valueOf(dtRelatorio.getValue().getYear());
        sincronizarBase();
        gerarNumRecibo();
        combos();
        Mascara.numerico(txtQuantia);
        Mascara.decimal(txtDesconto);
        Animacao.fade(tbVendas);
        Animacao.fade(pagination);

        dataVenda.setValue(LocalDate.now());

        txtUser.setText(LoginController.usuarioLogado.getNome());
        dataPickerAnoMes.valueProperty().addListener((observable, oldValue, newValue) -> {
            atualizarGrade(0);
            filtro(newValue, FXCollections.observableArrayList(listVendas));
        });

        txtPesquisar.textProperty().addListener((obs, old, novo)
                -> filtrosEmVendas(novo, FXCollections.observableArrayList(listVendas)));

        telaCadastro(null);
        Grupo.notEmpty(menu);
        txtDesconto.setText("0");
        rbSituacao.setText("SIM");
        rbSituacao.setOnMouseClicked(event -> {
            if (rbSituacao.isSelected()) {
                rbSituacao.setText("SIM");
            } else {
                rbSituacao.setText("NÃO");
            }
        });

        cbArtigo.setOnMouseClicked(event -> {
            ObservableList dadoArtigo = FXCollections.observableArrayList(DAOFactory.daoFactury().artigoDAO().findAll());
            cbArtigo.setItems(dadoArtigo);
        });
        // Limpa os detalhes do artigo.
        showArtigoSelected(null);
        // Detecta mudanças de seleção e mostra os detalhes do artigo quando houver mudança.
        cbArtigo.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showArtigoSelected(newValue));

        cbCliente.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showClienteSelected(newValue));

        cbCliente.setOnMouseClicked(event -> {
            ObservableList dadoCliente = FXCollections.observableArrayList(DAOFactory.daoFactury().clienteDAO().findAll());
            cbCliente.setItems(dadoCliente);
        });

        listViewProduto.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listViewProdutoSelected(null);
        listViewProduto.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> listViewProdutoSelected(newValue));

        pesquisarProduto.textProperty().addListener((obs, old, novo)
                -> filtros(novo, FXCollections.observableArrayList(listaProduto)));

        //TableView Editavel
        tbItens.setEditable(true);
        colQuantidade.setEditable(true);
        colNomeArtigo.setEditable(true);
        colNomeArtigo.setEditable(true);
        colNomeArtigo.setCellFactory(
                ComboBoxTableCell.forTableColumn());
        colNomeArtigo.setOnEditCommit((TableColumn.CellEditEvent<Item, Artigo> event)
                -> event.getTableView().getItems().get(event.getTablePosition().getRow()).setArtigo(event.getNewValue())
        );

        colQuantidade.setCellFactory(
                TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
                    @Override
                    public String toString(Integer object) {
                        return object.toString();
                    }

                    @Override
                    public Integer fromString(String string) {
                        return Integer.valueOf(string);
                    }
                }));

        colQuantidade.setOnEditCommit((TableColumn.CellEditEvent<Item, Integer> event) -> 
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setQuantidade(event.getNewValue())
        );

        inserir(txtQuantia);
        realizarVenda(txtDesconto);

        // Converte o array em uma lista e adiciona em nossa ObservableList de meses.
        observableListMeses.addAll(Arrays.asList(arrayMeses));
        // Associa os nomes de mês como categorias para o eixo horizontal.
        categoryAxis.setCategories(observableListMeses);
        // Associa os nomes de mês como categorias para o eixo horizontal.
        categoryAxisArea.setCategories(observableListMeses);
        // Associa os nomes de mês como categorias para o eixo horizontal.
        categoryAxisStacked.setCategories(observableListMeses);
        //chamado a funcao barChart
        barChart();

        //Chamando a funcao areaChart
        areaChart();
        // chamado a funçao lineChart
        dateTextFieldMes.setValue(LocalDate.now());
        lineChart(dateTextFieldMes.getValue());

        dateTextFieldMes.valueProperty().addListener((observable, oldValue, newValue)
                -> lineChart(newValue));

        dtRelatorio.valueProperty().addListener((observable, oldValue, newValue)
                -> createPieChart());

        createPieChart();
        stackedAreaChart();
        //ChangeListener on the tbvendas

        if (tbVendas.isHover()) {
            Venda venda = tbVendas.getSelectionModel().getSelectedItem();
            if (venda != null) {
                tbVendas.setTooltip(new Tooltip(venda.getCliente().getNomeCliente()));
            }
        }

        tbVendas.setOnMouseClicked(event -> {
            if (tbVendas.getSelectionModel().getSelectedItem() != null && event.getClickCount() == 2) {
                reciboPrint(event);
            }
        });

        colPrecoUnitario.setCellFactory(column -> new TableCell<Item, BigDecimal>() {
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
        });

        colSubTotal.setCellFactory(column -> new TableCell<Item, BigDecimal>() {
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
        });

        colQuantidade.setCellFactory(column -> new TableCell<Item, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setStyle("-fx-alignment: center-right;");
                    setText(item + "");
                }
            }
        });

        colSituacao.setCellFactory(column -> new TableCell<Venda, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    if (item == true) {
                        setText("SIM");
                    } else {
                        setText("NAO");
                    }
                }
            }
        });

        listViewProduto.setCellFactory((ListView<Artigo> param) -> new ListCell() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                setText(null);
                setGraphic(null);
                super.updateItem(item, empty);
                Artigo produts = (Artigo) item;
                if (produts != null) {
                    setText("N: " + produts.getNomeArtigo() + "\n" + "P: " + produts.getPreco() + " $00");
                    setMaxSize(48, 48);
                }
            }
        });

        pagination.setPageFactory((Integer pagina) -> {
            atualizarGrade(pagina);
            return tbVendas;
        });

    }

    @SuppressWarnings("LeakingThisInConstructor")
    public RegistroVendaController() {
        try {
            GenericFXXMLLoader.loadFXML(this, "registroVenda");
        } catch (IOException ex) {
            Logger.getLogger(RegistroVendaController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.alerta("Erro ao carregar tela registro de venda");
        }
    }

    //paginação da tabela venda
    private void atualizarGrade(int pagina) {
        int totalDeVendas;totalDeVendas = DAOFactory.daoFactury().orderDAO().count();
        pagination.setPageCount(totalDeVendas/ QUANTIDADE_PAGINA);
        ObservableList observableListDeVenda = DAOFactory.daoFactury().orderDAO().listar(QUANTIDADE_PAGINA, pagina);
        tbVendas.setItems(observableListDeVenda);
    }

    //Grafico de Bara (BarChart)
    protected void barChart() {
        Map<Integer, ArrayList<Number>> dados = DAOFactory.daoFactury().orderDAO().listarQuantidadeVendaPorMes();
        dados.entrySet().stream().map(dadosItem -> {
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName(dadosItem.getKey().toString());
            for (int i = 0; i < dadosItem.getValue().size(); i = i + 2) {
                String mes;
                Integer quantidade;

                mes = retornaNomeMes((int) dadosItem.getValue().get(i));
                quantidade = (Integer) dadosItem.getValue().get(i + 1);

                series.getData().add(new XYChart.Data<>(mes, quantidade));
            }
            return series;
        }).forEachOrdered(series -> barChart.getData().add(series));
    }

    //Grafico de Bara (StackedBarChart)
    protected void stackedBarChart() {
        Map<Integer, ArrayList<Number>> dados = DAOFactory.daoFactury().orderDAO().listarValorTotalVendaPorMes();
        dados.entrySet().stream().map(dadosItem -> {
            XYChart.Series<String, BigDecimal> series = new XYChart.Series<>();
            series.setName(dadosItem.getKey().toString());
            for (int i = 0; i < dadosItem.getValue().size(); i = i + 2) {
                String mes;
                BigDecimal valor;

                mes = retornaNomeMes((int) dadosItem.getValue().get(i));
                valor = (BigDecimal) dadosItem.getValue().get(i + 1);

                series.getData().add(new XYChart.Data<>(mes, valor));
            }
            return series;
        }).forEachOrdered(series -> stackedBarChart.getData().add(series));
    }

    //Grafico de area (AreaChart)
    protected void areaChart() {
        //AreaChart pra vendas por mes
        Map<Integer, ArrayList<Number>> area = DAOFactory.daoFactury().orderDAO().listarQuantidadeVendaPorMes();
        area.entrySet().stream().map(dadosItem -> {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(dadosItem.getKey().toString());
            for (int i = 0; i < dadosItem.getValue().size(); i = i + 2) {
                String mes;

                mes = retornaNomeMes((int) dadosItem.getValue().get(i));
                Number quantidade = dadosItem.getValue().get(i + 1);

                series.getData().add(new XYChart.Data<>(mes, quantidade));
            }
            return series;
        }).forEachOrdered(series -> areaChart.getData().add(series));
    }

    //Creando PieChart
    protected void createPieChart() {
        ano = String.valueOf(dtRelatorio.getValue().getYear());
        Map<Integer, ArrayList<Number>> dados = DAOFactory.daoFactury().orderDAO().listarValorTotalVendaPorMes(ano);
        dados.entrySet().stream().map(dadosItem -> {
            ObservableList<PieChart.Data> pieChartObser = FXCollections.observableArrayList();
            for (int i = 0; i < dadosItem.getValue().size(); i = i + 2) {
                String mes = retornaNomeMes((int) dadosItem.getValue().get(i));
                pieChartObser.add(new PieChart.Data(mes, dadosItem.getValue().get(i + 1).doubleValue()));
            }
            return pieChartObser;
        }).forEachOrdered(pieChartObser -> pieChart.setData(pieChartObser));
        double value = DAOFactory.daoFactury().orderDAO().totalAnual(ano).doubleValue();
        GraficoPie.info(pieChart, value);
    }

    //Grafico de Bara (StackedAreaChart)
    protected void stackedAreaChart() {
        Map<Integer, ArrayList<Number>> dados = DAOFactory.daoFactury().orderDAO().listarValorTotalVendaPorMes();
        dados.entrySet().stream().map(dadosItem -> {
            XYChart.Series<Number, BigDecimal> series = new XYChart.Series<>();
            series.setName(dadosItem.getKey().toString());
            for (int i = 0; i < dadosItem.getValue().size(); i = i + 2) {
                Number mes;
                BigDecimal valor;
                mes = dadosItem.getValue().get(i);
                valor = (BigDecimal) dadosItem.getValue().get(i + 1);
                series.getData().add(new XYChart.Data<>(mes, valor));
            }
            return series;
        }).forEachOrdered(series -> stackedAreaChart.getData().add(series));
    }

    //Grafico de linha (LineChart)
    protected void lineChart(LocalDate dataAgora) {
        String mes = String.valueOf(dataAgora.getMonthValue());
        String anoAgora = String.valueOf(dataAgora.getYear());
        lineChartDia.getData().clear();

        Map<Integer, ArrayList<Number>> dados = DAOFactory.daoFactury().orderDAO().listarQuantidadeVendaPorDia(mes, anoAgora);

        dados.entrySet().stream().map(dadosItem -> {
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName(dadosItem.getKey().toString());
            for (int i = 0; i < dadosItem.getValue().size(); i = i + 2) {
                String dia;
                Integer quantidade;

                dia = dadosItem.getValue().get(i).toString();
                quantidade = (Integer) dadosItem.getValue().get(i + 1);

                series.getData().add(new XYChart.Data<>(dia, quantidade));
            }
            return series;
        }).forEachOrdered(series -> lineChartDia.getData().add(series));
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, btRecibo, dataPickerAnoMes, telaCadastro, telaEdicao, telaRelatorio, txtPesquisar);

        legenda.setText(msg);
        tbVendas.getSelectionModel().clearSelection();
        tbItens.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idItens = 0;
        idVenda = 0;
    }

    /**
     * Preencher combos tela
     */
    private void combos() {
        cbArtigo.setPromptText("- Selecione um Artigo -");
        cbCliente.setPromptText("- Selecione um Cliente -");
        listaCliente = DAOFactory.daoFactury().clienteDAO().findAll();
        ObservableList dadoArtigo = FXCollections.observableArrayList(DAOFactory.daoFactury().artigoDAO().findAll());
        ObservableList dadoCliente = FXCollections.observableArrayList(listaCliente);
        cbArtigo.setItems(dadoArtigo);
        cbCliente.setItems(dadoCliente);
        listViewProduto.setItems(dadoArtigo);

        Combo.popular(cbFormaPag, "Dinheiro", "Debito", "Credito", "Parcelado", "Cheque", "Outro");
    }

    public void selecionarItemTableViewVendas(Venda venda) {
        if (venda != null) {
            tbItems.setItems((ObservableList<Item>) venda.getItens());
        }
    }

    public void selecionarItemTableViewItens(Item iten) {
        if (item != null) {
            txtQuantia.setText(iten.getQuantidade().toString());
            cbArtigo.setValue(iten.getArtigo());
        } else {
            txtQuantia.setText(null);
            cbArtigo.setValue(null);
        }
    }

    // Setando dados do combobox no caixa de texto
    public void showArtigoSelected(Artigo artigo) {
        if (artigo != null) {
            if (txtQuantia.getText().trim().isEmpty()) {
                txtPrecoDeVenda.setText(artigo.getPreco().toString());
            } else {
                txtCustoItem.setText(String.valueOf(artigo.getPreco()));
                txtPrecoDeVenda.setText(artigo.getPreco().toString());
            }
        } else {
            txtCustoItem.setText("0.00");
            txtPrecoDeVenda.clear();
        }
    }

    // Setando dados do combobox no caixa de texto
    public void showClienteSelected(Cliente cliente) {
        if (cliente != null) {
            cbCliente.setValue(cliente);
        } else {
            cbCliente.setValue(null);
        }
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listVendas = DAOFactory.daoFactury().orderDAO().findAll();
        listaDeItem = DAOFactory.daoFactury().itemDAO().findAll();
        listaCliente = DAOFactory.daoFactury().clienteDAO().findAll();
        listaProduto = DAOFactory.daoFactury().artigoDAO().findAll();
        cod = DAOFactory.daoFactury().orderDAO().ultimoRegisto(LocalDate.now().getYear());
    }

    // Função para inserir item na tabela quando click em enter
    public void inserir(TextField quantia) {
        quantia.setOnKeyReleased((KeyEvent key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                onMouseClickedAdcionarItem(null);
            } else {
                onKeyReleasedQuantia(null);
            }
        });
    }

    //Função para realizar venda ao clicar em ENTER
    private void realizarVenda(TextField venda) {
        venda.setOnKeyReleased((KeyEvent key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                salvar(null);
            } else {
                onKeyReleasedDescontos(key);
            }
        });
    }

    //Função para verificar se os campo esta vazio
    public boolean isNotNull() {
        boolean isNotNull = false;
        if (txtPrecoDeVenda.getText().trim().isEmpty() || txtQuantia.getText().trim().matches("")) {
            Nota.erro("Por favor, Selecione um artigo...");
            return isNotNull;
        } else {
            isNotNull = true;
        }
        return isNotNull;
    }

    // setando dados de item na tableView
    public void viewAll() {
        colCodigo.setCellValueFactory((CellDataFeatures<Item, Long> p)
                -> new ReadOnlyObjectWrapper(p.getValue().getArtigo().idArtigoProperty().get()));
        colNomeArtigo.setCellValueFactory((CellDataFeatures<Item, Artigo> p)
                -> new ReadOnlyObjectWrapper(p.getValue().getArtigo().nomeArtigoProperty().get()));
        colDescricao.setCellValueFactory((CellDataFeatures<Item, String> p)
                -> new ReadOnlyObjectWrapper(p.getValue().getArtigo().descricaoProperty().get()));
        colQuantidade.setCellValueFactory((CellDataFeatures<Item, Integer> p)
                -> new ReadOnlyObjectWrapper(p.getValue().getQuantidade()));
        colPrecoUnitario.setCellValueFactory((CellDataFeatures<Item, BigDecimal> p)
                -> new ReadOnlyObjectWrapper(p.getValue().getArtigo().precoProperty().get()));
        colSubTotal.setCellValueFactory((CellDataFeatures<Item, BigDecimal> p)
                -> new ReadOnlyObjectWrapper(p.getValue().getValorUnitario()));
        colActions.setCellFactory(actions);
    }

    // somado o preço total por item
    private void sumTotalCost() {
        tbItens.getSelectionModel().selectFirst();
        float sum = 0;
        int items = tbItens.getItems().size();

        for (int i = 0; i < items; i++) {
            tbItens.getSelectionModel().select(i);
            String selectedItem = tbItens.getSelectionModel().getSelectedItem().getValorUnitario().toString();
            float newFloat = Float.parseFloat(selectedItem);
            sum = sum + newFloat;
        }
        String totalCost = String.valueOf(sum);
        txtValorSubTotal.setText(totalCost);
        txtValorTotal.setText(totalCost);
        String totalItem = String.valueOf(items);
        txtTotalDeItens.setText(totalItem);
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        colIdVenda.setCellValueFactory(new PropertyValueFactory<>("idVenda"));
        colNumFatura.setCellValueFactory(new PropertyValueFactory<>("numFatura"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colUser.setCellValueFactory((TableColumn.CellDataFeatures<Venda, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getUsuario().getNome()));
        colFormaPag.setCellValueFactory(new PropertyValueFactory<>("meioDePagamento"));
        colDataVenda.setCellValueFactory(new PropertyValueFactory<>("data"));
        colSituacao.setCellValueFactory((CellDataFeatures<Venda, Boolean> p) -> new ReadOnlyObjectWrapper(p.getValue().isPago()));
        colSubTotalV.setCellValueFactory((CellDataFeatures<Venda, BigDecimal> p) -> new ReadOnlyObjectWrapper(p.getValue().getValor()));
        colValorTotal.setCellValueFactory((CellDataFeatures<Venda, BigDecimal> p) -> new ReadOnlyObjectWrapper(p.getValue().getValorTotal()));
        colDesconto.setCellValueFactory((CellDataFeatures<Venda, BigDecimal> p) -> new ReadOnlyObjectWrapper(p.getValue().getDesconto()));

    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabelaItemsVenda() {
        colItemsID.setCellValueFactory((CellDataFeatures<Item, Long> p)
                -> new ReadOnlyObjectWrapper(p.getValue().getArtigo().idArtigoProperty().get()));
        colItemsNomeArtigo.setCellValueFactory((CellDataFeatures<Item, String> p)
                -> new ReadOnlyObjectWrapper(p.getValue().getArtigo().nomeArtigoProperty().get()));
        colItemsDescricao.setCellValueFactory((CellDataFeatures<Item, String> p)
                -> new ReadOnlyObjectWrapper(p.getValue().getArtigo().descricaoProperty().get()));
        colItemsPrecoUnitario.setCellValueFactory((CellDataFeatures<Item, BigDecimal> p)
                -> new ReadOnlyObjectWrapper(p.getValue().getArtigo().precoProperty().get()));
        colItemsQuantidade.setCellValueFactory((CellDataFeatures<Item, Integer> p)
                -> new ReadOnlyObjectWrapper(p.getValue().getQuantidade()));
        colItemsSubTotal.setCellValueFactory((CellDataFeatures<Item, BigDecimal> p)
                -> new ReadOnlyObjectWrapper(p.getValue().getValorUnitario()));
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limparCampos() {
        Campo.limpar(txtQuantia, txtDesconto, txtValorSubTotal, txtValorTotal, txtTotalDeItens, txtCustoItem);
        tbItens.getItems().clear();
        tbItems.getItems().clear();
    }

    // Gerar e setando num de recibo na label
    private void gerarNumRecibo() {
        GerarCodigo.gerar(cod);
        String numRecibo = GerarCodigo.serie();
        labelNumRecibo.setText(numRecibo);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtrosEmVendas(String valor, ObservableList<Venda> listVendas) {
        FilteredList<Venda> dadosFiltrados = new FilteredList<>(listVendas, venda -> true);
        dadosFiltrados.setPredicate(venda -> venda.getCliente().getNomeCliente().toLowerCase().startsWith(valor.toLowerCase())
                || venda.getNumFatura().toLowerCase().startsWith(valor.toLowerCase())
                || venda.getUsuario().getNome().toLowerCase().startsWith(valor.toLowerCase())
                || venda.getMeioDePagamento().toLowerCase().startsWith(valor.toLowerCase())
                || venda.getData().toString().toLowerCase().startsWith(valor.toLowerCase())
        );

        SortedList<Venda> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbVendas.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de vendas encontradas");

        tbVendas.setItems(dadosOrdenados);
    }

    //Filtrar dados de venda com por data
    private void filtro(LocalDate valor, ObservableList<Venda> listVendas) {
        FilteredList<Venda> dadosFiltrados = new FilteredList<>(listVendas, venda -> true);
        dadosFiltrados.setPredicate(venda -> venda.getData().toString().toLowerCase().startsWith(valor.toString().toLowerCase()));
        SortedList<Venda> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbVendas.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de vendas encontradas");

        tbVendas.setItems(dadosOrdenados);
    }

    @FXML
    private void reciboPrint(MouseEvent event) {
        try {
            Venda vendas = tbVendas.getSelectionModel().getSelectedItem();
            Dialogo.Resposta response = Mensagem.confirmar("Imprimir Recibo/Fatura n°:: " + vendas.getNumFatura() + "?");
            if (response == Dialogo.Resposta.YES) {
                DAOFactory.daoFactury().orderDAO().reportReciboFatura(vendas.getIdVenda());
            }
            tbVendas.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Nota.alerta("Selecione uma venda na tabela para fazer a imprisão do recibo/fatura!");
        }
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Vendas", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampos();
        gerarNumRecibo();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("Vendas", "Quantidade de vendas encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar, dataPickerAnoMes, btRecibo);
        tabela();
        atualizarGrade(0);
        gerarNumRecibo();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Excluir Venda", "Quantidade de vendas encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar, dataPickerAnoMes, btRecibo);
        tabela();
        gerarNumRecibo();
    }

    @FXML
    private void telaRelatorio(ActionEvent event) {
        configTela("Relatorio de Vendas", "", 3);
        Modulo.visualizacao(true, telaRelatorio);
        sincronizarBase();
        //chamando a funcao barChart
        barChart.getData().clear();
        barChart();
        //Chamando a funcao areaChart
        areaChart.getData().clear();
        areaChart();
        // Chamando a funcao stackedBarChar
        stackedBarChart.getData().clear();
        stackedBarChart();

        pieChart.getData().clear();
        createPieChart();

        stackedAreaChart.getData().clear();
        stackedAreaChart();
    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            Venda dadosVenda = tbVendas.getSelectionModel().getSelectedItem();
            dadosVenda.getClass();
            telaCadastro(null);

            dataVenda.setValue(dadosVenda.getData());
            txtValorSubTotal.setText(dadosVenda.getValor().toString());
            txtValorTotal.setText(dadosVenda.getValorTotal().toString());
            txtDesconto.setText(dadosVenda.getDesconto().toString());
            txtUser.setText(dadosVenda.getUsuario().getNome());
            labelNumRecibo.setText(dadosVenda.getNumFatura());
            cbFormaPag.setValue(dadosVenda.getMeioDePagamento());
            cbCliente.setValue(dadosVenda.getCliente());

            if (dadosVenda.isPago() != true) {
                rbSituacao.setSelected(false);
                rbSituacao.setText("NÃO");
            } else {
                rbSituacao.setSelected(true);
                rbSituacao.setText("SIM");
            }

            tbItens.setItems((ObservableList<Item>) dadosVenda.getItens());

            lbTitulo.setText("Vendas Realizadas");
            menu.selectToggle(menu.getToggles().get(1));

            idVenda = dadosVenda.getIdVenda();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione uma venda na tabela para edição!");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        try {
            Venda vendas = tbVendas.getSelectionModel().getSelectedItem();
            Dialogo.Resposta response = Mensagem.confirmar("Excluir venda " + vendas.getNumFatura() + "?");

            if (response == Dialogo.Resposta.YES) {
                DAOFactory.daoFactury().itemDAO().delete(vendas.getIdVenda());
                DAOFactory.daoFactury().orderDAO().delete(vendas.getIdVenda());
                sincronizarBase();
                tabela();
                gerarNumRecibo();
                tbItems.getItems().clear();

            }

            tbVendas.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione venda na tabela para exclusão!");
        }
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean vazio = Campo.noEmpty(txtUser);
        boolean emptyFields = checkEmptyFields(cbCliente, cbFormaPag);
        try {

            Usuario user = new Usuario();
            user.setId(LoginController.usuarioLogado.getId());
            String numRecibo = labelNumRecibo.getText();
            Cliente cliente = cbCliente.getValue();
            String formaDePagamento = cbFormaPag.getValue();
            boolean pago = rbSituacao.isSelected();
            LocalDate dataVendas = dataVenda.getValue();
            BigDecimal valorSubTotal = new BigDecimal(txtValorSubTotal.getText().trim().isEmpty() ? "0.00" : txtValorSubTotal.getText());
            BigDecimal descontos = new BigDecimal(txtDesconto.getText().trim().isEmpty() ? "0.00" : txtDesconto.getText());
            BigDecimal total = new BigDecimal(txtValorTotal.getText().trim().isEmpty() ? valorSubTotal.toString() : txtValorTotal.getText());

            if (vazio) {
                Nota.alerta("Preencher campos vazios!");
            } else {
                if (emptyFields && validarDados()) {
                    Venda vendas = new Venda(idVenda, dataVendas, valorSubTotal, descontos, pago, cliente, user, formaDePagamento, numRecibo, total);
                    if (idVenda == 0) {
                        DAOFactory.daoFactury().orderDAO().create(vendas);

                        Item items = new Item();
                        int indexs = tbItens.getItems().size();
                        for (int i = 0; i < indexs; i++) {
                            tbItens.getSelectionModel().select(i);
                            items.setIdItem((long) idItemVenda);
                            items.setQuantidade(quantity = tbItens.getSelectionModel().getSelectedItem().getQuantidade());
                            items.setValorUnitario(tbItens.getSelectionModel().getSelectedItem().getValorUnitario());
                            items.setArtigo(tbItens.getSelectionModel().getSelectedItem().getArtigo());
                            items.setVenda(DAOFactory.daoFactury().orderDAO().buscarUltimaVenda());
                            DAOFactory.daoFactury().itemDAO().create(items);
                            Mensagem.info("Registro de pagamento feito com sucesso!");
                        }
                    } else {
                        DAOFactory.daoFactury().orderDAO().update(vendas);
                        Mensagem.info("Registro de pagamento foi atulizado com sucesso!");
                    }
                    telaCadastro(null);
                    sincronizarBase();
                    gerarNumRecibo();
                    DAOFactory.daoFactury().orderDAO().reportReciboFatura(
                            DAOFactory.daoFactury().orderDAO().buscarUltimaVenda().getIdVenda());
                }
            }
        } catch (NumberFormatException ex) {
            Nota.erro("Erro:\nErro de formatação de campo numerico\n");
        }
    }

    @FXML
    private void onKeyReleasedQuantia(KeyEvent event) {
        if (!txtQuantia.getText().isEmpty()) {
            quantity = Integer.parseInt(txtQuantia.getText().trim().isEmpty() ? "0" : txtQuantia.getText());
            float precoVenda = Float.parseFloat(txtPrecoDeVenda.getText().trim().isEmpty() ? "0.00" : txtPrecoDeVenda.getText());
            float netPreco = precoVenda * quantity;
            DecimalFormat myFormatter = new DecimalFormat("###,###.##");
            myFormatter.format(netPreco);
            txtCustoItem.setText(String.valueOf(netPreco));
        } else {
            txtCustoItem.setText("0.00");
        }
    }

    @FXML
    private void onMouseClickedRemoverItem(MouseEvent event) {
        removerItem(item, tbItens);
    }

    // funcao para remover item da tabela items
    public void removerItem(Item itens, TableView<Item> tb) {
        try {
            Dialogo.Resposta response = Mensagem.confirmar("Excluir item: " + itens.getArtigo().getNomeArtigo() + "?");
            if (response == Dialogo.Resposta.YES) {
                tb.getItems().remove(tb.getSelectionModel().getSelectedIndex());
                tb.getSelectionModel().clearSelection();
                viewAll();
                sumTotalCost();
            }
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione item na tabela para exclusão!\n");
        }
    }

    // editar item na tabela
    public void editarItem() {
        Item itemVenda = tbItens.getSelectionModel().getSelectedItem();
        itemVenda.getClass();

        txtQuantia.setText(itemVenda.getQuantidade().toString());
        cbArtigo.setValue(itemVenda.getArtigo());
    }

    @FXML
    private void onKeyReleasedDescontos(KeyEvent event) {
        if (!txtDesconto.getText().isEmpty()) {
            float fTotal = Float.parseFloat(txtValorSubTotal.getText().trim().isEmpty() ? "0" : txtValorSubTotal.getText());

            float porCentoDesconto = Float.parseFloat(txtDesconto.getText().trim().isEmpty() ? "0" : txtDesconto.getText());
            float valorComDesconto = fTotal - fTotal * porCentoDesconto / 100;

            txtValorTotal.setText(String.valueOf(valorComDesconto));
        } else {
            txtValorTotal.setText(txtValorSubTotal.getText());
        }
    }

    @FXML
    private void onMouseClickedAdcionarItem(MouseEvent event) {
        adcionarItemAoCarinho();
    }

    @FXML
    private void telaAddVenda(MouseEvent event) {
        telaCadastro(null);
    }

    @FXML
    private void telaDetalheVenda(MouseEvent event) {
    }

    @FXML
    private void telaImpresao(MouseEvent event) {
        reciboPrint(event);
    }

    @FXML
    private void adcionarClientes(ActionEvent event) {
        try {
            Cliente cliente = new Cliente();
            boolean buttonConfirmarClicked = showCadastrosClientesDialog(cliente);
            if (buttonConfirmarClicked) {
                DAOFactory.daoFactury().clienteDAO().create(cliente);
                sincronizarBase();
            }
        } catch (IOException ex) {
            Logger.getLogger(RegistroVendaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void pesquisarCliente(ActionEvent event) {
        clienteSelecionadoDialog();
    }

    public void adcionarItemAoCarinho() {
        try {
            if (isNotNull()) {
                Artigo artigoList = cbArtigo.getValue();
                Integer quantia = Integer.parseInt(txtQuantia.getText());
                BigDecimal valor = new BigDecimal(txtCustoItem.getText());
                Venda vendaId = new Venda();
                vendaId.getIdVenda();

                item = new Item(idItemVenda, artigoList, quantia, valor, vendaId);
                int items = tbItens.getItems().size();

                boolean findByArtigo = tbItens.getItems().stream()
                        .filter(p -> p.getArtigo().toString().equalsIgnoreCase(artigoList.toString()))
                        .findFirst()
                        .isPresent();
                if (!findByArtigo) {
                    dadoItemVenda.add(item);
                    tbItens.setItems(dadoItemVenda);
                } else {
                    for (int i = 0; i < items; i++) {
                        tbItens.getSelectionModel().select(i);
                        String selectedItem = tbItens.getSelectionModel().getSelectedItem().getArtigo().toString();
                        if (item.getArtigo().toString().equals(selectedItem)) {
                            dadoItemVenda.set(i, item);
                            tbItens.setItems(dadoItemVenda);
                            break;
                        }

                    }

                }

                viewAll();
                sumTotalCost();
                txtQuantia.clear();
                tbItens.refresh();
                listViewProduto.getSelectionModel().clearSelection();
                cbArtigo.getSelectionModel().clearSelection();
                tbItens.getSelectionModel().clearSelection();

            }
        } catch (NullPointerException ex) {
            //ex.printStackTrace();
        }
    }

    private boolean validarDados() {
        String errorMessage = "";
        if (tbItens.getItems().isEmpty()) {
            errorMessage += "Adicione Itens de Venda na tabela!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Nota.erro("Campos inválidos, por favor, corrija... " + errorMessage);
            return false;
        }
    }

    public boolean showCadastrosClientesDialog(Cliente cliente) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClienteDialogController.class.getResource("/cv/com/escola/view/clienteDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Clientes");
        //Especifica a modalidade para esta fase . Isso deve ser feito antes de fazer o estágio visível. A modalidade pode ser: Modality.NONE , Modality.WINDOW_MODAL , ou Modality.APPLICATION_MODAL 
        dialogStage.initModality(Modality.WINDOW_MODAL);//WINDOW_MODAL (possibilita minimizar)
        dialogStage.initStyle(StageStyle.UNDECORATED);
        //Especifica a janela do proprietário para esta página, ou null para um nível superior.
        //dialogStage.initOwner(null); //null deixa a Tela Principal livre para ser movida
        dialogStage.initOwner(this.tbItens.getScene().getWindow()); //deixa a tela de Preenchimento dos dados como prioritária
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        //center stage on screen

        //when mouse button is pressed, save the initial position of screen
        page.setOnMousePressed((MouseEvent me) -> {
            initX = me.getScreenX() - dialogStage.getX();
            initY = me.getScreenY() - dialogStage.getY();
        });

        //when screen is dragged, translate it accordingly
        page.setOnMouseDragged((MouseEvent me) -> {
            dialogStage.setX(me.getScreenX() - initX);
            dialogStage.setY(me.getScreenY() - initY);
        });

        // Setando o cliente no Controller.
        ClienteDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCliente(cliente);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }

    private GridPane createEditor(TableRowExpanderColumn.TableRowDataFeatures<Item> p) {
        p.setExpanded(false);

        ObservableList dadoArtigo = FXCollections.observableArrayList(DAOFactory.daoFactury().artigoDAO().findAll());

        GridPane editor = new GridPane();
        editor.setPadding(new Insets(10));
        editor.setHgap(10);
        editor.setVgap(5);

        item = p.getValue();

        TextField quantiadade = new TextField();
        quantiadade.setPrefSize(120, 30);

        ComboBox<Artigo> artigo = new ComboBox();
        artigo.setItems(dadoArtigo);

        artigo.setPrefSize(200, 30);
        artigo.setValue(item.getArtigo());

        quantiadade.setOnKeyReleased((event) -> {
            txtQuantia.setText(quantiadade.getText());
            onKeyReleasedQuantia(event);
        });

        editor.addRow(0, new Label("Quantia"), quantiadade);
        editor.addRow(1, new Label("Artigo"), artigo);

        Button adcionar = new Button("Adcionar");
        adcionar.setOnAction(event -> {
            item.setQuantidade(quantity);
            item.setArtigo(artigo.getValue());
            onMouseClickedAdcionarItem(null);
            sumTotalCost();
            quantiadade.clear();
            artigo.setValue(null);
            txtQuantia.clear();
            p.toggleExpanded();

        });
        Button cancelar = new Button("Cancelar");
        cancelar.setOnAction(event -> p.toggleExpanded());

        editor.addRow(2, adcionar, cancelar);
        return editor;
    }

    public void clienteSelecionadoDialog() {
        Group rootGroup = new Group();
        Scene scene = new Scene(rootGroup, 520, 420, Color.GOLD);

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage stage = new Stage();
        stage.setTitle("Pesquisar Cliente");
        //Especifica a modalidade para esta fase . Isso deve ser feito antes de fazer o estágio visível. A modalidade pode ser: Modality.NONE , Modality.WINDOW_MODAL , ou Modality.APPLICATION_MODAL
        stage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL (possibilita minimizar)
        stage.setResizable(true);
        stage.getIcons().add(new Image(RegistrarController.class.getResourceAsStream("/cv/com/escola/view/img/avater.jpg")));
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(scene);

        listCliente = new ListView();
        ObservableList dadoCliente = FXCollections.observableArrayList(DAOFactory.daoFactury().clienteDAO().findAll());
        listCliente.setItems(dadoCliente);

        listCliente.setCellFactory((ListView<Cliente> param) -> new ListCell() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                setText(null);
                setGraphic(null);
                super.updateItem(item, empty);
                Cliente cliente = ((Cliente) item);
                if (cliente != null) {
                    setText(cliente.getNomeCliente() + "\n" + cliente.getNif());
                }
            }
        }
        );

        listCliente.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> clienteSelectedListView(newValue));

        listCliente.setOnMouseClicked(event -> stage.close());

        //when mouse button is pressed, save the initial position of screen
        rootGroup.setOnMousePressed((MouseEvent me) -> {
            initX = me.getScreenX() - stage.getX();
            initY = me.getScreenY() - stage.getY();
        });

        //when screen is dragged, translate it accordingly
        rootGroup.setOnMouseDragged((MouseEvent me) -> {
            stage.setX(me.getScreenX() - initX);
            stage.setY(me.getScreenY() - initY);
        });

        // CREATE SIMPLE TEXT NODE
        TextField text = new TextField();
        text.setPrefHeight(30);
        text.setFocusTraversable(false);
        text.setPromptText("Buscar cliente");
        text.setFont(Font.font(Font.getDefault().getFamily(), 12));
        text.textProperty()
                .addListener((obs, old, novo) -> filtrosCliente(novo, FXCollections.observableArrayList(dadoCliente)));

        // USE A LAYOUT VBOX FOR EASIER POSITIONING OF THE VISUAL NODES ON SCENE
        VBox vBox = new VBox();
        vBox.setSpacing(8);
        vBox.setPadding(new Insets(8, 8, 8, 8));
        vBox.setPrefSize(520, 420);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().addAll(text, listCliente);
        rootGroup.setOpacity(0.8);
        rootGroup.getChildren().addAll(vBox);

        stage.showAndWait();
    }

    // Setando dados do listView no caixa de texto
    public void clienteSelectedListView(Cliente cliente) {
        if (cliente != null) {
            cbCliente.setValue(cliente);
        } else {
            cbCliente.setValue(null);
        }
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtrosCliente(String valor, ObservableList<Cliente> listaCliente) {
        FilteredList<Cliente> dadosFiltrados = new FilteredList<>(listaCliente, clientes -> true);
        dadosFiltrados.setPredicate(clientes -> clientes.getNomeCliente().toLowerCase().startsWith(valor.toLowerCase()));

        SortedList<Cliente> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().get();
        listCliente.setItems(dadosOrdenados);
    }

    Callback<TableColumn<Item, String>, TableCell<Item, String>> actions = param -> new TableCell<Item, String>() {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(new Actions() {
                    @Override
                    protected void editButton(ActionEvent actionEvent) {
                        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
                    }

                    @Override
                    protected void deleteButton(ActionEvent actionEvent) {
                        tbItens.getItems().remove(getIndex());
                        viewAll();
                        tbItens.getSelectionModel().clearSelection();
                        sumTotalCost();
                    }
                }
                );
            }
        }
    };

    // Setando dados do listview no caixa de texto
    public void listViewProdutoSelected(Artigo artigo) {
        if (artigo != null) {
            cbArtigo.setValue(artigo);
            if (txtQuantia.getText().trim().isEmpty()) {
                txtPrecoDeVenda.setText(artigo.getPreco().toString());
            } else {
                txtCustoItem.setText(String.valueOf(artigo.getPreco()));
                txtPrecoDeVenda.setText(artigo.getPreco().toString());
            }
        } else {
            txtCustoItem.setText("0");
            txtPrecoDeVenda.clear();
        }
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtros(String valor, ObservableList<Artigo> listaProduto) {
        FilteredList<Artigo> dadosFiltrados = new FilteredList<>(listaProduto, produto -> true);
        dadosFiltrados.setPredicate(produto -> produto.getNomeArtigo().toLowerCase().startsWith(valor.toLowerCase()));
        SortedList<Artigo> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().get();
        listViewProduto.setItems(dadosOrdenados);
    }

    @Override
    public void addItens() throws ParseException {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    @Override
    public void removeItens(int idItem) throws ParseException {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

}
