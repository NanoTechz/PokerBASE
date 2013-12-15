/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import controller.CadastroUsuarioViewController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.persistence.EntityManagerFactory;
import javax.swing.JFrame;
import model.Usuario;
import view.CadastroUsuarioDialog;

/**
 *
 * @author augusto
 */
public class AbrirCadastroUsuarioListener implements ActionListener {

    private JFrame parent;
    private EntityManagerFactory emf;
    private Usuario usuario;

    public AbrirCadastroUsuarioListener(JFrame parent, EntityManagerFactory emf, Usuario usuario) {
        this.parent = parent;
        this.emf = emf;
        this.usuario = usuario;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        CadastroUsuarioDialog cadastroView = new CadastroUsuarioDialog(parent, true);
        CadastroUsuarioViewController cadastroController = new CadastroUsuarioViewController(cadastroView, usuario, emf);
        cadastroView.setVisible(true);
    }

}
