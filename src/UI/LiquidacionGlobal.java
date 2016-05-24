/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.DetalleLiquidacion;
import model.Empleado;
import model.LiquidacionIndividualSueldo;
import sistemakiosco.sismain;

/**
 *
 * @author Administrador
 */
public class LiquidacionGlobal extends javax.swing.JFrame {

    private Empleado empleado = new Empleado();
    private LiquidacionIndividualSueldo liquidacionIndividualSueldo = 
            new LiquidacionIndividualSueldo();;
    private DefaultTableModel modeloTabla;
    private long ultimoIndice;
    private DetalleLiquidacion detalleLiquidacion;
    private ArrayList<Object> importes = new ArrayList<>();
    private double importeRemunerativo;
    private double importeNoRemunerativo;
    private double importeRetencion;
    private double totalNeto, haberesRemunerativos, haberesNoRemunerativos,
            retenciones;
    /**
     * Creates new form LiquidacionGlobal
     */
    public LiquidacionGlobal() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Liquidación Global");
        this.modeloTabla = (DefaultTableModel) tablaEmpleados.getModel();
        tablaEmpleados.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaEmpleados.getColumnModel().getColumn(0).setMinWidth(0);
        tablaEmpleados.getColumnModel().getColumn(0).setPreferredWidth(0);
        tablaEmpleados.setModel(modeloTabla);
        sismain.getControladorDate().iniciarCombos(comboDia, comboMes, comboAnio);
        sismain.getControladorDate().fechaActualCombos(comboDia, 
                comboMes, comboAnio);
        sismain.getControladorDate().establecerPeriodo(comboPeriodo);
        nuevaLiquidacion();
        completarDatos();
    }
    
    public void nuevaLiquidacion(){
        ultimoIndice = sismain.getControladorBD().obtenerUltimoIndice("LIQ_IND_SUELDO");
        String anio = sismain.getControladorDate().obtenerAnio();
        sismain.getControladorDate().establecerPeriodo(comboPeriodo);
        long indice = ultimoIndice +1;
        if(ultimoIndice>0 && ultimoIndice<10){
            lblNroLiquidacion.setText(
                    "Liquidacion Nro 000000"+indice+"/"+anio);
        }
        if(ultimoIndice>10 && ultimoIndice<100){
            lblNroLiquidacion.setText(
                    "Liquidacion Nro 00000"+indice+"/"+anio);
        }
        if(ultimoIndice>100 && ultimoIndice<1000){
            lblNroLiquidacion.setText(
                    "Liquidacion Nro 0000"+indice+"/"+anio);
        }
        if(ultimoIndice>1000 && ultimoIndice<10000){
            lblNroLiquidacion.setText(
                    "Liquidacion Nro 000"+indice+"/"+anio);
        }
        if(ultimoIndice>10000 && ultimoIndice<100000){
            lblNroLiquidacion.setText(
                    "Liquidacion Nro 00"+indice+"/"+anio);
        }
        if(ultimoIndice>100000 && ultimoIndice<1000000){
            lblNroLiquidacion.setText(
                    "Liquidacion Nro 0"+indice+"/"+anio);
        }
        if(ultimoIndice>1000000 && ultimoIndice<10000000){
            lblNroLiquidacion.setText(
                    "Liquidacion Nro "+indice+"/"+anio);
        }
    }
    
    public void completarDatos(){
        ArrayList<Long> indices;
        indices = empleado.buscarBD("0","P.ID_PERSONA = E.PERSONA_ID_PERSONA "
                + " AND E.ID_EMPLEADO !" , 'H', null);
        Object [] fila = new Object[6];
        for(int i = 0; i<indices.size(); i++){
            empleado.ampliarInfoBD(indices.get(i));
            fila [0] = empleado.getIdEmpleado();
            fila [1] = empleado.getDni();
            fila [2] = empleado.getNombreApellido();
            fila [3] = empleado.getCuil();
            fila [4] = sismain.getControladorDate().darFormatoFechaATabla(
                    empleado.getFechaInicioRelacionLaboral());
            fila [5] = "TRABAJA DURO";
            modeloTabla.addRow(fila);
            tablaEmpleados.setModel(modeloTabla);
        }
        
                     
    }

    public void buscarEmpleado(String datoEmpleado){
        ArrayList<Long> indices;
        String dato,columnaBusqueda;
        
        empleado = new Empleado();
        
        dato = datoEmpleado;
        columnaBusqueda = "E.CUIL";
        
        indices = empleado.buscarBD(
                "'"+dato+"' AND P.ID_PERSONA = E.PERSONA_ID_PERSONA", 
                columnaBusqueda , 'H', null);
        empleado.setIdTipoLiquidacion(liquidacionIndividualSueldo.getIdTipoLiquidacion());
        empleado.ampliarInfoBD(indices.get(0));
        
        for(int i = 0; i<empleado.getConceptos().size();i++){
            dividirImportes(empleado.getConceptos().get(i).getIdTipo(),
                            empleado.getConceptos().get(i).getImporte());
            sumar();
        }
        
    }
    
    
    public void dividirImportes(long tipo, double importe){
        
        if(tipo == 1){
            importeRemunerativo = importe;
            haberesRemunerativos = haberesRemunerativos + importeRemunerativo;
            importeNoRemunerativo = 0.0;
            importeRetencion = 0.0;
        }
        if(tipo == 3 || tipo == 4 || tipo == 5){
            importeNoRemunerativo = importe;
            haberesNoRemunerativos = haberesNoRemunerativos + importeNoRemunerativo;
            importeRemunerativo = 0.0;
            importeRetencion = 0.0;
        }
        if(tipo == 2){
            importeRetencion = importe;
            retenciones = retenciones + importeRetencion;
            importeRemunerativo = 0.0;
            importeNoRemunerativo = 0.0;
        }
       
        importes.add(importeRemunerativo);
        importes.add(importeNoRemunerativo);
        importes.add(importeRetencion);
    }
    
    
    public void sumar(){
        totalNeto = haberesRemunerativos + haberesNoRemunerativos - retenciones;
    }
        
    
    public void liquidacion(long idEmpleado, String fechaLiquidacion,
                            String periodo, long tipoLiquidacion){
        liquidacionIndividualSueldo.setMotivo("LIQUIDACION INDIVIDUAL");
        liquidacionIndividualSueldo.setFechaLiquidacion(fechaLiquidacion);
        liquidacionIndividualSueldo.setIdEmpleado(idEmpleado);
        liquidacionIndividualSueldo.setIdTipoLiquidacion(tipoLiquidacion);
        liquidacionIndividualSueldo.setImporteNeto(totalNeto);
        liquidacionIndividualSueldo.setPeriodo(periodo);
        liquidacionIndividualSueldo.setTotalHaberesRemunerativos(haberesRemunerativos);
        liquidacionIndividualSueldo.setTotalHaberesNoRemunerativos(haberesNoRemunerativos);
        liquidacionIndividualSueldo.setTotalRetenciones(retenciones);
        liquidacionIndividualSueldo.setTotalBruto(totalNeto);
        long idLiquidacion = liquidacionIndividualSueldo.guardarBD();
        
        detalleLiquidacion = new DetalleLiquidacion();
        int j = 0;
        for(int i = 0; i<empleado.getConceptos().size(); i++){
            detalleLiquidacion.setIdLiquidacionIndividual(idLiquidacion);
            detalleLiquidacion.setIdConcepto(Long.valueOf(String.valueOf(
                    empleado.getConceptos().get(i).getIdConcepto())));
            detalleLiquidacion.setUnidadConcepto(String.valueOf(
                    empleado.getConceptos().get(i).getUnidad()));
            detalleLiquidacion.setHaberesRemunerativos(Double.valueOf(String.valueOf(
                    importes.get(j))));
            detalleLiquidacion.setHaberesNoRemunerativos(Double.valueOf(String.valueOf(
                    importes.get(j+1))));
            detalleLiquidacion.setRetenciones(Double.valueOf(String.valueOf(
                    importes.get(j+2))));
            detalleLiquidacion.guardarBD();
            j = j + 3;
        }
        
        
        //REINICIAR VARIABLES
        haberesRemunerativos = 0.0;
        importeRemunerativo = 0.0;
        haberesNoRemunerativos = 0.0;
        importeNoRemunerativo = 0.0;
        retenciones = 0.0;
        importeRetencion = 0.0;
        totalNeto = 0.0;
        
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
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEmpleados = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboTipoLiquidacion = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        comboDia = new javax.swing.JComboBox<>();
        comboMes = new javax.swing.JComboBox<>();
        comboAnio = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        comboPeriodo = new javax.swing.JComboBox();
        btnAmpliarInfoEmpleado = new javax.swing.JButton();
        btnIniciarLiquidacion = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        lblNroLiquidacion = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setForeground(new java.awt.Color(51, 51, 51));

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Liquidación Global");

        tablaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID_EMPLEADO", "DNI", "APELLIDO Y NOMBRE", "CUIL", "TRABAJA DESDE", "TURNO"
            }
        ));
        jScrollPane1.setViewportView(tablaEmpleados);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("TIPO:");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("PERIODO:");

        comboTipoLiquidacion.setBackground(new java.awt.Color(0, 0, 51));
        comboTipoLiquidacion.setForeground(new java.awt.Color(255, 255, 255));
        comboTipoLiquidacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 MENSUAL", "2 VACACIONES", "3 AGUINALDO", "4 BAJAS" }));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FECHA:");

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

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 22)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Datos de la Liquidación");

        comboPeriodo.setBackground(new java.awt.Color(0, 0, 51));
        comboPeriodo.setForeground(new java.awt.Color(255, 255, 255));
        comboPeriodo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05/2016", "04/2016", "03/2016", "02/2016", "01/2016" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(211, 211, 211)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboDia, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboMes, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboTipoLiquidacion, 0, 166, Short.MAX_VALUE)
                .addGap(37, 37, 37))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(comboTipoLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(comboDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(comboPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        btnAmpliarInfoEmpleado.setBackground(new java.awt.Color(51, 0, 51));
        btnAmpliarInfoEmpleado.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnAmpliarInfoEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        btnAmpliarInfoEmpleado.setText("Ampliar Informacion de Empleado Seleccionado");
        btnAmpliarInfoEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmpliarInfoEmpleadoActionPerformed(evt);
            }
        });

        btnIniciarLiquidacion.setBackground(new java.awt.Color(0, 153, 0));
        btnIniciarLiquidacion.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnIniciarLiquidacion.setForeground(java.awt.Color.white);
        btnIniciarLiquidacion.setText("Iniciar Liquidacion");
        btnIniciarLiquidacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarLiquidacionActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(153, 0, 0));
        btnSalir.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnSalir.setForeground(java.awt.Color.white);
        btnSalir.setText("Cancelar y Salir");

        lblNroLiquidacion.setFont(new java.awt.Font("Dialog", 0, 22)); // NOI18N
        lblNroLiquidacion.setForeground(new java.awt.Color(255, 255, 255));
        lblNroLiquidacion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNroLiquidacion.setText("Liquidacion Nro 0000001/2016");

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 22)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Datos de los Empleados");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(673, 673, 673)
                                .addComponent(lblNroLiquidacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnIniciarLiquidacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAmpliarInfoEmpleado)
                        .addGap(30, 30, 30))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblNroLiquidacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnIniciarLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAmpliarInfoEmpleado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAmpliarInfoEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmpliarInfoEmpleadoActionPerformed
        // TODO add your handling code here:
        LiquidacionIndividual liquidacionIndividual = new LiquidacionIndividual();
        liquidacionIndividual.setVisible(true);
    }//GEN-LAST:event_btnAmpliarInfoEmpleadoActionPerformed

    private void comboDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDiaActionPerformed

    }//GEN-LAST:event_comboDiaActionPerformed

    private void comboMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMesActionPerformed
        sismain.getControladorDate().corregirCombos(comboDia, comboMes, comboAnio);
    }//GEN-LAST:event_comboMesActionPerformed

    private void comboAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAnioActionPerformed

        sismain.getControladorDate().corregirCombos(comboDia, comboMes, comboAnio);
    }//GEN-LAST:event_comboAnioActionPerformed

    private void btnIniciarLiquidacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarLiquidacionActionPerformed
        // TODO add your handling code here:
        liquidacionIndividualSueldo.setIdTipoLiquidacion(Long.valueOf(String.valueOf(
                comboTipoLiquidacion.getSelectedIndex()+1)));
        liquidacionIndividualSueldo.setPeriodo(String.valueOf(
                comboPeriodo.getSelectedItem()));
        
        //POR CADA EMPLEADO DE LA TABLA REALIZAR LA LIQUIDACION INDIVIDUAL
        for(int i = 0; i<tablaEmpleados.getRowCount();i++){
            buscarEmpleado(
                    String.valueOf(tablaEmpleados.getValueAt(i,3)));
            liquidacion(Long.valueOf(
                    String.valueOf(tablaEmpleados.getValueAt(i,0))),
                    sismain.getControladorDate().darFormatoFecha(
                            comboDia.getSelectedItem().toString(), 
                            comboMes.getSelectedItem().toString(), 
                            comboAnio.getSelectedItem().toString()),
                    liquidacionIndividualSueldo.getPeriodo(),
                    liquidacionIndividualSueldo.getIdTipoLiquidacion());
            
        }
        JOptionPane.showMessageDialog(
                    null, "LA LIQUIDACION DE CONCEPTOS SE HA REALIZADO CORRECTAMENTE",
                    "Mensaje",JOptionPane.INFORMATION_MESSAGE);
        
        //System.out.println(importes);
    }//GEN-LAST:event_btnIniciarLiquidacionActionPerformed

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
            java.util.logging.Logger.getLogger(LiquidacionGlobal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LiquidacionGlobal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LiquidacionGlobal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LiquidacionGlobal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LiquidacionGlobal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAmpliarInfoEmpleado;
    private javax.swing.JButton btnIniciarLiquidacion;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> comboAnio;
    private javax.swing.JComboBox<String> comboDia;
    private javax.swing.JComboBox<String> comboMes;
    private javax.swing.JComboBox comboPeriodo;
    private javax.swing.JComboBox comboTipoLiquidacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblNroLiquidacion;
    private javax.swing.JTable tablaEmpleados;
    // End of variables declaration//GEN-END:variables
}
