/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Controller.ControladorBD;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistemakiosco.sismain;

/**
 *
 * @author CX
 */
public class ObraSocial {
    
     private int idObraSocial;
     private String descripcion;
     private String banco;
     private String cuentaBancaria;
     
     public ObraSocial(){
         
     }
     
     public ObraSocial(int idObraSocial, String descripcion, String banco,
             String cuentaBancaria){
         this.idObraSocial = idObraSocial;
         this.descripcion = descripcion;
         this.banco = banco;
         this.cuentaBancaria = cuentaBancaria;
     }

    public int getIdObraSocial() {
        return idObraSocial;
    }

    public void setIdObraSocial(int idObraSocial) {
        this.idObraSocial = idObraSocial;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }
   /*

    public void modificarBD(ArrayList<String> cadena, String cadenaId){
             
            String set = "DESCRIPCION = " + cadena.get(0) + ", BANCO = " + cadena.get(1) 
                    + "CUENTA_BANCARIA = " + cadena.get(2);
            
            sismain.getControladorBD().modificar(set, "OBRA_SOCIAL", "ID_OBRA_SOCIAL", cadenaId);
    }
    
    public void eliminarBD(String cadenaId){
            sismain.getControladorBD().eliminar("RELACION_EMPLEADO_OS", "OBRA_SOCIAL_ID_OBRA_SOCIAL", cadenaId);
            sismain.getControladorBD().eliminar("OBRA_SOCIAL", "ID_OBRA_SOCIAL", cadenaId);
    }
    
    */ 
}
