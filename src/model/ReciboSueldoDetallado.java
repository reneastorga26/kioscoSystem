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
public class ReciboSueldoDetallado {
 
    private long idReciboSueldo;
    private long idLiquidacionIndividual;
    private String fecha;
    private String totalNetoEscrito;
    private String nroRecibo;
    
    public ReciboSueldoDetallado(){
        
    }
    
    public ReciboSueldoDetallado(long idReciboSueldo, long idLiquidacionIndividual,
                        String fecha, String totalNetoEscrito, String nroRecibo){
        this.fecha = fecha;
        this.idLiquidacionIndividual = idLiquidacionIndividual;
        this.idReciboSueldo = idReciboSueldo;
        this.totalNetoEscrito = totalNetoEscrito;
        this.nroRecibo = nroRecibo;
    }

    public long getIdReciboSueldo() {
        return idReciboSueldo;
    }

    public void setIdReciboSueldo(long idReciboSueldo) {
        this.idReciboSueldo = idReciboSueldo;
    }

    public long getIdLiquidacionIndividual() {
        return idLiquidacionIndividual;
    }

    public void setIdLiquidacionIndividual(long idLiquidacionIndividual) {
        this.idLiquidacionIndividual = idLiquidacionIndividual;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTotalNetoEscrito() {
        return totalNetoEscrito;
    }

    public void setTotalNetoEscrito(String totalNetoEscrito) {
        this.totalNetoEscrito = totalNetoEscrito;
    }

    public String getNroRecibo() {
        return nroRecibo;
    }

    public void setNroRecibo(String nroRecibo) {
        this.nroRecibo = nroRecibo;
    }
  
    
    
    public long guardarBD(){
        long idReciboSueldo=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(String.valueOf(idLiquidacionIndividual));
        valores.add(fecha);
        valores.add(totalNetoEscrito);
        valores.add(nroRecibo);
        valores.add("GENERADO");
        idReciboSueldo = sismain.getControladorBD().aniadir(
                valores, "RECIBO_SUELDO",false);
        valores.clear();
        return idReciboSueldo;
    }
    
    public ArrayList buscarBD(String criterioBusqueda, String columnaBusqueda,
                         DefaultTableModel modeloTabla){
        
        ArrayList<Long> indices;// = new ArrayList<>();

        String tablas = "RECIBO_SUELDO R";
        String columnas = "R.ID_RECIBO_SUELDO";
        String condicion = columnaBusqueda+ " = " + criterioBusqueda;
               
        indices = sismain.getControladorBD().buscar(columnas, 
                tablas, condicion, modeloTabla);
        
        return indices;
    }

    public void ampliarInfoBD(long idReciboSueldo){
        
        ArrayList<ArrayList<Object>> registros;

        String tablas;
        String columnas;
        String condicion;
        this.idReciboSueldo = idReciboSueldo;
        tablas = "RECIBO_SUELDO R";
        columnas = "R.ID_RECIBO_SUELDO, R.LIQ_IND_SUELDO_ID_LIQ_IND, R.FECHA, "
                + "R.TOTAL_NETO_ESCRITO, R.NRO_RECIBO_SUELDO";
        condicion = "R.ID_RECIBO_SUELDO = '"+ idReciboSueldo + "'";
        
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        setIdReciboSueldo(Long.parseLong(registros.get(0).get(0).toString()));
        setIdLiquidacionIndividual(Long.parseLong(registros.get(0).get(1).toString()));
        setFecha(registros.get(0).get(2).toString());
        setTotalNetoEscrito(registros.get(0).get(3).toString());
        setNroRecibo(registros.get(0).get(4).toString());
             
    }
    
    public void infoReciboSueldo(String datoEmpleado, String periodo, 
                        String tipo, DefaultTableModel modeloTabla, 
                        boolean todosLosDatos){
        ArrayList<ArrayList<Object>> registros;
        
        String tablas;
        String columnas;
        String condicion = "";
        
        tablas = "LIQ_IND_SUELDO L, EMPLEADO E, PERSONA P";
        columnas = "E.ID_EMPLEADO, E.CUIL, P.NOMBRE_APELLIDO, L.ID_LIQIND, "
                        + "L.PERIODO, L.FECHA_LIQUIDACION";
        
        //ESTO FUNCIONA CUANDO NO HAY CUIL INGRESADO, PERIODO Y TIPO - (SI)
        if(datoEmpleado.equals("") && periodo.equals("") && tipo.equals("")){
        condicion = "L.ID_LIQIND != '0' AND E.ID_EMPLEADO = L.EMPLEADO_ID_EMPLEADO "
                + "AND E.PERSONA_ID_PERSONA = P.ID_PERSONA ";
        }
        
        //ESTO FUNCIONA CUANDO HAY PERIODO INGRESADO Y NO HAY CUIL Y TIPO - (SI)
        if(datoEmpleado.equals("") && tipo.equals("")){
        condicion = "L.ID_LIQIND != '0' AND E.ID_EMPLEADO = L.EMPLEADO_ID_EMPLEADO "
                + "AND E.PERSONA_ID_PERSONA = P.ID_PERSONA AND "
                + "L.PERIODO = '"+ periodo +"'";
        }
        
        //ESTO FUNCIONA CUANDO HAY CUIL O NOMBRE Y APELLIDO INGRESADO Y NO HAY TIPO Y PERIODO - (SI)
        if(periodo.equals("") && tipo.equals("")){
        condicion = "L.ID_LIQIND != '0' AND E.ID_EMPLEADO = L.EMPLEADO_ID_EMPLEADO "
                + "AND E.PERSONA_ID_PERSONA = P.ID_PERSONA AND " + datoEmpleado +"";
        }
        
        //ESTO FUNCIONA CUANDO HAY TIPO INGRESADO Y NO HAY CUIL Y PERIODO - (SI)
        if(periodo.equals("") && datoEmpleado.equals("")){
        condicion = "L.ID_LIQIND != '0' AND E.ID_EMPLEADO = L.EMPLEADO_ID_EMPLEADO "
                + "AND E.PERSONA_ID_PERSONA = P.ID_PERSONA AND "
                + "L.TIPO_LIQ_ID_TIPO_LIQ = '" + tipo + "'";
        }
        
        //ESTO FUNCIONA CUANDO HAY TIPO Y PERIODO Y NO CUIL O NOMBRE Y APELLIDO - (SI)
        if(datoEmpleado.equals("") && periodo != "" && tipo != ""){
        condicion = "L.ID_LIQIND != '0' AND E.ID_EMPLEADO = L.EMPLEADO_ID_EMPLEADO AND "
                + "E.PERSONA_ID_PERSONA = P.ID_PERSONA AND "
                + "L.PERIODO = '"+ periodo + "' "
                + "AND L.TIPO_LIQ_ID_TIPO_LIQ = '"+ tipo +"'";
        }
        
        //ESTO FUNCIONA CUANDO HAY TIPO, CUIL Y PERIODO INGRESADO - (SI)
        if(todosLosDatos){
        condicion = "L.ID_LIQIND != '0' AND E.ID_EMPLEADO = L.EMPLEADO_ID_EMPLEADO AND "
                + "E.PERSONA_ID_PERSONA = P.ID_PERSONA AND "
                + datoEmpleado +" AND L.PERIODO = '"+ periodo + "' "
                + "AND L.TIPO_LIQ_ID_TIPO_LIQ = '"+ tipo +"'";
        }
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        Object [] fila = new Object[6];
            for(int i=0; i<registros.size();i++){
                fila [0] = registros.get(i).get(0).toString();
                fila [1] = registros.get(i).get(1).toString();
                fila [2] = registros.get(i).get(2).toString();
                fila [3] = registros.get(i).get(3).toString();
                fila [4] = registros.get(i).get(4).toString();
                fila [5] = sismain.getControladorDate().darFormatoFechaATabla(
                        registros.get(i).get(5).toString());
                modeloTabla.addRow(fila);
                
            }        
    }
    
    public void ampliarInfoReciboSueldo(long idEmpleado, long idTipoLiquidacion,
                              long idLiquidacion, DefaultTableModel modeloTabla){
        
        ArrayList<ArrayList<Object>> registros;

        String tablas;
        String columnas;
        String condicion;
        
        tablas = "RELACION_EMPLEADO_CONCEPTOS R, CONCEPTO_SUELDO C, LIQ_IND_SUELDO L, " 
                + "DETALLE_LIQ D";
        columnas = "C.ID_CONCEPTO, C.DESCRIPCION, D.CONCEPTO_SUELDO_UNIDAD, "
                + "D.HABERES_REMUNERATIVOS, " 
                + "D.HABERES_NO_REMUNERATIVOS, D.RETENCIONES";
        condicion = "R.EMPLEADO_ID_EMPLEADO = '"+idEmpleado+"' AND "
                + "R.CONCEPTO_ID_CONCEPTO = C.ID_CONCEPTO " 
                + "AND R.TIPO_LIQ_ID_TIPO_LIQ = '"+idTipoLiquidacion+"' AND "
                + "D.LIQ_IND_SUELDO_ID_LIQIND = L.ID_LIQIND AND " 
                + "L.ID_LIQIND = '" + idLiquidacion + "' AND "
                + "D.CONCEPTO_SUELDO_ID_CONCEPTO = C.ID_CONCEPTO";
        
        
        registros = sismain.getControladorBD().extenderInfo
        (columnas, tablas, condicion);
        
        Object [] fila = new Object[6];
        for(int i = 0; i<registros.size();i++){
            ConceptoSueldo concepto = new ConceptoSueldo();
            concepto.setIdConcepto(Long.valueOf(registros.get(i).get(0).toString()));
            concepto.setDescripcion(registros.get(i).get(1).toString());
            DetalleLiquidacion detalle = new DetalleLiquidacion();
            detalle.setUnidadConcepto(registros.get(i).get(2).toString());
            detalle.setHaberesRemunerativos(Double.valueOf(registros.get(i).get(3).toString()));
            detalle.setHaberesNoRemunerativos(Double.valueOf(registros.get(i).get(4).toString()));
            detalle.setRetenciones(Double.valueOf(registros.get(i).get(5).toString()));
            
            if(modeloTabla != null){
            fila [0] = concepto.getIdConcepto();
            fila [1] = concepto.getDescripcion();
            fila [2] = detalle.getUnidadConcepto();
            fila [3] = detalle.getHaberesRemunerativos();
            fila [4] = detalle.getHaberesNoRemunerativos();
            fila [5] = detalle.getRetenciones();
            modeloTabla.addRow(fila);
            }    
            
        }
    }
}
