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
    
    public ArrayList buscarBD(String criterioBusqueda, String columnaBusqueda,
            char estado, DefaultTableModel modeloTabla){
        
        ArrayList<Long> indices = new ArrayList<>();

        String tablas = "PERSONA P, FAMILIAR F";
        String columnas = "P.DNI, P.NOMBRE_APELLIDO, P.FECHA_NAC, F.PARENTESCO";
        String condicion = "F."+columnaBusqueda+ " = " + criterioBusqueda ;
        
        indices = sismain.getControladorBD().buscar(columnas, 
                tablas, condicion, modeloTabla);
        
        return indices;
    }

    public void modificarBD(){
            
            String tablas = "PERSONA P, FAMILIAR F";
            String set = "P.NOMBRE_APELLIDO = "+ super.getNombreApellido()+","
            + "P.DNI = " + super.getDni() + ","
            + "P.FECHA_NAC = " +super.getFechaNacimiento()+ ","
            + "F.PARENTESCO = '" + getParentesco() + "'";        
            String condicion = "F.EMPLEADO_ID_EMPLEADO = '"+ getIdEmpleado()+"'";
            sismain.getControladorBD().modificar(tablas,set,condicion);
        
    }
    
    public void eliminarBD(long id_referenciado){
        
        String tabla = "FAMILIAR F";
        String condicion = "F.EMPLEADO_ID_EMPLEADO = '" + id_referenciado +"'";
        
        sismain.getControladorBD().eliminar(tabla, condicion);
               
    }
    
}
