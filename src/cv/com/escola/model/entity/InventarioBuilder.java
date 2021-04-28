package cv.com.escola.model.entity;

import java.time.LocalDate;


public class InventarioBuilder {

    private int idInventario;
    private String numSerie;
    private String categoria;
    private String item;
    private String responsavel;
    private String area;
    private String local;
    private LocalDate dataDeCompra;
    private int mesesDesdeCompra;
    private double valor;
    private String estadoDeConservacao;
    private int vidaUtil;
    private double valorAtual;
    private String depreciacao;
    private LocalDate dataCadastro;

    public InventarioBuilder() {
        super();
    }

    public InventarioBuilder setIdInventario(int idInventario) {
        this.idInventario = idInventario;
        return this;
    }

    public InventarioBuilder setNumSerie(String numSerie) {
        this.numSerie = numSerie;
        return this;
    }

    public InventarioBuilder setCategoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public InventarioBuilder setItem(String item) {
        this.item = item;
        return this;
    }

    public InventarioBuilder setResponsavel(String responsavel) {
        this.responsavel = responsavel;
        return this;
    }

    public InventarioBuilder setArea(String area) {
        this.area = area;
        return this;
    }

    public InventarioBuilder setLocal(String local) {
        this.local = local;
        return this;
    }

    public InventarioBuilder setDataDeCompra(LocalDate dataDeCompra) {
        this.dataDeCompra = dataDeCompra;
        return this;
    }

    public InventarioBuilder setMesesDesdeCompra(int mesesDesdeCompra) {
        this.mesesDesdeCompra = mesesDesdeCompra;
        return this;
    }

    public InventarioBuilder setValor(double valor) {
        this.valor = valor;
        return this;
    }

    public InventarioBuilder setEstadoDeConservacao(String estadoDeConservacao) {
        this.estadoDeConservacao = estadoDeConservacao;
        return this;
    }

    public InventarioBuilder setVidaUtil(int vidaUtil) {
        this.vidaUtil = vidaUtil;
        return this;
    }

    public InventarioBuilder setValorAtual(double valorAtual) {
        this.valorAtual = valorAtual;
        return this;
    }

    public InventarioBuilder setDepreciacao(String depreciacao) {
        this.depreciacao = depreciacao;
        return this;
    }

    public InventarioBuilder setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
        return this;
    }

    public Inventario createInventario() {
        return new Inventario(idInventario, numSerie, categoria, item, responsavel, area, local, dataDeCompra, mesesDesdeCompra, valor, estadoDeConservacao, vidaUtil, valorAtual, depreciacao, dataCadastro);
    }
    
}
