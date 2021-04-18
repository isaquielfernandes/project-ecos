package cv.com.escola.controller;

import cv.com.escola.app.Login;
import cv.com.escola.EcosApp;
import cv.com.escola.app.Registrar;
import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.TipoUsuario;
import cv.com.escola.model.entity.Usuario;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Combo;
import cv.com.escola.model.util.Criptografia;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Filtro;
import cv.com.escola.model.util.Grupo;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import cv.com.escola.model.util.Nota;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class RegistrarController implements Initializable{

    private List<Usuario> listaUsuario;
    private int idUsuario = 0;

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private ToggleGroup menu;
    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextField txtNome;
    @FXML
    private ComboBox<String> cbStatus;
    @FXML
    private ComboBox<TipoUsuario> cbPermissaoUsuario;
    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtSenha;
    @FXML
    private PasswordField txtConfirmarSenha;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableView<Usuario> tbUsuario;
    @FXML
    private TableColumn<Usuario, Integer> colId;
    @FXML
    private TableColumn<Usuario, String> colNome;
    @FXML
    private TableColumn<Usuario, String> colLogin;
    @FXML
    private TableColumn<Usuario, String> colEmail;
    @FXML
    private TableColumn<Usuario, String> colStatus;
    @FXML
    private TableColumn<Usuario, String> colTipo;
    @FXML
    private TableColumn<Usuario, String> colDescricao;
    @FXML
    private Button btSalvar;
    @FXML
    private Button btEditar;
    @FXML
    private Button btExcluir;
    @FXML
    private Label legenda;
    @FXML
    private AnchorPane AnchorPane;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
   
    //@Override
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarBase();
        combos();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaUsuario));
        });
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        config("Cadastrar Usuário", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limpar();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        config("Editar Usuário", "Quantidade de usuários encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        config("Excluir Usuário", "Quantidade de usuários encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean vazio = Campo.noEmpty(txtNome, txtLogin, txtSenha, txtConfirmarSenha);
        //boolean validacao = Mascara.validationEmail(txtEmail);
        
        String nome = txtNome.getText();
        String login = txtLogin.getText().replaceAll(" ", "").trim();
        String email = txtEmail.getText();
        String confirmar = txtConfirmarSenha.getText();
        String senha = txtSenha.getText();
        String descricao = txtDescricao.getText();
        boolean status = cbStatus.getValue().equals("Ativo");
        TipoUsuario tipo = cbPermissaoUsuario.getValue();

        if (vazio) {
            /*Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Preencher campos vazios!");
            alert.show();*/
            Mensagem.alerta("Preencher campos vazios!");
        } else if (cbPermissaoUsuario.getValue() == null ) {
            Mensagem.alerta("Permissão do Usuário não encontrada!");
        } else if (!senha.equals(confirmar)) {
            Mensagem.alerta("Senha inválida, verifique se senhas são iguais!");
        } else if (DAOFactory.daoFactury().usuarioDAO().isUsuario(idUsuario, login)) {
            Mensagem.alerta("Login já cadastrado na base de dados!");
        } else {
            Usuario user = new Usuario(idUsuario, nome, login, Criptografia.converter(senha), email, status, null, descricao, tipo);

            if (idUsuario == 0) {
                DAOFactory.daoFactury().usuarioDAO().create(user);
                Mensagem.info("Usuário cadastrado com sucesso!");
            } else {
                DAOFactory.daoFactury().usuarioDAO().update(user);
                Mensagem.info("Usuário atualizado com sucesso!");
            }
            sincronizarBase();
            Registrar.fechar();
            new Login().start(new Stage());
        }
    }
    
    public void caregarTelaLogin() {
        try {
            Stage palco;
            Scene cena;
            AnchorPane page;

            Screen screen = Screen.getPrimary();
            Rectangle2D windows = screen.getVisualBounds();

            palco = new Stage();//stage;
            page = FXMLLoader.load(EcosApp.class.getResource("/cv/com/escola/view/login/login.fxml"));
            cena = new Scene(page);

            palco.initStyle(StageStyle.UNDECORATED);

            palco.setX(windows.getMinX());
            palco.setY(windows.getMinY());
            palco.setWidth(windows.getWidth());
            palco.setHeight(windows.getHeight());
            //stage.setMaximized(true);

            palco.getIcons().addAll(new Image(EcosApp.class.getResourceAsStream("/cv/com/escola/img/servidor.png")));

            palco.setScene(cena);
            palco.show();

        } catch (IOException ex) {
            System.out.println("Erro ao inicializar aplicação!" + ex);
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            Usuario user = tbUsuario.getSelectionModel().getSelectedItem();
            user.getClass();

            telaCadastro(null);

            txtNome.setText(user.getNome());
            txtLogin.setText(user.getLogin());
            txtEmail.setText(user.getEmail());
            txtDescricao.setText(user.getDescricao());
            txtSenha.setText("");
            txtConfirmarSenha.setText("");
            cbPermissaoUsuario.setValue(user.getTipoUsuario());
            cbStatus.setValue(user.isAtivo());

            lbTitulo.setText("Editar Usuário");
            menu.selectToggle(menu.getToggles().get(1));

            idUsuario = user.getId();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um usuário na tabela para edição!");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        try {
            Usuario usuario = tbUsuario.getSelectionModel().getSelectedItem();

            Dialogo.Resposta response = Mensagem.confirmar("Excluir usuário " + usuario.getNome() + " ?");

            if (response == Dialogo.Resposta.YES) {
                DAOFactory.daoFactury().usuarioDAO().delete(usuario.getId());
                sincronizarBase();
                tabela();
            }

            tbUsuario.getSelectionModel().clearSelection();

        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione usuário na tabela para exclusão!");
        }
    }

    /**
     * Preencher combos tela
     */
    private void combos() {
        Combo.popular(cbStatus, "Ativo", "Inativo");
        Combo.popular(cbPermissaoUsuario, DAOFactory.daoFactury().usuarioDAO().usuariosTipo());
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, String mensagem, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(mensagem);
        tbUsuario.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idUsuario = 0;
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaUsuario = DAOFactory.daoFactury().usuarioDAO().findAll();
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {

        ObservableList data = FXCollections.observableArrayList(listaUsuario);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
       
        colLogin.setCellValueFactory((TableColumn.CellDataFeatures<Usuario, String> obj) -> new SimpleStringProperty(obj.getValue().getLogin()));
        colNome.setCellValueFactory((TableColumn.CellDataFeatures<Usuario, String> obj) -> new SimpleStringProperty(obj.getValue().getNome()));
        colEmail.setCellValueFactory((TableColumn.CellDataFeatures<Usuario, String> obj) -> new SimpleStringProperty(obj.getValue().getEmail()));
        colDescricao.setCellValueFactory((TableColumn.CellDataFeatures<Usuario, String> obj) -> new SimpleStringProperty(obj.getValue().getDescricao()));
        colStatus.setCellValueFactory((TableColumn.CellDataFeatures<Usuario, String> obj) -> new SimpleStringProperty(obj.getValue().isAtivo()));
        colTipo.setCellValueFactory((TableColumn.CellDataFeatures<Usuario, String> obj) -> new SimpleStringProperty(obj.getValue().getTipoUsuario().getNome()));

        tbUsuario.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtro(String valor, ObservableList<Usuario> listaUsuario) {

        FilteredList<Usuario> dadosFiltrados = new FilteredList<>(listaUsuario, usuario -> true);
        dadosFiltrados.setPredicate(usuario -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (usuario.getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (usuario.getEmail().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (usuario.getLogin().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (usuario.getTipoUsuario().getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }

            return false;
        });

        SortedList<Usuario> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbUsuario.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de usuários encontradas");

        tbUsuario.setItems(dadosOrdenados);
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limpar() {
        Campo.limpar(txtConfirmarSenha, txtLogin, txtNome, txtSenha, txtEmail);
        Campo.limpar(txtDescricao);
    }
    
}
