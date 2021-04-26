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
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Slf4j
public class Print extends AnchorPane {

    private Button btnPrint;
    private Button save;
    private Button backPage;
    private Button firstPage;
    private Button nextPage;
    private Button lastPage;
    private Button zoomIn;
    private Button zoomOut;
    private static final String PATH = "/cv/com/escola/view/img/";
    private ImageView report;
    private Label lblReportPages;
    private Stage dialog;
    private TextField txtPage;
    private JasperPrint jasperPrint;

    private final SimpleIntegerProperty currentPage;
    private int imageHeight = 0;
    private int imageWidth = 0;
    private int reportPages = 0;

    public Print() {
        currentPage = new SimpleIntegerProperty(this, "currentPage", 1);
    }

    // ***********************************************
    // Scene and button actions
    // ***********************************************
    public BorderPane createContentPane() {
        buttonAcction();
        setupPrefSizes();

        backAction();
        nextAction();
        firstPageAction();
        lastPageAction();
        zoomInAction();
        zoomOutAction();
        printAction();
        saveAction();

        setupTxtPage();

        lblReportPages = new Label("/1");

        HBox menu = menuHBox();

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

    private void setupTxtPage() {
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
    }

    private HBox menuHBox() {
        HBox menu = new HBox(4);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(4));
        menu.setPrefHeight(40.0);
        menu.getChildren().addAll(btnPrint, save, firstPage, backPage, txtPage, 
                lblReportPages, nextPage, lastPage, zoomIn, zoomOut);
        return menu;
    }

    private void setupPrefSizes() {
        btnPrint.setPrefSize(30, 30);
        save.setPrefSize(30, 30);
        backPage.setPrefSize(30, 30);
        firstPage.setPrefSize(30, 30);
        nextPage.setPrefSize(30, 30);
        lastPage.setPrefSize(30, 30);
        zoomIn.setPrefSize(30, 30);
        zoomOut.setPrefSize(30, 30);
    }

    private void buttonAcction() {
        btnPrint = new Button(null, new ImageView(getClass()
                .getResource(PATH + "printer.png").toExternalForm()));
        save = new Button(null, new ImageView(getClass()
                .getResource(PATH + "save.png").toExternalForm()));
        backPage = new Button(null, new ImageView(getClass()
                .getResource(PATH + "backimg.png").toExternalForm()));
        firstPage = new Button(null, new ImageView(getClass()
                .getResource(PATH + "firstimg.png").toExternalForm()));
        nextPage = new Button(null, new ImageView(getClass()
                .getResource(PATH + "nextimg.png").toExternalForm()));
        lastPage = new Button(null, new ImageView(getClass()
                .getResource(PATH + "lastimg.png").toExternalForm()));
        zoomIn = new Button(null, new ImageView(getClass()
                .getResource(PATH + "zoomin.png").toExternalForm()));
        zoomOut = new Button(null, new ImageView(getClass()
                .getResource(PATH + "zoomout.png").toExternalForm()));
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
        btnPrint.setOnAction((ActionEvent event) -> {
            try {
                JasperPrintManager.printReport(jasperPrint, true);
            } catch (JRException ex) {
                Nota.erro("Error: " + ex);
            }
        });
    }

    private void saveAction() {
        save.setOnAction((ActionEvent event) -> {
            File file = fileChooser().showSaveDialog(dialog);
            if (file != null) {
                try {
                    saveOptions(file);
                } catch (JRException ex) {
                    Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void saveOptions(File file) throws JRException {
        List<String> box = fileChooser().getSelectedExtensionFilter().getExtensions();
        switch (box.get(0)) {
            case "*.pdf":
                JasperExportManager.exportReportToPdfFile(jasperPrint, file.getPath());
                break;
            case "*.html":
                JasperExportManager.exportReportToHtmlFile(jasperPrint, file.getPath());
                break;
            case "*.xml":
                JasperExportManager.exportReportToXmlFile(jasperPrint, file.getPath(), false);
                break;
            case "*.xls":
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
                exporter.exportReport();
                break;
            case "*.xlsx":
                JRXlsxExporter xlsxExporterx = new JRXlsxExporter();
                xlsxExporterx.setExporterInput(new SimpleExporterInput(jasperPrint));
                xlsxExporterx.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
                xlsxExporterx.exportReport();
                break;
            default:
                break;
        }
    }

    private FileChooser fileChooser() {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter pdf = new FileChooser.ExtensionFilter("Portable Document Format (*.pdf)", "*.pdf");
        FileChooser.ExtensionFilter html = new FileChooser.ExtensionFilter("HyperText Markup Language", "*.html");
        FileChooser.ExtensionFilter xml = new FileChooser.ExtensionFilter("eXtensible Markup Language", "*.xml");
        FileChooser.ExtensionFilter xls = new FileChooser.ExtensionFilter("Microsoft Excel 2007", "*.xls");
        FileChooser.ExtensionFilter xlsx = new FileChooser.ExtensionFilter("Microsoft Excel 2016", "*.xlsx");
        chooser.getExtensionFilters().addAll(pdf, html, xml, xls, xlsx);
        chooser.setTitle("Salvar");
        chooser.setSelectedExtensionFilter(pdf);
        return chooser;
    }

    private void zoomInAction() {
        zoomIn.setOnAction((ActionEvent event) -> zoom(0.15));
    }

    private void zoomOutAction() {
        zoomOut.setOnAction((ActionEvent event) -> zoom(-0.15));
    }

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
            log.error(ex.getMessage());
        }
    }

    public int getCurrentPage() {
        return currentPage.get();
    }

    public SimpleIntegerProperty currentPageProperty() {
        return currentPage;
    }

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

    public void viewPrint(JasperPrint jasperPrint) {
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

    public void zoom(double factor) {
        report.setScaleX(report.getScaleX() + factor);
        report.setScaleY(report.getScaleY() + factor);
        report.setFitHeight(imageHeight + factor);
        report.setFitWidth(imageWidth + factor);
    }

}
