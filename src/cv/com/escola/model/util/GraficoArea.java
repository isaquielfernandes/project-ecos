package cv.com.escola.model.util;


import cv.com.escola.model.entity.Relatorio;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.List;
import java.util.Map;


public class GraficoArea {

    private static CategoryAxis eixoX;
    private static NumberAxis eixoY;
    private static AreaChart<String, Number> grafico;

    private GraficoArea() {
        
    }
    /**
     * Criar grafico de area e inserir dados das series, datas e valores apartir do map informado
     * @param map
     * @param titulo
     * @return 
     */
    public static AreaChart criar(Map<String, List<Relatorio>> map, String titulo) {
        config(titulo);

        eixoX = new CategoryAxis();
        eixoY = new NumberAxis();
        grafico = new AreaChart<>(eixoX, eixoY);

        map.keySet().stream().map(chave -> {
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName(chave);
            List<Relatorio> relatorios = map.get(chave);
            relatorios.forEach(relatorio -> 
                serie.getData().add(new XYChart.Data<>(relatorio.getData(), relatorio.getTotal()))
            );
            return serie;
        }).forEachOrdered(serie -> grafico.getData().add(serie));
        return grafico;
    }

    /**
     * Configurar titulo do graficos e seus eixos X e Y
     * @param titulo
     */
    public static void config(String titulo) {
        grafico.getData().clear();
        grafico.setTitle(titulo);
        grafico.setVerticalGridLinesVisible(false);

        eixoY.setLabel("Value");
        eixoX.setLabel("Data");
    }
}
