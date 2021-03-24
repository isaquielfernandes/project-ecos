package cv.com.escola.model.util;

import cv.com.escola.controller.AppController;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * An simple approach to JasperViewer in JavaFX.
 *
 * @author Gustavo Fragoso
 * @version 3.3
 */
public class Print extends AnchorPane {

    private Button print, save, backPage, firstPage, nextPage, lastPage, zoomIn, zoomOut;
    private ImageView report;
    private Label lblReportPages;
    private Stage dialog;
    private TextField txtPage;
    private JasperPrint jasperPrint;

    private final SimpleIntegerProperty currentPage;
    private int imageHeight = 0, imageWidth = 0, reportPages = 0;
    //private final DialogPane dialogPane;

    private final Screen screen = Screen.getPrimary();
    private final Rectangle2D windows = screen.getVisualBounds();

    public Print() {
        /*initStyle(StageStyle.DECORATED);
        initModality(Modality.WINDOW_MODAL);
        setResizable(true);

        dialogPane = getDialogPane();
        dialogPane.setContent(createContentPane());
        dialogPane.getButtonTypes().add(ButtonType.CLOSE);
        dialogPane.lookupButton(ButtonType.CLOSE).setVisible(false);*/
        currentPage = new SimpleIntegerProperty(this, "currentPage", 1);
    }

    // ***********************************************
    // Scene and button actions
    // ***********************************************
    public BorderPane createContentPane() {
        print = new Button(null, new ImageView(getClass().getResource("/cv/com/escola/img/printer.png").toExternalForm()));
        save = new Button(null, new ImageView(getClass().getResource("/cv/com/escola/img/save.png").toExternalForm()));
        backPage = new Button(null, new ImageView(getClass().getResource("/cv/com/escola/img/backimg.png").toExternalForm()));
        firstPage = new Button(null, new ImageView(getClass().getResource("/cv/com/escola/img/firstimg.png").toExternalForm()));
        nextPage = new Button(null, new ImageView(getClass().getResource("/cv/com/escola/img/nextimg.png").toExternalForm()));
        lastPage = new Button(null, new ImageView(getClass().getResource("/cv/com/escola/img/lastimg.png").toExternalForm()));
        zoomIn = new Button(null, new ImageView(getClass().getResource("/cv/com/escola/img/zoomin.png").toExternalForm()));
        zoomOut = new Button(null, new ImageView(getClass().getResource("/cv/com/escola/img/zoomout.png").toExternalForm()));

        // Pref sizes
        print.setPrefSize(30, 30);
        save.setPrefSize(30, 30);
        backPage.setPrefSize(30, 30);
        firstPage.setPrefSize(30, 30);
        nextPage.setPrefSize(30, 30);
        lastPage.setPrefSize(30, 30);
        zoomIn.setPrefSize(30, 30);
        zoomOut.setPrefSize(30, 30);

        backAction();
        nextAction();
        firstPageAction();
        lastPageAction();
        zoomInAction();
        zoomOutAction();
        printAction();
        saveAction();

        txtPage = new TextField("1");
        txtPage.setPrefSize(40, 30);
        txtPage.setOnAction((ActionEvent event) -> {
            try {
                int p = Integer.parseInt(txtPage.getText());
                setCurrentPage((p > 0 && p <= reportPages) ? p : 1);
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.WARNING, "Invalid number", ButtonType.OK).show();
            }
        });

        lblReportPages = new Label("/ 1");

