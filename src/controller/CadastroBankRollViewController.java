/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.action.AbrirCadastroSalaListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.DefaultComboBoxModel;
import jpa.BankrollJpaController;
import model.Bankroll;
import model.Sala;
import model.Usuario;
import view.CadastroBankrollDialog;

/**
 *
 * @author augusto
 */
public class CadastroBankRollViewController extends ControllerView {

    private CadastroBankrollDialog cadastroBankRollView;
    private List<Sala> listaSala;
    private Usuario usuario;

    private BankrollJpaController bankRollJPA;
    private final SalaController salaController;
    
    public CadastroBankRollViewController(CadastroBankrollDialog cadastroBankRollView, Usuario usuario, EntityManagerFactory emf) {
        super(emf, cadastroBankRollView);
        this.cadastroBankRollView = cadastroBankRollView;
        this.usuario = usuario;
        
        this.bankRollJPA = new BankrollJpaController(emf);
        salaController = new SalaController(getEmf());
        
        this.cadastroBankRollView.setModelListaSala(salaController.getSalasComboBoxModel());

        this.cadastroBankRollView.addAdicionarSalaListener(new AdicionarSalaListener());
        this.cadastroBankRollView.addAdicionarBankrollListener(new AdicionarBankrollListener());
        
    }

    class AdicionarSalaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            AbrirCadastroSalaListener cadastroSala = new AbrirCadastroSalaListener(null, getEmf(), usuario);
            cadastroSala.actionPerformed(ae);
            
            
            DefaultComboBoxModel model = salaController.getSalasComboBoxModel();
            model.setSelectedItem(model.getElementAt(model.getSize() - 1));
            cadastroBankRollView.setModelListaSala(model);

        }

    }

    class AdicionarBankrollListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            double valor;
            Sala sala;

            try {
                valor = Double.parseDouble(cadastroBankRollView.getValor());
                sala = (Sala) cadastroBankRollView.getSalaSelecionada();
            } catch (Exception e) {
                cadastroBankRollView.setValor(0);
                cadastroBankRollView.erroMensagem("Erro na conversão do valor ou \"sala\" não selecionada.");
                return;
            }
            
            Bankroll banca = new Bankroll(usuario, sala, valor);
            banca.setValorAtual(valor);
            banca.setDataCriacao(new Date(System.currentTimeMillis()));
            
            bankRollJPA.create(banca);
            
            usuario.getListaBankRolls().add(banca);
            
            cadastroBankRollView.mensagem("Bankroll add ao \""+usuario.getUsername()+"\".");
            cadastroBankRollView.dispose();
        }

    }
}
