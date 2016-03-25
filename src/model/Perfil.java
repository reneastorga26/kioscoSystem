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
import javax.swing.table.DefaultTableModel;
import sistemakiosco.sismain;

/**
 *
 * @author CX
 */
public class Perfil {
 
    private int idPerfil;
    private String usuario;
    private String password;
    private String tipo;
    private int idEmpleado;
    
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

  
        private long guardarBD() {
        ArrayList<String> valores= new ArrayList<>();
        valores.add(String.valueOf(idPerfil));
        valores.add(usuario);
        valores.add(password);
        valores.add(tipo);
        valores.add(String.valueOf(idEmpleado));
        sismain.getControladorBD().aniadirBD(valores,"PERFIL",false);
        valores.clear();
        return idPerfil;
        
        
    }
        
        public ArrayList buscarBD(String columnaBusqueda, 
                         DefaultTableModel modeloTabla,
                         boolean preBuscar){
        ArrayList<String> indices = new ArrayList<>();
        String criterioBusqueda;
        String criterioPreBusqueda;
            
        String tablas = "PERFIL P";
        String columnas = "P.ID_PERFIL,P.USUARIO,P.PASSWORD,P.TIPO,P.EMPLEADO_ID_EMPLEADO";
        String condicion = "USUARIO = " + usuario;
     
        indices = sismain.getControladorBD().buscarBD(tablas, columnas, condicion, modeloTabla);
        return indices;
    }

    public void modificarBD(ArrayList<String> txt, String tabla, String columna, String id){
             
             String set = "DNI = '" + txt.get(0) + "', NOMBRE_APELLIDO = '" + txt.get(1) + 
                          "', FECHA_NAC = TO_DATE(" + txt.get(2) + "), SEXO = '" + txt.get(3) + 
                        "', OBSERVACIONES = '" + txt.get(4) + "'";
             try{

                 String query = "UPDATE " + tabla + " SET " + set + " WHERE " + columna + " = " + id ;
                 System.out.println(query);
                 sismain.getConexion().getStatement().execute(query);
             }catch (SQLException ex) {
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
}
