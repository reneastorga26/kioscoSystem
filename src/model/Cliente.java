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
    
    public ArrayList ampliarInfoBD(long idCliente){
        
        ArrayList<Object> infoAmpliada = new ArrayList<>();
        ArrayList<Object> infoCliente = new ArrayList<>();
        ArrayList<Telefono> telefonos = new ArrayList<>();
        ArrayList<Domicilio> domicilios = new ArrayList<>();
        ArrayList<CorreoElectronico> correosElectronicos = new ArrayList<>();
        String criterioBusqueda; 
        String tablas;
        String columnas;
        String condicion;
        this.idCliente=idCliente;
        tablas = "PERSONA P, CLIENTE C";
        columnas = "C.ID_CLIENTE , C.PERSONA_ID_PERSONA"
                + "P.ID_PERSONA , P.NOMBRE_APELLIDO , P.DNI, P.SEXO"
                + "P.FECHA_NAC, P.OSERVACIONES";
        condicion = "P.ID_PERSONA = C.PERSONA_ID_PERSONA AND "
                + "C.ID_CLIENTE = "+ idCliente;
        
        infoCliente = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        super.setIdPersona(Long.parseLong(infoCliente.get(3).toString()));
        super.setNombreApellido(infoCliente.get(4).toString());
        super.setDni(infoCliente.get(5).toString());
        super.setSexo(infoCliente.get(6).toString().charAt(0));
        super.setFechaNacimiento(infoCliente.get(7).toString());
        super.setObservaciones(infoCliente.get(8).toString());
        
        infoAmpliada.add(this);
        
        //TELEFONO;
        
        tablas =  "TELEFONO T";
        columnas = "T.ID_TELEFONO, T.NUMERO, T.MOVIL, T.PERSONA_ID_PERSONA ";
        condicion = "WHERE T.PERSONA_ID_PERSONA = "+ super.getIdPersona();
        
        telefonos = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        infoAmpliada.add(this);
        
        //DOMICILIO;
        
        tablas = "DOMICILIO D";
        columnas = "D.ID_DOMICLIO, D.DIRECCION, D.LOCALIDAD, D.PROVINCIA, D.PERSONA_ID_PERSONA ";
        condicion = "WHERE D.PERSONA_ID_PERSONA ="+super.getIdPersona();
        
        domicilios = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        infoAmpliada.add(domicilios);
        
        //CORREO ELECTRONICO
        
        tablas= "CORREO ELECTRONICO E";
        columnas ="E.ID_CORREO_ELECTRONICO, E.DIRECCION, E.PERSONA_ID_PERSONA ";
        condicion = "WHERE E.PERSONA_ID_PERSONA = "+ super.getIdPersona();
        
        correosElectronicos = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        infoAmpliada.add(correosElectronicos);
        
        
        /*
        ArrayList InfoAmpliada:
            Primer Elemento, clase Cliente,
            Segundo Elemento, ArrayList de Telefonos
            Tercer Elemento, ArrayList de Domicilios
            Cuarto Elemento, ArrayList de Correos Electroicos
        */
        
        return infoAmpliada;
    }

    public void modificarBD(ArrayList<String> cadena, String cadenaId){
        
            String set = "DNI = '" + cadena.get(0) + "', NOMBRE_APELLIDO = '" + cadena.get(1) + 
                          "', FECHA_NAC = TO_DATE(" + cadena.get(2) + "), SEXO = '" + cadena.get(3) + 
                        "', OBSERVACIONES = '" + cadena.get(4) + "'";
             
            sismain.getControladorBD().modificarBD(set, "PERSONA", "ID_PERSONA", cadenaId);
    }
    
    public void eliminarFisicaBD(String cadenaId){
            sismain.getControladorBD().eliminarBD("CLIENTE", "PERSONA_ID_PERSONA", cadenaId);
            sismain.getControladorBD().eliminarBD("PERSONA", "ID_PERSONA", cadenaId);
    }
    
    public void eliminarLogicaBD(String cadenaId){
            String set = "ESTADO = '0'";
             
            sismain.getControladorBD().modificarBD(set, "PERSONA", "ID_PERSONA", cadenaId);
    }
}

