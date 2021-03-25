package cv.com.escola.controller;

import cv.com.escola.model.entity.Aluno;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class AlunoCardController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private VBox vbox;
    @FXML
    private ImageView imageView;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelIdade;
    @FXML
    private Label labelContato;
    @FXML
    private Label labelDataNascimento;
    @FXML
    private Label labelNomeMae;
    @FXML
    private Label labelNomePai;
    @FXML
    private Label labelNumDoc;

    private Aluno aluno;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
        this.labelNome.setText(aluno.getNome());
        this.labelNomeMae.setText(aluno.getNomeDaMae());
        this.labelNomePai.setText(aluno.getNomeDoPai());
        this.labelNumDoc.setText(aluno.getNumBI());
        this.labelContato.setText(aluno.getContato());
        this.imageView.setImage(aluno.image);
        this.labelIdade.setText("" + " anos");
        this.labelDataNascimento.setText(aluno.getDataNascimento().toString());
    }
    
    private String idade(Aluno aluno) {
        String idade = "";
        LocalDate dataNascimento = aluno.getDataNascimento();
        LocalDate now = LocalDate.now();
        idade = String.valueOf(ChronoUnit.YEARS.between(dataNascimento, now));
        return idade;
    }
}
