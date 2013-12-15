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

/**
 *
 * @author augusto
 */
@Entity
@Table(name = "tb_cash")
public class Cash extends Modalidade implements Serializable {

    private static final long serialVersionUID = 1L;

    public static double bb100(int numeroMaos, double valorGanho, double valorBlind) {
        return (valorGanho / (double) (numeroMaos)) * (100 / valorBlind);
    }

    public Cash() {
    }

    public Cash(String limite, Number valorBlind, Number valorGanho, Number duracao, Number numeroMaos) {
        this.valorBlind = valorBlind.doubleValue();
        setValorGanho(valorGanho.doubleValue());
        this.numeroMaos = numeroMaos.intValue();
        this.limite = limite;
        this.duracaoHoras = duracao.doubleValue();
    }

    public Cash(double valorBlind, double valorGanho, int numeroMaos, double duracaoHoras, Sala sala) {
        this.valorBlind = valorBlind;
        setValorGanho(valorGanho);
        this.numeroMaos = numeroMaos;
        this.duracaoHoras = duracaoHoras;
        this.sala = sala;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cash")
    private Integer id;

    @Column(length = 10)
    private String limite;

    @Column(name = "valor_blind")
    private double valorBlind;

    @Column(name = "numero_maos")
    private int numeroMaos;

    @Column(name = "duracao_horas")
    private double duracaoHoras;

    //relação 1:N
    @ManyToOne
    @JoinColumn(name = "id_sala")
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "id_sessao")
    private Sessao sessao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLimite() {
        return limite;
    }

    public void setLimite(String limite) {
        this.limite = limite;
    }

    public int getNumeroMaos() {
        return numeroMaos;
    }

    public void setNumeroMaos(int numeroMaos) {
        this.numeroMaos = numeroMaos;
    }

    public double getDuracaoHoras() {
        return duracaoHoras;
    }

    public void setDuracaoHoras(double duracaoHoras) {
        this.duracaoHoras = duracaoHoras;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    public double getValorBlind() {
        return valorBlind;
    }

    public void setValorBlind(double valorBlind) {
        this.valorBlind = valorBlind;
    }
    
    
    @Override
    public String toString() {
        return "Cash{" + "limite=" + limite + ", valorBlind=" + valorBlind + ", numeroMaos=" + numeroMaos + '}';
    }
}
