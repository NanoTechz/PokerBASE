/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import controller.PrincipalViewController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.persistence.EntityManagerFactory;
import javax.swing.JFrame;
import model.Usuario;
import view.PrincipalFrame;

/**
 *
 * @author augusto
 */
public class AbrirFramePrincipalListener implements  ActionListener{

    private JFrame parent;
    private EntityManagerFactory emf;
    private Usuario usuario;

    public AbrirFramePrincipalListener(JFrame parent, EntityManagerFactory emf, Usuario usuario) {
        this.parent = parent;
        this.emf = emf;
        this.usuario = usuario;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        PrincipalFrame principalFrame = new PrincipalFrame();
        PrincipalViewController principalController = new PrincipalViewController(usuario, principalFrame, emf);
        principalFrame.setVisible(true);
    }
}
