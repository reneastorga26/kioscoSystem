/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;
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
public class ReciboSueldo extends javax.swing.JFrame {

    private Empleado empleado = new Empleado();
    private ReciboSueldoDetallado reciboSueldoDetallado = new ReciboSueldoDetallado();
    private DefaultTableModel modeloTabla;
    private String dato = "", datoEmpleado = "", columnaBusqueda = "", 
            periodo = "", tipo = "";
    private LiquidacionIndividualSueldo liquidacionIndividualSueldo;
    private double totalNeto;
    private long ultimoIndice;
    private String nroRecibo;
    private String totalNetoEscrito;
    
    //DEFINIR VARIABLES PARA EL RECIBO DE SUELDO
    private long idEmpleado;
    private long idLiquidacion;
    private long idBanco;
    private long idTipoLiquidacion;
    //--------------------------------
    /**
     * Creates new form ReciboSueldo
     */
    public ReciboSueldo() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Recibos de Sueldo");
        modeloTabla = (DefaultTableModel) tablaLiquidaciones.getModel();
        tablaLiquidaciones.setModel(modeloTabla);
        this.btnImprimirMultiplesRecibos.setEnabled(false);
        
        /*tablaLiquidaciones.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaLiquidaciones.getColumnModel().getColumn(0).setMinWidth(0);
        tablaLiquidaciones.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        tablaLiquidaciones.getColumnModel().getColumn(3).setMaxWidth(0);
        tablaLiquidaciones.getColumnModel().getColumn(3).setMinWidth(0);
        tablaLiquidaciones.getColumnModel().getColumn(3).setPreferredWidth(0);
        */
    }

    public void nuevoRecibo(){
        ultimoIndice = sismain.getControladorBD().obtenerUltimoIndice("RECIBO_SUELDO");
        long indice = ultimoIndice +1;
        if(ultimoIndice>0 && ultimoIndice<10){
            nroRecibo = "000000"+indice;
        }
        if(ultimoIndice>10 && ultimoIndice<100){
            nroRecibo = "00000"+indice;
        }
        if(ultimoIndice>100 && ultimoIndice<1000){
            nroRecibo = "0000"+indice;
        }
        if(ultimoIndice>1000 && ultimoIndice<10000){
            nroRecibo = "000"+indice;
        }
        if(ultimoIndice>10000 && ultimoIndice<100000){
            nroRecibo = "00"+indice;
        }
        if(ultimoIndice>100000 && ultimoIndice<1000000){
            nroRecibo = "0"+indice;
        }
        if(ultimoIndice>1000000 && ultimoIndice<10000000){
            nroRecibo = String.valueOf(indice);
        }
    }
    
    
    public void completarCampos(long idEmpleado, long idLiquidacion){
        
        //Completar Nro de recibo
        nuevoRecibo();
        
        empleado = new Empleado();
        liquidacionIndividualSueldo = new LiquidacionIndividualSueldo();
        
        liquidacionIndividualSueldo.ampliarInfoBD(idLiquidacion);
               
        this.idTipoLiquidacion = liquidacionIndividualSueldo.getIdTipoLiquidacion();
        this.idEmpleado = idEmpleado;
        this.idLiquidacion = idLiquidacion;
        this.idBanco = Long.valueOf(1);
        
        empleado.setIdTipoLiquidacion(idTipoLiquidacion);
        empleado.ampliarInfoBD(idEmpleado);
        
                      
        //DATO DEL TOTAL
        totalNeto = liquidacionIndividualSueldo.getImporteNeto();
        
        String totalEntero = separarParteEnteraTotal(String.valueOf(totalNeto));
        String resultado = numToText(Integer.valueOf(totalEntero));
        String totalDecimal = separarParteDecimalTotal(String.valueOf(totalNeto) + "0");
        totalNetoEscrito = resultado.toUpperCase() + " CON " + totalDecimal + "/100";
                
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
    
    public void generarRecibo(long idLiquidacion){
        Calendar calendar = Calendar.getInstance();
        String dia = Integer.toString(calendar.get(Calendar.DATE));
        String mes = "0"+Integer.toString(calendar.get(Calendar.MONTH)+1);
        String anio = Integer.toString(calendar.get(Calendar.YEAR));
        
        
        reciboSueldoDetallado = new ReciboSueldoDetallado();
        reciboSueldoDetallado.setFecha(
                sismain.getControladorDate().darFormatoStringOracle(dia, 
                sismain.getControladorDate().mesToTexto(mes), anio));
        reciboSueldoDetallado.setIdLiquidacionIndividual(idLiquidacion);
        reciboSueldoDetallado.setTotalNetoEscrito(totalNetoEscrito);
        reciboSueldoDetallado.setNroRecibo(nroRecibo);
        reciboSueldoDetallado.guardarBD();
        
    }
    
    public void imprimirDetalleRecibo() {
        try {
        Map parametros = new HashMap();
        parametros.put("idEmpleado", idEmpleado);
        parametros.put("idLiquidacion", idLiquidacion);
        parametros.put("idBanco", idBanco);
        parametros.put("idTipoLiquidacion", idTipoLiquidacion);
        
        JasperReport reporteJasper = null;
        String rutaInforme = "C:/Users/CX/Documents/NetBeansProjects/"
                + "SistemaKiosco - v2/src/Reportes/reciboSueldo.jasper";
        
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
        jLabel14 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        radioPeriodo = new javax.swing.JRadioButton();
        btnAplicarFiltros = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        comboTipo = new javax.swing.JComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCuil = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        radioCuil = new javax.swing.JRadioButton();
        radioNombre = new javax.swing.JRadioButton();
        radioTipo = new javax.swing.JRadioButton();
        comboPeriodo = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaLiquidaciones = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnAmpliarInfo = new javax.swing.JButton();
        btnImprimirMultiplesRecibos = new javax.swing.JButton();
        btnGenerarRecibos = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 23)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Recibos de Sueldos");

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("PERIODO:");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 23)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Liquidaciones Registradas - Filtros :");

        radioPeriodo.setBackground(new java.awt.Color(204, 204, 204));
        radioPeriodo.setForeground(new java.awt.Color(0, 0, 0));
        radioPeriodo.setText("Activar Filtro");

        btnAplicarFiltros.setBackground(new java.awt.Color(51, 0, 51));
        btnAplicarFiltros.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnAplicarFiltros.setForeground(new java.awt.Color(255, 255, 255));
        btnAplicarFiltros.setText("Aplicar Filtros Seleccionados");
        btnAplicarFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarFiltrosActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("TIPO:");

        comboTipo.setBackground(new java.awt.Color(0, 0, 51));
        comboTipo.setForeground(new java.awt.Color(255, 255, 255));
        comboTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 MENSUAL", "2 VACACIONES", "3 AGUINALDO", "4 BAJAS" }));

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("CUIL:");

        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("APELLIDO Y NOMBRE:");

        txtCuil.setBackground(new java.awt.Color(0, 0, 51));
        txtCuil.setForeground(new java.awt.Color(255, 255, 255));
        txtCuil.setText("20111111112");

        txtNombre.setBackground(new java.awt.Color(0, 0, 51));
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));

        radioCuil.setBackground(new java.awt.Color(204, 204, 204));
        radioCuil.setForeground(new java.awt.Color(0, 0, 0));
        radioCuil.setText("Activar Filtro");

        radioNombre.setBackground(new java.awt.Color(204, 204, 204));
        radioNombre.setForeground(new java.awt.Color(0, 0, 0));
        radioNombre.setText("Activar Filtro");

        radioTipo.setBackground(new java.awt.Color(204, 204, 204));
        radioTipo.setForeground(new java.awt.Color(0, 0, 0));
        radioTipo.setText("Activar Filtro");

        comboPeriodo.setBackground(new java.awt.Color(0, 0, 51));
        comboPeriodo.setForeground(new java.awt.Color(255, 255, 255));
        comboPeriodo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05/2016", "04/2016", "03/2016", "02/2016", "01/2016" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator4)
                    .addComponent(jSeparator3)
                    .addComponent(jSeparator6)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboPeriodo, 0, 156, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(radioPeriodo))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAplicarFiltros, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCuil, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(radioCuil))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(radioTipo))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(radioNombre)))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCuil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radioCuil))
                .addGap(15, 15, 15)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radioNombre))
                .addGap(10, 10, 10)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(radioPeriodo)
                    .addComponent(comboPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radioTipo))
                .addGap(43, 43, 43)
                .addComponent(btnAplicarFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        tablaLiquidaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID_EMPLEADO", "CUIL", "APELLIDO Y NOMBRES", "ID_LIQUIDACION", "PERIODO", "FECHA DE LIQUIDACION"
            }
        ));
        jScrollPane1.setViewportView(tablaLiquidaciones);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));

        btnAmpliarInfo.setBackground(new java.awt.Color(51, 0, 51));
        btnAmpliarInfo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnAmpliarInfo.setForeground(new java.awt.Color(255, 255, 255));
        btnAmpliarInfo.setText("Ampliar Informacion del Recibo Seleccionado");
        btnAmpliarInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmpliarInfoActionPerformed(evt);
            }
        });

        btnImprimirMultiplesRecibos.setBackground(new java.awt.Color(51, 0, 51));
        btnImprimirMultiplesRecibos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnImprimirMultiplesRecibos.setForeground(new java.awt.Color(255, 255, 255));
        btnImprimirMultiplesRecibos.setText("Imprimir Multiples Recibos de Sueldos");
        btnImprimirMultiplesRecibos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirMultiplesRecibosActionPerformed(evt);
            }
        });

        btnGenerarRecibos.setBackground(new java.awt.Color(51, 0, 51));
        btnGenerarRecibos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnGenerarRecibos.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerarRecibos.setText("Generar Recibos de Sueldo");
        btnGenerarRecibos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarRecibosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGenerarRecibos, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(btnImprimirMultiplesRecibos)
                .addGap(50, 50, 50)
                .addComponent(btnAmpliarInfo)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAmpliarInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnImprimirMultiplesRecibos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnGenerarRecibos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnAmpliarInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmpliarInfoActionPerformed
        // TODO add your handling code here:
        int row = tablaLiquidaciones.getSelectedRow();
        if(row!=-1){
        ReciboSueldoDetalle reciboSueldoDetalle = new ReciboSueldoDetalle();
        reciboSueldoDetalle.completarCampos(
                Long.valueOf(String.valueOf(tablaLiquidaciones.getValueAt(row, 0))),
                Long.valueOf(String.valueOf(tablaLiquidaciones.getValueAt(row, 3))));
        reciboSueldoDetalle.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "SELECCIONE UN REGISTRO", "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnAmpliarInfoActionPerformed

    private void btnImprimirMultiplesRecibosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirMultiplesRecibosActionPerformed
        // TODO add your handling code here:
        
        if(tablaLiquidaciones.getRowCount() == 0){
            JOptionPane.showMessageDialog(
                    null, "NO HAY DATOS EN LA TABLA",
                    "Mensaje",JOptionPane.ERROR_MESSAGE);
        }else{
        //POR CADA REGISTRO EN LA TABLA GENERAR EL RECIBO DE SUELDO
        for(int i = 0; i<tablaLiquidaciones.getRowCount();i++){
            completarCampos(Long.valueOf(String.valueOf(tablaLiquidaciones.getValueAt(i, 0))),
                Long.valueOf(String.valueOf(tablaLiquidaciones.getValueAt(i, 3))));
            
            imprimirDetalleRecibo();
            System.out.println("GUARDAR DATOS DEL RECIBO EN LA BD");
        System.out.println("ID_LIQ: "+ reciboSueldoDetallado.getIdLiquidacionIndividual());
        System.out.println("FECHA: "+ reciboSueldoDetallado.getFecha());
        System.out.println("TOTAL_ESCRITO: "+ reciboSueldoDetallado.getTotalNetoEscrito());
        System.out.println("NRO_RECIBO: "+ reciboSueldoDetallado.getNroRecibo());
        System.out.println("------------------------------------");
        System.out.println("DATOS DEL RECIBO DE SUELDO");
        System.out.println("ID_EMPLEADO: " + idEmpleado);
        System.out.println("ID_LIQ: " + idLiquidacion);
        System.out.println("ID_BANCO: " + idBanco);
        System.out.println("ID_TIPO_LIQ: " + idTipoLiquidacion);
        System.out.println("//////////////////////////////");
        }
        JOptionPane.showMessageDialog(
                    null, "LA GENERACION DE RECIBOS SE HA REALIZADO CORRECTAMENTE",
                    "Mensaje",JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_btnImprimirMultiplesRecibosActionPerformed

    private void btnAplicarFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarFiltrosActionPerformed
        // TODO add your handling code here:
        boolean todosLosDatos;
        //RADIOCUIL ESTA SELECCIONADO
        if(radioCuil.isSelected()){
            dato = txtCuil.getText();
            columnaBusqueda = "E.CUIL";
            datoEmpleado = columnaBusqueda + "= '" + dato + "'";
        }
        //RADIOCUIL Y RADIONOMBRE ESTAN SELECCIONADOS
        if(radioCuil.isSelected() && radioNombre.isSelected()){
            dato = txtCuil.getText();
            columnaBusqueda = "E.CUIL";
            datoEmpleado = columnaBusqueda + "= '" + dato + "'";
        }
        
        //RADIONOMBRE ESTA SELECCIONADO
        if(radioNombre.isSelected()){
            dato = txtNombre.getText();
            columnaBusqueda = "P.NOMBRE_APELLIDO";
        }
        
         //RADIOPERIODO ESTA SELECCIONADO       
        if(radioPeriodo.isSelected()){
            periodo = String.valueOf(comboPeriodo.getSelectedItem());
        }
        //RADIOTIPO ESTA SELECCIONADO
        if(radioTipo.isSelected()){
            tipo = String.valueOf(comboTipo.getSelectedIndex()+1);
        }
        
        
        //RADIOCUIL Y RADIOPERIODO ACTIVADOS
        if(radioPeriodo.isSelected() && radioCuil.isSelected()){
            columnaBusqueda = "E.CUIL";
            dato = txtCuil.getText();
            periodo = String.valueOf(comboPeriodo.getSelectedItem());
        }
        
        //RADIONOMBRE Y RADIOPERIODO ACTIVADOS
        if(radioPeriodo.isSelected() && radioNombre.isSelected()){
            dato = txtNombre.getText();
            columnaBusqueda = "P.NOMBRE_APELLIDO";
            periodo = String.valueOf(comboPeriodo.getSelectedItem());
        }
        
        //RADIOCUIL Y RADIOTIPO ACTIVADOS
        if(radioTipo.isSelected() && radioCuil.isSelected()){
            columnaBusqueda = "E.CUIL";
            dato = txtCuil.getText();
            tipo = String.valueOf(comboTipo.getSelectedIndex()+1);
        }
        
        //TODOS LOS RADIOS (RADIOCUIL O RADIONOMBRE)ESTAN SELECCIONADOS
        if(dato != "" && radioPeriodo.isSelected() && radioTipo.isSelected()){
            todosLosDatos = true;
        }else{
            todosLosDatos = false;
        }
        //COMPLETAR LA TABLA LIQUDIACIONES
        try{
        reciboSueldoDetallado.infoReciboSueldo(
                       datoEmpleado, periodo, tipo, modeloTabla, todosLosDatos);
        }catch(Exception e){
            JOptionPane.showMessageDialog(
                    null, "NO SE ENCONTRO DATOS",
                    "Mensaje",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAplicarFiltrosActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGenerarRecibosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarRecibosActionPerformed
        // TODO add your handling code here:
        ArrayList<Long> indicesTabla = new ArrayList<>();
        ArrayList<Long> indicesRegistros;
        long indiceEmpleado = 0, auxIdLiquidacion = 0;
        
        //GUARDAR TODOS LOS INDICES DE LIQUIDACION DE LA TABLA
        int row = tablaLiquidaciones.getRowCount();
        for(int i = 0; i<row; i++){
                indicesTabla.add(Long.valueOf(String.valueOf(tablaLiquidaciones.getValueAt(i, 3))));
        }
        //BUSCAR LOS INDICES DE LA TABLA EN LA BD. SI NO ENCUENTRA GENERA RS 
        for(int j = 0; j<row; j++){
                indicesRegistros = reciboSueldoDetallado.buscarBD(
                        String.valueOf(indicesTabla.get(j)), "R.LIQ_IND_SUELDO_ID_LIQ_IND", null);
                indiceEmpleado = Long.valueOf(String.valueOf(tablaLiquidaciones.getValueAt(j, 0)));
                auxIdLiquidacion = indicesTabla.get(j);
                if(indicesRegistros.isEmpty()){
                    System.out.println("AQUI DEBE GENERAR RECIBO DE LIQ " + auxIdLiquidacion);
                    completarCampos(indiceEmpleado, auxIdLiquidacion);
                    generarRecibo(auxIdLiquidacion);
                }
        }
        this.btnImprimirMultiplesRecibos.setEnabled(true);
        JOptionPane.showMessageDialog(
                    null, "LA GENERACION DE RECIBOS SE HA REALIZADO CORRECTAMENTE",
                    "Mensaje",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnGenerarRecibosActionPerformed

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
            java.util.logging.Logger.getLogger(ReciboSueldo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReciboSueldo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReciboSueldo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReciboSueldo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReciboSueldo().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAmpliarInfo;
    private javax.swing.JButton btnAplicarFiltros;
    private javax.swing.JButton btnGenerarRecibos;
    private javax.swing.JButton btnImprimirMultiplesRecibos;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox comboPeriodo;
    private javax.swing.JComboBox comboTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JRadioButton radioCuil;
    private javax.swing.JRadioButton radioNombre;
    private javax.swing.JRadioButton radioPeriodo;
    private javax.swing.JRadioButton radioTipo;
    private javax.swing.JTable tablaLiquidaciones;
    private javax.swing.JTextField txtCuil;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
