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
public class ConceptoSueldo {
     
    private int idConcepto;
    private String nombre;
    private char remunerativo;
    private char porcentaje;
    private double cantidad;
    
    public ConceptoSueldo(){
        
    }
    
    public ConceptoSueldo(int idConcepto, String nombre, char remunerativo, 
                            char porcentaje, double cantidad){
    this.idConcepto = idConcepto;
    this.nombre = nombre;
    this.remunerativo = remunerativo;
    this.porcentaje = porcentaje;
    this.cantidad = cantidad;
    }

    public int getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(int idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public char getRemunerativo() {
        return remunerativo;
    }

    public void setRemunerativo(char remunerativo) {
        this.remunerativo = remunerativo;
    }

    public char getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(char porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
