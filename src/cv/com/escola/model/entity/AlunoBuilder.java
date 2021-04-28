
package cv.com.escola.model.entity;

import java.time.LocalDate;


public class AlunoBuilder {

    private int idAluno;
    private String nome;
    private LocalDate dataNascimento;
    private String numBI;
    private LocalDate dataEmisao;
    private String residencia;
    private String conselho;
    private String natural;
    private String email;
    private String contato;
    private String habilitacaoLit;
    private String nacionalidade;
    private String descricao;
    private String nomeDaMae;
    private String nomeDoPai;
    private String professao;
    private String estadoCivil;
    private String localDeEmisao;
    private String freguesia;

    public AlunoBuilder() {
        super();
    }

    public AlunoBuilder setIdAluno(int idAluno) {
        this.idAluno = idAluno;
        return this;
    }

    public AlunoBuilder setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public AlunoBuilder setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public AlunoBuilder setNumBI(String numBI) {
        this.numBI = numBI;
        return this;
    }

    public AlunoBuilder setDataEmisao(LocalDate dataEmisao) {
        this.dataEmisao = dataEmisao;
        return this;
    }

    public AlunoBuilder setResidencia(String residencia) {
        this.residencia = residencia;
        return this;
    }

    public AlunoBuilder setConselho(String conselho) {
        this.conselho = conselho;
        return this;
    }

    public AlunoBuilder setNatural(String natural) {
        this.natural = natural;
        return this;
    }

    public AlunoBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public AlunoBuilder setContato(String contato) {
        this.contato = contato;
        return this;
    }

    public AlunoBuilder setHabilitacaoLit(String habilitacaoLit) {
        this.habilitacaoLit = habilitacaoLit;
        return this;
    }

    public AlunoBuilder setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
        return this;
    }

    public AlunoBuilder setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public AlunoBuilder setNomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
        return this;
    }

    public AlunoBuilder setNomeDoPai(String nomeDoPai) {
        this.nomeDoPai = nomeDoPai;
        return this;
    }

    public AlunoBuilder setProfessao(String professao) {
        this.professao = professao;
        return this;
    }

    public AlunoBuilder setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
        return this;
    }

    public AlunoBuilder setLocalDeEmisao(String localDeEmisao) {
        this.localDeEmisao = localDeEmisao;
        return this;
    }

    public AlunoBuilder setFreguesia(String freguesia) {
        this.freguesia = freguesia;
        return this;
    }

    public Aluno createAluno() {
        return new Aluno(idAluno, nome, dataNascimento, numBI, dataEmisao, 
                residencia, conselho, natural, email, contato, habilitacaoLit, 
                nacionalidade, descricao, nomeDaMae, nomeDoPai, professao, 
                estadoCivil, localDeEmisao, freguesia);
    }
    
}
