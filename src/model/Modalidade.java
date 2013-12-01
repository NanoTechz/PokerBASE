/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author augusto
 */
@MappedSuperclass
public class Modalidade implements Serializable{
    @Column(name="qtd_jgs_mesa")
    private int qtdJogadoresMesa;

    public int getQtdJogadoresMesa() {
        return qtdJogadoresMesa;
    }

    public void setQtdJogadoresMesa(int qtdJogadoresMesa) {
        this.qtdJogadoresMesa = qtdJogadoresMesa;
    }
}
