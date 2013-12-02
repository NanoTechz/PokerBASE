/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author augusto
 */
public abstract class Controller {
    private EntityManagerFactory emf;

    public Controller(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }
}
