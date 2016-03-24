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
        
        boolean resultado = false;
        long usuarioBD=0;
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
            String query = "SELECT ID_PERFIL,USUARIO,PASSWORD FROM PERFIL WHERE "
                + "USUARIO = "+ usuario +" AND PASSWORD = "+ password +"; ";
            ResultSet rs=null;
            rs=statement.executeQuery(query);
            while(rs.next()){
                if(rs.getString(2).equals(usuario) && rs.getString(3).equals(password)){
                    usuarioBD=rs.getInt(1);
                    resultado = true;
                    break;
                }
            }
            if (resultado == true){
                sesion = new Sesion(usuarioBD);
            }
            else{
                
            }
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
}
