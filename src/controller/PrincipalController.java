/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import jpa.BankrollJpaController;
import jpa.CashJpaController;
import jpa.SalaJpaController;
import jpa.SessaoJpaController;
import jpa.TorneioJpaController;
import jpa.UsuarioJpaController;
import model.Bankroll;
import model.Cash;
import model.Sessao;
import model.Torneio;
import model.Usuario;
import org.jfree.data.xy.XYSeries;
import util.Calculadora;
import util.FecharConexaoWindowListener;
import view.CadastroBankrollDialog;
import view.PrincipalFrame;

/**
 *
 * @author augusto
 */
public class PrincipalController extends Controller {

    private Usuario usuario;
    private PrincipalFrame principalView;

    private UsuarioJpaController usuarioJPA;
    private BankrollJpaController bankrollJPA;
    private SessaoJpaController sessaoJPA;
    private SalaJpaController salaJPA;

    public PrincipalController(Usuario usuario, PrincipalFrame principalView, EntityManagerFactory emf) {
        super(emf);
        this.usuario = usuario;
        this.principalView = principalView;
        
        this.usuarioJPA = new UsuarioJpaController(emf);
        this.bankrollJPA = new BankrollJpaController(emf);
        this.salaJPA = new SalaJpaController(emf);
        this.sessaoJPA = new SessaoJpaController(emf);

        pegarDadosUsuario(usuario);
        verificarBankroll();

        this.principalView.setUserName(usuario.getUsername());
        this.principalView.setBankrollLabel("$ " + Calculadora.somaBankroll(usuario.getListaBankRolls()));
        this.principalView.centralizarTela();
        this.principalView.addWindowListener(new FecharConexaoWindowListener());
        this.principalView.addBotaoBankrollListener(new ListarBankrollListener());

        
        gerarGrafico();
    }

    private void pegarDadosUsuario(Usuario usuario) {
        List<Bankroll> listaBankRoll = bankrollJPA.findBankRollUsuario(usuario);
        usuario.setListaBankRolls(listaBankRoll);

        List<Sessao> listaSessao = sessaoJPA.findSessaoUsuario(usuario);
        usuario.setListaSessao(listaSessao);
    }


     private void gerarGrafico() {
     TorneioJpaController torneioJpaController = new TorneioJpaController(getEmf());
     List<Torneio> findTorneioUsuario = torneioJpaController.findTorneioUsuario(usuario);
        
     CashJpaController cashJpaController = new CashJpaController(getEmf());
     List<Cash> findCashUsuario = cashJpaController.findCashUsuario(usuario);
        
     XYSeries serie = new XYSeries("torneios");
     XYSeries cashSeries = new XYSeries("cash");
     XYSeries serieRoiEsperado = new XYSeries("roi");
        
     double soma = 0, somaAjuste = 0, roi, mediaBuyIn, totalBuyIN;
        
     totalBuyIN = torneioJpaController.totalBuyIn(usuario); 
     roi = (torneioJpaController.totalValorGanho(usuario) - totalBuyIN)/totalBuyIN;
     mediaBuyIn = totalBuyIN/findTorneioUsuario.size();
        
     for (int i = 0; i < findTorneioUsuario.size(); i++) {
     soma+= findTorneioUsuario.get(i).getValorGanho() - findTorneioUsuario.get(i).getBuyIn();
            
     serie.add(i+1, soma);
            
     somaAjuste+= mediaBuyIn*roi;
     serieRoiEsperado.add(i,somaAjuste);
            
     }
        
     int count=1;
     soma = 0;
     for (Cash cash : findCashUsuario) {
     soma += cash.getValorGanho();
     cashSeries.add(count, soma, true);
            
     count++;
     }
        
     this.principalView.addDados(serie);
     this.principalView.addDados(serieRoiEsperado);
     this.principalView.addDados( cashSeries);
     }
     
    private void verificarBankroll() {
        if (this.usuario.getListaBankRolls().isEmpty()) {
            CadastroBankrollDialog cadastroView = new CadastroBankrollDialog(principalView, true);
            CadastroBankRollController bankrollController = new CadastroBankRollController(cadastroView, usuario, getEmf());
            cadastroView.setVisible(true);
        }
    }
    
    
    /** ActionListener */
    
    class ListarBankrollListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
           StringBuilder msg = new StringBuilder();
           
            for (Bankroll b: usuario.getListaBankRolls()) {
              msg.append("Sala: ").append(b.getSala().getRazaoSocial()).append(" , Valor: $ ").append(b.getValorAtual()).append("\n");
            }
            
            principalView.mensagem(msg.toString());
        }
        
    }
}
