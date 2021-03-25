package cv.com.escola.model.entity;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;


public class Empresa {

    private IntegerProperty idEscola;
    private StringProperty nome;
    private StringProperty cidade;
    private StringProperty endereco;
    private StringProperty nif;
    private StringProperty contato;
    private StringProperty email;
    private StringProperty descricao;
    private ObjectProperty<LocalDate> anoVigencia;
    private File fileLogo;
    private File fileCarimboAssinatura;
    private Blob logo;
    private Blob assinatura;
    public FileInputStream fileInputStreamLogo;
    public FileInputStream fileInputStreamAssinatura;
    private Image imageLogo;
    private Image imageAssinatura;

    public Empresa() {
    }
    
    public Empresa(String nome, String cidade, String nif, String endereco, String email, String contato, String descricao) {
        this.nome = new SimpleStringProperty(nome);
        this.cidade = new SimpleStringProperty(cidade);
        this.nif = new SimpleStringProperty(nif);
        this.endereco = new SimpleStringProperty(endereco);
        this.email = new SimpleStringProperty(email);
        this.contato = new SimpleStringProperty(contato);
        this.descricao = new SimpleStringProperty(descricao);
    }
    
    public Empresa(int idEscola, String nome, String cidade, String nif, String endereco, String email, String contato, String descricao) {
        this.idEscola = new SimpleIntegerProperty(idEscola);
        this.nome = new SimpleStringProperty(nome);
        this.cidade = new SimpleStringProperty(cidade);
        this.nif = new SimpleStringProperty(nif);
        this.endereco = new SimpleStringProperty(endereco);
        this.email = new SimpleStringProperty(email);
        this.contato = new SimpleStringProperty(contato);
        this.descricao = new SimpleStringProperty(descricao);
    }
    
    public Empresa(int idEscola, String nomeEscola, String cidade, String endereco, String nif, String contato, String email, String descricao, File file) {
        this.idEscola = new SimpleIntegerProperty(idEscola);
        this.nome = new SimpleStringProperty(nomeEscola);
        this.cidade = new SimpleStringProperty(cidade);
        this.nif = new SimpleStringProperty(nif);
        this.endereco = new SimpleStringProperty(endereco);
        this.contato = new SimpleStringProperty(contato);
        this.email = new SimpleStringProperty(email);
        this.descricao = new SimpleStringProperty(descricao);
        this.fileLogo = file;
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
    public Blob getLogo() {
        return logo;
    }

    public void setLogo(Blob logo) {
        this.logo = logo;
    }

    public FileInputStream getFileInputStreamLogo() {
        return fileInputStreamLogo;
    }

    public void setFileInputStreamLogo(FileInputStream fileInputStreamLogo) {
        this.fileInputStreamLogo = fileInputStreamLogo;
    }
    
    public File getFileLogo() {
        return fileLogo;
    }

    public void setFileLogo(File fileLogo) {
        this.fileLogo = fileLogo;
    }
    
    public File getFileCarimboAssinatura() {
        return fileCarimboAssinatura;
    }

    public void setFileCarimboAssinatura(File fileCarimboAssinatura) {
        this.fileCarimboAssinatura = fileCarimboAssinatura;
    }
    
    public Image getImageLogo() {
        return imageLogo;
    }

    public void setImageLogo(Image imageLogo) {
        this.imageLogo = imageLogo;
    }

    public Image getImageAssinatura() {
        return imageAssinatura;
    }

    public void setImageAssinatura(Image imageAssinatura) {
        this.imageAssinatura = imageAssinatura;
    }

    public Blob getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(Blob assinatura) {
        this.assinatura = assinatura;
    }

    public FileInputStream getFileInputStreamAssinatura() {
        return fileInputStreamAssinatura;
    }

    public void setFileInputStreamAssinatura(FileInputStream fileInputStreamAssinatura) {
        this.fileInputStreamAssinatura = fileInputStreamAssinatura;
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
        final Empresa other = (Empresa) obj;
        if (!Objects.equals(this.idEscola, other.idEscola)) {
            return false;
        }
        if (!Objects.equals(this.nif, other.nif)) {
            return false;
        }
        return true;
    }

}
