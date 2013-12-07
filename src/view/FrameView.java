/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import util.Desktop;

/**
 *
 * @author augusto
 */
public abstract class FrameView extends JFrame implements View{

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

    public FrameView() throws HeadlessException {
    }

    public FrameView(GraphicsConfiguration gc) {
        super(gc);
    }

    public FrameView(String title) throws HeadlessException {
        super(title);
    }

    public FrameView(String title, GraphicsConfiguration gc) {
        super(title, gc);
    }
}
