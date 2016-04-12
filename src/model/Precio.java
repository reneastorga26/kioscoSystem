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
import javax.swing.table.DefaultTableModel;
import sistemakiosco.sismain;

/**
 *
 * @author CX
 */
public class Precio {
    
    private long idPrecio;
    private double montoMinorista;
    private double montoMayorista=-1;
    private String fechaHoraInicio;
    private String fechaHoraFin;
    private long idProducto;
    private long idProveedor;
    private char venta;

    
    
        public Precio(){
        
    }

    public double getMontoMinorista() {
        return montoMinorista;
    }

    public void setMontoMinorista(double montoMinorista) {
        this.montoMinorista = montoMinorista;
    }

    public double getMontoMayorista() {
        return montoMayorista;
    }

    public void setMontoMayorista(double montoMayorista) {
        this.montoMayorista = montoMayorista;
    }


    
    
    public char getVenta() {
        return venta;
    }

    public void setVenta(char venta) {
        this.venta = venta;
    }
    
    
    public long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(long idProveedor) {
        this.idProveedor = idProveedor;
    }


   
    


    public long getIdPrecio() {
        return idPrecio;
    }

    public void setIdPrecio(long idPrecio) {
        this.idPrecio = idPrecio;
    }

    public String getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(String fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public String getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(String fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    public long guardarBD(){
        long idPrecio=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(fechaHoraInicio);
        valores.add(fechaHoraFin);
        valores.add(String.valueOf(idProducto));
        valores.add(String.valueOf(venta));
        valores.add(String.valueOf(idProveedor));
        valores.add(String.valueOf(montoMayorista));
        valores.add(String.valueOf(montoMinorista));
        sismain.getControladorBD().aniadir(valores, "PRECIO",false);
        valores.clear();
        return idPrecio;
    }
    
    public void modificarBD(ArrayList<String> txt, String tabla, String columna, String id){
        
            String tablas = "PRECIO P";
            String set = "P.FECHA_HORA_INICIO = '"+ fechaHoraInicio+"', "
                    + "P.FECHA_HORA_FIN = '" + fechaHoraFin + "', "
                    + "P.PRODUCTO_ID_PRODUCTO = '"+idProducto+"', "
                    + "P.VENTA = '"+venta+"', "
                    + "P.PROVEEDOR_ID_PROVEEDOR = '"+idProveedor+ "', "
                    + "P.MONTO_MAYORISTA = '"+montoMayorista+"', "
                    + "P.MONTO_MINORISTA = '"+montoMinorista+"', ";
                    
            String condicion = "P.ID_PRECIO = '"+idPrecio+"'";
            sismain.getControladorBD().modificar(tablas,set,condicion);
        
    }
    
}
