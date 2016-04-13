/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.ConceptoSueldo;
import model.TipoConcepto;

/**
 *
 * @author Administrador
 */
public class BCConceptos extends javax.swing.JFrame {

    private DefaultTableModel modeloTablaConceptos;
    private DefaultTableModel modeloTablaTipos;
    private DefaultTableModel modeloTabla;
    private TableRowSorter trsFiltro;
    private ConceptoSueldo conceptos = new ConceptoSueldo();
    private TipoConcepto tipoConcepto = new TipoConcepto();
    private int filaSeleccionada;
    /**
     * Creates new form BCMConceptos
     */
    public BCConceptos(boolean estadoLiquidacion, DefaultTableModel modeloTabla) {
        initComponents();
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Administración de Conceptos");
        completarTablas();
        
        if(estadoLiquidacion){
        this.btnAgregarConcepto.setEnabled(true);
        this.btnConformacionBoletaSueldo.setEnabled(false);
        this.btnLiquidacionIndividual.setEnabled(false);
        this.btnLiquidacionGlobal.setEnabled(false);
        this.modeloTabla = modeloTabla;
        }else{
        this.btnAgregarConcepto.setEnabled(false);
        this.btnConformacionBoletaSueldo.setEnabled(true);
        this.btnLiquidacionIndividual.setEnabled(true);
        this.btnLiquidacionGlobal.setEnabled(true);
        }
        
        
    }

    public void completarTablas(){
        completarTablaConceptos();
        completarTablaTiposConceptos();
    }
    
    public void completarTablaConceptos(){
        String aux;
        modeloTablaConceptos = (DefaultTableModel) tablaConceptos.getModel();
        this.tablaConceptos.setModel(modeloTablaConceptos);
        this.tablaConceptos.setRowSorter(trsFiltro);
        conceptos.buscarBD("'0' AND C.TIPO_CONCEPTO_ID_TIPO_CONCEPTO = "
                + "T.ID_TIPO_CONCEPTO","ID_CONCEPTO !", 'H', modeloTablaConceptos);
        for(int i = 0; i<tablaConceptos.getRowCount(); i++){
            aux = String.valueOf(tablaConceptos.getValueAt(i, 6));
            if(aux.equals("H")){
                this.tablaConceptos.setValueAt("HABILITADO", i, 6);
            }else{
                this.tablaConceptos.setValueAt("DESHABILITADO", i, 6);
            }
        }
    }
    
    public void completarTablaTiposConceptos(){
        modeloTablaTipos = (DefaultTableModel) tablaTiposConceptos.getModel();
        this.tablaTiposConceptos.setModel(modeloTablaTipos);
        this.tablaTiposConceptos.setRowSorter(trsFiltro);
        tipoConcepto.buscarBD("0", "ID_TIPO_CONCEPTO !", 'H', modeloTablaTipos);
    }
    
       
    public void filtro(int opcion) {
        int columnaABuscar;
        switch(opcion){
            case 1:
        
        if (comboOpcionConceptos.getSelectedIndex()==0) {
            columnaABuscar = 0; //CODIGO
            
        }else{
            columnaABuscar = 1; //DESCRIPCION
            
        }
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtBuscarConceptos.getText(), columnaABuscar));
        break;
            
            case 2:
                
