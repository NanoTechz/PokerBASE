/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JOptionPane;

/**
 *
 * @author augusto
 */
public class BasicaView implements View {

    private Window janela;

    public BasicaView(Window janela) {
        this.janela = janela;
    }

    @Override
    public void centralizarTela() {
        Dimension dimensaoMonitor = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dimensaoMonitor.width - janela.getWidth()) / 2;
        int y = (dimensaoMonitor.height - janela.getHeight()) / 2;
        janela.setLocation(x, y);
    }

    @Override
    public void erroMensagem(String mensagemErro) {
        JOptionPane.showMessageDialog(janela, mensagemErro, "Erro:", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void mensagem(String mensagem) {
        JOptionPane.showMessageDialog(janela, mensagem, "Mensagem:", JOptionPane.INFORMATION_MESSAGE);
    }

}
