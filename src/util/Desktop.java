/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JFrame;

/**
 *
 * @author augusto
 */
public class Desktop {
    public static void centralizarFrame(Window janela){
        Dimension dimensaoMonitor = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dimensaoMonitor.width - janela.getWidth())/2;
        int y = (dimensaoMonitor.height - janela.getHeight())/2;
        janela.setLocation(x, y);
    }
}
