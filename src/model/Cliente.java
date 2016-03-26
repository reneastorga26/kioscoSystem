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
    
    public ArrayList buscarBD(String criterioBusqueda, String columnaBusqueda,
            DefaultTableModel modeloTabla, char estado){
        
        ArrayList<Long> indices = new ArrayList<>();


        String tablas = "PERSONA P , CLIENTE C";
        String columnas = "C.ID_CLIENTE, P.DNI, P.NOMBRE_APELLIDO";
        String condicion = "P."+columnaBusqueda+ " = " + criterioBusqueda;
        
        if(estado=='H'){
            condicion = condicion + " AND P.ESTADO = 'H'";
        }
        if(estado=='D'){
            condicion = condicion + " AND P.ESTADO = 'D'";
        }
 
        indices = sismain.getControladorBD().buscar(columnas, 
                tablas, condicion, modeloTabla);
        
        return indices;
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
        
        tablas =  "TELEFONO T";
        columnas = "T.ID_TELEFONO, T.NUMERO, T.MOVIL, T.PERSONA_ID_PERSONA ";
        condicion = "T.PERSONA_ID_PERSONA = '"+ super.getIdPersona()+"'";
        
        super.setTelefonos(sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion));
        
        //DOMICILIO;
        
        tablas = "DOMICILIO D";
        columnas = "D.ID_DOMICLIO, D.DIRECCION, "
                + "D.LOCALIDAD, D.PROVINCIA, "
                + "D.PERSONA_ID_PERSONA ";
        condicion = "D.PERSONA_ID_PERSONA ='"+super.getIdPersona()+"'";
        
        super.setDomicilios(sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion));
        
        
        //CORREO ELECTRONICO
        
        tablas= "CORREOELECTRONICO E";
        columnas ="E.ID_CORREO_ELECTRONICO, E.DIRECCION, E.PERSONA_ID_PERSONA ";
        condicion = "E.PERSONA_ID_PERSONA = '"+ super.getIdPersona()+"'";
        
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

    public void modificarBD(){
        
                     
            String tablas = "PERSONA P";
            String set = "P.NOMBRE_APELLIDO = '"+ super.getNombreApellido()+"',"
            + "P.DNI = '"+ super.getDni() + "',"
            + "P.SEXO = '" + super.getSexo() + "',"
            + "P.FECHA_NAC = '" +super.getFechaNacimiento()+ "',"
            + "P.OBSERVACIONES = '"+super.getObservaciones()+ "',";
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

        /*
        tabla = "TELEFONO T";
        condicion = "T.PERSONA_ID_PERSONA= "+ idPersona;
        sismain.getControladorBD().eliminar(tabla, condicion);
        
        tabla = "DOMICILIO D";
        condicion = "D.PERSONA_ID_PERSONA= " + idPersona;
        sismain.getControladorBD().eliminar(tabla, condicion);
        
        tabla = "CORREOELECTRONICO E";
        condicion ="E.PERSONA_ID_PERSONA= "+ idPersona;
        
        tabla = "CLIENte"
        
*/