        if (comboOpcionTipos.getSelectedIndex()==0) {
            columnaABuscar = 0; //CODIGO
            
        }else{
            columnaABuscar = 1; //DESCRIPCION
            
        }
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtBuscarTipos.getText(), columnaABuscar));
        break;
        
        }
    }
    
    public void limpiarTabla(){
             for(int i=modeloTablaConceptos.getRowCount(); i>0;i--){
             modeloTablaConceptos.removeRow(i-1);
        }
    }
    
    
    public String seleccionarRegistro(int opcion){
        
        int modelRow = 0;
        String dato = "";
        switch(opcion){
            
            case 1:
                
        modeloTablaConceptos = (DefaultTableModel) tablaConceptos.getModel();
        filaSeleccionada = tablaConceptos.getSelectedRow();
        if (filaSeleccionada != -1){
        modelRow = tablaConceptos.convertRowIndexToModel(filaSeleccionada);
        dato = String.valueOf(modeloTablaConceptos.getValueAt(modelRow,0));
        
                      }
        break;
        
            case 2:
                
        modeloTablaTipos = (DefaultTableModel) tablaTiposConceptos.getModel();        
        filaSeleccionada = tablaTiposConceptos.getSelectedRow();
        if (filaSeleccionada != -1){
        modelRow = tablaConceptos.convertRowIndexToModel(filaSeleccionada);
        dato = String.valueOf(modeloTablaTipos.getValueAt(modelRow,0));
        
                      }
        
        break;
        
        }
        return dato;
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
        jLabel8 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaTiposConceptos = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        txtBuscarTipos = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        comboOpcionTipos = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaConceptos = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        txtBuscarConceptos = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        comboOpcionConceptos = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        btnNuevoConcepto = new javax.swing.JButton();
        btnModificarConcepto = new javax.swing.JButton();
        btnEliminarConcepto = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnAgregarConcepto = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnNuevoTipo = new javax.swing.JButton();
        btnModificarTipo = new javax.swing.JButton();
        btnEliminarTipo = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        btnConformacionBoletaSueldo = new javax.swing.JButton();
        btnLiquidacionIndividual = new javax.swing.JButton();
        btnLiquidacionGlobal = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Informacion de conceptos");

        jButton8.setBackground(new java.awt.Color(153, 0, 0));
        jButton8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton8.setForeground(java.awt.Color.white);
        jButton8.setText("Salir");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Tipo de Conceptos Cargados");

        tablaTiposConceptos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCION"
            }
        ));
        jScrollPane2.setViewportView(tablaTiposConceptos);

        jPanel8.setBackground(new java.awt.Color(0, 102, 204));

        txtBuscarTipos.setBackground(new java.awt.Color(0, 0, 51));
        txtBuscarTipos.setForeground(new java.awt.Color(255, 255, 255));
        txtBuscarTipos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarTiposActionPerformed(evt);
            }
        });
        txtBuscarTipos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarTiposKeyTyped(evt);
            }
        });

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("BUSCAR POR:");

        comboOpcionTipos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CODIGO", "DESCRIPCION" }));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboOpcionTipos, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscarTipos, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarTipos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(comboOpcionTipos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));

        tablaConceptos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCION", "TIPO", "UNIDAD", "IMPORTE", "PORCENTAJE", "ESTADO"
            }
        ));
        jScrollPane1.setViewportView(tablaConceptos);

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Conceptos Cargados");

        jPanel7.setBackground(new java.awt.Color(0, 102, 204));

        txtBuscarConceptos.setBackground(new java.awt.Color(0, 0, 51));
        txtBuscarConceptos.setForeground(new java.awt.Color(255, 255, 255));
        txtBuscarConceptos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarConceptosActionPerformed(evt);
            }
        });
        txtBuscarConceptos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarConceptosKeyTyped(evt);
            }
        });

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("BUSCAR POR:");

        comboOpcionConceptos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CODIGO", "DESCRIPCION" }));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboOpcionConceptos, 0, 109, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscarConceptos, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarConceptos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(comboOpcionConceptos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        btnNuevoConcepto.setBackground(new java.awt.Color(0, 153, 0));
        btnNuevoConcepto.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnNuevoConcepto.setForeground(java.awt.Color.white);
        btnNuevoConcepto.setText("Nuevo Concepto");
        btnNuevoConcepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoConceptoActionPerformed(evt);
            }
        });

        btnModificarConcepto.setBackground(new java.awt.Color(51, 0, 51));
        btnModificarConcepto.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnModificarConcepto.setForeground(java.awt.Color.white);
        btnModificarConcepto.setText("Modificar Concepto");
        btnModificarConcepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarConceptoActionPerformed(evt);
            }
        });

        btnEliminarConcepto.setBackground(new java.awt.Color(153, 0, 0));
        btnEliminarConcepto.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarConcepto.setForeground(java.awt.Color.white);
        btnEliminarConcepto.setText("Eliminar Concepto");
        btnEliminarConcepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarConceptoActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Opciones de Conceptos Cargados");

        btnAgregarConcepto.setBackground(new java.awt.Color(0, 153, 0));
        btnAgregarConcepto.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnAgregarConcepto.setForeground(java.awt.Color.white);
        btnAgregarConcepto.setText("Agregar Concepto a la Conf. de Boleta");
        btnAgregarConcepto.setToolTipText("Agregar Concepto a la Conformación de la Boleta de Sueldo");
        btnAgregarConcepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarConceptoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnModificarConcepto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarConcepto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnNuevoConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel11))
                    .addComponent(btnAgregarConcepto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(btnNuevoConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificarConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnEliminarConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgregarConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Opciones de Tipos de Conceptos");

        jLabel15.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Cargados");

        btnNuevoTipo.setBackground(new java.awt.Color(0, 153, 0));
        btnNuevoTipo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnNuevoTipo.setForeground(java.awt.Color.white);
        btnNuevoTipo.setText("Nuevo Tipo de Concepto");
        btnNuevoTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoTipoActionPerformed(evt);
            }
        });

        btnModificarTipo.setBackground(new java.awt.Color(51, 0, 51));
        btnModificarTipo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnModificarTipo.setForeground(java.awt.Color.white);
        btnModificarTipo.setText("Modificar Tipo de Concepto");
        btnModificarTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarTipoActionPerformed(evt);
            }
        });

        btnEliminarTipo.setBackground(new java.awt.Color(153, 0, 0));
        btnEliminarTipo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarTipo.setForeground(java.awt.Color.white);
        btnEliminarTipo.setText("Eliminar Tipo de Concepto");
        btnEliminarTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTipoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnEliminarTipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModificarTipo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuevoTipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(24, 24, 24))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel12))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNuevoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificarTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnEliminarTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));

        jLabel16.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Accesos Rápidos");

        btnConformacionBoletaSueldo.setBackground(new java.awt.Color(51, 0, 51));
        btnConformacionBoletaSueldo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnConformacionBoletaSueldo.setForeground(java.awt.Color.white);
        btnConformacionBoletaSueldo.setText("Conformación de la Boleta de Sueldo");
        btnConformacionBoletaSueldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConformacionBoletaSueldoActionPerformed(evt);
            }
        });

        btnLiquidacionIndividual.setBackground(new java.awt.Color(51, 0, 51));
        btnLiquidacionIndividual.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnLiquidacionIndividual.setForeground(java.awt.Color.white);
        btnLiquidacionIndividual.setText("Liquidación Individual");
        btnLiquidacionIndividual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiquidacionIndividualActionPerformed(evt);
            }
        });

        btnLiquidacionGlobal.setBackground(new java.awt.Color(51, 0, 51));
        btnLiquidacionGlobal.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnLiquidacionGlobal.setForeground(java.awt.Color.white);
        btnLiquidacionGlobal.setText("Liquidación Global");
        btnLiquidacionGlobal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiquidacionGlobalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnConformacionBoletaSueldo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLiquidacionIndividual, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLiquidacionGlobal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(41, 41, 41)
                .addComponent(btnConformacionBoletaSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLiquidacionIndividual, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLiquidacionGlobal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 9, Short.MAX_VALUE))
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

    private void btnModificarConceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarConceptoActionPerformed
        // TODO add your handling code here:
        String indice = seleccionarRegistro(1);
        if(indice.equals("")){
            JOptionPane.showMessageDialog(
                    null, "SELECCIONE UN CONCEPTO A MODIFICAR",
                    "Mensaje",JOptionPane.INFORMATION_MESSAGE);
        }else{
            conceptos.ampliarInfoBD(Long.valueOf(indice));
            AMConceptos adminConceptos = new AMConceptos(
                this,true,(DefaultTableModel) tablaConceptos.getModel(),
                false,conceptos);
            adminConceptos.setTitle("Modificar Concepto");
            adminConceptos.completarCampos();
            adminConceptos.setVisible(true);
        }
    }//GEN-LAST:event_btnModificarConceptoActionPerformed

    private void btnNuevoConceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoConceptoActionPerformed
        // TODO add your handling code here:
        AMConceptos conceptos = new AMConceptos(
                this,true,(DefaultTableModel) tablaConceptos.getModel(),true, null);
        conceptos.setTitle("Nuevo Concepto");
        conceptos.setVisible(true);
        
    }//GEN-LAST:event_btnNuevoConceptoActionPerformed

    private void btnEliminarConceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarConceptoActionPerformed
        // TODO add your handling code here:
        filaSeleccionada = tablaConceptos.getSelectedRow();
        if(filaSeleccionada == -1){
            JOptionPane.showMessageDialog(
                    null, "SELECCIONE UN TIPO DE CONCEPTO A MODIFICAR",
                    "Mensaje",JOptionPane.INFORMATION_MESSAGE);
        }else{
            String idValor = tablaConceptos.getValueAt(filaSeleccionada, 0).toString();
            int i = JOptionPane.showConfirmDialog(
                    null, "¿ESTA SEGURO DE ELIMINAR ESTE CONCEPTO?", 
                    "Confirmacion", 0);
            if(i==0){
                conceptos.setIdConcepto(Long.valueOf(idValor));
                conceptos.deshabilitarBD();
            }
        }  
    }//GEN-LAST:event_btnEliminarConceptoActionPerformed

    private void txtBuscarConceptosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarConceptosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarConceptosActionPerformed

    private void txtBuscarConceptosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarConceptosKeyTyped
        // TODO add your handling code here:
        
        txtBuscarConceptos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                
                String cadena = (txtBuscarConceptos.getText().toUpperCase());
                txtBuscarConceptos.setText(cadena);
                filtro(1);
                System.out.println("Criterio de Busqueda : "+ cadena);
                
            }
            
        });
        trsFiltro = new TableRowSorter(modeloTablaConceptos);
        tablaConceptos.setRowSorter(trsFiltro);
    }//GEN-LAST:event_txtBuscarConceptosKeyTyped

    private void txtBuscarTiposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarTiposActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarTiposActionPerformed

    private void txtBuscarTiposKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarTiposKeyTyped
        // TODO add your handling code here:
        
        txtBuscarTipos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                
                String cadena = (txtBuscarTipos.getText().toUpperCase());
                txtBuscarTipos.setText(cadena);
                filtro(2);
                System.out.println("Criterio de Busqueda : "+ cadena);
                
            }
            
        });
        trsFiltro = new TableRowSorter(modeloTablaTipos);
        tablaTiposConceptos.setRowSorter(trsFiltro);
    }//GEN-LAST:event_txtBuscarTiposKeyTyped

    private void btnNuevoTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTipoActionPerformed
        // TODO add your handling code here:
        modeloTablaTipos = (DefaultTableModel) tablaTiposConceptos.getModel();
        NDTipoConcepto tipoConcepto = new NDTipoConcepto(
                this, true,(DefaultTableModel) tablaTiposConceptos.getModel(),
                true, null);
        tipoConcepto.setTitle("Nuevo Tipo de Concepto");
        tipoConcepto.setVisible(true);
        
    }//GEN-LAST:event_btnNuevoTipoActionPerformed

    private void btnModificarTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarTipoActionPerformed
        // TODO add your handling code here:
        String indice = seleccionarRegistro(2);
        if(indice.equals("")){
            JOptionPane.showMessageDialog(
                    null, "SELECCIONE UN TIPO DE CONCEPTO A MODIFICAR",
                    "Mensaje",JOptionPane.INFORMATION_MESSAGE);
        
        }else{
            tipoConcepto.ampliarInfoBD(Long.valueOf(indice));
            NDTipoConcepto adminTipo = new NDTipoConcepto(
                this, true,(DefaultTableModel) tablaTiposConceptos.getModel(),
                false, tipoConcepto);
            adminTipo.setTitle("Modificar Tipo de Concepto");
            adminTipo.completarCampos();
            adminTipo.setVisible(true);
        }
    }//GEN-LAST:event_btnModificarTipoActionPerformed

    private void btnEliminarTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTipoActionPerformed
        // TODO add your handling code here:
        filaSeleccionada = tablaTiposConceptos.getSelectedRow();
        if(filaSeleccionada == -1){
            JOptionPane.showMessageDialog(
                    null, "SELECCIONE UN TIPO DE CONCEPTO A MODIFICAR",
                    "Mensaje",JOptionPane.INFORMATION_MESSAGE);
        }else{
            String idValor = tablaTiposConceptos.getValueAt(filaSeleccionada, 0).toString();
            int i = JOptionPane.showConfirmDialog(
                    null, "¿ESTA SEGURO DE ELIMINAR ESTE TIPO DE CONCEPTO?", 
                    "Confirmacion", 0);
            if(i==0){
                tipoConcepto.eliminarBD(Long.valueOf(idValor));
            }
        }
        
    }//GEN-LAST:event_btnEliminarTipoActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void btnAgregarConceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarConceptoActionPerformed
        // TODO add your handling code here:
        filaSeleccionada = tablaConceptos.getSelectedRow();
        try{
            String codigo,descripcion,tipo,unidad,importe,porcentaje;
            if(filaSeleccionada==-1){
                JOptionPane.showMessageDialog(
                        null, "DEBE SELECCIONAR UN CONCEPTO","Mensaje",
                        JOptionPane.INFORMATION_MESSAGE);
            }else{
                modeloTablaConceptos = (DefaultTableModel) tablaConceptos.getModel();
                codigo = tablaConceptos.getValueAt(filaSeleccionada, 0).toString();
                descripcion = tablaConceptos.getValueAt(filaSeleccionada, 1).toString();
                tipo = tablaConceptos.getValueAt(filaSeleccionada, 2).toString();
                unidad = tablaConceptos.getValueAt(filaSeleccionada, 3).toString();
                importe = tablaConceptos.getValueAt(filaSeleccionada, 4).toString();
                porcentaje = tablaConceptos.getValueAt(filaSeleccionada, 5).toString();
                
                
                String filaValores [] = {codigo,codigo,descripcion,unidad,tipo,importe,porcentaje};
                modeloTabla.addRow(filaValores);
                this.dispose();
            }
        }catch(Exception e){
            
        }
        
    }//GEN-LAST:event_btnAgregarConceptoActionPerformed

    private void btnConformacionBoletaSueldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConformacionBoletaSueldoActionPerformed
        // TODO add your handling code here:
        ConformacionBoletaSueldo boletaSueldo = new ConformacionBoletaSueldo();
        boletaSueldo.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnConformacionBoletaSueldoActionPerformed

    private void btnLiquidacionIndividualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidacionIndividualActionPerformed
        // TODO add your handling code here:
        LiquidacionIndividual liquidacionIndividual = new LiquidacionIndividual();
        liquidacionIndividual.setVisible(true);
    }//GEN-LAST:event_btnLiquidacionIndividualActionPerformed

    private void btnLiquidacionGlobalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidacionGlobalActionPerformed
        // TODO add your handling code here:
        LiquidacionGlobal liquidacionGlobal = new LiquidacionGlobal();
        liquidacionGlobal.setVisible(true);
    }//GEN-LAST:event_btnLiquidacionGlobalActionPerformed

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
            java.util.logging.Logger.getLogger(BCConceptos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BCConceptos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BCConceptos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BCConceptos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarConcepto;
    private javax.swing.JButton btnConformacionBoletaSueldo;
    private javax.swing.JButton btnEliminarConcepto;
    private javax.swing.JButton btnEliminarTipo;
    private javax.swing.JButton btnLiquidacionGlobal;
    private javax.swing.JButton btnLiquidacionIndividual;
    private javax.swing.JButton btnModificarConcepto;
    private javax.swing.JButton btnModificarTipo;
    private javax.swing.JButton btnNuevoConcepto;
    private javax.swing.JButton btnNuevoTipo;
    private javax.swing.JComboBox comboOpcionConceptos;
    private javax.swing.JComboBox comboOpcionTipos;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable tablaConceptos;
    private javax.swing.JTable tablaTiposConceptos;
    private javax.swing.JTextField txtBuscarConceptos;
    private javax.swing.JTextField txtBuscarTipos;
    // End of variables declaration//GEN-END:variables
}
