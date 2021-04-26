package cv.com.escola.model.util;

import java.time.Year;

public class GerarCodigo {

    private GerarCodigo() {
        
    }
    
    private static final int COUNT=1;
    private static String num="";
    private static final int ANOAGORA = Year.now().getValue();
    
    public static void gerar(int codigo){
        if((codigo >= 1000) || (codigo < 10000)){
            int novo = COUNT + codigo;
            num = "" + novo + "/" + ANOAGORA;
        }
        if((codigo >= 100) || (codigo < 1000)){
            int novo = COUNT + codigo;
            num = "0" + novo + "/" + ANOAGORA;
        }
        if((codigo >= 9) || (codigo < 100)){
            int novo = COUNT + codigo;
            num = "00" + novo + "/" + ANOAGORA;
        }
        if (codigo < 9){
            int novo = COUNT + codigo;
            num = "000" + novo + "/" + ANOAGORA;
        }
    }
    
    public static String serie(){
        return GerarCodigo.num;
    }
}
