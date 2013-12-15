/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.ejb.EntityManagerFactoryImpl;

/**
 *
 * @author augusto
 */
public class EntityManagerUtil {
    public static EntityManagerFactory emf;
    
    static{
        EntityManagerUtil.emf = Persistence.createEntityManagerFactory("PokerBASEPU");
    } 
    
    
    public static EntityManagerFactory getEspecificaPU(String name){
        return Persistence.createEntityManagerFactory(name);
    }
}
