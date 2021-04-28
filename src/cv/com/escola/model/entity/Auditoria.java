package cv.com.escola.model.entity;

import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Auditoria {

    private IntegerProperty id;
    public void setId(Integer value) { idProperty().set(value); }
     public Integer getId() { return idProperty().get(); }
     public IntegerProperty idProperty() {
         if (id == null) id = new SimpleIntegerProperty(this, "id");
         return id;
     }
    private StringProperty acao;
    public void setAcao(String value) { acaoProperty().set(value); }
     public String getAcao() { return acaoProperty().get(); }
     public StringProperty acaoProperty() {
         if (acao == null) acao = new SimpleStringProperty(this, "acao");
         return acao;
     }
    private StringProperty descricao;
    public void setDescricao(String value) { descricaoProperty().set(value); }
    public String getDescricao() { return descricaoProperty().get(); }
    public StringProperty descricaoProperty() {
        if (descricao == null) descricao = new SimpleStringProperty(this, "descricao");
        return descricao;
    }
    private ObjectProperty<LocalDate> data;
    public void setData(LocalDate value) { dataProperty().set(value); }
    public LocalDate getData() { return dataProperty().get(); }
    public ObjectProperty<LocalDate> dataProperty() {
        if (data == null) data = new SimpleObjectProperty(this, "data");
        return data;
    }
    private ObjectProperty<Usuario> user;
    public void setUser(Usuario value) { userProperty().set(value); }
    public Usuario getUser() { return userProperty().get(); }
    public ObjectProperty<Usuario> userProperty() {
        if (user == null) user = new SimpleObjectProperty(this, "user");
        return user;
    }

    public Auditoria(int id, String acao, LocalDate data, String descricao, Usuario user) {
        this.id = new SimpleIntegerProperty(id);
        this.acao = new SimpleStringProperty(acao);
        this.descricao = new SimpleStringProperty(descricao);
        this.data = new SimpleObjectProperty(data);
        this.user = new SimpleObjectProperty(user);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final Auditoria other = (Auditoria) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
