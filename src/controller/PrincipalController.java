/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import jpa.BankrollJpaController;
import jpa.SalaJpaController;
import jpa.SessaoJpaController;
import jpa.UsuarioJpaController;
import model.Bankroll;
import model.Sessao;
import model.Usuario;
import util.FecharConexaoWindowListener;
import view.CadastroBankrollDialog;
import view.PrincipalFrame;

/**
 *
 * @author augusto
 */
public class PrincipalController extends Controller{
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
        
        this.principalView.setUserName(usuario.getUsername());
        this.principalView.centralizarTela();
        this.principalView.addWindowListener(new FecharConexaoWindowListener());
        
        this.usuarioJPA = new UsuarioJpaController(emf);
        this.bankrollJPA = new BankrollJpaController(emf);
        this.salaJPA = new SalaJpaController(emf);
        this.sessaoJPA = new SessaoJpaController(emf);
        
        pegarInfoUsuario(usuario);
        
        if(usuario.getListaBankRolls().isEmpty()){
            CadastroBankrollDialog cadastroView = new CadastroBankrollDialog(principalView, true);
            CadastroBankRollController bankrollController = new CadastroBankRollController(cadastroView, usuario, emf);
            
            cadastroView.setVisible(true);
        }
        
    }
    
    public void pegarInfoUsuario(Usuario usuario){
        List<Bankroll> listaBankRoll = bankrollJPA.findBankRollUsuario(usuario);
        usuario.setListaBankRolls(listaBankRoll);
        
        List<Sessao> listaSessao = sessaoJPA.findSessaoUsuario(usuario);
        usuario.setListaSessao(listaSessao);
    }
}
