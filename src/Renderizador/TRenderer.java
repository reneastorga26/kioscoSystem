/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Renderizador;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Ignacio
 */
public class TRenderer extends DefaultTableCellRenderer{
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        switch(Integer.parseInt(table.getValueAt(row, table.getColumnCount()-1).toString())){
            case 0: setForeground(new Color(255,255,255)); setBackground(new Color(0,51,51));break; 
            case 1: setForeground(new Color(0,0,0)); setBackground(new Color(0,255,0)); break;
            case 2: setForeground(new Color(0,0,0)); setBackground(new Color(255,0,0)); break;
        }
        
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
    }

    
}
