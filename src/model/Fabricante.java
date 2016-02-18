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
    
    public void update(ArrayList<String> txt, String tabla, String columna, String id){
             
             String set = "DESCRIPCION = '" + txt.get(0) + "'";
             try{

                 String query = "UPDATE " + tabla + " SET " + set + " WHERE " + columna + " = " + id;
                 System.out.println(query);
                 sismain.getConexion().getStatement().execute(query);
             }catch (SQLException ex) {
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
