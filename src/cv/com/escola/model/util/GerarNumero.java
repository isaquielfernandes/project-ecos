package cv.com.escola.model.util;

public class GerarNumero {

    private static final int COUNT = 1;
    private String num = "";

    private GerarNumero() {

    }

    public void generar(int codigo) {
        int novo = COUNT + codigo;
        num = String.format("%06d", novo);
    }

    public String serie() {
        return this.num;
    }
}
