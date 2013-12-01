/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package seguranca;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author augusto
 */
public class Criptografia {
    
    /*
     * link : http://blog.caelum.com.br/guardando-senhas-criptografadas-em-java/
     */
    public static String cifrar(String texto) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256"); // Tb pode ser MD5
        byte messageDigest[] = algorithm.digest(texto.getBytes("UTF-8")); // cifra o texto e armazena em um vetor
        
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b)); // formata tirando o 0x e deixando duas casas decimais
        }
        return hexString.toString();
    }
}
