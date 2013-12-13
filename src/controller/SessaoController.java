/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import javax.persistence.EntityManagerFactory;
import model.Usuario;
import view.PrincipalFrame;

/**
 *
 * @author augusto
 */
public class SessaoController {
    private Usuario usuario;
    private PrincipalFrame principalView;
    private EntityManagerFactory emf;

    public SessaoController(Usuario usuario, PrincipalFrame principalView, EntityManagerFactory emf) {
        this.usuario = usuario;
        this.principalView = principalView;
        this.emf = emf;
    }
    
    public void salvarSessao(){
        
    }
}
