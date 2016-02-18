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
public class Empleado extends Persona{
    
    private long idEmpleado;
    private String cuil;
    private String fechaInicioRelacionLaboral;
    
    public Empleado(){
        super();
    }
    
    public Empleado(int idPersona, String nombreApellido, String dni, char sexo,
            String fechaNacimiento, String observaciones,  
            long idEmpleado, String cuil, String fechaInicioRelacionLaboral){
        super(idPersona, nombreApellido, dni, sexo, fechaNacimiento, observaciones);
    this.idEmpleado = idEmpleado;
    this.cuil = cuil;
    this.fechaInicioRelacionLaboral = fechaInicioRelacionLaboral;
    }

    public long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getFechaInicioRelacionLaboral() {
        return fechaInicioRelacionLaboral;
    }

    public void setFechaInicioRelacionLaboral(String fechaInicioRelacionLaboral) {
        this.fechaInicioRelacionLaboral = fechaInicioRelacionLaboral;
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
        sismain.getControladorBD().aniadirBD(valores,"EMPLEADO",false);
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
        String tablas = "PERSONA P, EMPLEADO E";
        String columnas = "P.ID_PERSONA , E.PERSONA_ID_PERSONA ,P.DNI , P.NOMBRE_APELLIDO";
        String condicion;
        if(preBuscar){
            condicion = "(P."+columnaBusqueda+" LIKE "+criterioPreBusqueda+" OR P."+columnaBusqueda+" = "+ criterioBusqueda+" ) AND P.ID_PERSONA = E.PERSONA_ID_PERSONA";
        }
        else{
            condicion = "P."+columnaBusqueda+" = "+criterioBusqueda+" AND P.ID_PERSONA = E.PERSONA_ID_PERSONA";
        }
        indices = sismain.getControladorBD().buscar(tablas, columnas, condicion, modeloTabla);
        return indices;
    }   
    
    public void update(ArrayList<String> txt, String tabla, String columna, String id){
             String set;
             if(tabla.equals("PERSONA")){
                 set = "DNI = '" + txt.get(0) + "', NOMBRE_APELLIDO = '" + txt.get(1) + 
                          "', FECHA_NAC = TO_DATE(" + txt.get(2) + "), SEXO = '" + txt.get(3) + 
                        "', OBSERVACIONES = '" + txt.get(4) + "'";
             }else{
                 set = "CUIL = '" + txt.get(0) + 
                        "', FECHA_INICIO_RELACION_LABORAL = TO_DATE(" + txt.get(1) + ")";
             }
             try{

                 String query = "UPDATE " + tabla + " SET " + set + " WHERE " + columna + " = " + id;
                 System.out.println(query);
                 sismain.getConexion().getStatement().execute(query);
                 set = "";
             }catch (SQLException ex) {
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
