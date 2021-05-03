package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.util.GraficoPie;
import cv.com.escola.model.util.Mensagem;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Year;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * @author Isaquiel Fernandes
 */
public class RelatorioEscolarController extends AnchorPane implements Initializable {

    @FXML
    private Label lbTeorica;
    @FXML
    private Label lbPratica;
    @FXML
    private Label lbCap;
    @FXML
    private Label lbTecnica;
    @FXML
    private Label lblAprovadoTeorica;
    @FXML
    private Label lbAprovadoPratica;
    @FXML
    private Label lblAprovadoCap;
    @FXML
    private Label lblAprovadoTecnica;
    @FXML
    private Label lblPorcentagemDeAprovadoTeorica;
    @FXML
    private Label lblPorcentagemDeAprovadoPratica;
    @FXML
    private Label lblPorcentagemDeAprovadoCap;
    @FXML
    private Label lblPorcentagemDeAprovadoTecnica;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private HBox root;
    @FXML
    private HBox rootBar;

    private static final String FALTOU = "FALTOU";
    private static final String REPROVADO = "REPROVADO";
    private static final String APROVADO = "APROVADO";

    private static final String TECNICA = "TECNICA";
    private static final String PRATICA = "PRATICA";
    private static final String TEORICA = "TEORICA";
    private static final String CAP = "CAP";
    
    private int totalTeorica;
    private int totalPratica;
    private int totalTecnica;
    private int totalCap;

