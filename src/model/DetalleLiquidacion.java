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
public class DetalleLiquidacion {
    
    private int idDetalleLiquidacion;
    private double subimporte;
    private LiquidacionIndividualSueldo idLiquidacionIndividual;
    private ConceptoSueldo idConcepto;
    
    public DetalleLiquidacion(){
        
    }
    
    public DetalleLiquidacion(int idDetalleLiquidacion, double subimporte,
                                LiquidacionIndividualSueldo idLiquidacionIndividual, 
                                ConceptoSueldo idConcepto){
     this.idDetalleLiquidacion = idDetalleLiquidacion;
     this.subimporte = subimporte;
    }

    public int getIdDetalleLiquidacion() {
        return idDetalleLiquidacion;
    }

    public void setIdDetalleLiquidacion(int idDetalleLiquidacion) {
        this.idDetalleLiquidacion = idDetalleLiquidacion;
    }

    public double getSubimporte() {
        return subimporte;
    }

    public void setSubimporte(double subimporte) {
        this.subimporte = subimporte;
    }

    public LiquidacionIndividualSueldo getIdLiquidacionIndividual() {
        return idLiquidacionIndividual;
    }

    public void setIdLiquidacionIndividual(LiquidacionIndividualSueldo idLiquidacionIndividual) {
        this.idLiquidacionIndividual = idLiquidacionIndividual;
    }

    public ConceptoSueldo getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(ConceptoSueldo idConcepto) {
        this.idConcepto = idConcepto;
    }

        
    
}
