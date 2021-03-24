package cv.com.escola.model.util;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author Isaquiel Fernandes
 */
public class Campo {
    private Campo() {
    }

    /**
     * Não permitir que campos de textos com valores nulos
     * @param field
     * @return 
     */
    public static boolean noEmpty(TextField... field) {

        boolean vazio = false;

        for (TextField campo : field) {
            if (campo.getText().trim().isEmpty()) {
                erro(campo, "Verificar valor vazio!");
                vazio = true;
            }
        }

        return vazio;
    }

    /**
     * Limpar textos dos campos informados
     * @param no
     */
    public static void limpar(TextField... no) {
        for (TextField campo : no) {
            campo.setText("");
        }
    }
    /**
     * Limpar textos dos campos informados
     * @param no
     */
    public static void limpar(Label... no) {
        for (Label campo : no) {
            //campo.setText("");
            campo.setTextFill(Color.BLACK);
            campo.setStyle("-fx-background-color: #EEEEEE");
        }
    }
// -----------------------------------------------------------
    /**
     * Não permitir que campos de data com valores nulos
     * @param datePicker
     * @return 
     */
    public static boolean noEmpty(DatePicker... datePicker) {
        boolean vazio = false;
        for (DatePicker campo : datePicker) {
            if (campo.getEditor().getText().trim().isEmpty() || campo.getValue() == null) {
                erro(campo, "Verificar valor vazio!");
                vazio = true;
            }
        }
        return vazio;
    }

    /**
     * Limpar textos dos campos informados
     * @param no
     */
    public static void limpar(DatePicker... no) {
        for (DatePicker campo : no) {
            campo.setValue(null);
            campo.getEditor().setText("");
        }
    }
// -----------------------------------------------------------

    /**
     * Limpar textos dos TextArea informado
     * @param no
     */
    public static void limpar(TextArea... no) {
        for (TextArea campo : no) {
            campo.setText("");
        }
    }
// -----------------------------------------------------------
    /**
     * Não permitir que campos de combobox com valores nulos
     * @param combobox
     * @return 
     */
    public static boolean noEmpty(ComboBox... combobox) {

        boolean vazio = false;

        for (ComboBox campo : combobox) {
            if (campo.getEditor().getText().trim().isEmpty() || campo.getValue() == null) {
                erro(campo, "Verificar valor vazio!");
                vazio = true;
            }
        }

        return vazio;
    }

    /**
     * Limpar textos dos campos combobox informados
     * @param no
     */
    public static void limpar(ComboBox... no) {
        for (ComboBox campo : no) {
            campo.setValue(null);
            campo.getEditor().setText("");
        }
    }
    // -----------------------------------------------------------
    /**
     * Indicador erro no campo informado com borda vermelha
     * @param no
     * @param mensagem
     */
    public static void erro(Node no, String mensagem) {
        try {
            if (no != null) {
                no.setStyle("-fx-border-color: #ff7575;");
                origem(no);
            }
        } catch (Exception ex) {
            Nota.erro("Erro erro campo");
        }
    }

    /**
     * Ao clicar no campo voltar ao estilo padrão do campo
     */
    private static void origem(Node no) {
        no.setOnMouseClicked((MouseEvent me) -> {
            no.setStyle("-fx-border-color: #eaeaea;");
        });
        no.setOnMouseReleased((MouseEvent me) -> {
            no.setStyle("-fx-border-color: #eaeaea;");
        });
    }

    /**
     * Exibir erro campo no login caso deixe espaço vazio ou incorreto
     * @param no
     */
    public static void erroLogin(Node no) {
        no.setStyle("-fx-border-color: #ff8b8b;");
        no.setOnMouseClicked((MouseEvent me) -> {
            no.setStyle("-fx-border-color: transparent transparent #e8e8e8 transparent;");
        });
    }
}
