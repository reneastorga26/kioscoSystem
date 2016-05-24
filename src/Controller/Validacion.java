
package Controller;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import sistemakiosco.sismain;
import java.awt.Color;

public abstract class Validacion {
    
    private ControladorBD controlador = sismain.getControladorBD();
    
    public Validacion(){
        
    }

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
       if(contenido.matches("[A-Za-z0-9 ]*")) return true;
       else return false;
   }
      public static boolean validarSoloTextoSinFormatoSinNumeros(String contenido){
       if(contenido.matches("[A-Za-z ]*")) return true;
       else return false;
   }
   public static boolean validarTextoFormatoMail(String contenido){
       if(contenido.matches("^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$")) return true;
       else return false;
   }
   
   public boolean validarComboBox(String item){
     boolean resultado = true;
       if(item.equals("--Seleccione Opcion--"))
           resultado = false;
           return resultado; 
   }
   
   public static boolean validarTxts(ResultSet rs, ArrayList<JTextField> txts, ArrayList<JLabel> labelsTxts, ArrayList<JComboBox> combos){
       boolean banderaVacios=false;
       boolean banderaFormato=false;
       String tipo = new String();
       String mensaje="Se han detectado los siguientes errores en el ingreso de Datos: \n\n";
       boolean resultado=true;
       String texto;
       for(int i = 0; i<txts.size();i++){
        texto = txts.get(i).getText();
        if(combos!= null && combos.get(i)!=null){
            if("0".equals(texto) || "--Seleccione Opcion--".equals(texto)){
                mensaje=mensaje + "+No selecciono una opcion en el combo: "+ labelsTxts.get(i).getText()+ "\n";
                combos.get(i).setBackground(Color.pink);
            }
            else{
                combos.get(i).setBackground(Color.white);
            }
        }
        else{
        if(validarEspacioVacio(texto)){
           banderaVacios=true;
           mensaje=mensaje+"+El campo "+ labelsTxts.get(i).getText()+" se encuentra Vacio \n";
           
        }
           try {
               tipo = rs.getMetaData().getColumnTypeName(i+1);
           } catch (SQLException ex) {
               Logger.getLogger(Validacion.class.getName()).log(Level.SEVERE, null, ex);
           }
           switch(tipo){
               case "INT": if(validarSoloNumerosEnteros(texto)==false){                         
                           banderaFormato=true;
                           mensaje=mensaje+"+El campo "+ labelsTxts.get(i).getText()+" debe tener formato de Numeros Enteros\n";
                           }
                           else
                           txts.get(i).setBackground(Color.white);
                           break;                         
               case "FLOAT": if(validarSoloNumerosDecimales(texto)==false){                            
                             banderaFormato=true;
                             mensaje=mensaje+"+El campo "+ labelsTxts.get(i).getText()+" debe tener formato de Numeros Decimales\n";
                             }
                           else
                           txts.get(i).setBackground(Color.white);
                             break;
             case "VARCHAR":if(validarSoloTextoSinFormatoSinNumeros(texto)==false){                            
                            banderaFormato=true;
                            mensaje=mensaje+"+El campo "+ labelsTxts.get(i).getText()+" debe tener formato de Solo Letras (Sin Numeros)\n";
                            }
                           else
                           txts.get(i).setBackground(Color.white);
                           break;
           }
           if(banderaVacios || banderaFormato){
                txts.get(i).setBackground(Color.pink);
                resultado=false;
        
           }
        }
       }
       if(resultado == false){
       mensaje = mensaje + "\n Realice la correccion necesaria a fin de poder ejecutar esta operacion";
       JOptionPane.showMessageDialog(null, mensaje, "Errores de Validacion en el ingreso de Datos",JOptionPane.WARNING_MESSAGE);
       }
       return resultado;
}
}
