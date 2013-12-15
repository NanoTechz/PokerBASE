/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import controller.CadastroSalaViewController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.persistence.EntityManagerFactory;
import javax.swing.JFrame;
import model.Usuario;
import view.CadastroSalaDialog;

/**
 *
 * @author augusto
 */
public class AbrirCadastroSalaListener implements ActionListener {

    private JFrame parent;
    private EntityManagerFactory emf;
    private Usuario usuario;

    public AbrirCadastroSalaListener(JFrame parent, EntityManagerFactory emf, Usuario usuario) {
        this.parent = parent;
        this.emf = emf;
        this.usuario = usuario;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        CadastroSalaDialog salaView = new CadastroSalaDialog(null, true);
        CadastroSalaViewController salaController = new CadastroSalaViewController(salaView, emf);
        salaView.setVisible(true);
    }

}
