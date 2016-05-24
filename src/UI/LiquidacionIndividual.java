/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import model.DetalleLiquidacion;
import model.Empleado;
import model.LiquidacionIndividualSueldo;
import sistemakiosco.sismain;

/**
 *
 * @author Administrador
 */
public class LiquidacionIndividual extends javax.swing.JFrame {

    private Empleado empleado;
    private DefaultTableModel modeloTabla;
    private LiquidacionIndividualSueldo liquidacionIndividual;
    private DetalleLiquidacion detalleLiquidacion;
    private double importeRemunerativo;
    private double importeNoRemunerativo;
    private double importeRetencion;
    private double totalBruto;
    private double totalNeto;
    private long ultimoIndice;
    
    /**
     * Creates new form LiquidacionIndividual
     */
    public LiquidacionIndividual() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Liquidación Individual");
        this.modeloTabla = (DefaultTableModel) tablaConceptosCargados.getModel();
        this.comboDia.setEnabled(false);
        this.comboMes.setEnabled(false);
        this.comboAnio.setEnabled(false);
        UIManager.put("ComboBox.disabledBackground", new Color(222,222,222));
        UIManager.put("ComboBox.disabledForeground", new Color(18,30,49));
        sismain.getControladorDate().iniciarCombos(comboDia, comboMes, comboAnio);
        sismain.getControladorDate().fechaActualCombos(comboDia, 
                comboMes, comboAnio);
        sismain.getControladorDate().establecerPeriodo(comboPeriodo);
        nuevaLiquidacion();
        addActions(); 
    }

    public void nuevaLiquidacion(){
        ultimoIndice = sismain.getControladorBD().obtenerUltimoIndice("LIQ_IND_SUELDO");
        String anio = sismain.getControladorDate().obtenerAnio();
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
                    buscarEmpleado(txtBuscarEmpleado.getText());
                }
            }
        };
        
        comboTipoLiquidacion.addItemListener(changeClick);
    }
    
    public void completarCampos(Empleado empleado, int indiceCombo, boolean sinDatos){
        String valorCampoBuscar;
        if(sinDatos){
        this.empleado = new Empleado();    
        }else{
        this.empleado = empleado;
        btnBuscarEmpleado.setEnabled(false);
        if(indiceCombo == 0){
            valorCampoBuscar = empleado.getCuil();
            comboDatoEmpleado.setSelectedIndex(0);
        }else{
            valorCampoBuscar = empleado.getDni();
            comboDatoEmpleado.setSelectedIndex(1);
        }
        txtBuscarEmpleado.setText(valorCampoBuscar);
        txtNombreEmpleado.setText(empleado.getNombreApellido());
        txtFechaIngreso.setText(
                sismain.getControladorDate().darFormatoFechaATabla(
                        empleado.getFechaInicioRelacionLaboral()));
        }
    }
    
    public void buscarEmpleado(String datoEmpleado){
        ArrayList<Long> indices;
        String dato,columnaBusqueda;
        
        empleado = new Empleado();
        
        if(comboDatoEmpleado.getSelectedIndex()==0){
            dato = datoEmpleado;
            columnaBusqueda = "E.CUIL";
        }else{
            dato = datoEmpleado;
            columnaBusqueda = "P.DNI";
        }
        
        
        indices = empleado.buscarBD(
                "'"+dato+"' AND P.ID_PERSONA = E.PERSONA_ID_PERSONA", 
                columnaBusqueda , 'H', null);
        empleado.setIdTipoLiquidacion(Long.valueOf(String.valueOf(
                comboTipoLiquidacion.getSelectedIndex()+1)));
        empleado.ampliarInfoBD(indices.get(0));
        txtNombreEmpleado.setText(empleado.getNombreApellido());
        txtFechaIngreso.setText(
                sismain.getControladorDate().darFormatoFechaATabla(
                        empleado.getFechaInicioRelacionLaboral()));
        
        modeloTabla = (DefaultTableModel) tablaConceptosCargados.getModel();
        Object [] fila = new Object[6];
                for(int i = 0; i<empleado.getConceptos().size();i++){
                    dividirImportes(empleado.getConceptos().get(i).getIdTipo(),
                            empleado.getConceptos().get(i).getImporte());
                    fila [0] = empleado.getConceptos().get(i).getIdConcepto();
                    fila [1] = empleado.getConceptos().get(i).getDescripcion();
                    fila [2] = empleado.getRelacionEmpleadoConceptos().get(i).getUnidadConcepto();
                    fila [3] = importeRemunerativo;
                    fila [4] = importeNoRemunerativo;
                    fila [5] = importeRetencion;
                    
                    modeloTabla.addRow(fila);
                    
                    tablaConceptosCargados.setModel(modeloTabla);
                    
                }
                empleado.getConceptos().clear();
                empleado.getRelacionEmpleadoConceptos().clear();
                sumar();
        
    }
    
    
    public void dividirImportes(long tipo, double importe){
        
        if(tipo == 1 ){
            importeRemunerativo = importe;
            importeNoRemunerativo = 0.0;
            importeRetencion = 0.0;
        }
        if(tipo == 3 || tipo == 4 || tipo == 5){
            importeRemunerativo = 0.0;
            importeNoRemunerativo = importe;
            importeRetencion = 0.0;
        }
        if(tipo == 2){
            importeRemunerativo = 0.0;
            importeNoRemunerativo = 0.0;
            importeRetencion = importe;
        }
       
    }
    
    public void sumar(){
        double sumatoria = 0, sumatoriaTotal = 0, haberesRemunerativos = 0,
                haberesNoRemunerativos = 0, retenciones = 0;
        totalNeto = 0;        
        int totalRow = tablaConceptosCargados.getRowCount();
        totalRow-=1;
        
        for(int i=0;i<=totalRow;i++){
            sumatoria = Double.valueOf(String.valueOf(tablaConceptosCargados.getValueAt(i,3)));
            sumatoriaTotal += sumatoria;
        }
        haberesRemunerativos = sumatoriaTotal;
        txtHaberesRemunerativos.setText(String.valueOf(sumatoriaTotal));
        
        sumatoria = 0;
        sumatoriaTotal = 0;
        totalRow = tablaConceptosCargados.getRowCount();
        totalRow-=1;
        
        for(int i=0;i<=totalRow;i++){
            sumatoria = Double.valueOf(String.valueOf(tablaConceptosCargados.getValueAt(i,4)));
            sumatoriaTotal += sumatoria;
        }
        haberesNoRemunerativos = sumatoriaTotal;
        txtHaberesNoRemunerativos.setText(String.valueOf(sumatoriaTotal));
        
        sumatoria = 0;
        sumatoriaTotal = 0;
        totalRow = tablaConceptosCargados.getRowCount();
        totalRow-=1;
        
        for(int i=0;i<=totalRow;i++){
            sumatoria = Double.valueOf(String.valueOf(tablaConceptosCargados.getValueAt(i,5)));
            sumatoriaTotal += sumatoria;
        }
        retenciones = sumatoriaTotal;
        txtRetenciones.setText(String.valueOf(sumatoriaTotal));
        
        totalBruto = haberesRemunerativos + haberesNoRemunerativos;
        lblTotalBruto.setText("Bruto a cobrar ($): "+ totalBruto);
        totalNeto = haberesRemunerativos + haberesNoRemunerativos - retenciones;
        lblTotalNeto.setText("Neto a cobrar ($): "+totalNeto);
    }
    
    public void liquidacion(long idEmpleado, String fechaLiquidacion,
                            String periodo, long tipoLiquidacion){
        liquidacionIndividual = new LiquidacionIndividualSueldo();
        liquidacionIndividual.setMotivo("LIQUIDACION INDIVIDUAL");
        liquidacionIndividual.setFechaLiquidacion(fechaLiquidacion);
        liquidacionIndividual.setIdEmpleado(idEmpleado);
        liquidacionIndividual.setIdTipoLiquidacion(tipoLiquidacion);
        liquidacionIndividual.setImporteNeto(totalNeto);
        liquidacionIndividual.setPeriodo(periodo);
        liquidacionIndividual.setTotalHaberesRemunerativos(importeRemunerativo);
        liquidacionIndividual.setTotalHaberesNoRemunerativos(importeNoRemunerativo);
        liquidacionIndividual.setTotalRetenciones(importeRetencion);
        liquidacionIndividual.setTotalBruto(totalNeto);
        long idLiquidacion = liquidacionIndividual.guardarBD();
        
        detalleLiquidacion = new DetalleLiquidacion();
        for(int i = 0; i<tablaConceptosCargados.getRowCount(); i++){
            detalleLiquidacion.setIdLiquidacionIndividual(idLiquidacion);
            detalleLiquidacion.setIdConcepto(Long.valueOf(String.valueOf(
                    tablaConceptosCargados.getValueAt(i, 0))));
            detalleLiquidacion.setUnidadConcepto(String.valueOf(
                    tablaConceptosCargados.getValueAt(i, 2)));
            detalleLiquidacion.setHaberesRemunerativos(Double.valueOf(String.valueOf(
                    tablaConceptosCargados.getValueAt(i, 3))));
            detalleLiquidacion.setHaberesNoRemunerativos(Double.valueOf(String.valueOf(
                    tablaConceptosCargados.getValueAt(i, 4))));
            detalleLiquidacion.setRetenciones(Double.valueOf(String.valueOf(
                    tablaConceptosCargados.getValueAt(i, 5))));
            detalleLiquidacion.guardarBD();
        }
                
    }
    
    
    public void limpiar(){
        comboDatoEmpleado.setSelectedIndex(0);
        txtBuscarEmpleado.setText("");
        txtNombreEmpleado.setText("");
        txtFechaIngreso.setText("");
        for(int i=modeloTabla.getRowCount(); i>0;i--){
             modeloTabla.removeRow(i-1);
        }
        txtHaberesRemunerativos.setText("");
        txtHaberesNoRemunerativos.setText("");
        txtRetenciones.setText("");
        lblTotalBruto.setText("Bruto a cobrar ($): ");
        lblTotalNeto.setText("Neto a cobrar ($): ");
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
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        txtFechaIngreso = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNombreEmpleado = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        txtBuscarEmpleado = new javax.swing.JTextField();
        btnBuscarEmpleado = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        comboDatoEmpleado = new javax.swing.JComboBox();
        btnAmpliarInfoEmpleado = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaConceptosCargados = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        lblNroLiquidacion = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtRetenciones = new javax.swing.JTextField();
        txtHaberesNoRemunerativos = new javax.swing.JTextField();
        txtHaberesRemunerativos = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblTotalNeto = new javax.swing.JLabel();
        lblTotalBruto = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboPeriodo = new javax.swing.JComboBox();
        comboTipoLiquidacion = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        comboDia = new javax.swing.JComboBox<>();
        comboMes = new javax.swing.JComboBox<>();
        comboAnio = new javax.swing.JComboBox<>();
        btnIniciarLiquidacion = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Liquidación Individual");

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("FECHA DE INGRESO:");

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 23)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Datos del Empleado");

        txtFechaIngreso.setBackground(new java.awt.Color(0, 0, 51));
        txtFechaIngreso.setForeground(new java.awt.Color(255, 255, 255));
        txtFechaIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaIngresoActionPerformed(evt);
            }
        });

        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("APELLIDO Y NOMBRE:");

        txtNombreEmpleado.setBackground(new java.awt.Color(0, 0, 51));
        txtNombreEmpleado.setForeground(new java.awt.Color(255, 255, 255));

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 313, Short.MAX_VALUE)
        );

        jButton1.setBackground(new java.awt.Color(51, 0, 51));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Buscar");

        jPanel8.setBackground(new java.awt.Color(0, 102, 204));

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

        jLabel6.setText("BUSCAR POR:");

        comboDatoEmpleado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CUIL", "DNI" }));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboDatoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarEmpleado)
                    .addComponent(jLabel6)
                    .addComponent(comboDatoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAmpliarInfoEmpleado))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 492, Short.MAX_VALUE)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)))
                .addContainerGap())
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(561, 561, 561)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(562, Short.MAX_VALUE)))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(779, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(btnAmpliarInfoEmpleado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(txtNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(txtFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(214, 214, 214)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(215, Short.MAX_VALUE)))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(61, 61, 61)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(340, Short.MAX_VALUE)))
        );

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));

        tablaConceptosCargados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCION", "UNIDAD", "HABERES REMUNERATIVOS", "HABERES NO REMUNERATIVOS", "RETENCIONES"
            }
        ));
        jScrollPane1.setViewportView(tablaConceptosCargados);

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 22)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Conceptos a liquidar");

        lblNroLiquidacion.setFont(new java.awt.Font("Dialog", 0, 22)); // NOI18N
        lblNroLiquidacion.setForeground(new java.awt.Color(255, 255, 255));
        lblNroLiquidacion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNroLiquidacion.setText("Liquidacion Nro 0000001/2016");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(668, 668, 668)
                        .addComponent(lblNroLiquidacion, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblNroLiquidacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtRetenciones.setBackground(new java.awt.Color(0, 0, 51));
        txtRetenciones.setForeground(new java.awt.Color(255, 255, 255));

        txtHaberesNoRemunerativos.setBackground(new java.awt.Color(0, 0, 51));
        txtHaberesNoRemunerativos.setForeground(new java.awt.Color(255, 255, 255));

        txtHaberesRemunerativos.setBackground(new java.awt.Color(0, 0, 51));
        txtHaberesRemunerativos.setForeground(new java.awt.Color(255, 255, 255));
        txtHaberesRemunerativos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHaberesRemunerativosActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Dialog", 0, 22)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Totales ($)");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtHaberesRemunerativos, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtHaberesNoRemunerativos, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtRetenciones, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHaberesRemunerativos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHaberesNoRemunerativos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRetenciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTotalNeto.setFont(new java.awt.Font("Dialog", 1, 22)); // NOI18N
        lblTotalNeto.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalNeto.setText("Neto a cobrar ($):");

        lblTotalBruto.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblTotalBruto.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalBruto.setText("Bruto a cobrar ($):");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotalBruto, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblTotalNeto, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalNeto)
                    .addComponent(lblTotalBruto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(102, 102, 102));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FECHA:");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("TIPO:");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("PERIODO:");

        comboPeriodo.setBackground(new java.awt.Color(0, 0, 51));
        comboPeriodo.setForeground(new java.awt.Color(255, 255, 255));
        comboPeriodo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05/2016", "04/2016", "03/2016", "02/2016", "01/2016" }));

        comboTipoLiquidacion.setBackground(new java.awt.Color(0, 0, 51));
        comboTipoLiquidacion.setForeground(new java.awt.Color(255, 255, 255));
        comboTipoLiquidacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 MENSUAL", "2 VACACIONES", "3 AGUINALDO", "4 BAJAS" }));

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 22)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Datos de la Liquidación");

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

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboDia, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboMes, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboTipoLiquidacion, 0, 207, Short.MAX_VALUE)
                .addGap(151, 151, 151))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel5)
                    .addComponent(comboPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(comboTipoLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnIniciarLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIniciarLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
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

    private void txtFechaIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaIngresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaIngresoActionPerformed

    private void txtHaberesRemunerativosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHaberesRemunerativosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHaberesRemunerativosActionPerformed

    private void btnAmpliarInfoEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmpliarInfoEmpleadoActionPerformed
        // TODO add your handling code here:
        BCMEmpleado adminEmpleado = new BCMEmpleado(empleado,true);
        adminEmpleado.completarCampos();
        adminEmpleado.setVisible(true);
    }//GEN-LAST:event_btnAmpliarInfoEmpleadoActionPerformed

    private void txtBuscarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarEmpleadoActionPerformed

    private void btnBuscarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarEmpleadoActionPerformed
        // TODO add your handling code here:
        buscarEmpleado(txtBuscarEmpleado.getText());
    }//GEN-LAST:event_btnBuscarEmpleadoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnIniciarLiquidacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarLiquidacionActionPerformed
        // TODO add your handling code here:
        liquidacion(empleado.getIdEmpleado(),
                sismain.getControladorDate().darFormatoStringOracle(
                        comboDia.getSelectedItem().toString(),
                        comboMes.getSelectedItem().toString(),
                        comboAnio.getSelectedItem().toString()),
                String.valueOf(comboPeriodo.getSelectedItem()),
                Long.valueOf(String.valueOf(
                comboTipoLiquidacion.getSelectedIndex()+1)));
        System.out.println("LA LIQUIDACION DE CONCEPTOS SE HA REALIZADO CORRECTAMENTE");
        
        JOptionPane.showMessageDialog(
                    null, "LA LIQUIDACION DE CONCEPTOS SE HA REALIZADO CORRECTAMENTE",
                    "Mensaje",JOptionPane.INFORMATION_MESSAGE);
               
        int j = JOptionPane.showConfirmDialog(
                null, "¿DESEA REALIZAR UNA NUEVA LIQUIDACIÓN?", "Confirmacion", 0);
        if(j==0){
            limpiar();
            
        }
    }//GEN-LAST:event_btnIniciarLiquidacionActionPerformed

    private void comboDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDiaActionPerformed

    }//GEN-LAST:event_comboDiaActionPerformed

    private void comboMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMesActionPerformed
        sismain.getControladorDate().corregirCombos(comboDia, comboMes, comboAnio);
    }//GEN-LAST:event_comboMesActionPerformed

    private void comboAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAnioActionPerformed

        sismain.getControladorDate().corregirCombos(comboDia, comboMes, comboAnio);
    }//GEN-LAST:event_comboAnioActionPerformed

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
            java.util.logging.Logger.getLogger(LiquidacionIndividual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LiquidacionIndividual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LiquidacionIndividual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LiquidacionIndividual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAmpliarInfoEmpleado;
    private javax.swing.JButton btnBuscarEmpleado;
    private javax.swing.JButton btnIniciarLiquidacion;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> comboAnio;
    private javax.swing.JComboBox comboDatoEmpleado;
    private javax.swing.JComboBox<String> comboDia;
    private javax.swing.JComboBox<String> comboMes;
    private javax.swing.JComboBox comboPeriodo;
    private javax.swing.JComboBox comboTipoLiquidacion;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
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
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lblNroLiquidacion;
    private javax.swing.JLabel lblTotalBruto;
    private javax.swing.JLabel lblTotalNeto;
    private javax.swing.JTable tablaConceptosCargados;
    private javax.swing.JTextField txtBuscarEmpleado;
    private javax.swing.JTextField txtFechaIngreso;
    private javax.swing.JTextField txtHaberesNoRemunerativos;
    private javax.swing.JTextField txtHaberesRemunerativos;
    private javax.swing.JTextField txtNombreEmpleado;
    private javax.swing.JTextField txtRetenciones;
    // End of variables declaration//GEN-END:variables
}
