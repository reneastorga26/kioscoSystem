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
public class LiquidacionIndividualSueldo {

    private long idLiquidacionIndividual;
    private String motivo;
    private String periodo;
    private String fechaLiquidacion;
    private double importeNeto;
    private long idEmpleado;
    private long idTipoLiquidacion;
    private double totalHaberesRemunerativos;
    private double totalHaberesNoRemunerativos;
    private double totalRetenciones;
    private double totalBruto;
    
    
    public LiquidacionIndividualSueldo(){
        
    }
    
    public LiquidacionIndividualSueldo(long idLiquidacionIndividual, String motivo,
                         String periodo, String fechaLiquidacion, double importeNeto, 
                         long idEmpleado, long idTipoLiquidacion, double totalHaberesRemunerativos,
                         double totalHaberesNoRemunerativos, double totalRetenciones,
                         double totalBruto){
        
        this.idLiquidacionIndividual = idLiquidacionIndividual;
        this.motivo = motivo;
        this.periodo = periodo;
        this.fechaLiquidacion = fechaLiquidacion;
        this.importeNeto = importeNeto;
        this.idEmpleado = idEmpleado;
        this.idTipoLiquidacion = idTipoLiquidacion;
        this.totalBruto = totalBruto;
        this.totalHaberesNoRemunerativos = totalHaberesNoRemunerativos;
        this.totalHaberesRemunerativos = totalHaberesRemunerativos;
        this.totalRetenciones = totalRetenciones;
    }

    public long getIdLiquidacionIndividual() {
        return idLiquidacionIndividual;
    }

    public void setIdLiquidacionIndividual(long idLiquidacionIndividual) {
        this.idLiquidacionIndividual = idLiquidacionIndividual;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
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

    public long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public long getIdTipoLiquidacion() {
        return idTipoLiquidacion;
    }

    public void setIdTipoLiquidacion(long idTipoLiquidacion) {
        this.idTipoLiquidacion = idTipoLiquidacion;
    }

    public double getTotalHaberesRemunerativos() {
        return totalHaberesRemunerativos;
    }

    public void setTotalHaberesRemunerativos(double totalHaberesRemunerativos) {
        this.totalHaberesRemunerativos = totalHaberesRemunerativos;
    }

    public double getTotalHaberesNoRemunerativos() {
        return totalHaberesNoRemunerativos;
    }

    public void setTotalHaberesNoRemunerativos(double totalHaberesNoRemunerativos) {
        this.totalHaberesNoRemunerativos = totalHaberesNoRemunerativos;
    }

    public double getTotalRetenciones() {
        return totalRetenciones;
    }

    public void setTotalRetenciones(double totalRetenciones) {
        this.totalRetenciones = totalRetenciones;
    }

    public double getTotalBruto() {
        return totalBruto;
    }

    public void setTotalBruto(double totalBruto) {
        this.totalBruto = totalBruto;
    }
    
    
   public long guardarBD(){
        long idLiquidacionIndividual=-1;
        String auxImporte = String.valueOf(importeNeto);
        auxImporte = auxImporte.replace('.', ',');
        String auxTotalHR = String.valueOf(totalHaberesRemunerativos);
        auxTotalHR = auxTotalHR.replace('.', ',');
        String auxTotalHNR = String.valueOf(totalHaberesNoRemunerativos);
        auxTotalHNR = auxTotalHNR.replace('.', ',');
        String auxTotalR = String.valueOf(totalRetenciones);
        auxTotalR = auxTotalR.replace('.', ',');
        String auxTotalB = String.valueOf(totalBruto);
        auxTotalB = auxTotalB.replace('.', ',');
        ArrayList<String> valores= new ArrayList<>();
        valores.add(motivo);
        valores.add(periodo);
        valores.add(fechaLiquidacion);
        valores.add(String.valueOf(auxImporte));
        valores.add(String.valueOf(idEmpleado));
        valores.add(String.valueOf(idTipoLiquidacion));
        valores.add(String.valueOf(auxTotalHR));
        valores.add(String.valueOf(auxTotalHNR));
        valores.add(String.valueOf(auxTotalR));
        valores.add(String.valueOf(auxTotalB));
        idLiquidacionIndividual = sismain.getControladorBD().aniadir(
                valores, "LIQ_IND_SUELDO",false);
        valores.clear();
        return idLiquidacionIndividual;
    }
    
    public ArrayList buscarBD(String criterioBusqueda, String columnaBusqueda,
                         DefaultTableModel modeloTabla){
        
        ArrayList<Long> indices;// = new ArrayList<>();

        String tablas = "LIQ_IND_SUELDO L";
        String columnas = "L.ID_LIQIND";
        String condicion = columnaBusqueda+ " = " + criterioBusqueda;
               
        indices = sismain.getControladorBD().buscar(columnas, 
                tablas, condicion, modeloTabla);
        
        return indices;
    }

    public void ampliarInfoBD(long idLiquidacionIndividual){
        
        ArrayList<ArrayList<Object>> registros;

        String tablas;
        String columnas;
        String condicion;
        this.idLiquidacionIndividual = idLiquidacionIndividual;
        tablas = "LIQ_IND_SUELDO L";
        columnas = "L.ID_LIQIND, L.MOTIVO, L.PERIODO, L.FECHA_LIQUIDACION, "
                + "L.IMPORTE_NETO, L.EMPLEADO_ID_EMPLEADO, "
                + "L.TIPO_LIQ_ID_TIPO_LIQ, L.TOTAL_HABERES_REMUNERATIVOS, "
                + "L.TOTAL_HABERES_NO_REMUNERATIVOS, "
                + "L.TOTAL_RETENCIONES, L.TOTAL_BRUTO";
        condicion = "L.ID_LIQIND = '"+ idLiquidacionIndividual + "'";
        
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        setIdLiquidacionIndividual(Long.parseLong(registros.get(0).get(0).toString()));
        setMotivo(registros.get(0).get(1).toString());
        setPeriodo(registros.get(0).get(2).toString());
        setFechaLiquidacion(String.valueOf(registros.get(0).get(3).toString()));
        setImporteNeto(Double.valueOf(registros.get(0).get(4).toString()));
        setIdEmpleado(Long.valueOf(registros.get(0).get(5).toString()));
        setIdTipoLiquidacion(Long.valueOf(registros.get(0).get(6).toString()));
        setTotalHaberesRemunerativos(Double.valueOf(registros.get(0).get(7).toString()));
        setTotalHaberesNoRemunerativos(Double.valueOf(registros.get(0).get(8).toString()));
        setTotalRetenciones(Double.valueOf(registros.get(0).get(9).toString()));
        setTotalBruto(Double.valueOf(registros.get(0).get(10).toString()));
     
    }
    
    
    
}
