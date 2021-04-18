package cv.com.escola.controller;

import cv.com.escola.model.entity.Aluno;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class AlunoCard extends AnchorPane {

    protected final AnchorPane anchorPane;
    protected final AnchorPane anchorPaneImag;
    protected final GridPane gridPane;
    protected final VBox vbox;
    protected final ImageView imageView;
    protected final Label labelNome;
    protected final Label labelIdade;
    protected final Label labelContato;
    protected final Label labelDataNascimento;
    protected final Label labelNomeMae;
    protected final Label labelNomePai;
    protected final Label labelNumDoc;
    protected final Label labelEndereco;
    
    protected final Label nome;
    protected final Label idade;
    protected final Label contato;
    protected final Label dataNascimento;
    protected final Label nomeMae;
    protected final Label nomePai;
    protected final Label numDoc;
    protected final Label endereco;

    protected final int alunoID;
    
    private static final String SYSTEM_BOLD = "System Bold";
    private static final String PADDING_0PX_0PX_0PX_6PX = "-fx-padding: 0px 0px 0px 6px";
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AlunoCard(Aluno aluno) {

        alunoID = aluno.getIdAluno();
        
        anchorPane = new AnchorPane();
        anchorPaneImag = new AnchorPane();
        gridPane = new GridPane();
        vbox = new VBox();
        
        nome = new Label("Nome:");
        nome.setMaxHeight(25.0);
        nome.setPrefWidth(70.0);

        nome.setFont(new Font(SYSTEM_BOLD, 11.0));
        
        contato = new Label("Contato:");
        contato.setMaxHeight(25.0);
        contato.setPrefWidth(70.0);
        contato.setFont(new Font(SYSTEM_BOLD, 11.0));
        
        dataNascimento = new Label("Dt. Nasc: ");
        dataNascimento.setMaxHeight(25.0);
        dataNascimento.setPrefWidth(70.0);
        dataNascimento.setFont(new Font(SYSTEM_BOLD, 11.0));
        
        nomeMae = new Label("Mãe:");
        nomeMae.setMaxHeight(25.0);
        nomeMae.setPrefWidth(70.0);
        nomeMae.setFont(new Font(SYSTEM_BOLD, 11.0));
        
        nomePai = new Label("Pai:");
        nomePai.setMaxHeight(25.0);
        nomePai.setPrefWidth(70.0);
        nomePai.setFont(new Font(SYSTEM_BOLD, 11.0));
        
        numDoc = new Label("N° BI/CNI");
        numDoc.setFont(new Font(SYSTEM_BOLD, 11.0));
        numDoc.setStyle(PADDING_0PX_0PX_0PX_6PX);
        
        idade = new Label("Idade:");
        idade.setMaxHeight(25.0);
        idade.setPrefWidth(70.0);
        idade.setFont(new Font(SYSTEM_BOLD, 11.0));
        
        endereco = new Label("Endereço:");
        endereco.setMaxHeight(25.0);
        endereco.setPrefWidth(70.0);
        endereco.setFont(new Font(SYSTEM_BOLD, 11.0));

        imageView = new ImageView();
        labelNome = new Label();
        labelNome.setFont(new Font(SYSTEM_BOLD, 11.0));
        labelNome.setMaxWidth(Double.MAX_VALUE);
        labelContato = new Label();
        labelContato.setFont(new Font(SYSTEM_BOLD, 11.0));
        labelDataNascimento = new Label();
        labelDataNascimento.setFont(new Font(SYSTEM_BOLD, 11.0));
        labelNomeMae = new Label();
        labelNomeMae.setFont(new Font(SYSTEM_BOLD, 11.0));
        labelNomeMae.setStyle(PADDING_0PX_0PX_0PX_6PX);
        labelNomePai = new Label();
        labelNomePai.setFont(new Font(SYSTEM_BOLD, 11.0));
        labelNomePai.setStyle(PADDING_0PX_0PX_0PX_6PX);
        labelNumDoc = new Label();
        labelNumDoc.setFont(new Font(SYSTEM_BOLD, 11.0));
        labelIdade = new Label();
        labelIdade.setFont(new Font(SYSTEM_BOLD, 11.0));
        labelEndereco = new Label();
        labelEndereco.setFont(new Font(SYSTEM_BOLD, 11.0));

        anchorPane.setPrefSize(340, 180);
        anchorPane.getChildren().add(gridPane);
        
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(2.0);
        gridPane.setPrefHeight(180.0);
        gridPane.setPrefWidth(340.0);
        gridPane.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.40), 6, 0.3, 0,1); -fx-background-color: #FFF;");
        gridPane.setVgap(2.0);

        AnchorPane.setBottomAnchor(gridPane, 4.0);
        AnchorPane.setLeftAnchor(gridPane, 4.0);
        AnchorPane.setRightAnchor(gridPane, 4.0);
        AnchorPane.setTopAnchor(gridPane, 4.0);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(Priority.ALWAYS);
        column1.setMaxWidth(84.0);
        column1.setMinWidth(78.0);
        column1.setPrefWidth(84.0);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHgrow(Priority.ALWAYS);
        column2.setMaxWidth(150.0);
        column2.setMinWidth(150.0);
        column2.setPrefWidth(150.0);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setHgrow(Priority.ALWAYS);
        column3.setMaxWidth(100.0);
        column3.setMinWidth(100.0);
        column3.setPrefWidth(100.0);
        gridPane.getColumnConstraints().addAll(column1, column2, column3); 
        
        RowConstraints row1 = new RowConstraints(5.0, 5.0, 5.0);
        row1.setVgrow(Priority.ALWAYS);
        
        RowConstraints row2 = new RowConstraints(20.0, 20.0, 20.0);
        row2.setVgrow(Priority.SOMETIMES);
        
        RowConstraints row3 = new RowConstraints(20.0, 20.0, 20.0);
        row3.setVgrow(Priority.SOMETIMES);
        
        RowConstraints row4 = new RowConstraints(20.0, 20.0, 20.0);
        row4.setVgrow(Priority.SOMETIMES);
        
        RowConstraints row5 = new RowConstraints(20.0, 20.0, 20.0);
        row5.setVgrow(Priority.SOMETIMES);
        
        RowConstraints row6 = new RowConstraints(20.0, 20.0, 20.0);
        row6.setVgrow(Priority.SOMETIMES);
        
        RowConstraints row7 = new RowConstraints(20.0, 20.0, 20.0);
        row7.setVgrow(Priority.SOMETIMES);
        
        RowConstraints row8 = new RowConstraints(20.0, 20.0, 20.0);
        row8.setVgrow(Priority.SOMETIMES);
        
        RowConstraints row9 = new RowConstraints();
        row9.setMinHeight(10.0);
        row9.setPrefHeight(30.0);
        row9.setVgrow(Priority.SOMETIMES);
        
        gridPane.getRowConstraints().addAll(row1, row2, row3, row4, row5,row6, row7, row8, row9);
        
        gridPane.add(vbox, 0, 0, 1, 6);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefHeight(140.0);
        vbox.setPrefWidth(86.0);
        vbox.getChildren().add(imageView);
        imageView.setFitHeight(90.0);
        imageView.setFitWidth(76.0); 
        imageView.setPickOnBounds(true); 
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image("/cv/com/escola/view/img/avater.jpg"));
        
        
        gridPane.add(labelNome, 1, 1, 2, 1); 
        gridPane.add(labelIdade, 1, 2, 2, 1); 
        gridPane.add(labelContato, 1, 3, 1, 1); 
        gridPane.add(labelEndereco, 1, 4, 1, 1); 
        gridPane.add(labelNumDoc, 1, 5, 2, 1);  
        gridPane.add(labelNomeMae, 0, 6, 3, 1); 
        gridPane.add(labelNomePai, 0, 7, 3, 1); 
        
        labelNome.setText(aluno.getNome());
        labelNomeMae.setText("Mae: " + aluno.getNomeDaMae());
        labelNomePai.setText("Pai: " + aluno.getNomeDoPai());
        labelNumDoc.setText("N Doc: " + aluno.getNumBI());
        labelContato.setText("Contato: " + aluno.getContato());
        imageView.setImage(aluno.readFoto());
        labelIdade.setText("Idade: " + idade(aluno) + " anos. Nascido em " + aluno.getDataNascimento());
        labelEndereco.setText("Morada: " + aluno.getResidencia());
        labelDataNascimento.setText(aluno.getDataNascimento().toString());
        
        getChildren().add(anchorPane);
    }

    private String idade(Aluno aluno) {
        LocalDate dataNas = aluno.getDataNascimento();
        return String.valueOf(ChronoUnit.YEARS.between(dataNas, LocalDate.now()));
    }
}
