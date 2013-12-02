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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author augusto
 */
@Entity
@Table(name="tb_usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_usuario")
    private Integer id;

    @Column(length = 50)
    private String nome;
    @Column(length = 30, unique = true)
    private String username;
    private String senha;
    
    //Relação 1:n
    
    @OneToMany(mappedBy = "usuario")
    private List<Sessao> listaSessao;

    @OneToMany(mappedBy = "usuario")
    private List<BankRoll> listaBankRolls;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Sessao> getListaSessao() {
        return listaSessao;
    }

    public void setListaSessao(List<Sessao> listaSessao) {
        this.listaSessao = listaSessao;
    }

    public List<BankRoll> getListaBankRolls() {
        return listaBankRolls;
    }

    public void setListaBankRolls(List<BankRoll> listaBankRolls) {
        this.listaBankRolls = listaBankRolls;
    }  
}
