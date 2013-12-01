/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author augusto
 */
@Entity
@Table(name="tb_cash")
public class Cash implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cash")
    private Integer id;
    
    @Column(length = 10)
    private String limite;
    
    @Column(name="valor_ganho")
    private double valorGanho;
    
    @Column(name="numero_maos")
    private int numeroMaos;
    
    @Column(name="duracao_horas")
    private double duracaoHoras;
    
    //relação 1:N
    @ManyToOne
    @JoinColumn(name="id_sala")
    private Sala sala;
    
    @ManyToOne
    @JoinColumn(name="id_sessao")
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

    public double getValorGanho() {
        return valorGanho;
    }

    public void setValorGanho(double valorGanho) {
        this.valorGanho = valorGanho;
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
    
    
}
