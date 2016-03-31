/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Watch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Ignacio
 */
public class Cronometro {
    private int hs=0,min=0,seg=0;
        private JLabel labelCronometro;

    public void setLabelCronometro(JLabel labelCronometro) {
        this.labelCronometro = labelCronometro;
    }
        
        public void ejecutarCronometro(){

            Timer timer = new Timer(1000,new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                seg++;
                if(seg==60){
                    min++;
                    seg=0;
                }
                if(min==60){
                    hs++;
                    min=0;
                }
                String cronometro = String.format("%02d:%02d:%02d", hs, min ,seg);
                labelCronometro.setText(cronometro);
            }
        });
        timer.start();
        
    }
}

