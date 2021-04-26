package cv.com.escola.model.util;

public class GerarNumero {

    private static final int COUNT = 1;
    private String num = "";

    private GerarNumero() {
        
    }

    public void generar(int codigo) {
        if ((codigo >= 10000000) || (codigo < 100000000)) {
            int novo = COUNT + codigo;
            num = "" + novo;
        }
        if ((codigo >= 1000000) || (codigo < 10000000)) {
            int novo = COUNT + codigo;
            num = "0" + novo;
        }
        if ((codigo >= 100000) || (codigo < 1000000)) {
            int novo = COUNT + codigo;
            num = "00" + novo;
        }
        if ((codigo >= 10000) || (codigo < 100000)) {
            int novo = COUNT + codigo;
            num = "000" + novo;
        }
        if ((codigo >= 1000) || (codigo < 10000)) {
            int novo = COUNT + codigo;
            num = "0000" + novo;
        }
        if ((codigo >= 100) || (codigo < 1000)) {
            int novo = COUNT + codigo;
            num = "00000" + novo;
        }
        if ((codigo >= 9) || (codigo < 100)) {
            int novo = COUNT + codigo;
            num = "000000" + novo;
        }
        if (codigo < 9) {
            int novo = COUNT + codigo;
            num = "0000000" + novo;
        }

    }

    public String serie() {
        return this.num;
    }
}
