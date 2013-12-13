/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import jpa.CashJpaController;
import jpa.TorneioJpaController;
import model.Cash;
import model.Torneio;
import model.Usuario;
import view.PrincipalFrame;
import view.model.CashTableModel;
import view.model.TorneioTableModel;

/**
 *
 * @author augusto
 */
public class TorneioController {
    private Usuario usuario;
    private PrincipalFrame principalView;
    private EntityManagerFactory emf;

    public TorneioController(Usuario usuario, PrincipalFrame principalView, EntityManagerFactory emf) {
        this.usuario = usuario;
        this.principalView = principalView;
        this.emf = emf;
    }
    
        public EntityManagerFactory getEmf() {
        return emf;
    }
    
    public TorneioTableModel getModelTorneio(){
        TorneioJpaController tJPA= new TorneioJpaController(emf);
        List<Torneio> findTorneioUsuarioPorBuyIN = tJPA.findTorneioUsuarioPorBuyIN(usuario);
        
        TorneioTableModel model = new TorneioTableModel(findTorneioUsuarioPorBuyIN);
        return model;
    }
}
