/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.DefaultComboBoxModel;
import jpa.TorneioJpaController;
import model.Torneio;
import model.Usuario;
import model.auxiliar.TipoTorneio;
import model.auxiliar.TipoTorneioGenero;
import model.simples.TorneioSimples;
import org.jfree.data.xy.XYSeries;
import util.Calculadora;
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

    public List<XYSeries> getXYSeries() {
        TorneioJpaController tJPA = new TorneioJpaController(emf);
        TorneioSimples total = tJPA.getTotal(usuario);
        List<TorneioSimples> lista = tJPA.findTorneiosSimples(usuario);
        
        double roi = Calculadora.roi(total.getValorGanho(), total.getBuyIn());
        double mediaBI = total.getBuyIn() / (double)lista.size();
        double soma = 0, somaROI = 0;

        List<XYSeries> series = new ArrayList<XYSeries>();

        XYSeries serieNormal = new XYSeries("torneios");
        XYSeries serieROI = new XYSeries("roi");
        
        for (int i = 0; i < lista.size(); i++) {

            soma += lista.get(i).getValorGanho() - lista.get(i).getBuyIn();
            serieNormal.add(i, soma);

            somaROI += mediaBI * roi;
            serieROI.add(i, somaROI);
        }
 
        series.add(serieROI);
        series.add(serieNormal);
        
        return series;
    }
}
