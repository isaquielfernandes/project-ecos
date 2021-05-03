package cv.com.escola.controller;

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
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class UsuarioController extends UserCommunField implements Initializable {

    private static final String QUANTIDADE_DE_USUARIOS_ENCONTRADOS = "Quantidade de usuários encontrados";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarBase();
        combos();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> 
            filtro(novo, FXCollections.observableArrayList(listaUsuario))
        );
    }

    public UsuarioController() {
        try {
            GenericFXXMLLoader.loadFXML(this, "usuario");
        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela do usuário!");
        }
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        config("Cadastrar Usuário", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limpar();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        config("Editar Usuário", QUANTIDADE_DE_USUARIOS_ENCONTRADOS, 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        config("Excluir Usuário", QUANTIDADE_DE_USUARIOS_ENCONTRADOS, 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }
    

    @FXML
    private void salvar(ActionEvent event) {
        boolean vazio = Campo.noEmpty(txtNome, txtLogin, txtSenha, txtConfirmarSenha);
        
        String nome = txtNome.getText();
        String login = txtLogin.getText().replaceAll(" ", "").trim();
        String email = txtEmail.getText();
        String confirmar = txtConfirmarSenha.getText();
        String senha = txtSenha.getText();
        String descricao = txtDescricao.getText();
        boolean status = cbStatus.getValue().equals("Ativo");
        TipoUsuario tipo = cbPermissaoUsuario.getValue();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else if (cbPermissaoUsuario.getValue() == null ) {
            Nota.alerta("Permissão do Usuário não encontrada!");
        } else if (!senha.equals(confirmar)) {
            Nota.alerta("Senha inválida, verifique se senhas são iguais!");
        } else if (DAOFactory.daoFactory().usuarioDAO().isUsuario(idUsuario, login)) {
            Nota.alerta("Login já cadastrado na base de dados!");
        } else {
            Usuario user = new Usuario(idUsuario, nome, login, Criptografia.converter(senha), email, status, null, descricao, tipo);

            if (idUsuario == 0) {
                DAOFactory.daoFactory().usuarioDAO().create(user);
                Mensagem.info("Usuário cadastrado com sucesso!");
            } else {
                DAOFactory.daoFactory().usuarioDAO().update(user);
                Mensagem.info("Usuário atualizado com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
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
                DAOFactory.daoFactory().usuarioDAO().delete(usuario.getId());
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
        Combo.popular(cbPermissaoUsuario, DAOFactory.daoFactory().usuarioDAO().usuariosTipo());
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
        listaUsuario = DAOFactory.daoFactory().usuarioDAO().findAll();
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
        dadosFiltrados.setPredicate(usuario -> 
                usuario.getNome().toLowerCase().startsWith(valor.toLowerCase()) || 
                usuario.getEmail().toLowerCase().startsWith(valor.toLowerCase()) || 
                usuario.getLogin().toLowerCase().startsWith(valor.toLowerCase()) || 
                usuario.getTipoUsuario().getNome().toLowerCase().startsWith(valor.toLowerCase())
        );
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
