
package UI;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.Fabricante;
import model.Precio;
import model.Producto;
import model.Proveedor;
import model.TipoProducto;

/**
 *
 * @author IgnacioMatias
 */
public class AMProducto extends javax.swing.JFrame {
    
    private ArrayList<ArrayList<Object>> registroProducto;
    private ArrayList<ArrayList<Object>> registrosProveedores;
    private ArrayList<ArrayList<Object>> registrosProveedoresFiltrados;
    private ArrayList<ArrayList<Object>> registrosPreciosAgregados
            = new ArrayList<ArrayList<Object>>();
    private ArrayList<ArrayList<Object>> registrosPreciosModificados
            = new ArrayList<ArrayList<Object>>();
    private ArrayList<ArrayList<Object>> registrosPreciosEliminados
            = new ArrayList<ArrayList<Object>>();
    private TableRowSorter trs;
    private DefaultTableModel modeloTabla;
    private ArrayList<ArrayList<Object>> registrosTipoProducto;
    private ArrayList<ArrayList<Object>> registrosFabricante;
    private Producto producto = new Producto();
    private TipoProducto tipoProducto = new TipoProducto();
    private Fabricante fabricante = new Fabricante();
    private Proveedor proveedor = new Proveedor();
    private int idProducto;
    private int opcion;
    private long idForm;
    /**
     * Creates new form ABMProducto
     */
    
    public AMProducto(Producto producto, int opcion, int idProducto) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.opcion=opcion;
        this.idProducto = idProducto;
        iniciarInterfaz();
        if(opcion==1){
            setModoA();
        }
        else{
            setModoM();
            }
        
