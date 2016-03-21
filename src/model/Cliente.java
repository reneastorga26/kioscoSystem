/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Controller.ControladorBD;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import sistemakiosco.sismain;

/**
 *
 * @author CX
 */
public class Cliente extends Persona{
    
    private long idCliente;

    public Cliente(){
    super ();
        
    }
    public Cliente(int idPersona, String nombreApellido, String dni, char sexo,
            String fechaNacimiento, String observaciones, int idCliente){ 
    super (idPersona, nombreApellido, dni, sexo, fechaNacimiento, observaciones);
    this.idCliente = idCliente;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }
    
    
    
    public long guardarBD(){
        long idPersona=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(super.getNombreApellido());
        valores.add(super.getDni());
        valores.add(String.valueOf(super.getSexo()));
        valores.add(super.getFechaNacimiento());
        valores.add(super.getObservaciones());
        idPersona = sismain.getControladorBD().aniadirBD(valores, "PERSONA",false);
        valores.clear();
        valores.add(String.valueOf(idPersona));
        sismain.getControladorBD().aniadirBD(valores,"CLIENTE",false);
        return idPersona;
    }
    
    public ArrayList buscarBD(String columnaBusqueda, 
                         DefaultTableModel modeloTabla,
                         boolean preBuscar){
        ArrayList<String> indices = new ArrayList<>();

        String criterioBusqueda;
        String criterioPreBusqueda;
        if(columnaBusqueda.equals("DNI")){
            criterioBusqueda=super.getDni();
            criterioPreBusqueda="'"+super.getDni()+"%'";
        }
        else{
            criterioBusqueda="'"+super.getNombreApellido()+"'";
            criterioPreBusqueda="'"+super.getNombreApellido()+"%'";
        }
        String tablas = "PERSONA P, CLIENTE C, DOMICILIO D, CORREOELECTRONICO CE, TELEFONO T";
        String columnas = "P.ID_PERSONA , C.PERSONA_ID_PERSONA , P.DNI , "
                + "P.NOMBRE_APELLIDO, D.PERSONA_ID_PERSONA, CE.PERSONA_ID_PERSONA"
                + "T.PERSONA_ID_PERSONA";
        String condicion;
        if(preBuscar){
            condicion = "(P."+columnaBusqueda+" LIKE "+criterioPreBusqueda+" OR P."+columnaBusqueda+" = "+ criterioBusqueda+" ) AND P.ID_PERSONA = C.PERSONA_ID_PERSONA";
        }
        else{
            condicion = "P."+columnaBusqueda+" = "+criterioBusqueda+
                    " AND P.ID_PERSONA = C.PERSONA_ID_PERSONA AND P.ID_PERSONA = D.PERSONA_ID_PERSONA"
                    + "AND P.ID_PERSONA = CE.PERSONA_ID_PERSONA AND P.ID_PERSONA = T.PERSONA_ID_PERSONA";
        }
        indices = sismain.getControladorBD().buscar(tablas, columnas, condicion, modeloTabla);
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

