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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Fabricante;
import model.Producto;
import model.TipoProducto;
import sistemakiosco.sismain;

/**
 *
 * @author IgnacioMatias
 */
public class BCMProducto extends javax.swing.JFrame {

    private Producto producto = new Producto();
    private TipoProducto tipoProducto = new TipoProducto();
    private Fabricante fabricante = new Fabricante();
    private String cadenaIdProducto;
    private long idTipoProducto;
    private long idFabricante;
    ControladorBD control = sismain.getControladorBD();
    /**
     * Creates new form BCMProducto
     */
    public BCMProducto() {
        initComponents();
        txtCodigo.setEnabled(false);
        txtDescripcion.setEnabled(false);
        cmbTipoProducto.setEnabled(false);
        btnNuevoTipo.setEnabled(false);
        txtFabricante.setEnabled(false);
        txtPrecioUnitarioVenta.setEnabled(false);
        txtPrecioUnitarioCompra.setEnabled(false);
        txtStockActual.setEnabled(false);
        txtStockMinimo.setEnabled(false);
        txtPuntoPedido.setEnabled(false);
        txtStockMaximo.setEnabled(false);
        btnGuardarModificacion.setEnabled(false);
    }

    public void buscar(String dato){
        
    }
    
    public void dato(String idProducto){
        
        cadenaIdProducto = idProducto;
        txtCodigo.setText(cadenaIdProducto);
        completarDatos();
    }
    
    
    public void completarDatos(){
        
        try{
        ResultSet rs;
        
        rs = control.buscarRegistros("*", "PRODUCTO", "ID_PRODUCTO = " + cadenaIdProducto);
        while(rs.next()){
            txtDescripcion.setText(rs.getString("DESCRIPCION"));
            cmbTipoProducto.setSelectedItem(rs.getString("TIPO_PRODUCTO_ID_TIPO_PRODUCTO"));
            txtFabricante.setText(rs.getString("FABRICANTE_ID_FABRICANTE"));
            txtStockActual.setText(rs.getString("STOCK_ACTUAL"));
            txtStockMinimo.setText(rs.getString("STOCK_CRITICO_MINIMO"));
            txtPuntoPedido.setText(rs.getString("PUNTO_PEDIDO"));
            idTipoProducto = Long.valueOf(rs.getString("TIPO_PRODUCTO_ID_TIPO_PRODUCTO"));
            idFabricante = Long.valueOf(rs.getString("FABRICANTE_ID_FABRICANTE"));
            }
        }catch (SQLException ex) {
            Logger.getLogger(ACliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        completarPrecio();
        completarTipo();
        completarFabricante();
    }
    
    public void completarTipo(){
        
        try{
        ResultSet res;
        res = control.buscarRegistros("DESCRIPCION", "TIPO_PRODUCTO", "ID_TIPO_PRODUCTO = " + idTipoProducto);
        while(res.next()){
                //cmbTipoProducto.setSelectedItem(res.getString("DESCRIPCION"));
                cmbTipoProducto.addItem(res.getString("DESCRIPCION"));
            }
        }catch (SQLException ex) {
            Logger.getLogger(ACliente.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public void completarComboTipo(){
        
        try{
        ResultSet res;
        res = control.buscarRegistros("DESCRIPCION", "TIPO_PRODUCTO", "ID_TIPO_PRODUCTO > 0 ");
        while(res.next()){
                //cmbTipoProducto.setSelectedItem(res.getString("DESCRIPCION"));
                cmbTipoProducto.addItem(res.getString("DESCRIPCION"));
            }
        }catch (SQLException ex) {
            Logger.getLogger(ACliente.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public void completarFabricante(){
        
        try{
        ResultSet res;
        res = control.buscarRegistros("DESCRIPCION", "FABRICANTE", "ID_FABRICANTE = " + idFabricante);
        while(res.next()){
                txtFabricante.setText(res.getString("DESCRIPCION"));
            }
        }catch (SQLException ex) {
            Logger.getLogger(ACliente.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void completarPrecio(){
        
        try{
        ResultSet res;
        res = control.buscarRegistros("NUMERO", "PRECIO", "PRODUCTO_ID_PRODUCTO = " + cadenaIdProducto);
        while(res.next()){
                txtPrecioUnitarioVenta.setText(res.getString("NUMERO"));
            }
        }catch (SQLException ex) {
            Logger.getLogger(ACliente.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        txtDescripcion = new javax.swing.JTextField();
        txtPrecioUnitarioVenta = new javax.swing.JTextField();
        txtPrecioUnitarioCompra = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtStockActual = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtPuntoPedido = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtStockMinimo = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtStockMaximo = new javax.swing.JTextField();
        cmbTipoProducto = new javax.swing.JComboBox<>();
        btnNuevoTipo = new javax.swing.JButton();
        txtFabricante = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        btnModificarDatos = new javax.swing.JButton();
        btnGuardarModificacion = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Información del Producto");

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 23)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 255, 0));
        jLabel1.setText("Informacion del Producto");

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));

        txtDescripcion.setForeground(new java.awt.Color(0, 0, 102));

        txtPrecioUnitarioVenta.setForeground(new java.awt.Color(0, 0, 102));

        txtPrecioUnitarioCompra.setForeground(new java.awt.Color(0, 0, 102));

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("STOCK ACTUAL (U):");

        txtStockActual.setForeground(new java.awt.Color(0, 0, 102));

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("PUNTO DE PEDIDO (U):");

        txtPuntoPedido.setForeground(new java.awt.Color(0, 0, 102));

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("STOCK MINIMO (U):");

        txtStockMinimo.setForeground(new java.awt.Color(0, 0, 102));

        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("STOCK MAXIMO (U):");

        txtStockMaximo.setForeground(new java.awt.Color(0, 0, 102));

        btnNuevoTipo.setText("Nuevo Tipo");
        btnNuevoTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoTipoActionPerformed(evt);
            }
        });

        btnModificarDatos.setBackground(new java.awt.Color(51, 0, 51));
        btnModificarDatos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnModificarDatos.setForeground(java.awt.Color.white);
        btnModificarDatos.setText("Modificar Datos");
        btnModificarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarDatosActionPerformed(evt);
            }
        });

        btnGuardarModificacion.setBackground(new java.awt.Color(51, 0, 51));
        btnGuardarModificacion.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnGuardarModificacion.setForeground(java.awt.Color.white);
        btnGuardarModificacion.setText("Guardar Modificacion");
        btnGuardarModificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarModificacionActionPerformed(evt);
            }
        });

