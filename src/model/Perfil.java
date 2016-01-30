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
public class Perfil {
 
    private int idPerfil;
    private String usuario;
    private String password;
    private String tipo;
    private Empleado idEmpleado;
    
    public Perfil(){
        
    }
    
    public Perfil(int idPerfil, String usuario, String password, String tipo, 
            Empleado idEmpleado){
        
        this.idPerfil = idPerfil;
        this.usuario = usuario;
        this.password = password;
        this.tipo = tipo;
        
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    
    
    
}
