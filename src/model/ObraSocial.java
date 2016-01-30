/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
     
     
}
