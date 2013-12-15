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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author augusto
 */
@Entity
@Table(name = "tb_sessao")
public class Sessao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_sessao")
    private Integer id;

    @Column(name = "data_sessao")
    private Date dataSessao;
    @Column(name = "duracao_horas")
    private double duracaoHoras;

    //Relação 1:n
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "sessao")
    private List<Cash> listaCash;

    @OneToMany(mappedBy = "sessao")
    private List<Torneio> listaTorneios;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataSessao() {
        return dataSessao;
    }

    public void setDataSessao(Date dataSessao) {
        this.dataSessao = dataSessao;
    }

    public double getDuracaoHoras() {
        return duracaoHoras;
    }

    public void setDuracaoHoras(double duracaoHoras) {
        this.duracaoHoras = duracaoHoras;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
    
    

}
