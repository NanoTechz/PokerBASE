/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.auxiliar;

/**
 *
 * @author augusto
 */
public enum TipoOperacao {

    bonus('B'), deposito('D'), saque('S');
    
    public char valor;

    private TipoOperacao(char valor) {
        this.valor = valor;
    }
    
    
}
