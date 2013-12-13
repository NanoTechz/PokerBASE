/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import jpa.BankrollJpaController;
import jpa.CashJpaController;
import jpa.SessaoJpaController;
import model.Bankroll;
import model.Cash;
import model.Sessao;
import model.Torneio;
import model.Usuario;
import util.AutenticaCampo;
import view.PrincipalFrame;

/**
 *
 * @author augusto
 */
public class SessaoController {

    private Usuario usuario;
    private PrincipalFrame principalView;
    private EntityManagerFactory emf;
    private ArrayList<Cash> listaCash;
    private ArrayList<Torneio> listaTorneio;
    private DefaultListModel model;
    private Map<Cash, Bankroll> cashMap;

    public SessaoController(Usuario usuario, PrincipalFrame principalView, EntityManagerFactory emf) {
        this.usuario = usuario;
        this.principalView = principalView;
        this.emf = emf;

        listaCash = new ArrayList<Cash>();
        listaTorneio = new ArrayList<Torneio>();
        model = new DefaultListModel();
        
        cashMap = new HashMap<Cash, Bankroll>();

        limparDadosJogos();
        this.principalView.addLimparSessaoListener(new LimparSessaoListener());
        this.principalView.addAdicionarCashListener(new AdicionarCashListener());
        
    }

    public void salvarSessao() {
        String duracao = JOptionPane.showInputDialog("Insira a duração da sessão:");
        
        Sessao sessao = new Sessao();
        
        sessao.setDuracaoHoras(Double.parseDouble(duracao));
        sessao.setDataSessao(new Date(System.currentTimeMillis()));
        
        SessaoJpaController sJPA = new SessaoJpaController(emf);
        CashJpaController cJPA = new CashJpaController(emf);
        BankrollJpaController bJPA = new BankrollJpaController(emf);
        
        sJPA.create(sessao);
        
        for (int i = 0; i < model.getSize(); i++) {
            if(model.getElementAt(i) instanceof Cash){
                Cash cash = (Cash) model.getElementAt(i);
                Bankroll get = cashMap.get(cash);
                
                cash.setSala(get.getSala());
                
                get.setValorAtual(get.getValorAtual() + cash.getValorGanho());
                cash.setSessao(sessao);
                try {
                    bJPA.edit(get);
                } catch (Exception ex) {
                    Logger.getLogger(SessaoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                cJPA.create(cash);
  
            }
        }
        
    }

    public void limparDadosJogos() {
        model = new DefaultListModel();
        principalView.setModelListaJogos(model);
        listaCash.clear();
        listaTorneio.clear();
        cashMap.clear();
    }

    class AdicionarCashListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Cash cash = new Cash();
            AutenticaCampo autentica = new AutenticaCampo();
            
            cash.setLimite(principalView.getLimiteCash());
            cash.setValorBlind(Double.parseDouble(principalView.getValorBlind()));
            cash.setDuracaoHoras(Double.parseDouble(principalView.getDuracaoHorasCash()));
            cash.setNumeroMaos(Integer.parseInt(principalView.getNumeroMaosCash()));
            cash.setValorGanho(Double.parseDouble(principalView.getValorGanhoCash()));
            
            model.addElement(cash);
            
            cashMap.put(cash, (Bankroll)principalView.getSalaSelected());
            
            principalView.limparAbaCash();
        }

    }

    class LimparSessaoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            limparDadosJogos();
        }

    }

}
