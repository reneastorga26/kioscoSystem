/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Ignacio
 */
public abstract class ControladorValidacion {
    
    public static boolean validarEspacioVacio(String contenido){
    if(contenido.isEmpty()){
       return true;
    }
    else
        return false;
    }
    
   public static boolean validarSoloNumerosEnteros(String contenido){
       if(contenido.matches("[0-9]*")) return true;
       else return false;
}
   public static boolean validarSoloNumerosDecimales(String contenido){
       if(contenido.matches("[0-9]+(\\,[0-9][0-9]?)?"))return true;
       else return false;
   }
    
   public static boolean validarSoloTextoSinFormato(String contenido){
       if(contenido.matches("[A-Za-z0-9.() ]*")) return true;
       else return false;
   }
      public static boolean validarSoloTextoSinFormatoSinNumeros(String contenido){
       if(contenido.matches("[A-Za-z ]*")) return true;
       else return false;
   }
   public static boolean validarTextoFormatoMail(String contenido){
       if(contenido.matches
        ("^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\\\"
                + ".[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$")) return true;
       else return false;
   }
   
   public static boolean validarComboBox(JComboBox combo){
     boolean resultado = false;
       if(combo.getSelectedIndex()==0)
           resultado = true;
           return resultado; 
   }
   
   public static boolean validar(ArrayList<JTextField> txts,
       ArrayList<JComboBox> combos, ArrayList<String> tiposTxts, 
       ArrayList<String> etiquetasTxts, ArrayList<String> etiquetasCombos){
       boolean resultado = false;
       boolean banderaVacios = false;
       boolean banderaFormato = false;
       String contenido;
       String mensaje="Se han detectado los siguientes errores "
               + "en el ingreso de Datos: \n\n";
       
       for(int i = 0; i<txts.size(); i++){
           contenido=txts.get(i).getText();
           if(validarEspacioVacio(contenido)){
               banderaVacios=true;
               mensaje=mensaje+"+El campo "+ etiquetasTxts
                       .get(i)+" se encuentra vacio \n";
           }
           else{
            switch(tiposTxts.get(i)){
               case "SS": if(!validarSoloTextoSinFormatoSinNumeros(contenido)){
                               banderaFormato = true;
                               mensaje=mensaje+"+El campo "+ etiquetasTxts
                               .get(i)+" debe tener "
                               + "solo letras (Sin Numeros)\n";
                               }break;
               case "SA":  if(!validarSoloTextoSinFormato(contenido)){
                               banderaFormato = true;
                               mensaje=mensaje+"+El campo "+ etiquetasTxts
                               .get(i)+" debe tener solo letras, "
                               + "numeros o espacios\n";
                               }break;
               case "SM":  if(!validarTextoFormatoMail(contenido)){
                               banderaFormato = true;
                               mensaje=mensaje+"+El campo "+ etiquetasTxts
                               .get(i)+" debe tener formato mail "
                               + "(abc012@example.com) \n";
                               }break;
               case "INT":  if(!validarSoloNumerosEnteros(contenido)){
                               banderaFormato=true;
                               mensaje=mensaje+"+El campo "+ etiquetasTxts
                               .get(i)+" debe tener formato "
                               + "de numeros enteros\n";
                               }break;
               case "FLOAT":  if(!validarSoloNumerosDecimales(contenido)){
                               banderaFormato=true;
                               mensaje=mensaje+"+El campo "+ etiquetasTxts
                               .get(i)+" debe tener formato "
                               +"de numeros decimales\n";
                               }break;
           }
           }
           if(banderaFormato || banderaVacios){
               txts.get(i).setForeground(Color.BLACK);
               txts.get(i).setBackground(Color.PINK);
               resultado=true;
           }
           else{
               txts.get(i).setForeground(new Color(255,255,255));
               txts.get(i).setBackground(new Color(0,51,51));
           }
           banderaFormato = false;
           banderaVacios = false;
       }
       for(int i = 0; i<combos.size(); i++){
           if(validarComboBox(combos.get(i))){
            resultado=true;
            combos.get(i).setBackground(Color.PINK);
            combos.get(i).setForeground(Color.BLACK);
            mensaje=mensaje+"+El combo "+ etiquetasCombos
                               .get(i)+" no tiene una "
                               +"opcion seleccionada \n";
           }
           else{
               combos.get(i).setForeground(new Color(255,255,255));
               combos.get(i).setBackground(new Color(0,51,51));               
           }
       }
       if(resultado){
       mensaje = mensaje + "\n Realice la"
               + " correccion necesaria a fin de poder ejecutar esta operacion";
       JOptionPane.showMessageDialog(null, mensaje, "Errores "
               + "de Validacion en el ingreso de Datos",
               JOptionPane.WARNING_MESSAGE);
       }
       System.out.println(mensaje);
       return resultado;
   }
}
