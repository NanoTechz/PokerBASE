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
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.UsuarioJpaController;
import model.Usuario;
import org.junit.Test;
import static org.junit.Assert.*;
import seguranca.Criptografia;

/**
 *
 * @author augusto
 */
public class UsuarioJPATest {
    
    @Test
    public void criarCampoTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PokerBASEPU");
        UsuarioJpaController usuarioJPA = new UsuarioJpaController(emf);
        Usuario usuario = new Usuario();

        if (usuarioJPA.findUsuario("GalapagosBr") == null) {
            usuario.setNome("Augusto");
            usuario.setUsername("GalapagosBr");
            
            try {
                usuario.setSenha(Criptografia.cifrar("fenix"));
                System.out.println(usuario.getSenha());
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                Logger.getLogger(UsuarioJPATest.class.getName()).log(Level.SEVERE, null, ex);
            }

            usuarioJPA.create(usuario);
            assertTrue(usuario.getId() != 0);
        }

        emf.close();
    }

    @Test
    public void findUsuarioTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PokerBASEPU");
        UsuarioJpaController usuarioJPA = new UsuarioJpaController(emf);

        Usuario findUsuario = usuarioJPA.findUsuario("GalapagosBr");
        assertNotNull(findUsuario);

        System.out.println(findUsuario.getId());
        emf.close();
    }
}
