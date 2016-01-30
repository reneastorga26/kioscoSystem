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
public class Empleado {
    
    private int idEmpleado;
    private String cuil;
    private String fechaInicioRelacionLaboral;
    private Persona idPersona;
    
    public Empleado(){
        
    }
    
    public Empleado(int idEmpleado, String cuil, String fechaInicioRelacionLaboral,
                    Persona idPersona){
    this.idEmpleado = idEmpleado;
    this.cuil = cuil;
    this.fechaInicioRelacionLaboral = fechaInicioRelacionLaboral;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getFechaInicioRelacionLaboral() {
        return fechaInicioRelacionLaboral;
    }

    public void setFechaInicioRelacionLaboral(String fechaInicioRelacionLaboral) {
        this.fechaInicioRelacionLaboral = fechaInicioRelacionLaboral;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    
    
    
}