        HBox menu = new HBox(4);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(4));
        menu.setPrefHeight(40.0);
        menu.getChildren().addAll(print, save, firstPage, backPage, txtPage, lblReportPages, nextPage, lastPage, zoomIn, zoomOut);

        // This imageview will preview the pdf inside scrollpane
        report = new ImageView();
        report.setFitHeight(imageHeight);
        report.setFitWidth(imageWidth);

        // Centralizing the ImageView on Scrollpane
        Group contentGroup = new Group();
        contentGroup.getChildren().add(report);

        StackPane stack = new StackPane(contentGroup);
        stack.setAlignment(Pos.CENTER);
        stack.setStyle("-fx-background-color: gray");

        ScrollPane scroll = new ScrollPane(stack);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);

        BorderPane root = new BorderPane();
        root.setTop(menu);
        root.setCenter(scroll);
        root.setPrefSize(1220, 640);

        return root;
    }

    private void backAction() {
        backPage.setOnAction((ActionEvent event) -> {
            int newValue = getCurrentPage() - 1;
            setCurrentPage(newValue);

            // Turn foward buttons on again
            if (nextPage.isDisabled()) {
                nextPage.setDisable(false);
                lastPage.setDisable(false);
            }
        });
    }

    private void firstPageAction() {
        firstPage.setOnAction((ActionEvent event) -> {
            setCurrentPage(1);

            // Turn foward buttons on again
            if (nextPage.isDisabled()) {
                nextPage.setDisable(false);
                lastPage.setDisable(false);
            }
        });
    }

    private void nextAction() {
        nextPage.setOnAction((ActionEvent event) -> {
            int newValue = getCurrentPage() + 1;
            setCurrentPage(newValue);

            // Turn previous button on again
            if (backPage.isDisabled()) {
                backPage.setDisable(false);
                firstPage.setDisable(false);
            }
        });
    }

    private void lastPageAction() {
        lastPage.setOnAction((ActionEvent event) -> {
            setCurrentPage(reportPages);

            // Turn previous button on again
            if (backPage.isDisabled()) {
                backPage.setDisable(false);
                firstPage.setDisable(false);
            }
        });
    }

    private void printAction() {
        print.setOnAction((ActionEvent event) -> {
            try {
                JasperPrintManager.printReport(jasperPrint, true);
            } catch (JRException ex) {
                //ex.printStackTrace();
                Nota.erro("Error: " + ex);
            }
        });
    }

    private void saveAction() {
        save.setOnAction((ActionEvent event) -> {

            FileChooser chooser = new FileChooser();
            FileChooser.ExtensionFilter pdf = new FileChooser.ExtensionFilter("Portable Document Format (*.pdf)", "*.pdf");
            FileChooser.ExtensionFilter html = new FileChooser.ExtensionFilter("HyperText Markup Language", "*.html");
            FileChooser.ExtensionFilter xml = new FileChooser.ExtensionFilter("eXtensible Markup Language", "*.xml");
            FileChooser.ExtensionFilter xls = new FileChooser.ExtensionFilter("Microsoft Excel 2007", "*.xls");
            FileChooser.ExtensionFilter xlsx = new FileChooser.ExtensionFilter("Microsoft Excel 2016", "*.xlsx");
            chooser.getExtensionFilters().addAll(pdf, html, xml, xls, xlsx);

            chooser.setTitle("Salvar");
            chooser.setSelectedExtensionFilter(pdf);
            File file = chooser.showSaveDialog(dialog);

            if (file != null) {
                List<String> box = chooser.getSelectedExtensionFilter().getExtensions();
                switch (box.get(0)) {
                    case "*.pdf":
                        try {
                            JasperExportManager.exportReportToPdfFile(jasperPrint, file.getPath());
                        } catch (JRException ex) {
                        }
                        break;
                    case "*.html":
                        try {
                            JasperExportManager.exportReportToHtmlFile(jasperPrint, file.getPath());
                        } catch (JRException ex) {
                        }
                        break;
                    case "*.xml":
                        try {
                            JasperExportManager.exportReportToXmlFile(jasperPrint, file.getPath(), false);
                        } catch (JRException ex) {
                        }
                        break;
                    case "*.xls":
                        try {
                            JRXlsExporter exporter = new JRXlsExporter();
                            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
                            exporter.exportReport();
                        } catch (JRException ex) {
                        }
                        break;
                    case "*.xlsx":
                        try {
                            JRXlsxExporter exporter = new JRXlsxExporter();
                            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
                            exporter.exportReport();
                        } catch (JRException ex) {
                        }
                        break;
                }
            }
        });
    }

    private void zoomInAction() {
        zoomIn.setOnAction((ActionEvent event) -> {
            zoom(0.15);
        });
    }

    private void zoomOutAction() {
        zoomOut.setOnAction((ActionEvent event) -> {
            zoom(-0.15);
        });
    }

    /**
     * Set the currentPage property and render report page
     *
     * @param page Page number
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public void setCurrentPage(int page) {
        try {
            if (page > 0 && page <= reportPages) {
                currentPage.set(page);
                txtPage.setText(Integer.toString(page));

                if (page == 1) {
                    backPage.setDisable(true);
                    firstPage.setDisable(true);
                }

                if (page == reportPages) {
                    nextPage.setDisable(true);
                    lastPage.setDisable(true);
                }

                // Rendering the current page
                float zoom = (float) 1.30;
                BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, page - 1, zoom);
                WritableImage fxImage = new WritableImage(imageHeight, imageWidth);
                report.setImage(SwingFXUtils.toFXImage(image, fxImage));
            }
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Get the current page
     *
     * @return Current page value
     */
    public int getCurrentPage() {
        return currentPage.get();
    }

    /**
     * Get the currentPage property
     *
     *
     * @return
     */
    public SimpleIntegerProperty currentPageProperty() {
        return currentPage;
    }

    /**
     * Load report from JasperPrint
     *
     * @param title Dialog title
     * @param jasperPrint JasperPrint object
     */
    public void viewReport(String title, JasperPrint jasperPrint) {
        abrir(AppController.getInstance().boxImprisao(), title);
        Platform.runLater(() -> {
            this.jasperPrint = jasperPrint;
            // Report rendered image properties
            imageHeight = jasperPrint.getPageHeight() + 284;
            imageWidth = jasperPrint.getPageWidth() + 201;
            reportPages = jasperPrint.getPages().size();
            lblReportPages.setText("/ " + reportPages);

            setCurrentPage(1);

            // With only one page those buttons are unnecessary
            if (reportPages == 1) {
                nextPage.setDisable(true);
                lastPage.setDisable(true);
            }
        });

    }

    public void viewPrint(String title, JasperPrint jasperPrint) {
        try {
            this.jasperPrint = jasperPrint;
            JasperPrintManager.printReport(jasperPrint, true);
        } catch (JRException ex) {
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void abrir(TabPane telaPrint, String title) {
        Tab tab = new Tab(title);
        tab.setContent(createContentPane());
        telaPrint.getTabs().add(tab);
        telaPrint.getSelectionModel().select(tab);
    }

    public void exibir() {

    }

    /**
     * Scale image from ImageView
     *
     * @param factor Zoom factor
     */
    public void zoom(double factor) {
        report.setScaleX(report.getScaleX() + factor);
        report.setScaleY(report.getScaleY() + factor);
        report.setFitHeight(imageHeight + factor);
        report.setFitWidth(imageWidth + factor);
    }
//
//    @Override
//    public void run() {
//        printAction();
//        show();
//    }

}
