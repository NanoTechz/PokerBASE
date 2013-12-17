/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import jpa.CashJpaController;
import model.Cash;
import model.Usuario;
import model.simples.CashSimples;
import view.PrincipalFrame;
import view.model.CashAbaTableModel;

/**
 *
 * @author augusto
 */
public class CashController {
    private Usuario usuario;
    private PrincipalFrame principalView;
    private EntityManagerFactory emf;

    public CashController(Usuario usuario, PrincipalFrame principalView, EntityManagerFactory emf) {
        this.usuario = usuario;
        this.principalView = principalView;
        this.emf = emf;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }
    
    public CashAbaTableModel getModelCash(){
        CashJpaController cJPA = new CashJpaController(emf);
        List<Cash> findCashUsuario = cJPA.findCashUsuario(usuario);
        
        CashAbaTableModel model = new CashAbaTableModel(findCashUsuario);
        
        return model;
    }
    
    public void gerarGrafico(){
        CashJpaController cJPA = new CashJpaController(emf);
        List<CashSimples> findCashGroupByLimite = cJPA.findCashGroupByLimite(usuario);
        
        principalView.getGraficoCash().limparGrafico();
        
        for (CashSimples cashSimples : findCashGroupByLimite) {
            principalView.getGraficoCash().addValue(cashSimples.getLimite(), cashSimples.getValorGanho());
        }
    }
    
    
}
