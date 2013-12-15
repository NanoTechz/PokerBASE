/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.persistence.EntityManagerFactory;
import jpa.UsuarioJpaController;
import model.Usuario;
import view.CadastroUsuarioDialog;
import view.LoginFrame;
import view.PrincipalFrame;

/**
 *
 * @author augusto
 */
public class LoginControllerView extends ControllerView {

    private Usuario usuarioModel;
    private final LoginFrame loginView;
    private final UsuarioController usuarioController;

    private final UsuarioJpaController usuarioJPA;

    public LoginControllerView(Usuario usuario, LoginFrame loginView, EntityManagerFactory emf) {
        super(emf, loginView);
        this.usuarioModel = usuario;
        this.loginView = loginView;
        this.usuarioController = new UsuarioController(usuario);
        this.usuarioJPA = new UsuarioJpaController(super.getEmf());

        this.loginView.addLoginBotaoListener(new LoginListener());
        this.loginView.addCadastroUsuarioListener(new CadastroListener());
    }

    private void abrirFramePrincipal() {
        PrincipalFrame principalFrame = new PrincipalFrame();
        PrincipalControllerView principalController = new PrincipalControllerView(usuarioModel, principalFrame, getEmf());

        principalFrame.setVisible(true);
    }

    private void abrirCadastroUsuario() {
        CadastroUsuarioDialog cadastroView = new CadastroUsuarioDialog(loginView, true);
        CadastroUsuarioControllerView cadastroController = new CadastroUsuarioControllerView(cadastroView, usuarioModel, getEmf());

        cadastroView.setVisible(true);
    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String username;
            String senha;

            username = loginView.getUserName();
            senha = loginView.getSenha();

            usuarioModel = usuarioJPA.findUsuario(username);

            if (usuarioModel == null) {
                loginView.erroMensagem("Usuario não cadastrado!");
                return;
            }

            usuarioController.setUsuario(usuarioModel);

            if (usuarioController.antentica(senha, loginView)) {
                abrirFramePrincipal();
                loginView.setVisible(false);
            } else {
                loginView.erroMensagem("Senha errada!");
            }
        }

    }

    class CadastroListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            abrirCadastroUsuario();
        }

    }

}
