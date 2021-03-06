/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author CX
 */
public abstract class Persona {

    private long idPersona; 
    private String nombreApellido;
    private String dni;
    private char sexo;
    private String fechaNacimiento;
    private String observaciones;
    private ArrayList<Telefono> telefonos = new ArrayList<>();
    private ArrayList<Domicilio> domicilios = new ArrayList<>();
    private ArrayList<CorreoElectronico> correosElectronicos = new ArrayList<>();

    public ArrayList<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(ArrayList<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public ArrayList<Domicilio> getDomicilios() {
        return domicilios;
    }

    public void setDomicilios(ArrayList<Domicilio> domicilios) {
        this.domicilios = domicilios;
    }

    public ArrayList<CorreoElectronico> getCorreosElectronicos() {
        return correosElectronicos;
    }

    public void setCorreosElectronicos(ArrayList<CorreoElectronico> correosElectronicos) {
        this.correosElectronicos = correosElectronicos;
    }
    
    
    public Persona(){
        
    }
    
    public Persona(int idPersona, String nombreApellido, String dni, char sexo,
            String fechaNacimiento, String observaciones){
        
        this.idPersona = idPersona;
        this.nombreApellido = nombreApellido;
        this.dni = dni;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.observaciones = observaciones;
        
    }

    public long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(long idPersona) {
        this.idPersona = idPersona;
    }

  

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    
    
    
}
