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
public class LineaCompra {
    
    private int idLineaCompra;
    private int cantidad;
    private OrdenCompra idOrdenCompra;
    private Producto idProducto;
    
    public LineaCompra(){
        
    }
    
    public LineaCompra(int idLineaCompra, int cantidad, OrdenCompra idOrdenCompra,
                       Producto idProducto){
        this.idLineaCompra = idLineaCompra;
        this.cantidad = cantidad;
        
    }

    public int getIdLineaCompra() {
        return idLineaCompra;
    }

    public void setIdLineaCompra(int idLineaCompra) {
        this.idLineaCompra = idLineaCompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public OrdenCompra getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(OrdenCompra idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    
    
    
}
