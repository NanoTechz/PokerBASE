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
public class PrincipalController extends Controller{
    private Usuario usuario;
    private PrincipalFrame principalView;

    public PrincipalController(Usuario usuario, PrincipalFrame principalView, EntityManagerFactory emf) {
        super(emf);
        this.usuario = usuario;
        this.principalView = principalView;
        
        principalView.setUserName(usuario.getUsername());
    }
}
