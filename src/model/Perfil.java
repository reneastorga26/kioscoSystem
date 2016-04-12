/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Controller.ControladorBD;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistemakiosco.sismain;

/**
 *
 * @author CX
 */
public class Perfil {
 
    private long idPerfil;
    private String usuario;
    private String password;
    private String tipo;
    private long idEmpleado;
    private char estado;
    
    public Perfil(){
        
    }
    
    
        public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
    public Perfil(int idPerfil, String usuario, String password, String tipo, 
            Empleado idEmpleado){
        
        this.idPerfil = idPerfil;
        this.usuario = usuario;
        this.password = password;
        this.tipo = tipo;
        
    }

    public long getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(long idPerfil) {
        this.idPerfil = idPerfil;
    }

    public long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(long idEmpleado) {
        this.idEmpleado = idEmpleado;
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

  
        private long guardarBD() {
        ArrayList<String> valores= new ArrayList<>();
        valores.add(String.valueOf(idPerfil));
        valores.add(usuario);
        valores.add(password);
        valores.add(tipo);
        valores.add(String.valueOf(idEmpleado));
        sismain.getControladorBD().aniadir(valores,"PERFIL",false);
        valores.clear();
        return idPerfil;
        
        
    }
        public int check_ampliarInfoBD(String usuario, String password){
            int resultado=2;
            ArrayList<ArrayList<Object>> registro;
            String tabla="PERFIL";
            String columnas="ID_PERFIL, USUARIO, PASSWORD, TIPO, EMPLEADO_ID_EMPLEADO, ESTADO"; 
            String condicion = "USUARIO = '"+usuario+"' AND "
                    + "PASSWORD = '"+password+"' AND ROWNUM=1";
            
            registro = sismain.getControladorBD().
                    extenderInfo(columnas, tabla, condicion);
            
            System.out.println("idPerfil = "+ registro.get(0).get(0).toString());
            if(!registro.isEmpty()){
                idPerfil=Long.valueOf(registro.get(0).get(0).toString());
                this.usuario=registro.get(0).get(1).toString();
                this.password=registro.get(0).get(2).toString();
                tipo=registro.get(0).get(3).toString();
                idEmpleado=Long.parseLong(registro.get(0).get(4).toString());
                estado=registro.get(0).get(5).toString().charAt(0);
                if(usuario.equals(this.usuario) && password.equals(this.password)){
                    if(estado=='H'){
                        resultado = 1;
                    }
                    else{
                        resultado = 3;
                    }
                }
            }
            System.out.println("idPerfil = " +idPerfil+ " usuario = "+
                                this.usuario +" password = "+ this.password +
                                " estado = " + estado + " Resultado conexion = "+resultado);
            return resultado;
        }
        
        public ArrayList buscarBD(String columnaBusqueda, 
                         DefaultTableModel modeloTabla,
                         boolean preBuscar){
        ArrayList<Long> indices = new ArrayList<>();
        String criterioBusqueda;
        String criterioPreBusqueda;
            
        String tablas = "PERFIL P";
        String columnas = "P.ID_PERFIL,P.USUARIO,P.PASSWORD,P.TIPO,P.EMPLEADO_ID_EMPLEADO";
        String condicion = "USUARIO = " + usuario;
     
        indices = sismain.getControladorBD().buscar(columnas, tablas, condicion, null);
        
        return indices;
    }

    public void modificarBD(long id_referenciado){
            
                   
            String tablas = "PERFIL P";
            String set = "P.USUARIO = '" + usuario + "', P.PASSWORD = '"+ password +"', "
                    + "P.TIPO = '" + tipo + "', ESTADO = '" + estado + "'";
            String condicion = "P.EMPLEADO_ID_EMPLEADO = '"+  idEmpleado +"' AND "
                    + "P.ID_PERFIL = '" + id_referenciado + "'";
             
             sismain.getControladorBD().modificar(tablas,set,condicion);
    } 
    
    public void eliminarBD(long id_referenciado){
        
                
        String tabla = "PERFIL P";
        String condicion = "P.EMPLEADO_ID_EMPLEADO = '"+  idEmpleado +"' AND "
                    + "P.ID_PERFIL = '" + id_referenciado + "'";
        
        sismain.getControladorBD().eliminar(tabla, condicion);
               
    }
    
    public void habilitarBD(){
        
        
        String tablas = "PERFIL P";
        String set = "P.ESTADO = 'H'";
        String condicion = "P.EMPLEADO_ID_EMPLEADO = '"+  idEmpleado + "'";

        sismain.getControladorBD().modificar(tablas,set,condicion);
        
    }
    
    public void deshabilitarBD(){
        
        String tablas = "PERFIL P";
        String set = "P.ESTADO = 'D'";
        String condicion = "P.EMPLEADO_ID_EMPLEADO = '"+  idEmpleado + "'";
        
        sismain.getControladorBD().modificar(tablas, set, condicion);

    }
    
}
