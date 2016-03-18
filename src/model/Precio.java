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
    
    private int idPrecio;
    private double numero;
    private String fechaHoraInicio;
    private String fechaHoraFin;
    private long idProducto;
    
    public Precio(){
        
    }
    
    public Precio(int idPrecio, double numero, String fechaHoraInicio, 
            String fechaHoraFin, long idProducto){
        this.idPrecio = idPrecio;
        this.numero = numero;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.idProducto = idProducto;
    }

    public int getIdPrecio() {
        return idPrecio;
    }

    public void setIdPrecio(int idPrecio) {
        this.idPrecio = idPrecio;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
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
        valores.add(String.valueOf(getNumero()));
        valores.add(getFechaHoraInicio());
        valores.add(getFechaHoraFin());
        valores.add(String.valueOf(getIdProducto()));
        idPrecio = sismain.getControladorBD().aniadirBD(valores, "PRECIO",false);
        valores.clear();
        valores.add(String.valueOf(idPrecio));
        sismain.getControladorBD().aniadirBD(valores,"PRECIO",false);
        return idPrecio;
    }
    
    /*public ArrayList buscarBD(String columnaBusqueda, 
                         DefaultTableModel modeloTabla,
                         boolean preBuscar){
        ArrayList<String> indices = new ArrayList<>();

        String criterioBusqueda;
        String criterioPreBusqueda;
        if(columnaBusqueda.equals("ID_PRODUCTO")){
            criterioBusqueda=String.valueOf(getIdProducto());
            criterioPreBusqueda="'"+String.valueOf(getIdProducto())+"%'";
        }
        else{
            criterioBusqueda="'"+getDescripcion()+"'";
            criterioPreBusqueda="'"+getDescripcion()+"%'";
        }
        String tablas = "PRODUCTO";
        String columnas = "ID_PRODUCTO , DESCRIPCION , STOCK_ACTUAL , STOCK_CRITICO_MINIMO, PUNTO_PEDIDO";
        String condicion;
        if(preBuscar){
            condicion = "("+columnaBusqueda+" LIKE "+criterioPreBusqueda+" OR "+columnaBusqueda+" = "+ criterioBusqueda+" )";
        }
        else{
            condicion = ""+columnaBusqueda+" = "+criterioBusqueda+"";
        }
        indices = sismain.getControladorBD().buscar(tablas, columnas, condicion, modeloTabla);
        return indices;
    }*/

    public void modificarBD(ArrayList<String> txt, String tabla, String columna, String id){
             
             String set = "NUMERO = " + txt.get(0);
             try{

                 String query = "UPDATE " + tabla + " SET " + set + " WHERE " + columna + " = " + id;
                 System.out.println(query);
                 sismain.getConexion().getStatement().execute(query);
             }catch (SQLException ex) {
            Logger.getLogger(ControladorBD.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
    
}
