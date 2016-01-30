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

    public boolean conectar() {
        
        boolean resultado = false;
        protocolo="jdbc:oracle:thin";
        servidor="localhost";
        port="1521";
        sid="xe";
        driver="oracle.jdbc.OracleDriver";
        String url = protocolo + ":@" + servidor + ":" + port + ":" + sid;
        System.out.println(url + " " + usuario + " " + password);
        try {
            Class.forName(driver);
            conection = DriverManager.getConnection(url, usuario, password);
            statement = conection.
                    createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            resultado = true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
}
