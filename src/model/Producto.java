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
    private char estadoStock;
    private char estado;

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public char getEstadoStock() {
        return estadoStock;
    }

    public void setEstadoStock(char estadoStock) {
        this.estadoStock = estadoStock;
    }
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
        ArrayList<String> valores= new ArrayList<>();
        valores.add(String.valueOf(idProducto));
        valores.add(descripcion);
        valores.add(String.valueOf(stockActual));
        valores.add(String.valueOf(tipoCompraProveedor));
        valores.add(String.valueOf(idFabricante));
        valores.add(String.valueOf(idTipoProducto));
        valores.add(String.valueOf(unidadesPackMayorista));
        valores.add(String.valueOf(estado));
        valores.add(String.valueOf(estadoStock));
        valores.add(String.valueOf(puntoPedido));
        valores.add(String.valueOf(stockCriticoMinimo));
        idProducto = sismain.getControladorBD().aniadir(valores, "PRODUCTO",false);
        valores.clear();
        valores.add(String.valueOf(idProducto));
        sismain.getControladorBD().aniadir(valores,"PRODUCTO",false);
        return idProducto;
    }
    
    public ArrayList buscarBD(String criterioBusqueda, String columnaBusqueda,
            char estado, DefaultTableModel modeloTabla){
        
        ArrayList<Long> indices = new ArrayList<>();

        String tablas = "PRODUCTO P";
        String columnas = "P.ID_PRODUCTO, P.ID_PRODUCTO, P.DESCRIPCION";
        String condicion= "P."+columnaBusqueda+ " = P." + criterioBusqueda;
        //COMO RESOLVER NO DUPLICAR LAS COLUMNAS??


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
    
    public void ampliarInfoBD(long idProducto){
        
        ArrayList<ArrayList<Object>> registros;

        String tablas;
        String columnas;
        String condicion;
        this.idProducto = idProducto;
        tablas = "PRODUCTO P";
        columnas = "P.ID_PRODUCTO , P.DESCRIPCION , P.STOCK_ACTUAL , "
                + "P.STOCK_CRITICO_MINIMO , P.PUNTO_PEDIDO , "
                + "P.TIPO_PRODUCTO_ID_TIPO_PRODUCTO , P.FABRICANTE_ID_FABRICANTE";
        condicion = "ID_PRODUCTO = '"+ getIdProducto() + "'";
        
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        setIdProducto(Long.parseLong(registros.get(0).get(0).toString()));
        setDescripcion(registros.get(0).get(1).toString());
        setStockActual(Integer.valueOf(registros.get(0).get(2).toString()));
        setStockCriticoMinimo(Integer.valueOf(registros.get(0).get(3).toString()));
        setPuntoPedido(Integer.valueOf(registros.get(0).get(4).toString()));
        setIdTipoProducto(Long.valueOf(registros.get(0).get(5).toString()));
        setIdFabricante(Long.valueOf(registros.get(0).get(6).toString()));
        
        
        //TIPO_PRODUCTO;
        
        tablas =  "TIPO_PRODUCTO T";
        columnas = "T.ID_TIPO_PRODUCTO, T.DESCRIPCION ";
        condicion = "T.ID_TIPO_PRODUCTO = '"+ getIdTipoProducto() + "'";
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        for(int i = 0; i<registros.size();i++){
            TipoProducto tipoProducto = new TipoProducto();
            tipoProducto.setIdTipoProducto(Long.parseLong(registros.get(i).get(0).toString()));
            tipoProducto.setDescripcion(registros.get(i).get(1).toString());
            getTiposProductos().add(tipoProducto);
        }
        
        registros.clear();
        //FABRICANTE;
        
        tablas = "FABRICANTE F";
        columnas = "F.ID_FABRICANTE, F.DESCRIPCION ";
        condicion = "F.ID_FABRICANTE = '"+getIdFabricante() + "'";
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        for(int i = 0; i<registros.size();i++){
            Fabricante fabricante = new Fabricante();
            fabricante.setIdFabricante(Long.parseLong(registros.get(i).get(0).toString()));
            fabricante.setDescripcion(registros.get(i).get(1).toString());
            getFabricantes().add(fabricante);
        }
        
        registros.clear();
        
        //PRECIO;
        
        tablas = "PRECIO P";
        columnas = "P.ID_PRECIO, P.NUMERO, P.PRODUCTO_ID_PRODUCTO ";
        condicion = "PRODUCTO_ID_PRODUCTO = '"+ getIdProducto() + "'";
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        for(int i = 0; i<registros.size();i++){
            Precio precio = new Precio();
            precio.setIdPrecio(Long.parseLong(registros.get(i).get(0).toString()));
            precio.setNumero(Double.valueOf(registros.get(i).get(1).toString()));
            getPrecios().add(precio);
        }       
        /*
        ArrayList InfoAmpliada:
            Primer Elemento, clase Cliente,
            Segundo Elemento, ArrayList de Telefonos
            Tercer Elemento, ArrayList de Domicilios
            Cuarto Elemento, ArrayList de Correos Electronicos
        */
        
     
    }
    
    public void modificarBD(){
        
                     
            String tablas = "PRODUCTO P";
            String set = "P.DESCRIPCION = '"+ getDescripcion()+"', "
            + "P.STOCK_ACTUAL = '" + getStockActual() + "', "
            + "P.STOCK_CRITICO_MINIMO = '" + getStockCriticoMinimo() + "', "
            + "P.PUNTO_PEDIDO = '" + getPuntoPedido() + "'";
            String condicion = "P.ID_PRODUCTO = '"+ getIdProducto()+"'";
            sismain.getControladorBD().modificar(tablas,set,condicion);
        
    }
    
    public void habilitarBD(){
        
        
        String tablas= "PRODUCTO P";
        String set = "P.ESTADO = 'H'";
        String condicion = "P.ID_PRODUCTO = '"+getIdProducto()+"'";

        sismain.getControladorBD().modificar(tablas,set,condicion);
        
    }
    
    public void deshabilitarBD(){
        
        
        String tablas= "PRODUCTO P";
        String set = "P.ESTADO = 'D'";
        String condicion = "P.ID_PRODUCTO = '"+getIdProducto()+"'";
        
        sismain.getControladorBD().modificar(tablas, set, condicion);

    }
    
}
