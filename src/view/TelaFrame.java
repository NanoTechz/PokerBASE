/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import util.Desktop;

/**
 *
 * @author augusto
 */
public class TelaFrame extends JFrame implements Tela{

    @Override
    public void centraliarTela() {
        Desktop.centralizarFrame(this);
    }
    
    @Override
    public void mensagemErro(String mensagemErro){
        JOptionPane.showMessageDialog(this, mensagemErro); 
    }

    public TelaFrame() throws HeadlessException {
    }

    public TelaFrame(GraphicsConfiguration gc) {
        super(gc);
    }

    public TelaFrame(String title) throws HeadlessException {
        super(title);
    }

    public TelaFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
    }
}
