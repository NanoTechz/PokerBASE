/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
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
public class OperacaoControllerAux {

    private PrincipalFrame principalView;
    private EntityManagerFactory emf;
    private Usuario usuario;

    public OperacaoControllerAux(PrincipalFrame principalView, EntityManagerFactory emf, Usuario usuario) {
        this.principalView = principalView;
        this.emf = emf;
        this.usuario = usuario;
    }

    public EntityManagerFactory getEmf() {
        return emf;
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
}
