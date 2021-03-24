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

/**
 *
 * @author Isaquiel Fernandes
 */
public class ValidationFields {

    private static final String STILE_BORDER_VALIDATION = "-fx-border-color: #FF0000";
    private static final Tooltip TOOLTIP = new Tooltip("Este Campo é Requerido.");

    public static boolean checkEmptyFields(Node... itemToCheck) {
        //used to determinate how many fields failed in validation
        List<Node> failedFields = new ArrayList<>();
        TOOLTIP.setStyle("-fx-background-color: linear-gradient(#FF6B6B , #FFA6A6 );"
                + " -fx-font-weight: bold;");
        hackTooltipStartTiming(TOOLTIP);
        arrayToList(itemToCheck).forEach((n) -> {
            // Validate TextFields
            if (n instanceof TextField) {
                TextField textField = (TextField) n;
                textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    removeToolTipAndBorderColor(textField, TOOLTIP);
                });
                if (textField.getText() == null || textField.getText().trim().equals("")) {
                    failedFields.add(n);
                    addToolTipAndBorderColor(textField, TOOLTIP);
                } else {
                    removeToolTipAndBorderColor(textField, TOOLTIP);
                }
            } 
            // Validate Combo Box
            else if (n instanceof ComboBox) {
                ComboBox comboBox = (ComboBox) n;
                comboBox.valueProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
                    removeToolTipAndBorderColor(comboBox, TOOLTIP);
                });
                if (comboBox.getValue() == null) {
                    failedFields.add(n);
                    addToolTipAndBorderColor(comboBox, TOOLTIP);
                } else {
                    removeToolTipAndBorderColor(comboBox, TOOLTIP);
                }
            }
            // Validate TextArea
            else if (n instanceof TextArea) {
                TextArea textArea = (TextArea) n;
                textArea.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    removeToolTipAndBorderColor(textArea, TOOLTIP);
                });
                if (textArea.getText() == null || textArea.getText().trim().equals("")) {
                    failedFields.add(n);
                    addToolTipAndBorderColor(textArea, TOOLTIP);
                } else {
                    removeToolTipAndBorderColor(textArea, TOOLTIP);
                }
            }
            //Validade DatePicker
            else if (n instanceof DatePicker) {
                DatePicker datePicker = (DatePicker) n;
                datePicker.valueProperty().addListener((ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) -> {
                    removeToolTipAndBorderColor(datePicker, TOOLTIP);
                });
                if (datePicker.getValue() == null) {
                    failedFields.add(n);
                    addToolTipAndBorderColor(datePicker, TOOLTIP);
                } else {
                    removeToolTipAndBorderColor(datePicker, TOOLTIP);
                }
            }
            //Validade ImageView
            else if (n instanceof ImageView) {
                ImageView imageView = (ImageView) n;
                imageView.imageProperty().addListener((observable, oldValue, newValue) -> {
                    removeToolTipAndBorderColor(imageView, TOOLTIP);
                });
                if (imageView.getImage() == null) {
                    failedFields.add(n);
                    addToolTipAndBorderColor(imageView, TOOLTIP);
                } else {
                    removeToolTipAndBorderColor(imageView, TOOLTIP);
                }
            }
            //TODO
        });

        return failedFields.isEmpty();
    }

    /**
     * *******ADD AND REMOVE STYLES********
     */
    private static void addToolTipAndBorderColor(Node n, Tooltip t) {
        Tooltip.install(n, t);
        n.setStyle(STILE_BORDER_VALIDATION);
    }

    private static void removeToolTipAndBorderColor(Node n, Tooltip t) {
        Tooltip.uninstall(n, t);
        n.setStyle(null);
    }

    /**
     * ***********ARRAY TO LIST UTILITY************
     */
    private static List<Node> arrayToList(Node[] n) {
        List<Node> listItems = new ArrayList<>();
        listItems.addAll(Arrays.asList(n));
        return listItems;
    }

    /**
     * ***********FORCE TOOL TIP TO BE DISPLAYED FASTER************
     */
    private static void hackTooltipStartTiming(Tooltip tooltip) {
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(5)));
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            System.out.println(e);
        }    
    }
}

