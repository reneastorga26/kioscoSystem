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
public class Proveedor {
    
    private long idProveedor;
    private String cuit;
    private String razonSocial;
    private String observaciones;
    private ArrayList<Telefono> telefonos = new ArrayList<>();
    private ArrayList<Domicilio> domicilios = new ArrayList<>();
    private ArrayList<CorreoElectronico> correosElectronicos = new ArrayList<>();
    
    public Proveedor(){
        
    }
    
    public Proveedor(long idProveedor, String cuit, String razonSocial, 
            String observaciones){
        this.idProveedor = idProveedor;
        this.cuit = cuit;
        this.razonSocial = razonSocial;
        this.observaciones = observaciones;
    }

    public long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public ArrayList<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(ArrayList<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public ArrayList<Domicilio> getDomicilios() {
        return domicilios;
    }

    public void setDomicilios(ArrayList<Domicilio> domicilios) {
        this.domicilios = domicilios;
    }

    public ArrayList<CorreoElectronico> getCorreosElectronicos() {
        return correosElectronicos;
    }

    public void setCorreosElectronicos(ArrayList<CorreoElectronico> correosElectronicos) {
        this.correosElectronicos = correosElectronicos;
    }
    
    
    
    public long guardarBD(){
        long idProveedor=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(getRazonSocial());
        valores.add(getCuit());
        valores.add(getObservaciones());
        idProveedor = sismain.getControladorBD().aniadir(valores, "PROVEEDOR",false);
        valores.clear();
        valores.add(String.valueOf(idProveedor));
        sismain.getControladorBD().aniadir(valores,"PROVEEDOR",false);
        return idProveedor;
    }
    
    public ArrayList buscarBD(String criterioBusqueda, String columnaBusqueda,
            char estado, DefaultTableModel modeloTabla){
        
        ArrayList<Long> indices = new ArrayList<>();

        String tablas = "PROVEEDOR P";
        String columnas = "P.ID_PROVEEDOR, P.CUIT, P.RAZON_SOCIAL";
        String condicion = "P."+columnaBusqueda+ " > " + criterioBusqueda; 
        
        if(estado=='H'){
            condicion = condicion + " AND P.ESTADO = 'H'";
        }
        if(estado=='D'){
            condicion = condicion + " AND P.ESTADO = 'D'";
        }
        
        sismain.getControladorBD().buscar(columnas, 
                tablas, condicion, modeloTabla);
        
        return indices;
    }
    
    public void ampliarInfoBD(long idProveedor){
        
        ArrayList<ArrayList<Object>> registros;

        String tablas;
        String columnas;
        String condicion;
        this.idProveedor = idProveedor;
        tablas = "PROVEEDOR P";
        columnas ="P.ID_PROVEEDOR , P.RAZON_SOCIAL , P.CUIT, P.OBSERVACIONES";
        condicion = "P.ID_PROVEEDOR = '" + getIdProveedor() + "'";
        
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        for(int i = 0; i<registros.size();i++){
            setIdProveedor(Long.parseLong(registros.get(i).get(0).toString()));
            setRazonSocial(registros.get(i).get(1).toString());
            setCuit(registros.get(i).get(2).toString());
            setObservaciones(registros.get(i).get(3).toString());
        }
        
        registros.clear();
        
        
        //TELEFONO;
        
        tablas =  "TELEFONO T";
        columnas = "T.ID_TELEFONO, T.NUMERO, T.MOVIL, T.PROVEEDOR_ID_PROVEEDOR";
        condicion = "T.PROVEEDOR_ID_PROVEEDOR = '"+ getIdProveedor()+"'";
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        for(int i = 0; i<registros.size();i++){
            Telefono telefono = new Telefono();
            telefono.setIdTelefono(Long.parseLong(registros.get(i).get(0).toString()));
            telefono.setNumero(registros.get(i).get(1).toString());
            telefono.setMovil(registros.get(i).get(2).toString().charAt(0));
            telefono.setIdProveedor(getIdProveedor());
            getTelefonos().add(telefono);
        }
        registros.clear();
        
        //DOMICILIO;
        
        tablas = "DOMICILIO D";
        columnas = "D.ID_DOMICILIO, D.DIRECCION, "
                + "D.LOCALIDAD, D.PROVINCIA, "
                + "D.PROVEEDOR_ID_PROVEEDOR ";
        condicion = "D.PROVEEDOR_ID_PROVEEDOR = '"+ getIdProveedor()+"'";
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        for(int i = 0; i<registros.size();i++){
            Domicilio domicilio = new Domicilio();
            domicilio.setIdDomicilio(Long.parseLong(registros.get(i).get(0).toString()));
            domicilio.setDireccion(registros.get(i).get(1).toString());
            domicilio.setLocalidad(registros.get(i).get(2).toString());
            domicilio.setProvincia(registros.get(i).get(3).toString());
            domicilio.setIdProveedor(getIdProveedor());
            getDomicilios().add(domicilio);
        }
        
        registros.clear();
        
        //CORREO ELECTRONICO
        
        tablas= "CORREOELECTRONICO E";
        columnas = "E.ID_CORREO_ELECTRONICO, E.DIRECCION, E.PROVEEDOR_ID_PROVEEDOR ";
        condicion = "E.PROVEEDOR_ID_PROVEEDOR = '"+ getIdProveedor()+"'";
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        for(int i = 0; i<registros.size();i++){
            CorreoElectronico correoElectronico = new CorreoElectronico();
            correoElectronico.setIdCorreoElectronico(Long.parseLong(registros.get(i).get(0).toString()));
            correoElectronico.setDireccion(registros.get(i).get(1).toString());
            correoElectronico.setIdProveedor(Long.valueOf(registros.get(i).get(2).toString()));
            getCorreosElectronicos().add(correoElectronico);
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
        
                     
            String tablas = "PROVEEDOR P";
            String set = "P.RAZON_SOCIAL = '"+ getRazonSocial()+"', "
            + "P.CUIT = '" + getCuit() + "', "
            + "P.OBSERVACIONES = '" + getObservaciones() + "'";
            String condicion = "P.ID_PROVEEDOR = '"+ getIdProveedor()+"'";
            sismain.getControladorBD().modificar(tablas,set,condicion);
        
    }
    
    public void habilitarBD(){
        
        
        String tablas= "PROVEEDOR P";
        String set = "P.ESTADO = 'H'";
        String condicion = "P.ID_PROVEEDOR = '"+getIdProveedor()+"'";

        sismain.getControladorBD().modificar(tablas,set,condicion);
        
    }
    
    public void deshabilitarBD(){
        
        
        String tablas= "PROVEEDOR P";
        String set = "P.ESTADO = 'D'";
        String condicion = "P.ID_PROVEEDOR = '"+getIdProveedor()+"'";
        
        sismain.getControladorBD().modificar(tablas, set, condicion);

    }
}
