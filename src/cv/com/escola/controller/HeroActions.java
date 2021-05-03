package cv.com.escola.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author iisaq
 */
public abstract class HeroActions extends AnchorPane {
    
    @FXML
    protected Label lbTitulo;
    @FXML
    protected ToggleGroup menu;
    @FXML
    protected AnchorPane telaEdicao;
    @FXML
    protected ToggleButton btAdcionar;
    @FXML
    protected ToggleButton btEdit;
    @FXML
    protected ToggleButton btApagar;
    @FXML
    protected Button btSalvar;
    @FXML
    protected Button btEditar;
    @FXML
    protected Button btExcluir;
    @FXML
    protected AnchorPane telaCadastro;
    @FXML
    protected TextField txtPesquisar;

    protected HeroActions() {
        super();
    }
    
}
