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
        idPersona = sismain.getControladorBD().aniadir(valores, "PERSONA",false);
        valores.clear();
        valores.add(String.valueOf(idPersona));
        sismain.getControladorBD().aniadir(valores,"EMPLEADO",false);
        return idPersona;
    }
    
    public ArrayList buscarBD(String columnaBusqueda, 
                              DefaultTableModel modeloTabla){
        
        ArrayList<String> indices = new ArrayList<>();

        String criterioBusqueda;
        String tablas;      
        String columnas;
        String condicion;
        
        if(columnaBusqueda.equals("DNI") || columnaBusqueda.equals("NOMBRE_APELLIDO")){
            criterioBusqueda=super.getDni();
            tablas  = "PERSONA P, EMPLEADO C";
            columnas = "P.ID_PERSONA , E.PERSONA_ID_PERSONA , P.DNI , P.NOMBRE_APELLIDO";
            condicion = "(P."+columnaBusqueda+" = "+ criterioBusqueda + " AND P.ID_PERSONA = E.PERSONA_ID_PERSONA "
                    + "AND P.ESTADO = '1'";
            indices = sismain.getControladorBD().buscar(tablas, columnas, condicion, modeloTabla);
            return indices;
        }
        else{
            criterioBusqueda="'"+super.getIdPersona()+"'";
            tablas = "PERSONA P, EMPLEADO E, DOMICILIO D, CORREOELECTRONICO CE, TELEFONO T";
            columnas = "P.ID_PERSONA , E.PERSONA_ID_PERSONA , P.DNI , "
                + "P.NOMBRE_APELLIDO, P.FECHA_NAC, P.SEXO, P.OBSERVACIONES, "
                + "P.ESTADO, E.CUIL, D.PERSONA_ID_PERSONA, D.DIRECCION, D.LOCALIDAD, "
                + "D.PROVINCIA, CE.PERSONA_ID_PERSONA, CE.DIRECCION, "
                + "T.PERSONA_ID_PERSONA, T.NUMERO, T.MOVIL";
            condicion = "P."+columnaBusqueda+" = "+criterioBusqueda+
                    " AND P.ID_PERSONA = E.PERSONA_ID_PERSONA AND P.ID_PERSONA = D.PERSONA_ID_PERSONA "
                    + "AND P.ID_PERSONA = CE.PERSONA_ID_PERSONA AND P.ID_PERSONA = T.PERSONA_ID_PERSONA "
                    + "AND P.ESTADO = '1'";
            indices = sismain.getControladorBD().buscar(tablas, columnas, condicion, null);
            return indices;
        }
        
        
    }
    
    public void modificarBD(ArrayList<String> cadena, String tabla, String cadenaId){
             String set;
             if(tabla.equals("PERSONA")){
                 set = "DNI = '" + cadena.get(0) + "', NOMBRE_APELLIDO = '" + cadena.get(1) + 
                          "', FECHA_NAC = TO_DATE(" + cadena.get(2) + "), SEXO = '" + cadena.get(3) + 
                        "', OBSERVACIONES = '" + cadena.get(4) + "'";
                 sismain.getControladorBD().modificar(set, "PERSONA", "ID_PERSONA", cadenaId);
             }else{
                 set = "CUIL = '" + cadena.get(0) + 
                        "', FECHA_INICIO_RELACION_LABORAL = TO_DATE(" + cadena.get(1) + ")";
                 sismain.getControladorBD().modificar(set, "EMPLEADO", "PERSONA_ID_PERSONA", cadenaId);
             }
             
    }
    
    public void eliminarFisicaBD(String cadenaId){
            sismain.getControladorBD().eliminar("EMPLEADO", "PERSONA_ID_PERSONA", cadenaId);
            sismain.getControladorBD().eliminar("PERSONA", "ID_PERSONA", cadenaId);
    }
    
    public void eliminarLogicaBD(String cadenaId){
            String set = "ESTADO = '0'";
             
            sismain.getControladorBD().modificar(set, "PERSONA", "ID_PERSONA", cadenaId);
    }
}
