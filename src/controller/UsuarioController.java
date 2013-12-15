/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import jpa.BankrollJpaController;
import jpa.SessaoJpaController;
import jpa.UsuarioJpaController;
import model.Bankroll;
import model.Sessao;
import model.Usuario;
import seguranca.Criptografia;
import view.View;

/**
 *
 * @author augusto
 */
public class UsuarioController {

    private Usuario usuario;

    public UsuarioController(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean antentica(String senha, View view) {
        senha = getSenhaCriptografada(senha, view);
        
        if(senha == null){
            return false;
        }
        
        return (senha.equals(usuario.getSenha()));
    }

    public void pegarListaBankrollSessao(Usuario usuario, EntityManagerFactory emf) {
        BankrollJpaController bankrollJPA = new BankrollJpaController(emf);
        SessaoJpaController sessaoJPA =  new SessaoJpaController(emf);
        
        List<Bankroll> listaBankRoll = bankrollJPA.findBankRollUsuario(usuario);
        usuario.setListaBankRolls(listaBankRoll);

        List<Sessao> listaSessao = sessaoJPA.findSessaoUsuario(usuario);
        usuario.setListaSessao(listaSessao);
    }
    
    public void alterarSenha(View view, String newSenha, String newSenhaConfirmcao, EntityManagerFactory emf){
        if(newSenha.equals(newSenhaConfirmcao)){
            newSenha = getSenhaCriptografada(newSenha, view);
            
            usuario.setSenha(newSenha);
            
            UsuarioJpaController uJPA = new UsuarioJpaController(emf);
            try {
                uJPA.edit(usuario);
            } catch (Exception ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                view.erroMensagem("Não foi possível salvar a nova senha.");
            }
            
        }
    }

    private String getSenhaCriptografada(String newSenha, View view) {
        try {
            return Criptografia.codificar(newSenha);
        } catch (    NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            view.erroMensagem("Não foi possível criptografar a senha.");
        }
        return null;
    }
    
    public boolean isUsernameEmUso(String username, EntityManagerFactory emf){
        UsuarioJpaController usuarioJPA = new UsuarioJpaController(emf);
        return (usuarioJPA.findUsuario(username) != null);
    }

}
