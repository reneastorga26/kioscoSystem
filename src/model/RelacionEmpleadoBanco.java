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
public class RelacionEmpleadoBanco {
 
    private long idEmpleado;
    private String nroCuenta;
    private long idBanco;
    
    public RelacionEmpleadoBanco(){
        
    }
    
    public RelacionEmpleadoBanco(long idEmpleado, String nroCuenta, long idBanco){
        this.idBanco = idBanco;
        this.idEmpleado = idEmpleado;
        this.nroCuenta = nroCuenta;
    }

    public long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public long getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(long idBanco) {
        this.idBanco = idBanco;
    }
    
    public long guardarBD(){
        long idRelacionEmpleadoBanco=-1;
        ArrayList<String> valores= new ArrayList<>();
        valores.add(String.valueOf(idEmpleado));
        valores.add(String.valueOf(nroCuenta));
        valores.add(String.valueOf(idBanco));
        idRelacionEmpleadoBanco = sismain.getControladorBD().aniadir(
                                valores, "BANCO",false);
        valores.clear();
        return idRelacionEmpleadoBanco;
    }
    
    public void eliminarBD(long id_referenciado){
        
        String tabla = "RELACION_EMPLEADO_BANCO R";
        String condicion = "R.EMPLEADO_ID_EMPLEADO = '"+ id_referenciado +"'";
        
        sismain.getControladorBD().eliminar(tabla, condicion);
               
    }
}
