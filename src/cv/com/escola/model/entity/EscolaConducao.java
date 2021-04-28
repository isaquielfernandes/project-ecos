package cv.com.escola.model.entity;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class EscolaConducao {

    private IntegerProperty idEscola;
    private StringProperty nome;
    private StringProperty cidade;
    private StringProperty endereco;
    private StringProperty nif;
    private StringProperty contato;
    private StringProperty email;
    private StringProperty descricao;
    private InputStream logo;
    private InputStream assinatura;
    private ObjectProperty<LocalDate> anoVigencia;

    public EscolaConducao() {
        
    }
    
    public EscolaConducao(String nome, String cidade, String nif, String endereco, String email, String contato, String descricao) {
        this.nome = new SimpleStringProperty(nome);
        this.cidade = new SimpleStringProperty(cidade);
        this.nif = new SimpleStringProperty(nif);
        this.endereco = new SimpleStringProperty(endereco);
        this.email = new SimpleStringProperty(email);
        this.contato = new SimpleStringProperty(contato);
        this.descricao = new SimpleStringProperty(descricao);
    }
    
    public EscolaConducao(int idEscola, String nome, String cidade, String nif, String endereco, String email, String contato, String descricao) {
        this.idEscola = new SimpleIntegerProperty(idEscola);
        this.nome = new SimpleStringProperty(nome);
        this.cidade = new SimpleStringProperty(cidade);
        this.nif = new SimpleStringProperty(nif);
        this.endereco = new SimpleStringProperty(endereco);
        this.email = new SimpleStringProperty(email);
        this.contato = new SimpleStringProperty(contato);
        this.descricao = new SimpleStringProperty(descricao);
    }

    public final int getIdEmpresa() {
        return idEscola.get();
    }

    public final void setIdEmpresa(int value) {
        idEscola.set(value);
    }

    public IntegerProperty idEmpresaProperty() {
        return idEscola;
    }

    public final String getCidade() {
        return cidade.get();
    }

    public final void setCidade(String value) {
        cidade.set(value);
    }

    public StringProperty cidadeProperty() {
        return cidade;
    }

    public final String getNome() {
        return nome.get();
    }

    public final void setNome(String value) {
        nome.set(value);
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public final String getNif() {
        return nif.get();
    }

    public final void setNif(String value) {
        nif.set(value);
    }

    public StringProperty nifProperty() {
        return nif;
    }

    public final String getEndereco() {
        return endereco.get();
    }

    public final void setEndereco(String value) {
        endereco.set(value);
    }

    public StringProperty enderecoProperty() {
        return endereco;
    }

    public final String getEmail() {
        return email.get();
    }

    public final void setEmail(String value) {
        email.set(value);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public final String getContato() {
        return contato.get();
    }

    public final void setContato(String value) {
        contato.set(value);
    }

    public StringProperty contatoProperty() {
        return contato;
    }

    public final LocalDate getAnoVigencia() {
        return anoVigencia.get();
    }

    public final void setAnoVigencia(LocalDate value) {
        anoVigencia.set(value);
    }

    public ObjectProperty<LocalDate> anoVigenciaProperty() {
        return anoVigencia;
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

    public InputStream getLogo() {
        return logo;
    }

    public void setLogo(InputStream logo) {
        this.logo = logo;
    }

    public InputStream getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(InputStream assinatura) {
        this.assinatura = assinatura;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.idEscola);
        hash = 13 * hash + Objects.hashCode(this.nif);
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
        final EscolaConducao other = (EscolaConducao) obj;
        if (!Objects.equals(this.idEscola, other.idEscola)) {
            return false;
        }
        return Objects.equals(this.nif, other.nif);
    }

}
