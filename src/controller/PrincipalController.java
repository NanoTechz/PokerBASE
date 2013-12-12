/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import jpa.BankrollJpaController;
import jpa.CashJpaController;
import jpa.OperacaoJpaController;
import jpa.TorneioJpaController;
import model.Bankroll;
import model.Cash;
import model.Operacao;
import model.Torneio;
import model.Usuario;
import model.auxiliar.TipoOperacao;
import org.jfree.data.xy.XYSeries;
import util.AutenticaCampo;
import util.Calculadora;
import view.CadastroBankrollDialog;
import view.PrincipalFrame;

/**
 *
 * @author augusto
 */
public class PrincipalController extends ControllerView {

    private final Usuario usuario;
    private final PrincipalFrame principalView;
    private final UsuarioController usuarioController;

    public PrincipalController(Usuario usuario, PrincipalFrame principalView, EntityManagerFactory emf) {
        super(emf, principalView);
        this.usuario = usuario;
        this.principalView = principalView;
        this.usuarioController = new UsuarioController(usuario);

        usuarioController.pegarListaBankrollSessao(usuario, getEmf());
        verificarBankroll();
        addListener();
        inicializarComponentes();
        gerarGrafico();
    }

    private void addListener() {
        this.principalView.addBotaoBankrollListener(new ListarBankrollListener());
        this.principalView.addBankrollAddBotaoListener(new AbrirBankrollListener());
        this.principalView.addSalvarOperacaoListener(new SalvarOperacaoListener());
    }

    private void inicializarComponentes() {
        usuarioController.pegarListaBankrollSessao(usuario, getEmf());

        this.principalView.setUserName(usuario.getUsername());
        this.principalView.setBankrollLabel("$ " + Calculadora.somaBankroll(usuario.getListaBankRolls()));
        this.principalView.setModelTipoOperacaoBox(getModelTipoOperacaoBox());
        this.principalView.setModelSalaBox(getModelSalaBox());
        this.principalView.setModelListaOperacoes(getModelListaOperacao());
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
        CadastroBankRollController bankrollController = new CadastroBankRollController(cadastroView, usuario, getEmf());
        cadastroView.setVisible(true);
        cadastroView = null;
    }

    private DefaultComboBoxModel getModelTipoOperacaoBox() {
        TipoOperacao[] values = TipoOperacao.values();
        DefaultComboBoxModel model = new DefaultComboBoxModel(values);

        return model;
    }

    private DefaultComboBoxModel getModelSalaBox() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Bankroll b : usuario.getListaBankRolls()) {
            model.addElement(b);
        }

        return model;
    }

    private DefaultListModel getModelListaOperacao() {
        DefaultListModel model = new DefaultListModel();
        OperacaoJpaController oJPA = new OperacaoJpaController(getEmf());

        try {
            List<Operacao> findOperacaoUsuario = oJPA.findOperacaoUsuario(usuario, 10, 0);

            if (findOperacaoUsuario.isEmpty()) {
                model.addElement("nenhuma operacao");
            } else {
                for (Operacao operacao : findOperacaoUsuario) {
                    model.addElement(operacao);
                }
            }
        } catch (Exception e) {
        }

        return model;
    }

    private void salvarOperacao() {
        TipoOperacao tipo = (TipoOperacao) this.principalView.getSelectedTipoOperacaoBox();
        Bankroll banca = (Bankroll) this.principalView.getSelectedSalaBox();
        String campo = this.principalView.getValorOperacao();

        AutenticaCampo autentica = new AutenticaCampo();

        if (autentica.verificarCampoNumero(campo, principalView)) {
            this.principalView.limparDadosOperacao();
            return;
        }

        if (Double.parseDouble(campo) < 0) {
            this.principalView.erroMensagem("Insira um número positivo.");
            this.principalView.limparDadosOperacao();
            return;
        }
        Date data = autentica.verificarCampoData(this.principalView.getDataOperacao());
        if (data == null) {
            this.principalView.erroMensagem("Insira uma data válida.");
            this.principalView.limparDadosOperacao();
            return;
        }
        
        Operacao op = new Operacao();
        op.setTipoOperacao(tipo);
        op.setBankroll(banca);
        op.setValor(Double.parseDouble(campo));
        op.setDataOperacao(data);

        BankrollJpaController bJPA = new BankrollJpaController(getEmf());

        Bankroll newBanca = bJPA.findBankRoll(banca.getId());
        OperacaoJpaController oJPA = new OperacaoJpaController(getEmf());

        newBanca.setUsuario(usuario);

        newBanca.setListaOperacao(oJPA.findOperacaoBankroll(newBanca));

        newBanca.atualizarValorAtual(tipo, Double.parseDouble(campo));

        try {
            bJPA.edit(newBanca);
        } catch (Exception ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            this.principalView.erroMensagem("Erro ao atualizar o bankroll!");
            return;
        }

        oJPA.create(op);
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
            salvarOperacao();
            inicializarComponentes();
            principalView.limparDadosOperacao();
        }

    }
}
