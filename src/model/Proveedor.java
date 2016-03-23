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
    
    private int idProveedor;
    private String cuit;
    private String razonSocial;
    private String observaciones;
    
    public Proveedor(){
        
    }
    
    public Proveedor(int idProveedor, String cuit, String razonSocial, 
            String observaciones){
        this.idProveedor = idProveedor;
        this.cuit = cuit;
        this.razonSocial = razonSocial;
        this.observaciones = observaciones;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
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
    
    public long guardarBD(){
        long idProveedor=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(getRazonSocial());
        valores.add(getCuit());
        valores.add(getObservaciones());
        idProveedor = sismain.getControladorBD().aniadirBD(valores, "PROVEEDOR",false);
        valores.clear();
        valores.add(String.valueOf(idProveedor));
        sismain.getControladorBD().aniadirBD(valores,"PROVEEDOR",false);
        return idProveedor;
    }
    
    public ArrayList buscarBD(String columnaBusqueda, 
                         DefaultTableModel modeloTabla,
                         boolean preBuscar){
        ArrayList<String> indices = new ArrayList<>();

        String criterioBusqueda;
        String criterioPreBusqueda;
        if(columnaBusqueda.equals("CUIT")){
            criterioBusqueda=getCuit();
            criterioPreBusqueda="'"+getCuit()+"%'";
        }
        else{
            criterioBusqueda="'"+getRazonSocial()+"'";
            criterioPreBusqueda="'"+getRazonSocial()+"%'";
        }
        String tablas1 = "PROVEEDOR";
        String tablas2 = "PROVEEDOR P, DOMICILIO D, CORREOELECTRONICO CE, TELEFONO T";
        String columnas1 = "ID_PROVEEDOR, CUIT , RAZON_SOCIAL";
        String columnas2 = "P.ID_PROVEEDOR , P.CUIT , P.RAZON_SOCIAL, P.OBSERVACIONES, "
                + "P.ESTADO, D.PROVEEDOR_ID_PROVEEDOR, "
                + "D.DIRECCION, D.LOCALIDAD, D.PROVINCIA, CE.PROVEEDOR_ID_PREOVEEDOR, "
                + "CE.DIRECCION, T.PROVEEDOR_ID_PROVEEDOR, T.NUMERO, T.MOVIL";
        String condicion;
        if(preBuscar){
            condicion = "("+columnaBusqueda+" = "+criterioPreBusqueda+" OR "+columnaBusqueda+" = "+ criterioBusqueda+" )";
            indices = sismain.getControladorBD().buscar(tablas1, columnas1, condicion, modeloTabla);
            return indices;
        }
        else{
            condicion = ""+columnaBusqueda+" = "+criterioBusqueda+"";
            indices = sismain.getControladorBD().buscar(tablas2, columnas2, condicion, modeloTabla);
            return indices;
        }
        
    }
    
    public void modificarBD(ArrayList<String> cadena, String cadenaId){
             
            String set = "CUIT = '" + cadena.get(0) + "', RAZON_SOCIAL = '" + cadena.get(1) + 
                          "', OBSERVACIONES = '" + cadena.get(2) + "'";
             
            sismain.getControladorBD().modificar(set, "PROVEEDOR", "ID_PROVEEDOR", cadenaId); 
    }
}
