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
public class Proveedor {
    
    private int idProveedor;
    private String cuit;
    private String razonSocial;
    private String observaciones;
    
    public Proveedor(){
        
    }
    
    public Proveedor(int idProveedor, String cuit, String razonSocial, 
            String observaciones){
        this.idProveedor = idProveedor;
        this.cuit = cuit;
        this.razonSocial = razonSocial;
        this.observaciones = observaciones;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    
    
}
