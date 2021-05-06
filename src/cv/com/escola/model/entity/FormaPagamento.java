package cv.com.escola.model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class FormaPagamento extends EntidadeAbstrata {

    private String formaDePagmento;
    private String descricao;

    public String getFormaDePagmento() {
        return formaDePagmento;
    }

    public void setFormaDePagmento(String formaDePagmento) {
        this.formaDePagmento = formaDePagmento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
