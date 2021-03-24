package cv.com.escola.model.dao;

import java.math.BigDecimal;

public interface FluxoCaixaDAO {

    BigDecimal totalPorAnoDasDespesas(String ano);
    BigDecimal totalPorAnoDasReceitas(String ano);
    BigDecimal totalPorMesDasDespesas(String mes, String ano);
    BigDecimal totalPorMesDasReceitas(String mes, String ano);
    
}
