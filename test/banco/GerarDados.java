/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import jpa.BankrollJpaController;
import jpa.CashJpaController;
import jpa.SalaJpaController;
import jpa.SessaoJpaController;
import jpa.TorneioJpaController;
import jpa.UsuarioJpaController;
import model.Bankroll;
import model.Cash;
import model.Sala;
import model.Sessao;
import model.Torneio;
import model.Usuario;
import seguranca.Criptografia;
import util.EntityManagerUtil;

/**
 *
 * @author augusto
 */
public class GerarDados {

    public static void main(String[] args) {

        EntityManagerFactory emf = EntityManagerUtil.emf;

        UsuarioJpaController uJPA = new UsuarioJpaController(emf);
        SalaJpaController sJPA = new SalaJpaController(emf);
        BankrollJpaController bJPA = new BankrollJpaController(emf);
        SessaoJpaController ssJPA = new SessaoJpaController(emf);
        CashJpaController cJPA = new CashJpaController(emf);
        TorneioJpaController tJPA = new TorneioJpaController(emf);

        Usuario user = uJPA.findUsuario("g");
        List<Cash> findCashUsuario = cJPA.findCashUsuario(user);
        for (Cash cash : findCashUsuario) {
            System.out.println(cash.getNumeroMaos());

        }
    }
}
