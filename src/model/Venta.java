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
public class Venta {
    
    private int idVenta;
    private String fechaEmision;
    private String horaEmision;
    private double totalVenta;
    private double iva;
    private Cliente idCliente;
    private Empleado idEmpleado;
    
    
    public Venta(){
        
    }
    
    public Venta(int idVenta, String fechaEmision, String horaEmision, 
            double totalVenta, double iva, Cliente idCliente, Empleado idEmpleado){
        this.idVenta = idVenta;
        this.fechaEmision = fechaEmision;
        this.horaEmision = horaEmision;
        this.totalVenta = totalVenta;
        this.iva = iva;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getHoraEmision() {
        return horaEmision;
    }

    public void setHoraEmision(String horaEmision) {
        this.horaEmision = horaEmision;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
    
    
    
}