    private String anoAgora = Year.now().toString();
    private String anoPassUm = Year.now().minusYears(1).toString();
    private String anoPassDois = Year.now().minusYears(2).toString();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        relatorioGeral();
        root.getChildren().add(createChart());
        rootBar.getChildren().add(createChartBar());
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public RelatorioEscolarController() {
        try {
            GenericFXXMLLoader.loadFXML(this, "relatorioEscolar");
        } catch (IOException ex) {
            Logger.getLogger(RelatorioEscolarController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela relatorio");
        }
    }

    public void relatorioGeral() {
        Year ano = Year.now();

        totalTeorica = DAOFactory.daoFactory().relatorioEscolaDAO().count(TEORICA, ano);
        totalPratica = DAOFactory.daoFactory().relatorioEscolaDAO().count(PRATICA, ano);
        totalTecnica = DAOFactory.daoFactory().relatorioEscolaDAO().count(TECNICA, ano);
        totalCap = DAOFactory.daoFactory().relatorioEscolaDAO().count(CAP, ano);

        int totalTeoricaAprovado = DAOFactory.daoFactory().relatorioEscolaDAO()
                .countResultadoPorTipoExame(TEORICA, APROVADO, ano.toString());
        int totalPraticaAprovado = DAOFactory.daoFactory().relatorioEscolaDAO()
                .countResultadoPorTipoExame(PRATICA, APROVADO, ano.toString());
        int totalCapAprovado = DAOFactory.daoFactory().relatorioEscolaDAO()
                .countResultadoPorTipoExame(CAP, APROVADO, ano.toString());
        int totalTecnicaAprovado = DAOFactory.daoFactory().relatorioEscolaDAO()
                .countResultadoPorTipoExame(TECNICA, APROVADO, ano.toString());

        lblAprovadoTeorica.setText(totalTeoricaAprovado + "");
        lbAprovadoPratica.setText(totalPraticaAprovado + "");
        lblAprovadoTecnica.setText(totalTecnicaAprovado + "");
        lblAprovadoCap.setText(totalCapAprovado + "");

        lbTeorica.setText(totalTeorica + "");
        lbPratica.setText(totalPratica + "");
        lbTecnica.setText(totalTecnica + "");
        lbCap.setText(totalCap + "");

        float percentagemAprovadoEmTeorica = totalTeorica == 0 ? 0 : Float.valueOf(totalTeoricaAprovado) / Float.valueOf(totalTeorica) * 100;
        float percentagemAprovadoEmPratica = totalPratica == 0 ? 0 : Float.valueOf(totalPraticaAprovado) / Float.valueOf(totalPratica) * 100;
        float percentagemAprovadoEmTecnica = totalTecnica == 0 ? 0 : Float.valueOf(totalTecnicaAprovado) / Float.valueOf(totalTecnica) * 100;
        float percentagemAprovadoEmCAP = totalCap == 0 ? 0 : Float.valueOf(totalCapAprovado) / Float.valueOf(totalCap) * 100;

        DecimalFormat myFormatter = new DecimalFormat("###,###.##");

        lblPorcentagemDeAprovadoTeorica.setText(myFormatter.format(percentagemAprovadoEmTeorica) + "%");
        lblPorcentagemDeAprovadoPratica.setText(myFormatter.format(percentagemAprovadoEmPratica) + "%");
        lblPorcentagemDeAprovadoCap.setText(myFormatter.format(percentagemAprovadoEmCAP) + "%");
        lblPorcentagemDeAprovadoTecnica.setText(myFormatter.format(percentagemAprovadoEmTecnica) + "%");

    }

    protected PieChart createChart() {
        Year ano = Year.now();

        final PieChart pc = new PieChart(FXCollections.observableArrayList(new PieChart.Data(TEORICA, totalTeorica),
                new PieChart.Data(PRATICA, (double) totalPratica),
                new PieChart.Data(TECNICA, (double) totalTecnica),
                new PieChart.Data(CAP, (double) totalCap)
        ));
        // setup chart
        pc.setId("BasicPie");
        pc.setTitle("QUANTIDADE DE EXAME POR TIPO");
        GraficoPie.info(pc, DAOFactory.daoFactory().relatorioEscolaDAO().count(ano));
        return pc;
    }

    @SuppressWarnings("Convert2Diamond")
    protected BarChart<String, Number> createChartBar() {
        final String[] years = {anoAgora, anoPassUm, anoPassDois};
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis, "", null));
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        // setup chart
        bc.setTitle("RESULTADO");
        xAxis.setLabel("ANO");
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(years)));
        yAxis.setLabel("Quantidade");
        // add starting data
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName(APROVADO);
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName(REPROVADO);
        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName(FALTOU);
        // create sample data
        series1.getData().add(new XYChart.Data<>(years[0], (int) DAOFactory
                .daoFactory().relatorioEscolaDAO().countResultado(APROVADO, anoAgora)));
        series1.getData().add(new XYChart.Data<>(years[1], (int) DAOFactory
                .daoFactory().relatorioEscolaDAO().countResultado(APROVADO, anoPassUm)));
        series1.getData().add(new XYChart.Data<>(years[2], (int) DAOFactory
                .daoFactory().relatorioEscolaDAO().countResultado(APROVADO, anoPassDois)));
        series2.getData().add(new XYChart.Data<>(years[0], (int) DAOFactory
                .daoFactory().relatorioEscolaDAO().countResultado(REPROVADO, anoAgora)));
        series2.getData().add(new XYChart.Data<>(years[1], (int) DAOFactory
                .daoFactory().relatorioEscolaDAO().countResultado(REPROVADO, anoPassUm)));
        series2.getData().add(new XYChart.Data<>(years[2], (int) DAOFactory
                .daoFactory().relatorioEscolaDAO().countResultado(REPROVADO, anoPassDois)));
        series3.getData().add(new XYChart.Data<>(years[0], (int) DAOFactory
                .daoFactory().relatorioEscolaDAO().countResultado(FALTOU, anoAgora)));
        series3.getData().add(new XYChart.Data<>(years[1], (int) DAOFactory
                .daoFactory().relatorioEscolaDAO().countResultado(FALTOU, anoPassUm)));
        series3.getData().add(new XYChart.Data<>(years[2], (int) DAOFactory
                .daoFactory().relatorioEscolaDAO().countResultado(FALTOU, anoPassDois)));
        
        bc.getData().add(series1);
        bc.getData().add(series2);
        bc.getData().add(series3);
        bc.setLegendVisible(true);
        return bc;
    }

}