        btnEliminarProducto.setBackground(new java.awt.Color(153, 0, 0));
        btnEliminarProducto.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarProducto.setForeground(java.awt.Color.white);
        btnEliminarProducto.setText("Eliminar Producto");
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("TIPO DE PRODUCTO:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("FABRICANTE:");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("PRECIO UNITARIO DE VENTA ($):");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("PRECIO UNITARIO DE COMPRA ($):");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("DESCRIPCION:");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("CODIGO:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(btnModificarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnGuardarModificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(cmbTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(27, 27, 27)
                                    .addComponent(btnNuevoTipo))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(txtFabricante)
                                    .addGap(66, 66, 66)))
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioUnitarioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioUnitarioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(52, 52, 52))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel19))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtStockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStockActual, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtStockMaximo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPuntoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(110, 110, 110))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevoTipo)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPrecioUnitarioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecioUnitarioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtStockActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtPuntoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStockMaximo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnModificarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnGuardarModificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEliminarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnSalir.setBackground(new java.awt.Color(153, 0, 0));
        btnSalir.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnSalir.setForeground(java.awt.Color.white);
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jSeparator1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnSalir))
                .addGap(4, 4, 4)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarDatosActionPerformed
        // TODO add your handling code here:
        txtCodigo.setEnabled(true);
        txtDescripcion.setEnabled(true);
        cmbTipoProducto.setEnabled(true);
        cmbTipoProducto.removeAllItems();
        completarComboTipo();
        btnNuevoTipo.setEnabled(true);
        txtFabricante.setEnabled(true);
        txtPrecioUnitarioVenta.setEnabled(true);
        txtPrecioUnitarioCompra.setEnabled(true);
        txtStockActual.setEnabled(true);
        txtStockMinimo.setEnabled(true);
        txtPuntoPedido.setEnabled(true);
        txtStockMaximo.setEnabled(true);
        btnGuardarModificacion.setEnabled(true);
    }//GEN-LAST:event_btnModificarDatosActionPerformed

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnGuardarModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificacionActionPerformed
        // TODO add your handling code here:
        producto.setDescripcion(txtDescripcion.getText());
        producto.setStockActual(Integer.valueOf(txtStockActual.getText()));
        producto.setPuntoPedido(Integer.valueOf(txtPuntoPedido.getText()));
        producto.setStockCriticoMinimo(Integer.valueOf(txtStockMinimo.getText()));
        tipoProducto.setDescripcion(String.valueOf(cmbTipoProducto.getSelectedItem()));
        fabricante.setDescripcion(txtFabricante.getText());
        
        
        ArrayList<String> valoresProducto = new ArrayList<>();
        valoresProducto.add(producto.getDescripcion());
        valoresProducto.add(String.valueOf(producto.getStockActual()));
        valoresProducto.add(String.valueOf(producto.getStockCriticoMinimo()));
        valoresProducto.add(String.valueOf(producto.getPuntoPedido()));
        
        producto.modificarBD(valoresProducto, "PRODUCTO", "ID_PRODUCTO", cadenaIdProducto);
        
        ArrayList<String> valoresTipoProducto = new ArrayList<>();
        valoresTipoProducto.add(tipoProducto.getDescripcion());
        
        tipoProducto.modificarBD(valoresTipoProducto, "TIPO_PRODUCTO", "ID_TIPO_PRODUCTO", String.valueOf(idTipoProducto));
        
        ArrayList<String> valoresFabricante = new ArrayList<>();
        valoresFabricante.add(fabricante.getDescripcion());
        
        fabricante.modificarBD(valoresFabricante, "FABRICANTE", "ID_FABRICANTE", String.valueOf(idFabricante));
        
        JOptionPane.showMessageDialog(null, "EL PRODUCTO SE HA MODIFICADO CORRECTAMENTE","Mensaje",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnGuardarModificacionActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnNuevoTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTipoActionPerformed
        // TODO add your handling code here:
        NDTipoProducto tipoProd = new NDTipoProducto(this,
                true,(DefaultComboBoxModel) cmbTipoProducto.getModel());
        tipoProd.setVisible(true);
        tipoProd.setTitle("Nuevo Tipo de Producto");
    }//GEN-LAST:event_btnNuevoTipoActionPerformed

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
            java.util.logging.Logger.getLogger(BCMProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BCMProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BCMProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BCMProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BCMProducto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnGuardarModificacion;
    private javax.swing.JButton btnModificarDatos;
    private javax.swing.JButton btnNuevoTipo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cmbTipoProducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtFabricante;
    private javax.swing.JTextField txtPrecioUnitarioCompra;
    private javax.swing.JTextField txtPrecioUnitarioVenta;
    private javax.swing.JTextField txtPuntoPedido;
    private javax.swing.JTextField txtStockActual;
    private javax.swing.JTextField txtStockMaximo;
    private javax.swing.JTextField txtStockMinimo;
    // End of variables declaration//GEN-END:variables
}
