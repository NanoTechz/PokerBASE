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
@Table(name = "tb_sala")
@Entity
public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_sala")
    private Integer id;

    @Column(name = "razao_social", unique = true)
    private String razaoSocial;
    private String link;
    private String descricao;
    
    //relaçcao 1:n
    @OneToMany(mappedBy = "sala")
    private List<Cash> listaCash;
    
    @OneToMany(mappedBy = "sala")
    private List<Torneio> listaTorneios;
    
    @OneToMany(mappedBy = "sala")
    private List<Bankroll> listaBankRoll;

    public Sala(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public Sala() {
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Cash> getListaCash() {
        return listaCash;
    }

    public void setListaCash(List<Cash> listaCash) {
        this.listaCash = listaCash;
    }

    public List<Torneio> getListaTorneios() {
        return listaTorneios;
    }

    public void setListaTorneios(List<Torneio> listaTorneios) {
        this.listaTorneios = listaTorneios;
    }
    
    
    public String toString(){
       return razaoSocial; 
    }
}
