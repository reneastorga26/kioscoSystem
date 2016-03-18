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
                         DefaultTableModel modeloTabla,
                         boolean preBuscar){
        ArrayList<String> indices = new ArrayList<>();

        String criterioBusqueda;
        String criterioPreBusqueda;
        if(columnaBusqueda.equals("ID_PRODUCTO")){
            criterioBusqueda=String.valueOf(getIdProducto());
            criterioPreBusqueda="'"+String.valueOf(getIdProducto())+"%'";
        }
        else{
            criterioBusqueda="'"+getDescripcion()+"'";
            criterioPreBusqueda="'"+getDescripcion()+"%'";
        }
        String tablas = "PRODUCTO";
        String columnas = "ID_PRODUCTO , DESCRIPCION , STOCK_ACTUAL , STOCK_CRITICO_MINIMO, PUNTO_PEDIDO";
        String condicion;
        if(preBuscar){
            condicion = "("+columnaBusqueda+" LIKE "+criterioPreBusqueda+" OR "+columnaBusqueda+" = "+ criterioBusqueda+" )";
        }
        else{
            condicion = ""+columnaBusqueda+" = "+criterioBusqueda+"";
        }
        indices = sismain.getControladorBD().buscar(tablas, columnas, condicion, modeloTabla);
        return indices;
    }

    public void modificarBD(ArrayList<String> txt, String tabla, String columna, String id){
             
             String set = "DESCRIPCION = '" + txt.get(0) + "', STOCK_ACTUAL = " + txt.get(1) + 
                          ", STOCK_CRITICO_MINIMO = " + txt.get(2) + ", PUNTO_PEDIDO = " + txt.get(3);
             try{

                 String query = "UPDATE " + tabla + " SET " + set + " WHERE " + columna + " = " + id;
                 System.out.println(query);
                 sismain.getConexion().getStatement().execute(query);
             }catch (SQLException ex) {
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
}
