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

        String tablas = "PROVEEDOR";
        String columnas = "P.ID_PROVEEDOR, P.CUIT, P.RAZON_SOCIAL";
        String condicion = ""+columnaBusqueda+ " = " + criterioBusqueda; 
        
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
    
    public void ampliarInfoBD(long idProveedor){
        
        ArrayList<Object> camposProveedor = new ArrayList<>();

        String tablas;
        String columnas;
        String condicion;
        this.idProveedor = idProveedor;
        tablas = "PROVEEDOR P";
        columnas ="P.ID_PROVEEDOR , P.RAZON_SOCIAL , P.CUIT, P.OBSERVACIONES";
        condicion = "P.ID_PROVEEDOR = '" + idProveedor + "'";
        
        
        camposProveedor = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        setIdProveedor(Long.parseLong(camposProveedor.get(1).toString()));
        setRazonSocial(camposProveedor.get(2).toString());
        setCuit(camposProveedor.get(3).toString());
        setObservaciones(camposProveedor.get(4).toString());
        
        
        //TELEFONO;
        
        tablas =  "TELEFONO T";
        columnas = "T.NUMERO, T.MOVIL ";
        condicion = "T.PROVEEDOR_ID_PROVEEDOR = '"+ getIdProveedor()+"'";
        
        setTelefonos(sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion));
        
        //DOMICILIO;
        
        tablas = "DOMICILIO D";
        columnas = "D.DIRECCION, "
                + "D.LOCALIDAD, D.PROVINCIA ";
        condicion = "D.PROVEEDOR_ID_PROVEEDOR ="+ getIdProveedor()+"'";
        
        setDomicilios(sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion));
        
        
        //CORREO ELECTRONICO
        
        tablas= "CORREOELECTRONICO E";
        columnas ="E.DIRECCION ";
        condicion = "E.PROVEEDOR_ID_PROVEEDOR = '"+ getIdProveedor()+"'";
        
        setCorreosElectronicos(sismain.getControladorBD().extenderInfo
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
