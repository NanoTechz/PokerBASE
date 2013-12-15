/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.DefaultComboBoxModel;
import jpa.TorneioJpaController;
import model.Torneio;
import model.Usuario;
import model.auxiliar.TipoTorneio;
import model.auxiliar.TipoTorneioGenero;
import view.PrincipalFrame;
import view.model.TorneioTableModel;

/**
 *
 * @author augusto
 */
public class TorneioController {

    private Usuario usuario;
    private PrincipalFrame principalView;
    private EntityManagerFactory emf;

    public TorneioController(Usuario usuario, PrincipalFrame principalView, EntityManagerFactory emf) {
        this.usuario = usuario;
        this.principalView = principalView;
        this.emf = emf;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public TorneioTableModel getModelTorneio() {
        TorneioJpaController tJPA = new TorneioJpaController(emf);
        TorneioTableModel model = new TorneioTableModel();

        TipoTorneio[] values = TipoTorneio.values();

        for (TipoTorneio tipoTorneio : values) {
            List<Torneio> findTorneioUsuarioPorBuyIN = tJPA.findTorneioUsuarioPorBuyIN(usuario, tipoTorneio);
            model.addListaDeTorneios(findTorneioUsuarioPorBuyIN);
        }
        return model;
    }

    public DefaultComboBoxModel TipoTorneioComboBoxModel() {

        DefaultComboBoxModel model = new DefaultComboBoxModel(TipoTorneio.values());
        return model;
    }

    public DefaultComboBoxModel TipoTorneioGeneroComboBoxModel() {

        DefaultComboBoxModel model = new DefaultComboBoxModel(TipoTorneioGenero.values());
        return model;
    }
}
