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
public class DialogView extends JDialog implements View {
    
    @Override
    public void centralizarTela() {
        Desktop.centralizarFrame(this);
    }
    
    @Override
    public void erroMensagem(String mensagemErro){
        JOptionPane.showMessageDialog(this, mensagemErro, "Erro:", JOptionPane.ERROR_MESSAGE); 
    }

    @Override
    public void mensagem(String mensagem){
        JOptionPane.showMessageDialog(this, mensagem);
    }
    
    class CancelarListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            dispose();
        }
        
    }
    
    public DialogView() {
    }

    public DialogView(Frame owner) {
        super(owner);
    }

    public DialogView(Frame owner, boolean modal) {
        super(owner, modal);
    }

    public DialogView(Frame owner, String title) {
        super(owner, title);
    }

    public DialogView(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
    }

    public DialogView(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
    }

    public DialogView(Dialog owner) {
        super(owner);
    }

    public DialogView(Dialog owner, boolean modal) {
        super(owner, modal);
    }

    public DialogView(Dialog owner, String title) {
        super(owner, title);
    }

    public DialogView(Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
    }

    public DialogView(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
    }

    public DialogView(Window owner) {
        super(owner);
    }

    public DialogView(Window owner, ModalityType modalityType) {
        super(owner, modalityType);
    }

    public DialogView(Window owner, String title) {
        super(owner, title);
    }

    public DialogView(Window owner, String title, ModalityType modalityType) {
        super(owner, title, modalityType);
    }

    public DialogView(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
        super(owner, title, modalityType, gc);
    }    
}
