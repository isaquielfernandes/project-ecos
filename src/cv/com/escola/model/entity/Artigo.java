package cv.com.escola.model.entity;

import java.math.BigDecimal;
import java.util.Objects;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.data.annotation.Id;


public class Artigo {
    
    @Id
    private LongProperty idArtigo;
    private StringProperty nomeArtigo;
    private ObjectProperty<BigDecimal> preco;
    private StringProperty descricao;

    public Artigo() {
    }

    public Artigo(Long idArtigo, String nomeArtigo, BigDecimal preco) {
        this.idArtigo = new SimpleLongProperty(idArtigo);
        this.nomeArtigo = new SimpleStringProperty(nomeArtigo);
        this.preco = new SimpleObjectProperty(preco);
    }

    public Artigo(Long idArtigo, String nomeArtigo, BigDecimal preco, String descricao) {
        this.idArtigo = new SimpleLongProperty(idArtigo);
        this.nomeArtigo = new SimpleStringProperty(nomeArtigo);
        this.preco = new SimpleObjectProperty(preco);
        this.descricao = new SimpleStringProperty(descricao);
    }

    public long getIdArtigo() {
        return idArtigoProperty().get();
    }

    public void setIdArtigo(long value) {
        idArtigoProperty().set(value);
    }

    public LongProperty idArtigoProperty() {
        if(idArtigo == null) idArtigo = new SimpleLongProperty(this, "idArtigo");
        return idArtigo;
    }

    public final String getNomeArtigo() {
        return nomeArtigoProperty().get();
    }

    public void setNomeArtigo(String value) {
        nomeArtigoProperty().set(value);
    }

    public StringProperty nomeArtigoProperty() {
        if(nomeArtigo == null) nomeArtigo = new SimpleStringProperty(this, "nomeArtigo");
        return nomeArtigo;
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
        hash = 53 * hash + Objects.hashCode(this.idArtigo);
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
        return Objects.equals(this.idArtigo, other.idArtigo);
    }

    @Override
    public String toString() {
        return nomeArtigo.get();
    }
}
