/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Perfil;
import model.Sesion;

/**
 *
 * @author hites
 */
public class Conexion {
     
    private String servidor;
    private String sid;
    private String port;
    private String protocolo;
    private String driver;
    private final String usuario;
    private final String password;
    private Sesion sesion;
    private Connection conection;
    private Statement statement;
    private Perfil perfil;

    public Perfil getPerfil() {
        return perfil;
    }
    
    public Conexion(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public Connection getConection() {
        return conection;
    }

    public Statement getStatement() {
        return statement;
    }

    public Sesion getSesion() {
        return sesion;
    }

    
    public boolean conectar() {
        
        boolean resultadoConexion = false;
        int resultado;
        protocolo="jdbc:oracle:thin";
        servidor="localhost";
        port="1521";
        sid="xe";
        driver="oracle.jdbc.OracleDriver";
        String url = protocolo + ":@" + servidor + ":" + port + ":" + sid;
        System.out.println(url + " " + usuario + " " + password);
        try {
            Class.forName(driver);
            conection = DriverManager.getConnection(url, 
                    "SISKIOS", "SIS01");
            statement = conection.
                    createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            resultadoConexion = true;
            
            perfil=new Perfil();
            resultado = perfil.check_ampliarInfoBD(usuario, password);
            switch(resultado){
                case 1: sesion = new Sesion(perfil.getIdPerfil());
                        resultadoConexion=true;
                        break;
                case 2: JOptionPane.showMessageDialog(null, 
                        "Cliente o Contraseña Incorrecta","ERROR AL CONECTAR",
                        JOptionPane.ERROR_MESSAGE);
                        break;
                case 3:JOptionPane.showMessageDialog(null, 
                        "Su usuario se encuentra bloqueado"
                        + "\n No puede ingresar al Sistema","USUARIO BLOQUEADO",
                        JOptionPane.ERROR_MESSAGE);
                        break;
            }  
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultadoConexion;
    }
}