        radioAgregar.setSelected(true);
        radioMayorista.setSelected(true);
        radioMinorista.setSelected(true);
        
    }
    
    public void iniciarInterfaz(){
        comboFiltro.setSelectedIndex(0);
        registrosProveedores=proveedor.obtenerRazonSocialTodos();
        registrosTipoProducto=tipoProducto.obtenerDescripcionTodos();
        registrosFabricante=fabricante.obtenerDescripcionTodos();
        comboTipoProducto.addItem("SELECCIONE TIPO");
        comboTipoProducto.addItem("____________________");
        comboFabricante.addItem("SELECCIONE FABRICANTE");
        comboFabricante.addItem("__________________");
        comboEstado.addItem("SELECCIONE ESTADO");
        comboEstado.addItem("___________________");
        comboEstado.addItem("Habilitado");
        comboEstado.addItem("Deshabilitado");
        for(int i=0;i<registrosTipoProducto.size();i++){
            comboTipoProducto.addItem(registrosTipoProducto.get(i)
                    .get(1).toString());
        }
        for(int i=0;i<registrosFabricante.size();i++){
            comboFabricante.addItem(registrosFabricante.get(i)
                    .get(1).toString());
        }
        //Falta Renderizar y desactivar editor.
        this.modeloTabla=(DefaultTableModel) tablaPreciosCompra.getModel();
        trs = new TableRowSorter<TableModel>(modeloTabla);
        tablaPreciosCompra.setRowSorter(trs);     
        this.actualizarCombo(comboFiltro,false);
    }
    
    public void setModoA(){
            lblHeading.setText("Nuevo Producto");
            lblHeading.setForeground(new Color(0,255,0));
            btnGuardar.setText("Guardar");
            btnGuardar.setBackground(new Color(0,153,0));
            radioAgregar.setSelected(true);
            radioModificar.setEnabled(false);
            radioEliminar.setEnabled(false);
    }
    
    public void setModoM(){
            long valorInteres = 0;
            lblHeading.setText("Modificar Informacion de Producto");
            lblHeading.setForeground(new Color(204,204,204));
            producto.ampliarInfoBD(idProducto);
            radioAgregar.setSelected(true);
            btnGuardar.setText("Guardar Cambios");
            btnGuardar.setBackground(new Color(51,0,51));
            
            actualizarRegistrosProveedoresActuales();
            actualizarTabla();
            
            txtCodigo.setText(String.valueOf(producto.getCodigo()));
            txtDescripcion.setText(producto.getDescripcion());
            
            //Asigno Valores a Combos en Modificar
            valorInteres=producto.getIdTipoProducto();
            for(int i = 0; i<registrosTipoProducto.size();i++){
                if(Long.parseLong(registrosTipoProducto.get(i)
                        .get(0).toString())== valorInteres){
                    comboTipoProducto.setSelectedIndex(i+2);
                    break;
                }
            }
            valorInteres=producto.getIdFabricante();
            for(int i = 0; i<registrosFabricante.size();i++){
                if(Long.parseLong(registrosFabricante.get(i).get(0).toString()) 
                        == valorInteres){
                    comboFabricante.setSelectedIndex(i+2);
                    break;
                }
            }
            
            lblEstadoStock.setText(producto.traductorEstadoStock(String.valueOf(
                    producto.getEstadoStock()), false));
            
            if(producto.getEstado()=='H'){
                comboEstado.setSelectedIndex(2);
            }
            else{
                comboEstado.setSelectedIndex(3);
            }
            
            txtStockActual.setText(String.valueOf(producto.getStockActual()));
            txtPuntoPedido.setText(String.valueOf(producto.getPuntoPedido()));
            txtStockMinimo.setText(String.valueOf(producto
                    .getStockCriticoMinimo()));
    }
    
    public void actualizarTabla(){

            for(int i=0; i<producto.getPreciosCompra().size();i++){
                Object[] fila = new Object[5];
                fila[0]=producto.getPreciosCompra().get(i).getIdPrecio();
                long idProveedor=producto.getPreciosCompra()
                        .get(i).getIdProveedor();
                for(int j=0; j<registrosProveedoresFiltrados.size(); j++){
                    if(idProveedor==Long.parseLong(registrosProveedoresFiltrados
                            .get(i).get(0).toString())){
                        fila[1]=registrosProveedoresFiltrados.get(i).get(1);
                    }
                }
                fila[2]=producto.getPreciosCompra().get(i).getMontoMinorista();
                fila[3]=producto.getPreciosCompra().get(i).getMontoMayorista();
                fila[4]=0;
                modeloTabla.addRow(fila);
            }
        
        if(modeloTabla.getRowCount()==0){
            radioAgregar.setEnabled(true);
            radioModificar.setEnabled(false);
            radioEliminar.setEnabled(false);
        }
        else{
            radioAgregar.setEnabled(true);
            radioModificar.setEnabled(true);
            radioEliminar.setEnabled(true);
        }
        
    }
    
    public void filtrarTabla(String proveedor){
        trs.setRowFilter(RowFilter.regexFilter(proveedor,1));
    }
    
    public void actualizarCombo(JComboBox combo, boolean filtroCombo){
        combo.removeAllItems();
        if(filtroCombo == true){
        combo.addItem("PROVEEDORES DEL PRODUCTO");
        combo.addItem("________________________");
        for(int i=0;i<producto.getPreciosCompra().size();i++){
            long idProveedor = producto.getPreciosCompra()
                    .get(i).getIdProveedor();
            for(int j=0;j<registrosProveedoresFiltrados.size();j++){
                if(Long.parseLong(registrosProveedoresFiltrados.get(j)
                        .get(0).toString())==idProveedor){
                    combo.addItem(registrosProveedoresFiltrados.get(j).get(1));
                    break;
                }
            }
        }
        }
        else{
            combo.addItem("TODOS");
            combo.addItem("________________");
            for(int i=0;i<registrosProveedores.size();i++){
                combo.addItem(registrosProveedores.get(i).get(1));
            }
            
        }
    }
   
    public void actualizarRegistrosProveedoresActuales(){
        registrosProveedoresFiltrados.clear();
        for(int i = 0; i<producto.getPreciosCompra().size();i++){
            for (ArrayList<Object> registrosProveedore : registrosProveedores) {
                if(producto.getPreciosCompra().get(i).getIdProveedor()
                        == Long.parseLong(registrosProveedores.get(i)
                                .get(0).toString())
                        && producto.getPreciosCompra().get(i).getFechaHoraFin()
                                .equals("")){
                    registrosProveedoresFiltrados.add(registrosProveedores.get(i));
                    break;
                }
            }
        }
    }
    
    public void formularioPrecioCompra(int opcion){
        
        switch(opcion){
            case 1: radioModificar.setSelected(false);
                    radioEliminar.setSelected(false);
                    btnFormulario.setText("Agregar");
                    lblTitulo.setText("AGREGAR NUEVO VINCULO PROVEEDOR-PRODUCTO");
                    actualizarCombo(comboForm,false);break;
            case 2: radioAgregar.setSelected(false);
                    radioEliminar.setSelected(false);
                    btnFormulario.setText("Modificar");
                    lblTitulo.setText("MODIFICAR VINCULO ENTRE PROVEEDOR-PRODUCTO");
                    actualizarCombo(comboForm,true);break;
            case 3: radioAgregar.setSelected(false);
                    radioEliminar.setSelected(false);
                    btnFormulario.setText("Eliminar");
                    lblTitulo.setText("DESVINCULAR PROVEEDOR DEL PRODUCTO");
                    actualizarCombo(comboForm,true);break;
        }

    }
    
    public void limpiarTabla(){
             for(int i=modeloTabla.getRowCount(); i>0;i--){
             modeloTabla.removeRow(i-1);
        }
    }
    
    public void completarFormulario(int opcion){
        idForm=-1;
        int indexTabla=-1;
        String proveedor;
        if(opcion == 1){
            indexTabla = tablaPreciosCompra.getSelectedRow();
        }
        else{
            int index_combo = comboForm.getSelectedIndex();
            if (index_combo != 0 || index_combo !=1){
            proveedor = comboForm.getSelectedItem().toString();
            for(int i=0; i<tablaPreciosCompra.getRowCount();i++){
                if(tablaPreciosCompra.getValueAt(i,2)
                        .equals(proveedor)){
                    indexTabla = tablaPreciosCompra.getSelectedRow();
                }
            }
        }}
        if(indexTabla!=-1){
            if(radioModificar.isSelected()|| radioEliminar.isSelected()){
                idForm =Long.parseLong(tablaPreciosCompra
                        .getValueAt(indexTabla, 0).toString());
                proveedor = 
                        tablaPreciosCompra.getValueAt(indexTabla, 1).toString();
                for (int i = 0; i<comboForm.getItemCount();i++){
                    if(comboForm.getItemAt(i).toString()
                            .equals(proveedor)){
                        comboForm.setSelectedIndex(i);
                        break;
                    }
                }
                
                if(tablaPreciosCompra.getValueAt(indexTabla,3)!=null){
                    radioMayorista.setSelected(true);
                    txtPrecioMayorista
                            .setText(tablaPreciosCompra.getValueAt(indexTabla,3)
                                    .toString());
                }
                else{
                    radioMayorista.setSelected(false);
                    txtPrecioMayorista.setText("No permitido");
                }
                if(tablaPreciosCompra.getValueAt(indexTabla,3)!=null){
                    radioMinorista.setSelected(true);
                    txtPrecioMinorista
                            .setText(tablaPreciosCompra.getValueAt(indexTabla,2)
                                    .toString());
                }
                else{
                    radioMinorista.setSelected(false);
                    txtPrecioMinorista.setText("No permitido");
                }
            }
        }
    }
    

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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
        lblHeading = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtUnidadesxMayor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtStockActual = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPuntoPedido = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtStockMinimo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        comboTipoProducto = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblEstadoStock = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        comboEstado = new javax.swing.JComboBox<>();
        comboFabricante = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtPrecioVenta = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPreciosCompra = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        comboFiltro = new javax.swing.JComboBox<>();
        radioAgregar = new javax.swing.JRadioButton();
        radioModificar = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        comboForm = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        radioMinorista = new javax.swing.JRadioButton();
        radioMayorista = new javax.swing.JRadioButton();
        txtPrecioMinorista = new javax.swing.JTextField();
        txtPrecioMayorista = new javax.swing.JTextField();
        btnFormulario = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        radioEliminar = new javax.swing.JRadioButton();
        lblTitulo = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        checkIncluir = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtPrecioVenta1 = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel26 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JSeparator();
        jTextField2 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nuevo Producto");

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setForeground(new java.awt.Color(0, 0, 102));

        lblHeading.setBackground(new java.awt.Color(255, 255, 255));
        lblHeading.setFont(new java.awt.Font("Dialog", 0, 23)); // NOI18N
        lblHeading.setForeground(new java.awt.Color(0, 255, 0));
        lblHeading.setText("Datos del Nuevo Producto");

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("CODIGO:");

        txtCodigo.setForeground(new java.awt.Color(0, 0, 102));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("DESCRIPCION:");

        txtDescripcion.setForeground(new java.awt.Color(0, 0, 102));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("UNIDADES PACK MAYORISTA:");

        txtUnidadesxMayor.setForeground(new java.awt.Color(0, 0, 102));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("STOCK ACTUAL (U):");

        txtStockActual.setForeground(new java.awt.Color(0, 0, 102));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("PUNTO DE PEDIDO (U):");

        txtPuntoPedido.setForeground(new java.awt.Color(0, 0, 102));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("STOCK MINIMO (U):");

        txtStockMinimo.setForeground(new java.awt.Color(0, 0, 102));

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("TIPO DE PRODUCTO:");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("FABRICANTE:");

        jLabel12.setText("ESTADO DEL STOCK: ");

        lblEstadoStock.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblEstadoStock.setText("NINGUNO");

        jLabel13.setText("ESTADO DE HABILITACION: ");

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        comboFabricante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFabricanteActionPerformed(evt);
            }
        });

        jLabel7.setText("PRECIO ACTUAL DE VENTA:");

        txtPrecioVenta.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        tablaPreciosCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PROVEEDOR", "ULTIMO PRECIO MINORISTA", "ULTIMO PRECIO MAYORISTA", "RENDERIZADOR"
            }
        ));
        jScrollPane1.setViewportView(tablaPreciosCompra);
        if (tablaPreciosCompra.getColumnModel().getColumnCount() > 0) {
            tablaPreciosCompra.getColumnModel().getColumn(0).setMinWidth(0);
            tablaPreciosCompra.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablaPreciosCompra.getColumnModel().getColumn(0).setMaxWidth(0);
            tablaPreciosCompra.getColumnModel().getColumn(4).setMinWidth(0);
            tablaPreciosCompra.getColumnModel().getColumn(4).setPreferredWidth(0);
            tablaPreciosCompra.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel9.setText("Lista de Proveedores Actuales vinculados al Producto y Precios");

        jLabel15.setText("Filtrar por proveedor:");

        comboFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFiltroActionPerformed(evt);
            }
        });

        radioAgregar.setText("Añadir Nuevo Proveedor del Producto");
        radioAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioAgregarActionPerformed(evt);
            }
        });

        radioModificar.setText("Modificar Datos de Proveedor Vinculado al Producto");

        jLabel16.setText("Acciones:");

        jLabel17.setText("SELECCIONE PROVEEDOR:");

        jLabel18.setText("ULTIMO PRECIO MAYORISTA:");

        jLabel19.setText("ULTIMO PRECIO MINORISTA:");

        radioMinorista.setText("Compra Minorista Permitida");

        radioMayorista.setText("Compra Mayorista Permitida");

        txtPrecioMayorista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioMayoristaActionPerformed(evt);
            }
        });

        btnFormulario.setText("Agregar");
        btnFormulario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFormularioActionPerformed(evt);
            }
        });

        radioEliminar.setText("Desvincular a un Proveedor del Producto");

        lblTitulo.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        lblTitulo.setText("AÑADIR NUEVO PROVEEDOR");

        checkIncluir.setText("Incluir Fila Seleccionada en la Accion");
        checkIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkIncluirActionPerformed(evt);
            }
        });

        jLabel14.setText("Agregados");

        jLabel20.setText("Modificados");

        jLabel21.setText("Desvinculados");

        jLabel22.setText("Sin Cambios");

        jLabel23.setText("Ref. Tabla");

        jLabel24.setText("NUEVO PRECIO DE VENTA:");

        txtPrecioVenta1.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N

        jLabel25.setText("ASISTENTE DE MODIFICACION DE PRECIO DE VENTA ");

        jLabel27.setText("Elegir Accion");

        jRadioButton1.setText("Subir Precio");

        jRadioButton2.setText("Bajar Precio");

        jLabel26.setText("Realizar Accion en Funcion de:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel28.setText("Introduzca porcentaje:");

        jButton1.setText("Calcular");

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jTextField2.setText("Precio Resultante:");

        jLabel29.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        jLabel29.setText("$ 0,00");

        jButton2.setText("Fijar como Nuevo Precio de Venta");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jRadioButton1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jRadioButton2)))
                            .addComponent(jLabel26)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField1)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel29)
                                .addGap(63, 63, 63))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(28, 28, 28))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator9)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(5, 5, 5)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jRadioButton1)
                                    .addComponent(jRadioButton2)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addGap(5, 5, 5)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );

        jLabel30.setText("La Diferencia entre el Precio de Venta Actual y el Nuevo Precio de Venta es de: ");

        jLabel31.setText("Precio");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEstadoStock)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtStockActual)
                                    .addComponent(txtStockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addGap(2, 2, 2)
                                .addComponent(txtPuntoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(22, 22, 22))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel10))
                                .addGap(83, 83, 83)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDescripcion)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(comboTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboFabricante, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(359, 359, 359)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(txtUnidadesxMayor, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(11, 11, 11))
                    .addComponent(jSeparator8)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioVenta1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel31)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(radioEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(radioModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(radioAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(checkIncluir))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(48, 48, 48)
                        .addComponent(comboFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addGap(35, 35, 35)
                            .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblTitulo)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(radioMayorista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(radioMinorista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel19)
                                .addComponent(jLabel18))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtPrecioMinorista, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPrecioMayorista, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator6))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(comboTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(comboFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtUnidadesxMayor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel5)
                                            .addComponent(txtStockActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel8)
                                            .addComponent(txtStockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel6)
                                            .addComponent(txtPuntoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1)))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEstadoStock)
                                    .addComponent(jLabel12)))
                            .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrecioVenta1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtPrecioVenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jSeparator5)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(comboFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21)
                        .addGap(30, 30, 30)))
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioAgregar)
                    .addComponent(jLabel16)
                    .addComponent(checkIncluir))
                .addGap(4, 4, 4)
                .addComponent(radioModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radioMinorista)
                            .addComponent(jLabel18)
                            .addComponent(txtPrecioMinorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(radioMayorista)
                            .addComponent(txtPrecioMayorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnFormulario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGuardar.setBackground(new java.awt.Color(0, 153, 0));
        btnGuardar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnGuardar.setForeground(java.awt.Color.white);
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblHeading)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalir))
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHeading)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardar)
                        .addComponent(btnSalir)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        producto.setCodigo(Long.valueOf(txtCodigo.getText()));
        producto.setDescripcion(txtDescripcion.getText());
        producto.setStockActual(Integer.valueOf(txtStockActual.getText()));
        producto.setStockCriticoMinimo(Integer.valueOf(txtStockMinimo.getText()));
        producto.setPuntoPedido(Integer.valueOf(txtPuntoPedido.getText()));
        producto.setUnidadesPackMayorista(Integer.valueOf(txtUnidadesxMayor.getText()));
        producto.setEstado(comboEstado.getSelectedItem().toString().charAt(0));
        char traduccionEstadoStock = producto.traductorEstadoStock(
                lblEstadoStock.getText(), true).charAt(0);
        producto.setEstadoStock(traduccionEstadoStock);
        tipoProducto.ampliarInfoBD(comboTipoProducto.getSelectedItem().toString(),
                               "DESCRIPCION",null);
        producto.setIdTipoProducto(tipoProducto.getIdTipoProducto());
        fabricante.ampliarInfoBD(comboFabricante.getSelectedItem().toString(),
                                "DESCRIPCION",'H',null);
        
        if(opcion == 1){
            producto.guardarBD();
            JOptionPane.showMessageDialog(null, "El Producto se ha guardado exitosamente","Mensaje",JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            producto.modificarBD();
            JOptionPane.showMessageDialog(null, "El Producto se ha modificado exitosamente","Mensaje",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void comboFabricanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFabricanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboFabricanteActionPerformed

    private void btnFormularioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFormularioActionPerformed
        int indexCombo = comboForm.getSelectedIndex();
        Object[] fila;
        int resultado = -1;
        boolean ban = true;
        ArrayList<Object> nuevoRegistro=null;
        nuevoRegistro = new ArrayList<>();
        
            if(radioAgregar.isSelected()){
            nuevoRegistro.add("");
            resultado=1;
            }
            
            if(radioModificar.isSelected() || radioEliminar.isSelected()){
            nuevoRegistro.add(idForm);
            if(!radioEliminar.isSelected()){
                resultado=2;
            }
            if(!radioModificar.isSelected()){
                resultado=3;
            }
            }
            
            if((indexCombo!=0 || indexCombo!=1)){
                nuevoRegistro.add(comboForm.getSelectedItem());
                ban = false;
            }
            
            if(!ban){
                
            if(radioMinorista.isSelected()){
                nuevoRegistro.add(txtPrecioMinorista.getText());
            }
            else{
                nuevoRegistro.add("");
            }
            
            if(radioMayorista.isSelected()){
                nuevoRegistro.add(txtPrecioMayorista.getText());
            }
            else{
                nuevoRegistro.add("");
            }
            nuevoRegistro.add(1);
            registrosPreciosAgregados.add(nuevoRegistro);
            

        }
       switch(resultado){
           case 1:fila= new Object[5];
                  for(int i=0; i<nuevoRegistro.size();i++){
                  fila[i]=registrosPreciosAgregados.get(i);
                  } 
                  modeloTabla.addRow(fila);
       }
    }//GEN-LAST:event_btnFormularioActionPerformed

    private void comboFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFiltroActionPerformed
 
        int index=comboFiltro.getSelectedIndex();
        if(index!=0 && index!=1){
            filtrarTabla(comboFiltro.getSelectedItem().toString());
        }
        else{
            if(index==0){
                filtrarTabla(""); 
            }
        }
        
    }//GEN-LAST:event_comboFiltroActionPerformed

    private void radioAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioAgregarActionPerformed

    }//GEN-LAST:event_radioAgregarActionPerformed

    private void checkIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkIncluirActionPerformed
        if(radioAgregar.isSelected()){
            
        }
    }//GEN-LAST:event_checkIncluirActionPerformed

    private void txtPrecioMayoristaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioMayoristaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioMayoristaActionPerformed

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
            java.util.logging.Logger.getLogger(AMProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AMProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AMProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AMProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFormulario;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JCheckBox checkIncluir;
    private javax.swing.JComboBox<String> comboEstado;
    private javax.swing.JComboBox<String> comboFabricante;
    private javax.swing.JComboBox<String> comboFiltro;
    private javax.swing.JComboBox<String> comboForm;
    private javax.swing.JComboBox<String> comboTipoProducto;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lblEstadoStock;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JRadioButton radioAgregar;
    private javax.swing.JRadioButton radioEliminar;
    private javax.swing.JRadioButton radioMayorista;
    private javax.swing.JRadioButton radioMinorista;
    private javax.swing.JRadioButton radioModificar;
    private javax.swing.JTable tablaPreciosCompra;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtPrecioMayorista;
    private javax.swing.JTextField txtPrecioMinorista;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtPrecioVenta1;
    private javax.swing.JTextField txtPuntoPedido;
    private javax.swing.JTextField txtStockActual;
    private javax.swing.JTextField txtStockMinimo;
    private javax.swing.JTextField txtUnidadesxMayor;
    // End of variables declaration//GEN-END:variables
}
