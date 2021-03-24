package cv.com.escola.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Logger;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Isaquiel Fernandes
 */
public class Seguro {

    private LongProperty id;
    private StringProperty compania;
    private StringProperty cobertura;
    private ObjectProperty<BigDecimal> valorFranquia;
    private ObjectProperty<Veiculo> veiculo;
    private ObjectProperty<LocalDate> deste;
    private ObjectProperty<LocalDate> validade;
    private ObjectProperty<LocalDate> emissao;
    
    private ObservableList<Seguro> seguro = FXCollections.observableArrayList();
    private static final Logger LOG = Logger.getLogger(Seguro.class.getName());

    public Seguro() {
    }
    
    public Seguro(Long id, String compania, Veiculo veiculo, LocalDate deste, LocalDate validade, LocalDate emissao) {
        this.id = new SimpleLongProperty(id);
        this.compania = new SimpleStringProperty(compania);
        this.veiculo = new SimpleObjectProperty(veiculo);
        this.deste = new SimpleObjectProperty(deste);
        this.validade = new SimpleObjectProperty(validade);
        this.emissao = new SimpleObjectProperty(emissao);
    }

    public final long getId() {
        return id.get();
    }

    public final void setId(long value) {
        id.set(value);
    }

    public LongProperty idProperty() {
        return id;
    }

    public final String getCompania() {
        return compania.get();
    }

    public final void setCompania(String value) {
        compania.set(value);
    }

    public StringProperty companiaProperty() {
        return compania;
    }

    public final String getCobertura() {
        return cobertura.get();
    }

    public final void setCobertura(String value) {
        cobertura.set(value);
    }

    public StringProperty coberturaProperty() {
        return cobertura;
    }

    public final BigDecimal getValorFranquia() {
        return valorFranquia.get();
    }

    public final void setValorFranquia(BigDecimal value) {
        valorFranquia.set(value);
    }

    public ObjectProperty<BigDecimal> valorFranquiaProperty() {
        return valorFranquia;
    }

    public final Veiculo getVeiculo() {
        return veiculo.get();
    }

    public final void setVeiculo(Veiculo value) {
        veiculo.set(value);
    }

    public ObjectProperty<Veiculo> veiculoProperty() {
        return veiculo;
    }

    public final LocalDate getDeste() {
        return deste.get();
    }

    public final void setDeste(LocalDate value) {
        deste.set(value);
    }

    public ObjectProperty<LocalDate> desteProperty() {
        return deste;
    }

    public final LocalDate getValidade() {
        return validade.get();
    }

    public final void setValidade(LocalDate value) {
        validade.set(value);
    }

    public ObjectProperty<LocalDate> validadeProperty() {
        return validade;
    }

    public final LocalDate getEmissao() {
        return emissao.get();
    }

    public final void setEmissao(LocalDate value) {
        emissao.set(value);
    }

    public ObjectProperty<LocalDate> emissaoProperty() {
        return emissao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final Seguro other = (Seguro) obj;
        return Objects.equals(this.id, other.id);
    }
    
    
}
