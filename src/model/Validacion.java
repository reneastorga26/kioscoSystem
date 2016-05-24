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
public class Validacion {
 
    private void dniKeyTyped(java.awt.event.KeyEvent evt) {                             
           char c = evt.getKeyChar();
           if (!(Character.isDigit(c) ||
              (c == java.awt.event.KeyEvent.VK_BACK_SPACE) ||
              (c == java.awt.event.KeyEvent.VK_DELETE))) {
                evt.consume();
           }
    }
    
    private void nombreKeyTyped(java.awt.event.KeyEvent evt) {                                
           char c = evt.getKeyChar();
           if (!(Character.isLetter(c) ||
              (c == java.awt.event.KeyEvent.VK_SPACE) ||
              (c == java.awt.event.KeyEvent.VK_BACK_SPACE) ||
              (c == java.awt.event.KeyEvent.VK_DELETE))) {
                evt.consume();
              }
      
    }
    
        
        
}
