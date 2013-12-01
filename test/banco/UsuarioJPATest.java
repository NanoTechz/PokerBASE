/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banco;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.UsuarioJpaController;
import model.Usuario;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author augusto
 */
public class UsuarioJPATest {
    
    public static void criarCampoTest(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PokerBASEPU");
        
        Usuario usuario= new Usuario();
        
        usuario.setNome("Augusto");
        usuario.setUsername("GalapagosBr");
        usuario.setSenha("criptografia");
        
        UsuarioJpaController usuarioJPA = new UsuarioJpaController(emf);
        try{
            usuarioJPA.create(usuario);
        }catch(Exception e){
            System.out.println("**************** Usuario ja cadastrado! *********************");
            e.printStackTrace(System.out);
        }
        
        
       // assertTrue(usuario.getId() != 0);
        emf.close();
    }
    
    @Test
    public void findUsuarioTest(){
        criarCampoTest();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PokerBASEPU");
        UsuarioJpaController usuarioJPA = new UsuarioJpaController(emf);
        
        Usuario findUsuario = usuarioJPA.findUsuario("GalapagosBr");
        
        assertNotNull(findUsuario);
        
        System.out.println(findUsuario.getId());
        emf.close();
    }
}
