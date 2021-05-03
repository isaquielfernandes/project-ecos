package cv.com.escola.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.data.annotation.Id;


public class Artigo implements Serializable {
    
    @Id
    private LongProperty id;
    private StringProperty nome;
    private ObjectProperty<BigDecimal> preco;
    private StringProperty descricao;

    public Artigo() {
        super();
    }

    public Artigo(Long id, String nome, BigDecimal preco) {
        this.id = new SimpleLongProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.preco = new SimpleObjectProperty(preco);
    }

    public Artigo(Long id, String nome, BigDecimal preco, String descricao) {
        this.id = new SimpleLongProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.preco = new SimpleObjectProperty(preco);
        this.descricao = new SimpleStringProperty(descricao);
    }

    public long getId() {
        return idProperty().get();
    }

    public void setId(long value) {
        idProperty().set(value);
    }

    public LongProperty idProperty() {
        if(id == null) id = new SimpleLongProperty(this, "id");
        return id;
    }

    public final String getNome() {
        return nomeArtigoProperty().get();
    }

    public void setNome(String value) {
        nomeArtigoProperty().set(value);
    }

    public StringProperty nomeArtigoProperty() {
        if(nome == null) nome = new SimpleStringProperty(this, "nome");
        return nome;
    }

    public BigDecimal getPreco() {
        return precoProperty().get();
    }

    public void setPreco(BigDecimal value) {
        precoProperty().set(value);
    }

    public ObjectProperty<BigDecimal> precoProperty() {
        if(preco == null) preco = new SimpleObjectProperty(this, "preco");
        return preco;
    }

    public String getDescricao() {
        return descricaoProperty().get();
    }

    public void setDescricao(String value) {
        descricaoProperty().set(value);
    }

    public StringProperty descricaoProperty() {
        if(descricao == null) descricao = new SimpleStringProperty(this, "descricao");
        return descricao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Artigo other = (Artigo) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return nome.get();
    }
}
