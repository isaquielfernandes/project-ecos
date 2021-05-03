package cv.com.escola.controller;


import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Organizacao;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Filtro;
import cv.com.escola.model.util.Grupo;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import cv.com.escola.model.util.Nota;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class OrganizacaoController extends AnchorPane implements Initializable{

    private List<Organizacao> listaOrganizacao;
    private int idOrganizacao = 0;

    @FXML
    private GridPane telaCadastro;
    @FXML
    private TableColumn colCidade;
    @FXML
    private TableColumn colFax;
    @FXML
    private TableView<Organizacao> tbOrganizacao;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TextField txtNome;
    @FXML
    private Button btSalvar;
    @FXML
    private TextField txtPais;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TextField txtFax;
    @FXML
    private TableColumn colSigla;
    @FXML
    private TableColumn colLogradouro;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private TextField txtCidade;
    @FXML
    private TableColumn colDescricao;
    @FXML
    private Button btEditar;
    @FXML
    private TableColumn colId;
    @FXML
    private TextField txtEstado;
    @FXML
    private TextField txtLogradouro;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TextField txtSigla;
    @FXML
    private TableColumn colTelefone;
    @FXML
    private Label legenda;
    @FXML
    private Button btExcluir;
    @FXML
    private TableColumn colBairro;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private ToggleGroup menu;
    @FXML
    private TableColumn colNome;
    @FXML
    private TableColumn colPais;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtBairro;

    @SuppressWarnings("LeakingThisInConstructor")
    public OrganizacaoController() {
        try {
            GenericFXXMLLoader.loadFXML(this, "organisacao");
        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela pesquisar organizacao da organização");
        }
    }

    @FXML
    void telaCadastro(ActionEvent event) {
        config("Cadastrar Organização", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limpar();
    }

    @FXML
    void telaEdicao(ActionEvent event) {
        config("Editar Organização", "Quantidade de organizações encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    void telaExcluir(ActionEvent event) {
        config("Excluir Organização", "Quantidade de organizações encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    void salvar(ActionEvent event) {
        boolean vazio = Campo.noEmpty(txtNome, txtBairro, txtCidade, txtEmail, txtEstado, txtLogradouro, txtPais, txtSigla, txtTelefone);

        String nome = txtNome.getText();
        String sigla = txtSigla.getText();
        String email = txtEmail.getText();
        String logradouro = txtLogradouro.getText();
        String estado = txtEstado.getText();
        String cidade = txtCidade.getText();
        String pais = txtPais.getText();
        String bairro = txtBairro.getText();
        String fax = txtFax.getText();
        String telefone = txtTelefone.getText();
        String descricao = txtDescricao.getText();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else {
            Organizacao orgao = new Organizacao(idOrganizacao, nome, sigla, email, fax, telefone, logradouro, bairro, cidade, estado, pais, descricao, null);

            if (idOrganizacao == 0) {
                DAOFactory.daoFactory().organizacaoDAO().create(orgao);
                Mensagem.info("Organização cadastrada com sucesso!");
            } else {
                DAOFactory.daoFactory().organizacaoDAO().update(orgao);
                Mensagem.info("Organização atualizada com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    void editar(ActionEvent event) {
        try {
            Organizacao orgao = tbOrganizacao.getSelectionModel().getSelectedItem();
            orgao.getClass();

            telaCadastro(null);

            txtNome.setText(orgao.getNome());
            txtBairro.setText(orgao.getBairro());
            txtCidade.setText(orgao.getCidade());
            txtEmail.setText(orgao.getEmail());
            txtEstado.setText(orgao.getEstado());
            txtFax.setText(orgao.getFax());
            txtLogradouro.setText(orgao.getLogradouro());
            txtPais.setText(orgao.getPais());
            txtTelefone.setText(orgao.getTelefone());
            txtSigla.setText(orgao.getSigla());
            txtDescricao.setText(orgao.getDescricao());

            lbTitulo.setText("Editar Organização");
            menu.selectToggle(menu.getToggles().get(1));

            idOrganizacao = orgao.getId();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um organização na tabela para edição!");
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        try {
            Organizacao orgao = tbOrganizacao.getSelectionModel().getSelectedItem();
            Dialogo.Resposta response = Mensagem.confirmar("Excluir organização " + orgao.getNome() + " ?");
            if (response == Dialogo.Resposta.YES) {
                DAOFactory.daoFactory().organizacaoDAO().delete(orgao.getId());
                sincronizarBase();
                tabela();
            }
            tbOrganizacao.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione organização na tabela para exclusão!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarBase();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> 
            filtro(novo, FXCollections.observableArrayList(listaOrganizacao))
        );
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);//mensagem legenda
        tbOrganizacao.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idOrganizacao = 0;
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaOrganizacao = DAOFactory.daoFactory().organizacaoDAO().findAll();
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaOrganizacao);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colFax.setCellValueFactory(new PropertyValueFactory<>("fax"));
        colLogradouro.setCellValueFactory(new PropertyValueFactory<>("logradouro"));
        colPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        colSigla.setCellValueFactory(new PropertyValueFactory<>("sigla"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tbOrganizacao.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtro(String valor, ObservableList<Organizacao> listaOrganizacao) {
        FilteredList<Organizacao> dadosFiltrados = new FilteredList<>(listaOrganizacao, orgao -> true);
        dadosFiltrados.setPredicate(orgao -> 
                orgao.getNome().toLowerCase().startsWith(valor.toLowerCase()) || 
                orgao.getSigla().toLowerCase().startsWith(valor.toLowerCase()) || 
                orgao.getEmail().toLowerCase().startsWith(valor.toLowerCase()) || 
                orgao.getFax().toLowerCase().startsWith(valor.toLowerCase()) || 
                orgao.getTelefone().toLowerCase().startsWith(valor.toLowerCase()) || 
                orgao.getLogradouro().toLowerCase().startsWith(valor.toLowerCase()) || 
                orgao.getBairro().toLowerCase().startsWith(valor.toLowerCase()) || 
                orgao.getCidade().toLowerCase().startsWith(valor.toLowerCase()) || 
                orgao.getEstado().toLowerCase().startsWith(valor.toLowerCase()) || 
                orgao.getPais().toLowerCase().startsWith(valor.toLowerCase()) 
        );

        SortedList<Organizacao> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbOrganizacao.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de organizações encontradas");

        tbOrganizacao.setItems(dadosOrdenados);
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limpar() {
        Campo.limpar(txtNome, txtSigla, txtBairro, txtCidade, txtEmail, txtEstado, txtFax, txtLogradouro, txtPais, txtTelefone);
        Campo.limpar(txtDescricao);
    }

}
