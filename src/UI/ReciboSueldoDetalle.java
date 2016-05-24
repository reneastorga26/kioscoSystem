/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Color;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import model.Empleado;
import model.LiquidacionIndividualSueldo;
import model.ReciboSueldoDetallado;
import model.TipoLiquidacion;
import model.numberToText;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import sistemakiosco.sismain;

/**
 *
 * @author Administrador
 */
public class ReciboSueldoDetalle extends javax.swing.JFrame {

    private Empleado empleado;
    private LiquidacionIndividualSueldo liquidacionIndividualSueldo;
    private TipoLiquidacion tipoLiquidacion;
    private ReciboSueldoDetallado reciboSueldo;
    private DefaultTableModel modeloTabla;
    private double totalNeto;
    private long ultimoIndice;
    
    //DEFINIR VARIABLES PARA EL RECIBO DE SUELDO
    private long idEmpleado;
    private long idLiquidacion;
    private long idBanco;
    private long idTipoLiquidacion;
    //--------------------------------
    
    
    /**
     * Creates new form ReciboSueldoDetalle
     */
    public ReciboSueldoDetalle() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Detalles del Recibo de Sueldo");
        modeloTabla = (DefaultTableModel) tablaConceptos.getModel();
        this.comboFuncionEmpleado.setEnabled(false);
        this.comboPeriodo.setEnabled(false);
        this.comboBanco.setEnabled(false);
        UIManager.put("ComboBox.disabledBackground", new Color(222,222,222));
        UIManager.put("ComboBox.disabledForeground", new Color(18,30,49));
        
    }

    
    
    public void nuevoRecibo(){
        ultimoIndice = sismain.getControladorBD().obtenerUltimoIndice("RECIBO_SUELDO");
        long indice = ultimoIndice +1;
        if(ultimoIndice>0 && ultimoIndice<10){
            txtNroRecibo.setText("000000"+indice);
        }
        if(ultimoIndice>10 && ultimoIndice<100){
            txtNroRecibo.setText("00000"+indice);
        }
        if(ultimoIndice>100 && ultimoIndice<1000){
            txtNroRecibo.setText("0000"+indice);
        }
        if(ultimoIndice>1000 && ultimoIndice<10000){
            txtNroRecibo.setText("000"+indice);
        }
        if(ultimoIndice>10000 && ultimoIndice<100000){
            txtNroRecibo.setText("00"+indice);
        }
        if(ultimoIndice>100000 && ultimoIndice<1000000){
            txtNroRecibo.setText("0"+indice);
        }
        if(ultimoIndice>1000000 && ultimoIndice<10000000){
            txtNroRecibo.setText(String.valueOf(indice));
        }
    }
    
    
    public void completarCampos(long idEmpleado, long idLiquidacion){
        
        ArrayList<Object> indicesTipos;
        //Completar Nro de recibo
        nuevoRecibo();
        
        empleado = new Empleado();
        liquidacionIndividualSueldo = new LiquidacionIndividualSueldo();
        tipoLiquidacion = new TipoLiquidacion();
        reciboSueldo = new ReciboSueldoDetallado();
        
        liquidacionIndividualSueldo.ampliarInfoBD(idLiquidacion);
               
        this.idTipoLiquidacion = liquidacionIndividualSueldo.getIdTipoLiquidacion();
        this.idEmpleado = idEmpleado;
        this.idLiquidacion = idLiquidacion;
        empleado.setIdTipoLiquidacion(idTipoLiquidacion);
        empleado.ampliarInfoBD(idEmpleado);
        
        this.idBanco = Long.valueOf(comboBanco.getSelectedIndex()+1);
        
        //DATOS DEL EMPLEADO
        txtCuil.setText(empleado.getCuil());
        txtNombre.setText(empleado.getNombreApellido());
        txtNroCuenta.setText(empleado.getNroCuentaBanco());
        txtFechaIngreso.setText(
                sismain.getControladorDate().darFormatoFechaATabla(
                empleado.getFechaInicioRelacionLaboral()));
        
        //DATOS DE LA LIQUIDACION
        txtFechaLiquidacion.setText(sismain.getControladorDate().darFormatoFechaATabla(
                liquidacionIndividualSueldo.getFechaLiquidacion()));
                
        indicesTipos = tipoLiquidacion.buscarBD(String.valueOf(idTipoLiquidacion), 
                                        "T.ID_TIPO_LIQ", null);
        tipoLiquidacion.ampliarInfoBD(Long.valueOf(String.valueOf(indicesTipos.get(0))));
        txtTipoLiquidacion.setText(tipoLiquidacion.getNombre());
        comboPeriodo.setSelectedItem(liquidacionIndividualSueldo.getPeriodo());
        
              
        //DATOS DE LA TABLA
        modeloTabla = (DefaultTableModel) tablaConceptos.getModel();
        reciboSueldo.ampliarInfoReciboSueldo(idEmpleado, idTipoLiquidacion, 
                        idLiquidacion, modeloTabla);
        tablaConceptos.setModel(modeloTabla);
        sumar();
                       
        //DATOS DE TOTALES
        /*txtHaberesRemunerativos.setText(String.valueOf(
                liquidacionIndividualSueldo.getTotalHaberesRemunerativos()));
        txtHaberesNoRemunerativos.setText(String.valueOf(
                liquidacionIndividualSueldo.getTotalHaberesNoRemunerativos()));
        txtRetenciones.setText(String.valueOf(
                liquidacionIndividualSueldo.getTotalRetenciones()));
        */
        totalNeto = liquidacionIndividualSueldo.getImporteNeto();
        txtTotalNeto.setText(String.valueOf(totalNeto));
        
                
        String totalEntero = separarParteEnteraTotal(String.valueOf(totalNeto));
        String resultado = numToText(Integer.valueOf(totalEntero));
        String totalDecimal = separarParteDecimalTotal(String.valueOf(totalNeto));
        txtTotalNetoEscrito.setText(resultado.toUpperCase() + " CON " + 
                                totalDecimal + "/100");
                
    }
    
       
    public String separarParteEnteraTotal(String totalNeto){
        String totalEntero="";
        int ultimoDigito = totalNeto.length();
        
        for(int i=0; i<ultimoDigito; i++){
             if(totalNeto.charAt(i)== '.'){
                 break;
             }else{
                 totalEntero = totalEntero + totalNeto.charAt(i);
             }
        }
        return totalEntero;
    }
    
    public String separarParteDecimalTotal(String totalNeto){
        String totalDecimal="";
        int ultimoDigito = totalNeto.length();
        
        for(int i=0; i<ultimoDigito; i++){
             if(totalNeto.charAt(i)== '.'){
                 totalDecimal = totalDecimal + totalNeto.charAt(i+1);
                 totalDecimal = totalDecimal + totalNeto.charAt(i+2);
             }else{
                 
             }
        }
        return totalDecimal;
    }
    
    public String numToText(int num){
        numberToText numero;
	String res;
		numero = new numberToText(num);
		res = numero.convertirLetras(num);
		System.out.print(res.toUpperCase());
		System.out.println("\n");
                return res;
    }
    
    public void imprimirDetalleRecibo() {
        
        Map parametros = new HashMap();
        parametros.put("idEmpleado", idEmpleado);
        parametros.put("idLiquidacion", idLiquidacion);
        parametros.put("idBanco", idBanco);
        parametros.put("idTipoLiquidacion", idTipoLiquidacion);
        
        JasperReport reporteJasper = null;
        String rutaInforme = "C:/Users/CX/Documents/NetBeansProjects/"
                + "SistemaKiosco - v2/src/Reportes/reciboSueldo.jasper";
        try {
        reporteJasper = (JasperReport) JRLoader.loadObjectFromFile(rutaInforme);
        JasperPrint informe = JasperFillManager.fillReport(reporteJasper, parametros, 
                sismain.getConexion().getConection());
        JasperViewer visor = new JasperViewer(informe, false);
        visor.setTitle("Recibo de Sueldo");
        visor.setVisible(true);
        } catch (HeadlessException | JRException e) {
            JOptionPane.showMessageDialog(null, "Error en el reporte", "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*public void comprobarReciboGenerado(long idLiquidacion){
        ArrayList<Long> indices;
        indices = reciboSueldo.buscarBD("'"+idLiquidacion+"'", "R.LIQ_IND_SUELDO_ID_LIQ_IND", null);
        if(indices.get(0).equals("")){
            reciboSueldo.guardarBD();
        }
    }*/
    
    public void sumar(){
        double sumatoria = 0, sumatoriaTotal = 0, haberesRemunerativos = 0,
                haberesNoRemunerativos = 0, retenciones = 0;
        totalNeto = 0;        
        int totalRow = tablaConceptos.getRowCount();
        totalRow-=1;
        
        for(int i=0;i<=totalRow;i++){
            sumatoria = Double.valueOf(String.valueOf(tablaConceptos.getValueAt(i,3)));
            sumatoriaTotal += sumatoria;
        }
        haberesRemunerativos = sumatoriaTotal;
        txtHaberesRemunerativos.setText(String.valueOf(haberesRemunerativos));
        
        sumatoria = 0;
        sumatoriaTotal = 0;
        totalRow = tablaConceptos.getRowCount();
        totalRow-=1;
        
        for(int i=0;i<=totalRow;i++){
            sumatoria = Double.valueOf(String.valueOf(tablaConceptos.getValueAt(i,4)));
            sumatoriaTotal += sumatoria;
        }
        haberesNoRemunerativos = sumatoriaTotal;
        txtHaberesNoRemunerativos.setText(String.valueOf(haberesNoRemunerativos));
        
        sumatoria = 0;
        sumatoriaTotal = 0;
        totalRow = tablaConceptos.getRowCount();
        totalRow-=1;
        
        for(int i=0;i<=totalRow;i++){
            sumatoria = Double.valueOf(String.valueOf(tablaConceptos.getValueAt(i,5)));
            sumatoriaTotal += sumatoria;
        }
        retenciones = sumatoriaTotal;
        txtRetenciones.setText(String.valueOf(retenciones));
        
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCuil = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNroRecibo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtTipoLiquidacion = new javax.swing.JTextField();
        txtFechaIngreso = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        comboFuncionEmpleado = new javax.swing.JComboBox<>();
        comboPeriodo = new javax.swing.JComboBox<>();
        txtFechaLiquidacion = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaConceptos = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtHaberesRemunerativos = new javax.swing.JTextField();
        txtHaberesNoRemunerativos = new javax.swing.JTextField();
        txtRetenciones = new javax.swing.JTextField();
        lblTotalNeto = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        comboBanco = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        txtNroCuenta = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTotalNetoEscrito = new javax.swing.JTextField();
        txtTotalNeto = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btnImprimirRecibo = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Detalles del Recibo de Sueldo");

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CUIL:");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("APELLIDO Y NOMBRE:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("TIPO DE LIQUIDACION:");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("FECHA DE INGRESO:");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("PERIODO DE LIQUIDACION:");

        txtCuil.setBackground(new java.awt.Color(0, 0, 51));
        txtCuil.setForeground(new java.awt.Color(255, 255, 255));

        txtNombre.setBackground(new java.awt.Color(0, 0, 51));
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("NRO DE RECIBO:");

        txtNroRecibo.setBackground(new java.awt.Color(0, 0, 51));
        txtNroRecibo.setForeground(new java.awt.Color(255, 255, 255));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("FUNCION:");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("FECHA DE LIQUIDACION: ");

        txtTipoLiquidacion.setBackground(new java.awt.Color(0, 0, 51));
        txtTipoLiquidacion.setForeground(new java.awt.Color(255, 255, 255));

        txtFechaIngreso.setBackground(new java.awt.Color(0, 0, 51));
        txtFechaIngreso.setForeground(new java.awt.Color(255, 255, 255));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        comboFuncionEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Empleado", "Cajero" }));

        comboPeriodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01/2016", "02/2016", "03/2016", "04/2016", "05/2016" }));

        txtFechaLiquidacion.setBackground(new java.awt.Color(0, 0, 51));
        txtFechaLiquidacion.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(txtCuil, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipoLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(141, 141, 141)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNroRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(comboPeriodo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboFuncionEmpleado, javax.swing.GroupLayout.Alignment.LEADING, 0, 155, Short.MAX_VALUE))
                    .addComponent(txtFechaLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(136, 136, 136))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCuil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTipoLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtNroRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtFechaLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboFuncionEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(comboPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator2)
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));

        tablaConceptos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "CONCEPTO", "UNIDAD", "HABERES C/ DCTO", "HABERES S/ DCTO", "RETENCIONES"
            }
        ));
        jScrollPane1.setViewportView(tablaConceptos);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("TOTALES");

        txtHaberesRemunerativos.setBackground(new java.awt.Color(0, 0, 51));
        txtHaberesRemunerativos.setForeground(new java.awt.Color(255, 255, 255));

        txtHaberesNoRemunerativos.setBackground(new java.awt.Color(0, 0, 51));
        txtHaberesNoRemunerativos.setForeground(new java.awt.Color(255, 255, 255));

        txtRetenciones.setBackground(new java.awt.Color(0, 0, 51));
        txtRetenciones.setForeground(new java.awt.Color(255, 255, 255));

        lblTotalNeto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotalNeto.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalNeto.setText("TOTAL NETO A COBRAR ($):");

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("BANCO:");

        comboBanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Banco Industrial" }));

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("NRO DE CUENTA:");

        txtNroCuenta.setBackground(new java.awt.Color(0, 0, 51));
        txtNroCuenta.setForeground(new java.awt.Color(255, 255, 255));

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("SON PESOS:");

        txtTotalNetoEscrito.setBackground(new java.awt.Color(0, 0, 51));
        txtTotalNetoEscrito.setForeground(new java.awt.Color(255, 255, 255));

        txtTotalNeto.setBackground(new java.awt.Color(0, 0, 51));
        txtTotalNeto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtTotalNeto.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboBanco, 0, 174, Short.MAX_VALUE)
                    .addComponent(txtNroCuenta))
                .addGap(57, 57, 57)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTotalNetoEscrito, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(35, 35, 35)
                                .addComponent(txtHaberesRemunerativos, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(txtHaberesNoRemunerativos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtRetenciones, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(lblTotalNeto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotalNeto, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(24, 24, 24))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtHaberesRemunerativos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRetenciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHaberesNoRemunerativos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTotalNeto)
                            .addComponent(txtTotalNeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(comboBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNroCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))))
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalNetoEscrito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(23, 23, 23))
        );

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));

        btnImprimirRecibo.setBackground(new java.awt.Color(0, 153, 0));
        btnImprimirRecibo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnImprimirRecibo.setForeground(java.awt.Color.white);
        btnImprimirRecibo.setText("Generar Recibo");
        btnImprimirRecibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirReciboActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnImprimirRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImprimirRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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
                            .addComponent(jLabel8)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
        txtCuil.setText("");
        txtNombre.setText("");
        txtTipoLiquidacion.setText("");
        txtFechaIngreso.setText("");
        txtNroRecibo.setText("");
        txtFechaLiquidacion.setText("");
        comboPeriodo.setSelectedIndex(0);
        modeloTabla = (DefaultTableModel) tablaConceptos.getModel();
        txtHaberesRemunerativos.setText("");
        txtHaberesNoRemunerativos.setText("");
        txtRetenciones.setText("");
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnImprimirReciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirReciboActionPerformed
        // TODO add your handling code here:
        ArrayList<Long> indices;
        Calendar calendar = Calendar.getInstance();
        String dia = Integer.toString(calendar.get(Calendar.DATE));
        String mes = "0"+Integer.toString(calendar.get(Calendar.MONTH)+1);
        String anio = Integer.toString(calendar.get(Calendar.YEAR));
        
        reciboSueldo = new ReciboSueldoDetallado();
        reciboSueldo.setFecha(
                sismain.getControladorDate().darFormatoStringOracle(dia, 
                sismain.getControladorDate().mesToTexto(mes), anio));
        reciboSueldo.setIdLiquidacionIndividual(idTipoLiquidacion);
        reciboSueldo.setTotalNetoEscrito(txtTotalNetoEscrito.getText());
        reciboSueldo.setNroRecibo(txtNroRecibo.getText());
        
        indices = reciboSueldo.buscarBD("'"+idLiquidacion+"'", "R.LIQ_IND_SUELDO_ID_LIQ_IND", null);
        System.out.println(indices.size());
        if(indices.size() == 0){
            reciboSueldo.guardarBD();
            JOptionPane.showMessageDialog(null, "Recibo de Sueldo Guardado. "
                    + "Presione de nuevo para generar el recibo", "Mensaje", 
                JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Recibo de Sueldo Generado", "Mensaje", 
                JOptionPane.INFORMATION_MESSAGE);
            imprimirDetalleRecibo(); 
        }
        
        
         
        
    }//GEN-LAST:event_btnImprimirReciboActionPerformed

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
            java.util.logging.Logger.getLogger(ReciboSueldoDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReciboSueldoDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReciboSueldoDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReciboSueldoDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReciboSueldoDetalle().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimirRecibo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox comboBanco;
    private javax.swing.JComboBox<String> comboFuncionEmpleado;
    private javax.swing.JComboBox<String> comboPeriodo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblTotalNeto;
    private javax.swing.JTable tablaConceptos;
    private javax.swing.JTextField txtCuil;
    private javax.swing.JTextField txtFechaIngreso;
    private javax.swing.JTextField txtFechaLiquidacion;
    private javax.swing.JTextField txtHaberesNoRemunerativos;
    private javax.swing.JTextField txtHaberesRemunerativos;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNroCuenta;
    private javax.swing.JTextField txtNroRecibo;
    private javax.swing.JTextField txtRetenciones;
    private javax.swing.JTextField txtTipoLiquidacion;
    private javax.swing.JTextField txtTotalNeto;
    private javax.swing.JTextField txtTotalNetoEscrito;
    // End of variables declaration//GEN-END:variables
}
