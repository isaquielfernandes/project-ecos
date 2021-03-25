package cv.com.escola.model.entity;

import java.math.BigDecimal;
import java.util.Objects;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Artigo {
    
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

    public final long getIdArtigo() {
        return idArtigo.get();
    }

    public final void setIdArtigo(long value) {
        idArtigo.set(value);
    }

    public LongProperty idArtigoProperty() {
        return idArtigo;
    }

    public final String getNomeArtigo() {
        return nomeArtigo.get();
    }

    public final void setNomeArtigo(String value) {
        nomeArtigo.set(value);
    }

    public StringProperty nomeArtigoProperty() {
        return nomeArtigo;
    }

    public final BigDecimal getPreco() {
        return preco.get();
    }

    public final void setPreco(BigDecimal value) {
        preco.set(value);
    }

    public ObjectProperty<BigDecimal> precoProperty() {
        return preco;
    }

    public final String getDescricao() {
        return descricao.get();
    }

    public final void setDescricao(String value) {
        descricao.set(value);
    }

    public StringProperty descricaoProperty() {
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
        if (!Objects.equals(this.idArtigo, other.idArtigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nomeArtigo.get();
    }
}
