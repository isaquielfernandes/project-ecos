package cv.com.escola.model.entity;

import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Veiculo {

    private LongProperty codigo;
    private StringProperty placa;
    private StringProperty cidade;
    private StringProperty fabricante;
    private StringProperty modelo;
    private IntegerProperty anoFabricacao;
    private IntegerProperty anoModelo;
    private StringProperty chassi;
    private StringProperty tipoCombustivel;
    private LocalDate dataCadastro;
    private LocalDate dataModificacao;
    private String especificacoes;
    private Proprietario proprietario;
    private String descricao;
    private byte[] foto;


    public Veiculo(String placa, String cidade) {
        this.placa = new SimpleStringProperty(placa);
        this.cidade = new SimpleStringProperty(cidade);
    }
    
    public Veiculo(Long codigo, String placa, String fabricante, String modelo, Integer anoFabricacao, String chassi) {
        this.codigo = new SimpleLongProperty(codigo);
        this.placa = new SimpleStringProperty(placa);
        this.fabricante = new SimpleStringProperty(fabricante);
        this.modelo = new SimpleStringProperty(modelo);
        this.anoFabricacao = new SimpleIntegerProperty(anoFabricacao);
        this.chassi = new SimpleStringProperty(chassi);
    }
    
    public Veiculo(Long codigo, String placa, String fabricante, String modelo, Integer anoFabricacao, Proprietario proprietario, String chassi) {
        this.codigo = new SimpleLongProperty(codigo);
        this.placa = new SimpleStringProperty(placa);
        this.fabricante = new SimpleStringProperty(fabricante);
        this.modelo = new SimpleStringProperty(modelo);
        this.anoFabricacao = new SimpleIntegerProperty(anoFabricacao);
        this.proprietario = proprietario;
        this.chassi = new SimpleStringProperty(chassi);
    }
    
    public Veiculo(Long codigo, String placa, String cidade, String fabricante, String modelo, Integer anoFabricacao, Integer anoModelo, String chassi, String tipoCombustivel, Proprietario proprietario, LocalDate dataCadastro, String especificacoes) {
        this.codigo = new SimpleLongProperty(codigo);
        this.placa = new SimpleStringProperty(placa);
        this.cidade = new SimpleStringProperty(cidade);
        this.fabricante = new SimpleStringProperty(fabricante);
        this.modelo = new SimpleStringProperty(modelo);
        this.anoFabricacao = new SimpleIntegerProperty(anoFabricacao);
        this.anoModelo = new SimpleIntegerProperty(anoModelo);
        this.chassi = new SimpleStringProperty(chassi);
        this.tipoCombustivel = new SimpleStringProperty(tipoCombustivel);
        this.dataCadastro = dataCadastro;
        this.especificacoes = especificacoes;
        this.proprietario = proprietario;
    }

    public Veiculo(Long codigo,  String placa, String cidade, String fabricante,
            String modelo, Integer anoFabricacao, Integer anoModelo, String valor, 
            String tipoCombustivel, Proprietario proprietario, LocalDate dataCadastro, 
            LocalDate dataModificacao, String especificacoes) {
        
        this.codigo = new SimpleLongProperty(codigo);
        this.placa = new SimpleStringProperty(placa);
        this.cidade = new SimpleStringProperty(cidade);
        this.fabricante = new SimpleStringProperty(fabricante);
        this.modelo = new SimpleStringProperty(modelo);
        this.anoFabricacao = new SimpleIntegerProperty(anoFabricacao);
        this.anoModelo = new SimpleIntegerProperty(anoModelo);
        this.chassi = new SimpleStringProperty(valor);
        this.tipoCombustivel = new SimpleStringProperty(tipoCombustivel);
        this.dataCadastro = dataCadastro;
        this.dataModificacao = dataModificacao;
        this.especificacoes = especificacoes;
        this.proprietario = proprietario;
    }

    public Veiculo() {
    }

    public final long getCodigo() {
        return codigo.get();
    }

    public final void setCodigo(long value) {
        codigo.set(value);
    }

    public LongProperty codigoProperty() {
        return codigo;
    }

    public final String getPlaca() {
        return placa.get();
    }

    public final void setPlaca(String value) {
        placa.set(value);
    }

    public StringProperty placaProperty() {
        return placa;
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

    public final String getFabricante() {
        return fabricante.get();
    }

    public final void setFabricante(String value) {
        fabricante.set(value);
    }

    public StringProperty fabricanteProperty() {
        return fabricante;
    }

    public final String getModelo() {
        return modelo.get();
    }

    public final void setModelo(String value) {
        modelo.set(value);
    }

    public StringProperty modeloProperty() {
        return modelo;
    }

    public final int getAnoFabricacao() {
        return anoFabricacao.get();
    }

    public final void setAnoFabricacao(int value) {
        anoFabricacao.set(value);
    }

    public IntegerProperty anoFabricacaoProperty() {
        return anoFabricacao;
    }

    public final int getAnoModelo() {
        return anoModelo.get();
    }

    public final void setAnoModelo(int value) {
        anoModelo.set(value);
    }

    public IntegerProperty anoModeloProperty() {
        return anoModelo;
    }

    public final String getChassi() {
        return chassi.get();
    }

    public final void setChassi(String value) {
        chassi.set(value);
    }

    public StringProperty chassiProperty() {
        return chassi;
    }

    public final String getTipoCombustivel() {
        return tipoCombustivel.get();
    }

    public final void setTipoCombustivel(String value) {
        tipoCombustivel.set(value);
    }

    public StringProperty tipoCombustivelProperty() {
        return tipoCombustivel;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDate getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(LocalDate dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public String getEspecificacoes() {
        return especificacoes;
    }

    public void setEspecificacoes(String especificacoes) {
        this.especificacoes = especificacoes;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.placa);
        hash = 79 * hash + Objects.hashCode(this.cidade);
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
        final Veiculo other = (Veiculo) obj;
        if (!Objects.equals(this.placa, other.placa)) {
            return false;
        }
        return Objects.equals(this.cidade, other.cidade);
    }

    @Override
    public String toString() {
        return placa.get();
    }
    
}
