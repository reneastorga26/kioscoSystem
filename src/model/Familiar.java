/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import sistemakiosco.sismain;

/**
 *
 * @author CX
 */
public class Familiar extends Persona{
    
    private int idFamiliar;
    private String parentesco;
    private long idEmpleado;
    
    public Familiar(){
        super();
    }
    
    public Familiar(int idPersona, String nombreApellido, String dni, char sexo,
            String fechaNacimiento, String observaciones, int idFamiliar, 
            String parentesco, long idEmpleado){
        super(idPersona, nombreApellido, dni, sexo, fechaNacimiento, observaciones);
        this.idFamiliar = idFamiliar;
        this.parentesco = parentesco;
        this.idEmpleado = idEmpleado;
    }

    public int getIdFamiliar() {
        return idFamiliar;
    }

    public void setIdFamiliar(int idFamiliar) {
        this.idFamiliar = idFamiliar;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    
    public long guardarBD(){
        long idPersona=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(super.getNombreApellido());
        valores.add(super.getDni());
        valores.add(super.getFechaNacimiento());
        idPersona = sismain.getControladorBD().aniadir(valores, "PERSONA",false);
        valores.clear();
        
        valores.add(parentesco);
        valores.add(String.valueOf(idEmpleado));
        sismain.getControladorBD().aniadir(valores,"FAMILIAR",false);
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
        String tablas = "PERSONA P, EMPLEADO E, FAMILIAR F";
       
        String columnas = "P.ID_PERSONA , E.PERSONA_ID_PERSONA , P.DNI , "
                + "P.NOMBRE_APELLIDO, P.FECHA_NAC, F.ID_FAMILIAR, F.PARENTESCO, "
                + "F.EMPLEADO_ID_EMPLEADO";
        String condicion;
        if(preBuscar){
            condicion = "(P."+columnaBusqueda+" = "+criterioPreBusqueda+" OR P."+columnaBusqueda+" = "+ criterioBusqueda+" ) AND P.ID_PERSONA = E.PERSONA_ID_PERSONA";
            indices = sismain.getControladorBD().buscar(tablas, columnas, condicion, modeloTabla);
            return indices;
        }
        else{
            condicion = "P."+columnaBusqueda+" = "+criterioBusqueda+
                    " AND P.ID_PERSONA = E.PERSONA_ID_PERSONA AND E.ID_EMPLEADO = F.EMPLEADO_ID_EMPLEADO";
            indices = sismain.getControladorBD().buscar(tablas, columnas, condicion, modeloTabla);
            return indices;
        }
        
        
        
    }

    public void modificarBD(ArrayList<String> cadena, String cadenaId){
        
             String set = "DNI = '" + cadena.get(0) + "', NOMBRE_APELLIDO = '" + cadena.get(1) + 
                          "', FECHA_NAC = TO_DATE(" + cadena.get(2) + "), SEXO = '" + cadena.get(3) + 
                        "', OBSERVACIONES = '" + cadena.get(4) + "'";
             
            sismain.getControladorBD().modificar(set, "PERSONA", "ID_PERSONA", cadenaId);
    }
    
    public void eliminarBD(String cadenaId){
            sismain.getControladorBD().eliminar("FAMILIAR", "EMPLEADO_ID_EMPLEADO", cadenaId);
    }
    
}
