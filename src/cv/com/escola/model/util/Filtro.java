package cv.com.escola.model.util;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;

/**
 *
 * @author Isaquiel Fernandes
 */
public class Filtro {
    private Filtro() {
    }

    /**
     * Exibir mensagem na legenda da quantidade de itens filtrados pela pesquisas realizadas nas telas de edição e exclusão
     * @param legenda
     * @param qtItens
     * @param mensagem
     */
    public static void mensagem(Label legenda, int qtItens, String mensagem) {
        IntegerProperty size = new SimpleIntegerProperty(qtItens);//criar um property para tamanho da listar de itens filtrados e ordenados
        legenda.textProperty().bind(new StringBinding() {
            {
                bind(size); //monitora mudança
            }

            @Override
            protected String computeValue() {
                return mensagem + ": " + size.get(); //mensagem a ser exibida na filtragem da pesquisa com resultado da filtragem
            }
        });

        legenda.textProperty().unbind();//remove bind para não gerar erro
    }
}
