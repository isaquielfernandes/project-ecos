/*
 * Copyright (C) 2019 Isaquiel Fernandes.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package cv.com.escola.model.entity;

import cv.com.escola.model.entity.Curso;
import cv.com.escola.model.entity.Aluno;
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
public class Matricula {
    
    private LongProperty id;
    private ObjectProperty<LocalDate> data;
    private ObjectProperty<Aluno> aluno;
    private ObjectProperty<Curso> cursoPretendido;
    private StringProperty turma;
    private StringProperty periodo;
    private StringProperty observacao;

    public Matricula() {
    }

    public Matricula(Long id, LocalDate data, Aluno aluno, Curso cursoPretendido, String turma, String periodo, String observacao) {
        this.id = new SimpleLongProperty(id);
        this.data = new SimpleObjectProperty(data);
        this.aluno = new SimpleObjectProperty(aluno);
        this.cursoPretendido = new SimpleObjectProperty(cursoPretendido);
        this.turma = new SimpleStringProperty(turma);
        this.periodo = new SimpleStringProperty(periodo);
        this.observacao = new SimpleStringProperty(observacao);
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

    public final LocalDate getData() {
        return data.get();
    }

    public final void setData(LocalDate value) {
        data.set(value);
    }

    public ObjectProperty<LocalDate> dataProperty() {
        return data;
    }

    public final Aluno getAluno() {
        return aluno.get();
    }

    public final void setAluno(Aluno value) {
        aluno.set(value);
    }

    public ObjectProperty<Aluno> alunoProperty() {
        return aluno;
    }

    public final Curso getCursoPretendido() {
        return cursoPretendido.get();
    }

    public final void setCursoPretendido(Curso value) {
        cursoPretendido.set(value);
    }

    public ObjectProperty<Curso> cursoPretendidoProperty() {
        return cursoPretendido;
    }

    public final String getTurma() {
        return turma.get();
    }

    public final void setTurma(String value) {
        turma.set(value);
    }

    public StringProperty turmaProperty() {
        return turma;
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

    public final String getObservacao() {
        return observacao.get();
    }

    public final void setObservacao(String value) {
        observacao.set(value);
    }

    public StringProperty observacaoProperty() {
        return observacao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.aluno);
        hash = 97 * hash + Objects.hashCode(this.cursoPretendido);
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
        final Matricula other = (Matricula) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.aluno, other.aluno)) {
            return false;
        }
        if (!Objects.equals(this.cursoPretendido, other.cursoPretendido)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Matricula{" + "id=" + id + ", data=" + data + ", aluno=" + aluno + ", cursoPretendido=" + cursoPretendido + ", turma=" + turma + ", periodo=" + periodo + ", observacao=" + observacao + '}';
    }
}
