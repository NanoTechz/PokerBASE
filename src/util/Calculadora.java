/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.List;
import model.Bankroll;

/**
 *
 * @author augusto
 */
public class Calculadora {

    public static double somaBankroll(List<Bankroll> bankroll) {
        double soma = 0;

        for (Bankroll bankroll1 : bankroll) {
            soma += bankroll1.getValorAtual();
        }

        return soma;
    }

    public static double roi(double ganho, double buyin, int totalJogados) {
        if (buyin == 0) {
            return 0;
        }
        
        return (ganho - (buyin * totalJogados)) / (buyin * totalJogados);
    }

    public static double roi(double totalGanho, double totalBuyin) {
        if (totalBuyin == 0) {
            return 0;
        }

        return (totalGanho - totalBuyin) / totalBuyin;
    }
}
