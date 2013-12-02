/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.persistence.EntityManagerFactory;
import jpa.SalaJpaController;
import model.Sala;
import view.CadastroSalaDialog;

/**
 *
 * @author augusto
 */
public class CadastroSalaController extends Controller {

    private CadastroSalaDialog salaView;
    private Sala sala;
    private SalaJpaController salaJPA;

    public CadastroSalaController(CadastroSalaDialog salaView, EntityManagerFactory emf) {
        super(emf);
        this.salaView = salaView;
        this.salaView.addAdicionarSalaListener(new AdicionarSalaListener());
    }

    class AdicionarSalaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(salaView.getRazaoSocial().isEmpty()){
                salaView.mensagemErro("Campo \"Nome Empresa\" vazio!");
                return;
            }
            
            Sala sala = new Sala();
            
            sala.setRazaoSocial(salaView.getRazaoSocial());
            sala.setLink(salaView.getWebSite());
            sala.setDescricao(salaView.getDescricao());
            
            salaJPA = new SalaJpaController(getEmf());
            
            salaJPA.create(sala);
            
            salaView.mensagemErro("Sala add com sucesso!");
            salaView.dispose();
        }

    }
}
