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
public class Domicilio {
    
    private int idDomicilio;
    private String direccion;
    private String localidad;
    private String provincia;
    private long idProveedor;
    private long idPersona;
    
    public Domicilio(){
        
    }
    
    public Domicilio(int idDomicilio, String direccion, String localidad,
                     String departamento, String provincia, Proveedor idProveedor,
                     Persona idPersona){
    this.idDomicilio = idDomicilio;
    this.direccion = direccion;
    this.localidad = localidad;
    this.provincia = provincia;
    }

    public int getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(int idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }


    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
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
        long idDomicilio=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(direccion);
        valores.add(localidad);
        valores.add(provincia);
        valores.add(String.valueOf(idProveedor));
        valores.add(String.valueOf(idPersona));
        idDomicilio = sismain.getControladorBD().aniadirBD(valores, "DOMICILIO",false);
        return idDomicilio;
    }


        public void update(ArrayList<String> txt, String tabla, String columna, String id){
             
             String set = "DIRECCION = '" + txt.get(0) + "', LOCALIDAD = '" + txt.get(1) +
                            "', PROVINCIA = '" + txt.get(2) + "'";
             try{

                 String query = "UPDATE " + tabla + " SET " + set + " WHERE " + columna + " = " + id;
                 System.out.println(query);
                 sismain.getConexion().getStatement().execute(query);
             }catch (SQLException ex) {
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
}
