/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import sistemakiosco.sismain;

/**
 *
 * @author CX
 */
public class Sesion {
    
    private int idSesion;
    private String fechaHoraInicio;
    private String fechaHoraFin;
    private String novedades = "";
    private long idPerfil;


    
    public Sesion(){
        
    }
    
    public Sesion(long idPerfil){
        this.idPerfil=idPerfil;
        initSesion();
    }

    public long getIdPerfil() {
        return idPerfil;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
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

    public String getNovedades() {
        return novedades;
    }

    public void setNovedades(String novedades) {
        this.novedades = novedades;
    }

    public void initSesion() {
        fechaHoraInicio=sismain.getReloj().getDate()+" , "+sismain.getReloj().getHour();
        System.out.println("fechaHoraInicio");
    }
   
    public void finishSesion() throws Throwable{
        fechaHoraInicio=sismain.getReloj().getDate()+" , "+sismain.getReloj().getHour();
        guardarBD();
        super.finalize();
    }

    public long guardarBD() {
        ArrayList<String> valores= new ArrayList<>();
        valores.add(String.valueOf(idSesion));
        valores.add(fechaHoraInicio);
        valores.add(fechaHoraFin);
        valores.add(novedades);
        valores.add(String.valueOf(idPerfil));
        sismain.getControladorBD().aniadir(valores,"PERFIL",false);
        valores.clear();
        return idSesion;
    }
    
}
