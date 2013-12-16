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
public class CashSimples {

    private String limite;
    private int numeroDeMaos;
    private double valorGanho;

    public CashSimples(String limite, int numeroDeMaos, double valorGanho) {
        this.limite = limite;
        this.numeroDeMaos = numeroDeMaos;
        this.valorGanho = valorGanho;
    }

    public CashSimples(String limite, Number numeroDeMaos, Number valorGanho) {
        this(limite, numeroDeMaos.intValue(), valorGanho.doubleValue());
    }

    public String getLimite() {
        return limite;
    }

    public void setLimite(String limite) {
        this.limite = limite;
    }

    public int getNumeroDeMaos() {
        return numeroDeMaos;
    }

    public void setNumeroDeMaos(int numeroDeMaos) {
        this.numeroDeMaos = numeroDeMaos;
    }

    public double getValorGanho() {
        return valorGanho;
    }

    public void setValorGanho(double valorGanho) {
        this.valorGanho = valorGanho;
    }

}
