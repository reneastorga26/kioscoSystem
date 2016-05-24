/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import sistemakiosco.sismain;

/**
 *
 * @author CX
 */
public class TipoLiquidacion {
    
    private long idTipoLiquidacion;
    private String nombre;
    
    public TipoLiquidacion(){
        
    }
    
    public TipoLiquidacion(long idTipoLiquidacion, String nombre){
        this.idTipoLiquidacion = idTipoLiquidacion;
        this.nombre = nombre;
        
    }

    public long getIdTipoLiquidacion() {
        return idTipoLiquidacion;
    }

    public void setIdTipoLiquidacion(long idTipoLiquidacion) {
        this.idTipoLiquidacion = idTipoLiquidacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public long guardarBD(){
        long idTipoLiquidacion=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(nombre);
        idTipoLiquidacion = sismain.getControladorBD().aniadir(
                valores, "TIPO_LIQ",false);
        valores.clear();
        return idTipoLiquidacion;
    }
    
    public ArrayList buscarBD(String criterioBusqueda, String columnaBusqueda,
                        DefaultTableModel modeloTabla){
        
        ArrayList<Long> indices;// = new ArrayList<>();

        String tablas = "TIPO_LIQ T";
        String columnas = "T.ID_TIPO_LIQ, T.NOMBRE";
        String condicion = columnaBusqueda+ " = " + criterioBusqueda;
               
        indices = sismain.getControladorBD().buscar(columnas, 
                tablas, condicion, modeloTabla);
        
        return indices;
    }
    
    public void ampliarInfoBD(long idReferenciado){
        
        ArrayList<ArrayList<Object>> registros;

        String tablas = "TIPO_LIQ T";
        String columnas = "T.ID_TIPO_LIQ, T.NOMBRE";
        String condicion = "T.ID_TIPO_LIQ = " + idReferenciado;
               
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        setIdTipoLiquidacion(Long.valueOf(registros.get(0).get(0).toString()));
        setNombre(registros.get(0).get(1).toString());
    }
    
}
