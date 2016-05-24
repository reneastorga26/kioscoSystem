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
public class PeriodoLiquidacion {
    
    private long idPeriodo;
    private String periodo;
    
    public PeriodoLiquidacion(){
        
    }
    
    public PeriodoLiquidacion(long idPeriodo, String periodo){
        this.idPeriodo = idPeriodo;
        this.periodo = periodo;
        
    }

    public long getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    public long guardarBD(){
        long idPeriodoLiquidacion=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(periodo);
        idPeriodoLiquidacion = sismain.getControladorBD().aniadir(
                valores, "PERIODO_LIQ",false);
        valores.clear();
        return idPeriodoLiquidacion;
    }
    
    public ArrayList buscarBD(String criterioBusqueda, String columnaBusqueda,
                        DefaultTableModel modeloTabla){
        
        ArrayList<Long> indices;// = new ArrayList<>();

        String tablas = "PERIODO_LIQ P";
        String columnas = "P.ID_PERIODO_LIQ, P.PERIODO";
        String condicion = columnaBusqueda+ " = " + criterioBusqueda;
               
        indices = sismain.getControladorBD().buscar(columnas, 
                tablas, condicion, modeloTabla);
        
        return indices;
    }
}
