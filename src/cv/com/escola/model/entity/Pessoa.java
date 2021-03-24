/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv.com.escola.model.entity;

import java.time.LocalDate;

/**
 *
 * @author Isaquiel Fernandes
 */
public abstract class Pessoa {
    private Long id;
    private String nome;
    private String endereco;
    private LocalDate dataNascimento;
}
