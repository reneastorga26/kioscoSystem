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
public class LineaVenta {
 
    private int idLineaVenta;
    private int cantidad;
    private double subtotal;
    private Venta idVenta;
    private Producto idProducto;
    
    public LineaVenta(){
        
    }
    
    public LineaVenta(int idLineaVenta, int cantidad, double subtotal,
                       Venta idVenta, Producto idProducto){
    this.idLineaVenta = idLineaVenta;
    this.cantidad = cantidad;
    this.subtotal = subtotal;
    }

    public int getIdLineaVenta() {
        return idLineaVenta;
    }

    public void setIdLineaVenta(int idLineaVenta) {
        this.idLineaVenta = idLineaVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Venta getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Venta idVenta) {
        this.idVenta = idVenta;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    
    
    
}
