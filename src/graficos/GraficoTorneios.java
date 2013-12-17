/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graficos;

import java.util.List;
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
public class GraficoTorneios implements Grafico {

    private JFreeChart grafico;
    private XYSeriesCollection series;

    public GraficoTorneios(String title, String xLabel, String yLabel) {
        series = new XYSeriesCollection();
        grafico = ChartFactory.createXYLineChart(title, xLabel, yLabel, series);
    }

    public JFreeChart getGrafico() {
        return grafico;
    }

    public void add(XYSeries serie) {
        series.addSeries(serie);
    }

    @Override
    public JPanel getPanel() {
        return new ChartPanel(grafico);
    }

    public void limpar() {
       series.removeAllSeries();
    }

    public void add(List<XYSeries> series) {
        for (XYSeries xYSeries : series) {
            add(xYSeries);
        }
    }

}
