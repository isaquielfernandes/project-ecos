package cv.com.escola.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Item implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Long idItem;
    private Artigo artigo;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal descontoPorItem;
    private Venda venda;

    public Item() {
    }

    public Item(long idItem, Artigo artigo, int quantidade, BigDecimal valor, Venda venda) {
        this.idItem = idItem;
        this.artigo = artigo;
        this.quantidade = quantidade;
        this.valorUnitario = valor;
        this.venda = venda;
    }
    
    public Item(Artigo artigo, int quantidade, BigDecimal valor, Venda venda) {
        this.artigo = artigo;
        this.quantidade = quantidade;
        this.valorUnitario = valor;
        this.venda = venda;
    }

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getDescontoPorItem() {
        return descontoPorItem;
    }

    public void setDescontoPorItem(BigDecimal descontoPorItem) {
        this.descontoPorItem = descontoPorItem;
    }

    public Artigo getArtigo() {
        return artigo;
    }

    public void setArtigo(Artigo artigo) {
        this.artigo = artigo;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.idItem);
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
        final Item other = (Item) obj;
        return Objects.equals(this.idItem, other.idItem);
    }
}
