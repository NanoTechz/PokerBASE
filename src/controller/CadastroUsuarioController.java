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
import view.CadastroUsuarioDialog;

/**
 *
 * @author augusto
 */
public class CadastroUsuarioController extends Controller {

    private CadastroUsuarioDialog cadastroView;
    private Usuario usuario;
    private UsuarioJpaController usuarioJPA;

    public CadastroUsuarioController(CadastroUsuarioDialog cadastroView, Usuario usuario, EntityManagerFactory emf) {
        super(emf);
        this.cadastroView = cadastroView;
        this.usuario = usuario;
        this.usuarioJPA = new UsuarioJpaController(super.getEmf());

        this.cadastroView.addAdicionarListener(new AdicionarListener());
        this.cadastroView.centralizarTela();
    }

    class AdicionarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (cadastroView.getSenha().isEmpty() || cadastroView.getSenhaConfirmaacao().isEmpty()
                    || cadastroView.getNome().isEmpty() || cadastroView.getUserName().isEmpty()) {
                cadastroView.erroMensagem("Algum campo vazio!");
                return;
            }

            if (!(verificarSenha(cadastroView.getSenha(), cadastroView.getSenhaConfirmaacao()))) {
                cadastroView.erroMensagem("Senhas não batem!");
                cadastroView.limparCampoSenha();
                return;
            }

            if (verificarUserName(cadastroView.getUserName())) {
                cadastroView.erroMensagem("Username já em uso");
                return;
            }

            usuario = new Usuario();

            usuario.setNome(cadastroView.getNome());
            usuario.setUsername(cadastroView.getUserName());

            try {
                usuario.setSenha(Criptografia.cifrar(cadastroView.getSenha()));
                usuarioJPA.create(usuario);

                if (usuario.getId() != 0) {
                    cadastroView.erroMensagem("Usuario cadastrado com sucesso!");
                }
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                cadastroView.erroMensagem("Erro inesperado!");
                Logger.getLogger(CadastroUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                cadastroView.dispose();
            }
        }
    }

    private boolean verificarSenha(String senha, String confirmacao) {
        return senha.equals(confirmacao);
    }

    private boolean verificarUserName(String userName) {
        return (usuarioJPA.findUsuario(userName) != null);
    }

}
