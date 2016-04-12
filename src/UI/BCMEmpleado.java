/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Controller.ControladorBD;
import Controller.ControladorDate;
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
import model.Empleado;
import model.Familiar;
import model.ObraSocial;
import model.RelacionEmpleadoObraSocial;
import model.Telefono;
import sistemakiosco.sismain;

/**
 *
 * @author IgnacioMatias
 */
public class BCMEmpleado extends javax.swing.JFrame {

    
    private Empleado empleado;
    private Telefono telefono = new Telefono();
    private Domicilio domicilio = new Domicilio();
    private CorreoElectronico correoElectronico = new CorreoElectronico();
    private Familiar familiar = new Familiar();
    private RelacionEmpleadoObraSocial relacionEmpleadoObraSocial = new RelacionEmpleadoObraSocial();
    private ObraSocial obraSocial = new ObraSocial();
    private DefaultTableModel model;
    private String cadenaIdPersona;
    private int filaSeleccionada;
    private boolean estado = true;
    /**
     * Creates new form ABMProducto
     */
    public BCMEmpleado(Empleado empleado, boolean estadoLiquidacion) {
        initComponents();
        sismain.getControladorDate().iniciarCombos(comboDia, comboMes, comboAnio);
        sismain.getControladorDate().iniciarCombos(comboDia1, comboMes1, comboAnio1);
        sismain.getControladorDate().formatoHora(comboHoraEntrada);
        sismain.getControladorDate().formatoHora(comboHoraSalida);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Información del Empleado");
        this.empleado = empleado;
        this.btnNuevoFamiliar.setEnabled(false);
        this.btnModificarFamiliar.setEnabled(false);
        this.btnEliminarFamiliar.setEnabled(false);
        this.txtDni.setEditable(false);
        this.txtNombre.setEditable(false);
        this.txtCuil.setEditable(false);
        this.comboDia.setEnabled(false);
        this.comboMes.setEnabled(false);
        this.comboAnio.setEnabled(false);
        this.txaObservaciones.setEnabled(false);
        this.tablaDomicilio.setEnabled(false);
        this.tablaCorreoElectronico.setEnabled(false);
        this.comboSexo.setEnabled(false);
        this.comboTurno.setEnabled(false);
        this.comboDia1.setEnabled(false);
        this.comboMes1.setEnabled(false);
        this.comboAnio1.setEnabled(false);
        this.tablaObraSocial.setEnabled(false);
        this.tablaFamiliares.setEnabled(false);
        this.btnGuardarModificacion.setText("Modificar Datos");
        this.btnNuevoDomicilio.setEnabled(false);
        this.btnNuevoTelefono.setEnabled(false);
        this.btnNuevoCorreo.setEnabled(false);
        this.btnNuevaObraSocial.setEnabled(false);
        this.btnEliminarCorreos.setEnabled(false);
        this.btnEliminarDomicilios.setEnabled(false);
        this.btnEliminarObrasSociales.setEnabled(false);
        this.btnEliminarTelefono.setEnabled(false);
        
        this.tablaTelefono.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaTelefono.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablaTelefono.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        this.tablaDomicilio.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaDomicilio.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablaDomicilio.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        this.tablaCorreoElectronico.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaCorreoElectronico.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablaCorreoElectronico.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        this.tablaObraSocial.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaObraSocial.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablaObraSocial.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        this.tablaFamiliares.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaFamiliares.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablaFamiliares.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        if(estadoLiquidacion){
            this.btnGuardarModificacion.setEnabled(false);
            this.btnEliminarEmpleado.setEnabled(false);
            this.btnConformacionBoletaEmpleado.setEnabled(false);
            this.btnCuentaEmpleado.setEnabled(false);
            this.btnLiquidacionesSueldoEmpleado.setEnabled(false);
            this.btnSesionesEmpleado.setEnabled(false);
            this.btnVentasEmpleado.setEnabled(false);
        }else{
            this.btnGuardarModificacion.setEnabled(true);
            this.btnEliminarEmpleado.setEnabled(true);
            this.btnConformacionBoletaEmpleado.setEnabled(true);
            this.btnCuentaEmpleado.setEnabled(true);
            this.btnLiquidacionesSueldoEmpleado.setEnabled(true);
            this.btnSesionesEmpleado.setEnabled(true);
            this.btnVentasEmpleado.setEnabled(true);
        }
    }
    
