/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv.com.escola.model.entity;

import cv.com.escola.model.entity.Curso;
import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Isaquiel Fernandes
 */
public class Turma {
    
    private LongProperty idTurma;
    private ObjectProperty<Curso> curso;
    private StringProperty periodo;
    private LongProperty numeroDeAluno;
    private ObjectProperty<LocalDate> dataInicio;
    private ObjectProperty<LocalDate> dataFim;

    public Turma() {
    }

    public Turma(Long idTurma, Curso curso, String periodo,Long numeroDeAluno, LocalDate dataInicio, LocalDate dataFim) {
        this.idTurma = new SimpleLongProperty(idTurma);
        this.curso = new SimpleObjectProperty(curso);
        this.periodo = new SimpleStringProperty(periodo);
        this.numeroDeAluno = new SimpleLongProperty(numeroDeAluno);
        this.dataInicio = new SimpleObjectProperty(dataInicio);
        this.dataFim = new SimpleObjectProperty(dataFim);
    }

    public final long getIdTurma() {
        return idTurma.get();
    }

    public final void setIdTurma(long value) {
        idTurma.set(value);
    }

    public LongProperty idTurmaProperty() {
        return idTurma;
    }

    public final Curso getCurso() {
        return curso.get();
    }

    public final void setCurso(Curso value) {
        curso.set(value);
    }

    public ObjectProperty<Curso> cursoProperty() {
        return curso;
    }

    public final String getPeriodo() {
        return periodo.get();
    }

    public final void setPeriodo(String value) {
        periodo.set(value);
    }

    public StringProperty periodoProperty() {
        return periodo;
    }

    public final long getNumeroDeAluno() {
        return numeroDeAluno.get();
    }

    public final void setNumeroDeAluno(long value) {
        numeroDeAluno.set(value);
    }

    public LongProperty numeroDeAlunoProperty() {
        return numeroDeAluno;
    }

    public final LocalDate getDataInicio() {
        return dataInicio.get();
    }

    public final void setDataInicio(LocalDate value) {
        dataInicio.set(value);
    }

    public ObjectProperty<LocalDate> dataInicioProperty() {
        return dataInicio;
    }

    public final LocalDate getDataFim() {
        return dataFim.get();
    }

    public final void setDataFim(LocalDate value) {
        dataFim.set(value);
    }

    public ObjectProperty<LocalDate> dataFimProperty() {
        return dataFim;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idTurma);
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
        final Turma other = (Turma) obj;
        return Objects.equals(this.idTurma, other.idTurma);
    }
    
    
}
