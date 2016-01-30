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
public class OrdenCompra {
    
        private int idOrdenCompra;
        private String fechaEmision;
        private double totalCompra;
        private Proveedor idProveedor;
        
        public OrdenCompra(){
            
        }
        
        public OrdenCompra(int idOrdenCompra, String fechaEmision, 
                double totalCompra, Proveedor idProveedor){
            this.idOrdenCompra = idOrdenCompra;
            this.fechaEmision = fechaEmision;
            this.totalCompra = totalCompra;
        }

    public int getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(int idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public Proveedor getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Proveedor idProveedor) {
        this.idProveedor = idProveedor;
    }

      
        
}

