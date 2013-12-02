/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManagerFactory;
import javax.swing.DefaultComboBoxModel;
import jpa.BankRollJpaController;
import jpa.SalaJpaController;
import model.Sala;
import model.Usuario;
import view.CadastroBankrollDialog;
import view.CadastroSalaDialog;

/**
 *
 * @author augusto
 */
public class CadastroBankRollController extends Controller {
    
    private CadastroBankrollDialog cadastroBankRollView;
    private List<Sala> listaSala;
    private Usuario usuario;
    
    private SalaJpaController salaJPA;
    private BankRollJpaController bankRollJPA;

    public CadastroBankRollController(CadastroBankrollDialog cadastroBankRollView, Usuario usuario, EntityManagerFactory emf) {
        super(emf);
        this.cadastroBankRollView = cadastroBankRollView;
        this.usuario = usuario;
        
        this.salaJPA =  new SalaJpaController(emf);
        this.bankRollJPA = new BankRollJpaController(emf);
         
        this.cadastroBankRollView.centralizarTela();
        this.cadastroBankRollView.setModelListaSala(getModel());
        
        this.cadastroBankRollView.addAdicionarSalaListener(new AdicionarSalaListener());
    }
    
    
    public DefaultComboBoxModel getModel(){
        List<Sala> salas = salaJPA.findSalaEntities();
        DefaultComboBoxModel model;
        if(salas.isEmpty()){
            model = new DefaultComboBoxModel(new String[]{"Nenhuma Sala Cadastrada"});
        }else{
            model = new DefaultComboBoxModel(new Vector(salas));
        }
        return model;
    }
    
    class AdicionarSalaListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            CadastroSalaDialog salaView = new CadastroSalaDialog(null, true);
            salaView.centralizarTela();
            
            CadastroSalaController salaController = new CadastroSalaController(salaView,getEmf());
            salaView.setVisible(true);
            DefaultComboBoxModel model = getModel();
            
            model.setSelectedItem(model.getElementAt(model.getSize() - 1));
            
            cadastroBankRollView.setModelListaSala(model);
               
        }
        
    }
}
