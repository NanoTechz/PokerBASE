/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author augusto
 */
public class FecharConexaoWindowListener implements WindowListener{

    @Override
    public void windowOpened(WindowEvent we) {
        //
    }

    @Override
    public void windowClosing(WindowEvent we) {
        System.out.println("***************fechando a conexão com o banco!***************");
       EntityManagerUtil.emf.close();
    }

    @Override
    public void windowClosed(WindowEvent we) {
      //
    }

    @Override
    public void windowIconified(WindowEvent we) {
       //
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
        //
    }

    @Override
    public void windowActivated(WindowEvent we) {
        //
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        //
    }
    
}
