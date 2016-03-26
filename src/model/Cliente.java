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
        idPersona = sismain.getControladorBD().aniadir(valores, "PERSONA",false);
        valores.clear();
        valores.add(String.valueOf(idPersona));
        sismain.getControladorBD().aniadir(valores,"CLIENTE",false);
        return idPersona;
    }
    
       
    public void buscarBD(DefaultTableModel modeloTabla){
        sismain.getControladorBD().buscar("C.ID_CLIENTE, P.DNI, P.NOMBRE_APELLIDO", 
                                "CLIENTE C, PERSONA P", 
                                "C.PERSONA_ID_PERSONA = P.ID_PERSONA AND P.ESTADO = '1'", 
                                modeloTabla);
                
    }
    
    public void ampliarInfoBD(long idCliente){
        
        ArrayList<Object> camposCliente = new ArrayList<>();

        String tablas;
        String columnas;
        String condicion;
        this.idCliente=idCliente;
        tablas = "PERSONA P, CLIENTE C";
        columnas ="P.ID_PERSONA , P.NOMBRE_APELLIDO , P.DNI, P.SEXO"
                + "P.FECHA_NAC, P.OSERVACIONES";
        condicion = "P.ID_PERSONA = C.PERSONA_ID_PERSONA AND "
                + "C.ID_CLIENTE = "+ idCliente;
        
        
        camposCliente = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        super.setIdPersona(Long.parseLong(camposCliente.get(1).toString()));
        super.setNombreApellido(camposCliente.get(2).toString());
        super.setDni(camposCliente.get(3).toString());
        super.setSexo(camposCliente.get(4).toString().charAt(5));
        super.setFechaNacimiento(camposCliente.get(6).toString());
        super.setObservaciones(camposCliente.get(7).toString());
        
        
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

    public void modificarBD(ArrayList<String> cadena, String cadenaId){
        String set;
             
                 set = "DNI = '" + cadena.get(0) + "', NOMBRE_APELLIDO = '" + cadena.get(1) + 
                          "', FECHA_NAC = TO_DATE(" + cadena.get(2) + "), SEXO = '" + cadena.get(3) + 
                        "', OBSERVACIONES = '" + cadena.get(4) + "'";
                 sismain.getControladorBD().modificar(set, "PERSONA", "ID_PERSONA", cadenaId);
             
        
    }
    
    public void eliminarFisicaBD(String cadenaId){
            sismain.getControladorBD().eliminar("CLIENTE", "PERSONA_ID_PERSONA", cadenaId);
            sismain.getControladorBD().eliminar("PERSONA", "ID_PERSONA", cadenaId);
    }
    
    public void eliminarLogicaBD(String cadenaId){
            String set = "ESTADO = '0'";
             
            sismain.getControladorBD().modificar(set, "PERSONA", "ID_PERSONA", cadenaId);
    }
}

