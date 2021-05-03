package cv.com.escola.model.dto;

/**
 *
 * @author Isaquiel Fernandes
 */
public class RelatrioDTO {
    
    private Long mes;
    private Long dia; 
    private Long ano;

    public RelatrioDTO() {
        super();
    }

    public RelatrioDTO(Long mes, Long dia, Long ano) {
        this.mes = mes;
        this.dia = dia;
        this.ano = ano;
    }

    public Long getMes() {
        return mes;
    }

    public Long getDia() {
        return dia;
    }

    public Long getAno() {
        return ano;
    }
    
}
