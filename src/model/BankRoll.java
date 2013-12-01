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

/**
 *
 * @author augusto
 */
@Entity
public class BankRoll implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_bankroll")
    private Integer id;

    @Column(name="valor_inicial")
    private double valorInicial;
    
    @Column(name="valor_atual")
    private double valorAtual;
    
    //relação 1:N
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name="id_sala")
    private Sala sala;
    
    @OneToMany(mappedBy = "bankroll")
    private List<Operacao> listaOperacao;
}
