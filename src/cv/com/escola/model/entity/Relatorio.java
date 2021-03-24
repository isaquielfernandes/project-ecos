/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv.com.escola.model.entity;

import cv.com.escola.model.util.Tempo;

/**
 *
 * @author Isaquiel Fernandes
 */
public class Relatorio {
    private String tipo;
    private String data;
    private int total;

    public Relatorio(String tipo, String data) {
        this.tipo = tipo;
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFormatar() {
        try {
            return Tempo.mes(data);
        } catch (NumberFormatException ex) {
            return data;
        }
    }
    
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
