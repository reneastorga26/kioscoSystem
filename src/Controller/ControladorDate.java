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
    
    public void darFormatoaComboBox(String fecha, JComboBox comboDia, JComboBox comboMes, JComboBox comboAnio){    
        String cadenaFecha = fecha;
        String dia1 = String.valueOf(cadenaFecha.charAt(8));
        String dia2 = String.valueOf(cadenaFecha.charAt(9));
        String dia = dia1 + dia2;
        String mes1 = String.valueOf(cadenaFecha.charAt(5));
        String mes2 = String.valueOf(cadenaFecha.charAt(6));
        String mes = mes1 + mes2;
        String anio1 = String.valueOf(cadenaFecha.charAt(0));
        String anio2 = String.valueOf(cadenaFecha.charAt(1));
        String anio3 = String.valueOf(cadenaFecha.charAt(2));
        String anio4 = String.valueOf(cadenaFecha.charAt(3));
        String anio = anio1 + anio2 + anio3 + anio4;
        
        System.out.println(dia);
        System.out.println(mes);
        System.out.println(anio);
        
        comboDia.setSelectedItem(dia);
        if(mes.equals("01")){
            comboMes.setSelectedItem("Enero");
        }
        if(mes.equals("02")){
            comboMes.setSelectedItem("Febrero"); 
        }
        if(mes.equals("03")){
            comboMes.setSelectedItem("Marzo");
        }
        if(mes.equals("04")){
            comboMes.setSelectedItem("Abril");
        }
        if(mes.equals("05")){
            comboMes.setSelectedItem("Mayo");
        }
        if(mes.equals("06")){
            comboMes.setSelectedItem("Junio");
        }
        if(mes.equals("07")){
            comboMes.setSelectedItem("Julio");
        }
        if(mes.equals("08")){
            comboMes.setSelectedItem("Agosto");
        }
        if(mes.equals("09")){
            comboMes.setSelectedItem("Septiembre"); 
        }
        if(mes.equals("10")){
            comboMes.setSelectedItem("Octubre"); 
        }
        if(mes.equals("11")){
            comboMes.setSelectedItem("Noviembre");
        }
        if(mes.equals("12")){
            comboMes.setSelectedItem("Diciembre");
        }else{
        }
        comboAnio.setSelectedItem(anio);
        
    }
    
}
