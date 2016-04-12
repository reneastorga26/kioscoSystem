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
public class RelacionEmpleadoObraSocial {

    private long idEmpleado;
    private long idObraSocial;
    
    public RelacionEmpleadoObraSocial(){
        
    }
    
    public RelacionEmpleadoObraSocial(long idEmpleado, long idObraSocial){
    this.idEmpleado = idEmpleado;
    this.idObraSocial = idObraSocial;
    }

    public long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public long getIdObraSocial() {
        return idObraSocial;
    }

    public void setIdObraSocial(long idObraSocial) {
        this.idObraSocial = idObraSocial;
    }
    
    public long guardarBD(){
        long idRelacionEmpleadoObraSocial=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(String.valueOf(idEmpleado));
        valores.add(String.valueOf(idObraSocial));
        idRelacionEmpleadoObraSocial = sismain.getControladorBD().aniadir(valores, "RELACION_EMPLEADO_OS",false);
        return idRelacionEmpleadoObraSocial;
    }
    
    
    public void eliminarBD(long id_referenciado){
        
        String tabla = "RELACION_EMPLEADO_OS R";
        String condicion = "R.OBRA_SOCIAL_ID_OBRA_SOCIAL = '"+ id_referenciado +"'";
        
        sismain.getControladorBD().eliminar(tabla, condicion);
               
    }
    
    
}
