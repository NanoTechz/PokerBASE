/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author augusto
 */
@Entity
public class Operacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_operacao")
    private Integer id;
    
    private double valor;
    
    @Column(name="data_operacao")
    private Date dataOperacao;
    
    private char tipo;
    
    
    //Relaçao 1:N
    @ManyToOne
    @JoinColumn(name="id_bankroll")
    private BankRoll bankroll;
    
    
    
    
}
