/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.simples;

/**
 *
 * @author augusto
 */
public class TorneioSimples {
    private double valorGanho;
    private double BuyIn;
    
    public TorneioSimples(Number valorGanho, Number BuyIn) {
        this.valorGanho = valorGanho.doubleValue();
        this.BuyIn = BuyIn.doubleValue();
    }
    
    public TorneioSimples(double valorGanho, double BuyIn) {
        this.valorGanho = valorGanho;
        this.BuyIn = BuyIn;
    }

    public double getValorGanho() {
        return valorGanho;
    }

    public void setValorGanho(double valorGanho) {
        this.valorGanho = valorGanho;
    }

    public double getBuyIn() {
        return BuyIn;
    }

    public void setBuyIn(double BuyIn) {
        this.BuyIn = BuyIn;
    }
    
    
}
