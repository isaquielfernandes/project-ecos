package cv.com.escola.model.util;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Isaquiel Fernandes
 */
@Slf4j
public class ValidationFields {

    public static final String STILE_BORDER_VALIDATION = "-fx-border-color: #FF0000";
    
    private ValidationFields() {
        super();
    }
    
    public static boolean checkEmptyFields(Node... itemToCheck) {
        Tooltip tooltip = new Tooltip("Este Campo é Requerido.");
        //used to determinate how many fields failed in validation
        List<Node> failedFields = new ArrayList<>();
        tooltip.setStyle("-fx-background-color: linear-gradient(#FF6B6B , #FFA6A6 );"
                + " -fx-font-weight: bold;");
        hackTooltipStartTiming(tooltip);
        arrayToList(itemToCheck).forEach(node -> {
            // Validate TextFields
            if (node instanceof TextField) {
                TextField textField = new TextField();
                textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> 
                    removeToolTipAndBorderColor(textField, tooltip)
                );
                if (textField.getText() == null || textField.getText().trim().equals("")) {
                    failedFields.add(node);
                    addToolTipAndBorderColor(textField, tooltip);
                } else {
                    removeToolTipAndBorderColor(textField, tooltip);
                }
            } 
            // Validate Combo Box
            else if (node instanceof ComboBox) {
                ComboBox comboBox = new ComboBox();
                comboBox(comboBox, failedFields, node, tooltip);
            }
            // Validate TextArea
            else if (node instanceof TextArea) {
                TextArea textArea = new TextArea();
                textArea(textArea, failedFields, node, tooltip);
            }
            //Validade DatePicker
            else if (node instanceof DatePicker) {
                DatePicker datePicker = new DatePicker();
                datePicker(datePicker, failedFields, node, tooltip);
            }
            //Validade ImageView
            else if (node instanceof ImageView) {
                ImageView imgView = new ImageView();
                imageView(imgView, failedFields, node, tooltip);
            }
        });

        return failedFields.isEmpty();
    }

    public static void textArea(TextArea textArea, List<Node> failedFields, Node n, Tooltip tooltip) {
        textArea.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                removeToolTipAndBorderColor(textArea, tooltip)
        );
        if (textArea.getText() == null || textArea.getText().trim().equals("")) {
            failedFields.add(n);
            addToolTipAndBorderColor(textArea, tooltip);
        } else {
            removeToolTipAndBorderColor(textArea, tooltip);
        }
    }

    public static void comboBox(ComboBox comboBox, List<Node> failedFields, Node n, Tooltip tooltip) {
        comboBox.valueProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) ->
                removeToolTipAndBorderColor(comboBox, tooltip)
        );
        if (comboBox.getValue() == null) {
            failedFields.add(n);
            addToolTipAndBorderColor(comboBox, tooltip);
        } else {
            removeToolTipAndBorderColor(comboBox, tooltip);
        }
    }

    public static void datePicker(DatePicker datePicker, List<Node> failedFields, Node n, Tooltip tooltip) {
        datePicker.valueProperty().addListener((ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) ->
                removeToolTipAndBorderColor(datePicker, tooltip)
        );
        if (datePicker.getValue() == null) {
            failedFields.add(n);
            addToolTipAndBorderColor(datePicker, tooltip);
        } else {
            removeToolTipAndBorderColor(datePicker, tooltip);
        }
    }

    public static void imageView(ImageView imageView, List<Node> failedFields, Node n, Tooltip tooltip) {
        imageView.imageProperty().addListener((observable, oldValue, newValue) ->
                removeToolTipAndBorderColor(imageView, tooltip)
        );
        if (imageView.getImage() == null) {
            failedFields.add(n);
            addToolTipAndBorderColor(imageView, tooltip);
        } else {
            removeToolTipAndBorderColor(imageView, tooltip);
        }
    }

    /**
     * *******ADD AND REMOVE STYLES********
     * @param n
     * @param t
     */
    public static void addToolTipAndBorderColor(Node n, Tooltip t) {
        Tooltip.install(n, t);
        n.setStyle(STILE_BORDER_VALIDATION);
    }

    public static void removeToolTipAndBorderColor(Node n, Tooltip t) {
        Tooltip.uninstall(n, t);
        n.setStyle(null);
    }

    /**
     * ***********ARRAY TO LIST UTILITY************
     * @param n
     * @return 
     */
    public static List<Node> arrayToList(Node[] n) {
        List<Node> listItems = new ArrayList<>();
        listItems.addAll(Arrays.asList(n));
        return listItems;
    }

    public static void hackTooltipStartTiming(Tooltip tooltip) {
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            Object objBehavior = fieldBehavior.get(tooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(5)));
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            log.error(e.getMessage());
        }    
    }
  
}

