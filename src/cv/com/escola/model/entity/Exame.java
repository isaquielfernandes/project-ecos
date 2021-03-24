/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv.com.escola.model.entity;

import cv.com.escola.model.entity.Categoria;
import cv.com.escola.model.entity.Aluno;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Isaquiel Fernandes
 */
public class Exame {

    private long idExame;
    private String tipoExame;
    private LocalDate dataExame;
    private LocalTime horaDeExame;
    private String descricao;
    private Categoria categoria;
    private Aluno aluno;
    private String atestadoMedico;
    private String registroCriminal;
    
    public Exame() {
    }
    
    public Exame(long idExame, String tipoExame, LocalDate dataExame, LocalTime horaDeExame, String descricao, Categoria categoria, Aluno aluno) {
        this.idExame = idExame;
        this.tipoExame = tipoExame;
        this.dataExame = dataExame;
        this.horaDeExame = horaDeExame;
        this.descricao = descricao;
        this.categoria = categoria;
        this.aluno = aluno;
    }

    public Exame(long idExame, Aluno aluno, Categoria categoria, LocalDate dataExame, LocalTime horaDeExame, String tipoExame) {
        this.idExame = idExame;
        this.aluno = aluno;
        this.categoria = categoria;
        this.dataExame = dataExame;
        this.horaDeExame = horaDeExame;
        this.tipoExame = tipoExame;
    }

    public long getIdExame() {
        return idExame;
    }

    public void setIdExame(long idExame) {
        this.idExame = idExame;
    }

    public String getTipoExame() {
        return tipoExame;
    }

    public void setTipoExame(String tipoExame) {
        this.tipoExame = tipoExame;
    }

    public LocalDate getDataExame() {
        return dataExame;
    }

    public void setDataExame(LocalDate dataExame) {
        this.dataExame = dataExame;
    }

    public LocalTime getHoraDeExame() {
        return horaDeExame;
    }

    public void setHoraDeExame(LocalTime horaDeExame) {
        this.horaDeExame = horaDeExame;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public String getAtestadoMedico() {
        return atestadoMedico;
    }

    public void setAtestadoMedico(String atestadoMedico) {
        this.atestadoMedico = atestadoMedico;
    }

    public String getRegistroCriminal() {
        return registroCriminal;
    }

    public void setRegistroCriminal(String registroCriminal) {
        this.registroCriminal = registroCriminal;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + (int) (this.idExame ^ (this.idExame >>> 32));
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
        final Exame other = (Exame) obj;
        return this.idExame == other.idExame;
    }

    @Override
    public String toString() {
        return "Exame{" + "tipoExame=" + tipoExame + ", dataExame=" + dataExame + ", descricao=" + descricao + ", categoria=" + categoria + ", aluno=" + aluno + '}';
    }
    
    
}
