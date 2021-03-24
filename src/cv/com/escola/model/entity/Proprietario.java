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
public class Proprietario {

    private LongProperty codigoPropretario;
    public void setCdigoPropretario(Long value){ codigoPropretarioProperty().set(value);}
    public Long getCdigoPropretario() { return codigoPropretarioProperty().get(); }
    public LongProperty codigoPropretarioProperty() {
        if (codigoPropretario == null) codigoPropretario = new SimpleLongProperty(this, "codigoPropretario");
        return codigoPropretario;
     }
    
    private StringProperty nomePropretario;
    public void setNomePropretario(String value) { nomePropretarioProperty().set(value); }
    public String getNomePropretario() { return nomePropretarioProperty().get(); }
    public StringProperty nomePropretarioProperty() {
        if (nomePropretario == null) nomePropretario = new SimpleStringProperty(this, "nomePropretario");
        return nomePropretario;
    }
    
    private StringProperty telefonePropretario;
    public void setTelefonePropretario(String value) { telefonePropretarioProperty().set(value); }
    public String getTelefonePropretario() { return telefonePropretarioProperty().get(); }
    public StringProperty telefonePropretarioProperty() {
        if (telefonePropretario == null) telefonePropretario = new SimpleStringProperty(this, "telefonePropretario");
        return telefonePropretario;
    }

    private StringProperty emailPropertario;
    public void setEmailPropertario(String value) { emailPropertarioProperty().set(value); }
    public String getEmailPropertario() { return emailPropertarioProperty().get(); }
    public StringProperty emailPropertarioProperty() {
        if (emailPropertario == null) emailPropertario = new SimpleStringProperty(this, "emailPropertario");
        return emailPropertario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.codigoPropretario);
        return hash;
    }

    public Proprietario() {
    }

    public Proprietario(String nomePropretario) {
        this.nomePropretario = new SimpleStringProperty(nomePropretario);
    }
    public Proprietario(long codigoPropretario, String nomePropretario, String telefonePropretario, String emailPropertario) {
        this.codigoPropretario = new SimpleLongProperty(codigoPropretario);
        this.nomePropretario = new SimpleStringProperty(nomePropretario);
        this.telefonePropretario = new SimpleStringProperty(telefonePropretario);
        this.emailPropertario = new SimpleStringProperty(emailPropertario);
    }
    public Proprietario( String nomePropretario, String telefonePropretario, String emailPropertario) {
        this.nomePropretario = new SimpleStringProperty(nomePropretario);
        this.telefonePropretario = new SimpleStringProperty(telefonePropretario);
        this.emailPropertario = new SimpleStringProperty(emailPropertario);
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
        final Proprietario other = (Proprietario) obj;
        return Objects.equals(this.codigoPropretario, other.codigoPropretario);
    }

    
}
