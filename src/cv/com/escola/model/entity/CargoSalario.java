package cv.com.escola.model.entity;

import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Isaquiel Fernandes
 */
public class CargoSalario {
    
    private IntegerProperty idCargoSalario;
    public void setIdcargoSalario(Integer value){ idCargoSalarioProperty().set(value);}
    public Integer getIdcargoSalario() { return idCargoSalarioProperty().get();}
    public IntegerProperty idCargoSalarioProperty() {
        if (idCargoSalario == null) idCargoSalario = new SimpleIntegerProperty(this, "idCargoSalario");
        return idCargoSalario;
    }
    private StringProperty nomeCargo;
    public void setNomeCargo(String value) { nomeCargoProperty().set(value); }
    public String getNomeCargo() { return nomeCargoProperty().get(); }
    public StringProperty nomeCargoProperty() {
        if (nomeCargo == null) nomeCargo = new SimpleStringProperty(this, "nomeCargo");
        return nomeCargo;
    }
    
    private DoubleProperty salario;
    public void setSalario(double value) { salarioProperty().set(value); }
    public Double getSalario() { return salarioProperty().get(); }
    public DoubleProperty salarioProperty() {
        if (salario == null) salario = new SimpleDoubleProperty(this, "salario");
        return salario;
    }
    
    private StringProperty descricao;
    public void setDescricao(String value) { descricaoProperty().set(value); }
    public String getDescricao() { return descricaoProperty().get(); }
    public StringProperty descricaoProperty() {
        if (descricao == null) descricao = new SimpleStringProperty(this, "descricao");
        return descricao;
     }
    
    public CargoSalario() {
    }
    
    public CargoSalario(int id, String cargo, double salario, String descricao) {
        this.idCargoSalario = new SimpleIntegerProperty(id);
        this.nomeCargo = new SimpleStringProperty(cargo);
        this.salario = new SimpleDoubleProperty(salario);
        this.descricao = new SimpleStringProperty(descricao);
    }

    public CargoSalario(int id, String cargo) {
        this.idCargoSalario = new SimpleIntegerProperty(id);
        this.nomeCargo = new SimpleStringProperty(cargo);
    }
    public CargoSalario(String cargo) {
        this.nomeCargo = new SimpleStringProperty(cargo);
    }

    
    public CargoSalario(String cargo, double salario) {
        this.nomeCargo = new SimpleStringProperty(cargo);
        this.salario = new SimpleDoubleProperty(salario);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.idCargoSalario);
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
        final CargoSalario other = (CargoSalario) obj;
        return Objects.equals(this.idCargoSalario, other.idCargoSalario);
    }
    
    @Override
    public String toString() {
        return nomeCargo.get() ;
    }
    
}
