/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import controller.LoginViewController;
import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;
import model.Usuario;
import util.EntityManagerUtil;
import view.LoginFrame;

/**
 *
 * @author augusto
 */
public class PokerBASE {

    public static void main(String[] args) {
        try {

            javax.swing.UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());

        } catch (UnsupportedLookAndFeelException | ParseException ex) {
            Logger.getLogger(PokerBASE.class.getName()).log(Level.SEVERE, null, ex);
        }

        Usuario usuario = new Usuario();
        LoginFrame loginFrame = new LoginFrame();

        LoginViewController controller = new LoginViewController(usuario, loginFrame, EntityManagerUtil.emf);
        loginFrame.setVisible(true);
    }
}
