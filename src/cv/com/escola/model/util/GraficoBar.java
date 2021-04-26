package cv.com.escola.model.util;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;


/**
 * Criar gráficos de barra
 */
public class GraficoBar {

    private static CategoryAxis eixoX;
    private static NumberAxis eixoY;
    private static BarChart<String, Number> grafico;

    private GraficoBar() {
    }

    /**
     * Exibir acima da barra valor atingido
     * @param data
     */
    public static void info(XYChart.Data<String, Number> data) {
        Text texto = new Text(data.getYValue().toString());
        texto.setStyle("-fx-fill: #555; -fx-font-size: 11px;");

        data.getNode().parentProperty().addListener((ObservableValue<? extends Parent> obs, Parent old, Parent novo) -> 
            Platform.runLater(() -> {
                if (novo != null) {
                    Group grupo = new Group(novo);
                    grupo.getChildren().add(texto);
                }
                
            })
        );

        data.getNode().boundsInParentProperty().addListener((ObservableValue<? extends Bounds> obs, Bounds old, Bounds novo) -> {
            texto.setLayoutX(Math.round(novo.getMinX() + novo.getWidth() / 2 - texto.prefWidth(-1) / 2));
            texto.setLayoutY(Math.round(novo.getMinY() - texto.prefHeight(-1) * 0.5));
            texto.setText(null);
        });
        
    }

    /**
     * Configurar elementos do grafico eixos, titulos, legendas
     * @param titulo
     * @param eixo
     */
    public static void config(String titulo, String eixo) {
        grafico.getData().clear();
        eixoX.setLabel(eixo);
        eixoY.setLabel(titulo);
        grafico.setLegendVisible(false);
    }
}
