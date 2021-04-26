package cv.com.escola.model.util;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 *
 * @author Isaquiel Fernandes
 */
public class Combo {

    private Combo() {

    }

    /**
     * Popular combos generico atravês de uma coleção do tipo List
     *
     * @param combo
     * @param lista
     */
    public static void popular(ComboBox combo, List<?> lista) {
        dados(combo, FXCollections.observableArrayList(lista));
    }

    /**
     * Popular combos generico atravês um array de strings passados
     *
     * @param combo
     * @param itens
     */
    public static void popular(ComboBox combo, String... itens) {
        dados(combo, FXCollections.observableArrayList(itens));
    }

    /**
     * Popular combos com dados informados ao combo
     */
    @SuppressWarnings("null")
    private static void dados(ComboBox combo, ObservableList dados) {
        if (dados.isEmpty()) {
            limpar(combo);
        } else {
            combo.setItems(dados);
            combo.getSelectionModel().selectFirst();
        }
    }

    /**
     * Limpar combo informado
     */
    private static void limpar(ComboBox<Object> combo) {
        combo.getItems().clear();
        combo.setPromptText("-- Registros não encontrados --");
    }
}
