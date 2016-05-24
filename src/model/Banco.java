/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import sistemakiosco.sismain;

/**
 *
 * @author CX
 */
public class Banco {
    
    private long idBanco;
    private String nombre;
    
    public Banco(){
        
    }
    
    public Banco(long idBanco, String nombre){
        this.idBanco = idBanco;
        this.nombre = nombre;
    }

    public long getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(long idBanco) {
        this.idBanco = idBanco;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    
    public long guardarBD(){
        long idBanco=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(nombre);
        idBanco = sismain.getControladorBD().aniadir(valores, "BANCO",false);
        valores.clear();
        return idBanco;
    }
    
    public ArrayList buscarBD(String criterioBusqueda, String columnaBusqueda,
                            DefaultTableModel modeloTabla){
        
        ArrayList<Long> indices;// = new ArrayList<>();

        String tablas = "BANCO B";
        String columnas = "B.ID_BANCO, B.NOMBRE";
        String condicion = columnaBusqueda+ " = " + criterioBusqueda;
        
               
        indices = sismain.getControladorBD().buscar(columnas, 
                tablas, condicion, modeloTabla);
        
        return indices;
    }
}
