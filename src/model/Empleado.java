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
    private ArrayList<Familiar> familiares = new ArrayList<>();
    private ArrayList<ObraSocial> obrasSociales = new ArrayList<>();
    
    
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

    public ArrayList<Familiar> getFamiliares() {
        return familiares;
    }

    public void setFamiliares(ArrayList<Familiar> familiares) {
        this.familiares = familiares;
    }

    public ArrayList<ObraSocial> getObrasSociales() {
        return obrasSociales;
    }

    public void setObrasSociales(ArrayList<ObraSocial> obrasSociales) {
        this.obrasSociales = obrasSociales;
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
    
    public ArrayList buscarBD(String criterioBusqueda, String columnaBusqueda,
            char estado, DefaultTableModel modeloTabla){
        
        ArrayList<Long> indices = new ArrayList<>();

        String tablas = "PERSONA P , EMPLEADO E";
        String columnas = "E.ID_EMPLEADO, P.DNI, P.NOMBRE_APELLIDO";
        String condicion = "P."+columnaBusqueda+ " = " + criterioBusqueda ;
        
        switch(estado){
            case 1:
                condicion = condicion + " AND ESTADO = 'H'";
                break;
            case 2:
                condicion = condicion + " AND ESTADO = 'D'";
                break;
            default:
        }
        
        indices = sismain.getControladorBD().buscar(columnas, 
                tablas, condicion, modeloTabla);
        
        return indices;
    }
    
    public void ampliarInfoBD(long idEmpleado){
        
        ArrayList<Object> camposEmpleado = new ArrayList<>();

        String tablas;
        String columnas;
        String condicion;
        this.idEmpleado = idEmpleado;
        tablas = "PERSONA P, EMPLEADO E";
        columnas ="P.ID_PERSONA , P.NOMBRE_APELLIDO , P.DNI, P.SEXO"
                + "P.FECHA_NAC, P.OSERVACIONES, E.CUIL, E.FECHA_INICIO_RELACION_LABORAL";
        condicion = "P.ID_PERSONA = C.PERSONA_ID_PERSONA AND "
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
        
        tablas =  "TELEFONO T";
        columnas = "T.NUMERO, T.MOVIL ";
        condicion = "T.PERSONA_ID_PERSONA = '"+ super.getIdPersona()+"'";
        
        super.setTelefonos(sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion));
        
        //DOMICILIO;
        
        tablas = "DOMICILIO D";
        columnas = "D.DIRECCION, "
                + "D.LOCALIDAD, D.PROVINCIA ";
        condicion = "D.PERSONA_ID_PERSONA = "+super.getIdPersona()+"'";
        
        super.setDomicilios(sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion));
        
        
        //CORREO ELECTRONICO
        
        tablas= "CORREOELECTRONICO E";
        columnas ="E.DIRECCION ";
        condicion = "E.PERSONA_ID_PERSONA = '"+ super.getIdPersona()+"'";
        
        super.setCorreosElectronicos(sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion));
        
        //FAMILIARES
        
        tablas= "PERSONA P, FAMILIAR F";
        columnas ="P.DNI, P.NOMBRE_APELLIDO, P.FECHA_NAC, P.SEXO, P. OBSERVACIONES, "
                + "F.PARENTESCO ";
        condicion = "P.ID_PERSONA = '"+ super.getIdPersona()+"' AND "
                + "F.EMPLEADO_ID_EMPLEADO = '" + getIdEmpleado() + "'";
        
        setFamiliares(sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion));
        /*
        ArrayList InfoAmpliada:
            Primer Elemento, clase Cliente,
            Segundo Elemento, ArrayList de Telefonos
            Tercer Elemento, ArrayList de Domicilios
            Cuarto Elemento, ArrayList de Correos Electronicos
        */
        
        //OBRAS SOCIALES
        
        tablas= "OBRA_SOCIAL O";
        columnas ="O.DESCRIPCION, O.BANCO, O.CUENTA_BANCARIA";
        condicion = "O.ID_OBRA_SOCIAL = '"+ getIdObraSocial()+"'";
        
        setObrasSociales(sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion));
    }
    
    
    public void modificarBD(){
            
            String tablas = "PERSONA P, EMPLEADO E";
            String set = "P.NOMBRE_APELLIDO = "+ super.getNombreApellido()+","
            + "P.DNI = " + super.getDni() + ","
            + "P.SEXO = " + super.getSexo() + ","
            + "P.FECHA_NAC = " +super.getFechaNacimiento()+ ","
            + "P.OBSERVACIONES = "+super.getObservaciones()+ "',"
            + "E.CUIL = '" + getCuil() + "',"
            + "E.FECHA_INICIO_RELACION_LABORAL = '" + getFechaInicioRelacionLaboral() + "'";        
            String condicion = "P.ID_PERSONA = '"+ super.getIdPersona()+"'";
            sismain.getControladorBD().modificar(tablas,set,condicion);
        
    }
    
    public void habilitarBD(){
        
        
        String tablas= "PERSONA P";
        String set = "P.ESTADO = 'H'";
        String condicion = "P.ID_PERSONA = '"+super.getIdPersona()+"'";

        sismain.getControladorBD().modificar(tablas,set,condicion);
        
    }
    
    public void deshabilitarBD(){
        
        
        String tablas= "PERSONA P";
        String set = "P.ESTADO = 'D'";
        String condicion = "P.ID_PERSONA = '"+super.getIdPersona()+"'";
        
        sismain.getControladorBD().modificar(tablas, set, condicion);

    }
}
