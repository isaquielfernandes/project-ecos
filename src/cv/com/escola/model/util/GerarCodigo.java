package cv.com.escola.model.util;

import java.time.Year;

public class GerarCodigo {

    public GerarCodigo() {
    }
    
    private static int CODIGO;
    private static final int COUNT=1;
    private static String num="";//String.format("%015d", id);
    private static final int ANOAGORA = Year.now().getValue();
    
    public static void gerar(int codigo){
        GerarCodigo.CODIGO = codigo;
        if((GerarCodigo.CODIGO>=1000)|| (GerarCodigo.CODIGO<10000)){
            int novo = COUNT + GerarCodigo.CODIGO;
            num = "" + novo + "/" + ANOAGORA;
        }
        if((GerarCodigo.CODIGO>=100) || (GerarCodigo.CODIGO<1000)){
            int novo = COUNT + GerarCodigo.CODIGO;
            num = "0" + novo + "/" + ANOAGORA;
        }
        if((GerarCodigo.CODIGO>=9) || (GerarCodigo.CODIGO<100)){
            int novo = COUNT + GerarCodigo.CODIGO;
            num = "00" + novo + "/" + ANOAGORA;
        }
        if (GerarCodigo.CODIGO<9){
            int novo = COUNT + GerarCodigo.CODIGO;
            num = "000" + novo + "/" + ANOAGORA;
        }
    }
    
    public static String serie(){
        return GerarCodigo.num;
    }
}
