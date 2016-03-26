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
    private ArrayList<TipoProducto> tiposProductos = new ArrayList<>();
    private ArrayList<Fabricante> fabricantes = new ArrayList<>();
    private ArrayList<Precio> precios = new ArrayList<>();
    
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

    public ArrayList<TipoProducto> getTiposProductos() {
        return tiposProductos;
    }

    public void setTiposProductos(ArrayList<TipoProducto> tiposProductos) {
        this.tiposProductos = tiposProductos;
    }

    public ArrayList<Fabricante> getFabricantes() {
        return fabricantes;
    }

    public void setFabricantes(ArrayList<Fabricante> fabricantes) {
        this.fabricantes = fabricantes;
    }

    public ArrayList<Precio> getPrecios() {
        return precios;
    }

    public void setPrecios(ArrayList<Precio> precios) {
        this.precios = precios;
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
        idProducto = sismain.getControladorBD().aniadir(valores, "PRODUCTO",false);
        valores.clear();
        valores.add(String.valueOf(idProducto));
        sismain.getControladorBD().aniadir(valores,"PRODUCTO",false);
        return idProducto;
    }
    
    public void buscarBD(DefaultTableModel modeloTabla){
        sismain.getControladorBD().buscar("ID_PRODUCTO, DESCRIPCION", 
                                "PRODUCTO", 
                                "ESTADO = '1'", 
                                modeloTabla);
                
    }
    
    public void ampliarInfoBD(long idProducto){
        
        ArrayList<Object> camposProducto = new ArrayList<>();

        String tablas;
        String columnas;
        String condicion;
        this.idProducto = idProducto;
        tablas = "PRODUCTO";
        columnas = "ID_PRODUCTO , DESCRIPCION , STOCK_ACTUAL , "
                + "STOCK_CRITICO_MINIMO , PUNTO_PEDIDO , "
                + "TIPO_PRODUCTO_ID_TIPO_PRODUCTO , FABRICANTE_ID_FABRICANTE";
        condicion = "ID_PRODUCTO = "+ idProducto;
        
        
        camposProducto = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        setIdProducto(Long.parseLong(camposProducto.get(1).toString()));
        setDescripcion(camposProducto.get(2).toString());
        setStockActual(Integer.valueOf(camposProducto.get(3).toString()));
        setStockCriticoMinimo(Integer.valueOf(camposProducto.get(4).toString()));
        setPuntoPedido(Integer.valueOf(camposProducto.get(5).toString()));
        setIdTipoProducto(Long.valueOf(camposProducto.get(6).toString()));
        setIdFabricante(Long.valueOf(camposProducto.get(7).toString()));
        
        
        //TIPO_PRODUCTO;
        
        tablas =  "TIPO_PRODUCTO ";
        columnas = "DESCRIPCION ";
        condicion = "ID_TIPO_PRODUCTO = "+ getIdTipoProducto();
        
        setTiposProductos(sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion));
        
        //FABRICANTE;
        
        tablas = "FABRICANTE ";
        columnas = "DESCRIPCION ";
        condicion = "ID_FABRICANTE = "+getIdFabricante();
        
        setFabricantes(sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion));
        
        //PRECIO;
        
        tablas = "PRECIO ";
        columnas = "NUMERO ";
        condicion = "PRODUCTO_ID_PRODUCTO = "+getIdProducto();
        
        setPrecios(sismain.getControladorBD().extenderInfo
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
             
             String set = "DESCRIPCION = '" + cadena.get(0) + "', STOCK_ACTUAL = " + cadena.get(1) + 
                          ", STOCK_CRITICO_MINIMO = " + cadena.get(2) + ", PUNTO_PEDIDO = " + cadena.get(3);
             
             sismain.getControladorBD().modificar(set, "PRODUCTO", "ID_PRODUCTO", cadenaId);
    }
    
    public void eliminarFisicaBD(String cadenaId){
            sismain.getControladorBD().eliminar("PRODUCTO", "ID_PRODUCTO", cadenaId);
    }
    
    public void eliminarLogicaBD(String cadenaId){
            String set = "ESTADO = '0'";
             
            sismain.getControladorBD().modificar(set, "PRODUCTO", "ID_PRODUCTO", cadenaId);
    }
    
}