    public void completarCampos(){
        completarDatosEmpleado();
        completarDomicilios();
        completarTelefonos();
        completarCorreosElectronicos();
        completarFamiliares();
        completarObrasSociales();
    }
    
    public void completarDatosEmpleado(){
        txtDni.setText(empleado.getDni());
        txtNombre.setText(empleado.getNombreApellido());
        txtCuil.setText(empleado.getCuil());
        sismain.getControladorDate().darFormatoaComboBox(
                empleado.getFechaNacimiento(), comboDia, comboMes, comboAnio);
        comboSexo.removeAllItems();
        if(empleado.getSexo()== 'M') 
            comboSexo.addItem("Masculino");
        else
            comboSexo.addItem("Femenino");
        sismain.getControladorDate().darFormatoaComboBox(
                empleado.getFechaInicioRelacionLaboral(), comboDia1, comboMes1, comboAnio1);
        txaObservaciones.setText(empleado.getObservaciones());
    }
    
    public void completarDomicilios(){
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaDomicilio.getModel();
        Object [] fila = new Object[4];
                for(int i = 0; i<empleado.getDomicilios().size();i++){
                    fila[0] = empleado.getDomicilios().get(i).getIdDomicilio();
                    fila[1] = empleado.getDomicilios().get(i).getDireccion();
                    fila[2] = empleado.getDomicilios().get(i).getLocalidad();
                    fila[3] = empleado.getDomicilios().get(i).getProvincia();
                    modeloTabla.addRow(fila);
                    tablaDomicilio.setModel(modeloTabla);
                }
                empleado.getDomicilios().clear();
    }
    
    public void completarTelefonos(){
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaTelefono.getModel();
        Object [] fila = new Object[3];
                for(int i = 0; i<empleado.getTelefonos().size();i++){
                    fila[0] = empleado.getTelefonos().get(i).getIdTelefono();
                    fila[1] = empleado.getTelefonos().get(i).getNumero();
                    if(empleado.getTelefonos().get(i).getMovil() == 'F'){
                       fila[2] = "Fijo"; 
                    }else{
                       fila[2] = "Móvil";  
                    }
                    modeloTabla.addRow(fila);
                    tablaTelefono.setModel(modeloTabla);
                }
                empleado.getTelefonos().clear();
    }
    
    public void completarCorreosElectronicos(){
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaCorreoElectronico.getModel();
        Object [] fila = new Object[2];
                for(int i = 0; i<empleado.getCorreosElectronicos().size();i++){
                    fila[0] = empleado.getCorreosElectronicos().get(i).getIdCorreoElectronico();
                    fila[1] = empleado.getCorreosElectronicos().get(i).getDireccion();
                    modeloTabla.addRow(fila);
                    tablaCorreoElectronico.setModel(modeloTabla);
                }
                empleado.getCorreosElectronicos().clear();
    }
    
    public void completarFamiliares(){
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaFamiliares.getModel();
        Object [] fila = new Object[5];
                for(int i = 0; i<empleado.getFamiliares().size();i++){
                    fila[0] = empleado.getFamiliares().get(i).getIdFamiliar();
                    fila[1] = empleado.getFamiliares().get(i).getDni();
                    fila[2] = empleado.getFamiliares().get(i).getNombreApellido();
                    fila[3] = sismain.getControladorDate().darFormatoFechaATabla(
                            empleado.getFamiliares().get(i).getFechaNacimiento());
                    fila[4] = empleado.getFamiliares().get(i).getParentesco();
                    modeloTabla.addRow(fila);
                    tablaFamiliares.setModel(modeloTabla);
                }
                empleado.getFamiliares().clear();
    }
    
