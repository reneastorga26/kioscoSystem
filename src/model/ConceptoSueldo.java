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
public class ConceptoSueldo {
     
    private long idConcepto;
    private String descripcion;
    private double importe;
    private String unidad;
    private double porcentaje;
    private char estado;
    private String observaciones;
    private long idTipo;
    
    public ConceptoSueldo(){
        
    }
    
    public ConceptoSueldo(long idConcepto, String descripcion, double importe, 
                            String unidad, double porcentaje, char estado, 
                            String observaciones, long idTipo){
    this.idConcepto = idConcepto;
    this.descripcion = descripcion;
    this.importe = importe;
    this.unidad = unidad;
    this.porcentaje = porcentaje;
    this.estado = estado;
    this.observaciones = observaciones;
    this.idTipo = idTipo;
    }

    public long getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(long idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    
    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(long idTipo) {
        this.idTipo = idTipo;
    }

    
    
    public long guardarBD(){
        long idConcepto=-1;
        String auxImporte = String.valueOf(importe);
        String auxPorcentaje = String.valueOf(porcentaje);
        auxImporte = auxImporte.replace('.', ',');
        auxPorcentaje = auxPorcentaje.replace('.', ',');
        ArrayList<String> valores= new ArrayList<>();
        valores.add(descripcion);
        valores.add(auxImporte);
        valores.add(unidad);
        valores.add(auxPorcentaje);
        valores.add(String.valueOf(estado));
        valores.add(observaciones);
        valores.add(String.valueOf(idTipo));
        idConcepto = sismain.getControladorBD().aniadir(valores, "CONCEPTO_SUELDO",false);
        valores.clear();
        
        return idConcepto;
    }
    
    public ArrayList buscarBD(String criterioBusqueda, String columnaBusqueda,
            char estado, DefaultTableModel modeloTabla){
        
        ArrayList<Long> indices = new ArrayList<>();

        String tablas = "CONCEPTO_SUELDO C, TIPOS_CONCEPTOS T";
        String columnas = "C.ID_CONCEPTO, C.DESCRIPCION, T.DESCRIPCION, C.UNIDAD, "
                + "C.IMPORTE, C.PORCENTAJE, C.ESTADO, C.OBSERVACIONES";
        String condicion = "C."+columnaBusqueda+ " = " + criterioBusqueda ;
        
        indices = sismain.getControladorBD().buscar(columnas, 
                tablas, condicion, modeloTabla);
        
        return indices;
    }

    public void ampliarInfoBD(long idConcepto){
        
        ArrayList<ArrayList<Object>> registros;

        this.idConcepto = idConcepto;
        
        String tablas = "CONCEPTO_SUELDO C";
        String columnas = "C.ID_CONCEPTO, C.DESCRIPCION, "
                + "C.TIPO_CONCEPTO_ID_TIPO_CONCEPTO, "
                + "C.UNIDAD, C.IMPORTE, C.PORCENTAJE, C.ESTADO, C.OBSERVACIONES";
        String condicion = "C.ID_CONCEPTO = '" + idConcepto + "'" ;
        
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        setIdConcepto(Long.valueOf(registros.get(0).get(0).toString()));
        setDescripcion(registros.get(0).get(1).toString());
        setIdTipo(Long.valueOf(registros.get(0).get(2).toString()));
        setUnidad(registros.get(0).get(3).toString());
        setImporte(Double.valueOf(registros.get(0).get(4).toString()));
        setPorcentaje(Double.valueOf(registros.get(0).get(5).toString()));
        setEstado(registros.get(0).get(6).toString().charAt(0));
        setObservaciones(registros.get(0).get(7).toString());
        
        registros.clear();
        
    }
    
    
    public void modificarBD(){
            
        String tablas = "CONCEPTO_SUELDO C";
        String set = "C.DESCRIPCION = '"+ descripcion +"',"
            + "C.IMPORTE = '" + importe + "',"
            + "C.UNIDAD = '" + unidad + "',"
            + "C.PORCENTAJE = '" + porcentaje + "',"
            + "C.OBSERVACIONES = '" + observaciones + "',"
            + "C.TIPO_CONCEPTO_ID_TIPO_CONCEPTO";        
        String condicion = "C.ID_CONCEPTO = '"+ idConcepto +"'";    
            
            sismain.getControladorBD().modificar(tablas,set,condicion);
    }
    
    
    
    public void eliminarBD(long id_referenciado){
        
        String tabla = "CONCEPTO_SUELDO C";
        String condicion = "C.ID_CONCEPTO = '" + id_referenciado +"'";    
            
        sismain.getControladorBD().eliminar(tabla, condicion);
               
    }
}
