package cv.com.escola.model.entity;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Isaquiel Fernandes
 */
public class Aluno {
    
    private IntegerProperty idAluno;
    public Integer getIdAluno() { return idAlunoProperty().get(); }
    public void setIdAluno(int value) { idAlunoProperty().set(value);}
    public IntegerProperty idAlunoProperty(){
        if(idAluno == null) idAluno = new SimpleIntegerProperty(this, "idAluno");
        return idAluno;
    }
    
    private StringProperty nome;
    public String getNome() { return nomeProperty().get(); }
    public void setNome(String value) { nomeProperty().set(value); }
    public StringProperty nomeProperty(){
        if(nome == null) nome = new SimpleStringProperty(this, "nome");
        return nome;
    }
    
    private ObjectProperty<LocalDate> dataNascimento;
    public LocalDate getDataNascimento() { return dataNascimentoProperty().get(); }
    public void setDataNascimento(LocalDate value) { dataNascimentoProperty().set(value); }
    public ObjectProperty<LocalDate> dataNascimentoProperty(){
        if(dataNascimento == null) dataNascimento = new SimpleObjectProperty(this, "dataNascimento");
        return dataNascimento;
    }
    
    private StringProperty numBI;
    public String getNumBI() { return numBIProperty().get(); }
    public void setNumBI(String value) { numBIProperty().set(value); }
    public StringProperty numBIProperty(){
        if(numBI == null) numBI = new SimpleStringProperty(this, "numBI");
        return numBI;
    }
    
    private ObjectProperty<LocalDate> dataEmisao;
    public LocalDate getDataEmisao() { return dataEmisaoProperty().get(); }
    public void setDataEmisao(LocalDate value) { dataEmisaoProperty().set(value); }
    public ObjectProperty<LocalDate> dataEmisaoProperty(){
        if(dataEmisao == null) dataEmisao = new SimpleObjectProperty(this, "dataEmisao");
        return dataEmisao;
    }
    
    private StringProperty residencia;
    public String getResidencia() { return residenciaProperty().get(); }
    public void setResidencia(String value) { residenciaProperty().set(value); }
    public StringProperty residenciaProperty(){
        if(residencia == null) residencia = new SimpleStringProperty(this, "residencia");
        return residencia;
    }
    
    private StringProperty conselho;
    public String getConselho() { return conselhoProperty().get(); }
    public void setConselho(String value) { conselhoProperty().set(value); }
    public StringProperty conselhoProperty(){
        if(conselho == null) conselho = new SimpleStringProperty(this, "conselho");
        return conselho;
    }
    private StringProperty freguesia;
    public String getFreguesia() { return freguesiaProperty().get(); }
    public void setFreguesia(String value) { freguesiaProperty().set(value); }
    public StringProperty freguesiaProperty(){
        if(freguesia == null) freguesia = new SimpleStringProperty(this, "freguesia");
        return freguesia;
    }
    
    private StringProperty natural;
    public String getNatural() { return naturalProperty().get(); }
    public void setNatural(String value) { naturalProperty().set(value); }
    public StringProperty naturalProperty(){
        if(natural == null) natural = new SimpleStringProperty(this, "natural");
        return natural;
    }
    
    private StringProperty email;
    public String getEmail() { return emailProperty().get(); }
    public void setEmail(String value) { emailProperty().set(value); }
    public StringProperty emailProperty(){
        if(email == null) email = new SimpleStringProperty(this, "email");
        return email;
    }
    
    private StringProperty contato;
    public String getContato() { return contatoProperty().get(); }
    public void setContato(String value) { contatoProperty().set(value); }
    public StringProperty contatoProperty(){
        if(contato == null) contato = new SimpleStringProperty(this, "contato");
        return contato;
    }
    
    private StringProperty habilitacaoLit;
    public String getHabilitacaoLit() { return habilitacaoLitProperty().get(); }
    public void setHabilitacaoLit(String value) { habilitacaoLitProperty().set(value); }
    public StringProperty habilitacaoLitProperty(){
        if(habilitacaoLit == null) habilitacaoLit = new SimpleStringProperty(this, "habilitacaoLit");
        return habilitacaoLit;
    }
    
    private StringProperty nacionalidade;
    public String getNacionalidade() { return nacionalidadeProperty().get(); }
    public void setNacionalidade(String value) { nacionalidadeProperty().set(value); }
    public StringProperty nacionalidadeProperty(){
        if(nacionalidade == null) nacionalidade = new SimpleStringProperty(this, "nacionalidade");
        return nacionalidade;
    }
    
