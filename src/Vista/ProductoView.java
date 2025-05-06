package Vista;

import javax.swing.*;
import Controlador.ProductoControlador;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;

import Modelo.ProductoModelo;


public class ProductoView extends JFrame {
    JPanel panelProductos;
    JTextField Producto1;
    JTextField NombreProducto1;
    JTextField Compra1;
    JTextField Descripcion1;
    JTextField PrecioProducto1;
    JTextField PrecioCompra1;
    JTextField Cantidad1;
    JTextField stock;
    JTextField CantidadFinal;
    JButton agregarButton;
    JButton eliminarButton;
    JButton modificarButton;
    JButton mostrarButton;
    JTable table1;
    JButton volverButton;
    JLabel COD_PRODUCT;
    JLabel COD_PURCHASE;
    JLabel PRODUCT_NAME;
    JLabel QUANTITY_Kg;
    JLabel PURCHASE_VALUE;
    JLabel PRODUCT_PRICE;
    JLabel DESCRIPTION_PRODUCT_STATUS;
    JLabel STOCK;
    JLabel FINAL_QUANTITY_Kg;
    JScrollPane scrollPane;
    JScrollPane scrollPaneCP;
    JButton listaComprasButton;

    // Constructor
    public ProductoView() {
        setSize(600, 600);
        setLocationRelativeTo(null);
        setContentPane(panelProductos);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Métodos para configurar los listeners de los botones
    public void agregarListenerAgregar(ActionListener listener) {
        agregarButton.addActionListener(listener);
    }

    public void agregarListenerModificar(ActionListener listener) {
        modificarButton.addActionListener(listener);
    }

    public void agregarListenerEliminar(ActionListener listener) {
        eliminarButton.addActionListener(listener);
    }

    public void agregarListenerMostrar(ActionListener listener) {
        mostrarButton.addActionListener(listener);
    }

    public void agregarListenerVolver(ActionListener listener) {
        volverButton.addActionListener(listener);
    }

    // Configurar listener para la tabla de productos
    public void configurarTablaProductos() {
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table1.getSelectedRow();
                if (row != -1) {
                    Producto1.setText(table1.getValueAt(row, 0).toString());
                    Compra1.setText(table1.getValueAt(row, 1).toString());
                    NombreProducto1.setText(table1.getValueAt(row, 2).toString());
                    stock.setText(table1.getValueAt(row, 3).toString());
                    Cantidad1.setText(table1.getValueAt(row, 4).toString());
                    CantidadFinal.setText(table1.getValueAt(row, 5).toString());
                    PrecioCompra1.setText(table1.getValueAt(row, 6).toString());
                    PrecioProducto1.setText(table1.getValueAt(row, 7).toString());
                    Descripcion1.setText(table1.getValueAt(row, 8).toString());
                }
            }
        });
    }



    // Métodos para obtener los valores de los campos
    public String getCodigoProducto() {
        return Producto1.getText();
    }

    public String getCodigoCompra() {
        return Compra1.getText();
    }

    public String getNombreProducto() {
        return NombreProducto1.getText();
    }

    public String getStock() {
        return stock.getText();
    }

    public String getCantidad() {
        return Cantidad1.getText();
    }

    public String getCantidadFinal() {
        return CantidadFinal.getText();
    }

    public String getPrecioCompra() {
        return PrecioCompra1.getText();
    }

    public String getPrecioProducto() {
        return PrecioProducto1.getText();
    }

    public String getDescripcion() {
        return Descripcion1.getText();
    }

    // Método para actualizar el código de producto generado
    public void setCodigoProducto(int codigo) {
        Producto1.setText(String.valueOf(codigo));
    }

    // Métodos para actualizar las tablas
    public void actualizarTablaProductos(DefaultTableModel modelo) {
        table1.setModel(modelo);
    }


    // Mostrar mensajes al usuario
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public static void mostrarVentanaProductos() {
        ProductoView vista = new ProductoView();
        ProductoModelo modelo = new ProductoModelo();
        ProductoControlador controlador = new ProductoControlador(vista, modelo);

        vista.setVisible(true);
        vista.pack();
    }
}
