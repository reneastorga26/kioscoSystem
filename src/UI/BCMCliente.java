/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;


import Controller.ControladorBD;
import Controller.ControladorDate;
import java.awt.image.ImageObserver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
//import java.util.Calendar;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import model.CorreoElectronico;
import model.Domicilio;
import model.Telefono;
/**
 *
 * @author IgnacioMatias
 */
public class BCMCliente extends javax.swing.JFrame {

    private Cliente cliente = new Cliente ();
    private Telefono telefono = new Telefono();
    private Domicilio domicilio = new Domicilio();
    private CorreoElectronico correoElectronico = new CorreoElectronico();
    private ControladorDate controladorDate = new ControladorDate();
    private ControladorDate controladorDate1 = new ControladorDate();
    private DefaultTableModel model;
    private String cadenaIdPersona;
    
    /**
     * Creates new form ABMProducto
     */
    public BCMCliente() {
        initComponents();
        
        controladorDate.iniciarCombos(comboDia, comboMes, comboAnio);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.txtDni.setEnabled(false);
        this.txtNombre.setEnabled(false);
        this.comboDia.setEnabled(false);
        this.comboMes.setEnabled(false);
        this.comboAnio.setEnabled(false);
        this.tablaDomicilio.setEnabled(false);
        this.tablaCorreoElectronico.setEnabled(false);
        this.btnGuardarModificacion.setEnabled(false);
        this.btnNuevoDomicilio.setEnabled(false);
        this.btnNuevoTelefono.setEnabled(false);
        this.btnNuevoCorreo.setEnabled(false);
        this.btnEliminarEmails.setEnabled(false);
        this.btnEliminarDomicilio.setEnabled(false);
        this.btnEliminarTels.setEnabled(false);
    }

    public JTable getTablaTelefono() {
        return tablaTelefono;
    }
    
