package cv.com.escola.model.entity;

import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;


public class Cliente {
    
    private IntegerProperty idCliente;
    public Integer getIdCliente() { return idClienteProperty().get(); }
    public void setIdCliente(int value) { idClienteProperty().set(value);}
    public IntegerProperty idClienteProperty(){
        if(idCliente == null) idCliente = new SimpleIntegerProperty(this, "idCliente");
        return idCliente;
    }
    
    private StringProperty idDoCliente;
    public String getIdDoCliente() { return idDoClienteProperty().get(); }
    public void setIdDoClienteNomeCliente(String value) { idDoClienteProperty().set(value); }
    public StringProperty idDoClienteProperty(){
        if(idDoCliente == null) idDoCliente = new SimpleStringProperty(this, "idDoCliente");
        return idDoCliente;
    }
    
    private StringProperty nomeCliente;
    public String getNomeCliente() { return nomeClienteProperty().get(); }
    public void setNomeCliente(String value) { nomeClienteProperty().set(value); }
    public StringProperty nomeClienteProperty(){
        if(nomeCliente == null) nomeCliente = new SimpleStringProperty(this, "nomeCliente");
        return nomeCliente;
    }
    
    private StringProperty nif;
    public String getNif() { return numCNIProperty().get(); }
    public void setNif(String value) { numCNIProperty().set(value); }
    public StringProperty numCNIProperty(){
        if(nif == null) nif = new SimpleStringProperty(this, "nif");
        return nif;
    }
    
    private StringProperty contato;
    public String getContato() { return contatoProperty().get(); }
    public void setContato(String value) { contatoProperty().set(value); }
    public StringProperty contatoProperty(){
        if(contato == null) contato = new SimpleStringProperty(this, "contato");
        return contato;
    }
    
    private StringProperty tipoCliente;
    public String getTipoCliente() { return tipoClienteProperty().get(); }
    public void setTipoCliente(String value) { tipoClienteProperty().set(value); }
    public StringProperty tipoClienteProperty(){
        if(tipoCliente == null) tipoCliente = new SimpleStringProperty(this, "tipoCliente");
        return tipoCliente;
    }
    
    private StringProperty descricao;
    public String getDescricao() { return descricaoProperty().get(); }
    public void setDescricao(String value) { descricaoProperty().set(value); }
    public StringProperty descricaoProperty(){
        if(descricao == null) descricao = new SimpleStringProperty(this, "descricao");
        return descricao;
    }
    private StringProperty endereco;
    public String getEndereco() { return enderecoProperty().get(); }
    public void setEndereco(String value) { enderecoProperty().set(value); }
    public StringProperty enderecoProperty(){
        if(endereco == null) endereco = new SimpleStringProperty(this, "descricao");
        return endereco;
    }
    
    private StringProperty codigoPostal;
    public String getCodigoPostal() { return codigoPostalProperty().get(); }
    public void setCodigoPostal(String value) { codigoPostalProperty().set(value); }
    public StringProperty codigoPostalProperty(){
        if(codigoPostal == null) codigoPostal = new SimpleStringProperty(this, "codigoPostal");
        return codigoPostal;
    }
    
    private StringProperty localidade;
    public String getLocalidade() { return localidadeProperty().get(); }
    public void setLocalidade(String value) { localidadeProperty().set(value); }
    public StringProperty localidadeProperty(){
        if(localidade == null) localidade = new SimpleStringProperty(this, "localidade");
        return localidade;
    }
    
    private Button remove;
    public Button getRemove() {
        return remove;
    }
    public void setRemove(Button remove) {
        this.remove = remove;
    }
    
    private Button editar;
    public Button getEditar() {
        return editar;
    }
    public void setEditar(Button editar) {
        this.editar = editar;
    }
    
    public Cliente() {
    }

    public Cliente(Integer idCliente, String nomeCliente, String nif) {
        this.idCliente = new SimpleIntegerProperty(idCliente);
        this.nomeCliente = new SimpleStringProperty(nomeCliente);
        this.nif = new SimpleStringProperty(nif);
        this.remove = new Button("Remover");
        remove.setStyle("-fx-background-color: #F44336; -fx-text-fill: #FFF;");
        remove.setPrefSize(100, 20);
        this.editar = new Button("Editar");
        editar.setStyle("-fx-background-color: #388E3C; -fx-text-fill: #fff;");
        editar.setPrefSize(100, 20);
    }
    
    public Cliente(Integer idCliente, String nomeCliente, String cni, String contato, String tipoCliente, String descricao) {
        this.idCliente = new SimpleIntegerProperty(idCliente);
        this.nomeCliente = new SimpleStringProperty(nomeCliente);
        this.nif = new SimpleStringProperty(cni);
        this.contato = new SimpleStringProperty(contato);
        this.tipoCliente = new SimpleStringProperty(tipoCliente);
        this.descricao = new SimpleStringProperty(descricao);
        this.remove = new Button("Remover");
        remove.setStyle("-fx-background-color: #F44336; -fx-text-fill: #FFF;");
        editar.setPrefSize(100, 20);
    }

    public Cliente(Integer idCliente, String nomeCliente, String nif, String contato, String tipoCliente, String descricao, String endereco, String codigoPostal, String localidade) {
        this.idCliente = new SimpleIntegerProperty(idCliente);
        this.nomeCliente = new SimpleStringProperty(nomeCliente);
        this.nif = new SimpleStringProperty(nif);
        this.contato = new SimpleStringProperty(contato);
        this.tipoCliente = new SimpleStringProperty(tipoCliente);
        this.descricao = new SimpleStringProperty(descricao);
        this.endereco = new SimpleStringProperty(endereco);
        this.codigoPostal = new SimpleStringProperty(codigoPostal);
        this.localidade = new SimpleStringProperty(localidade);
        this.remove = new Button("Remover");
        remove.setStyle("-fx-background-color: #F44336; -fx-text-fill: #FFF;");
        remove.setPrefSize(100, 20);
        this.editar = new Button("Editar");
        editar.setStyle("-fx-background-color: #388E3C; -fx-text-fill: #fff;");
        editar.setPrefSize(100, 20);
        
    }
    public Cliente(Integer idCliente, String nomeCliente, String nif, String contato, String tipoCliente, String endereco, String codigoPostal, String localidade) {
        this.idCliente = new SimpleIntegerProperty(idCliente);
        this.nomeCliente = new SimpleStringProperty(nomeCliente);
        this.nif = new SimpleStringProperty(nif);
        this.contato = new SimpleStringProperty(contato);
        this.tipoCliente = new SimpleStringProperty(tipoCliente);
        this.endereco = new SimpleStringProperty(endereco);
        this.codigoPostal = new SimpleStringProperty(codigoPostal);
        this.localidade = new SimpleStringProperty(localidade);
        this.remove = new Button("Remover");
        remove.setStyle("-fx-background-color: #F44336; -fx-text-fill: #FFF;");
        remove.setPrefSize(100, 20);
        this.editar = new Button("Editar");
        editar.setStyle("-fx-background-color: #388E3C; -fx-text-fill: #fff;");
        editar.setPrefSize(100, 20);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.idCliente);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.idCliente, other.idCliente)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nomeCliente.get();
    }
    
    
}
