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

import cv.com.escola.app.App;
import cv.com.escola.app.Configuracao;
import cv.com.escola.model.dao.db.DAOFactory;
import cv.com.escola.model.entity.Empresa;
import cv.com.escola.model.util.Campo;
import cv.com.escola.model.util.Mensagem;
import static cv.com.escola.model.util.ValidationFields.checkEmptyFields;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Isaquiel Fernandes
 */
public class ConfiguracaoController implements Initializable {

    private List<Empresa> listaEmpresa;
    private int idEmpresa = 0;
    private Image image;
    private File file;
    private File fileLogo;
    private File fileAssinatura;
    private FileOutputStream fileOutput;
    private FileInputStream fileInput;
    private byte[] userImage;
    private String imgPath;

    //@FXML
    private ToggleGroup menu;
    @FXML
    private Label lbTitulo;
    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextField txtCidade;
    @FXML
    private TextField txtNomeEscola;
    @FXML
    private TextField txtEndereco;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtContato;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private TextField txtNif;
    @FXML
    private ImageView imageViewBarnner;
    @FXML
    private ImageView imageViewAssinatura;
    @FXML
    private TextField txtID;
    @FXML
    private Button btSalvar;
    @FXML
    private Label legenda;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Grupo.notEmpty(menu);
        sincronizarBase();
        //txtID.setText(String.valueOf(idEmpresa));

        imageViewBarnner.setOnMouseClicked((event) -> {
            uploadLogo();
        });
        
        imageViewAssinatura.setOnMouseClicked((event) -> {
            uploadFileAssinatura();
        });
    }

    public void uploadLogo(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fotografia", "*.png", "*.jpg"));

        file = fileChooser.showOpenDialog(null);

        if (file != null) {
            if (file.length() < 6000000) {
                try {
                    fileInput = new FileInputStream(file);
                    image = new Image(file.getAbsoluteFile().toURI().toString(), imageViewBarnner.getFitWidth(), imageViewBarnner.getFitHeight(), true, true);
                    imageViewBarnner.setImage(image);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //fileInput = newFileInputStream(new Image());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Permiss");
                alert.setHeaderText("Permission denied");
                alert.setContentText("Your Image file is too big to upload \n please choise another image");
                alert.initStyle(StageStyle.UNDECORATED);

            }

        }
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaEmpresa = DAOFactory.daoFactury().empresaDAO().findAll();
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limparCampos() {
        Campo.limpar(txtEndereco, txtNomeEscola, txtCidade, txtEmail, txtNif, txtContato);
        Campo.limpar(txtDescricao);
        imageViewBarnner.setImage(null);
    }

    @FXML
    private void salvar(ActionEvent event) {
        boolean vazio = checkEmptyFields(txtCidade, txtNomeEscola, txtNif, txtEmail, txtContato, txtEndereco);

        String cidade = txtCidade.getText();
        String nomeEscola = txtNomeEscola.getText();
        String nif = txtNif.getText();
        String endereco = txtEndereco.getText();
        String email = txtEmail.getText();
        String contato = txtContato.getText();
        String descricao = txtDescricao.getText();
        idEmpresa = Integer.valueOf(txtID.getText().trim().isEmpty() ? "0" : txtID.getText());

        if (vazio) {
            Empresa empresa = new Empresa(idEmpresa, nomeEscola, cidade, endereco, nif, contato, email, descricao, fileLogo);
            empresa.setFileCarimboAssinatura(fileAssinatura);
            
            DAOFactory.daoFactury().empresaDAO().create(empresa);

            sincronizarBase();
            new App().start(new Stage());
            Configuracao.palco.close();
        }
    }
    
    public void uploadFileAssinatura(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fotografia", "*.png", "*.jpg"));

        fileAssinatura = fileChooser.showOpenDialog(null);

        if (fileAssinatura != null) {
            if (fileAssinatura.length() < 6000000) {
                try {
                    fileInput = new FileInputStream(fileAssinatura);
                    image = new Image(fileAssinatura.getAbsoluteFile().toURI().toString(), imageViewAssinatura.getFitWidth(), imageViewAssinatura.getFitHeight(), true, true);
                    imageViewAssinatura.setImage(image);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ConfiguracaoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Mensagem.info("Permissão", "Sua imagem file é muito grande para upload \n Por favor escolha outra image");
            }

        }
    }
}
