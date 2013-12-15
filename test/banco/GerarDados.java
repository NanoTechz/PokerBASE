/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import jpa.BankrollJpaController;
import jpa.CashJpaController;
import jpa.SalaJpaController;
import jpa.SessaoJpaController;
import jpa.TorneioJpaController;
import jpa.UsuarioJpaController;
import model.Cash;
import model.Usuario;
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
