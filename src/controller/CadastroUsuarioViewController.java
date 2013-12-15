/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import jpa.UsuarioJpaController;
import model.Usuario;
import seguranca.Criptografia;
import util.AutenticaCampo;
import view.CadastroUsuarioDialog;

/**
 *
 * @author augusto
 */
public class CadastroUsuarioViewController extends ControllerView {

    private final CadastroUsuarioDialog cadastroView;
    private Usuario usuario;
    private final UsuarioJpaController usuarioJPA;
    private final UsuarioController usuarioController;

    public CadastroUsuarioViewController(CadastroUsuarioDialog cadastroView, Usuario usuario, EntityManagerFactory emf) {
        super(emf, cadastroView);
        this.cadastroView = cadastroView;
        this.usuario = usuario;
        this.usuarioJPA = new UsuarioJpaController(super.getEmf());
        this.usuarioController = new UsuarioController(usuario);
        
        this.cadastroView.addAdicionarListener(new AdicionarListener());
    }

    class AdicionarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            if (!isCamposValidos()) {
                return;
            }

            usuario = new Usuario();

            usuario.setNome(cadastroView.getNome());
            usuario.setUsername(cadastroView.getUserName());

            try {
                usuario.setSenha(Criptografia.codificar(cadastroView.getSenha()));
                usuarioJPA.create(usuario);

                if (usuario.getId() != 0) {
                    cadastroView.mensagem("Usuario cadastrado com sucesso!");
                }
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                cadastroView.erroMensagem("Erro ao codificar senha!");
                Logger.getLogger(CadastroUsuarioViewController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                cadastroView.dispose();
            }
        }
    }

    private boolean verificarSenha(String senha, String confirmacao) {
        return senha.equals(confirmacao);
    }

    private boolean isCamposValidos() {
        AutenticaCampo autentica = new AutenticaCampo();
        
        if (autentica.isCampoVazio(cadastroView.getNome(), "nome", cadastroView)
                || autentica.isCampoVazio(cadastroView.getSenha(), "senha", cadastroView)
                || autentica.isCampoVazio(cadastroView.getSenhaConfirmacao(), "senha confirmação", cadastroView)
                || autentica.isCampoVazio(cadastroView.getUserName(), "username", cadastroView)) {
            //cadastroView.erroMensagem("Algum campo vazio!");
            return false;
        }
        if (!(verificarSenha(cadastroView.getSenha(), cadastroView.getSenhaConfirmacao()))) {
            cadastroView.erroMensagem("Senhas não batem!");
            cadastroView.limparCampoSenha();
            return false;
        }
        if (usuarioController.isUsernameEmUso(cadastroView.getUserName(), getEmf())) {
            cadastroView.erroMensagem("Username já em uso");
            return false;
        }
        
        return true;
    }

}
