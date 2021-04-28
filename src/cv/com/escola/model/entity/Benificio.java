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
public class Benificio {
    private IntegerProperty idBenificio;
    private StringProperty nomeBenificio;
    private StringProperty descricao;

    public Benificio(int id, String nomeBenificio, String descricao) {
        this.idBenificio = new SimpleIntegerProperty(id);
        this.nomeBenificio = new SimpleStringProperty(nomeBenificio);
        this.descricao = new SimpleStringProperty(descricao);
    }

    public Benificio(int id, String nomeBenificio) {
        this.idBenificio = new SimpleIntegerProperty(id);
        this.nomeBenificio = new SimpleStringProperty(nomeBenificio);
    }
    

    public Benificio(String nomeBenificio) {
        this.nomeBenificio = new SimpleStringProperty(nomeBenificio);
    }

    public int getIdBenificio() { return idBenificioProperty().get(); }
    public void setIdBenificio(int value) { idBenificioProperty().set(value); }
    public IntegerProperty idBenificioProperty() {
         if (idBenificio == null) idBenificio = new SimpleIntegerProperty(this, "idBenificio");
         return idBenificio;
    }

    public String getNomeBenificio() { return nomeBenificioProperty().get(); }
    public void setNomeBenificio(String value) { nomeBenificioProperty().set(value); }
    public StringProperty nomeBenificioProperty() {
         if (nomeBenificio == null) nomeBenificio = new SimpleStringProperty(this, "nomeBenificio");
         return nomeBenificio;
    }
    
    public String getDescricao() { return descricaoProperty().get(); }
    public void setDescricao(String descricao) { descricaoProperty().set(descricao); }
    public StringProperty descricaoProperty() {
         if (descricao == null) descricao = new SimpleStringProperty(this, "descricao");
         return descricao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idBenificio);
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
        final Benificio other = (Benificio) obj;
        return Objects.equals(this.idBenificio, other.idBenificio);
    }
    
    @Override
    public String toString() {
        return nomeBenificio.get();
    }
    
}
