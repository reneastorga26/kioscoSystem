/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author CX
 */
public class Turno {
    
        private int idTurno;
        private char diaSemana;
        private String inicioTurno;
        private String finTurno;
        private Empleado idEmpleado;
        
        
        public Turno(){
            
        }
        
        public Turno(int idTurno, char diaSemana, String inicioTurno,
                String finTurno, Empleado idEmpleado){
              this.idTurno = idTurno;
              this.diaSemana = diaSemana;
              this.inicioTurno = inicioTurno;
              this.finTurno = finTurno;
        }
        
        
    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public char getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(char diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getInicioTurno() {
        return inicioTurno;
    }

    public void setInicioTurno(String inicioTurno) {
        this.inicioTurno = inicioTurno;
    }

    public String getFinTurno() {
        return finTurno;
    }

    public void setFinTurno(String finTurno) {
        this.finTurno = finTurno;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    
        
        
    
}
