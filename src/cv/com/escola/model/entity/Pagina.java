/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv.com.escola.model.entity;

/**
 *
 * @author Isaquiel Fernandes
 */
public class Pagina {
    private int total;
    private int inicio;
    private int fim;
    private int perPag;

    public Pagina() {
    }

    public Pagina(int total, int perPag) {
        this.total = total;
        this.perPag = perPag;
    }

    public Pagina(int total, int inicio, int fim, int perPag) {
        this.total = total;
        this.inicio = inicio;
        this.fim = fim;
        this.perPag = perPag;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public int getFim() {
        return fim;
    }

    public void setFim(int fim) {
        this.fim = fim;
    }

    public int getPerPag() {
        return perPag;
    }

    public void setPerPag(int perPag) {
        this.perPag = perPag;
    }
    
}
