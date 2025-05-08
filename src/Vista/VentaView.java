package Vista;

import Controlador.VentaControlador;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class VentaView extends JFrame {
    private JPanel panelVentas;
    private JTable table1; // Tabla de productos disponibles
    private JButton agregarProductoButton;
    private JTable table2; // Tabla de ventas realizadas
    private JTextField Cod_Venta;
    private JTextField Cod_Usuario;
    private JTextField Cod_Producto;
    public JTextField Cantidad;
    public JTextField Precio_Producto;
    private JTextField Total_Por_Producto;
    private JTextField Nombre_Producto;
    private JTextField Total_Venta;
    private JTextField Identificacion_Cliente;
    private JTextField Fecha_Venta;
    private JLabel COD_SALE;
    private JLabel COD_USER;
    private JLabel DATE_SALE;
    private JLabel COSTUMER_IDENTIFICATION;
    private JLabel COD_PRODUCT;
    private JLabel PRODUCT_NAME;
    private JLabel PRODUCT_QUANTITY_Kg;
    private JLabel PRODUCT_PRICE;
    private JLabel TOTAL_PRODUCT;
    private JLabel TOTAL_SALE_VALUE;
    private JButton agregarVentaButton;
    private JButton mostrarButton;
    private JButton listaProductosButton;
    private JButton volverButton;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane2;

    public VentaView() {
        setTitle("Sistema de Ventas - Fruver Aguacates JJ");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(panelVentas);

        // Configuración inicial de los campos
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatofecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Fecha_Venta.setText(fechaActual.format(formatofecha));

        // Configurar campos no editables
        Cod_Producto.setEditable(false);
        Nombre_Producto.setEditable(false);
        Precio_Producto.setEditable(false);
        Total_Por_Producto.setEditable(false);
        Total_Venta.setEditable(false);
        Fecha_Venta.setEditable(false);

        // Establecer el listener para calcular el total por producto cuando cambia la cantidad
        Cantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                notificarCambioEnCantidad();
            }
        });

        // Configurar el listener para seleccionar un producto de la tabla
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    Cod_Producto.setText(table1.getValueAt(selectedRow, 0).toString());
                    Nombre_Producto.setText(table1.getValueAt(selectedRow, 2).toString());
                    Precio_Producto.setText(table1.getValueAt(selectedRow, 5).toString());
                }
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentaControlador.regresar();
                dispose();
            }
        });
    }

    // Métodos para configurar los listeners de los botones
    public void setAgregarProductoButtonListener(ActionListener listener) {
        agregarProductoButton.addActionListener(listener);
    }

    public void setAgregarVentaButtonListener(ActionListener listener) {
        agregarVentaButton.addActionListener(listener);
    }

    public void setMostrarButtonListener(ActionListener listener) {
        mostrarButton.addActionListener(listener);
    }

    public void volverListener(ActionListener listener) {
        volverButton.addActionListener(listener);
    }

    public void setListaProductosButtonListener(ActionListener listener) {
        listaProductosButton.addActionListener(listener);
    }

    // Métodos para notificar eventos
    public void notificarCambioEnCantidad() {
        try {
            String cantidadText = Cantidad.getText().trim();
            String precioText = Precio_Producto.getText().trim();

            if (!cantidadText.isEmpty() && !precioText.isEmpty()) {
                double cantidad = Double.parseDouble(cantidadText);
                double precio = Double.parseDouble(precioText);

                // El controlador llamará a un método que usará esta información
            }
        } catch (NumberFormatException ignored) {
            // Se maneja en el controlador
        }
    }

    // Métodos para obtener la información de los campos
    public String getCodVenta() {
        return Cod_Venta.getText().trim();
    }

    public String getCodUsuario() {
        return Cod_Usuario.getText().trim();
    }

    public String getCodProducto() {
        return Cod_Producto.getText().trim();
    }

    public String getNombreProducto() {
        return Nombre_Producto.getText().trim();
    }

    public String getCantidad() {
        return Cantidad.getText().trim();
    }

    public String getPrecioProducto() {
        return Precio_Producto.getText().trim();
    }

    public String getTotalPorProducto() {
        return Total_Por_Producto.getText().trim();
    }

    public String getFechaVenta() {
        return Fecha_Venta.getText().trim();
    }

    public String getIdentificacionCliente() {
        return Identificacion_Cliente.getText().trim();
    }

    public String getTotalVenta() {
        return Total_Venta.getText().trim();
    }

    // Métodos para establecer valores en los campos
    public void setCodVenta(String codVenta) {
        Cod_Venta.setText(codVenta);
    }

    public void setTotalPorProducto(String totalPorProducto) {
        Total_Por_Producto.setText(totalPorProducto);
    }

    public void setTotalVenta(String totalVenta) {
        Total_Venta.setText(totalVenta);
    }

    // Métodos para actualizar las tablas
    public void actualizarTablaVentas(List<Object[]> datosVentas) {
        DefaultTableModel model = (DefaultTableModel) table2.getModel();
        model.setRowCount(0);

        if (model.getColumnCount() == 0) {
            model.addColumn("Cod_Venta");
            model.addColumn("Cod_Empleado");
            model.addColumn("Fecha_Venta");
            model.addColumn("Identificacion_Cliente");
            model.addColumn("Cod_Producto");
            model.addColumn("Nombre_Producto ");
            model.addColumn("Cantidad_Producto_Kg");
            model.addColumn("Precio_Producto");
            model.addColumn("Total_Producto");
            model.addColumn("Total_Venta");
        }

        for (Object[] fila : datosVentas) {
            model.addRow(fila);
        }
    }

    public void actualizarTablaProductos(List<Object[]> datosProductos) {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0);

        if (model.getColumnCount() == 0) {
            model.addColumn("Cod_Producto");
            model.addColumn("Cod_Compra");
            model.addColumn("Nombre_Producto");
            model.addColumn("Cantidad_Kg");
            model.addColumn("Valor_Compra");
            model.addColumn("Precio_Producto");
            model.addColumn("Descripcion");
        }

        for (Object[] fila : datosProductos) {
            model.addRow(fila);
        }
    }

    // Métodos de utilidad para interactuar con el usuario
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void limpiarCamposProducto() {
        Cod_Producto.setText("");
        Nombre_Producto.setText("");
        Cantidad.setText("");
        Precio_Producto.setText("");
        Total_Por_Producto.setText("");
    }

    public void limpiarCamposVenta() {
        Cod_Venta.setText("");
        Cod_Usuario.setText("");
        Identificacion_Cliente.setText("");
        Total_Venta.setText("");
        limpiarCamposProducto();
    }



    public static void mostrarVentanaVentas() {
        VentaView ventaView = new VentaView();
        ventaView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventaView.setVisible(true);
        ventaView.pack();
    }
}

