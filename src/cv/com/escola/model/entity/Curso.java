package cv.com.escola.model.entity;

import java.util.Objects;


public class Curso {
    
    private Long codigo;
    private String nome;
    private Categoria categoria;
    private int duracao;
    private String descricao;

    public Curso() {
    }

    public Curso(Long codigo, String nome, int duracao, String descricao, Categoria categoria) {
        this.codigo = codigo;
        this.nome = nome;
        this.categoria = categoria;
        this.duracao = duracao;
        this.descricao = descricao;
    }

    public Curso(Long codigo, String curso, Categoria categoria) {
        this.codigo = codigo;
        this.nome = curso;
        this.categoria = categoria;
    }
    
    public Curso(Long codigo, String curso) {
        this.codigo = codigo;
        this.nome = curso;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.codigo);
        hash = 97 * hash + Objects.hashCode(this.nome);
        hash = 97 * hash + Objects.hashCode(this.categoria);
        hash = 97 * hash + this.duracao;
        hash = 97 * hash + Objects.hashCode(this.descricao);
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
        final Curso other = (Curso) obj;
        if (this.duracao != other.duracao) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return Objects.equals(this.categoria, other.categoria);
    }

    @Override
    public String toString() {
        return nome ;
    }
    
}
