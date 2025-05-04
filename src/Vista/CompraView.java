package Vista;


import Controlador.CompraControlador;
import Modelo.CompraModelo;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CompraView extends JFrame {
    private JButton agregarProductoButton;
    private JButton agregarCompraButton;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JButton mostrarButton;
    private JTextField Nombre_Proveedor;
    private JTextField Direccion;
    private JTextField Telefono;
    private JTextField Cod_Compra;
    private JTextField Cod_Usuario;
    private JTextField Fecha_Compra;
    private JTextField Producto_Comprado;
    private JTextField Cantidad_Producto_Kg;
    private JTextField Valor_Unitario;
    private JTextField Total_Producto;
    private JTextField Total_Compra;
    private JLabel COD_SUPPLIER;
    private JLabel SUPPLIER_NAME;
    private JLabel ADDRESS;
    private JLabel PHONE_NUMBER;
    private JTable table1P;
    private JScrollPane scrollPane1P;
    private JLabel COD_PURCHASE;
    private JLabel COD_USER;
    private JLabel COD_SUPPLIERC;
    private JLabel DATE_PURCHASE;
    private JLabel PURCHASED_PRODUCT;
    private JLabel QUANTITY_Kg;
    private JLabel UNIT_VALUE;
    private JLabel TOTAL_PRODUCT;
    private JLabel TOTAL_PURCHASE_VALUE;
    private JTable table1;
    private JTextField Cod_Proveedor;
    private JTextField Cod_ProveedorC;
    private JPanel panelCompras;
    private JButton mostrarComprasButton;
    private JButton volverButton;
    private JScrollPane scrollPane;

    public CompraView() {
        setContentPane(panelCompras);

        // Establecer fecha actual
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatofecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Fecha_Compra.setText(fechaActual.format(formatofecha));

        // Configuración de campos no editables
        Cod_ProveedorC.setEditable(false);
        Total_Producto.setEditable(false);
        Total_Compra.setEditable(false);
        Fecha_Compra.setEditable(false);

        // Configurar modelos de tablas
        DefaultTableModel modeloProveedores = new DefaultTableModel();
        modeloProveedores.addColumn("Cod_Proveedor");
        modeloProveedores.addColumn("Nombre_Proveedor");
        modeloProveedores.addColumn("Direccion");
        modeloProveedores.addColumn("Telefono");
        table1P.setModel(modeloProveedores);

        DefaultTableModel modeloCompras = new DefaultTableModel();
        modeloCompras.addColumn("Cod_Compra");
        modeloCompras.addColumn("Cod_Administrador");
        modeloCompras.addColumn("Cod_Proveedor");
        modeloCompras.addColumn("Fecha_Compra");
        modeloCompras.addColumn("Producto_Comprado");
        modeloCompras.addColumn("Valor_Unitario");
        modeloCompras.addColumn("Cantidad_Producto_Kg");
        modeloCompras.addColumn("Total_Producto");
        modeloCompras.addColumn("Total_Compra");
        table1.setModel(modeloCompras);
    }

    // Métodos para añadir listeners a los botones
    public void addAgregarButtonListener(java.awt.event.ActionListener listener) {
        agregarButton.addActionListener(listener);
    }

    public void addModificarButtonListener(java.awt.event.ActionListener listener) {
        modificarButton.addActionListener(listener);
    }

    public void addEliminarButtonListener(java.awt.event.ActionListener listener) {
        eliminarButton.addActionListener(listener);
    }

    public void addMostrarButtonListener(java.awt.event.ActionListener listener) {
        mostrarButton.addActionListener(listener);
    }

    public void addAgregarProductoButtonListener(java.awt.event.ActionListener listener) {
        agregarProductoButton.addActionListener(listener);
    }

    public void addAgregarCompraButtonListener(java.awt.event.ActionListener listener) {
        agregarCompraButton.addActionListener(listener);
    }

    public void addMostrarComprasButtonListener(java.awt.event.ActionListener listener) {
        mostrarComprasButton.addActionListener(listener);
    }

    public void addVolverButtonListener(java.awt.event.ActionListener listener) {
        volverButton.addActionListener(listener);
    }

    // Añadir KeyListeners para calcular totales
    public void addCantidadKeyListener(KeyAdapter keyAdapter) {
        Cantidad_Producto_Kg.addKeyListener(keyAdapter);
    }

    public void addValorUnitarioKeyListener(KeyAdapter keyAdapter) {
        Valor_Unitario.addKeyListener(keyAdapter);
    }

    // Método para añadir ListSelectionListener a la tabla de proveedores
    public void addTableSelectionListener(ListSelectionListener listener) {
        table1P.getSelectionModel().addListSelectionListener(listener);
    }

    // Métodos para obtener datos de los campos
    public String getNombreProveedor() {
        return Nombre_Proveedor.getText();
    }

    public String getDireccion() {
        return Direccion.getText();
    }

    public String getTelefono() {
        return Telefono.getText();
    }

    public String getCodProveedor() {
        return Cod_Proveedor.getText();
    }

    public String getCodProveedorC() {
        return Cod_ProveedorC.getText();
    }

    public String getCodCompra() {
        return Cod_Compra.getText();
    }

    public String getCodUsuario() {
        return Cod_Usuario.getText();
    }

    public String getFechaCompra() {
        return Fecha_Compra.getText();
    }

    public String getProductoComprado() {
        return Producto_Comprado.getText();
    }

    public String getCantidadProductoKg() {
        return Cantidad_Producto_Kg.getText().trim();
    }

    public String getValorUnitario() {
        return Valor_Unitario.getText().trim();
    }

    public String getTotalProducto() {
        return Total_Producto.getText().trim();
    }

    public String getTotalCompra() {
        return Total_Compra.getText().trim();
    }

    // Métodos para establecer datos en los campos
    public void setCodProveedor(String codProveedor) {
        Cod_Proveedor.setText(codProveedor);
    }

    public void setCodProveedorC(String codProveedorC) {
        Cod_ProveedorC.setText(codProveedorC);
    }

    public void setCodCompra(String codCompra) {
        Cod_Compra.setText(codCompra);
    }

    public void setTotalProducto(String totalProducto) {
        Total_Producto.setText(totalProducto);
    }

    public void setTotalCompra(String totalCompra) {
        Total_Compra.setText(totalCompra);
    }

    // Métodos para actualizar las tablas
    public void actualizarTablaProveedores(Object[][] datos) {
        DefaultTableModel model = (DefaultTableModel) table1P.getModel();
        model.setRowCount(0);

        for (Object[] fila : datos) {
            model.addRow(fila);
        }
    }

    public void actualizarTablaCompras(Object[][] datos) {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0);

        for (Object[] fila : datos) {
            model.addRow(fila);
        }
    }

    // Métodos para mostrar mensajes
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Método para obtener la fila seleccionada en la tabla de proveedores
    public int getFilaSeleccionadaProveedores() {
        return table1P.getSelectedRow();
    }

    // Método para obtener datos de la tabla de proveedores
    public Object getValorTablaProveedores(int fila, int columna) {
        return table1P.getValueAt(fila, columna);
    }

    public static void mostrarVentanaCompras() {
        CompraModelo modelo = new CompraModelo();
        CompraView vista = new CompraView();
        CompraControlador controlador = new CompraControlador(modelo, vista);

        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setVisible(true);
        vista.pack();
    }
}

