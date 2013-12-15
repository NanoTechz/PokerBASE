/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.action.AbrirCadastroUsuarioListener;
import controller.action.AbrirFramePrincipalListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.persistence.EntityManagerFactory;
import jpa.UsuarioJpaController;
import model.Usuario;
import view.LoginFrame;

/**
 *
 * @author augusto
 */
public class LoginViewController extends ControllerView {

    private Usuario usuario;
    private final LoginFrame loginView;
    private final UsuarioController usuarioController;

    private final UsuarioJpaController usuarioJPA;

    public LoginViewController(Usuario usuario, LoginFrame loginView, EntityManagerFactory emf) {
        super(emf, loginView);
        this.usuario = usuario;
        this.loginView = loginView;
        this.usuarioController = new UsuarioController(usuario);
        this.usuarioJPA = new UsuarioJpaController(super.getEmf());

        AbrirCadastroUsuarioListener cadastroUsuarioListener = new AbrirCadastroUsuarioListener(loginView, emf, usuario);    
        
        this.loginView.addCadastroUsuarioListener(cadastroUsuarioListener);
        this.loginView.addLoginBotaoListener(new LoginListener());
    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String username;
            String senha;

            username = loginView.getUserName();
            senha = loginView.getSenha();

            usuario = usuarioJPA.findUsuario(username);

            if (usuario == null) {
                loginView.erroMensagem("Usuario não cadastrado!");
                return;
            }

            usuarioController.setUsuario(usuario);

            if (usuarioController.antentica(senha, loginView)) {
                AbrirFramePrincipalListener framePrincipalListener = new AbrirFramePrincipalListener(loginView, getEmf(), usuario);
                loginView.setVisible(false);
                framePrincipalListener.actionPerformed(ae);
                
            } else {
                loginView.erroMensagem("Senha errada!");
            }
        }

    }
}