    public void dato(long idPersona){
        ControladorBD control = new ControladorBD(); 
        ResultSet rs;
        cadenaIdPersona = String.valueOf(idPersona);
        try{
            rs = control.buscarRegistrosSinTabla("DNI", "PERSONA", "ID_PERSONA = " + cadenaIdPersona);
            while(rs.next()){
                txtDni.setText(rs.getString("DNI"));
                completarNombre();
                completarFechaNac();
                completarDomicilios();
                completarCorreosElectronicos();
                completarTelefonos();
            }
        }catch (SQLException ex) {
            Logger.getLogger(ACliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
    public void completarNombre(){
        ControladorBD controlador = new ControladorBD();
        ResultSet res;
        String dni = txtDni.getText();
        String condicion = "DNI = " + dni;
        String nombre;
        try{
        res = controlador.buscarRegistrosSinTabla("NOMBRE_APELLIDO", "PERSONA", condicion);
        while(res.next()){
                    nombre = res.getString("NOMBRE_APELLIDO");
                    txtNombre.setText(nombre);
                    System.out.println(nombre);
                    
        }
        }catch (SQLException ex) {
            Logger.getLogger(ACliente.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
    public void completarFechaNac(){
        ControladorBD controlador = new ControladorBD();
        ResultSet res;
        String dni = txtDni.getText();
        String condicion = "DNI = " + dni;
        String fechaNac;
        try{
        res = controlador.buscarRegistrosSinTabla("FECHA_NAC", "PERSONA", condicion);
        while(res.next()){
                    fechaNac = res.getString("FECHA_NAC");
                    controladorDate1.darFormatoaComboBox(fechaNac,comboDia,comboMes,comboAnio);
        }
        }catch (SQLException ex) {
            Logger.getLogger(ACliente.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
     
    public void completarDomicilios(){
        ControladorBD controlador = new ControladorBD();
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaDomicilio.getModel();
        ResultSet res;
        String direccion;
        String localidad;
        String provincia;
        try{
        res = controlador.buscarRegistrosSinTabla("*", "DOMICILIO D", "D.PERSONA_ID_PERSONA = " + cadenaIdPersona);
        while(res.next()){
                    direccion = res.getString("DIRECCION");
                    localidad = res.getString("LOCALIDAD");
                    provincia = res.getString("PROVINCIA");
                    Object [] fila = new Object[3];
                    fila[0] = direccion;
                    fila[1] = localidad;
                    fila[2] = provincia;
                    modeloTabla.addRow(fila);
                    tablaDomicilio.setModel(modeloTabla);
        }
        }catch (SQLException ex) {
            Logger.getLogger(ACliente.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void completarCorreosElectronicos(){
        ControladorBD controlador = new ControladorBD();
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaCorreoElectronico.getModel();
        ResultSet res;
        String direccion;
        try{
        res = controlador.buscarRegistrosSinTabla("DIRECCION", "CORREOELECTRONICO", "PERSONA_ID_PERSONA = " + cadenaIdPersona);
        while(res.next()){
                    direccion = res.getString("DIRECCION");
                    Object [] fila = new Object[1];
                    fila[0] = direccion;
                    modeloTabla.addRow(fila);
                    tablaCorreoElectronico.setModel(modeloTabla);
        }
        }catch (SQLException ex) {
            Logger.getLogger(ACliente.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void completarTelefonos(){
        ControladorBD controlador = new ControladorBD();
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaTelefono.getModel();
        ResultSet res;
        String telefono;
        try{
        res = controlador.buscarRegistrosSinTabla("NUMERO", "TELEFONO", "PERSONA_ID_PERSONA = " + cadenaIdPersona);
        while(res.next()){
                    telefono = res.getString("NUMERO");
                    Object [] fila = new Object[1];
                    fila[0] = telefono;
                    modeloTabla.addRow(fila);
                    tablaCorreoElectronico.setModel(modeloTabla);
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

        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnNuevoTelefono = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        comboDia = new javax.swing.JComboBox<>();
        comboMes = new javax.swing.JComboBox<>();
        comboAnio = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        comboSexo = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        btnEliminarTels = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaTelefono = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDomicilio = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        btnNuevoDomicilio = new javax.swing.JButton();
        btnEliminarDomicilio = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaCorreoElectronico = new javax.swing.JTable();
        btnNuevoCorreo = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        btnModificar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        btnEliminarEmails = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnGuardarModificacion = new javax.swing.JButton();

        jLabel7.setText("jLabel7");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setForeground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 23)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 255, 0));
        jLabel1.setText("Datos del Cliente");

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("APELLIDO Y NOMBRE:");

        btnNuevoTelefono.setBackground(new java.awt.Color(51, 51, 255));
        btnNuevoTelefono.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnNuevoTelefono.setForeground(java.awt.Color.white);
        btnNuevoTelefono.setText("Nuevo Telefono");
        btnNuevoTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoTelefonoActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("DNI:");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("FECHA NACIMIENTO :");

        jLabel19.setBackground(new java.awt.Color(0, 0, 0));
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("/");

        jLabel20.setBackground(new java.awt.Color(0, 0, 0));
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("/");

        comboDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDiaActionPerformed(evt);
            }
        });

        comboMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMesActionPerformed(evt);
            }
        });

        comboAnio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAnioActionPerformed(evt);
            }
        });

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("SEXO:");

        comboSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));
        comboSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSexoActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("TELEFONOS:");

        btnEliminarTels.setBackground(new java.awt.Color(255, 102, 0));
        btnEliminarTels.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarTels.setForeground(java.awt.Color.white);
        btnEliminarTels.setText("Eliminar Telefonos Seleccionados");
        btnEliminarTels.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTelsActionPerformed(evt);
            }
        });

        tablaTelefono.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero", "Tipo"
            }
        ));
        jScrollPane3.setViewportView(tablaTelefono);
        if (tablaTelefono.getColumnModel().getColumnCount() > 0) {
            tablaTelefono.getColumnModel().getColumn(1).setHeaderValue("Tipo");
        }

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("DOMICILIO:");

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

        btnEliminarDomicilio.setBackground(new java.awt.Color(255, 102, 0));
        btnEliminarDomicilio.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarDomicilio.setForeground(java.awt.Color.white);
        btnEliminarDomicilio.setText("Eliminar Domicilios Seleccionados");
        btnEliminarDomicilio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarDomicilioActionPerformed(evt);
            }
        });

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("CORREOS ELECTRONICOS:");

        tablaCorreoElectronico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Direccion"
            }
        ));
        jScrollPane5.setViewportView(tablaCorreoElectronico);

        btnNuevoCorreo.setBackground(new java.awt.Color(51, 51, 255));
        btnNuevoCorreo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnNuevoCorreo.setForeground(java.awt.Color.white);
        btnNuevoCorreo.setText("Nuevo Correo Electronico");
        btnNuevoCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCorreoActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(51, 0, 51));
        btnModificar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnModificar.setForeground(java.awt.Color.white);
        btnModificar.setText("Modificar Datos");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(153, 0, 0));
        btnSalir.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnSalir.setForeground(java.awt.Color.white);
        btnSalir.setText("Cancelar y Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        txtObservaciones.setColumns(20);
        txtObservaciones.setRows(5);
        jScrollPane2.setViewportView(txtObservaciones);

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("OBSERVACIONES:");

        btnEliminarEmails.setBackground(new java.awt.Color(255, 102, 0));
        btnEliminarEmails.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarEmails.setForeground(java.awt.Color.white);
        btnEliminarEmails.setText("Eliminar Correos Seleccionados");
        btnEliminarEmails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEmailsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator3)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel10)
                                        .addGap(22, 22, 22)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnEliminarTels, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addComponent(btnNuevoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel9)
                                                    .addComponent(jLabel8))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(comboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                                        .addComponent(comboDia, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(6, 6, 6)
                                                        .addComponent(jLabel19)
                                                        .addGap(6, 6, 6)
                                                        .addComponent(comboMes, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel20)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(comboAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addComponent(jLabel3)
                                                            .addComponent(jLabel2))
                                                        .addGap(25, 25, 25)
                                                        .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(18, 18, 18)))
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel13)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGap(14, 14, 14)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnEliminarEmails, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                .addComponent(btnNuevoCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(16, 16, 16)))))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnEliminarDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnNuevoDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 764, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator4)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(17, 17, 17)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20)
                                    .addComponent(comboDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(comboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(2, 2, 2)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        .addComponent(btnNuevoTelefono)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnEliminarTels, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel12)
                                        .addGap(151, 151, 151))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        .addComponent(btnNuevoCorreo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnEliminarEmails, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)))))))
                .addGap(7, 7, 7)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevoDomicilio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButton1.setBackground(new java.awt.Color(51, 0, 51));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Habilitar un cliente eliminado");

        btnGuardarModificacion.setBackground(new java.awt.Color(51, 0, 51));
        btnGuardarModificacion.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnGuardarModificacion.setForeground(java.awt.Color.white);
        btnGuardarModificacion.setText("Guardar Modificacion");
        btnGuardarModificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarModificacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1)
                        .addGap(330, 330, 330)
                        .addComponent(btnGuardarModificacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1)
                    .addComponent(btnGuardarModificacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarDomicilioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarDomicilioActionPerformed
        model = (DefaultTableModel)tablaDomicilio.getModel();
        model.removeRow(tablaDomicilio.getSelectedRow()); 
    }//GEN-LAST:event_btnEliminarDomicilioActionPerformed

    private void btnNuevoDomicilioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoDomicilioActionPerformed
        NDDomicilio dDomicilio= new NDDomicilio(this,
                true, (DefaultTableModel) tablaDomicilio.getModel());
        dDomicilio.setVisible(true);
    }//GEN-LAST:event_btnNuevoDomicilioActionPerformed

    private void comboAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAnioActionPerformed
        
        controladorDate.corregirCombos(comboDia, comboMes, comboAnio);
    }//GEN-LAST:event_comboAnioActionPerformed

    private void comboMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMesActionPerformed
        controladorDate.corregirCombos(comboDia, comboMes, comboAnio);
    }//GEN-LAST:event_comboMesActionPerformed

    private void btnNuevoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTelefonoActionPerformed
        NDTelefono dTelefono= new NDTelefono(this,
                true,(DefaultTableModel) tablaTelefono.getModel());
        dTelefono.setVisible(true);
    }//GEN-LAST:event_btnNuevoTelefonoActionPerformed

    private void btnNuevoCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCorreoActionPerformed
        NDObraSocial dCorreo= new NDObraSocial(this,
                true,(DefaultTableModel) tablaCorreoElectronico.getModel());
        dCorreo.setVisible(true);
    }//GEN-LAST:event_btnNuevoCorreoActionPerformed

    private void btnEliminarEmailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEmailsActionPerformed
        model = (DefaultTableModel)tablaCorreoElectronico.getModel();
        model.removeRow(tablaCorreoElectronico.getSelectedRow()); 
    }//GEN-LAST:event_btnEliminarEmailsActionPerformed

    private void btnEliminarTelsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTelsActionPerformed
        model = (DefaultTableModel)tablaTelefono.getModel();
        model.removeRow(tablaTelefono.getSelectedRow()); 
    }//GEN-LAST:event_btnEliminarTelsActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        this.txtDni.setEnabled(true);
        this.txtNombre.setEnabled(true);
        this.comboDia.setEnabled(true);
        this.comboMes.setEnabled(true);
        this.comboAnio.setEnabled(true);
        this.tablaDomicilio.setEnabled(true);
        this.tablaCorreoElectronico.setEnabled(true);
        this.btnGuardarModificacion.setEnabled(true);
        this.btnNuevoDomicilio.setEnabled(true);
        this.btnNuevoTelefono.setEnabled(true);
        this.btnNuevoCorreo.setEnabled(true);
        this.btnEliminarEmails.setEnabled(true);
        this.btnEliminarDomicilio.setEnabled(true);
        this.btnEliminarTels.setEnabled(true);   
        
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void comboDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDiaActionPerformed
        
    }//GEN-LAST:event_comboDiaActionPerformed

    private void comboSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSexoActionPerformed

    private void btnGuardarModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarModificacionActionPerformed

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
            java.util.logging.Logger.getLogger(BCMCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BCMCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BCMCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BCMCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new BCMCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminarDomicilio;
    private javax.swing.JButton btnEliminarEmails;
    private javax.swing.JButton btnEliminarTels;
    private javax.swing.JButton btnGuardarModificacion;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevoCorreo;
    private javax.swing.JButton btnNuevoDomicilio;
    private javax.swing.JButton btnNuevoTelefono;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> comboAnio;
    private javax.swing.JComboBox<String> comboDia;
    private javax.swing.JComboBox<String> comboMes;
    private javax.swing.JComboBox<String> comboSexo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tablaCorreoElectronico;
    private javax.swing.JTable tablaDomicilio;
    private javax.swing.JTable tablaTelefono;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextArea txtObservaciones;
    // End of variables declaration//GEN-END:variables
}
