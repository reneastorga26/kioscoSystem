/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;


import Controller.ControladorBD;
import Controller.ControladorDate;
import java.awt.Color;
import java.awt.image.ImageObserver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import model.CorreoElectronico;
import model.Domicilio;
import model.Persona;
import model.Telefono;
import sistemakiosco.sismain;
import sun.swing.DefaultLookup;
/**
 *
 * @author IgnacioMatias
 */
public class BCMCliente extends javax.swing.JFrame {

    private Cliente cliente;
    private Telefono telefono = new Telefono();
    private Domicilio domicilio = new Domicilio();
    private CorreoElectronico correoElectronico = new CorreoElectronico();
    private DefaultTableModel model;
    private boolean estado = true;
    private ArrayList<Object> auxiliarNuevosTelefonos = new ArrayList<>();
    private ArrayList<Object> auxiliarNuevosCorreosElectronicos = new ArrayList<>();
    private ArrayList<Object> auxiliarNuevosDomicilios = new ArrayList<>();
    private ArrayList<Object> auxiliarEliminarTelefonos = new ArrayList<>();
    private ArrayList<Object> auxiliarEliminarCorreosElectronicos = new ArrayList<>();
    private ArrayList<Object> auxiliarEliminarDomicilios = new ArrayList<>();
    /**
     * Creates new form ABMProducto
     */
    public BCMCliente(Cliente cliente) {
        initComponents();
        
        sismain.getControladorDate().iniciarCombos(comboDia, comboMes, comboAnio);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Datos del Cliente");
        this.cliente=cliente;
        this.txtDni.setEditable(false);
        this.txtNombre.setEditable(false);
        this.comboSexo.setEnabled(false);
        this.comboDia.setEnabled(false);
        this.comboMes.setEnabled(false);
        this.comboAnio.setEnabled(false);
        this.tablaDomicilio.setEnabled(false);
        this.tablaCorreoElectronico.setEnabled(false);
        this.tablaTelefono.setEnabled(false);
        this.btnGuardarModificacion.setText("Modificar Datos");
        UIManager.put("ComboBox.disabledBackground", new Color(222,222,222));
        UIManager.put("ComboBox.disabledForeground", new Color(18,30,49));
        this.btnNuevoDomicilio.setEnabled(false);
        this.btnNuevoTelefono.setEnabled(false);
        this.btnNuevoCorreo.setEnabled(false);
        this.btnEliminarEmails.setEnabled(false);
        this.btnEliminarDomicilio.setEnabled(false);
        this.btnEliminarTels.setEnabled(false);
        this.txtObservaciones.setEditable(false);
        
        this.tablaTelefono.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaTelefono.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablaTelefono.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        this.tablaDomicilio.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaDomicilio.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablaDomicilio.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        this.tablaCorreoElectronico.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaCorreoElectronico.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablaCorreoElectronico.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public JTable getTablaTelefono() {
        return tablaTelefono;
    }
    
    
    public void completarCampos(){
        completarDatosCliente();
        completarDomicilios();
        completarTelefonos();
        completarCorreosElectronicos();
    }
    
    public void completarDatosCliente(){
        txtDni.setText(cliente.getDni());
        txtNombre.setText(cliente.getNombreApellido());
        
        sismain.getControladorDate().darFormatoaComboBox(cliente.getFechaNacimiento(), comboDia, comboMes, comboAnio);
        comboSexo.removeAllItems();
        if(cliente.getSexo()== 'M') 
            comboSexo.addItem("Masculino");
        else
            comboSexo.addItem("Femenino");
        txtObservaciones.setText(cliente.getObservaciones());
    }
    
    public void completarDomicilios(){
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaDomicilio.getModel();
        Object [] fila = new Object[4];
                for(int i = 0; i<cliente.getDomicilios().size();i++){
                    fila[0] = cliente.getDomicilios().get(i).getIdDomicilio();
                    fila[1] = cliente.getDomicilios().get(i).getDireccion();
                    fila[2] = cliente.getDomicilios().get(i).getLocalidad();
                    fila[3] = cliente.getDomicilios().get(i).getProvincia();
                    modeloTabla.addRow(fila);
                    tablaDomicilio.setModel(modeloTabla);
                }
                cliente.getDomicilios().clear();
    }
    
    public void completarTelefonos(){
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaTelefono.getModel();
        Object [] fila = new Object[3];
                for(int i = 0; i<cliente.getTelefonos().size();i++){
                    fila[0] = cliente.getTelefonos().get(i).getIdTelefono();
                    fila[1] = cliente.getTelefonos().get(i).getNumero();
                    
                    if(cliente.getTelefonos().get(i).getMovil() == 'F'){
                       fila[2] = "Fijo"; 
                    }else{
                       fila[2] = "Móvil";  
                    }
                    modeloTabla.addRow(fila);
                    tablaTelefono.setModel(modeloTabla);
                }
                cliente.getTelefonos().clear();
    }
    
    public void completarCorreosElectronicos(){
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaCorreoElectronico.getModel();
        Object [] fila = new Object[2];
                for(int i = 0; i<cliente.getCorreosElectronicos().size();i++){
                    fila[0] = cliente.getCorreosElectronicos().get(i).getIdCorreoElectronico();
                    fila[1] = cliente.getCorreosElectronicos().get(i).getDireccion();
                    modeloTabla.addRow(fila);
                    tablaCorreoElectronico.setModel(modeloTabla);
                }
                cliente.getCorreosElectronicos().clear();
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
        txtNombre = new javax.swing.JTextField();
        btnNuevoTelefono = new javax.swing.JButton();
        txtDni = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        comboDia = new javax.swing.JComboBox<>();
        comboMes = new javax.swing.JComboBox<>();
        comboAnio = new javax.swing.JComboBox<>();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        btnEliminarEmails = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnGuardarModificacion = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

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
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Datos del Cliente");

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        btnNuevoTelefono.setBackground(new java.awt.Color(51, 51, 255));
        btnNuevoTelefono.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnNuevoTelefono.setForeground(java.awt.Color.white);
        btnNuevoTelefono.setText("Nuevo Telefono");
        btnNuevoTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoTelefonoActionPerformed(evt);
            }
        });

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
                "Id", "Numero", "Tipo"
            }
        ));
        jScrollPane3.setViewportView(tablaTelefono);

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("DOMICILIO:");

        tablaDomicilio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Direccion", "Localidad", "Provincia"
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
                "Id", "Direccion"
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

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("NOMBRE Y APELLIDO:");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("FECHA DE NACIMIENTO:");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SEXO:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("DNI:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(comboMes, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel20)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(comboAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel2)
                                                    .addComponent(jLabel6)
                                                    .addComponent(jLabel3)
                                                    .addComponent(jLabel5))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(comboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                                                        .addComponent(comboDia, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtDni, javax.swing.GroupLayout.Alignment.TRAILING)))))
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(0, 42, Short.MAX_VALUE)
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(btnEliminarTels, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                    .addComponent(btnNuevoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(18, 18, 18)))
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnEliminarEmails, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnNuevoCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(1, 1, 1))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 752, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnNuevoDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminarDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)))
                .addContainerGap())
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel20)
                                        .addComponent(comboMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(comboDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
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
                                        .addComponent(btnEliminarTels, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(4, 4, 4))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                .addGap(10, 10, 10)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel12)
                                        .addGap(151, 151, 151))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNuevoDomicilio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        jButton1.setBackground(new java.awt.Color(51, 0, 51));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Habilitar un cliente eliminado");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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

        btnSalir.setBackground(new java.awt.Color(153, 0, 0));
        btnSalir.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(153, 0, 0));
        btnEliminar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminar.setForeground(java.awt.Color.white);
        btnEliminar.setText("Eliminar Cliente");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarModificacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(btnGuardarModificacion)
                    .addComponent(btnSalir)
                    .addComponent(btnEliminar))
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
        domicilio.setIdDomicilio(Long.valueOf(String.valueOf(model.getValueAt(tablaDomicilio.getSelectedRow(),0))));
                
        model.removeRow(tablaDomicilio.getSelectedRow());
        domicilio.eliminarBD(cliente.getIdPersona(),true);
    }//GEN-LAST:event_btnEliminarDomicilioActionPerformed

    private void btnNuevoDomicilioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoDomicilioActionPerformed
        NDDomicilio dDomicilio= new NDDomicilio(this,
                true, (DefaultTableModel) tablaDomicilio.getModel());
        dDomicilio.setVisible(true);
    }//GEN-LAST:event_btnNuevoDomicilioActionPerformed

    private void comboAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAnioActionPerformed
        
        sismain.getControladorDate().corregirCombos(comboDia, comboMes, comboAnio);
    }//GEN-LAST:event_comboAnioActionPerformed

    private void comboMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMesActionPerformed
        sismain.getControladorDate().corregirCombos(comboDia, comboMes, comboAnio);
    }//GEN-LAST:event_comboMesActionPerformed

    private void btnNuevoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTelefonoActionPerformed
        NDTelefono dTelefono= new NDTelefono(this,
                true,(DefaultTableModel) tablaTelefono.getModel());
        dTelefono.setVisible(true);
    }//GEN-LAST:event_btnNuevoTelefonoActionPerformed

    private void btnNuevoCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCorreoActionPerformed
        NDCorreoElectronico dCorreo= new NDCorreoElectronico(this,
                true,(DefaultTableModel) tablaCorreoElectronico.getModel());
        dCorreo.setVisible(true);
    }//GEN-LAST:event_btnNuevoCorreoActionPerformed

    private void btnEliminarEmailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEmailsActionPerformed
        model = (DefaultTableModel)tablaCorreoElectronico.getModel();
        correoElectronico.setIdCorreoElectronico(Long.valueOf(String.valueOf(model.getValueAt(tablaCorreoElectronico.getSelectedRow(),0))));
                
        model.removeRow(tablaCorreoElectronico.getSelectedRow());
        correoElectronico.eliminarBD(cliente.getIdPersona(),true);
    }//GEN-LAST:event_btnEliminarEmailsActionPerformed

    private void btnEliminarTelsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTelsActionPerformed
        model = (DefaultTableModel)tablaTelefono.getModel();
        
        telefono.setIdTelefono(Long.valueOf(String.valueOf(model.getValueAt(tablaTelefono.getSelectedRow(),0))));
                
                
        model.removeRow(tablaTelefono.getSelectedRow());
        telefono.eliminarBD(cliente.getIdPersona(),true);
    }//GEN-LAST:event_btnEliminarTelsActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int i =JOptionPane.showConfirmDialog(this,
                "¿REALMENTE DESEA ELIMINAR EL CLIENTE?","Mensaje",JOptionPane.YES_NO_OPTION);
        if(i==0){
            cliente.deshabilitarBD();
            JOptionPane.showMessageDialog(
                            null, "EL CLIENTE SE HA ELIMINADO CORRECTAMENTE",
                            "Mensaje",JOptionPane.INFORMATION_MESSAGE);
        }
        
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void comboDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDiaActionPerformed
        
    }//GEN-LAST:event_comboDiaActionPerformed

    private void comboSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSexoActionPerformed

    private void btnGuardarModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificacionActionPerformed
        // TODO add your handling code here:
        
        if(estado){
        this.btnGuardarModificacion.setText("Guardar Modificación");
        this.txtDni.setEditable(true);
        this.txtNombre.setEditable(true);
        this.comboDia.setEnabled(true);
        this.comboSexo.setEnabled(true);
        this.comboMes.setEnabled(true);
        this.comboAnio.setEnabled(true);
        this.tablaTelefono.setEnabled(true);
        this.tablaDomicilio.setEnabled(true);
        this.tablaCorreoElectronico.setEnabled(true);
        //this.btnGuardarModificacion.setEnabled(true);
        this.btnNuevoDomicilio.setEnabled(true);
        this.btnNuevoTelefono.setEnabled(true);
        this.btnNuevoCorreo.setEnabled(true);
        this.btnEliminarEmails.setEnabled(true);
        this.btnEliminarDomicilio.setEnabled(true);
        this.btnEliminarTels.setEnabled(true);   
        this.txtObservaciones.setEditable(true);
        estado = false;
        
        }else{
        
        cliente.setDni(txtDni.getText());
        cliente.setNombreApellido(txtNombre.getText());
        cliente.setFechaNacimiento(sismain.getControladorDate().darFormatoStringOracle(
                        comboDia.getSelectedItem().toString(),
                        comboMes.getSelectedItem().toString(),
                        comboAnio.getSelectedItem().toString()));
        if(comboSexo.getSelectedIndex()==0) 
            cliente.setSexo('M');
        else
            cliente.setSexo('F');
        cliente.setObservaciones(txtObservaciones.getText());
        cliente.modificarBD();
        
        for(int i = 0; i<tablaDomicilio.getRowCount();i++){
            domicilio.setIdDomicilio(
                    Long.valueOf(String.valueOf(tablaDomicilio.getValueAt(i,0))));
            domicilio.setDireccion(
                    String.valueOf(tablaDomicilio.getValueAt(i,1)));
            domicilio.setLocalidad(
                    String.valueOf(tablaDomicilio.getValueAt(i, 2)));
            domicilio.setProvincia(
                    String.valueOf(tablaDomicilio.getValueAt(i, 3)));
            domicilio.setIdPersona(cliente.getIdPersona());
            domicilio.modificarBD(cliente.getIdPersona(),true);
        }
        
        
        for(int i = 0; i<tablaTelefono.getRowCount();i++){
            telefono.setIdTelefono(Long.valueOf(String.valueOf(tablaTelefono.getValueAt(i,0))));
            telefono.setNumero(String.valueOf(tablaTelefono.getValueAt(i,1)));
            telefono.setMovil(
                    String.valueOf(tablaTelefono.getValueAt(i, 2)).charAt(0));
            telefono.setIdPersona(cliente.getIdPersona());
            telefono.modificarBD(cliente.getIdPersona(),true);
        }
        
        
        for(int i = 0; i<tablaCorreoElectronico.getRowCount();i++){
            correoElectronico.setIdCorreoElectronico(Long.valueOf(String.valueOf(tablaCorreoElectronico.getValueAt(i,0))));
            correoElectronico.setDireccion(
                    String.valueOf(tablaCorreoElectronico.getValueAt(i,1)));
            correoElectronico.setIdPersona(cliente.getIdPersona());
            correoElectronico.modificarBD(cliente.getIdPersona(),true);
        }
        
        
        JOptionPane.showMessageDialog(null, "EL CLIENTE SE HA MODIFICADO CORRECTAMENTE","Mensaje",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarModificacionActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Buscar buscar = new Buscar("Cliente", "DNI", "NOMBRE Y APELLIDO", 'D', 1);
        buscar.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

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

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarDomicilio;
    private javax.swing.JButton btnEliminarEmails;
    private javax.swing.JButton btnEliminarTels;
    private javax.swing.JButton btnGuardarModificacion;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
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
