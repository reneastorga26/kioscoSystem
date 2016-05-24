/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import sistemakiosco.sismain;

/**
 *
 * @author CX
 */
public class RelacionEmpleadoConceptos {
    
    private long idEmpleado;
    private long idConcepto;
    private long idTipoLiquidacion;
    private String unidadConcepto;
    
    public RelacionEmpleadoConceptos(){
        
    }
    
    public RelacionEmpleadoConceptos(long idEmpleado, long idConcepto, 
                long idTipoLiquidacion, String unidadConcepto){
        this.idConcepto = idConcepto;
        this.idEmpleado = idEmpleado;
        this.idTipoLiquidacion = idTipoLiquidacion;
        this.unidadConcepto = unidadConcepto;
    }

    public long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public long getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(long idConcepto) {
        this.idConcepto = idConcepto;
    }

    public long getIdTipoLiquidacion() {
        return idTipoLiquidacion;
    }

    public void setIdTipoLiquidacion(long idTipoLiquidacion) {
        this.idTipoLiquidacion = idTipoLiquidacion;
    }

    public String getUnidadConcepto() {
        return unidadConcepto;
    }

    public void setUnidadConcepto(String unidadConcepto) {
        this.unidadConcepto = unidadConcepto;
    }
    
    
    
    public long guardarBD(){
        long idRelacionEmpleadoConceptos=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(String.valueOf(idEmpleado));
        valores.add(String.valueOf(idConcepto));
        valores.add(String.valueOf(idTipoLiquidacion));
        valores.add(unidadConcepto);
        idRelacionEmpleadoConceptos = sismain.getControladorBD().aniadir(valores, 
                "RELACION_EMPLEADO_CONCEPTOS",true);
        return idRelacionEmpleadoConceptos;
    }
    
    public void modificarBD(){
            
            String tablas;
            String set;
            String condicion;
            tablas = "RELACION_EMPLEADO_CONCEPTOS R";
            set = "R.UNIDAD_CONCEPTO = '" + unidadConcepto + "'";        
            condicion = "R.EMPLEADO_ID_EMPLEADO = '" + idEmpleado + "' AND "
                    + "R.CONCEPTO_ID_CONCEPTO = '"+ idConcepto + "' AND "
                    + "R.TIPO_LIQ_ID_TIPO_LIQ = '" + idTipoLiquidacion + "'";    
            
            sismain.getControladorBD().modificar(tablas,set,condicion);
            
    }
    
    
    public void eliminarBD(long id_referenciado){
        
        String tabla = "RELACION_EMPLEADO_CONCEPTOS R";
        String condicion = "R.CONCEPTO_ID_CONCEPTO = '"+ id_referenciado +"'";
        
        sismain.getControladorBD().eliminar(tabla, condicion);
               
    }
}
