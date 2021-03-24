package cv.com.escola.model.util;

import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;

import java.text.DecimalFormat;

/**
 * Criar grafico de pizza
 */
public class GraficoPie {

    static PieChart grafico = new PieChart();

    private GraficoPie() {
    }

    /**
     * Exibir nome e porcentagem atingida da fatia do grafico ao passar o mouse sobre as fatias
     * @param grafico
     * @param total
     */
    public static void info(PieChart grafico, double total) {
        grafico.getData().forEach((data) -> {
            final Tooltip tip = new Tooltip(data.getName()+ " - Valor: "+ data.getPieValue() + " : " + new DecimalFormat("#.##").format((data.getPieValue() / total) * 100) + " %");

            data.getNode().setOnMouseEntered((MouseEvent me) -> {
                if (me.getSource() instanceof Node) {
                    Node sender = (Node) me.getSource();
                    Tooltip.install(sender, tip);
                    sender.setEffect(new Glow(0.4));
                }
            });
            data.getNode().setOnMouseExited((MouseEvent me) -> {
                if (me.getSource() instanceof Node) {
                    Node sender = (Node) me.getSource();
                    sender.setEffect(null);
                    tip.hide();
                }
            });
        });
    }
}


