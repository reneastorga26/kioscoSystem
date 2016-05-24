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
public class Calculo {
    
    private double sueldoBasico;
    private double porcentaje;
    private double importe;
    
    public double calcular(double sueldoBasico, double porcentaje){
        importe = sueldoBasico * porcentaje;
        return importe;
    }
}
