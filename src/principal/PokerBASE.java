/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import controller.LoginController;
import model.Usuario;
import util.EntityManagerUtil;
import view.LoginFrame;

/**
 *
 * @author augusto
 */
public class PokerBASE {

    public static void main(String[] args) {
        Usuario usuario = new Usuario();
        LoginFrame loginFrame = new LoginFrame();
        
        
        LoginController controller = new LoginController(usuario, loginFrame, EntityManagerUtil.emf);
        
        
        loginFrame.setVisible(true);
    }
}
