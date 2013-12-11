/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public boolean antentica(String senha,View view){
        try {
            senha = Criptografia.codificar(senha);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            view.erroMensagem("Não foi possível criptografar a senha.");
            return false;
        }
        
        return (senha.equals(usuario.getSenha()));
    }
    
    
    
}
