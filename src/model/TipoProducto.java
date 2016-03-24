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
public class TipoProducto {
    
       private int idTipoProducto;
       private String descripcion;
       private char refrigeracion;
       
       public TipoProducto(){
           
       }
       
       public TipoProducto(int idTipoProducto, String descripcion, char refrigeracion){
           this.idTipoProducto = idTipoProducto;
           this.descripcion = descripcion;
           this.refrigeracion = refrigeracion;
       }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public char getRefrigeracion() {
        return refrigeracion;
    }

    public void setRefrigeracion(char refrigeracion) {
        this.refrigeracion = refrigeracion;
    }
       
     public void modificarBD(ArrayList<String> cadena, String cadenaId){
             
             String set = "DESCRIPCION = '" + cadena.get(0) + "'";
             
             sismain.getControladorBD().modificarBD(set, "TIPO_PRODUCTO", "ID_TIPO_PRODUCTO", cadenaId); 
     }
}
