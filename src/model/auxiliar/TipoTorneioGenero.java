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
public enum TipoTorneioGenero {
    //T=turbo , R = regular, HT= hiperturbo, O=outro
    TURBO("Turbo", 'T'), REGULAR("REGULAR", 'R'), HYPER_TURBO("Hyper Turbo", 'H'), OUTRO("Outro", 'O');
    
    private String name;
    private char valor;

    private TipoTorneioGenero(String name, char valor) {
        this.name = name;
        this.valor = valor;
    }

    public String getName() {
        return name;
    }

    public char getValor() {
        return valor;
    }
    
    
    public static TipoTorneioGenero getTipoTonerneioGenero(char valor){
        TipoTorneioGenero[] values = TipoTorneioGenero.values();
        
        for (TipoTorneioGenero tipoTorneioGenero : values) {
            if(tipoTorneioGenero.valor == valor){
                return tipoTorneioGenero;
            }
        }
        
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

}
