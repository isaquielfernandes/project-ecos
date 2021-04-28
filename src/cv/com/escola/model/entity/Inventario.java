package cv.com.escola.model.entity;

import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Inventario {
    private IntegerProperty idInventario;
    public Integer getIdInventario() { return idInventarioProperty().get(); }
    public void setIdInventario(int value) { idInventarioProperty().set(value);}
    public IntegerProperty idInventarioProperty(){
        if(idInventario == null) idInventario = new SimpleIntegerProperty(this, "idInventario");
        return idInventario;
    }
    
    private StringProperty numSerie;
    public String getNumSerie() { return numSerieProperty().get(); }
    public void setNumSerie(String value) { numSerieProperty().set(value);}
    public StringProperty numSerieProperty(){
        if(numSerie == null) numSerie = new SimpleStringProperty(this, "numSerie");
        return numSerie;
    }
    
    private StringProperty categoria;
    public String getCategoria() { return categoriaProperty().get(); }
    public void setCategoria(String value) { categoriaProperty().set(value);}
    public StringProperty categoriaProperty(){
        if(categoria == null) categoria = new SimpleStringProperty(this, "categoria");
        return categoria;
    }
    
    private StringProperty item;
    public String getItem() { return itemProperty().get(); }
    public void setItem(String value) { itemProperty().set(value);}
    public StringProperty itemProperty(){
        if(item == null) item = new SimpleStringProperty(this, "item");
        return item;
    }
    
    private StringProperty responsavel;
    public String getResponsavel() { return responsavelProperty().get(); }
    public void setResponsavel(String value) { responsavelProperty().set(value);}
    public StringProperty responsavelProperty(){
        if(responsavel == null) responsavel = new SimpleStringProperty(this, "responsavel");
        return responsavel;
    }
    
    private StringProperty area;
    public String getArea() { return areaProperty().get(); }
    public void setArea(String value) { areaProperty().set(value);}
    public StringProperty areaProperty(){
        if(area == null) area = new SimpleStringProperty(this, "area");
        return area;
    }
    
    private StringProperty local;
    public String getLocal() { return localProperty().get(); }
    public void setLocal(String value) { localProperty().set(value);}
    public StringProperty localProperty(){
        if(local == null) local = new SimpleStringProperty(this, "local");
        return local;
    }
    
    private ObjectProperty<LocalDate> dataCompra;
    public LocalDate getDataCompra() { return dataCompraProperty().get(); }
    public void setDataCompra(LocalDate value) { dataCompraProperty().set(value);}
    public ObjectProperty<LocalDate> dataCompraProperty(){
        if(dataCompra == null) dataCompra = new SimpleObjectProperty(this, "dataCompra");
        return dataCompra;
    }
    private IntegerProperty mesesDesdeCompra;
    public Integer getMesesDesdeCompra() { return mesesDesdeCompraProperty().get(); }
    public void setMesesDesdeCompra(int value) { mesesDesdeCompraProperty().set(value);}
    public IntegerProperty mesesDesdeCompraProperty(){
        if(mesesDesdeCompra == null) mesesDesdeCompra = new SimpleIntegerProperty(this, "mesesDesdeCompra");
        return mesesDesdeCompra;
    }
    private DoubleProperty valor;
    public Double getValor() { return valorProperty().get(); }
    public void setValor(int value) { valorProperty().set(value);}
    public DoubleProperty valorProperty(){
        if(valor == null) valor = new SimpleDoubleProperty(this, "valor");
        return valor;
    }
    
    private StringProperty estadoConservacao;
    public String getEstadoConservacao() { return estadoConservacaoProperty().get(); }
    public void setEstadoConservacao(String value) { estadoConservacaoProperty().set(value);}
    public StringProperty estadoConservacaoProperty(){
        if(estadoConservacao == null) estadoConservacao = new SimpleStringProperty(this, "estadoConservacao");
        return estadoConservacao;
    }
    
    private IntegerProperty vidaUtilAno;
    public Integer getVidaUtilAno() { return vidaUtilAnoProperty().get(); }
    public void setVidaUtilAno(int value) { vidaUtilAnoProperty().set(value);}
    public IntegerProperty vidaUtilAnoProperty(){
        if(vidaUtilAno == null) vidaUtilAno = new SimpleIntegerProperty(this, "vidaUtilAno");
        return vidaUtilAno;
    }
    
    private DoubleProperty valorAtual;
    public Double getValorAtual() { return valorAtualProperty().get(); }
    public void setValorAtual(int value) { valorAtualProperty().set(value);}
    public DoubleProperty valorAtualProperty(){
        if(valorAtual == null) valorAtual = new SimpleDoubleProperty(this, "valorAtual");
        return valorAtual;
    }
    private StringProperty depreciacao;
    public String getDepreciacao() { return depreciacaoProperty().get(); }
    public void setDepreciacao(String value) { depreciacaoProperty().set(value);}
    public StringProperty depreciacaoProperty(){
        if(depreciacao == null) depreciacao = new SimpleStringProperty(this, "depreciacao");
        return depreciacao;
    }
    private ObjectProperty<LocalDate> dataCadastro;

    public LocalDate getDataCadastro() {
        return dataCadastro.get();
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro.set(dataCadastro);
    }

     public Inventario() {
         super();
     }
     
    public Inventario(int idInventario, String numSerie, String categoria, String item, String responsavel, String area, String local, LocalDate dataDeCompra, int mesesDesdeCompra, double valor, String estadoDeConservacao, int vidaUtil, double valorAtual, String depreciacao, LocalDate dataCadastro) {
        this.idInventario = new SimpleIntegerProperty(idInventario);
        this.numSerie = new SimpleStringProperty(numSerie);
        this.categoria = new SimpleStringProperty(categoria);
        this.item = new SimpleStringProperty(item);
        this.responsavel = new SimpleStringProperty(responsavel);
        this.area = new SimpleStringProperty(area);
        this.local = new SimpleStringProperty(local);
        this.dataCompra = new SimpleObjectProperty(dataDeCompra);
        this.mesesDesdeCompra = new SimpleIntegerProperty(mesesDesdeCompra);
        this.valor = new SimpleDoubleProperty(valor);
        this.estadoConservacao = new SimpleStringProperty(estadoDeConservacao);
        this.vidaUtilAno = new SimpleIntegerProperty(vidaUtil);
        this.valorAtual = new SimpleDoubleProperty(valorAtual);
        this.depreciacao = new SimpleStringProperty(depreciacao);
        this.dataCadastro = new SimpleObjectProperty(dataCadastro);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.idInventario);
        hash = 59 * hash + Objects.hashCode(this.numSerie);
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
        final Inventario other = (Inventario) obj;
        if (!Objects.equals(this.idInventario, other.idInventario)) {
            return false;
        }
        return Objects.equals(this.numSerie, other.numSerie);
    }

}