    private Blob foto;
    private Blob fotocopiaBI;
    private StringProperty descricao;
    public String getDescricao() { return descricaoProperty().get(); }
    public void setDescricao(String descricao) { descricaoProperty().set(descricao); }
    public StringProperty descricaoProperty(){
        if(descricao == null) descricao = new SimpleStringProperty(this, "descricao");
        return descricao;
    }
    private StringProperty nomeDaMae;
    public String getNomeDaMae() { return nomeDaMaeProperty().get(); }
    public void setNomeDaMae(String value) { nomeDaMaeProperty().set(value); }
    public StringProperty nomeDaMaeProperty(){
        if(nomeDaMae == null) nomeDaMae = new SimpleStringProperty(this, "nomeDaMae");
        return nomeDaMae;
    }
    private StringProperty nomeDoPai;
    public String getNomeDoPai() { return nomeDoPaiProperty().get(); }
    public void setNomeDoPai(String value) { nomeDoPaiProperty().set(value); }
    public StringProperty nomeDoPaiProperty(){
        if(nomeDoPai == null) nomeDoPai = new SimpleStringProperty(this, "nomeDoPai");
        return nomeDoPai;
    }
    private StringProperty professao;
    public String getProfessao() { return professaoProperty().get(); }
    public void setProfessao(String value) { professaoProperty().set(value); }
    public StringProperty professaoProperty(){
        if(professao == null) professao = new SimpleStringProperty(this, "professao");
        return professao;
    }
    private StringProperty estadoCivil;
    public String getEstadoCivil() { return estadoCivilProperty().get(); }
    public void setEstadoCivil(String value) { estadoCivilProperty().set(value); }
    public StringProperty estadoCivilProperty(){
        if(estadoCivil == null) estadoCivil = new SimpleStringProperty(this, "estadoCivil");
        return estadoCivil;
    }
    private StringProperty localDeEmisao;
    public String getLocalDeEmisao() { return localDeEmisaoProperty().get(); }
    public void setLocalDeEmisao(String value) { localDeEmisaoProperty().set(value); }
    public StringProperty localDeEmisaoProperty(){
        if(localDeEmisao == null) localDeEmisao = new SimpleStringProperty(this, "localDeEmisao");
        return localDeEmisao;
    }
    
    public Image image;
    public Image getImage() {return image;}
    public void setImage(Image image) {
        this.image = image;
    }
    
    public ImageView imageView;
    public ImageView getImageView() {
        return imageView = new ImageView(this.getImage());
    }
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
    
    
    public String imagePath;
    public String filePath;
    private File file;
    private FileInputStream fileInputStream;
    public FileInputStream getFileInputStream() {
        return fileInputStream;
    }
    public void setFileInputStream(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }
    
    private ObjectProperty<LocalDate> dataCadastro;
    public LocalDate getDataCadastro() { return dataCadastroProperty().get(); }
    public void setDataCadastro(LocalDate value) { dataCadastroProperty().set(value); }
    public ObjectProperty<LocalDate> dataCadastroProperty(){
        if(dataCadastro == null) dataCadastro = new SimpleObjectProperty(this, "dataCadastro");
        return dataCadastro;
    }
    
    public Blob getFoto() {
        return foto;
    }

    public void setFoto(Blob foto) {
        this.foto = foto;
    }

    public Blob getFotocopiaBI() {
        return fotocopiaBI;
    }

    public void setFotocopiaBI(Blob fotocopiaBI) {
        this.fotocopiaBI = fotocopiaBI;
    }
    
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    
    public Aluno() {
        
    }
    
    public Aluno(String nome) {
        this.nome = new SimpleStringProperty(nome);
    }
    public Aluno(Integer idAluno, String nome, String value) {
        this.idAluno = new SimpleIntegerProperty(idAluno);
        this.nome = new SimpleStringProperty(nome);
        this.numBI = new SimpleStringProperty(value);
    }
    
    public Aluno(int idAluno, String nome) {
        this.idAluno = new SimpleIntegerProperty(idAluno);
        this.nome = new SimpleStringProperty(nome);
    }

    public Aluno(int idAluno, String nome, LocalDate dataNascimento, String numBI, LocalDate dataEmisao, String residencia, String conselho, String natural, String email, String contato, String habilitacaoLit, String nacionalidade,File file , String descricao, String nomeDaMae, String nomeDoPai, String professao, String estadoCivil, String localDeEmisao, String freguesia) {
        this.idAluno = new SimpleIntegerProperty(idAluno);
        this.nome = new SimpleStringProperty(nome);
        this.dataNascimento = new SimpleObjectProperty(dataNascimento);
        this.numBI = new SimpleStringProperty(numBI);
        this.dataEmisao = new SimpleObjectProperty(dataEmisao);
        this.residencia = new SimpleStringProperty(residencia);
        this.conselho = new SimpleStringProperty(conselho);
        this.natural = new SimpleStringProperty(natural);
        this.email = new SimpleStringProperty(email);
        this.contato = new SimpleStringProperty(contato);
        this.habilitacaoLit = new SimpleStringProperty(habilitacaoLit);
        this.nacionalidade = new SimpleStringProperty(nacionalidade);
        this.file = file;
        this.descricao = new SimpleStringProperty(descricao);
        this.nomeDaMae = new SimpleStringProperty(nomeDaMae);
        this.nomeDoPai = new SimpleStringProperty(nomeDoPai);
        this.professao = new SimpleStringProperty(professao);
        this.estadoCivil = new SimpleStringProperty(estadoCivil);
        this.localDeEmisao = new SimpleStringProperty(localDeEmisao);
        this.freguesia = new SimpleStringProperty(freguesia);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idAluno);
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
        final Aluno other = (Aluno) obj;
        return Objects.equals(this.idAluno, other.idAluno);
    }

    @Override
    public String toString() {
        return  nome.get() ;
    }
}