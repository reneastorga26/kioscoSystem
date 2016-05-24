/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Empleado;
import model.RelacionEmpleadoConceptos;

/**
 *
 * @author Administrador
 */
public class ConformacionBoletaSueldo extends javax.swing.JFrame {

    private Empleado empleado = new Empleado();
    private RelacionEmpleadoConceptos relacionEmpleadoConcepto = new RelacionEmpleadoConceptos();
    private DefaultTableModel modeloTabla;
    private boolean estado = false;
    private ArrayList<Object> auxiliarNuevosConceptos = new ArrayList<>();
    private ArrayList<Object> auxiliarConceptosEliminados = new ArrayList<>();
    private ArrayList<Object> auxiliarConceptosExistentes = new ArrayList<>();
    private boolean eliminarRegistrosDeBD = false;
    private boolean noEncontro = false;
    private boolean confirmacion = false;
    
    /**
     * Creates new form ConceptoAutomatico
     */
    public ConformacionBoletaSueldo() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        modeloTabla = (DefaultTableModel) tablaConceptosCargados.getModel();
        this.setTitle("Conformación de la Boleta de Sueldo");
        this.tablaConceptosCargados.setModel(modeloTabla);
        this.tablaConceptosCargados.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaConceptosCargados.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablaConceptosCargados.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.tablaConceptosCargados.getColumnModel().getColumn(1).setPreferredWidth(45);
        this.tablaConceptosCargados.getColumnModel().getColumn(2).setPreferredWidth(190);
        this.tablaConceptosCargados.getColumnModel().getColumn(3).setPreferredWidth(60);
        this.tablaConceptosCargados.getColumnModel().getColumn(4).setPreferredWidth(70);
        this.tablaConceptosCargados.getColumnModel().getColumn(5).setPreferredWidth(80);
        addActions(); 
    }

     
    public void addActions()
    {
        final ItemListener changeClick = new ItemListener()
        {
            public void itemStateChanged(ItemEvent e) 
            {
                if(comboTipoLiquidacion.getSelectedItem().equals(e.getItem()))
                {
                    for(int i=modeloTabla.getRowCount(); i>0;i--){
                        modeloTabla.removeRow(i-1);
                    }
                    buscarDatosEmpleado();
                }
            }
        };
        
        comboTipoLiquidacion.addItemListener(changeClick);
    } 
    
    public void leerNuevosRegistrosTablaConceptos(){
        
        ArrayList<Long> registros = new ArrayList<>();
        for(int i = 0; i<auxiliarNuevosConceptos.size();i++){
            for(int j = 0; j<auxiliarConceptosExistentes.size();j++){
                
                if(auxiliarNuevosConceptos.get(i) == auxiliarConceptosExistentes.get(j)){
                    noEncontro = false;
                    //MODIFICAR LA UNIDAD DEL CONCEPTO
                    relacionEmpleadoConcepto.setIdEmpleado(empleado.getIdEmpleado());
                    relacionEmpleadoConcepto.setUnidadConcepto(String.valueOf(
                    tablaConceptosCargados.getValueAt(j, 3)));
                    relacionEmpleadoConcepto.setIdTipoLiquidacion(
                    comboTipoLiquidacion.getSelectedIndex()+1);
                    relacionEmpleadoConcepto.setIdConcepto(Long.valueOf(
                    String.valueOf(tablaConceptosCargados.getValueAt(j, 0))));
                    relacionEmpleadoConcepto.modificarBD();
                    break;
                
                }else{
                noEncontro = true;
                }
            }
            
            if(noEncontro){
                registros.add(Long.valueOf(String.valueOf(auxiliarNuevosConceptos.get(i))));
            }
            
        }
        
        for(int i = 0; i<registros.size();i++){
            System.out.println("SE GUARDO UN NUEVO CONCEPTO: "+ registros.get(i));
            relacionEmpleadoConcepto.setIdConcepto(Long.valueOf(
                    String.valueOf(registros.get(i))));
            relacionEmpleadoConcepto.setIdEmpleado(empleado.getIdEmpleado());
            relacionEmpleadoConcepto.setIdTipoLiquidacion(
                    comboTipoLiquidacion.getSelectedIndex()+1);
            relacionEmpleadoConcepto.setUnidadConcepto(String.valueOf(
                    tablaConceptosCargados.getValueAt(i, 3)));
            relacionEmpleadoConcepto.guardarBD();
        }
        registros.clear();
        auxiliarNuevosConceptos.clear();
        auxiliarConceptosExistentes.clear();
        confirmacion = true;
    }
    
    public void leerRegistrosEliminadosTablaConceptos(){
        for(int i = 0; i<auxiliarConceptosEliminados.size();i++){
            if(eliminarRegistrosDeBD){
            relacionEmpleadoConcepto.setIdConcepto(Long.valueOf(String.valueOf(
                    auxiliarConceptosEliminados.get(i))));
            relacionEmpleadoConcepto.eliminarBD(relacionEmpleadoConcepto.getIdConcepto());
            System.out.println("SE ELIMINO UN CONCEPTO DE BD: "+ auxiliarConceptosEliminados.get(i));
            }
        }
        auxiliarConceptosEliminados.clear();
    }
    
    public void verificarEliminacionBD(long idReferenciado){
        
            for(int i = 0;i<auxiliarConceptosExistentes.size();i++){
                if(Long.valueOf(String.valueOf(auxiliarConceptosExistentes.get(i))) == 
                       idReferenciado){
                    eliminarRegistrosDeBD = true;
                    System.out.println("SE ELIMINARA UN CONCEPTO DE LA BD");
                    auxiliarConceptosEliminados.add(idReferenciado);
                }else{
                    System.out.println("SE ELIMINARA UN CONCEPTO DE LA TABLA");
                }
            }
                 
    }
    
    public void limpiarTabla(){
        for(int i=modeloTabla.getRowCount(); i>0;i--){
             modeloTabla.removeRow(i-1);
        }
    }
    
    public void limpiarTodo(){
        txtBuscarEmpleado.setText("");
        txtNombreEmpleado.setText("");
        comboDatoEmpleado.setSelectedIndex(0);
        txtUnidadConcepto.setText("");
        limpiarTabla();
    }
    
    public void buscarDatosEmpleado(){
        ArrayList<Long> indices = new ArrayList<>();
        String dato,columnaBusqueda;
        limpiarTabla();
        if(comboDatoEmpleado.getSelectedIndex()==0){
            dato = txtBuscarEmpleado.getText();
            columnaBusqueda = "E.CUIL";
        }else{
            dato = txtBuscarEmpleado.getText();
            columnaBusqueda = "P.DNI";
        }   
        
            indices = empleado.buscarBD(
                "'"+dato+"' AND P.ID_PERSONA = E.PERSONA_ID_PERSONA", 
                columnaBusqueda , 'H', null);
        empleado.setIdTipoLiquidacion(comboTipoLiquidacion.getSelectedIndex()+1);
        
        empleado.ampliarInfoBD(indices.get(0));
        indices.clear();
        txtNombreEmpleado.setText(empleado.getNombreApellido());
        txtNroCuenta.setText(empleado.getNroCuentaBanco());
        modeloTabla = (DefaultTableModel) tablaConceptosCargados.getModel();
        Object [] fila = new Object[6];
                for(int i = 0; i<empleado.getConceptos().size();i++){
                    fila [0] = empleado.getConceptos().get(i).getIdConcepto();
                    fila [1] = empleado.getConceptos().get(i).getIdConcepto();
                    fila [2] = empleado.getConceptos().get(i).getDescripcion();
                    fila [3] = empleado.getRelacionEmpleadoConceptos().get(i).getUnidadConcepto();
                    fila [4] = empleado.getConceptos().get(i).getImporte();
                    fila [5] = empleado.getConceptos().get(i).getPorcentaje();
                    
                    modeloTabla.addRow(fila);
                    auxiliarConceptosExistentes.add(
                            empleado.getConceptos().get(i).getIdConcepto());
                    auxiliarNuevosConceptos.add(
                            empleado.getConceptos().get(i).getIdConcepto());
                    tablaConceptosCargados.setModel(modeloTabla);
                }
                empleado.getConceptos().clear();
                empleado.getRelacionEmpleadoConceptos().clear();
        
            if(auxiliarConceptosExistentes.isEmpty()){
                eliminarRegistrosDeBD = false;
            }else{
                eliminarRegistrosDeBD = true;
                System.out.println("ELIMINACION DE CONCEPTOS EN BD ACTIVADA");
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNombreEmpleado = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        txtBuscarEmpleado = new javax.swing.JTextField();
        btnBuscarEmpleado = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        comboDatoEmpleado = new javax.swing.JComboBox();
        btnAmpliarInfo = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        btnLimpiar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        buscarConceptos = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboTipoLiquidacion = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        txtUnidadConcepto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnAgregarUnidad = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        comboBanco = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtNroCuenta = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaConceptosCargados = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnEliminarConcepto = new javax.swing.JButton();
        btnEliminarTodosConceptos = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        btnLiquidacionGlobal = new javax.swing.JButton();
        btnLiquidacionIndividual = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Conformacion de la Boleta de Sueldo");

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Datos del Empleado");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("NOMBRE Y APELLIDO");

        txtNombreEmpleado.setBackground(new java.awt.Color(0, 0, 51));
        txtNombreEmpleado.setForeground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(0, 102, 204));

        txtBuscarEmpleado.setBackground(new java.awt.Color(0, 0, 51));
        txtBuscarEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        txtBuscarEmpleado.setText("20111111112");
        txtBuscarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarEmpleadoActionPerformed(evt);
            }
        });

        btnBuscarEmpleado.setBackground(new java.awt.Color(51, 0, 51));
        btnBuscarEmpleado.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnBuscarEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarEmpleado.setText("Buscar");
        btnBuscarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarEmpleadoActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("BUSCAR POR:");

        comboDatoEmpleado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CUIL", "DNI" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboDatoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarEmpleado)
                    .addComponent(jLabel1)
                    .addComponent(comboDatoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAmpliarInfo.setBackground(new java.awt.Color(51, 0, 51));
        btnAmpliarInfo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnAmpliarInfo.setForeground(new java.awt.Color(255, 255, 255));
        btnAmpliarInfo.setText("Ampliar Información del Empleado");
        btnAmpliarInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmpliarInfoActionPerformed(evt);
            }
        });

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnLimpiar.setBackground(new java.awt.Color(51, 0, 51));
        btnLimpiar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(6, 6, 6)
                        .addComponent(txtNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAmpliarInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnAmpliarInfo)
                                    .addComponent(btnLimpiar)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));

        jPanel6.setBackground(new java.awt.Color(0, 102, 204));

        buscarConceptos.setBackground(new java.awt.Color(51, 0, 51));
        buscarConceptos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        buscarConceptos.setForeground(new java.awt.Color(255, 255, 255));
        buscarConceptos.setText("Buscar Concepto");
        buscarConceptos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarConceptosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buscarConceptos, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buscarConceptos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Datos del Concepto");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("TIPO:");

        comboTipoLiquidacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1  MENSUAL", "2 VACACIONES", "3 AGUINALDO", "4 BAJAS" }));

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Tipo de Liquidación");

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        txtUnidadConcepto.setBackground(new java.awt.Color(0, 0, 51));
        txtUnidadConcepto.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("UNIDAD:");

        btnAgregarUnidad.setBackground(new java.awt.Color(51, 0, 51));
        btnAgregarUnidad.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnAgregarUnidad.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarUnidad.setText("Agregar");
        btnAgregarUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarUnidadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUnidadConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnAgregarUnidad)
                .addGap(18, 18, 18)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboTipoLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel14))
                .addGap(121, 121, 121))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtUnidadConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(btnAgregarUnidad))
                                .addGap(14, 14, 14))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(comboTipoLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(102, 102, 102));

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("BANCO:");

        comboBanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BANCO INDUSTRIAL" }));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("NRO DE CUENTA:");

        txtNroCuenta.setBackground(new java.awt.Color(0, 0, 51));
        txtNroCuenta.setForeground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Datos de Entidad Bancaria");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNroCuenta)
                            .addComponent(comboBanco, 0, 191, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(comboBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNroCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        tablaConceptosCargados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CODIGO", "DESCRIPCION", "UNIDAD", "IMPORTE", "PORCENTAJE"
            }
        ));
        tablaConceptosCargados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaConceptosCargadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaConceptosCargados);

        btnGuardar.setBackground(new java.awt.Color(0, 153, 0));
        btnGuardar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnGuardar.setForeground(java.awt.Color.white);
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(153, 0, 0));
        jButton9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton9.setForeground(java.awt.Color.white);
        jButton9.setText("Cancelar y Salir");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        btnEliminarConcepto.setBackground(new java.awt.Color(255, 102, 0));
        btnEliminarConcepto.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarConcepto.setForeground(java.awt.Color.white);
        btnEliminarConcepto.setText("Eliminar Concepto Seleccionado");
        btnEliminarConcepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarConceptoActionPerformed(evt);
            }
        });

        btnEliminarTodosConceptos.setBackground(new java.awt.Color(255, 102, 0));
        btnEliminarTodosConceptos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarTodosConceptos.setForeground(java.awt.Color.white);
        btnEliminarTodosConceptos.setText("Eliminar todos los conceptos");
        btnEliminarTodosConceptos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTodosConceptosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEliminarConcepto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addComponent(btnEliminarTodosConceptos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarTodosConceptos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(102, 102, 102));

        btnLiquidacionGlobal.setBackground(new java.awt.Color(51, 0, 51));
        btnLiquidacionGlobal.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnLiquidacionGlobal.setForeground(new java.awt.Color(255, 255, 255));
        btnLiquidacionGlobal.setText("Liquidación de Sueldo Global");
        btnLiquidacionGlobal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiquidacionGlobalActionPerformed(evt);
            }
        });

        btnLiquidacionIndividual.setBackground(new java.awt.Color(51, 0, 51));
        btnLiquidacionIndividual.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnLiquidacionIndividual.setForeground(new java.awt.Color(255, 255, 255));
        btnLiquidacionIndividual.setText("Liquidación de Sueldo Individual");
        btnLiquidacionIndividual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiquidacionIndividualActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Acceso a Liquidación");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLiquidacionIndividual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLiquidacionGlobal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(7, 7, 7)
                .addComponent(btnLiquidacionIndividual, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLiquidacionGlobal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 4, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton9)))
                .addGap(26, 26, 26))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jButton9)
                    .addComponent(btnGuardar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void txtBuscarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarEmpleadoActionPerformed

    private void buscarConceptosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarConceptosActionPerformed
        // TODO add your handling code here:
        BCConceptos bcConceptos = new BCConceptos(true,auxiliarNuevosConceptos,
                (DefaultTableModel) tablaConceptosCargados.getModel());
        bcConceptos.setVisible(true);
    }//GEN-LAST:event_buscarConceptosActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void btnLiquidacionIndividualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidacionIndividualActionPerformed
        // TODO add your handling code here:
        int indiceCombo = comboDatoEmpleado.getSelectedIndex();
        if(estado){
            LiquidacionIndividual liquidacionIndividual = 
                    new LiquidacionIndividual();
            liquidacionIndividual.completarCampos(empleado, indiceCombo, false);
            liquidacionIndividual.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(
                    null, "DEBE GUARDAR LOS DATOS ANTES DE ACCEDER A LA LIQUIDACION",
                    "Mensaje",JOptionPane.INFORMATION_MESSAGE); 
        }
    }//GEN-LAST:event_btnLiquidacionIndividualActionPerformed

    private void btnLiquidacionGlobalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidacionGlobalActionPerformed
        // TODO add your handling code here:
        if(estado){
            LiquidacionGlobal liquidacionGlobal = new LiquidacionGlobal();
            liquidacionGlobal.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(
                    null, "DEBE GUARDAR LOS DATOS ANTES DE ACCEDER A LA LIQUIDACION",
                    "Mensaje",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnLiquidacionGlobalActionPerformed

    private void btnBuscarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarEmpleadoActionPerformed
        // TODO add your handling code here:
        buscarDatosEmpleado();
        /*ArrayList<Long> indices;
        String dato,columnaBusqueda;
        if(comboDatoEmpleado.getSelectedIndex()==0){
            dato = txtBuscarEmpleado.getText();
            columnaBusqueda = "E.CUIL";
        }else{
            dato = txtBuscarEmpleado.getText();
            columnaBusqueda = "P.DNI";
        }   
        indices = empleado.buscarBD(
                "'"+dato+"' AND P.ID_PERSONA = E.PERSONA_ID_PERSONA", 
                columnaBusqueda , 'H', null);
        empleado.setIdTipoLiquidacion(comboTipoLiquidacion.getSelectedIndex()+1);
        empleado.ampliarInfoBD(indices.get(0));
        
        txtNombreEmpleado.setText(empleado.getNombreApellido());
        txtNroCuenta.setText(empleado.getNroCuentaBanco());
        
        modeloTabla = (DefaultTableModel) tablaConceptosCargados.getModel();
        Object [] fila = new Object[7];
                for(int i = 0; i<empleado.getConceptos().size();i++){
                    fila [0] = empleado.getConceptos().get(i).getIdConcepto();
                    fila [1] = empleado.getConceptos().get(i).getIdConcepto();
                    fila [2] = empleado.getConceptos().get(i).getDescripcion();
                    fila [3] = empleado.getConceptos().get(i).getUnidad();
                    fila [4] = empleado.getConceptos().get(i).getIdTipo();
                    fila [5] = empleado.getConceptos().get(i).getImporte();
                    fila [6] = empleado.getConceptos().get(i).getPorcentaje();
                    
                    modeloTabla.addRow(fila);
                    auxiliarConceptosExistentes.add(
                            empleado.getConceptos().get(i).getIdConcepto());
                    auxiliarNuevosConceptos.add(
                            empleado.getConceptos().get(i).getIdConcepto());
                    tablaConceptosCargados.setModel(modeloTabla);
                }
                empleado.getConceptos().clear();
        
            if(auxiliarConceptosExistentes.isEmpty()){
                eliminarRegistrosDeBD = false;
            }else{
                eliminarRegistrosDeBD = true;
                System.out.println("ELIMINACION DE CONCEPTOS EN BD ACTIVADA");
            }
             */   
    }//GEN-LAST:event_btnBuscarEmpleadoActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiarTodo();
        
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnAmpliarInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmpliarInfoActionPerformed
        // TODO add your handling code here:
        BCMEmpleado adminEmpleado = new BCMEmpleado(empleado, true);
        adminEmpleado.completarCampos();
        adminEmpleado.setVisible(true);
    }//GEN-LAST:event_btnAmpliarInfoActionPerformed

    private void btnEliminarConceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarConceptoActionPerformed
        // TODO add your handling code here:
        long idReferenciado;
        if(tablaConceptosCargados.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(
                    null, "DEBE SELECCIONAR UN CONCEPTO",
                    "Mensaje",JOptionPane.INFORMATION_MESSAGE);
        }else{
            
        int i = JOptionPane.showConfirmDialog(
                null, "¿ESTA SEGURO DE ELIMINAR ESTE CONCEPTO?", "Confirmacion", 0);
        if(i==0){
        modeloTabla = (DefaultTableModel)tablaConceptosCargados.getModel();
        idReferenciado = Long.valueOf(String.valueOf(modeloTabla.getValueAt(
                        tablaConceptosCargados.getSelectedRow(),0)));
        verificarEliminacionBD(idReferenciado);
        int j = tablaConceptosCargados.getSelectedRow();
        auxiliarNuevosConceptos.remove(j);
        modeloTabla.removeRow(tablaConceptosCargados.getSelectedRow());
        
        }
        }
    }//GEN-LAST:event_btnEliminarConceptoActionPerformed

    private void btnEliminarTodosConceptosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTodosConceptosActionPerformed
        // TODO add your handling code here:
        if(tablaConceptosCargados.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(
                    null, "DEBE SELECCIONAR UN CONCEPTO",
                    "Mensaje",JOptionPane.INFORMATION_MESSAGE);
        }else{
        for(int i=modeloTabla.getRowCount(); i>0;i--){
             modeloTabla.removeRow(i-1);
             auxiliarConceptosEliminados.add(
                Long.valueOf(String.valueOf(modeloTabla.getValueAt(i,0))));
        }
        
        }
    }//GEN-LAST:event_btnEliminarTodosConceptosActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        
        leerNuevosRegistrosTablaConceptos();
        leerRegistrosEliminadosTablaConceptos();
        if(confirmacion){
        estado = true;
        
        JOptionPane.showMessageDialog(
                    null, "LOS DATOS SE HAN GUARDADO CORRECTAMENTE",
                    "Mensaje",JOptionPane.INFORMATION_MESSAGE);
        limpiarTodo();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void tablaConceptosCargadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaConceptosCargadosMouseClicked
        // TODO add your handling code here:
            
     int filaseleccionada = tablaConceptosCargados.getSelectedRow();

     try{
        if (filaseleccionada==-1){
         
             JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");

         }else{

             modeloTabla=(DefaultTableModel) tablaConceptosCargados.getModel();
           
             txtUnidadConcepto.setText(String.valueOf(modeloTabla.getValueAt(
                     filaseleccionada, 3)));
          }

       }catch (HeadlessException ex){
           JOptionPane.showMessageDialog(
                   null, "Error: "+ex+"\nInténtelo nuevamente", " .::Error En la Operacion::." ,
                   JOptionPane.ERROR_MESSAGE);
       }     
        
    }//GEN-LAST:event_tablaConceptosCargadosMouseClicked

    private void btnAgregarUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarUnidadActionPerformed
        // TODO add your handling code here:
        int filaseleccionada = tablaConceptosCargados.getSelectedRow();
        
        modeloTabla=(DefaultTableModel) tablaConceptosCargados.getModel();
           
        String nuevaUnidad = txtUnidadConcepto.getText();
        modeloTabla.setValueAt(nuevaUnidad,filaseleccionada, 3);
    }//GEN-LAST:event_btnAgregarUnidadActionPerformed

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
            java.util.logging.Logger.getLogger(ConformacionBoletaSueldo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConformacionBoletaSueldo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConformacionBoletaSueldo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConformacionBoletaSueldo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConformacionBoletaSueldo().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarUnidad;
    private javax.swing.JButton btnAmpliarInfo;
    private javax.swing.JButton btnBuscarEmpleado;
    private javax.swing.JButton btnEliminarConcepto;
    private javax.swing.JButton btnEliminarTodosConceptos;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnLiquidacionGlobal;
    private javax.swing.JButton btnLiquidacionIndividual;
    private javax.swing.JButton buscarConceptos;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox comboBanco;
    private javax.swing.JComboBox comboDatoEmpleado;
    private javax.swing.JComboBox<String> comboTipoLiquidacion;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTable tablaConceptosCargados;
    private javax.swing.JTextField txtBuscarEmpleado;
    private javax.swing.JTextField txtNombreEmpleado;
    private javax.swing.JTextField txtNroCuenta;
    private javax.swing.JTextField txtUnidadConcepto;
    // End of variables declaration//GEN-END:variables
}
