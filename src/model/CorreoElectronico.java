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
public class CorreoElectronico {
    
    private long idCorreoElectronico;
    private String direccion;
    private long idProveedor;
    private long idPersona;
    
    public CorreoElectronico(){
        
    }
    
    public CorreoElectronico(int idCorreoElectronico, String direccion,
                             Proveedor idProveedor, Persona idPersona){
        this.idCorreoElectronico = idCorreoElectronico;
        this.direccion = direccion;
    }

    public long getIdCorreoElectronico() {
        return idCorreoElectronico;
    }

    public void setIdCorreoElectronico(long idCorreoElectronico) {
        this.idCorreoElectronico = idCorreoElectronico;
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

    

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

       public long guardarBD(){
        long idCorreoElectronico=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(String.valueOf(direccion));
        valores.add(String.valueOf(idProveedor));
        valores.add(String.valueOf(idPersona));
        idCorreoElectronico = sismain.getControladorBD().aniadir(valores, "CORREOELECTRONICO",false);
        return idCorreoElectronico;
    }

    public void modificarBD(ArrayList<String> cadena, String cadenaId){
             
             String set = "DIRECCION = '" + cadena.get(0) + "'";
             
             sismain.getControladorBD().modificar(set, "CORREOELECTRONICO", "PERSONA_ID_PERSONA", cadenaId);
    }
    
    public void eliminarBD(String columna, String cadenaId){
            sismain.getControladorBD().eliminar("CORREOELECTRONICO", columna, cadenaId);
    }
    
}
