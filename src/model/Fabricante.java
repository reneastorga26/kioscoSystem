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
public class Fabricante {
    
    private long idFabricante;
    private String descripcion;
    private char tipoFabricante;
    
    public Fabricante(){
        
    }
    
    public Fabricante(long idFabricante, String descripcion, char tipoFabricante){
    this.idFabricante = idFabricante;
    this.descripcion = descripcion;
    this.tipoFabricante = tipoFabricante;
    }

    public long getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(long idFabricante) {
        this.idFabricante = idFabricante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public char getTipoFabricante() {
        return tipoFabricante;
    }

    public void setTipoFabricante(char tipoFabricante) {
        this.tipoFabricante = tipoFabricante;
    }
    
    
    public long guardarBD(){
        long idFabricante=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(String.valueOf(getIdFabricante()));
        valores.add(getDescripcion());
        idFabricante = sismain.getControladorBD().aniadir(valores, "FABRICANTE",false);
        valores.clear();
        
        return idFabricante;
    }
    
    
    public ArrayList buscarBD(String criterioBusqueda, String columnaBusqueda,
            char estado, DefaultTableModel modeloTabla){
        
        ArrayList<Long> indices = new ArrayList<>();

        String tablas = "FABRICANTE F";
        String columnas = "F.ID_FABRICANTE, F.DESCRIPCION";
        String condicion = ""+columnaBusqueda+ " = " + criterioBusqueda ;
        
               
        sismain.getControladorBD().buscar(columnas, 
                tablas, condicion, modeloTabla);
        
        return indices;
    }
    
    public void modificarBD(){
             
            String tablas = "FABRICANTE F";
            String set = "F.DESCRIPCION = '" + getDescripcion() + "'";
            String condicion = "T.ID_FABRICANTE = '"+ idFabricante+"'";
             
             sismain.getControladorBD().modificar(tablas,set,condicion);
    } 
    
    public void eliminarBD(long id_referenciado){
        
        String tabla = "FABRICANTE F";
        String condicion = " F.ID_FABRICANTE = '"+ id_referenciado +"'";
        
        sismain.getControladorBD().eliminar(tabla, condicion);
               
    }
}
