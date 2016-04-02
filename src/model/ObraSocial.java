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
    
     private long idObraSocial;
     private String descripcion;
     private String banco;
     private String cuentaBancaria;
     
     public ObraSocial(){
         
     }
     
     public ObraSocial(long idObraSocial, String descripcion, String banco,
             String cuentaBancaria){
         this.idObraSocial = idObraSocial;
         this.descripcion = descripcion;
         this.banco = banco;
         this.cuentaBancaria = cuentaBancaria;
     }

    public long getIdObraSocial() {
        return idObraSocial;
    }

    public void setIdObraSocial(long idObraSocial) {
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
   
    public long guardarBD(){
        long idObraSocial=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(descripcion);
        valores.add(banco);
        valores.add(cuentaBancaria);
        idObraSocial = sismain.getControladorBD().aniadir(valores, "OBRA_SOCIAL",false);
        return idObraSocial;
    }
    
    public void modificarBD(){
             
            String tablas= "OBRA_SOCIAL O";
            String set = "O.DESCRIPCION = '" + getDescripcion() 
                    + "', O.BANCO = '"+ getBanco() +"', O.CUENTA_BANCARIA = '" + getCuentaBancaria() + "'";
            String condicion = "O.ID_OBRA_SOCIAL = '" + getIdObraSocial() + "'";
             
             sismain.getControladorBD().modificar(tablas,set,condicion);
    } 
    
    public void eliminarBD(long id_referenciado){
        
        String tablas= "OBRA_SOCIAL O";
        String condicion = "O.ID_OBRA_SOCIAL = '" + id_referenciado + "'";
        
        sismain.getControladorBD().eliminar(tablas, condicion);
               
    }
}
