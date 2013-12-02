/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import util.Desktop;

/**
 *
 * @author augusto
 */
public class TelaDialog extends JDialog implements Tela {
    
    @Override
    public void centralizarTela() {
        Desktop.centralizarFrame(this);
    }
    
    @Override
    public void mensagemErro(String mensagemErro){
        JOptionPane.showMessageDialog(this, mensagemErro); 
    }
    
    class CancelarListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            dispose();
        }
        
    }
    
    public TelaDialog() {
    }

    public TelaDialog(Frame owner) {
        super(owner);
    }

    public TelaDialog(Frame owner, boolean modal) {
        super(owner, modal);
    }

    public TelaDialog(Frame owner, String title) {
        super(owner, title);
    }

    public TelaDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
    }

    public TelaDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
    }

    public TelaDialog(Dialog owner) {
        super(owner);
    }

    public TelaDialog(Dialog owner, boolean modal) {
        super(owner, modal);
    }

    public TelaDialog(Dialog owner, String title) {
        super(owner, title);
    }

    public TelaDialog(Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
    }

    public TelaDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
    }

    public TelaDialog(Window owner) {
        super(owner);
    }

    public TelaDialog(Window owner, ModalityType modalityType) {
        super(owner, modalityType);
    }

    public TelaDialog(Window owner, String title) {
        super(owner, title);
    }

    public TelaDialog(Window owner, String title, ModalityType modalityType) {
        super(owner, title, modalityType);
    }

    public TelaDialog(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
        super(owner, title, modalityType, gc);
    }    
}
