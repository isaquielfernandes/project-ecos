/*
 * Copyright (C) 2019 Isaquiel Fernandes.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package cv.com.escola.model.entity;

/**
 *
 * @author Isaquiel Fernandes
 */
public enum Ilha {
    SANTO_ANTAO("SANTO ANTÃO"), 
    SAO_VICENTE("SÂO VICENTE"),
    SAO_NICOLAU("SÂO NICOLAU"),
    SAL("SAL"),
    BOA_VISTA("BOA VISTA"),
    MAIO("MAIO"),
    SANTIAGO("SANTIAGO"),
    FOGO("FOGO"),
    BRAVA("BRAVA");
    
    private final String ilha;
    
    Ilha(String valor){
        this.ilha = valor;
    }

    public String getIlha() {
        return ilha;
    }
}
