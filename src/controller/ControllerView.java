/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import javax.persistence.EntityManagerFactory;
import view.View;

/**
 *
 * @author augusto
 */
public abstract class ControllerView {
    private EntityManagerFactory emf;

    public ControllerView(EntityManagerFactory emf, View view) {
        this.emf = emf;
        view.centralizarTela();
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }
}
