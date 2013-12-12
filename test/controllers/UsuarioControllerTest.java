/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import controller.UsuarioController;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuario;
import org.junit.Test;
import static org.junit.Assert.*;
import seguranca.Criptografia;

/**
 *
 * @author augusto
 */
public class UsuarioControllerTest {
    
    @Test
    public void autenticaTest(){
        Usuario user = null;
        try {
            user = new Usuario("Teste", Criptografia.codificar("senha"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(UsuarioControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        UsuarioController ctrl = new UsuarioController(user);
        assertTrue(ctrl.antentica("senha", null));
        assertFalse(ctrl.antentica("erro", null));
          
    }
}
