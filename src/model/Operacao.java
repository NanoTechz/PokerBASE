/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import model.auxiliar.TipoOperacao;
import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
@Table(name="tb_operacao")
public class Operacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_operacao")
    private Integer id;
    
    private double valor;
    
    @Column(name="data_operacao")
    private Date dataOperacao;
    
    private char tipo = 0;
    
    //Relaçao 1:N
    @ManyToOne
    @JoinColumn(name="id_bankroll")
    private Bankroll bankroll;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(Date dataOperacao) {
        this.dataOperacao = dataOperacao;
    }

    public Bankroll getBankroll() {
        return bankroll;
    }

    public void setBankroll(Bankroll bankroll) {
        this.bankroll = bankroll;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
       
       return "Tipo : "+TipoOperacao.getOperacao(tipo)+", Valor: "+valor+", Data: "+f.format(dataOperacao);
    }
    
}
