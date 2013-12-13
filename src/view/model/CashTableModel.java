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
import model.Sala;

/**
 * Codigo adaptado de: http://devsv.wordpress.com/2012/07/08/como-implementar-um-tablemodel/
 * @author augusto
 */
public class CashTableModel extends AbstractTableModel {

    List<Cash> lista;

    public final int LIMITE = 0;
    public final int NUMERO_MAOS = 1;
    public final int DURACAO = 2;
    public final int BB_100 = 3;
    public final int SALA = 4;

    public Cash getSocio(int indiceLinha) {
        return lista.get(indiceLinha);
    }

    public void addCash(Cash cash) {
        lista.add(cash);
        
        int ultimoIndice = getRowCount() - 1;

        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void removeSocio(int indiceLinha) {
        lista.remove(indiceLinha);

        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void addListaDeSocios(List<Cash> cash) {
        int indice = getRowCount();

        lista.addAll(cash);

        fireTableRowsInserted(indice, indice + cash.size());
    }

    public void limpar() {
        lista.clear();
        fireTableDataChanged();
    }

    public CashTableModel(List<Cash> lista) {
        this.lista = lista;
    }

    public CashTableModel() {
        this.lista = new ArrayList<Cash>();
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return Coluna.values().length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return Coluna.getColuna(columnIndex).getNomeColuna();
    }

    @Override
    public Object getValueAt(int i, int columnIndex) {
        Cash cash = lista.get(i);

        switch (columnIndex) {
            case LIMITE:
                return cash.getLimite();
            case NUMERO_MAOS:
                return cash.getNumeroMaos();
            case DURACAO:
                return cash.getDuracaoHoras();
            case BB_100:
                return Cash.bb100(cash.getNumeroMaos(), cash.getValorGanho(), cash.getValorBlind());
            case SALA:
                return cash.getSala();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Cash cash = lista.get(rowIndex);

        switch (columnIndex) {
            case LIMITE:
                cash.setLimite((String) aValue);
            case NUMERO_MAOS:
                cash.setNumeroMaos((int) aValue);
            case DURACAO:
                cash.setDuracaoHoras((double) aValue);
            case BB_100:
                Cash.bb100(cash.getNumeroMaos(), cash.getValorGanho(), cash.getValorBlind());
            case SALA:
                cash.setSala((Sala) aValue);
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Coluna.getColuna(columnIndex).getClasse();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex
    ) {
        return false;
    }

}

enum Coluna {

    limite("Limite", 0, String.class), numeroMaos("N° mãos", 1, Integer.class),
    duracao("Duracao(h)", 2, Double.class), bb("bb/100", 3, Double.class), sala("Sala", 4, Sala.class);

    private String nomeColuna;
    private int valorColuna;
    private Class c;

    private Coluna(String nomeColuna, int valorColuna, Class c) {
        this.nomeColuna = nomeColuna;
        this.valorColuna = valorColuna;
        this.c = c;
    }

    public String getNomeColuna() {
        return nomeColuna;
    }

    public int getValorColuna() {
        return valorColuna;
    }

    public Class getClasse() {
        return c;
    }

    public static Coluna getColuna(int valor) {
        Coluna[] values = Coluna.values();
        for (Coluna coluna : values) {
            if (coluna.valorColuna == valor) {
                return coluna;
            }
        }

        return null;
    }

}
