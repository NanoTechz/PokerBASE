/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import util.FecharConexaoWindowListener;

/**
 *
 * @author augusto
 */
public abstract class FrameView extends JFrame implements View{
    
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

    public FrameView() throws HeadlessException {
        
        super.addWindowListener(new FecharConexaoWindowListener());
        this.view = new BasicaView(this);
    }

    public FrameView(GraphicsConfiguration gc) {
        super(gc);
        super.addWindowListener(new FecharConexaoWindowListener());
        this.view = new BasicaView(this);
    }

    public FrameView(String title) throws HeadlessException {
        super(title);
        super.addWindowListener(new FecharConexaoWindowListener());
        this.view = new BasicaView(this);
    }

    public FrameView(String title, GraphicsConfiguration gc) {
        super(title, gc);
        super.addWindowListener(new FecharConexaoWindowListener());
        this.view = new BasicaView(this);
    }
}
