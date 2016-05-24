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
    private long codigo;
    private String descripcion;
    private int stockActual;
    private int stockCriticoMinimo;
    private int puntoPedido;
    private int unidadesPackMayorista;
    private long idTipoProducto;
    private long idFabricante;
    private char estadoStock;
    private char estado;
    private ArrayList<Precio> preciosVenta;
    private ArrayList<Precio> preciosCompra;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    
    
    public ArrayList<Precio> getPreciosVenta() {
        return preciosVenta;
    }

    public void setPreciosVenta(ArrayList<Precio> preciosVenta) {
        this.preciosVenta = preciosVenta;
    }

    public ArrayList<Precio> getPreciosCompra() {
        return preciosCompra;
    }

    public void setPreciosCompra(ArrayList<Precio> preciosCompra) {
        this.preciosCompra = preciosCompra;
    }
    

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
        ArrayList<String> valores= new ArrayList<>();
        valores.add(String.valueOf(idProducto));
        valores.add(descripcion);
        valores.add(String.valueOf(stockActual));
        valores.add(String.valueOf(stockCriticoMinimo));
        valores.add(String.valueOf(puntoPedido));
        valores.add(String.valueOf(unidadesPackMayorista));
        valores.add(String.valueOf(idTipoProducto));
        valores.add(String.valueOf(idFabricante));
        valores.add(String.valueOf(estado));
        valores.add(String.valueOf(estadoStock));
        valores.add(String.valueOf(codigo));
        
        idProducto = sismain.getControladorBD().aniadir(valores, "PRODUCTO",false);
        return idProducto;
    }
    
    public String traductorEstadoStock(String estado, boolean sentido){
        String resultado="";
        if(sentido == true){
        switch(estado){
                case "Normal":resultado="A";break;
                case "Cerca al Punto de Pedido": resultado="B";break;
                case "Punto de Pedido Alcanzado": resultado="C";break;
                case "Stock Critico Minimo Alcanzado": resultado="D";break;
                case "Agotado": resultado = "E";break; 
            }
        }
        else{
            switch(estado){
                case "A": resultado = "Normal";break;
                case "B": resultado = "Cerca al Punto de Pedido";break;
                case "C": resultado = "Punto de Pedido Alcanzado";break;
                case "D": resultado = "Stock Ciritico Minimo Alcanzado";break;
                case "E": resultado = "Agotado";break;
            }
        }
        return resultado;
    }
    
    public ArrayList buscarBD(String criterioBusqueda, String columnaBusqueda,
            char estado, DefaultTableModel modeloTabla){
        
        ArrayList<Long> indices = new ArrayList<>();

        String tablas = "PRODUCTO P";
        String columnas = "P.ID_PRODUCTO, P.ID_PRODUCTO, P.DESCRIPCION";
        String condicion= "P."+columnaBusqueda+ " = " + criterioBusqueda;


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
                + "P.STOCK_CRITICO_MINIMO , P.PUNTO_PEDIDO ,"
                + "P.UNIDADES_PACK_MAYORISTA,P.TIPO_PRODUCTO_ID_TIPO_PRODUCTO ,"
                + " P.FABRICANTE_ID_FABRICANTE, P.ESTADO, P.ESTADO_STOCK, "
                + "P.CODIGO";
        condicion = "ID_PRODUCTO = '"+ this.idProducto + "' AND ROWNUM = 1";
        
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        this.idProducto=Long.parseLong(registros.get(0).get(0).toString());
        descripcion=registros.get(0).get(1).toString();
        stockActual=Integer.valueOf(registros.get(0).get(2).toString());
        stockCriticoMinimo=Integer.valueOf(registros.get(0).get(3).toString());
        puntoPedido=Integer.valueOf(registros.get(0).get(4).toString());
        unidadesPackMayorista=Integer.parseInt(registros.get(0).get(5).toString());
        idTipoProducto=Long.valueOf(registros.get(0).get(6).toString());
        idFabricante=Long.valueOf(registros.get(0).get(7).toString());
        estado=registros.get(0).get(8).toString().charAt(0);
        estadoStock=registros.get(0).get(9).toString().charAt(0);
        codigo=Long.parseLong(registros.get(0).get(10).toString());
        
        registros.clear();
        
        //PRECIO VENTA;
        
        tablas = "PRECIO P";
        columnas = "P.ID_PRECIO, P.MONTO_MAYORISTA, MONTO_MINORISTA, P.FECHA_HORA_INICIO,"
                + "P.FECHA_HORA_FIN";
        condicion = "PRODUCTO_ID_PRODUCTO = '"+ idProducto + "'"
                + "AND VENTA = 'S'";
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        for(int i = 0; i<registros.size();i++){
            Precio precio = new Precio();
            precio.setIdPrecio(Long.parseLong(registros.get(i)
            .get(0).toString()));
            precio.setMontoMayorista(Double.valueOf(registros.get(i)
            .get(1).toString()));
            precio.setMontoMinorista(Double.valueOf(registros.get(i)
            .get(2).toString()));
            precio.setFechaHoraInicio(String.valueOf(registros.get(i)
            .get(3).toString()));
            precio.setFechaHoraFin(String.valueOf(registros.get(i)
            .get(4).toString()));
            preciosVenta.add(precio);
        }       
        
        registros.clear();
        
        //PRECIO COMPRA;
        
        tablas = "PRECIO P";
        columnas = "P.ID_PRECIO, P.MONTO_MAYORISTA, P.MONTO_MINORISTA, P.FECHA_HORA_INICIO,"
                + "P.FECHA_HORA_FIN, P.PROVEEDOR_ID_PROVEEDOR";
        condicion = "PRODUCTO_ID_PRODUCTO = '"+ idProducto + "'"
                + "AND VENTA = 'N'";
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        for(int i = 0; i<registros.size();i++){
            Precio precio = new Precio();
            precio.setIdPrecio(Long.parseLong(registros.get(i).get(0).toString()));
            precio.setMontoMayorista(Double.valueOf(registros.get(i)
            .get(1).toString()));
            precio.setMontoMinorista(Double.valueOf(registros.get(i)
            .get(2).toString()));
            precio.setFechaHoraInicio(String.valueOf(registros.get(i)
            .get(3).toString()));
            precio.setFechaHoraFin(String.valueOf(registros.get(i)
            .get(4).toString()));
            precio.setIdProveedor(Long.parseLong(registros.get(i)
            .get(5).toString()));
            preciosCompra.add(precio);
        }       
        
        registros.clear();
     
    }
    
    public void modificarBD(){
          
            String tablas = "PRODUCTO P";
            String set = "P.ID_PRODUCTO = '"+idProducto+"', "                                                                                   
                    + "P.DESCRIPCION = '"+ descripcion+"', "
                    + "P.STOCK_ACTUAL = '" + stockActual + "', "
                    + "P.FABRICANTE_ID_FABRICANTE = '"+idFabricante+"', "
                    + "P.UNIDADES_PACK_MAYORISTA = '"+unidadesPackMayorista+ "', "
                    + "P.ESTADO = '"+estado+"',"
                    + "P.ESTADO_STOCK = '"+estadoStock+"',"
                    + "P.STOCK_CRITICO_MINIMO = '" + stockCriticoMinimo + "', "
                    + "P.PUNTO_PEDIDO = '" + puntoPedido + "', "
                    + "P.CODIGO = '"+codigo+"'";
            String condicion = "P.ID_PRODUCTO = '"+idProducto+"'";
            sismain.getControladorBD().modificar(tablas,set,condicion);
        
    }
    
}
