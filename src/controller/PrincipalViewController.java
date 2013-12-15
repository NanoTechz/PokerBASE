/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import jpa.CashJpaController;
import jpa.TorneioJpaController;
import model.Bankroll;
import model.Cash;
import model.Torneio;
import model.Usuario;
import org.jfree.data.xy.XYSeries;
import util.Calculadora;
import view.CadastroBankrollDialog;
import view.PrincipalFrame;

/**
 *
 * @author augusto
 */
public class PrincipalViewController extends ControllerView {

    private final Usuario usuario;
    private final PrincipalFrame principalView;
    private final UsuarioController usuarioController;
    private OperacaoController operacaoControllerAux;
    private CashController cashController;
    private TorneioController torneioController;
    private SessaoController sessaoController;

    public PrincipalViewController(Usuario usuario, PrincipalFrame principalView, EntityManagerFactory emf) {
        super(emf, principalView);
        this.usuario = usuario;
        this.principalView = principalView;
        this.usuarioController = new UsuarioController(usuario);
        this.operacaoControllerAux = new OperacaoController(principalView, emf, usuario);
        this.cashController = new CashController(usuario, principalView, emf);
        this.torneioController = new TorneioController(usuario, principalView, emf);
        sessaoController = new SessaoController(usuario, principalView, emf);

        usuarioController.pegarListaBankrollSessao(usuario, getEmf());
        this.principalView.addModelTabelaCash(this.cashController.getModelCash());
        this.principalView.addModelTabelaTorneio(this.torneioController.getModelTorneio());

        verificarBankroll();
        addListener();
        inicializarComponentes();
        gerarGrafico();
    }

    private void addListener() {
        this.principalView.addBotaoBankrollListener(new ListarBankrollListener());
        this.principalView.addBankrollAddBotaoListener(new AbrirBankrollListener());
        this.principalView.addSalvarOperacaoListener(new SalvarOperacaoListener());
        this.principalView.addConfirmarMudarSenha(new AlterarSenhaListener());
        this.principalView.addSessaoListener(new SalvarSessaoListener());
    }

    private void inicializarComponentes() {
        usuarioController.pegarListaBankrollSessao(usuario, getEmf());

        this.principalView.setUserName(usuario.getUsername());
        this.principalView.setBankrollLabel("$ " + Calculadora.somaBankroll(usuario.getListaBankRolls()));
        this.principalView.setModelListaSala(operacaoControllerAux.getModelSalaBox());
        this.operacaoControllerAux.atualizarComponentes();

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
        roi = (torneioJpaController.totalValorGanho(usuario) - totalBuyIN) / totalBuyIN;
        mediaBuyIn = totalBuyIN / findTorneioUsuario.size();

        for (int i = 0; i < findTorneioUsuario.size(); i++) {
            soma += findTorneioUsuario.get(i).getValorGanho() - findTorneioUsuario.get(i).getBuyIn();

            serie.add(i + 1, soma);

            somaAjuste += mediaBuyIn * roi;
            serieRoiEsperado.add(i, somaAjuste);

        }

        int count = 1;
        soma = 0;
        for (Cash cash : findCashUsuario) {
            soma += cash.getValorGanho();
            cashSeries.add(count, soma, true);

            count++;
        }

        this.principalView.addDados(serie);
        this.principalView.addDados(serieRoiEsperado);
        this.principalView.addDados(cashSeries);
    }

    private void verificarBankroll() {
        if (this.usuario.getListaBankRolls().isEmpty()) {
            abrirBankrollDialog();
        }
    }

    private void abrirBankrollDialog() {
        CadastroBankrollDialog cadastroView = new CadastroBankrollDialog(principalView, true);
        CadastroBankRollViewController bankrollController = new CadastroBankRollViewController(cadastroView, usuario, getEmf());
        cadastroView.setVisible(true);
    }

    /**
     * ActionListener
     */
    class ListarBankrollListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            StringBuilder msg = new StringBuilder();

            for (Bankroll b : usuario.getListaBankRolls()) {
                msg.append("Sala: ").append(b.getSala().getRazaoSocial()).append(" , Valor: $ ").append(b.getValorAtual()).append("\n");
            }

            principalView.mensagem(msg.toString());
        }

    }

    class AbrirBankrollListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            abrirBankrollDialog();
            inicializarComponentes();
        }
    }

    class SalvarOperacaoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            operacaoControllerAux.salvarOperacao();
            inicializarComponentes();
            principalView.limparDadosOperacao();
        }

    }

    class AlterarSenhaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            usuarioController.alterarSenha(principalView, principalView.getSenha(), principalView.getSenhaConfirmacao(), getEmf());
            principalView.limparPerfil();

        }

    }

    class SalvarSessaoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            sessaoController.salvarSessao();
            sessaoController.limparDadosJogos();
            inicializarComponentes();
        }

    }
}
