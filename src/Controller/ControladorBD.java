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
import javax.swing.JOptionPane;
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
     
    
    public long aniadir(ArrayList<String> valores, String tabla, boolean idcargado){
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
                if(tipo.equals("DATE"))
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
    
    
    public void eliminar(String tabla, String condicion){  

        
        try {
            String query = "DELETE FROM " + tabla + " WHERE " + condicion;
            sismain.getConexion().getStatement().execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
      
    
    public void modificar(String tablas, String set, String condicion){

        try {
            String query = "UPDATE " + tablas + " SET " + set + " WHERE " + condicion ;
            System.out.println(query);
            sismain.getConexion().getStatement().execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
         
    
public ArrayList extenderInfo(String columnas, 
                         String tablas, String condicion){

        ArrayList<ArrayList<Object>> registros = new ArrayList<ArrayList<Object>>();
        rs=null;
        String query = "SELECT "+columnas+" FROM "+tablas+" WHERE "+ condicion+"";
        try {
            System.out.println(query);
            rs=sismain.getConexion().getStatement().executeQuery(query);
            int i=0;
            while(rs.next()){
                registros.add(new ArrayList<Object>());
                for(int j=0;j<rs.getMetaData().getColumnCount();j++){
                    registros.get(i).add(rs.getObject(j+1));
                }
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registros;
    }

    public ArrayList buscar (String columnas, 
                         String tablas,
                         String condicion,
                         DefaultTableModel modeloTabla
                         ){
    
     rs = null;
     String query = "SELECT "+columnas+" FROM "+tablas+" WHERE "+ condicion;

     ArrayList<Long> indices = new ArrayList<>();
        try {
            
            rs=sismain.getConexion().getStatement().executeQuery(query);
            System.out.println("Buscando...");
            while(rs.next()){
                if(modeloTabla == null){
                    JOptionPane.showMessageDialog(
                            null, "EL DATO INGRESADO YA EXISTE EN EL SISTEMA",
                            "Mensaje",JOptionPane.INFORMATION_MESSAGE);
                }else{
                indices.add(Long.parseLong(rs.getObject(1).toString()));
                
                Object[] fila = new Object[rs.getMetaData().getColumnCount()]; 
                
                for(int i = 0; i<rs.getMetaData().getColumnCount(); i++){
                    fila[i]=rs.getObject(i+1);
                }
                modeloTabla.addRow(fila);
                
            }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
     
     return indices;
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
