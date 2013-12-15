/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.persistence.EntityManagerFactory;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author augusto
 */
public class FecharConexaoTest {

    @Test
    public void fecharConexaoTest() {
        EntityManagerFactory emf = EntityManagerUtil.emf;
        
        assertTrue(emf.isOpen());
        
        EntityManagerUtil.emf.close();
        
        assertFalse(emf.isOpen());
    }

}
