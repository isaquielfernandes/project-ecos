package cv.com.escola.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Item extends EntidadeAbstrata implements Serializable {

    private Artigo artigo;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal descontoPorItem;
    private Venda venda;

    public Item() {
    }

    public Item(long id, Artigo artigo, int quantidade, BigDecimal valor, Venda venda) {
        this.id = id;
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

}
