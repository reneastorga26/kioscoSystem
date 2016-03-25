/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Controller.ControladorBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.CorreoElectronico;
import model.Domicilio;
import model.Proveedor;
import model.Telefono;
import sistemakiosco.sismain;

/**
 *
 * @author IgnacioMatias
 */
public class AProveedor extends javax.swing.JFrame {

    
    private Proveedor proveedor = new Proveedor();
    private Telefono telefono = new Telefono();
    private Domicilio domicilio = new Domicilio();
    private CorreoElectronico correoElectronico = new CorreoElectronico();
    private DefaultTableModel model;
    ControladorBD control = sismain.getControladorBD();
    /**
     * Creates new form ABMProducto
     */
    public AProveedor() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCuit = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        btnComprobar = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDomicilio = new javax.swing.JTable();
        btnNuevoDomicilio = new javax.swing.JButton();
        btnEliminarDomicilios = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaCorreoElectronico = new javax.swing.JTable();
        btnNuevoCorreo = new javax.swing.JButton();
        btnEliminarCorreos = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaTelefono = new javax.swing.JTable();
        btnNuevoTelefono = new javax.swing.JButton();
        btnEliminarTelefonos = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nuevo Proveedor");

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setForeground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 23)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 255, 0));
        jLabel1.setText("Datos del Nuevo Proveedor");

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("CUIT:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("RAZON SOCIAL:");

        btnGuardar.setBackground(new java.awt.Color(0, 153, 0));
        btnGuardar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnGuardar.setForeground(java.awt.Color.white);
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(153, 0, 0));
        btnSalir.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnSalir.setForeground(java.awt.Color.white);
        btnSalir.setText("Cancelar y Salir");

        txtObservaciones.setColumns(20);
        txtObservaciones.setRows(5);
        jScrollPane2.setViewportView(txtObservaciones);

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("OBSERVACIONES:");

        btnComprobar.setBackground(new java.awt.Color(51, 0, 51));
        btnComprobar.setForeground(new java.awt.Color(255, 255, 255));
        btnComprobar.setText("Comprobar");
        btnComprobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobarActionPerformed(evt);
            }
        });

        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("DOMICILIOS:");

        tablaDomicilio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Direccion", "Localidad", "Provincia"
            }
        ));
        jScrollPane4.setViewportView(tablaDomicilio);

        btnNuevoDomicilio.setBackground(new java.awt.Color(51, 51, 255));
        btnNuevoDomicilio.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnNuevoDomicilio.setForeground(java.awt.Color.white);
        btnNuevoDomicilio.setText("Nuevo Domicilio");
        btnNuevoDomicilio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoDomicilioActionPerformed(evt);
            }
        });

        btnEliminarDomicilios.setBackground(new java.awt.Color(255, 102, 0));
        btnEliminarDomicilios.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarDomicilios.setForeground(java.awt.Color.white);
        btnEliminarDomicilios.setText("Eliminar Domicilios Seleccionados");
        btnEliminarDomicilios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarDomiciliosActionPerformed(evt);
            }
        });

        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("CORREOS ELECTRONICOS:");

        tablaCorreoElectronico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "..."
            }
        ));
        jScrollPane6.setViewportView(tablaCorreoElectronico);

        btnNuevoCorreo.setBackground(new java.awt.Color(51, 51, 255));
        btnNuevoCorreo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnNuevoCorreo.setForeground(java.awt.Color.white);
        btnNuevoCorreo.setText("Nuevo Correo Electronico");
        btnNuevoCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCorreoActionPerformed(evt);
            }
        });

        btnEliminarCorreos.setBackground(new java.awt.Color(255, 102, 0));
        btnEliminarCorreos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarCorreos.setForeground(java.awt.Color.white);
        btnEliminarCorreos.setText("Eliminar Correos Seleccionados");
        btnEliminarCorreos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCorreosActionPerformed(evt);
            }
        });

        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel28.setText("TELEFONOS:");

        tablaTelefono.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "..."
            }
        ));
        jScrollPane7.setViewportView(tablaTelefono);

        btnNuevoTelefono.setBackground(new java.awt.Color(51, 51, 255));
        btnNuevoTelefono.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnNuevoTelefono.setForeground(java.awt.Color.white);
        btnNuevoTelefono.setText("Nuevo Telefono");
        btnNuevoTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoTelefonoActionPerformed(evt);
            }
        });

        btnEliminarTelefonos.setBackground(new java.awt.Color(255, 102, 0));
        btnEliminarTelefonos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarTelefonos.setForeground(java.awt.Color.white);
        btnEliminarTelefonos.setText("Eliminar Telefonos Seleccionados");
        btnEliminarTelefonos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTelefonosActionPerformed(evt);
            }
        });

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel28)
                                .addComponent(jLabel24)))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(btnNuevoDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnEliminarDomicilios)))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(txtCuit, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnComprobar))
                                .addComponent(txtRazonSocial, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btnNuevoTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarTelefonos, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel27)
                            .addGap(18, 18, 18)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(btnNuevoCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminarCorreos, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(29, 29, 29)
                            .addComponent(btnSalir)))
                    .addComponent(jLabel15))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator4))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtCuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnComprobar))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnEliminarDomicilios, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnNuevoDomicilio))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNuevoTelefono)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarTelefonos)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNuevoCorreo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarCorreos)
                .addGap(27, 27, 27)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(51, 0, 51));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Habilitar un proveedor eliminado");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addGap(5, 5, 5)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnComprobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobarActionPerformed
        // TODO add your handling code here:
        int i=0;
        String cadena = txtCuit.getText();
        ArrayList<String> datos = new ArrayList<>();
             
            datos = proveedor.buscarBD("CUIT", null);
            Iterator iter = datos.iterator();
            while (iter.hasNext() && i<datos.size()){
             System.out.println(iter.next());
             if(datos.get(i).equals(cadena))   
             JOptionPane.showMessageDialog(null, "EL CUIT INGRESADO YA EXISTE","Mensaje",JOptionPane.INFORMATION_MESSAGE);
             i++;
            }
    }//GEN-LAST:event_btnComprobarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        proveedor.setCuit(txtCuit.getText());
        proveedor.setRazonSocial(txtRazonSocial.getText());
        proveedor.setObservaciones(txtObservaciones.getText());
        
        //GUARDAR DATOS EN BD
        long idProveedor=proveedor.guardarBD();
        
        
        for(int i = 0; i<tablaTelefono.getRowCount();i++){
            telefono.setNumero(String.valueOf(tablaTelefono.getValueAt(i,0)));
            telefono.setMovil(
                    String.valueOf(tablaTelefono.getValueAt(i, 1)).charAt(0));
            telefono.setIdProveedor(idProveedor);
            telefono.setIdPersona(0);
            telefono.guardarBD();
        }   
        
        for(int i = 0; i<tablaDomicilio.getRowCount();i++){
            domicilio.setDireccion(
                    String.valueOf(tablaDomicilio.getValueAt(i,0)));
            domicilio.setLocalidad(
                    String.valueOf(tablaDomicilio.getValueAt(i, 1)));
            domicilio.setProvincia(
                    String.valueOf(tablaDomicilio.getValueAt(i, 2)));
            domicilio.setIdProveedor(idProveedor);
            domicilio.setIdPersona(0);
            domicilio.guardarBD();
        }   
        
        for(int i = 0; i<tablaCorreoElectronico.getRowCount();i++){
            correoElectronico.setDireccion(
                    String.valueOf(tablaCorreoElectronico.getValueAt(i,0)));
            correoElectronico.setIdPersona(idProveedor);
            correoElectronico.setIdPersona(0);
            correoElectronico.guardarBD();
        }   
        JOptionPane.showMessageDialog(null, "EL PROVEEDOR SE HA REGISTRADO CORRECTAMENTE","Mensaje",JOptionPane.INFORMATION_MESSAGE);
                                           
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoDomicilioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoDomicilioActionPerformed
        NDDomicilio dDomicilio= new NDDomicilio(this,
            true,(DefaultTableModel) tablaDomicilio.getModel());
        dDomicilio.setVisible(true);
    }//GEN-LAST:event_btnNuevoDomicilioActionPerformed

    private void btnEliminarDomiciliosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarDomiciliosActionPerformed
        model = (DefaultTableModel)tablaDomicilio.getModel();
        model.removeRow(tablaDomicilio.getSelectedRow());
    }//GEN-LAST:event_btnEliminarDomiciliosActionPerformed

    private void btnNuevoCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCorreoActionPerformed
        // TODO add your handling code here:
        NDCorreoElectronico dCorreo = new NDCorreoElectronico(this,
            true,(DefaultTableModel) tablaCorreoElectronico.getModel());
        dCorreo.setVisible(true);
    }//GEN-LAST:event_btnNuevoCorreoActionPerformed

    private void btnEliminarCorreosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCorreosActionPerformed
        // TODO add your handling code here:
        model = (DefaultTableModel)tablaCorreoElectronico.getModel();
        model.removeRow(tablaCorreoElectronico.getSelectedRow());
    }//GEN-LAST:event_btnEliminarCorreosActionPerformed

    private void btnNuevoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoTelefonoActionPerformed

    private void btnEliminarTelefonosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTelefonosActionPerformed
        // TODO add your handling code here:
        model = (DefaultTableModel)tablaTelefono.getModel();
        model.removeRow(tablaTelefono.getSelectedRow());
    }//GEN-LAST:event_btnEliminarTelefonosActionPerformed

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
            java.util.logging.Logger.getLogger(AProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AProveedor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnComprobar;
    private javax.swing.JButton btnEliminarCorreos;
    private javax.swing.JButton btnEliminarDomicilios;
    private javax.swing.JButton btnEliminarTelefonos;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevoCorreo;
    private javax.swing.JButton btnNuevoDomicilio;
    private javax.swing.JButton btnNuevoTelefono;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable tablaCorreoElectronico;
    private javax.swing.JTable tablaDomicilio;
    private javax.swing.JTable tablaTelefono;
    private javax.swing.JTextField txtCuit;
    private javax.swing.JTextArea txtObservaciones;
    private javax.swing.JTextField txtRazonSocial;
    // End of variables declaration//GEN-END:variables
}
