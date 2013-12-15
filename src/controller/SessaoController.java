/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import jpa.BankrollJpaController;
import jpa.CashJpaController;
import jpa.SessaoJpaController;
import jpa.TorneioJpaController;
import model.Bankroll;
import model.Cash;
import model.Sessao;
import model.Torneio;
import model.Usuario;
import model.auxiliar.TipoTorneio;
import model.auxiliar.TipoTorneioGenero;
import util.AutenticaCampo;
import view.PrincipalFrame;

/**
 *
 * @author augusto
 */
public class SessaoController {

    private final Usuario usuario;
    private final PrincipalFrame principalView;
    private final EntityManagerFactory emf;
    private DefaultListModel model;
    private final Map<Cash, Bankroll> cashMap;
    private final Map<Torneio, Bankroll> torneioMap;

    public SessaoController(Usuario usuario, PrincipalFrame principalView, EntityManagerFactory emf) {
        this.usuario = usuario;
        this.principalView = principalView;
        this.emf = emf;
        model = new DefaultListModel();

        cashMap = new HashMap<Cash, Bankroll>();
        torneioMap = new HashMap<Torneio, Bankroll>();

        limparDadosJogos();

        this.principalView.addLimparSessaoListener(new LimparSessaoListener());
        this.principalView.addAdicionarCashListener(new AdicionarCashListener());
        this.principalView.addTorneioListener(new AdicionarTorneioListener());

    }

    public void salvarSessao() {
        String duracao = JOptionPane.showInputDialog("Insira a duração da sessão:");

        Sessao sessao = new Sessao();

        sessao.setDuracaoHoras(Double.parseDouble(duracao));
        sessao.setDataSessao(new Date(System.currentTimeMillis()));
        sessao.setUsuario(usuario);

        SessaoJpaController sJPA = new SessaoJpaController(emf);
        CashJpaController cJPA = new CashJpaController(emf);
        BankrollJpaController bJPA = new BankrollJpaController(emf);
        TorneioJpaController tJPA = new TorneioJpaController(emf);

        sJPA.create(sessao);

        for (int i = 0; i < model.getSize(); i++) {
            if (model.getElementAt(i) instanceof Cash) {
                Cash cash = (Cash) model.getElementAt(i);
                Bankroll get = cashMap.get(cash);

                cash.setSala(get.getSala());

                get.setValorAtual(get.getValorAtual() + cash.getValorGanho());

                cash.setSessao(sessao);
                cJPA.create(cash);
            } else {
                Torneio t = (Torneio) model.getElementAt(i);
                t.setSessao(sessao);

                Bankroll get = torneioMap.get(t);
                double novoValor = get.getValorAtual() - t.getBuyIn() + t.getValorGanho() - t.getQtdRebuys() * t.getValorRebuy() - t.getValorAddOn();
                get.setValorAtual(novoValor);

                System.out.println(get);

                tJPA.create(t);
            }
        }
        salvarBankroll(bJPA);
    }

    private void salvarBankroll(BankrollJpaController bJPA) {
        Set<Cash> keySet = cashMap.keySet();
        Set<Torneio> keySet1 = torneioMap.keySet();
        Iterator<Torneio> iterator1 = keySet1.iterator();
        Iterator<Cash> iterator = keySet.iterator();

        while (iterator.hasNext()) {
            try {
                bJPA.edit(cashMap.get(iterator.next()));
            } catch (Exception ex) {
                Logger.getLogger(SessaoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        while(iterator1.hasNext()){
            try {
                bJPA.edit(torneioMap.get(iterator1.next()));
            } catch (Exception ex) {
                Logger.getLogger(SessaoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    public void limparDadosJogos() {
        model = new DefaultListModel();
        principalView.setModelListaJogos(model);
        cashMap.clear();
        torneioMap.clear();
    }

    class AdicionarTorneioListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Torneio torneio = new Torneio();

            torneio.setBuyIn(Integer.parseInt(principalView.getBuyInTorneio()));
            torneio.setDuracao(Double.parseDouble(principalView.getDuracaoTorneio()));
            torneio.setGenero(((TipoTorneioGenero) principalView.getSelectedGeneroTorneio()).getValor());
            torneio.setTipo(((TipoTorneio) principalView.getSelectedTipoTorneio()).getValor());
            torneio.setPosicao(Integer.parseInt(principalView.getPosicaoTorneio()));
            torneio.setQtdJogadoresMesa(Integer.parseInt(principalView.getJgsPorMesaTorneio()));
            torneio.setSala(((Bankroll) principalView.getSelectedSalaTorneio()).getSala());
            torneio.setValorGanho(Double.parseDouble(principalView.getValorGanhoTorneio()));
            torneio.setTotalJogadores(Integer.parseInt(principalView.getNumeroJogadoresTorneio()));

            if (principalView.isRebuy()) {
                torneio.setValorAddOn(Double.parseDouble(principalView.getValorAddOn()));
                torneio.setValorRebuy(Double.parseDouble(principalView.getValorRebuy()));
                torneio.setQtdRebuys(Integer.parseInt(principalView.getQtdRebuy()));
            }

            model.addElement(torneio);
            torneioMap.put(torneio, (Bankroll) principalView.getSelectedSalaTorneio());

            principalView.limparAbaTorneios();
        }

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
            cash.setQtdJogadoresMesa(Integer.parseInt(principalView.getJogadoresPorMesaCash()));

            model.addElement(cash);

            cashMap.put(cash, (Bankroll) principalView.getSalaSelected());

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
