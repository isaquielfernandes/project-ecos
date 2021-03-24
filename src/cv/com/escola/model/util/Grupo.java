package cv.com.escola.model.util;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class Grupo {
    
    /**
     * Auxilia para togglemenus ou botões não fique nulo ao clicar duas vezes no mesmo menu,
     * para esse caso deixar sempre o ultimo menu selecionado ativo
     * @param grupoMenu
     */
    public static void notEmpty(ToggleGroup... grupoMenu) {
        for (ToggleGroup grupo : grupoMenu) {
            grupo.selectedToggleProperty().addListener(
                    (ObservableValue<? extends Toggle> obs, Toggle old, Toggle novo) -> {
                        if (grupo.getSelectedToggle() == null) {
                            grupo.selectToggle(old);
                        }
                    });
        }
    }
}
