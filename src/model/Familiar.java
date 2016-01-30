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
public class Familiar {
    
    private int idFamiliar;
    private String dni;
    private String nombreApellido;
    private String fechaNacimiento;
    private String parentesco;
    private Empleado idEmpleado;
    
    public Familiar(){
        
    }
    
    public Familiar(int idFamiliar, String dni, String nombreApellido,
                    String fechaNacimiento, String parentesco, Empleado idEmpleado){
    this.idFamiliar = idFamiliar;
    this.dni = dni;
    this.nombreApellido = nombreApellido;
    this.fechaNacimiento = fechaNacimiento;
    this.parentesco = parentesco;
    }

    public int getIdFamiliar() {
        return idFamiliar;
    }

    public void setIdFamiliar(int idFamiliar) {
        this.idFamiliar = idFamiliar;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    
    
    
}
