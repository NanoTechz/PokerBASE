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

    bonus('B', "Bônus", 1), deposito('D', "Depósito", 1), saque('S', "Saque", -1);
    
    public char valor;
    public int fator;

    private String nome;

    private TipoOperacao(char valor, String nome, int fator) {
        this.valor = valor;
        this.nome = nome;
        this.fator = fator;
    }

    @Override
    public String toString() {
        return nome;
    }
    
    public static TipoOperacao getOperacao(char tipo){
        TipoOperacao[] values = TipoOperacao.values();
        for (TipoOperacao tipoOperacao : values) {
             if(tipoOperacao.valor == tipo){
                 return tipoOperacao;
             }
        }
        return null;
    }
    
}
