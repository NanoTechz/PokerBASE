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

/**
 *
 * @author augusto
 */
public class DialogView extends JDialog implements View {
    
    private final BasicaView view;
    
    @Override
    public void centralizarTela() {
       view.centralizarTela();
    }
    
    @Override
    public void erroMensagem(String mensagemErro){
        view.erroMensagem(mensagemErro);
    }

    @Override
    public void mensagem(String mensagem){
        view.mensagem(mensagem);
    }
    
    class CancelarListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            dispose();
        }
        
    }

    public DialogView() {
        this.view = new BasicaView(this);
    }

    public DialogView(Frame owner) {
        super(owner);
        this.view = new BasicaView(this);
    }

    public DialogView(Frame owner, boolean modal) {
        super(owner, modal);
        this.view = new BasicaView(this);
    }

    public DialogView(Frame owner, String title) {
        super(owner, title);
        this.view = new BasicaView(this);
    }

    public DialogView(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        this.view = new BasicaView(this);
    }

    public DialogView(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
        this.view = new BasicaView(this);
    }

    public DialogView(Dialog owner) {
        super(owner);
        this.view = new BasicaView(this);
    }

    public DialogView(Dialog owner, boolean modal) {
        super(owner, modal);
        this.view = new BasicaView(this);
    }

    public DialogView(Dialog owner, String title) {
        super(owner, title);
        this.view = new BasicaView(this);
    }

    public DialogView(Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
        this.view = new BasicaView(this);
    }

    public DialogView(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
        this.view = new BasicaView(this);
    }

    public DialogView(Window owner) {
        super(owner);
        this.view = new BasicaView(this);
    }

    public DialogView(Window owner, ModalityType modalityType) {
        super(owner, modalityType);
        this.view = new BasicaView(this);
    }

    public DialogView(Window owner, String title) {
        super(owner, title);
        this.view = new BasicaView(this);
    }

    public DialogView(Window owner, String title, ModalityType modalityType) {
        super(owner, title, modalityType);
        this.view = new BasicaView(this);
    }

    public DialogView(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
        super(owner, title, modalityType, gc);
        this.view = new BasicaView(this);
    }
    
   
}
