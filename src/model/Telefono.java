/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import sistemakiosco.sismain;

/**
 *
 * @author CX
 */
public class Telefono {
 
     private int idTelefono;
     private String numero;
     private char movil;
     private long idProveedor;
     private long idPersona;
     
     public Telefono(){
         
     }
     
     public Telefono(int idTelefono, String numero, char movil,
             Proveedor idProveedor, Persona idPersona){
        this.idTelefono = idTelefono;
        this.numero = numero;
        this.movil = movil;
        
     }

    public int getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(int idTelefono) {
        this.idTelefono = idTelefono;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public char getMovil() {
        return movil;
    }

    public void setMovil(char movil) {
        this.movil = movil;
    }

    public long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(long idPersona) {
        this.idPersona = idPersona;
    }

    
      public long guardarBD(){
        long idTelefono=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(numero);
        valores.add(String.valueOf(movil));
        valores.add(String.valueOf(idProveedor));
        valores.add(String.valueOf(idPersona));
        idTelefono = sismain.getControladorBD().aniadirBD(valores, "TELEFONO",false);
        return idTelefono;
    }
     
     
}
