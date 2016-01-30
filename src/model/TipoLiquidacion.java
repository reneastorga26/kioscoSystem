/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author CX
 */
public class TipoLiquidacion {
    
    private int idTipoLiquidacion;
    private String nombre;
    
    public TipoLiquidacion(){
        
    }
    
    public TipoLiquidacion(int idTipoLiquidacion, String nombre){
        this.idTipoLiquidacion = idTipoLiquidacion;
        this.nombre = nombre;
        
    }

    public int getIdTipoLiquidacion() {
        return idTipoLiquidacion;
    }

    public void setIdTipoLiquidacion(int idTipoLiquidacion) {
        this.idTipoLiquidacion = idTipoLiquidacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
