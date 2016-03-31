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
        valores.add(String.valueOf(getIdTipoProducto()));
        valores.add(getDescripcion());
        idTipoProducto = sismain.getControladorBD().aniadir(valores, "TIPO_PRODUCTO",false);
        valores.clear();
        
        return idTipoProducto;
    }
    
    
    public ArrayList buscarBD(String criterioBusqueda, String columnaBusqueda,
            char estado, DefaultTableModel modeloTabla){
        
        ArrayList<Long> indices = new ArrayList<>();

        String tablas = "TIPO_PRODUCTO T";
        String columnas = "T.ID_TIPO_PRODUCTO, T.DESCRIPCION";
        String condicion = ""+columnaBusqueda+ " = " + criterioBusqueda ;
        
               
        sismain.getControladorBD().buscar(columnas, 
                tablas, condicion, modeloTabla);
        
        return indices;
    }
    
    public void modificarBD(){
             
            String tablas = "TIPO_PRODUCTO T";
            String set = "T.DESCRIPCION = '" + getDescripcion() + "'";
            String condicion = "T.ID_TIPO_PRODUCTO = '"+ idTipoProducto+"'";
             
             sismain.getControladorBD().modificar(tablas,set,condicion);
    } 
    
    public void eliminarBD(long id_referenciado){
        
        String tabla = "TIPO_PRODUCTO T";
        String condicion = " T.ID_TIPO_PRODUCTO = '"+ id_referenciado +"'";
        
        sismain.getControladorBD().eliminar(tabla, condicion);
               
    }
    
    
}
