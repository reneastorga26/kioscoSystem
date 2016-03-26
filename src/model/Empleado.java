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
    
    public void buscarBD(DefaultTableModel modeloTabla){
        sismain.getControladorBD().buscar("E.ID_EMPLEADO, P.DNI, P.NOMBRE_APELLIDO", 
                                "EMPLEADO E, PERSONA P", 
                                "E.PERSONA_ID_PERSONA = P.ID_PERSONA AND P.ESTADO = '1'", 
                                modeloTabla);
                
    }
    
    public void ampliarInfoBD(long idEmpleado){
        
        ArrayList<Object> camposEmpleado = new ArrayList<>();

        String tablas;
        String columnas;
        String condicion;
        this.idEmpleado = idEmpleado;
        tablas = "PERSONA P, EMPLEADO E";
        columnas ="P.ID_PERSONA , P.NOMBRE_APELLIDO , P.DNI, P.SEXO"
                + "P.FECHA_NAC, P.OSERVACIONES, E.CUIL, "
                + "E.FECHA_INICIO_RELACION_LABORAL";
        condicion = "P.ID_PERSONA = E.PERSONA_ID_PERSONA AND "
                + "E.ID_EMPLEADO = "+ idEmpleado;
        
        
        camposEmpleado = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        super.setIdPersona(Long.parseLong(camposEmpleado.get(1).toString()));
        super.setNombreApellido(camposEmpleado.get(2).toString());
        super.setDni(camposEmpleado.get(3).toString());
        super.setSexo(camposEmpleado.get(4).toString().charAt(5));
        super.setFechaNacimiento(camposEmpleado.get(6).toString());
        super.setObservaciones(camposEmpleado.get(7).toString());
        setCuil(camposEmpleado.get(8).toString());
        setFechaInicioRelacionLaboral(camposEmpleado.get(9).toString());
        
        
        //TELEFONO;
        
        tablas =  "TELEFONO ";
        columnas = "NUMERO, MOVIL ";
        condicion = "PERSONA_ID_PERSONA = "+ super.getIdPersona();
        
        super.setTelefonos(sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion));
        
        //DOMICILIO;
        
        tablas = "DOMICILIO ";
        columnas = "DIRECCION, "
                + "LOCALIDAD, PROVINCIA ";
        condicion = "PERSONA_ID_PERSONA = "+super.getIdPersona();
        
        super.setDomicilios(sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion));
        
        
        //CORREO ELECTRONICO
        
        tablas= "CORREO ELECTRONICO ";
        columnas ="DIRECCION ";
        condicion = "PERSONA_ID_PERSONA = "+ super.getIdPersona();
        
        super.setCorreosElectronicos(sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion));
        
        
        /*
        ArrayList InfoAmpliada:
            Primer Elemento, clase Cliente,
            Segundo Elemento, ArrayList de Telefonos
            Tercer Elemento, ArrayList de Domicilios
            Cuarto Elemento, ArrayList de Correos Electronicos
        */
        
     
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
