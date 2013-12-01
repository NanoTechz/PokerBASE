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
import util.EntityManagerUtil;
import view.LoginFrame;

/**
 *
 * @author augusto
 */
public class LoginController {
    
    private Usuario usuarioModel;
    private LoginFrame loginView;
    
    private EntityManagerFactory emf;
    private UsuarioJpaController usuarioJPA;

    public LoginController(Usuario usuarioModel, LoginFrame loginView, EntityManagerFactory emf) {
        this.usuarioModel = usuarioModel;
        this.loginView = loginView;
        this.emf = emf;
        
        this.loginView.addLoginBotaoListener(new LoginListener());
        
        
        this.usuarioJPA = new UsuarioJpaController(this.emf);
    }

    class LoginListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            String username;
            String senha;
            
            username = loginView.getUserName();
            senha = loginView.getSenha();
            
            usuarioModel = usuarioJPA.findUsuario(username);
            
            if(usuarioModel == null){
                loginView.mensagemErro("Usuario não cadastrado!");
            }else{
                try {
                    senha = Criptografia.cifrar(senha);
                    
                    if(autenticar(usuarioModel, senha)){
                        System.out.println("***************continuar**********************");
                    }else{
                        loginView.mensagemErro("Senha errada!");
                    }
                } catch (        NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
    
    
    public boolean autenticar(Usuario usuario, String senha){
        return (senha.equals(usuario.getSenha()));
    }
}
