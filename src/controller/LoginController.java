/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import jpa.UsuarioJpaController;
import model.Usuario;
import seguranca.Criptografia;
import view.CadastroUsuarioDialog;
import view.LoginFrame;
import view.PrincipalFrame;

/**
 *
 * @author augusto
 */
public class LoginController  extends ControllerView {

    private Usuario usuarioModel;
    private LoginFrame loginView;
    private UsuarioController usuarioController;

    private final UsuarioJpaController usuarioJPA;

    public LoginController(Usuario usuario, LoginFrame loginView, EntityManagerFactory emf) {
        super(emf, loginView);
        this.usuarioModel = usuario;
        this.loginView = loginView;
        this.usuarioController =  new UsuarioController(usuario);
        this.usuarioJPA = new UsuarioJpaController(super.getEmf());
        
        this.loginView.addLoginBotaoListener(new LoginListener());
        this.loginView.addCadastroListener(new CadastroListener());
    }

    public boolean autenticar(Usuario usuario, String senha) {
        return (senha.equals(usuario.getSenha()));
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
            } else {
                try {
                    senha = Criptografia.codificar(senha);

                    if (autenticar(usuarioModel, senha)) {
                        PrincipalFrame principalFrame = new PrincipalFrame();
                        PrincipalController principalController = new PrincipalController(usuarioModel, principalFrame, getEmf());
                        
                        principalFrame.setVisible(true);
                        loginView.setVisible(false);
                    } else {
                        loginView.erroMensagem("Senha errada!");
                    }
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    class CadastroListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            CadastroUsuarioDialog cadastroView = new CadastroUsuarioDialog(loginView, true);
            CadastroUsuarioController cadastroController = new CadastroUsuarioController(cadastroView, usuarioModel, getEmf());

            cadastroView.setVisible(true);

        }

    }

}
