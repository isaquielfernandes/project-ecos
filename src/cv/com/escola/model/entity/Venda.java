package cv.com.escola.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Venda extends EntidadeAbstrata implements Serializable {

    private String numFatura;
    private LocalDate data;
    private BigDecimal subTotal;
    private BigDecimal desconto;
    private BigDecimal valorTotal;
    private boolean pago;
    private ObservableList<Item> itens = FXCollections.observableArrayList();
    private Cliente cliente;
    private Usuario usuario;
    private String meioDePagamento;

    public Venda() {
        super();
    }

    public Venda(LocalDate data, BigDecimal subTotal, boolean pago, Cliente cliente, Usuario usuario, String meioDePagamento, String numFatura) {
        this.data = data;
        this.subTotal = subTotal;
        this.pago = pago;
        this.cliente = cliente;
        this.usuario = usuario;
        this.meioDePagamento = meioDePagamento;
        this.numFatura = numFatura;
    }

    public Venda(long id, LocalDate data, BigDecimal valor, boolean pago,
            String meioDePagamento, BigDecimal desconto, String numFatura,
            Cliente cliente, Usuario usuario, BigDecimal valorTotal) {
        this.id = id;
        this.data = data;
        this.subTotal = valor;
        this.pago = pago;
        this.meioDePagamento = meioDePagamento;
        this.desconto = desconto;
        this.numFatura = numFatura;
        this.cliente = cliente;
        this.usuario = usuario;
        this.valorTotal = valorTotal;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public ObservableList<Item> getItens() {
        return itens;
    }

    public void setItens(ObservableList<Item> itens) {
        this.itens = itens;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMeioDePagamento() {
        return meioDePagamento;
    }

    public void setMeioDePagamento(String meioDePagamento) {
        this.meioDePagamento = meioDePagamento;
    }

    public String getNumFatura() {
        return numFatura;
    }

    public void setNumFatura(String numFatura) {
        this.numFatura = numFatura;
    }

    public void addItem(Item itemVenda) {
        itens.add(itemVenda);
    }

}
