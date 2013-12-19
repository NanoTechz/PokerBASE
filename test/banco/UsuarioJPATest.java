/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpa.UsuarioJpaController;
import model.Usuario;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import seguranca.Criptografia;
import util.EntityManagerUtil;

/**
 *
 * @author augusto
 */
public class UsuarioJPATest {
    
    private final EntityManagerFactory emf = EntityManagerUtil.getEspecificaPU("TestePU");
    
    @Before
    public void inicializarCampo(){
        UsuarioJpaController uJPA = new UsuarioJpaController(emf);
        Usuario usuario = uJPA.findUsuario("GalapagosBr");
        
        if(usuario == null){
            try {
                usuario = new Usuario("GalapagosBr", Criptografia.codificar("senha"));
                uJPA.create(usuario);
                
            } catch (    NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                Logger.getLogger(UsuarioJPATest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Test
    public void findUsuarioTest() {;
        UsuarioJpaController usuarioJPA = new UsuarioJpaController(emf);

        Usuario findUsuario = usuarioJPA.findUsuario("GalapagosBr");

        assertNotNull(findUsuario);

        System.out.println(findUsuario.getId());
        emf.close();
    }
    
}
