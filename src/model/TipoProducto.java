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
import javax.swing.table.DefaultTableModel;
import sistemakiosco.sismain;

/**
 *
 * @author CX
 */
public class TipoProducto {
    
       private long idTipoProducto;
       private String descripcion;
       private char refrigeracion;
       
       public TipoProducto(){
           
       }
       
       public TipoProducto(long idTipoProducto, String descripcion, char refrigeracion){
           this.idTipoProducto = idTipoProducto;
           this.descripcion = descripcion;
           this.refrigeracion = refrigeracion;
       }

    public long getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(long idTipoProducto) {
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
       
    
    public long guardarBD(){
        long idTipoProducto=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(descripcion);
        valores.add(String.valueOf(refrigeracion));
        idTipoProducto = sismain.getControladorBD().aniadir(valores, "TIPO_PRODUCTO",false);
        valores.clear();
        return idTipoProducto;
    }
    
    
    public void ampliarInfoBD(String criterioBusqueda, String columnaBusqueda, 
            DefaultTableModel modeloTabla){
        
        ArrayList<ArrayList<Object>> registros;

        String tablas = "TIPO_PRODUCTO T";
        String columnas = "T.ID_TIPO_PRODUCTO, T.DESCRIPCION, T.REFRIGERACION";
        String condicion = "T."+columnaBusqueda+ " = '" + criterioBusqueda 
                +"' AND ROWNUM = 1";
        

        registros = sismain.getControladorBD().extenderInfo(columnas, 
                tablas, condicion);
 
        idTipoProducto = Long.parseLong(registros.get(0).get(0).toString());
        descripcion = registros.get(0).get(1).toString();
        refrigeracion = registros.get(0).get(2).toString().charAt(0);
        
    }
    
    public void modificarBD(){
             
            String tablas = "TIPO_PRODUCTO T";
            String set = "T.DESCRIPCION = '" + descripcion + "',"
                    + "T.REFRIGERACION = ' " + refrigeracion+ "'";
            String condicion = "T.ID_TIPO_PRODUCTO = '"+ idTipoProducto+"'";
             
            sismain.getControladorBD().modificar(tablas,set,condicion);
    } 
    
    public void eliminarBD(long id_referenciado){
        
        String tabla = "TIPO_PRODUCTO T";
        String condicion = " T.ID_TIPO_PRODUCTO = '"+ id_referenciado +"'";
        
        sismain.getControladorBD().eliminar(tabla, condicion);
               
    }
    
       public ArrayList obtenerDescripcionTodos(){
       ArrayList<ArrayList<Object>> registros;
       
       String tablas= "TIPO_PRODUCTO T";
       String columnas="T.ID_TIPO_PRODUCTO, T.DESCRIPCION";
       String condicion="T.ID_TIPO_PRODUCTO = T.ID_TIPO_PRODUCTO";
       
       registros=sismain.getControladorBD().extenderInfo(columnas, tablas, condicion);
       
       return registros;
   }
}
