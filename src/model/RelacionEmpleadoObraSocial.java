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

    private Empleado idEmpleado;
    private ObraSocial idObraSocial;
    
    public RelacionEmpleadoObraSocial(){
        
    }
    
    public RelacionEmpleadoObraSocial(Empleado idEmpleado, ObraSocial idObraSocial){
    this.idEmpleado = idEmpleado;
    this.idObraSocial = idObraSocial;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public ObraSocial getIdObraSocial() {
        return idObraSocial;
    }

    public void setIdObraSocial(ObraSocial idObraSocial) {
        this.idObraSocial = idObraSocial;
    }

    public void modificarBD(ArrayList<String> cadena, String cadenaId){
             
            String set = "DESCRIPCION = " + cadena.get(0) + ", BANCO = " + cadena.get(1) 
                    + "CUENTA_BANCARIA = " + cadena.get(2);
            
            sismain.getControladorBD().modificar(set, "RELACION_EMPLEADO_OS", "EMPLEADO_ID_EMPLEADO", cadenaId);
    }
    
    
}
