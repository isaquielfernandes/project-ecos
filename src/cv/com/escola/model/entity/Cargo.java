/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv.com.escola.model.entity;

import java.util.Objects;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Isaquiel Fernandes
 */
public class Cargo {
    private LongProperty idCargo;
    public void setIdCargo(Long value){ idCargoProperty().set(value);}
    public Long getIdCargo() { return idCargoProperty().get(); }
    public LongProperty idCargoProperty() {
        if (idCargo == null) idCargo = new SimpleLongProperty(this, "idCargo");
        return idCargo;
     }
    
    private StringProperty nomeCargo;
    public void setNomeCargo(String value) { nomeCargoProperty().set(value); }
    public String getNomeCargo() { return nomeCargoProperty().get(); }
    public StringProperty nomeCargoProperty() {
        if (nomeCargo == null) nomeCargo = new SimpleStringProperty(this, "nomeCargo");
        return nomeCargo;
     }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.idCargo);
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
        final Cargo other = (Cargo) obj;
        return Objects.equals(this.idCargo, other.idCargo);
    }

    public Cargo() {
    }

    public Cargo(long idCargo, String nomeCargo) {
        this.idCargo = new SimpleLongProperty(idCargo);
        this.nomeCargo = new SimpleStringProperty(nomeCargo);
    }
    
}
