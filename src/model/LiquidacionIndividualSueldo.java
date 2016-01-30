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
public class LiquidacionIndividualSueldo {

    private int idLiquidacionIndividual;
    private String motivo;
    private int mes;
    private String fechaLiquidacion;
    private double importeNeto;
    private Empleado idEmpleado;
    private TipoLiquidacion idTipoLiquidacion;
    
    public LiquidacionIndividualSueldo(){
        
    }
    
    public LiquidacionIndividualSueldo(int idLiquidacionIndividual, String motivo,
                         int mes, String fechaLiquidacion, double importeNeto, 
                         Empleado idEmpleado, TipoLiquidacion idTipoLiquidacion){
        
        this.idLiquidacionIndividual = idLiquidacionIndividual;
        this.motivo = motivo;
        this.mes = mes;
        this.fechaLiquidacion = fechaLiquidacion;
        this.importeNeto = importeNeto;
        
    }

    public int getIdLiquidacionIndividual() {
        return idLiquidacionIndividual;
    }

    public void setIdLiquidacionIndividual(int idLiquidacionIndividual) {
        this.idLiquidacionIndividual = idLiquidacionIndividual;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public String getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(String fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public double getImporteNeto() {
        return importeNeto;
    }

    public void setImporteNeto(double importeNeto) {
        this.importeNeto = importeNeto;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public TipoLiquidacion getIdTipoLiquidacion() {
        return idTipoLiquidacion;
    }

    public void setIdTipoLiquidacion(TipoLiquidacion idTipoLiquidacion) {
        this.idTipoLiquidacion = idTipoLiquidacion;
    }

    
    
    
}
