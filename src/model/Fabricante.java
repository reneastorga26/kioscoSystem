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
import sistemakiosco.sismain;

/**
 *
 * @author CX
 */
public class Fabricante {
    
    private int idFabricante;
    private String descripcion;
    private char tipoFabricante;
    
    public Fabricante(){
        
    }
    
    public Fabricante(int idFabricante, String descripcion, char tipoFabricante){
    this.idFabricante = idFabricante;
    this.descripcion = descripcion;
    this.tipoFabricante = tipoFabricante;
    }

    public int getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(int idFabricante) {
        this.idFabricante = idFabricante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public char getTipoFabricante() {
        return tipoFabricante;
    }

    public void setTipoFabricante(char tipoFabricante) {
        this.tipoFabricante = tipoFabricante;
    }
    
    public void modificarBD(ArrayList<String> cadena, String cadenaId){
             
             String set = "DESCRIPCION = '" + cadena.get(0) + "'";
             
             sismain.getControladorBD().modificar(set, "FABRICANTE", "ID_FABRICANTE", cadenaId);
    }
    
    public void eliminarBD(String cadenaId){
            sismain.getControladorBD().eliminar("FABRICANTE", "ID_FABRICANTE", cadenaId);
    }
}
