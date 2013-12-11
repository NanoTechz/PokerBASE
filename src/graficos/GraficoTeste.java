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
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author augusto
 */
public class GraficoTeste {
    private JFreeChart grafico;
    private XYSeriesCollection datasets;

    public GraficoTeste(String title, String labelX, String labelY) {
        datasets = new XYSeriesCollection();
        
        grafico = ChartFactory.createXYLineChart(title, labelY, labelY, datasets);
          
    }
    
    public void addSerie(XYSeries serie){
        datasets.addSeries(serie);
    }


    public JPanel getPanel(){
        return new ChartPanel(grafico);
    }

    public JFreeChart getGrafico() {
        return grafico;
    }
}