    public void completarObrasSociales(){
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaObraSocial.getModel();
        Object [] fila = new Object[4];
                for(int i = 0; i<empleado.getObrasSociales().size();i++){
                    fila[0] = empleado.getObrasSociales().get(i).getIdObraSocial();
                    fila[1] = empleado.getObrasSociales().get(i).getDescripcion();
                    fila[2] = empleado.getObrasSociales().get(i).getBanco();
                    fila[3] = empleado.getObrasSociales().get(i).getCuentaBancaria();
                    modeloTabla.addRow(fila);
                    tablaObraSocial.setModel(modeloTabla);
                }
                empleado.getObrasSociales().clear();
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
        btnVentasEmpleado = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        btnCuentaEmpleado = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        btnLiquidacionesSueldoEmpleado = new javax.swing.JButton();
        btnConformacionBoletaEmpleado = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        btnSesionesEmpleado = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaObservaciones = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDomicilio = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaCorreoElectronico = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        comboTurno = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        comboDia1 = new javax.swing.JComboBox<>();
        comboMes1 = new javax.swing.JComboBox<>();
        comboAnio1 = new javax.swing.JComboBox<>();
        btnNuevoDomicilio = new javax.swing.JButton();
        btnEliminarDomicilios = new javax.swing.JButton();
        btnNuevoCorreo = new javax.swing.JButton();
        btnEliminarCorreos = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCuil = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        comboDia = new javax.swing.JComboBox<>();
        comboMes = new javax.swing.JComboBox<>();
        comboAnio = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        comboSexo = new javax.swing.JComboBox<>();
        txtNombre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        comboHoraEntrada = new javax.swing.JComboBox<>();
        comboHoraSalida = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaFamiliares = new javax.swing.JTable();
        btnModificarFamiliar = new javax.swing.JButton();
        btnNuevoFamiliar = new javax.swing.JButton();
        btnEliminarFamiliar = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaTelefono = new javax.swing.JTable();
        btnNuevoTelefono = new javax.swing.JButton();
        btnEliminarTelefono = new javax.swing.JButton();
        btnNuevaObraSocial = new javax.swing.JButton();
        btnEliminarObrasSociales = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaObraSocial = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        btnGuardarModificacion = new javax.swing.JButton();
        btnEliminarEmpleado = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setForeground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 23)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Informacion del Empleado");

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        btnVentasEmpleado.setBackground(new java.awt.Color(51, 0, 51));
        btnVentasEmpleado.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnVentasEmpleado.setForeground(java.awt.Color.white);
        btnVentasEmpleado.setText("Ventas del Empleado");
        btnVentasEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasEmpleadoActionPerformed(evt);
            }
        });

        btnCuentaEmpleado.setBackground(new java.awt.Color(51, 0, 51));
        btnCuentaEmpleado.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnCuentaEmpleado.setForeground(java.awt.Color.white);
        btnCuentaEmpleado.setText("Cuenta del Empleado");
        btnCuentaEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuentaEmpleadoActionPerformed(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Operaciones del Empleado con el Sistema");

        btnLiquidacionesSueldoEmpleado.setBackground(new java.awt.Color(51, 0, 51));
        btnLiquidacionesSueldoEmpleado.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnLiquidacionesSueldoEmpleado.setForeground(java.awt.Color.white);
        btnLiquidacionesSueldoEmpleado.setText("Liquidaciones de Sueldo del Empleado");

        btnConformacionBoletaEmpleado.setBackground(new java.awt.Color(51, 0, 51));
        btnConformacionBoletaEmpleado.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnConformacionBoletaEmpleado.setForeground(java.awt.Color.white);
        btnConformacionBoletaEmpleado.setText("Conformacion de la Boleta de Sueldo del Empleado");

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Liquidacion de Haberes del Empleado");

        btnSesionesEmpleado.setBackground(new java.awt.Color(51, 0, 51));
        btnSesionesEmpleado.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnSesionesEmpleado.setForeground(java.awt.Color.white);
        btnSesionesEmpleado.setText("Sesiones del Empleado");
        btnSesionesEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSesionesEmpleadoActionPerformed(evt);
            }
        });

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Observaciones del Empleado");

        txaObservaciones.setColumns(20);
        txaObservaciones.setRows(5);
        jScrollPane2.setViewportView(txaObservaciones);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConformacionBoletaEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLiquidacionesSueldoEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCuentaEmpleado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVentasEmpleado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSesionesEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel17)
                .addGap(2, 2, 2)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel16)
                .addGap(2, 2, 2)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCuentaEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSesionesEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVentasEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addGap(2, 2, 2)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConformacionBoletaEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLiquidacionesSueldoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel18.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel18.setText("Datos Generales del Empleado");

        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("DOMICILIOS:");

        tablaDomicilio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Direccion", "Localidad", "Provincia"
            }
        ));
        jScrollPane4.setViewportView(tablaDomicilio);

        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("CORREOS ELECTRONICOS:");

        tablaCorreoElectronico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Direccion"
            }
        ));
        jScrollPane6.setViewportView(tablaCorreoElectronico);

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel10.setText("TURNO:");

        comboTurno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mañana", "Tarde", "Noche" }));

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel11.setText("TRABAJA DESDE:");

        comboDia1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDia1ActionPerformed(evt);
            }
        });

        comboMes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMes1ActionPerformed(evt);
            }
        });

        comboAnio1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAnio1ActionPerformed(evt);
            }
        });

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
        btnEliminarDomicilios.setText("Eliminar Domicilio Seleccionado");
        btnEliminarDomicilios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarDomiciliosActionPerformed(evt);
            }
        });

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
        btnEliminarCorreos.setText("Eliminar Correo Seleccionado");
        btnEliminarCorreos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCorreosActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("DNI:");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("CUIL:");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("FECHA DE NACIMIENTO:");

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

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("SEXO:");

        comboSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("NOMBRE Y APELLIDO:");

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel13.setText("HORA DE ENTRADA:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("HORA DE SALIDA:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel18)
                                .addGap(152, 152, 152))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCuil, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(comboDia, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(comboMes, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(comboAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(btnNuevoDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnEliminarDomicilios))
                                    .addComponent(jLabel24)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel27)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(btnNuevoCorreo)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnEliminarCorreos, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel13))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(comboTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                    .addComponent(comboHoraEntrada, 0, 62, Short.MAX_VALUE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel3)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(comboHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                    .addComponent(comboDia1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(comboMes1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(comboAnio1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(24, 24, 24)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtCuil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(comboDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(12, 12, 12)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminarDomicilios, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoDomicilio))
                .addGap(18, 18, 18)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminarCorreos)
                    .addComponent(btnNuevoCorreo))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboDia1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboMes1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboAnio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(comboHoraEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(comboHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        jLabel19.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Grupo Familiar del Empleado");

        tablaFamiliares.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "DNI", "NOMBRE Y APELLIDO", "FECHA DE NACIMIENTO", "PARENTESCO"
            }
        ));
        jScrollPane1.setViewportView(tablaFamiliares);

        btnModificarFamiliar.setBackground(new java.awt.Color(51, 0, 51));
        btnModificarFamiliar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnModificarFamiliar.setForeground(java.awt.Color.white);
        btnModificarFamiliar.setText("Modificar");

        btnNuevoFamiliar.setBackground(new java.awt.Color(0, 153, 0));
        btnNuevoFamiliar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnNuevoFamiliar.setForeground(java.awt.Color.white);
        btnNuevoFamiliar.setText("Agregar");
        btnNuevoFamiliar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoFamiliarActionPerformed(evt);
            }
        });

        btnEliminarFamiliar.setBackground(new java.awt.Color(153, 0, 0));
        btnEliminarFamiliar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarFamiliar.setForeground(java.awt.Color.white);
        btnEliminarFamiliar.setText("Eliminar");
        btnEliminarFamiliar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarFamiliarActionPerformed(evt);
            }
        });

        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel28.setText("TELEFONOS:");

        tablaTelefono.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Numero", "Tipo"
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

        btnEliminarTelefono.setBackground(new java.awt.Color(255, 102, 0));
        btnEliminarTelefono.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarTelefono.setForeground(java.awt.Color.white);
        btnEliminarTelefono.setText("Eliminar Telefono Seleccionado");
        btnEliminarTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTelefonoActionPerformed(evt);
            }
        });

        btnNuevaObraSocial.setBackground(new java.awt.Color(51, 51, 255));
        btnNuevaObraSocial.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnNuevaObraSocial.setForeground(java.awt.Color.white);
        btnNuevaObraSocial.setText("Nueva Obra Social");
        btnNuevaObraSocial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaObraSocialActionPerformed(evt);
            }
        });

        btnEliminarObrasSociales.setBackground(new java.awt.Color(255, 102, 0));
        btnEliminarObrasSociales.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarObrasSociales.setForeground(java.awt.Color.white);
        btnEliminarObrasSociales.setText("Eliminar Obra Social Seleccionada");
        btnEliminarObrasSociales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarObrasSocialesActionPerformed(evt);
            }
        });

        tablaObraSocial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "DESCRIPCION", "BANCO", "CUENTA BANCARIA"
            }
        ));
        jScrollPane5.setViewportView(tablaObraSocial);

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel12.setText("OBRAS SOCIALES:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnNuevoFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnModificarFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(btnEliminarFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(18, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(1, 1, 1))
                            .addComponent(jSeparator6)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(btnNuevoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(btnNuevaObraSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminarObrasSociales)))
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel28)
                            .addComponent(jLabel12))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificarFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarTelefono)
                    .addComponent(btnNuevoTelefono))
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarObrasSociales)
                    .addComponent(btnNuevaObraSocial))
                .addContainerGap(137, Short.MAX_VALUE))
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

        btnGuardarModificacion.setBackground(new java.awt.Color(51, 0, 51));
        btnGuardarModificacion.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnGuardarModificacion.setForeground(java.awt.Color.white);
        btnGuardarModificacion.setText("Guardar Modificacion");
        btnGuardarModificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarModificacionActionPerformed(evt);
            }
        });

        btnEliminarEmpleado.setBackground(new java.awt.Color(153, 0, 0));
        btnEliminarEmpleado.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarEmpleado.setForeground(java.awt.Color.white);
        btnEliminarEmpleado.setText("Eliminar Empleado");
        btnEliminarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1)
                        .addGap(548, 548, 548)
                        .addComponent(btnGuardarModificacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSalir)
                        .addComponent(btnGuardarModificacion)
                        .addComponent(btnEliminarEmpleado)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnEliminarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEmpleadoActionPerformed
        // TODO add your handling code here:
        int i =JOptionPane.showConfirmDialog(this,
                "¿REALMENTE DESEA ELIMINAR EL EMPLEADO?","Mensaje",JOptionPane.YES_NO_OPTION);
        if(i==0){
            empleado.deshabilitarBD();
            JOptionPane.showMessageDialog(
                            null, "EL EMPLEADO SE HA ELIMINADO CORRECTAMENTE",
                            "Mensaje",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarEmpleadoActionPerformed

    private void btnCuentaEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuentaEmpleadoActionPerformed
        // TODO add your handling code here:
        AEmpleado cuentaEmpleado = new AEmpleado();
        cuentaEmpleado.setVisible(true);
    }//GEN-LAST:event_btnCuentaEmpleadoActionPerformed

    private void btnSesionesEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSesionesEmpleadoActionPerformed
        // TODO add your handling code here:
        CSesiones consultarSesiones = new CSesiones();
        consultarSesiones.setVisible(true);
    }//GEN-LAST:event_btnSesionesEmpleadoActionPerformed

    private void btnVentasEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasEmpleadoActionPerformed
        // TODO add your handling code here:
        HistorialVentas historialVentas = new HistorialVentas();
        historialVentas.setVisible(true);
    }//GEN-LAST:event_btnVentasEmpleadoActionPerformed

    private void comboDia1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDia1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboDia1ActionPerformed

    private void comboMes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMes1ActionPerformed
        // TODO add your handling code here:
        sismain.getControladorDate().corregirCombos(comboDia1, comboMes1, comboAnio1);
    }//GEN-LAST:event_comboMes1ActionPerformed

    private void comboAnio1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAnio1ActionPerformed
        // TODO add your handling code here:
        sismain.getControladorDate().corregirCombos(comboDia1, comboMes1, comboAnio1);
    }//GEN-LAST:event_comboAnio1ActionPerformed

    private void btnNuevoFamiliarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoFamiliarActionPerformed
        // TODO add your handling code here:
        NDFamiliar dFamiliar = new NDFamiliar(this,
                true,(DefaultTableModel) tablaFamiliares.getModel());
        dFamiliar.setVisible(true);
    }//GEN-LAST:event_btnNuevoFamiliarActionPerformed

    private void btnNuevoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoTelefonoActionPerformed

    private void btnEliminarTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTelefonoActionPerformed
        model = (DefaultTableModel)tablaTelefono.getModel();
        
        telefono.setIdTelefono(Long.valueOf(String.valueOf(model.getValueAt(tablaTelefono.getSelectedRow(),0))));
        model.removeRow(tablaTelefono.getSelectedRow());
        telefono.eliminarBD(empleado.getIdPersona(),true);
    }//GEN-LAST:event_btnEliminarTelefonoActionPerformed

    private void btnNuevoDomicilioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoDomicilioActionPerformed
        NDDomicilio dDomicilio= new NDDomicilio(this,
            true,(DefaultTableModel) tablaDomicilio.getModel());
        dDomicilio.setVisible(true);
    }//GEN-LAST:event_btnNuevoDomicilioActionPerformed

    private void btnEliminarDomiciliosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarDomiciliosActionPerformed
        model = (DefaultTableModel)tablaDomicilio.getModel();
        domicilio.setIdDomicilio(Long.valueOf(String.valueOf(model.getValueAt(tablaDomicilio.getSelectedRow(),0))));
                
        model.removeRow(tablaDomicilio.getSelectedRow());
        domicilio.eliminarBD(empleado.getIdPersona(),true);
    }//GEN-LAST:event_btnEliminarDomiciliosActionPerformed

    private void btnNuevoCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCorreoActionPerformed
        // TODO add your handling code here:
        NDCorreoElectronico dCorreo = new NDCorreoElectronico(this,
            true,(DefaultTableModel) tablaCorreoElectronico.getModel());
        dCorreo.setVisible(true);
    }//GEN-LAST:event_btnNuevoCorreoActionPerformed

    private void btnEliminarCorreosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCorreosActionPerformed
        model = (DefaultTableModel)tablaCorreoElectronico.getModel();
        correoElectronico.setIdCorreoElectronico(Long.valueOf(String.valueOf(model.getValueAt(tablaCorreoElectronico.getSelectedRow(),0))));
                
        model.removeRow(tablaCorreoElectronico.getSelectedRow());
        correoElectronico.eliminarBD(empleado.getIdPersona(),true);
    }//GEN-LAST:event_btnEliminarCorreosActionPerformed

    private void btnNuevaObraSocialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaObraSocialActionPerformed
        NDObraSocial dObraSocial = new NDObraSocial(this,
            true,(DefaultTableModel) tablaObraSocial.getModel());
        dObraSocial.setVisible(true);
    }//GEN-LAST:event_btnNuevaObraSocialActionPerformed

    private void btnEliminarObrasSocialesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarObrasSocialesActionPerformed
        model = (DefaultTableModel)tablaObraSocial.getModel();
        relacionEmpleadoObraSocial.setIdObraSocial(Long.valueOf(String.valueOf(model.getValueAt(tablaObraSocial.getSelectedRow(),0))));
                
        model.removeRow(tablaObraSocial.getSelectedRow());
        relacionEmpleadoObraSocial.eliminarBD(relacionEmpleadoObraSocial.getIdObraSocial());
    }//GEN-LAST:event_btnEliminarObrasSocialesActionPerformed

    private void btnGuardarModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificacionActionPerformed
        // TODO add your handling code here:
        if(estado){
        this.btnGuardarModificacion.setText("Guardar Modificación");
        this.txtDni.setEditable(true);
        this.txtNombre.setEditable(true);
        this.txtCuil.setEditable(true);
        this.comboDia.setEnabled(true);
        this.comboMes.setEnabled(true);
        this.comboAnio.setEnabled(true);
        this.tablaDomicilio.setEnabled(true);
        this.tablaCorreoElectronico.setEnabled(true);
        this.comboSexo.setEnabled(true);
        this.comboTurno.setEnabled(true);
        this.comboDia1.setEnabled(true);
        this.comboMes1.setEnabled(true);
        this.comboAnio1.setEnabled(true);
        this.tablaObraSocial.setEnabled(true);
        this.tablaFamiliares.setEnabled(true);
        this.btnNuevoFamiliar.setEnabled(true);
        this.btnModificarFamiliar.setEnabled(true);
        this.btnEliminarFamiliar.setEnabled(true);
        this.btnGuardarModificacion.setEnabled(true);
        this.btnNuevoDomicilio.setEnabled(true);
        this.btnNuevoTelefono.setEnabled(true);
        this.btnNuevoCorreo.setEnabled(true);
        this.btnNuevaObraSocial.setEnabled(true);
        this.btnEliminarCorreos.setEnabled(true);
        this.btnEliminarDomicilios.setEnabled(true);
        this.btnEliminarObrasSociales.setEnabled(true);
        this.btnEliminarTelefono.setEnabled(true);
        this.txaObservaciones.setEnabled(true);
        }else{
        
        empleado.setDni(txtDni.getText());
        empleado.setCuil(txtCuil.getText());
        empleado.setNombreApellido(txtNombre.getText());
        if(comboSexo.getSelectedIndex()==0) 
            empleado.setSexo('M');
        else
            empleado.setSexo('F');
        empleado.setFechaNacimiento(sismain.getControladorDate().darFormatoStringOracle(
                        comboDia.getSelectedItem().toString(),
                        comboMes.getSelectedItem().toString(),
                        comboAnio.getSelectedItem().toString()));
        empleado.setFechaInicioRelacionLaboral(sismain.getControladorDate().darFormatoStringOracle(
                        comboDia1.getSelectedItem().toString(),
                        comboMes1.getSelectedItem().toString(),
                        comboAnio1.getSelectedItem().toString()));   
        
        empleado.modificarBD(false);
        empleado.modificarBD(true);
                
        for(int i = 0; i<tablaDomicilio.getRowCount();i++){
            domicilio.setIdDomicilio(Long.valueOf(
                    String.valueOf(tablaDomicilio.getValueAt(i,0))));
            domicilio.setDireccion(
                    String.valueOf(tablaDomicilio.getValueAt(i,1)));
            domicilio.setLocalidad(
                    String.valueOf(tablaDomicilio.getValueAt(i,2)));
            domicilio.setProvincia(
                    String.valueOf(tablaDomicilio.getValueAt(i,3)));
            domicilio.setIdPersona(empleado.getIdPersona());
            domicilio.modificarBD(empleado.getIdPersona(), true);
        }
        
        for(int i = 0; i<tablaTelefono.getRowCount();i++){
            telefono.setIdTelefono(Long.valueOf(
                    String.valueOf(tablaDomicilio.getValueAt(i,0))));
            telefono.setNumero(String.valueOf(tablaTelefono.getValueAt(i,1)));
            telefono.setMovil(
                    String.valueOf(tablaTelefono.getValueAt(i,2)).charAt(0));
            telefono.setIdPersona(empleado.getIdPersona());
            
            telefono.modificarBD(empleado.getIdPersona(), true);
        }
        
        
        for(int i = 0; i<tablaCorreoElectronico.getRowCount();i++){
            correoElectronico.setIdCorreoElectronico(Long.valueOf(
                    String.valueOf(tablaCorreoElectronico.getValueAt(i,0))));
            correoElectronico.setDireccion(
                    String.valueOf(tablaCorreoElectronico.getValueAt(i,1)));
            correoElectronico.setIdPersona(empleado.getIdPersona());
            
            correoElectronico.modificarBD(empleado.getIdPersona(), true);
        }
  
        

        for(int i = 0; i<tablaFamiliares.getRowCount();i++){
            familiar.setIdPersona(Long.valueOf(
                    String.valueOf(tablaFamiliares.getValueAt(i,0))));
            familiar.setDni(
                    String.valueOf(tablaFamiliares.getValueAt(i,1)));
            familiar.setNombreApellido(
                    String.valueOf(tablaFamiliares.getValueAt(i,2)));
            familiar.setFechaNacimiento(
                    String.valueOf(tablaFamiliares.getValueAt(i,3)));
            familiar.setParentesco(
                    String.valueOf(tablaFamiliares.getValueAt(i,4)));
            familiar.setIdEmpleado(empleado.getIdEmpleado()); 
            familiar.modificarBD(true);
            familiar.modificarBD(false);
        }
        
                
        
        for(int i = 0; i<tablaObraSocial.getRowCount();i++){
            obraSocial.setIdObraSocial(Long.valueOf(
                    String.valueOf(tablaObraSocial.getValueAt(i,0))));
            obraSocial.setDescripcion(
                    String.valueOf(tablaObraSocial.getValueAt(i,1)));
            obraSocial.setBanco(
                    String.valueOf(tablaObraSocial.getValueAt(i,2)));
            obraSocial.setCuentaBancaria(
                    String.valueOf(tablaObraSocial.getValueAt(i,3)));
            obraSocial.modificarBD();
        }
        
        JOptionPane.showMessageDialog(null, "EL EMPLEADO SE HA MODIFICADO CORRECTAMENTE","Mensaje",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarModificacionActionPerformed

    private void comboDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDiaActionPerformed

    }//GEN-LAST:event_comboDiaActionPerformed

    private void comboMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMesActionPerformed
        sismain.getControladorDate().corregirCombos(comboDia, comboMes, comboAnio);
    }//GEN-LAST:event_comboMesActionPerformed

    private void comboAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAnioActionPerformed

        sismain.getControladorDate().corregirCombos(comboDia, comboMes, comboAnio);
    }//GEN-LAST:event_comboAnioActionPerformed

    private void btnEliminarFamiliarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarFamiliarActionPerformed
        // TODO add your handling code here:
        model = (DefaultTableModel)tablaFamiliares.getModel();
        familiar.setIdPersona(Long.valueOf(String.valueOf(model.getValueAt(tablaFamiliares.getSelectedRow(),0))));
                
        model.removeRow(tablaFamiliares.getSelectedRow());
        familiar.eliminarBD(familiar.getIdPersona(),true);
        familiar.eliminarBD(familiar.getIdPersona(),false);
    }//GEN-LAST:event_btnEliminarFamiliarActionPerformed

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
            java.util.logging.Logger.getLogger(BCMEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BCMEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BCMEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BCMEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JButton btnConformacionBoletaEmpleado;
    private javax.swing.JButton btnCuentaEmpleado;
    private javax.swing.JButton btnEliminarCorreos;
    private javax.swing.JButton btnEliminarDomicilios;
    private javax.swing.JButton btnEliminarEmpleado;
    private javax.swing.JButton btnEliminarFamiliar;
    private javax.swing.JButton btnEliminarObrasSociales;
    private javax.swing.JButton btnEliminarTelefono;
    private javax.swing.JButton btnGuardarModificacion;
    private javax.swing.JButton btnLiquidacionesSueldoEmpleado;
    private javax.swing.JButton btnModificarFamiliar;
    private javax.swing.JButton btnNuevaObraSocial;
    private javax.swing.JButton btnNuevoCorreo;
    private javax.swing.JButton btnNuevoDomicilio;
    private javax.swing.JButton btnNuevoFamiliar;
    private javax.swing.JButton btnNuevoTelefono;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSesionesEmpleado;
    private javax.swing.JButton btnVentasEmpleado;
    private javax.swing.JComboBox<String> comboAnio;
    private javax.swing.JComboBox<String> comboAnio1;
    private javax.swing.JComboBox<String> comboDia;
    private javax.swing.JComboBox<String> comboDia1;
    private javax.swing.JComboBox<String> comboHoraEntrada;
    private javax.swing.JComboBox<String> comboHoraSalida;
    private javax.swing.JComboBox<String> comboMes;
    private javax.swing.JComboBox<String> comboMes1;
    private javax.swing.JComboBox<String> comboSexo;
    private javax.swing.JComboBox<String> comboTurno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTable tablaCorreoElectronico;
    private javax.swing.JTable tablaDomicilio;
    private javax.swing.JTable tablaFamiliares;
    private javax.swing.JTable tablaObraSocial;
    private javax.swing.JTable tablaTelefono;
    private javax.swing.JTextArea txaObservaciones;
    private javax.swing.JTextField txtCuil;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
