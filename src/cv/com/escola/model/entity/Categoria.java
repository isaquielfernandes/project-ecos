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
    private IntegerProperty id_categoria;
    private StringProperty nome;
    private StringProperty descricao;

    public Categoria(int id_categoria, String nome, String descricao) {
        this.id_categoria = new SimpleIntegerProperty(id_categoria);
        this.nome = new SimpleStringProperty(nome);
        this.descricao = new SimpleStringProperty(descricao);
    }

    public Categoria(String nome) {
        this.nome = new SimpleStringProperty(nome);
    }

    public Categoria(int id_categoria, String nome) {
        this.id_categoria = new SimpleIntegerProperty(id_categoria);
        this.nome = new SimpleStringProperty(nome);
    }
 
    public Categoria() {
    }

    public int getId_categoria() { return idCategoriaProperty().get();}
    public void setId_categoria(int id_categoria) { idCategoriaProperty().set(id_categoria);}
    public IntegerProperty idCategoriaProperty() {
         if (id_categoria == null) id_categoria = new SimpleIntegerProperty(this, "id_categoria");
         return id_categoria;
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
        hash = 79 * hash + Objects.hashCode(this.id_categoria);
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
        if (!Objects.equals(this.id_categoria, other.id_categoria)) {
            return false;
        }
        return Objects.equals(this.nome, other.nome);
    }
}
