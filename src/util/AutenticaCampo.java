/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

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
}
