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
public enum TipoTorneio {

    SNG('S', 0), MTT('M', 1);

    private char valor;
    private int valorNumerico;

    private TipoTorneio(char valor, int valorNumerico) {
        this.valor = valor;
        this.valorNumerico = valorNumerico;
    }

    public char getValor() {
        return valor;
    }

    public int getValorNumerico() {
        return valorNumerico;
    }

    public static TipoTorneio getTipoTorneio(char valor) {
        for (TipoTorneio t : TipoTorneio.values()) {
            if (t.valor == valor) {
                return t;
            }

        }
        return null;
    } 
    
}
