package cv.com.escola.model.entity;

import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;

/**
 *
 * @author Isaquiel Fernandes
 */
public class Instrutor {
    
    private LongProperty id;
    private StringProperty nome;
    private ObjectProperty<LocalDate> admissao;
    private StringProperty email;
    private StringProperty contactoTelefonico;
    private StringProperty contactoMovel;
    private StringProperty foto;
    private StringProperty nomeDoPai;
    private StringProperty nomeDaMae;
    private StringProperty grauAcademico;
    private StringProperty tipoSanguineo;
    private StringProperty morada;
    private StringProperty cidadeIlha;
    private StringProperty numeroDeIndentificacao;
    private StringProperty naturalidade;
    private StringProperty nacionalidade;
    private ObjectProperty<LocalDate> nascimento;
    private StringProperty cartaConducao;
    private StringProperty banco;
    private StringProperty agencia;
    private StringProperty numDeConta;
    private StringProperty observacao;
    private ImageView imgView;

    public Instrutor() {
    }

    public Instrutor(Long id, String nome, LocalDate admissao, String email, String contactoTelefonico, String contactoMovel, String foto, String nomeDoPai, String nomeDaMae, String grauAcademico, String tipoSanguineo, String morada, String cidadeIlha, String numeroDeIndentificacao, String naturalidade, String nacionalidade, LocalDate nascimento, String cartaConducao, String banco, String agencia, String numDeConta, String obsercacao) {
        this.id = new SimpleLongProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.admissao = new SimpleObjectProperty(admissao);
        this.email = new SimpleStringProperty(email);
        this.contactoTelefonico = new SimpleStringProperty(contactoTelefonico);
        this.contactoMovel = new SimpleStringProperty(contactoMovel);
        this.foto = new SimpleStringProperty(foto);
        this.nomeDoPai = new SimpleStringProperty(nomeDoPai);
        this.nomeDaMae = new SimpleStringProperty(nomeDaMae);
        this.grauAcademico = new SimpleStringProperty(grauAcademico);
        this.tipoSanguineo = new SimpleStringProperty(tipoSanguineo);
        this.morada = new SimpleStringProperty(morada);
        this.cidadeIlha = new SimpleStringProperty(cidadeIlha);
        this.numeroDeIndentificacao = new SimpleStringProperty(numeroDeIndentificacao);
        this.naturalidade = new SimpleStringProperty(naturalidade);
        this.nacionalidade = new SimpleStringProperty(nacionalidade);
        this.nascimento = new SimpleObjectProperty(nascimento);
        this.cartaConducao = new SimpleStringProperty(cartaConducao);
        this.banco = new SimpleStringProperty(banco);
        this.agencia = new SimpleStringProperty(agencia);
        this.numDeConta = new SimpleStringProperty(numDeConta);
        this.observacao = new SimpleStringProperty(obsercacao);
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

    public final String getNome() {
        return nome.get();
    }

    public final void setNome(String value) {
        nome.set(value);
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public final LocalDate getAdmissao() {
        return admissao.get();
    }

    public final void setAdmissao(LocalDate value) {
        admissao.set(value);
    }

    public ObjectProperty<LocalDate> admissaoProperty() {
        return admissao;
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

    public final String getContactoTelefonico() {
        return contactoTelefonico.get();
    }

    public final void setContactoTelefonico(String value) {
        contactoTelefonico.set(value);
    }

    public StringProperty contactoTelefonicoProperty() {
        return contactoTelefonico;
    }

    public final String getContactoMovel() {
        return contactoMovel.get();
    }

    public final void setContactoMovel(String value) {
        contactoMovel.set(value);
    }

    public StringProperty contactoMovelProperty() {
        return contactoMovel;
    }

    public final String getFoto() {
        return foto.get();
    }

    public final void setFoto(String value) {
        foto.set(value);
    }

    public StringProperty fotoProperty() {
        return foto;
    }

    public final String getNomeDoPai() {
        return nomeDoPai.get();
    }

    public final void setNomeDoPai(String value) {
        nomeDoPai.set(value);
    }

    public StringProperty nomeDoPaiProperty() {
        return nomeDoPai;
    }

    public final String getNomeDaMae() {
        return nomeDaMae.get();
    }

    public final void setNomeDaMae(String value) {
        nomeDaMae.set(value);
    }

    public StringProperty nomeDaMaeProperty() {
        return nomeDaMae;
    }

    public final String getGrauAcademico() {
        return grauAcademico.get();
    }

    public final void setGrauAcademico(String value) {
        grauAcademico.set(value);
    }

    public StringProperty grauAcademicoProperty() {
        return grauAcademico;
    }

    public final String getTipoSanguineo() {
        return tipoSanguineo.get();
    }

    public final void setTipoSanguineo(String value) {
        tipoSanguineo.set(value);
    }

    public StringProperty tipoSanguineoProperty() {
        return tipoSanguineo;
    }

    public final String getMorada() {
        return morada.get();
    }

    public final void setMorada(String value) {
        morada.set(value);
    }

    public StringProperty moradaProperty() {
        return morada;
    }

    public final String getCidadeIlha() {
        return cidadeIlha.get();
    }

    public final void setCidadeIlha(String value) {
        cidadeIlha.set(value);
    }

    public StringProperty cidadeIlhaProperty() {
        return cidadeIlha;
    }

    public final String getNumeroDeIndentificacao() {
        return numeroDeIndentificacao.get();
    }

    public final void setNumeroDeIndentificacao(String value) {
        numeroDeIndentificacao.set(value);
    }

    public StringProperty numeroDeIndentificacaoProperty() {
        return numeroDeIndentificacao;
    }

    public final String getNaturalidade() {
        return naturalidade.get();
    }

    public final void setNaturalidade(String value) {
        naturalidade.set(value);
    }

    public StringProperty naturalidadeProperty() {
        return naturalidade;
    }

    public final String getNacionalidade() {
        return nacionalidade.get();
    }

    public final void setNacionalidade(String value) {
        nacionalidade.set(value);
    }

    public StringProperty nacionalidadeProperty() {
        return nacionalidade;
    }

    public final LocalDate getNascimento() {
        return nascimento.get();
    }

    public final void setNascimento(LocalDate value) {
        nascimento.set(value);
    }

    public ObjectProperty<LocalDate> nascimentoProperty() {
        return nascimento;
    }

    public final String getCartaConducao() {
        return cartaConducao.get();
    }

    public final void setCartaConducao(String value) {
        cartaConducao.set(value);
    }

    public StringProperty cartaConducaoProperty() {
        return cartaConducao;
    }

    public final String getBanco() {
        return banco.get();
    }

    public final void setBanco(String value) {
        banco.set(value);
    }

    public StringProperty bancoProperty() {
        return banco;
    }

    public final String getAgencia() {
        return agencia.get();
    }

    public final void setAgencia(String value) {
        agencia.set(value);
    }

    public StringProperty agenciaProperty() {
        return agencia;
    }

    public final String getNumDeConta() {
        return numDeConta.get();
    }

    public final void setNumDeConta(String value) {
        numDeConta.set(value);
    }

    public StringProperty numDeContaProperty() {
        return numDeConta;
    }

    public final String getObservacao() {
        return observacao.get();
    }

    public final void setObservacao(String value) {
        observacao.set(value);
    }

    public StringProperty observacaoProperty() {
        return observacao;
    }
    
    public ImageView getImgView() {
        return imgView;
    }

    public void setImgView(ImageView imgView) {
        this.imgView = imgView;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.numeroDeIndentificacao);
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
        final Instrutor other = (Instrutor) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.numeroDeIndentificacao, other.numeroDeIndentificacao)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome.get();
    }
}
