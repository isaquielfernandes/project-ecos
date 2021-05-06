package cv.com.escola.model.util;

import java.time.Year;

public class GerarCodigo {

    private GerarCodigo() {

    }

    private static final int COUNT = 1;
    private static String num = "";
    private static final int ANO_AGORA = Year.now().getValue();

    public static void gerar(int codigo) {
        int novo = COUNT + codigo;
        num = String.format("%04d", novo) + "/" + ANO_AGORA;
    }

    public static String serie() {
        return GerarCodigo.num;
    }
}
