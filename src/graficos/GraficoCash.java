/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graficos;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author augusto
 */
public class GraficoCash implements Grafico{
    private JFreeChart grafico;
    private DefaultCategoryDataset dataset;

    public GraficoCash(String title, String xLabel, String yLabel) {
        dataset = new DefaultCategoryDataset();
        grafico = ChartFactory.createBarChart(title, xLabel, yLabel, dataset);
    }
    
    public void addValue(String limite, double valor){
        dataset.addValue(valor, "profit", limite);
    }
    
    public void limparGrafico(){
        dataset.clear();
    }
    
    @Override
    public JPanel getPanel() {
        return new ChartPanel(grafico);
    }
    
}
