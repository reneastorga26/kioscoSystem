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
public class Fabricante {
    
    private long idFabricante;
    private String descripcion;
    private String tipoFabricante;
    private char estado;

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
    
    public Fabricante(){
        
    }
    
    public Fabricante(long idFabricante, String descripcion, String tipoFabricante){
    this.idFabricante = idFabricante;
    this.descripcion = descripcion;
    this.tipoFabricante = tipoFabricante;
    }

    public long getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(long idFabricante) {
        this.idFabricante = idFabricante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoFabricante() {
        return tipoFabricante;
    }

    public void setTipoFabricante(String tipoFabricante) {
        this.tipoFabricante = tipoFabricante;
    }
    
    
    public long guardarBD(){
        long idFabricante=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(descripcion);
        valores.add(tipoFabricante);
        valores.add(String.valueOf(estado));
        idFabricante = sismain.getControladorBD().aniadir(valores, "FABRICANTE",false);
        valores.clear();
        
        return idFabricante;
    }
    
    
    public void ampliarInfoBD(String criterioBusqueda, String columnaBusqueda,
            char estado, DefaultTableModel modeloTabla){
        
        ArrayList<ArrayList<Object>> registros;

        String tablas = "F_FABRICANTE";
        String columnas = "F.ID_FABRICANTE, F.DESCRIPCION, "
                + "F.TIPO_FABRICANTE , F.ESTADO";
        String condicion = "T."+columnaBusqueda+ " = '" + criterioBusqueda 
                +"'AND F.ESTADO = '"+ estado + "' AND ROWNUM = 1";
        

        registros = sismain.getControladorBD().extenderInfo(columnas, 
                tablas, condicion);
 
        idFabricante = Long.parseLong(registros.get(0).get(0).toString());
        descripcion = registros.get(0).get(1).toString();
        tipoFabricante = registros.get(0).get(2).toString();
        this.estado = estado;
        
    }
    
    public void modificarBD(){
             
            String tablas = "FABRICANTE F";
            String set = "F.DESCRIPCION = '" + descripcion + "',"+
                    "F.TIPO_FABRICANTE = '"+ tipoFabricante+ "',"+
                    "F.ESTADO = '"+estado+"'";
            String condicion = "T.ID_FABRICANTE = '"+ idFabricante+"'";
             
             sismain.getControladorBD().modificar(tablas,set,condicion);
    } 
    
    public void eliminarBD(long id_referenciado){
        
        String tabla = "FABRICANTE F";
        String condicion = " F.ID_FABRICANTE = '"+ id_referenciado +"'";
        
        sismain.getControladorBD().eliminar(tabla, condicion);
               
    }
    
    public ArrayList obtenerDescripcionTodos(){
       ArrayList<ArrayList<Object>> registros;
       
       String tablas= "FABRICANTE F";
       String columnas="F.ID_FABRICANTE, F.DESCRIPCION";
       String condicion="F.ID_FABRICANTE = F.ID_FABRICANTE";
       
       registros=sismain.getControladorBD().extenderInfo(columnas, tablas, condicion);
       
       return registros;
   }
}
