/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import UI.Buscar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CX
 */
public class ClaseControl {
    
    private String queryCliente;
    private String queryEmpleado;
    private String queryProveedor;
    public DefaultTableModel modeloTabla = new DefaultTableModel();
    static Connection cn;
    static Statement s;
    static ResultSet rs;
    
    public ClaseControl(){
        
    }
    
    public void Querys(){
        //queryCliente = "select p.DNI,p.NOMBRE_APELLIDO from cliente c, persona p where p.ID_PERSONA = c.PERSONA_ID_PERSONA";
        queryEmpleado = "select p.DNI,p.NOMBRE_APELLIDO from empleado e, persona p where p.ID_PERSONA = e.PERSONA_ID_PERSONA";
        queryProveedor = "select p.DNI,p.NOMBRE_APELLIDO from cliente c, persona p where p.ID_PERSONA = c.PERSONA_ID_PERSONA";
        
    }
    public void consultarTablaCliente(ResultSet rs){
    try {
            //Para establecer el modelo al JTable
        
            //Para conectarnos a nuestra base de datos
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
            // Establecemos los valores de cadena de conexión, usuario y contraseña
        cn = DriverManager.getConnection(url, "SISKIOS", "SIS01");
            //Para ejecutar la consulta
        s = cn.createStatement();
            //Ejecutamos la consulta y los datos lo almacenamos en un ResultSet
        rs = s.executeQuery("select p.DNI,p.NOMBRE_APELLIDO from cliente c, persona p where p.ID_PERSONA = c.PERSONA_ID_PERSONA");
            //Obteniendo la informacion de las columnas que estan siendo consultadas
       
       } catch (Exception ex) {
            ex.printStackTrace();
       }
    }
    
    public void consultarTablaProveedor(ResultSet rs){
    try {
            //Para establecer el modelo al JTable
        
            //Para conectarnos a nuestra base de datos
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
            // Establecemos los valores de cadena de conexión, usuario y contraseña
        cn = DriverManager.getConnection(url, "SISKIOS", "SIS01");
            //Para ejecutar la consulta
        s = cn.createStatement();
            //Ejecutamos la consulta y los datos lo almacenamos en un ResultSet
        rs = s.executeQuery("select p.DNI,p.NOMBRE_APELLIDO from cliente c, persona p where p.ID_PERSONA = c.PERSONA_ID_PERSONA");
            //Obteniendo la informacion de las columnas que estan siendo consultadas
       
       } catch (Exception ex) {
            ex.printStackTrace();
       }
    }
    
    
    
    
}
