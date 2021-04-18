/*
 * Copyright (C) 2019 Isaquiel Fernandes.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package cv.com.escola.controller;

import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Aluno;
import cv.com.escola.model.entity.Curso;
import cv.com.escola.model.entity.Matricula;
import cv.com.escola.model.util.Combo;
import cv.com.escola.model.util.Dialogo;
import cv.com.escola.model.util.Filtro;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Modulo;
import cv.com.escola.model.util.Nota;
import static cv.com.escola.model.util.ValidationFields.checkEmptyFields;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class InscricaoController extends AnchorPane implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private ToggleGroup menu;
    @FXML
    private ToggleButton btAtualizar;
    @FXML
    private GridPane telaCadastro;
    @FXML
    private ComboBox<Curso> cbCurso;
    @FXML
    private ComboBox<String> cbTurma;
    @FXML
    private DatePicker dataInscricao;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private ListView<Aluno> listViewAluno;
    @FXML
    private TextField txtBuscarAluno;
    @FXML
    private ComboBox<String> cbPeriodo;
    @FXML
    private TableView<Matricula> tbInscricao;
    @FXML
    private TableColumn<Matricula, String> colNomeAluno;
    @FXML
    private TableColumn<Matricula, LocalDate> colDataInscricao;
    @FXML
    private TableColumn<Matricula, Curso> colCurso;
    @FXML
    private TableColumn<Matricula, String> colTurma;
    @FXML
    private TableColumn<Matricula, String> colPeriodo;
    @FXML
    private Button btAdd;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private Button btEditar;
    @FXML
    private Button btExcluir;
    @FXML
    private Button btSalvar;
    @FXML
    private TextField txtAluno;
    @FXML
    private Label legenda;

    private int idInscricao;
    private List<Aluno> listaAluno;
    private List<Curso> listaCurso;
    private List<Matricula> listaInscricao;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataInscricao.setValue(LocalDate.now());
        sincronizarBase();
        combo();
        listView();
        tabela();
        telaCadastro(null);
        selectAlunoListView(null);
        listViewAluno.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectAlunoListView(newValue);
        });
        txtBuscarAluno.textProperty().addListener((observable, oldValue, newValue) -> {
            filtros(newValue, FXCollections.observableArrayList(listaAluno));
        });
        txtPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
            filtro(newValue, FXCollections.observableArrayList(listaInscricao));
        });
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public InscricaoController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/cv/com/escola/view/inscricao.fxml"));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(InscricaoController.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao carregar tela inscricao! \n" + ex);
        }
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbInscricao.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idInscricao = 0;
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaAluno = DAOFactory.daoFactury().alunoDAO().findAll();
        listaCurso = DAOFactory.daoFactury().cursosDAO().findAll();
        listaInscricao = DAOFactory.daoFactury().matriculaDAO().findAll();
    }

    //limpar campos
    private void limparCampos() {
        txtAluno.clear();
        listViewAluno.getSelectionModel().clearSelection();
    }

    //combo
    private void combo() {
        Combo.popular(cbTurma, "CODIGO", "MECANICA", "CARTEIRA", "PESADO", "PUBLICO", "ATRELADO", "INSTRUTORES");
        Combo.popular(cbPeriodo, "MANHA", "TARDE", "NOITE");
        Combo.popular(cbCurso, DAOFactory.daoFactury().cursosDAO().findAll());
    }

    //Setando lista de aluno na listView
    private void listView() {
        ObservableList data = FXCollections.observableArrayList(listaAluno);
        listViewAluno.setItems(data);
    }

    public void selectAlunoListView(Aluno aluno) {
        if (aluno != null) {
            txtAluno.setText(aluno.getNome());
        } else {
            txtAluno.clear();
        }
    }

    //Sentando registro na tabela
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaInscricao);

        colDataInscricao.setCellValueFactory((CellDataFeatures<Matricula, LocalDate> obj) -> obj.getValue().dataProperty());
        colNomeAluno.setCellValueFactory((CellDataFeatures<Matricula, String> obj) -> obj.getValue().alunoProperty().get().nomeProperty());
        colCurso.setCellValueFactory((CellDataFeatures<Matricula, Curso> obj) -> obj.getValue().cursoPretendidoProperty());
        colPeriodo.setCellValueFactory((CellDataFeatures<Matricula, String> obj) -> obj.getValue().periodoProperty());
        colTurma.setCellValueFactory((CellDataFeatures<Matricula, String> obj) -> obj.getValue().turmaProperty());
        //colObservacao.setCellValueFactory((CellDataFeatures<Matricula, String> obj) -> obj.getValue().observacaoProperty());

        tbInscricao.setItems(data);
    }
    
    /**
     * Campo de pesquisar para filtrar dados na listView
     */
    private void filtros(String valor, ObservableList<Aluno> listaAluno) {

        FilteredList<Aluno> dadosFiltrados = new FilteredList<>(listaAluno, aluno -> true);
        dadosFiltrados.setPredicate(aluno -> {
            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (aluno.getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }
            return false; //To change body of generated lambdas, choose Tools | Templates.
        });

        SortedList<Aluno> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().get();

        listViewAluno.setItems(dadosOrdenados);
    }
    
    //Filtrar dados de matricula
    private void filtro(String valor, ObservableList<Matricula> listaMatriculas) {

        FilteredList<Matricula> dadosFiltrados = new FilteredList<>(listaMatriculas, inscricao -> true);
        dadosFiltrados.setPredicate(inscrito -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (inscrito.getAluno().getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (inscrito.getData().toString().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (inscrito.getCursoPretendido().getCurso().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }
            return false;
        });

        SortedList<Matricula> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbInscricao.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de inscritos encontrados");

        tbInscricao.setItems(dadosOrdenados);
    }

    @FXML
    private void telaCadastro(ActionEvent event) {
        configTela("Matricula/Inscrição", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampos();
    }

    @FXML
    private void telaEdicao(ActionEvent event) {
        configTela("Matricula/Inscrição", "Quantidade de inscrição encontrados", 1);
        Modulo.visualizacao(true, telaCadastro, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    private void telaExcluir(ActionEvent event) {
        configTela("Matricula/Inscrição", "Quantidade de inscrição encontrados", 2);
        Modulo.visualizacao(true, telaCadastro, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    private void imprimirListaDeAlunoInscrito(ActionEvent event) {

    }

    @FXML
    private void telaAtualizar(ActionEvent event) {
        sincronizarBase();
    }

    @FXML
    private void add(ActionEvent event) {

    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            Matricula dadosDeInscricao = tbInscricao.getSelectionModel().getSelectedItem();
            dadosDeInscricao.getClass();
            telaCadastro(null);

            dataInscricao.setValue(dadosDeInscricao.getData());
            txtAluno.setText(dadosDeInscricao.getAluno().getNome());
            cbCurso.setValue(dadosDeInscricao.getCursoPretendido());
            cbTurma.setValue(dadosDeInscricao.getTurma());
            cbPeriodo.setValue(dadosDeInscricao.getPeriodo());
            txtDescricao.setText(dadosDeInscricao.getObservacao());

            lbTitulo.setText("Editar Inscrição/Matricula");
            menu.selectToggle(menu.getToggles().get(1));

            idInscricao = (int) dadosDeInscricao.getId();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione uma inscrição na tabela para atualizar os seus dadoss!");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        try {
            Matricula inscricao = tbInscricao.getSelectionModel().getSelectedItem();
            Dialogo.Resposta response = Mensagem.confirmar("Excluir inscricao/matricula " + "?");

            if (response == Dialogo.Resposta.YES) {
                DAOFactory.daoFactury().matriculaDAO().delete(inscricao.getId());
                sincronizarBase();
                tabela();
            }

            tbInscricao.getSelectionModel().clearSelection();
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione uma inscrição na tabela para remoção!");
        }
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean emptyFields = checkEmptyFields(cbCurso, cbPeriodo, cbTurma, txtAluno, dataInscricao);

        Aluno aluno = listViewAluno.getSelectionModel().getSelectedItem();
        if (aluno != null) {
            aluno.setIdAluno(aluno.getIdAluno());
        }
        LocalDate dataInsc = dataInscricao.getValue();
        Curso curso = cbCurso.getValue();
        String turma = cbTurma.getValue();
        String periodo = cbPeriodo.getValue();
        String observacao = txtDescricao.getText();

        if (emptyFields) {
            Matricula inscricao = new Matricula((long) idInscricao, dataInsc, aluno, curso, turma, periodo, observacao);
            if (idInscricao == 0) {
                DAOFactory.daoFactury().matriculaDAO().create(inscricao);
            } else {
                DAOFactory.daoFactury().matriculaDAO().update(inscricao);
            }
            telaCadastro(null);
            sincronizarBase();
            tabela();
        }

    }
}
