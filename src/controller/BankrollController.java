/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.swing.DefaultComboBoxModel;
import model.Bankroll;
import model.Usuario;

/**
 *
 * @author augusto
 */
public class BankrollController {

    public DefaultComboBoxModel getBankrollComboBoxModel(Usuario usuario) {
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Bankroll b : usuario.getListaBankRolls()) {
            model.addElement(b);
        }

        return model;
    }
}
