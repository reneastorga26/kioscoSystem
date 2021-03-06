/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;


import Controller.ControladorBD;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Cliente;
import model.Empleado;
import model.Producto;
import model.Proveedor;
import sistemakiosco.sismain;

/**
 *
 * @author IgnacioMatias
 */
public class Buscar extends javax.swing.JFrame {

    private DefaultTableModel modeloTabla;
    private TableRowSorter trsFiltro;
    private int valorSeleccion;
    private Cliente cliente = new Cliente();
    private Empleado empleado = new Empleado();
    private Proveedor proveedor = new Proveedor();
    private Producto producto = new Producto();
    /**
     * Creates new form BuscarCliente
     */
    public Buscar(String titulo, String parametro1, String parametro2, 
                        char estado, int opcion) {
        initComponents();
        this.setLocationRelativeTo(null);
        modeloTabla = (DefaultTableModel) tablaBuscar.getModel();
        this.modeloTabla.setColumnIdentifiers(new String[]{"",parametro1,parametro2});
        this.tablaBuscar.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaBuscar.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablaBuscar.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.tablaBuscar.getColumnModel().getColumn(1).setPreferredWidth(150);
        this.tablaBuscar.getColumnModel().getColumn(2).setPreferredWidth(350);
        this.tablaBuscar.setModel(modeloTabla);
        this.tablaBuscar.setRowSorter(trsFiltro);
        this.setTitle("Búsqueda de " + titulo);
        this.lblTitulo.setText("Búsqueda de " + titulo);
        this.jRadioButton1.setText(parametro1);
        this.jRadioButton1.setSelected(true);
        this.jRadioButton2.setText(parametro2);
        this.evaluar(opcion, estado);
        this.setVisible(true);
        this.btnAmpliarInfo.setText("Ampliar Informacion y Opciones sobre "+ 
                                    titulo + " seleccionado");
        
    }
    
    public void evaluar(int opcion, char estado){
        
        switch(opcion){
             case 1:
                cliente.buscarBD("C.PERSONA_ID_PERSONA", "ID_PERSONA", estado , modeloTabla);
                valorSeleccion = 1;
                 break;
             
             case 2:
                empleado.buscarBD("E.PERSONA_ID_PERSONA", "P.ID_PERSONA", estado, modeloTabla);
                valorSeleccion = 2;
                 break;
             
             case 3:
                proveedor.buscarBD("0", "ID_PROVEEDOR", estado, modeloTabla);
                valorSeleccion = 3;
                 break;
             
             case 4:
                producto.buscarBD("ID_PRODUCTO", "ID_PRODUCTO", estado, modeloTabla);
                valorSeleccion = 4;
                 break;
             }
    } 
    
    public void limpiarTabla(){
             for(int i=modeloTabla.getRowCount(); i>0;i--){
             modeloTabla.removeRow(i-1);
        }
    }
    
    
    public void filtro() {
        
        int columnaABuscar = 0;
        if (jRadioButton1.isSelected()) {
            columnaABuscar = 1; //DNI, CODIGO O CUIT
        }
        if (jRadioButton2.isSelected()) {
            columnaABuscar = 2; // NOMBRE Y APELLIDO, DESCRIPCION, RAZON SOCIAL
        }
        
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtBuscar.getText(), columnaABuscar));
        
    }
    
    public String seleccionarRegistro(){
        int modelRow = 0;
        String dato = "";
        int fila = tablaBuscar.getSelectedRow();
        if (fila >= 0){
        modelRow = tablaBuscar.convertRowIndexToModel(fila);
        dato = String.valueOf(modeloTabla.getValueAt(modelRow,0));
        
        }
        return dato;
    }
    
    public void ampliarInfoRegistro(int opcion){
        switch(opcion){
            case 1:
                 
        //BUSCAR CLIENTE
        
        
        String datoCliente = seleccionarRegistro();
        cliente.ampliarInfoBD(Long.valueOf(datoCliente));
        BCMCliente adminCliente = new BCMCliente(cliente);
        adminCliente.setVisible(true);
        adminCliente.completarCampos();
        
        break;
        
            case 2:
            
        //BUSCAR EMPLEADO
        
        
        String datoEmpleado = seleccionarRegistro();
        empleado.ampliarInfoBD(Long.valueOf(datoEmpleado));
        BCMEmpleado adminEmpleado = new BCMEmpleado(empleado, false);
        adminEmpleado.setVisible(true);
        adminEmpleado.completarCampos();
        break;
        
        
        
            case 3:
        //BUSCAR PROVEEDOR
        
        
        String datoProveedor = seleccionarRegistro();
        proveedor.ampliarInfoBD(Long.valueOf(datoProveedor));
        BCMProveedor adminProveedor = new BCMProveedor(proveedor);
        adminProveedor.setVisible(true);
        adminProveedor.completarCampos();
        break;
        
       
            case 4:
        //BUSCAR PRODUCTO
        
        
        String datoProducto = seleccionarRegistro();
        producto.ampliarInfoBD(Long.valueOf(datoProducto));
        BCProducto adminProducto = new BCProducto(producto);
        adminProducto.setVisible(true);
        adminProducto.completarCampos();
        break;
        
        
    }
    }
    
          
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaBuscar = new javax.swing.JTable();
        btnAmpliarInfo = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        checkEstado = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        lblTitulo.setFont(new java.awt.Font("Dialog", 0, 23)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Búsqueda de ");

        tablaBuscar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DNI", "APELLIDO Y NOMBRE"
            }
        ));
        jScrollPane1.setViewportView(tablaBuscar);

        btnAmpliarInfo.setBackground(new java.awt.Color(51, 0, 51));
        btnAmpliarInfo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnAmpliarInfo.setForeground(new java.awt.Color(255, 255, 255));
        btnAmpliarInfo.setText("Ampliar Informacion y Opciones sobre Cliente seleccionado");
        btnAmpliarInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmpliarInfoActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(153, 0, 0));
        jButton7.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton7.setForeground(java.awt.Color.white);
        jButton7.setText("Salir");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(0, 102, 204));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("BUSCAR: ");

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("jRadioButton1");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("jRadioButton2");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FILTRAR POR:");

        checkEstado.setText("Deshabilitados");
        checkEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkEstadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(checkEstado))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jLabel1)
                    .addComponent(checkEstado))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAmpliarInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(btnAmpliarInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        // TODO add your handling code here:
        
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                
                String cadena = (txtBuscar.getText().toUpperCase());
                txtBuscar.setText(cadena);
                filtro();
                System.out.println("Criterio de Busqueda : "+ cadena);
                
            }
            
        });
        trsFiltro = new TableRowSorter(modeloTabla);
        tablaBuscar.setRowSorter(trsFiltro);
       
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void btnAmpliarInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmpliarInfoActionPerformed
        // TODO add your handling code here:
        ampliarInfoRegistro(valorSeleccion);
        
    }//GEN-LAST:event_btnAmpliarInfoActionPerformed

    private void checkEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkEstadoActionPerformed
        // TODO add your handling code here:
       
        if(checkEstado.isSelected()){            
            limpiarTabla();
            evaluar(valorSeleccion, 'D');
        }else{
            limpiarTabla();
            evaluar(valorSeleccion, 'H');
        } 
    }//GEN-LAST:event_checkEstadoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Buscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Buscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Buscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Buscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Buscar().setVisible(true);
            }
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAmpliarInfo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox checkEstado;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    public javax.swing.JRadioButton jRadioButton1;
    public javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JLabel lblTitulo;
    public javax.swing.JTable tablaBuscar;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

    private void iniciarModeloFrame(int opcion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
