/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.View;

/**
 *
 * @author augusto
 */
public class AutenticaCampo {
    
    public boolean verificarCampoVazio(String campo, String nomeCampo, View view){
        if((campo == null) || (campo.isEmpty())){    
            view.erroMensagem("Campo \'"+nomeCampo+"\" vazio.");
            return true;
        }
        
        return false;
    }
    
    public boolean verificarCampoNumero(String campo, View view){
        double valor;
        
        try {
            valor = Double.parseDouble(campo);
        } catch (Exception e) {
            view.erroMensagem("Erro na conversão do valor. Insira um número válido.");
            return true;
        }
        
        return false;
    }
    
    public boolean verificarCampoNumero(String campo){
        double valor;
        
        try {
            valor = Double.parseDouble(campo);
        } catch (Exception e) {
            return true;
        }
        
        return false;
    }
    
    public Date verificarCampoData(String campo){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date parse = null;
        try {
            parse = df.parse(campo);
        } catch (ParseException ex) {
            Logger.getLogger(AutenticaCampo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return new Date(parse.getTime());
    }
}
