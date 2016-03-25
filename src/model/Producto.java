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
public class Producto {

    private long idProducto;
    private String descripcion;
    private int stockActual;
    private int stockCriticoMinimo;
    private int puntoPedido;
    private char tipoCompraProveedor;
    private int unidadesPackMayorista;
    private long idTipoProducto;
    private long idFabricante;
    
    public Producto(){
        
    }
    
    public Producto(int idProducto, String descripcion, int stockActual,
            int stockCriticoMinimo, int puntoPedido, char tipoCompraProveedor, 
            int unidadesPackMayorista, long idTipoProducto, long idFabricante){
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.stockActual = stockActual;
        this.stockCriticoMinimo = stockCriticoMinimo;
        this.puntoPedido = puntoPedido;
        this.tipoCompraProveedor = tipoCompraProveedor;
        this.unidadesPackMayorista = unidadesPackMayorista;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public int getStockCriticoMinimo() {
        return stockCriticoMinimo;
    }

    public void setStockCriticoMinimo(int stockCriticoMinimo) {
        this.stockCriticoMinimo = stockCriticoMinimo;
    }

    public int getPuntoPedido() {
        return puntoPedido;
    }

    public void setPuntoPedido(int puntoPedido) {
        this.puntoPedido = puntoPedido;
    }

    public char getTipoCompraProveedor() {
        return tipoCompraProveedor;
    }

    public void setTipoCompraProveedor(char tipoCompraProveedor) {
        this.tipoCompraProveedor = tipoCompraProveedor;
    }

    public int getUnidadesPackMayorista() {
        return unidadesPackMayorista;
    }

    public void setUnidadesPackMayorista(int unidadesPackMayorista) {
        this.unidadesPackMayorista = unidadesPackMayorista;
    }

    public long getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(long idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public long getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(long idFabricante) {
        this.idFabricante = idFabricante;
    }

    

    public long guardarBD(){
        long idProducto=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(String.valueOf(getIdProducto()));
        valores.add(getDescripcion());
        valores.add(String.valueOf(getStockActual()));
        valores.add(String.valueOf(getStockCriticoMinimo()));
        valores.add(String.valueOf(getPuntoPedido()));
        valores.add(String.valueOf(getIdProducto()));
        valores.add(String.valueOf(getIdFabricante()));
        idProducto = sismain.getControladorBD().aniadirBD(valores, "PRODUCTO",false);
        valores.clear();
        valores.add(String.valueOf(idProducto));
        sismain.getControladorBD().aniadirBD(valores,"PRODUCTO",false);
        return idProducto;
    }
    
    public ArrayList buscarBD(String columnaBusqueda, 
                              DefaultTableModel modeloTabla){
        
        ArrayList<String> indices = new ArrayList<>();

        String criterioBusqueda;
        String tablas;      
        String columnas;
        String condicion;
        
        if(columnaBusqueda.equals("ID_PRODUCTO") || columnaBusqueda.equals("DESCRIPCION")){
            criterioBusqueda="'"+getIdProducto()+"'";
            tablas  = "PRODUCTO";
            columnas = "ID_PRODUCTO , DESCRIPCION , STOCK_ACTUAL , STOCK_CRITICO_MINIMO, PUNTO_PEDIDO";
            condicion = columnaBusqueda+" = "+ criterioBusqueda;
            indices = sismain.getControladorBD().buscar(tablas, columnas, condicion, modeloTabla);
            return indices;
        }
        else{
            criterioBusqueda="'"+getIdProducto()+"'";
            tablas = "PRODUCTO PRO, TIPO_PRODUCTO T, FABRICANTE F, PRECIO PRE";
            columnas = "PRO.ID_PRODUCTO , PRO.DESCRIPCION , PRO.STOCK_ACTUAL , "
                + "PRO.STOCK_CRITICO_MINIMO, PRO.PUNTO_PEDIDO, PRO.ESTADO, "
                + "T.ID_TIPO_PRODUCTO, T.DESCRIPCION, F.ID_FABRICANTE, F.DESCRIPCION,"
                + "PRE.NUMERO, PRE.PRODUCTO_ID_PRODUCTO";
            condicion = "P."+columnaBusqueda+" = "+criterioBusqueda+
                    " AND P.ID_PERSONA = C.PERSONA_ID_PERSONA AND P.ID_PERSONA = D.PERSONA_ID_PERSONA"
                    + "AND P.ID_PERSONA = CE.PERSONA_ID_PERSONA AND P.ID_PERSONA = T.PERSONA_ID_PERSONA";
            indices = sismain.getControladorBD().buscar(tablas, columnas, condicion, null);
            return indices;
        }
        
        
    }
    

    public void modificarBD(ArrayList<String> cadena, String cadenaId){
             
             String set = "DESCRIPCION = '" + cadena.get(0) + "', STOCK_ACTUAL = " + cadena.get(1) + 
                          ", STOCK_CRITICO_MINIMO = " + cadena.get(2) + ", PUNTO_PEDIDO = " + cadena.get(3);
             
             sismain.getControladorBD().modificarBD(set, "PRODUCTO", "ID_PRODUCTO", cadenaId);
    }
    
    public void eliminarFisicaBD(String cadenaId){
            sismain.getControladorBD().eliminarBD("PRODUCTO", "ID_PRODUCTO", cadenaId);
    }
    
    public void eliminarLogicaBD(String cadenaId){
            String set = "ESTADO = '0'";
             
            sismain.getControladorBD().modificarBD(set, "PRODUCTO", "ID_PRODUCTO", cadenaId);
    }
    
}
