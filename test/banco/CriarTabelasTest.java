/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banco;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Test;

/**
 *
 * @author augusto
 */
public class CriarTabelasTest {
    
    @Test
    public void criarTabelasTest(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestePU");
        emf.close();
    }
}
