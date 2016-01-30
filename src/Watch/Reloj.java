
package Watch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JLabel;
import javax.swing.Timer;
//import kioscosystem.KioscoSystem;

public class Reloj {
    private Calendar calendario;
    private int dia, mes, año, segundos, minutos, hora;
    private JLabel label;
    
    public Reloj(JLabel label){
        this.label=label;
    }
    
    public void ejecutarReloj(){
        calendario= new GregorianCalendar();
        segundos=calendario.get(Calendar.SECOND);
        Timer timer = new Timer(1000,new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                Date actual = new Date();
                calendario.setTime(actual);
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = (calendario.get(Calendar.MONTH)+1);
                año = calendario.get(Calendar.YEAR);
                segundos = calendario.get(Calendar.SECOND);
                minutos = calendario.get(Calendar.MINUTE);
                hora = calendario.get(Calendar.HOUR_OF_DAY);
                String hour = String.format("%02d : %02d : %02d", hora, minutos,segundos);
                String date = String.format("%02d/%02d/%02d", dia, mes, año);
                label.setText("Fecha Actual: "+date+ " Hora Actual: " + hour );
            }
        });
        timer.start();
    }
}
