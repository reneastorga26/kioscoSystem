/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Watch;

import javax.swing.JComboBox;

/**
 *
 * @author hites
 */
public abstract class Fecha {
    
    public String formatoFechaNormal(String dia, int mes, String anio){
        String resultado= dia + "-" + mes + "-"+ anio;
        return resultado;
    }
    
}
