/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import controller.LoginController;
import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel;
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

            javax.swing.UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());

        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PokerBASE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(PokerBASE.class.getName()).log(Level.SEVERE, null, ex);
        }

        Usuario usuario = new Usuario();
        LoginFrame loginFrame = new LoginFrame();

        LoginController controller = new LoginController(usuario, loginFrame, EntityManagerUtil.emf);
        loginFrame.setVisible(true);
    }
}
