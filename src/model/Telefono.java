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
     
    public void update(ArrayList<String> txt, String tabla, String columna, String id){
             
             String set = "NUMERO = '" + txt.get(0) + "', MOVIL = '" + txt.get(1) + "'";
             try{

                 String query = "UPDATE " + tabla + " SET " + set + " WHERE " + columna + " = " + id;
                 System.out.println(query);
                 sismain.getConexion().getStatement().execute(query);
             }catch (SQLException ex) {
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
            }
    } 
}
