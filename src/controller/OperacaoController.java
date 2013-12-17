/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import jpa.BankrollJpaController;
import jpa.OperacaoJpaController;
import model.Bankroll;
import model.Operacao;
import model.Usuario;
import model.auxiliar.TipoOperacao;
import util.AutenticaCampo;
import view.PrincipalFrame;

/**
 *
 * @author augusto
 */
public class OperacaoController {

    private PrincipalFrame principalView;
    private EntityManagerFactory emf;
    private Usuario usuario;
    private BankrollController bankrollController;

    public OperacaoController(PrincipalFrame principalView, EntityManagerFactory emf, Usuario usuario) {
        this.principalView = principalView;
        this.emf = emf;
        this.usuario = usuario;
        this.bankrollController = new BankrollController();
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }
    
    public void atualizarComponentes(){
        this.principalView.setModelSalaBox(bankrollController.getBankrollComboBoxModel(usuario));
        this.principalView.setModelListaOperacoes(getModelListaOperacao());
    }
    
    public DefaultComboBoxModel getModelTipoOperacaoBox() {
        TipoOperacao[] values = TipoOperacao.values();
        DefaultComboBoxModel model = new DefaultComboBoxModel(values);

        return model;
    }

    public DefaultListModel getModelListaOperacao() {
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

    public void salvarOperacao() {
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
        
        
        
        if (this.principalView.getDataOperacao() == null) {
            this.principalView.erroMensagem("Insira uma data válida.");
            this.principalView.limparDadosOperacao();
            return;
        }
        
        Date data = new Date(this.principalView.getDataOperacao().getTime());
        
        Operacao op = new Operacao();
        op.setTipo(tipo.valor);
        op.setBankroll(banca);
        op.setValor(Double.parseDouble(campo));
        op.setDataOperacao(data);
        
   

        BankrollJpaController bJPA = new BankrollJpaController(getEmf());

        Bankroll newBanca = bJPA.findBankRoll(banca.getId());
        OperacaoJpaController oJPA = new OperacaoJpaController(getEmf());

        newBanca.setUsuario(usuario);

        newBanca.setListaOperacao(oJPA.findOperacaoBankroll(newBanca));

        newBanca.atualizarValorAtual(tipo, Double.parseDouble(campo));
        
        if(newBanca.getValorAtual() < 0){
            principalView.erroMensagem("Valor ultrapassa o limite.");
            principalView.limparDadosOperacao();
            return;
        }

        try {
            bJPA.edit(newBanca);
        } catch (Exception ex) {
            Logger.getLogger(PrincipalViewController.class.getName()).log(Level.SEVERE, null, ex);
            this.principalView.erroMensagem("Erro ao atualizar o bankroll!");
            return;
        }

        oJPA.create(op);
    }
}
