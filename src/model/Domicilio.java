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
public class Domicilio {
    
    private long idDomicilio;
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

    public long getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(long idDomicilio) {
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
        idDomicilio = sismain.getControladorBD().aniadir(valores, "DOMICILIO",false);
        return idDomicilio;
    }

    public void modificarBD(long id_referenciado, boolean persona){
            String referenciado;
        
            if(persona)referenciado ="D.PERSONA_ID_PERSONA"; 
            else referenciado="D.PROVEEDOR_ID_PROVEEDOR"; 
        //HI
            String tablas = "DOMICILIO D";
            String set = "D.DIRECCION = '" + direccion +"',"
                       + "D.LOCALIDAD = '"+ localidad +"',"
                       + "D.PROVINCIA = '"+ provincia +"'";
            String condicion = "D.ID_DOMICILIO = '"+ idDomicilio +"' AND "
                    + referenciado + " = "+ id_referenciado;
             
             sismain.getControladorBD().modificar(tablas,set,condicion);
    } 
    
    public void eliminarBD(long id_referenciado, boolean persona){
        
        String referenciado;
        
        if(persona)referenciado ="D.PERSONA_ID_PERSONA"; 
        else referenciado="D.PROVEEDOR_ID_PROVEEDOR";
        
        String tabla = "DOMICILIO D";
        String condicion = referenciado + " = "+ id_referenciado +" AND "
                +" D.ID_DOMICILIO = '"+ idDomicilio + "'";
        
        sismain.getControladorBD().eliminar(tabla, condicion);
               
    }
    
    
}
