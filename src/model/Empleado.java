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
        
        sismain.getControladorBD().buscar(columnas, 
                tablas, condicion, modeloTabla);
        
        return indices;
    }
    
    public void ampliarInfoBD(long idEmpleado){
        
        ArrayList<ArrayList<Object>> registros;

        String tablas;
        String columnas;
        String condicion;
        this.idEmpleado = idEmpleado;
        tablas = "PERSONA P, EMPLEADO E";
        columnas ="P.ID_PERSONA , P.NOMBRE_APELLIDO , P.DNI, P.SEXO, "
                + "P.FECHA_NAC, P.OBSERVACIONES, E.CUIL, E.FECHA_INICIO_RELACION_LABORAL";
        condicion = "P.ID_PERSONA = E.PERSONA_ID_PERSONA AND "
                + "E.ID_EMPLEADO = '"+ getIdEmpleado() + "'";
        
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        super.setIdPersona(Long.parseLong(registros.get(0).get(0).toString()));
        super.setNombreApellido(registros.get(0).get(1).toString());
        super.setDni(registros.get(0).get(2).toString());
        super.setSexo(registros.get(0).get(3).toString().charAt(0));
        super.setFechaNacimiento(registros.get(0).get(4).toString());
        super.setObservaciones(registros.get(0).get(5).toString());
        setCuil(registros.get(0).get(6).toString());
        setFechaInicioRelacionLaboral(registros.get(0).get(7).toString());
        
        
        //TELEFONO;
        
        tablas =  "TELEFONO T";
        columnas = "T.ID_TELEFONO, T.NUMERO, T.MOVIL, T.PERSONA_ID_PERSONA";
        condicion = "T.PERSONA_ID_PERSONA = '"+ super.getIdPersona()+"'";
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        for(int i = 0; i<registros.size();i++){
            Telefono telefono = new Telefono();
            telefono.setIdTelefono(Long.parseLong(registros.get(i).get(0).toString()));
            telefono.setNumero(registros.get(i).get(1).toString());
            telefono.setMovil(registros.get(i).get(2).toString().charAt(0));
            telefono.setIdPersona(super.getIdPersona());
            super.getTelefonos().add(telefono);
        }
        
        registros.clear();
        
        //DOMICILIO;
        
        tablas = "DOMICILIO D";
        columnas = "D.ID_DOMICILIO, D.DIRECCION, "
                + "D.LOCALIDAD, D.PROVINCIA, "
                + "D.PERSONA_ID_PERSONA ";
        condicion = "D.PERSONA_ID_PERSONA = '"+super.getIdPersona()+"'";
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        for(int i = 0; i<registros.size();i++){
            Domicilio domicilio = new Domicilio();
            domicilio.setIdDomicilio(Long.parseLong(registros.get(i).get(0).toString()));
            domicilio.setDireccion(registros.get(i).get(1).toString());
            domicilio.setLocalidad(registros.get(i).get(2).toString());
            domicilio.setProvincia(registros.get(i).get(3).toString());
            domicilio.setIdPersona(super.getIdPersona());
            super.getDomicilios().add(domicilio);
        }
        
        registros.clear();
        
        
        //CORREO ELECTRONICO
        
        tablas= "CORREOELECTRONICO E";
        columnas = "E.ID_CORREO_ELECTRONICO, E.DIRECCION, E.PERSONA_ID_PERSONA ";
        condicion = "E.PERSONA_ID_PERSONA = '"+ super.getIdPersona()+"'";
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        for(int i = 0; i<registros.size();i++){
            CorreoElectronico correoElectronico = new CorreoElectronico();
            correoElectronico.setIdCorreoElectronico(Long.parseLong(registros.get(i).get(0).toString()));
            correoElectronico.setDireccion(registros.get(i).get(1).toString());
            correoElectronico.setIdPersona(Long.valueOf(registros.get(i).get(2).toString()));
            super.getCorreosElectronicos().add(correoElectronico);
        }
        
        registros.clear();
        
        //FAMILIARES
        
        tablas= "PERSONA P, FAMILIAR F";
        columnas ="P.ID_PERSONA, P.DNI, P.NOMBRE_APELLIDO, P.FECHA_NAC, F.PARENTESCO, "
                + "F.PERSONA_ID_PERSONA, F.EMPLEADO_ID_EMPLEADO";
        condicion = "P.ID_PERSONA = F.PERSONA_ID_PERSONA AND "
                + "F.EMPLEADO_ID_EMPLEADO = '" + getIdEmpleado() + "'";
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        for(int i = 0; i<registros.size();i++){
            Familiar familiar = new Familiar();
            familiar.setIdFamiliar(Long.parseLong(registros.get(i).get(0).toString()));
            familiar.setDni(registros.get(i).get(1).toString());
            familiar.setNombreApellido(registros.get(i).get(2).toString());
            familiar.setFechaNacimiento(registros.get(i).get(3).toString());
            familiar.setParentesco(registros.get(i).get(4).toString());
            getFamiliares().add(familiar);
        }
        
        registros.clear();
        
        /*
        ArrayList InfoAmpliada:
            Primer Elemento, clase Cliente,
            Segundo Elemento, ArrayList de Telefonos
            Tercer Elemento, ArrayList de Domicilios
            Cuarto Elemento, ArrayList de Correos Electronicos

        */
        //OBRAS SOCIALES
        
        tablas= "RELACION_EMPLEADO_OS R, OBRA_SOCIAL O";
        columnas ="R.EMPLEADO_ID_EMPLEADO, R.OBRA_SOCIAL_ID_OBRA_SOCIAL, "
                + "O.ID_OBRA_SOCIAL, O.DESCRIPCION, O.BANCO, O.CUENTA_BANCARIA";
        condicion = "R.EMPLEADO_ID_EMPLEADO = '"+ getIdEmpleado()+"' AND "
                + "R.OBRA_SOCIAL_ID_OBRA_SOCIAL = O.ID_OBRA_SOCIAL";
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        for(int i = 0; i<registros.size();i++){
            ObraSocial obraSocial = new ObraSocial();
            obraSocial.setIdObraSocial(Long.parseLong(registros.get(i).get(2).toString()));
            obraSocial.setDescripcion(registros.get(i).get(3).toString());
            obraSocial.setBanco(registros.get(i).get(4).toString());
            obraSocial.setCuentaBancaria(registros.get(i).get(5).toString());
            getObrasSociales().add(obraSocial);
        }
        
        registros.clear();
    }
    
    
    public void modificarBD(){
            
            String tablas = "PERSONA P";
            String set = "P.NOMBRE_APELLIDO = '"+ getNombreApellido()+"',"
            + "P.DNI = '" + getDni() + "',"
            + "P.SEXO = '" + getSexo() + "',"
            + "P.FECHA_NAC = " +getFechaNacimiento()+ ","
            + "P.OBSERVACIONES = '"+getObservaciones()+ "'";        
            String condicion = "P.ID_PERSONA = '"+ getIdPersona()+"'";
            sismain.getControladorBD().modificar(tablas,set,condicion);
            
    }
    
    public void modificarBD2(){
            String tablas = "EMPLEADO E";
            String set = "E.CUIL = '" + getCuil() + "',"
            + "E.FECHA_INICIO_RELACION_LABORAL = " + getFechaInicioRelacionLaboral() + "";        
            String condicion = "E.ID_EMPLEADO = '" + getIdEmpleado() + "'";
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
