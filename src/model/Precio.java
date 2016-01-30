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
public class Precio {
    
    private int idPrecio;
    private double numero;
    private String fechaHoraInicio;
    private String fechaHoraFin;
    private Producto idProducto;
    
    public Precio(){
        
    }
    
    public Precio(int idPrecio, double numero, String fechaHoraInicio, 
            String fechaHoraFin, Producto idProducto){
        this.idPrecio = idPrecio;
        this.numero = numero;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
    }

    public int getIdPrecio() {
        return idPrecio;
    }

    public void setIdPrecio(int idPrecio) {
        this.idPrecio = idPrecio;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
    }

    public String getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(String fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public String getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(String fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    
    
    
}
