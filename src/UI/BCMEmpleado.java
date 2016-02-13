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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.CorreoElectronico;
import model.Domicilio;
import model.Empleado;
import model.ObraSocial;
import model.Telefono;

/**
 *
 * @author IgnacioMatias
 */
public class BCMEmpleado extends javax.swing.JFrame {

    private ControladorDate controladorDate = new ControladorDate();
    private ControladorDate controladorDate1 = new ControladorDate();
    private ControladorDate controladorDate2 = new ControladorDate();
    private Empleado empleado = new Empleado();
    private Domicilio domicilio = new Domicilio();
    private Telefono telefono = new Telefono();
    private ObraSocial obraSocial = new ObraSocial();
    private CorreoElectronico correoElectronico = new CorreoElectronico();
    private DefaultTableModel model;
    private String cadenaIdPersona;
    /**
     * Creates new form ABMProducto
     */
    public BCMEmpleado() {
        initComponents();
        controladorDate.iniciarCombos(comboDia, comboMes, comboAnio);
        controladorDate1.iniciarCombos(comboDia1, comboMes1, comboAnio1);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.btnNuevoFamiliar.setEnabled(false);
        this.btnModificarFamiliar.setEnabled(false);
        this.btnEliminarFamiliar.setEnabled(false);
        this.txtDni.setEnabled(false);
        this.txtNombre.setEnabled(false);
        this.comboDia.setEnabled(false);
        this.comboMes.setEnabled(false);
        this.comboAnio.setEnabled(false);
        this.tablaDomicilio.setEnabled(false);
        this.tablaCorreoElectronico.setEnabled(false);
        this.comboCategoria.setEnabled(false);
        this.comboTurno.setEnabled(false);
        this.comboDia1.setEnabled(false);
        this.comboMes1.setEnabled(false);
        this.comboAnio1.setEnabled(false);
        this.tablaObraSocial.setEnabled(false);
        this.tablaFamiliares.setEnabled(false);
        this.btnGuardarModificacion.setEnabled(false);
        this.btnNuevoDomicilio.setEnabled(false);
        this.btnNuevoTelefono.setEnabled(false);
        this.btnNuevoCorreo.setEnabled(false);
        this.btnNuevaObraSocial.setEnabled(false);
        this.btnEliminarCorreos.setEnabled(false);
        this.btnEliminarDomicilios.setEnabled(false);
        this.btnEliminarObrasSociales.setEnabled(false);
        this.btnEliminarTelefono.setEnabled(false);
        
    }
    
       
    public void dato(String idPersona){
        ControladorBD control = new ControladorBD(); 
        ResultSet rs;
        cadenaIdPersona = idPersona;
        try{
            rs = control.buscarRegistrosSinTabla("DNI", "PERSONA", "ID_PERSONA = " + cadenaIdPersona);
            while(rs.next()){
                txtDni.setText(rs.getString("DNI"));
            }
                completarCuil();
                completarNombre();
                completarFechaNac();
                completarDomicilios();
                completarCorreosElectronicos();
                completarInicioLaboral();
                completarTelefonos();
        }catch (SQLException ex) {
            Logger.getLogger(BCMEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
    
    public void completarCuil(){
        ControladorBD controlador = new ControladorBD();
        ResultSet res;
        String condicion = "PERSONA_ID_PERSONA = " + cadenaIdPersona;
        String cuil;
        try{
        res = controlador.buscarRegistrosSinTabla("CUIL", "EMPLEADO", condicion);
        while(res.next()){
                    cuil = res.getString("CUIL");
                    txtCuil.setText(cuil);
                    System.out.println(cuil);
                    
        }
        }catch (SQLException ex) {
            Logger.getLogger(BCMEmpleado.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(BCMEmpleado.class.getName()).log(Level.SEVERE, null, ex);
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
                    controladorDate2.darFormatoaComboBox(fechaNac,comboDia,comboMes,comboAnio);
        }
        }catch (SQLException ex) {
            Logger.getLogger(BCMEmpleado.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(BCMEmpleado.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(BCMEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    public void completarInicioLaboral(){
        ControladorBD controlador = new ControladorBD();
        ResultSet res;
        String fechaInicioLaboral;
        try{
        res = controlador.buscarRegistrosSinTabla("FECHA_INICIO_RELACION_LABORAL", "EMPLEADO", "PERSONA_ID_PERSONA = " + cadenaIdPersona);
        while(res.next()){
                    fechaInicioLaboral = res.getString("FECHA_INICIO_RELACION_LABORAL");
                    controladorDate2.darFormatoaComboBox(fechaInicioLaboral,comboDia1,comboMes1,comboAnio1);
        }
        }catch (SQLException ex) {
            Logger.getLogger(BCMEmpleado.class.getName()).log(Level.SEVERE, null, ex);
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
                    tablaTelefono.setModel(modeloTabla);
        }
        }catch (SQLException ex) {
            Logger.getLogger(BCMEmpleado.class.getName()).log(Level.SEVERE, null, ex);
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
        jPanel3 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jButton10 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        btnModificarDatos = new javax.swing.JButton();
        btnEliminarEmpleado = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        comboDia = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        comboMes = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        comboAnio = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDomicilio = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaCorreoElectronico = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        comboCategoria = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        comboTurno = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        comboDia1 = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        comboMes1 = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        comboAnio1 = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaObraSocial = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCuil = new javax.swing.JTextField();
        btnNuevoDomicilio = new javax.swing.JButton();
        btnEliminarDomicilios = new javax.swing.JButton();
        btnNuevoCorreo = new javax.swing.JButton();
        btnEliminarCorreos = new javax.swing.JButton();
        btnNuevaObraSocial = new javax.swing.JButton();
        btnEliminarObrasSociales = new javax.swing.JButton();
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
        btnSalir = new javax.swing.JButton();
        btnGuardarModificacion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setForeground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 23)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 255, 0));
        jLabel1.setText("Informacion del Empleado");

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jButton9.setBackground(new java.awt.Color(51, 0, 51));
        jButton9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton9.setForeground(java.awt.Color.white);
        jButton9.setText("Ventas del Empleado");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(51, 0, 51));
        jButton10.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton10.setForeground(java.awt.Color.white);
        jButton10.setText("Cuenta del Empleado");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Operaciones del Empleado con el Sistema");

        jButton11.setBackground(new java.awt.Color(51, 0, 51));
        jButton11.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton11.setForeground(java.awt.Color.white);
        jButton11.setText("Liquidaciones de Sueldo del Empleado");

        jButton13.setBackground(new java.awt.Color(51, 0, 51));
        jButton13.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton13.setForeground(java.awt.Color.white);
        jButton13.setText("Conformacion de la Boleta de Sueldo del Empleado");

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Liquidacion de Haberes del Empleado");

        jButton17.setBackground(new java.awt.Color(51, 0, 51));
        jButton17.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton17.setForeground(java.awt.Color.white);
        jButton17.setText("Observaciones del Empleado");

        jButton18.setBackground(new java.awt.Color(51, 0, 51));
        jButton18.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton18.setForeground(java.awt.Color.white);
        jButton18.setText("Sesiones del Empleado");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Observaciones del Empleado");

        btnModificarDatos.setBackground(new java.awt.Color(51, 0, 51));
        btnModificarDatos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnModificarDatos.setForeground(java.awt.Color.white);
        btnModificarDatos.setText("Modificar Datos");
        btnModificarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarDatosActionPerformed(evt);
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
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnModificarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel17)
                .addGap(2, 2, 2)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addGap(2, 2, 2)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addGap(2, 2, 2)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("DNI:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("APELLIDO Y NOMBRE:");

        jLabel18.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel18.setForeground(java.awt.Color.white);
        jLabel18.setText("Datos Generales del Empleado");

        comboDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDiaActionPerformed(evt);
            }
        });

        jLabel22.setBackground(new java.awt.Color(0, 0, 0));
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("/");

        comboMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMesActionPerformed(evt);
            }
        });

        jLabel23.setBackground(new java.awt.Color(0, 0, 0));
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("/");

        comboAnio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAnioActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("FECHA DE NACIMIENTO:");

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

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel9.setText("CATEGORIA:");

        comboCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Empleado", "Cajero", " " }));

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

        jLabel25.setBackground(new java.awt.Color(0, 0, 0));
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("/");

        comboMes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMes1ActionPerformed(evt);
            }
        });

        jLabel26.setBackground(new java.awt.Color(0, 0, 0));
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("/");

        comboAnio1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAnio1ActionPerformed(evt);
            }
        });

        tablaObraSocial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DESCRIPCION", "BANCO", "CUENTA BANCARIA"
            }
        ));
        jScrollPane5.setViewportView(tablaObraSocial);

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel12.setText("OBRAS SOCIALES:");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("CUIL:");

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
        btnEliminarObrasSociales.setText("Eliminar Obras Sociales Seleccionadas");
        btnEliminarObrasSociales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarObrasSocialesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombre)
                                    .addComponent(txtDni)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(comboDia, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboMes, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel24))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(comboDia1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel25)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboMes1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel26)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboAnio1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(txtCuil))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNuevoDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarDomicilios))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnNuevoCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminarCorreos))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNuevaObraSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarObrasSociales)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCuil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(comboDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminarDomicilios, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoDomicilio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoCorreo)
                    .addComponent(btnEliminarCorreos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboDia1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(comboMes1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(comboAnio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarObrasSociales)
                    .addComponent(btnNuevaObraSocial))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        jLabel19.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel19.setForeground(java.awt.Color.white);
        jLabel19.setText("Grupo Familiar del Empleado");

        tablaFamiliares.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DNI", "NOMBRE Y APELLIDO", "FECHA DE NACIMIENTO", "PARENTESCO"
            }
        ));
        jScrollPane1.setViewportView(tablaFamiliares);

        btnModificarFamiliar.setBackground(new java.awt.Color(51, 0, 51));
        btnModificarFamiliar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnModificarFamiliar.setForeground(java.awt.Color.white);
        btnModificarFamiliar.setText("Modificar Seleccionado");

        btnNuevoFamiliar.setBackground(new java.awt.Color(0, 153, 0));
        btnNuevoFamiliar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnNuevoFamiliar.setForeground(java.awt.Color.white);
        btnNuevoFamiliar.setText("Agregar Nuevo");
        btnNuevoFamiliar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoFamiliarActionPerformed(evt);
            }
        });

        btnEliminarFamiliar.setBackground(new java.awt.Color(153, 0, 0));
        btnEliminarFamiliar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarFamiliar.setForeground(java.awt.Color.white);
        btnEliminarFamiliar.setText("Eliminar Seleccionado");

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

        btnEliminarTelefono.setBackground(new java.awt.Color(255, 102, 0));
        btnEliminarTelefono.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarTelefono.setForeground(java.awt.Color.white);
        btnEliminarTelefono.setText("Eliminar Telefonos Seleccionados");
        btnEliminarTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTelefonoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(btnNuevoFamiliar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificarFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(btnEliminarFamiliar))
                    .addComponent(jScrollPane7)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnNuevoTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarTelefono))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel28))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificarFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarTelefono)
                    .addComponent(btnNuevoTelefono))
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1)
                        .addGap(703, 703, 703)
                        .addComponent(btnGuardarModificacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
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
                        .addComponent(btnGuardarModificacion)))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnEliminarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarEmpleadoActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        AEmpleado cuentaEmpleado = new AEmpleado();
        cuentaEmpleado.setVisible(true);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        CSesiones consultarSesiones = new CSesiones();
        consultarSesiones.setVisible(true);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        HistorialVentas historialVentas = new HistorialVentas();
        historialVentas.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void comboDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDiaActionPerformed

    }//GEN-LAST:event_comboDiaActionPerformed

    private void comboMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMesActionPerformed
        controladorDate.corregirCombos(comboDia, comboMes, comboAnio);
    }//GEN-LAST:event_comboMesActionPerformed

    private void comboAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAnioActionPerformed

        controladorDate.corregirCombos(comboDia, comboMes, comboAnio);
    }//GEN-LAST:event_comboAnioActionPerformed

    private void comboDia1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDia1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboDia1ActionPerformed

    private void comboMes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMes1ActionPerformed
        // TODO add your handling code here:
        controladorDate1.corregirCombos(comboDia1, comboMes1, comboAnio1);
    }//GEN-LAST:event_comboMes1ActionPerformed

    private void comboAnio1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAnio1ActionPerformed
        // TODO add your handling code here:
        controladorDate1.corregirCombos(comboDia1, comboMes1, comboAnio1);
    }//GEN-LAST:event_comboAnio1ActionPerformed

    private void btnNuevoFamiliarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoFamiliarActionPerformed
        // TODO add your handling code here:
        NDFamiliar dFamiliar = new NDFamiliar(this,
                true,(DefaultTableModel) tablaFamiliares.getModel());
        dFamiliar.setVisible(true);
    }//GEN-LAST:event_btnNuevoFamiliarActionPerformed

    private void btnModificarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarDatosActionPerformed
        // TODO add your handling code here:
        this.txtDni.setEnabled(true);
        this.txtNombre.setEnabled(true);
        this.comboDia.setEnabled(true);
        this.comboMes.setEnabled(true);
        this.comboAnio.setEnabled(true);
        this.tablaDomicilio.setEnabled(true);
        this.tablaCorreoElectronico.setEnabled(true);
        this.comboCategoria.setEnabled(true);
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
    }//GEN-LAST:event_btnModificarDatosActionPerformed

    private void btnNuevoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoTelefonoActionPerformed

    private void btnEliminarTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTelefonoActionPerformed
        model = (DefaultTableModel)tablaTelefono.getModel();
        model.removeRow(tablaTelefono.getSelectedRow());
    }//GEN-LAST:event_btnEliminarTelefonoActionPerformed

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
        model = (DefaultTableModel)tablaCorreoElectronico.getModel();
        model.removeRow(tablaCorreoElectronico.getSelectedRow());
    }//GEN-LAST:event_btnEliminarCorreosActionPerformed

    private void btnNuevaObraSocialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaObraSocialActionPerformed
        NDObraSocial dObraSocial = new NDObraSocial(this,
            true,(DefaultTableModel) tablaObraSocial.getModel());
        dObraSocial.setVisible(true);
    }//GEN-LAST:event_btnNuevaObraSocialActionPerformed

    private void btnEliminarObrasSocialesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarObrasSocialesActionPerformed
        model = (DefaultTableModel)tablaObraSocial.getModel();
        model.removeRow(tablaObraSocial.getSelectedRow());
    }//GEN-LAST:event_btnEliminarObrasSocialesActionPerformed

    private void btnGuardarModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificacionActionPerformed
        // TODO add your handling code here:
        empleado.setDni(txtDni.getText());
        empleado.setCuil(txtCuil.getText());
        empleado.setNombreApellido(txtNombre.getText());
        empleado.setFechaNacimiento(controladorDate.darFormatoStringOracle(comboDia,comboMes,
                        comboAnio));
        empleado.setFechaInicioRelacionLaboral(controladorDate.darFormatoStringOracle(comboDia1,comboMes1,
                        comboAnio1));
        
        ArrayList<String> valoresPersona = new ArrayList<>();
        valoresPersona.add(empleado.getDni());
        valoresPersona.add(empleado.getCuil());
        valoresPersona.add(empleado.getNombreApellido());
        valoresPersona.add(empleado.getFechaNacimiento());
        valoresPersona.add(empleado.getFechaInicioRelacionLaboral());
        valoresPersona.add("M");
        valoresPersona.add("");
        empleado.update(valoresPersona, "PERSONA", "ID_PERSONA", cadenaIdPersona);
        
        ArrayList<String> valoresDomicilio = new ArrayList<>();
        for(int i = 0; i<tablaDomicilio.getRowCount();i++){
            domicilio.setDireccion(
                    String.valueOf(tablaDomicilio.getValueAt(i,0)));
            domicilio.setLocalidad(
                    String.valueOf(tablaDomicilio.getValueAt(i, 1)));
            domicilio.setProvincia(
                    String.valueOf(tablaDomicilio.getValueAt(i, 2)));
            domicilio.setIdPersona(Long.valueOf(cadenaIdPersona));
            valoresDomicilio.add(domicilio.getDireccion());
            valoresDomicilio.add(domicilio.getLocalidad());
            valoresDomicilio.add(domicilio.getProvincia());
            domicilio.update(valoresDomicilio, "DOMICILIO", "PERSONA_ID_PERSONA", cadenaIdPersona);
            valoresDomicilio.clear();
        }
        
        ArrayList<String> valoresTelefono = new ArrayList<>();
        for(int i = 0; i<tablaTelefono.getRowCount();i++){
            telefono.setNumero(String.valueOf(tablaTelefono.getValueAt(i,0)));
            telefono.setMovil(
                    String.valueOf(tablaTelefono.getValueAt(i, 1)).charAt(0));
            telefono.setIdPersona(Long.valueOf(cadenaIdPersona));
            valoresTelefono.add(telefono.getNumero());
            valoresTelefono.add(String.valueOf(telefono.getMovil()));
            telefono.update(valoresTelefono, "TELEFONO", "PERSONA_ID_PERSONA", cadenaIdPersona);
            valoresTelefono.clear();
        }
        
        ArrayList<String> valoresEmail = new ArrayList<>();
        for(int i = 0; i<tablaCorreoElectronico.getRowCount();i++){
            correoElectronico.setDireccion(
                    String.valueOf(tablaCorreoElectronico.getValueAt(i,0)));
            correoElectronico.setIdPersona(Long.valueOf(cadenaIdPersona));
            valoresEmail.add(correoElectronico.getDireccion());
            correoElectronico.update(valoresEmail, "CORREOELECTRONICO", "PERSONA_ID_PERSONA", cadenaIdPersona);
            valoresEmail.clear();
        }
        
        ArrayList<String> valoresObraSocial = new ArrayList<>();
        for(int i = 0; i<tablaObraSocial.getRowCount();i++){
            obraSocial.setDescripcion(
                    String.valueOf(tablaCorreoElectronico.getValueAt(i,0)));
            obraSocial.setBanco(
                    String.valueOf(tablaCorreoElectronico.getValueAt(i,1)));
            obraSocial.setCuentaBancaria(
                    String.valueOf(tablaCorreoElectronico.getValueAt(i,2)));
            valoresObraSocial.add(correoElectronico.getDireccion());
            obraSocial.update(valoresObraSocial, "OBRA_SOCIAL", "ID_OBRA_SOCIAL", cadenaIdPersona);
            valoresObraSocial.clear();
        }
        
        JOptionPane.showMessageDialog(null, "EL EMPLEADO SE HA MODIFICADO CORRECTAMENTE","Mensaje",JOptionPane.INFORMATION_MESSAGE);
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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BCMEmpleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminarCorreos;
    private javax.swing.JButton btnEliminarDomicilios;
    private javax.swing.JButton btnEliminarEmpleado;
    private javax.swing.JButton btnEliminarFamiliar;
    private javax.swing.JButton btnEliminarObrasSociales;
    private javax.swing.JButton btnEliminarTelefono;
    private javax.swing.JButton btnGuardarModificacion;
    private javax.swing.JButton btnModificarDatos;
    private javax.swing.JButton btnModificarFamiliar;
    private javax.swing.JButton btnNuevaObraSocial;
    private javax.swing.JButton btnNuevoCorreo;
    private javax.swing.JButton btnNuevoDomicilio;
    private javax.swing.JButton btnNuevoFamiliar;
    private javax.swing.JButton btnNuevoTelefono;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> comboAnio;
    private javax.swing.JComboBox<String> comboAnio1;
    private javax.swing.JComboBox<String> comboCategoria;
    private javax.swing.JComboBox<String> comboDia;
    private javax.swing.JComboBox<String> comboDia1;
    private javax.swing.JComboBox<String> comboMes;
    private javax.swing.JComboBox<String> comboMes1;
    private javax.swing.JComboBox<String> comboTurno;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JTextField txtCuil;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
