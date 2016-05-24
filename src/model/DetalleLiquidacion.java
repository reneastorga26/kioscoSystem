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
public class DetalleLiquidacion {
    
    private long idLiquidacionIndividual;
    private long idConcepto;
    private String unidadConcepto;
    private double haberesRemunerativos;
    private double haberesNoRemunerativos;
    private double retenciones;
    
    public DetalleLiquidacion(){
        
    }
    
    public DetalleLiquidacion(long idLiquidacionIndividual, long idConcepto,
                              String unidadConcepto,
                              double haberesRemunerativos,
                              double haberesNoRemunerativos,
                              double retenciones){
     
        this.idLiquidacionIndividual = idLiquidacionIndividual;
        this.idConcepto = idConcepto;
        this.haberesRemunerativos = haberesRemunerativos;
        this.haberesNoRemunerativos = haberesNoRemunerativos;
        this.retenciones = retenciones;
        this.unidadConcepto = unidadConcepto;
        
    }

    public long getIdLiquidacionIndividual() {
        return idLiquidacionIndividual;
    }

    public void setIdLiquidacionIndividual(long idLiquidacionIndividual) {
        this.idLiquidacionIndividual = idLiquidacionIndividual;
    }

    public long getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(long idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getUnidadConcepto() {
        return unidadConcepto;
    }

    public void setUnidadConcepto(String unidadConcepto) {
        this.unidadConcepto = unidadConcepto;
    }

    public double getHaberesRemunerativos() {
        return haberesRemunerativos;
    }

    public void setHaberesRemunerativos(double haberesRemunerativos) {
        this.haberesRemunerativos = haberesRemunerativos;
    }

    public double getHaberesNoRemunerativos() {
        return haberesNoRemunerativos;
    }

    public void setHaberesNoRemunerativos(double haberesNoRemunerativos) {
        this.haberesNoRemunerativos = haberesNoRemunerativos;
    }

    public double getRetenciones() {
        return retenciones;
    }

    public void setRetenciones(double retenciones) {
        this.retenciones = retenciones;
    }

    

    public long guardarBD(){
        long idDetalleLiquidacion=-1;
        String auxHaberesR = String.valueOf(haberesRemunerativos);
        auxHaberesR = auxHaberesR.replace('.', ',');
        String auxHaberesNR = String.valueOf(haberesNoRemunerativos);
        auxHaberesNR = auxHaberesNR.replace('.', ',');
        String auxRetenciones = String.valueOf(retenciones);
        auxRetenciones = auxRetenciones.replace('.', ',');
        
        ArrayList<String> valores= new ArrayList<>();
        valores.add(String.valueOf(idLiquidacionIndividual));
        valores.add(String.valueOf(idConcepto));
        valores.add(unidadConcepto);
        valores.add(String.valueOf(auxHaberesR));
        valores.add(String.valueOf(auxHaberesNR));
        valores.add(String.valueOf(auxRetenciones));
        
        idDetalleLiquidacion = sismain.getControladorBD().aniadir(
                valores, "DETALLE_LIQ",true);
        valores.clear();
        return idDetalleLiquidacion;
    }
    
    public ArrayList buscarBD(String criterioBusqueda,
                         DefaultTableModel modeloTabla){
        
        ArrayList<Long> indices;// = new ArrayList<>();

        String tablas = "DETALLE_LIQ D";
        String columnas = "D.LIQ_IND_SUELDO_ID_LIQIND";
        String condicion = "D.LIQ_IND_SUELDO_ID_LIQIND = " + criterioBusqueda;
               
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
        tablas = "DETALLE_LIQ D";
        columnas = "D.LIQ_IND_SUELDO_ID_LIQIND, D.CONCEPTO_SUELDO_ID_CONCEPTO,"
                + "D.CONCEPTO_SUELDO_UNIDAD, D.HABERES_REMUNERATIVOS, "
                + "D.HABERES_NO_REMUNERATIVOS, D.RETENCIONES";
        condicion = "D.LIQ_IND_SUELDO_ID_LIQIND = '"+ idLiquidacionIndividual + "'";
        
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        for(int i = 0; i<registros.size();i++){
            setIdLiquidacionIndividual(Long.valueOf(registros.get(i).get(0).toString()));
            setIdConcepto(Long.parseLong(registros.get(i).get(1).toString()));
            setUnidadConcepto(registros.get(i).get(2).toString());
            setHaberesRemunerativos(Double.valueOf(registros.get(i).get(3).toString()));
            setHaberesNoRemunerativos(Double.valueOf(registros.get(i).get(4).toString()));
            setRetenciones(Double.valueOf(registros.get(i).get(5).toString()));
            
        }
     
    }

        
    
}
