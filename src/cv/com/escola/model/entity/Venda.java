package cv.com.escola.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Venda implements Serializable{

    private Integer idVenda;
    private String numFatura;
    private LocalDate data;
    private BigDecimal valor;
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

    public Venda(Integer idVenda, LocalDate data, BigDecimal valor, BigDecimal desconto, boolean pago, Cliente cliente, Usuario usuario, String meioDePagamento, String numFatura, BigDecimal valorTotal) {
        this.idVenda = idVenda;
        this.data = data;
        this.valor = valor;
        this.desconto = desconto;
        this.pago = pago;
        this.cliente = cliente;
        this.usuario = usuario;
        this.meioDePagamento = meioDePagamento;
        this.numFatura = numFatura;
        this.valorTotal = valorTotal;
    }

    public Venda(LocalDate data, BigDecimal valor, boolean pago, Cliente cliente, Usuario usuario, String meioDePagamento, String numFatura) {
        this.data = data;
        this.valor = valor;
        this.pago = pago;
        this.cliente = cliente;
        this.usuario = usuario;
        this.meioDePagamento = meioDePagamento;
        this.numFatura = numFatura;
    }

    public Venda(int idVenda, LocalDate data, BigDecimal valor, boolean pago,
            String meioDePagamento, BigDecimal desconto, String numFatura,
            Cliente cliente, Usuario usuario, BigDecimal valorTotal) {
        this.idVenda = idVenda;
        this.data = data;
        this.valor = valor;
        this.pago = pago;
        this.meioDePagamento = meioDePagamento;
        this.desconto = desconto;
        this.numFatura = numFatura;
        this.cliente = cliente;
        this.usuario = usuario;
        this.valorTotal = valorTotal;
    }

    public Integer getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.idVenda);
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
        final Venda other = (Venda) obj;
        return Objects.equals(this.idVenda, other.idVenda);
    }

}
