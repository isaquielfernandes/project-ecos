package cv.com.escola.model.entity;

import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Isaquiel Fernandes
 */
public class Categoria {
    
    private IntegerProperty id;
    private StringProperty nome;
    private StringProperty descricao;

    public Categoria(int id, String nome, String descricao) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.descricao = new SimpleStringProperty(descricao);
    }

    public Categoria(String nome) {
        this.nome = new SimpleStringProperty(nome);
    }

    public Categoria(int id, String nome) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
    }
 
    public Categoria() {
    }

    public int getId() { return idCategoriaProperty().get();}
    public void setIdCategoria(int id) { idCategoriaProperty().set(id);}
    public IntegerProperty idCategoriaProperty() {
         if (id == null) id = new SimpleIntegerProperty(this, "id");
         return id;
    }
    
    public String getNome() { return nomeProperty().get();}
    public void setNome(String nome) { nomeProperty().set(nome);}
    public StringProperty nomeProperty() {
         if (nome == null) nome = new SimpleStringProperty(this, "nome");
         return nome;
    }
    
    public String getDescricao() { return descricaoProperty().get();}
    public void setDescricao(String descricao) { descricaoProperty().set(descricao);}
    public StringProperty descricaoProperty() {
         if (descricao == null) descricao = new SimpleStringProperty(this, "descricao");
         return descricao;
    }

    @Override
    public String toString() {
        return  nome.get();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.nome);
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
        final Categoria other = (Categoria) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.nome, other.nome);
    }
}
