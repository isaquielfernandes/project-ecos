
package cv.com.escola.model.entity;

import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InspecaoTecnica {

    private LongProperty id;
    private ObjectProperty<Veiculo> veiculo;
    private StringProperty tipoDeInspeccao;
    private ObjectProperty<LocalDate> dataDeInspeccao;
    private StringProperty resultado;
    private IntegerProperty mesDeDuracao;
    private StringProperty validade;

    public InspecaoTecnica() {
    }

    public InspecaoTecnica(long id, String tipoDeInspeccao, String resultado, LocalDate dataDeInspeccao, Veiculo veiculo, Integer mesDeDuracao, String validade) {
        this.id = new SimpleLongProperty(id);
        this.tipoDeInspeccao = new SimpleStringProperty(tipoDeInspeccao);
        this.resultado = new SimpleStringProperty(resultado);
        this.dataDeInspeccao = new SimpleObjectProperty(dataDeInspeccao);
        this.veiculo = new SimpleObjectProperty(veiculo);
        this.mesDeDuracao = new SimpleIntegerProperty(mesDeDuracao);
        this.validade = new SimpleStringProperty(validade);
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

    public final String getTipoDeInspeccao() {
        return tipoDeInspeccao.get();
    }

    public final void setTipoDeInspeccao(String value) {
        tipoDeInspeccao.set(value);
    }

    public StringProperty tipoDeInspeccaoProperty() {
        return tipoDeInspeccao;
    }

    public final String getResultado() {
        return resultado.get();
    }

    public final void setResultado(String value) {
        resultado.set(value);
    }

    public StringProperty resultadoProperty() {
        return resultado;
    }

    public final LocalDate getDataDeInspeccao() {
        return dataDeInspeccao.get();
    }

    public final void setDataDeInspeccao(LocalDate value) {
        dataDeInspeccao.set(value);
    }

    public ObjectProperty<LocalDate> dataDeInspeccaoProperty() {
        return dataDeInspeccao;
    }

    public final int getMesDeDuracao() {
        return mesDeDuracao.get();
    }

    public final void setMesDeDuracao(int value) {
        mesDeDuracao.set(value);
    }

    public IntegerProperty mesDeDuracaoProperty() {
        return mesDeDuracao;
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

    public final String getValidade() {
        return validade.get();
    }

    public final void setValidade(String value) {
        validade.set(value);
    }

    public StringProperty validadeProperty() {
        return validade;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final InspecaoTecnica other = (InspecaoTecnica) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
