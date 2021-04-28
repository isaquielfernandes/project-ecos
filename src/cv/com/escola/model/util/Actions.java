package cv.com.escola.model.util;

import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


public abstract class Actions extends GridPane {
    
    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints1;
    protected final RowConstraints rowConstraints;
    protected final Button button;
    protected final Button button1;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    protected Actions() {

        columnConstraints = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        button = new Button();
        button1 = new Button();

        setPrefHeight(28.0);
        setPrefWidth(100.0);
        setHgap(-20);
        setStyle("-fx-background-color: none;");
        getStylesheets().add("cv/com/escola/view/css/bootstrap3.css");

        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints.setMaxWidth(80.0);
        columnConstraints.setMinWidth(80.0);
        columnConstraints.setPrefWidth(80.0);

        columnConstraints1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints1.setMaxWidth(50.0);
        columnConstraints1.setMinWidth(50.0);
        columnConstraints1.setPrefWidth(50.0);

        rowConstraints.setMinHeight(20.0);
        rowConstraints.setPrefHeight(28.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        button.setDefaultButton(true);
        button.setMnemonicParsing(false);
        button.setOnAction(this::editButton);
        button.setPrefWidth(90);
        button.setMinWidth(90);
        button.setPrefHeight(28);
        button.getStyleClass().add("success");
        button.getStyleClass().add("middle");
        button.setText("Edit");


        GridPane.setColumnIndex(button1, 2);
        button1.setCancelButton(true);
        button1.setMnemonicParsing(false);
        button1.setOnAction(this::deleteButton);
        button1.setPrefWidth(80);
        button1.setMinWidth(80);
        button1.setPrefHeight(28);
        button1.getStyleClass().add("danger");
        button1.getStyleClass().add("middle");
        button1.setText("Delete");

        getColumnConstraints().add(columnConstraints);
        getColumnConstraints().add(columnConstraints1);
        getRowConstraints().add(rowConstraints);
        getChildren().add(button);
        getChildren().add(button1);

    }

    protected abstract void editButton(javafx.event.ActionEvent actionEvent);

    protected abstract void deleteButton(javafx.event.ActionEvent actionEvent);
}
