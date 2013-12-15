/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import model.auxiliar.TipoOperacao;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author augusto
 */
@Entity
@Table(name="tb_bankroll")
public class Bankroll implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_bankroll")
    private Integer id;

    @Column(name="valor_inicial")
    private double valorInicial;
    
    @Column(name="valor_atual")
    private double valorAtual;
    
    @Column(name="data_criacao")
    private Date dataCriacao;
    
    //relação 1:N
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name="id_sala")
    private Sala sala;
    
    @OneToMany(mappedBy = "bankroll")
    private List<Operacao> listaOperacao;
    
    public Bankroll(){}

    public Bankroll(Usuario usuario) {
        this.usuario = usuario;
    }

    public Bankroll(Usuario usuario, Sala sala, double valorInicial) {
        this.valorInicial = valorInicial;
        this.sala = sala;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public double getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(double valorAtual) {
        this.valorAtual = valorAtual;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public List<Operacao> getListaOperacao() {
        return listaOperacao;
    }

    public void setListaOperacao(List<Operacao> listaOperacao) {
        this.listaOperacao = listaOperacao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public void atualizarValorAtual(TipoOperacao op, double valor){
        valorAtual += op.fator * valor;
    }

    @Override
    public String toString() {
        return sala+" - $"+valorAtual;
    }
    
    
}
