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
        String tablas = "PERSONA P, CLIENTE C";
        String columnas = "P.ID_PERSONA , C.PERSONA_ID_PERSONA ,P.DNI , P.NOMBRE_APELLIDO";
        String condicion;
        if(preBuscar){
            condicion = "(P."+columnaBusqueda+" LIKE "+criterioPreBusqueda+" OR P."+columnaBusqueda+" = "+ criterioBusqueda+" ) AND P.ID_PERSONA = C.PERSONA_ID_PERSONA";
        }
        else{
            condicion = "P."+columnaBusqueda+" = "+criterioBusqueda+" AND P.ID_PERSONA = C.PERSONA_ID_PERSONA";
        }
        indices = sismain.getControladorBD().buscar(tablas, columnas, condicion, modeloTabla);
        return indices;
    }

}

