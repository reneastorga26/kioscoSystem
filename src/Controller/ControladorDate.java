/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.Calendar;
import javax.swing.JComboBox;

/**
 *
 * @author hites
 */
public class ControladorDate {
    
    private Calendar calendar = Calendar.getInstance();
    
    public ControladorDate(){
    
}
    public void iniciarCombos(JComboBox comboDia, 
                               JComboBox comboMes, 
                               JComboBox comboAnio){

        String sdia;
        comboAnio.addItem("Anio");

        for(int i=calendar.get(Calendar.YEAR); i>=1900; i--){
            comboAnio.addItem(String.valueOf(i));
        }
        
        comboMes.addItem("Mes");
        comboMes.addItem("Enero");
        comboMes.addItem("Febrero");
        comboMes.addItem("Marzo");
        comboMes.addItem("Abril");
        comboMes.addItem("Mayo");
        comboMes.addItem("Junio");
        comboMes.addItem("Julio");
        comboMes.addItem("Agosto");
        comboMes.addItem("Septiembre");
        comboMes.addItem("Octubre");
        comboMes.addItem("Noviembre");
        comboMes.addItem("Diciembre");
        
        comboDia.removeAllItems();
        comboDia.addItem("Dia");
        comboDia.setSelectedIndex(0);
        for (int i=1; i<=31; i++){
              sdia=String.valueOf(i);
              if(i<10) sdia = "0"+sdia;
              comboDia.addItem(sdia);
        }
        
    }
    
    public void corregirCombos(JComboBox comboDia, 
                               JComboBox comboMes, 
                               JComboBox comboAnio){
        
        int seleccionado;
        int mes = comboMes.getSelectedIndex();
        int anio = correctorAnio(comboAnio.getSelectedIndex());
        seleccionado=comboDia.getSelectedIndex();
        String sdia;
       
        int cantDias;
        switch(mes){
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    cantDias=31;break;
                case 2: if ( anio % 4 == 0 || anio==-1) cantDias = 29; else cantDias = 28;
                        break;
                default: cantDias = 30; break;
            }
        comboDia.removeAllItems();
        comboDia.addItem("Dia");
        for(int i=1; i<=cantDias;i++){
                sdia=String.valueOf(i);
                if(i<10) sdia = "0"+sdia;
                comboDia.addItem(sdia);
            }
        if(seleccionado>cantDias) seleccionado=cantDias;
        
        comboDia.setSelectedIndex(seleccionado);
        System.out.println(darFormatoStringOracle(comboDia,comboMes,comboAnio));
    }
    
    public int correctorAnio(int index){
        int anio =-1;
        if(index!=0){
            int ultimo_anio = calendar.get(Calendar.YEAR);
            anio=ultimo_anio - (index-1);
            }
        return anio;
        
    }
    
    public String darFormatoStringOracle(JComboBox comboDia, JComboBox comboMes, JComboBox comboAnio){
        String resultado;
        String dia= String.valueOf(comboDia.getSelectedIndex());
        String mes=String.valueOf(comboMes.getSelectedIndex());
        String anio= String.valueOf(correctorAnio(comboAnio.getSelectedIndex()));
        
        if(comboMes.getSelectedIndex()<10) mes = "0" + mes;  
        
        resultado = "'"+dia +"/"+mes+"/"+anio +"' , 'dd/mm/yyyy'";
        return resultado;
    }
    
}
