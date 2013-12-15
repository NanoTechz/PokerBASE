/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.DefaultComboBoxModel;
import jpa.SalaJpaController;
import model.Sala;

/**
 *
 * @author augusto
 */
public class SalaController {
    private SalaJpaController salaJPA;

    public SalaController(EntityManagerFactory emf) {
        this.salaJPA = new SalaJpaController(emf);
    }
    public DefaultComboBoxModel getSalasComboBoxModel() {
        List<Sala> salas = salaJPA.findSalaEntities();
        DefaultComboBoxModel model;
        if (salas.isEmpty()) {
            model = new DefaultComboBoxModel(new String[]{"Nenhuma Sala Cadastrada"});
        } else {
            model = new DefaultComboBoxModel();
            for (Sala sala : salas) {
                model.addElement(sala);
            }
        }
        return model;
    }
}
