/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.auxiliar;

import java.sql.Date;

/**
 *
 * @author augusto
 */
public class FiltroTorneio {
    private double buyInDe = -1;
    private double BuyInAte = -1;
    
    private Date dataDe = null;
    private Date dataAte = null;
    
    private boolean sng;
    private boolean mtt;

    public double getBuyInDe() {
        return buyInDe;
    }

    public void setBuyInDe(double buyInDe) {
        this.buyInDe = buyInDe;
    }

    public double getBuyInAte() {
        return BuyInAte;
    }

    public void setBuyInAte(double BuyInAte) {
        this.BuyInAte = BuyInAte;
    }

    public Date getDataDe() {
        return dataDe;
    }

    public void setDataDe(Date dataDe) {
        this.dataDe = dataDe;
    }

    public Date getDataAte() {
        return dataAte;
    }

    public void setDataAte(Date dataAte) {
        this.dataAte = dataAte;
    }

    public boolean isSng() {
        return sng;
    }

    public void setSng(boolean sng) {
        this.sng = sng;
    }

    public boolean isMtt() {
        return mtt;
    }

    public void setMtt(boolean mtt) {
        this.mtt = mtt;
    }

    @Override
    public String toString() {
        return "FiltroTorneio{" + "buyInDe=" + buyInDe + ", BuyInAte=" + BuyInAte + ", dataDe=" + dataDe + ", dataAte=" + dataAte + ", sng=" + sng + ", mtt=" + mtt + '}';
    }
    
    
}
