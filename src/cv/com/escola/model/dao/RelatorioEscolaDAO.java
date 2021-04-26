package cv.com.escola.model.dao;

import java.time.Year;
import java.util.ArrayList;
import java.util.Map;

public interface RelatorioEscolaDAO {

    int count(Year ano);
    int count(String tipo, Year ano);
    int countResultado(String resultado, String ano);
    int countResultadoPorTipoExame(String tipo, String resultado, String ano);
    Map<String, ArrayList<Object>> listarExamePorTipoDeExame(Year ano);
    
}
