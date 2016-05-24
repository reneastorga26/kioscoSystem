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
public class TipoConcepto {
    
    private long idTipo;
    private String descripcion;
    
    public TipoConcepto(){
        
    }
    
    public TipoConcepto(long idTipo, String descripcion){
        this.idTipo = idTipo;
        this.descripcion = descripcion;
    }

    public long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(long idTipo) {
        this.idTipo = idTipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public long guardarBD(){
        long idTipoConcepto=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(descripcion);
        idTipoConcepto = sismain.getControladorBD().aniadir(valores, "TIPOS_CONCEPTOS",false);
        valores.clear();
        
        return idTipoConcepto;
    }
    
    public ArrayList buscarBD(String criterioBusqueda, String columnaBusqueda,
                        DefaultTableModel modeloTabla){
        
        ArrayList<Long> indices = new ArrayList<>();

        String tablas = "TIPOS_CONCEPTOS T";
        String columnas = "T.ID_TIPO_CONCEPTO, T.DESCRIPCION";
        String condicion = "T."+columnaBusqueda+ " = " + criterioBusqueda ;
        
        indices = sismain.getControladorBD().buscar(columnas, 
                tablas, condicion, modeloTabla);
        
        return indices;
    }
    
    public void ampliarInfoBD(long idTipoConcepto){
        
        ArrayList<ArrayList<Object>> registros;

        this.idTipo = idTipoConcepto;
        
        String tablas = "TIPOS_CONCEPTOS T";
        String columnas = "T.ID_TIPO_CONCEPTO, T.DESCRIPCION";
        String condicion = "T.ID_TIPO_CONCEPTO = '" + idTipo + "'" ;
        
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        setIdTipo(Long.valueOf(registros.get(0).get(0).toString()));
        setDescripcion(registros.get(0).get(1).toString());
        registros.clear();
    }
    
    public void eliminarBD(long id_referenciado){
        
        String tabla = "TIPO_CONCEPTO T";
        String condicion = "T.ID_TIPO_CONCEPTO = '" + id_referenciado +"'";    
            
        sismain.getControladorBD().eliminar(tabla, condicion);
               
    }
}
