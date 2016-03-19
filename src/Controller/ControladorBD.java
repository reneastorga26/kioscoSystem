/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import sistemakiosco.sismain;

/**
 *
 * @author hites
 */
public class ControladorBD {
    
    private ResultSet rs;
    
    
    public ControladorBD(){
        
    }
    
    
    public ResultSet leer(String tabla) {
        rs = null;
        String query = "SELECT * FROM " + tabla;
        try {
            rs = sismain.getConexion().getStatement().executeQuery(query);
            
        } catch (SQLException ex) {
            System.out.println("Error al leer");
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }    
     
    
    public long aniadirBD(ArrayList<String> valores, String tabla, boolean idcargado){
        String values = new String();
        String campos = new String();
        long index = 0;
        values = "";
        campos= "";
        rs = leer(tabla);
        int startField = 1;
        int j = 0;
        if(idcargado) startField = 0; 
        try {
            for (int i = startField; i < rs.getMetaData().getColumnCount(); i++) {
                String tipo= rs.getMetaData().getColumnTypeName(i+1);
                campos = campos + rs.getMetaData().getColumnName(i+1);
                if(tipo ==  "DATE")
                    values = values +  "TO_DATE(" +valores.get(j)+ ")";
                    
                else
                    values = values + "'" + valores.get(j)+"'";
                if(i!=rs.getMetaData().getColumnCount() -1){
                    values=values+",";
                    campos=campos+",";
                }
                j++;
            }
        values = "(" + values + ")";
        campos= "(" + campos + ")";
        String query = "INSERT INTO SISKIOS." + tabla + " " + campos + " values " + values;
        System.out.println(query);
        sismain.getConexion().getStatement().execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        index=obtenerUltimoIndice(tabla);
        return index;
    }
    
    
    public void eliminar(ArrayList<JTextField> txts, String tabla){  
        rs = leer(tabla);
        int i = Integer.parseInt(txts.get(0).getText());
        try {
            String query = "DELETE FROM " + tabla + " WHERE " + rs.getMetaData().getColumnName(1) + " = " + i;
            sismain.getConexion().getStatement().execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public void modificar(ArrayList<JTextField> txts, String tabla){
        rs = leer(tabla);
        String set = "";
        try {
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                if(i!=0){
                set = set + " " + rs.getMetaData().getColumnName(i+1) + " = '" + txts.get(i).getText() + "'";
                if(i!= rs.getMetaData().getColumnCount() - 1)
                    set=set + ",";
            }
            }
            int i = Integer.parseInt(txts.get(0).getText());
            String query = "UPDATE " + tabla + " SET " + set + " WHERE " + rs.getMetaData().getColumnName(1) + " = " + i;
            sismain.getConexion().getStatement().execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
         
    
    public ArrayList buscar (String columnas, 
                         String tablas,
                         String condicion,
                         DefaultTableModel modeloTabla
                         ){
    
     rs = null;
     String query = "SELECT "+columnas+" FROM "+tablas+" WHERE "+ condicion;
     int numIndice=1;
     ArrayList<String> indices = new ArrayList<>();
     char character;
     for(int i=0; i<tablas.length(); i++){
         character = tablas.charAt(i);
         if(character==','){
             numIndice++;
         }
     }
     System.out.println(query);
        try {
            
            rs=sismain.getConexion().getStatement().executeQuery(query);
            System.out.println("Buscando...");
            while(rs.next()){
                
                indices.add(rs.getObject(1).toString());
                Object[] fila = new Object[rs.getMetaData().getColumnCount()]; 
                
                for(int i = 0; i<rs.getMetaData().getColumnCount(); i++){
                    fila[i]=rs.getObject(i+1);
                    System.out.println(fila[i]);
                }
                modeloTabla.addRow(fila);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
     
     return indices;
     }
     
    
    public ResultSet buscarRegistros(String columnas, 
                         String tablas,
                         String condicion
                         ){
    
     rs = null;
     String query = "SELECT "+columnas+" FROM "+tablas+" WHERE "+ condicion;
     
     System.out.println(query);
        try {
            
            rs=sismain.getConexion().getStatement().executeQuery(query);
            System.out.println("Buscando...");
                      
            
        } catch (SQLException ex) {
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
     
     return rs;
     } 
    
    
    public long obtenerUltimoIndice(String tabla){
        long index=0;
        rs = leer(tabla);
        try {
            while(rs.next()){
                if(index<Long.parseLong(rs.getObject(1).toString())){
                    index=Long.parseLong(rs.getObject(1).toString());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al leer");
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return index;
     }
    
    
    public long obtenerUltimoRegistro(String tabla, String id){
        long reg = 0;
        rs = null;
        String query = "SELECT * FROM " + tabla + " ORDER BY " + id + " DESC" ;
        try {
            rs = sismain.getConexion().getStatement().executeQuery(query);
            while(rs.next()){
                if(reg<Long.parseLong(rs.getObject(1).toString())){
                    reg=Long.parseLong(rs.getObject(1).toString());
                    
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al leer");
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reg;
    }
    
}


        /*

INSERT INTO "SISKIOS"."SIQ" (COLUMN1, COLUMN2, COLUMN3, COLUMN4, COLUMN5, COLUMN6) VALUES ('121', 'Hola0', 'Hola12345', 'M', TO_DATE('2016-01-16 18:45:07', 'YYYY-MM-DD HH24:MI:SS'), '224')
            column id_persona format a10
column persona_id_persona format a10
column dni format a10
column nombre_apellido format a10
            select P.ID_PERSONA , C.PERSONA_ID_PERSONA ,P.DNI , P.NOMBRE_APELLIDO
    from PERSONA P, CLIENTE C
   where (P.DNI LIKE '2%' OR P.DNI = 2) AND P.ID_PERSONA = C.PERSONA_ID_PERSONA;
     
            ID_PERSONA PERSONA_ID DNI        NOMBRE_APE
---------- ---------- ---------- ----------
        48         48 23132      SDAS      

            */
