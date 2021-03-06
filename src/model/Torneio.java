/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author augusto
 */
@Entity
@Table(name = "tb_torneio")
public class Torneio extends Modalidade implements Serializable {

    public Torneio(Number buyIn, char tipo, char genero, Number valorGanho, Number totalJogados) {
        this.tipo = tipo;
        this.buyIn = buyIn.doubleValue();
        this.genero = genero;
        setValorGanho(valorGanho.doubleValue());
        this.totalJogados = totalJogados.intValue();
    }

    public Torneio(char tipo, double buyIn, int totalJogadores, int posicao, Sessao sessao, Sala sala) {
        this.tipo = tipo;
        this.buyIn = buyIn;
        this.totalJogadores = totalJogadores;
        this.posicao = posicao;
        this.sessao = sessao;
        this.sala = sala;
    }

    public Torneio() {
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_torneio")
    private Integer id;

    @Column(name = "tipo")
    private char tipo; // s=SnG e m=mtt

    @Column(name = "genero")
    private char genero;//T=turbo , R = regular, HT= hiperturbo, O=outro
    
    @Column(name = "buy_in")
    private double buyIn;

    @Column(name = "total_jogadores")
    private int totalJogadores;

    private int posicao;

    @Column(name = "duracao_horas")
    private double duracao;

    @Column(name = "qtd_rebuys")
    private int qtdRebuys;

    @Column(name = "valor_rebuy")
    private double valorRebuy;

    @Column(name = "valor_add_on")
    private double valorAddOn;
    
    @Transient
    private int totalJogados;

    //relacao 1:n
    @ManyToOne
    @JoinColumn(name = "id_sessao")
    private Sessao sessao;

    @ManyToOne
    @JoinColumn(name = "id_sala")
    private Sala sala;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getBuyIn() {
        return buyIn;
    }

    public void setBuyIn(double buyIn) {
        this.buyIn = buyIn;
    }

    public int getTotalJogadores() {
        return totalJogadores;
    }

    public void setTotalJogadores(int totalJogadores) {
        this.totalJogadores = totalJogadores;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public double getDuracao() {
        return duracao;
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }

    public int getQtdRebuys() {
        return qtdRebuys;
    }

    public void setQtdRebuys(int qtdRebuys) {
        this.qtdRebuys = qtdRebuys;
    }

    public double getValorRebuy() {
        return valorRebuy;
    }

    public void setValorRebuy(double valorRebuy) {
        this.valorRebuy = valorRebuy;
    }

    public double getValorAddOn() {
        return valorAddOn;
    }

    public void setValorAddOn(double valorAddOn) {
        this.valorAddOn = valorAddOn;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public int getTotalJogados() {
        return totalJogados;
    }

    public void setTotalJogados(int totalJogados) {
        this.totalJogados = totalJogados;
    }
    
    public static double roi(double ganho, double buyin, int totalJogados){
        return (ganho - (buyin * totalJogados))/(buyin * totalJogados);
    }
    
    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Torneio{" + "tipo=" + tipo + ", genero=" + genero + ", buyIn=" + buyIn + ", totalJogadores=" + totalJogadores + ", posicao=" + posicao + '}';
    }
 
    
    
}
