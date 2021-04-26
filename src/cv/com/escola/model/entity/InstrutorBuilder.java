package cv.com.escola.model.entity;

import java.time.LocalDate;


public class InstrutorBuilder {

    private Long id;
    private String nome;
    private LocalDate admissao;
    private String email;
    private String contactoTelefonico;
    private String contactoMovel;
    private String foto;
    private String nomeDoPai;
    private String nomeDaMae;
    private String grauAcademico;
    private String tipoSanguineo;
    private String morada;
    private String cidadeIlha;
    private String numeroDeIndentificacao;
    private String naturalidade;
    private String nacionalidade;
    private LocalDate nascimento;
    private String cartaConducao;
    private String banco;
    private String agencia;
    private String numDeConta;
    private String obsercacao;

    public InstrutorBuilder() {
    }

    public InstrutorBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public InstrutorBuilder setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public InstrutorBuilder setAdmissao(LocalDate admissao) {
        this.admissao = admissao;
        return this;
    }

    public InstrutorBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public InstrutorBuilder setContactoTelefonico(String contactoTelefonico) {
        this.contactoTelefonico = contactoTelefonico;
        return this;
    }

    public InstrutorBuilder setContactoMovel(String contactoMovel) {
        this.contactoMovel = contactoMovel;
        return this;
    }

    public InstrutorBuilder setFoto(String foto) {
        this.foto = foto;
        return this;
    }

    public InstrutorBuilder setNomeDoPai(String nomeDoPai) {
        this.nomeDoPai = nomeDoPai;
        return this;
    }

    public InstrutorBuilder setNomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
        return this;
    }

    public InstrutorBuilder setGrauAcademico(String grauAcademico) {
        this.grauAcademico = grauAcademico;
        return this;
    }

    public InstrutorBuilder setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
        return this;
    }

    public InstrutorBuilder setMorada(String morada) {
        this.morada = morada;
        return this;
    }

    public InstrutorBuilder setCidadeIlha(String cidadeIlha) {
        this.cidadeIlha = cidadeIlha;
        return this;
    }

    public InstrutorBuilder setNumeroDeIndentificacao(String numeroDeIndentificacao) {
        this.numeroDeIndentificacao = numeroDeIndentificacao;
        return this;
    }

    public InstrutorBuilder setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
        return this;
    }

    public InstrutorBuilder setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
        return this;
    }

    public InstrutorBuilder setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
        return this;
    }

    public InstrutorBuilder setCartaConducao(String cartaConducao) {
        this.cartaConducao = cartaConducao;
        return this;
    }

    public InstrutorBuilder setBanco(String banco) {
        this.banco = banco;
        return this;
    }

    public InstrutorBuilder setAgencia(String agencia) {
        this.agencia = agencia;
        return this;
    }

    public InstrutorBuilder setNumDeConta(String numDeConta) {
        this.numDeConta = numDeConta;
        return this;
    }

    public InstrutorBuilder setObsercacao(String obsercacao) {
        this.obsercacao = obsercacao;
        return this;
    }

    public Instrutor createInstrutor() {
        return new Instrutor(id, nome, admissao, email, contactoTelefonico, contactoMovel, foto, nomeDoPai, nomeDaMae, grauAcademico, tipoSanguineo, morada, cidadeIlha, numeroDeIndentificacao, naturalidade, nacionalidade, nascimento, cartaConducao, banco, agencia, numDeConta, obsercacao);
    }
    
}
