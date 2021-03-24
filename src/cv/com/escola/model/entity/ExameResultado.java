/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv.com.escola.model.entity;


import java.util.Objects;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Isaquiel Fernandes
 */
public class ExameResultado {

    private LongProperty idExameResultado;
    private ObjectProperty<Exame> exame;
    private StringProperty resultado;

    public ExameResultado(long idExameResultado, Exame exame, String resultado) {
        this.idExameResultado = new SimpleLongProperty(idExameResultado);
        this.exame = new SimpleObjectProperty(exame);
        this.resultado = new SimpleStringProperty(resultado);
    }
    public ExameResultado() {
    }

    public final long getIdExameResultado() { return idExameResultado.get();}
    public final void setIdExameResultado(long value) { idExameResultado.set(value);}
    public LongProperty idExameResultadoProperty() {
        return idExameResultado;
    }

    public final Exame getExame() { return exame.get();}
    public final void setExame(Exame value) { exame.set(value);}
    public ObjectProperty<Exame> exameProperty() {
        return exame;
    }

    public final String getResultado() { return resultado.get();}
    public final void setResultado(String value) { resultado.set(value);}
    public StringProperty resultadoProperty() {
        return resultado;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.idExameResultado);
        hash = 19 * hash + Objects.hashCode(this.exame);
        hash = 19 * hash + Objects.hashCode(this.resultado);
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
        final ExameResultado other = (ExameResultado) obj;
        if (!Objects.equals(this.idExameResultado, other.idExameResultado)) {
            return false;
        }
        if (!Objects.equals(this.exame, other.exame)) {
            return false;
        }
        return Objects.equals(this.resultado, other.resultado);
    }
    
    
}
