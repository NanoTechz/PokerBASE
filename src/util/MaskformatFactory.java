/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.text.ParseException;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author augusto
 */
public class MaskformatFactory {
     public static MaskFormatter getData() throws ParseException {
        MaskFormatter ftmData = new MaskFormatter("##/##/####");
        ftmData.setValidCharacters("0123456789");
        return ftmData;
    }
}
