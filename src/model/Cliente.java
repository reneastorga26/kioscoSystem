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
    //
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
            char estado, DefaultTableModel modeloTabla){
        
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
        
        ArrayList<ArrayList<Object>> registros;

        String tablas;
        String columnas;
        String condicion;
        this.idCliente=idCliente;
        tablas = "PERSONA P, CLIENTE C";
        columnas ="P.ID_PERSONA, P.NOMBRE_APELLIDO, P.DNI, P.SEXO, "
                + "P.FECHA_NAC, P.OBSERVACIONES";
        condicion = "P.ID_PERSONA = C.PERSONA_ID_PERSONA AND "
                + "C.ID_CLIENTE = '"+ idCliente + "'";
        
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        super.setIdPersona(Long.parseLong(registros.get(0).get(0).toString()));
        super.setNombreApellido(registros.get(0).get(1).toString());
        super.setDni(registros.get(0).get(2).toString());
        super.setSexo(registros.get(0).get(3).toString().charAt(0));
        super.setFechaNacimiento(registros.get(0).get(4).toString());
        super.setObservaciones(registros.get(0).get(5).toString());
        registros.clear();
        
        //TELEFONO;
        
        tablas =  "TELEFONO T";
        columnas = "T.ID_TELEFONO, T.NUMERO, T.MOVIL, T.PERSONA_ID_PERSONA ";
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
        columnas ="E.ID_CORREO_ELECTRONICO, E.DIRECCION, E.PERSONA_ID_PERSONA ";
        condicion = "E.PERSONA_ID_PERSONA = '"+ super.getIdPersona()+"'";
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        for(int i = 0; i<registros.size();i++){
            CorreoElectronico correoElectronico = new CorreoElectronico();
            correoElectronico.setIdCorreoElectronico(Long.parseLong(registros.get(i).get(0).toString()));
            correoElectronico.setDireccion(registros.get(i).get(1).toString());
            correoElectronico.setIdPersona(super.getIdPersona());
            super.getCorreosElectronicos().add(correoElectronico);
        }
        
        registros.clear();
        
        
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
            String set = "P.NOMBRE_APELLIDO = '"+ getNombreApellido()+"',"
            + "P.DNI = '"+ getDni() + "',"
            + "P.SEXO = '" + getSexo() + "',"
            + "P.FECHA_NAC = TO_DATE(" + getFechaNacimiento()+ "),"
            + "P.OBSERVACIONES = '"+getObservaciones()+ "'";
            String condicion = "P.ID_PERSONA = '"+ getIdPersona()+"'";
            sismain.getControladorBD().modificar(tablas,set,condicion);
        
    }
    
    public void habilitarBD(){
        
        
        String tablas= "PERSONA P";
        String set = "P.ESTADO = 'H'";
        String condicion = "P.ID_PERSONA = '"+getIdPersona()+"'";

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
