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
import util.AutenticaCampo;
import view.CadastroSalaDialog;

/**
 *
 * @author augusto
 */
public class CadastroSalaViewController extends ControllerView {

    private final CadastroSalaDialog salaView;
    private SalaJpaController salaJPA;
    private final AutenticaCampo autentica;

    public CadastroSalaViewController(CadastroSalaDialog salaView, EntityManagerFactory emf) {
        super(emf, salaView);
        this.salaView = salaView;
        this.salaView.addAdicionarSalaListener(new AdicionarSalaListener());
        autentica = new AutenticaCampo();
    }

    class AdicionarSalaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(autentica.isCampoVazio(salaView.getRazaoSocial(), "Campo \"Nome Empresa\" vazio!", salaView)){
                return;
            }
            
            Sala sala = new Sala();
            
            sala.setRazaoSocial(salaView.getRazaoSocial());
            sala.setLink(salaView.getWebSite());
            sala.setDescricao(salaView.getDescricao());
            
            salaJPA = new SalaJpaController(getEmf());
            
            salaJPA.create(sala);
            
            salaView.mensagem("Sala add com sucesso!");
            salaView.dispose();
        }

    }
}
