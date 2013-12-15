/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import controller.CadastroBankRollViewController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.persistence.EntityManagerFactory;
import javax.swing.JFrame;
import model.Usuario;
import view.CadastroBankrollDialog;

/**
 *
 * @author augusto
 */
public class AbrirCadastroBankrollListener implements ActionListener {

    private JFrame parent;
    private EntityManagerFactory emf;
    private Usuario usuario;

    public AbrirCadastroBankrollListener(JFrame parent, EntityManagerFactory emf, Usuario usuario) {
        this.parent = parent;
        this.emf = emf;
        this.usuario = usuario;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println(System.currentTimeMillis());
        CadastroBankrollDialog cadastroView = new CadastroBankrollDialog(parent, true);
        CadastroBankRollViewController bankrollController = new CadastroBankRollViewController(cadastroView, usuario, emf);
        cadastroView.setVisible(true);
    }

}
