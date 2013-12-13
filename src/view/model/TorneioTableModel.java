/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Cash;
import model.Torneio;

/**
 * Codigo adaptado de:
 * http://devsv.wordpress.com/2012/07/08/como-implementar-um-tablemodel/
 *
 * @author augusto
 */
public class TorneioTableModel extends AbstractTableModel {

    List<Torneio> lista;

    private String[] names = new String[]{"Tipo", "Buy-in", "Gênero", "Total Ganho", "Total Jogados", "Roi"};
    public final int TIPO = 0;
    public final int BUYIN = 1;
    public final int GENERO = 2;
    public final int VALOR_GANHO = 3;
    public final int JOGADOS = 4;
    public final int ROI = 5;

    public Torneio getTorneio(int indiceLinha) {
        return lista.get(indiceLinha);
    }

    public void addTorneio(Torneio torneio) {
        lista.add(torneio);

        int ultimoIndice = getRowCount() - 1;

        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void removeTorneio(int indiceLinha) {
        lista.remove(indiceLinha);

        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void addListaDeTorneios(List<Torneio> listaTorneios) {
        int indice = getRowCount();

        lista.addAll(listaTorneios);

        fireTableRowsInserted(indice, indice + listaTorneios.size());
    }

    public void limpar() {
        lista.clear();
        fireTableDataChanged();
    }

    public TorneioTableModel(List<Torneio> lista) {
        this.lista = lista;
    }

    public TorneioTableModel() {
        this.lista = new ArrayList<Torneio>();
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return names.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return names[columnIndex];
    }

    @Override
    public Object getValueAt(int i, int columnIndex) {
        Torneio torneio = lista.get(i);

        switch (columnIndex) {
            case TIPO:
                return torneio.getTipo();
            case BUYIN:
                return torneio.getBuyIn();
            case GENERO:
                return torneio.getGenero();
            case VALOR_GANHO:
                return torneio.getValorGanho();
            case JOGADOS:
                return torneio.getTotalJogados();
            case ROI:
                return Torneio.roi(torneio.getValorGanho(), torneio.getBuyIn(), torneio.getTotalJogados());
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Torneio torneio = lista.get(rowIndex);
        switch (columnIndex) {
            case TIPO:
                torneio.setTipo((char) aValue);
            case BUYIN:
                torneio.setBuyIn((double) aValue);
            case GENERO:
                torneio.setGenero((char) aValue);
            case VALOR_GANHO:
                torneio.setValorGanho((double) aValue);
            case JOGADOS:
                torneio.setTotalJogados((int) aValue);
            case ROI:
                Torneio.roi(torneio.getValorGanho(), torneio.getBuyIn(), torneio.getTotalJogados());
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case TIPO:
                return Character.class;
            case BUYIN:
                return Double.class;
            case GENERO:
                return Character.class;
            case VALOR_GANHO:
                return Double.class;
            case JOGADOS:
                return Integer.class;
            case ROI:
                return Double.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex
    ) {
        return false;
    }

}
