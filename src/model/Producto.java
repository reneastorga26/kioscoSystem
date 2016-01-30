/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author CX
 */
public class Producto {

    private int idProducto;
    private String descripcion;
    private int stockActual;
    private int stockCriticoMinimo;
    private int puntoPedido;
    private char tipoCompraProveedor;
    private int unidadesPackMayorista;
    private TipoProducto idTipoProducto;
    private Fabricante idFabricante;
    
    public Producto(){
        
    }
    
    public Producto(int idProducto, String descripcion, int stockActual,
            int stockCriticoMinimo, int puntoPedido, char tipoCompraProveedor, 
            int unidadesPackMayorista, TipoProducto idTipoProducto, Fabricante idFabricante){
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.stockActual = stockActual;
        this.stockCriticoMinimo = stockCriticoMinimo;
        this.puntoPedido = puntoPedido;
        this.tipoCompraProveedor = tipoCompraProveedor;
        this.unidadesPackMayorista = unidadesPackMayorista;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
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

    public TipoProducto getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(TipoProducto idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public Fabricante getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(Fabricante idFabricante) {
        this.idFabricante = idFabricante;
    }

    
    
    
}
