package Vista;

import Controlador.CompraControlador;
import Modelo.CompraModelo;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompraView extends JFrame {
    private JButton agregarProductoButton;
    private JButton agregarCompraButton;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
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
    private JTable table1P;
    private JScrollPane scrollPane1P;
    private JTable table1;
    private JTextField Cod_Proveedor;
    private JTextField Cod_ProveedorC;
    private JPanel panelCompras;
    private JButton mostrarComprasButton;
    private JButton volverButton;
    private JLabel COD_PURCHASE;
    private JLabel COD_USER;
    private JLabel COD_SUPPLIERC;
    private JLabel DATE_PURCHASE;
    private JLabel PURCHASED_PRODUCT;
    private JLabel QUANTITY_Kg;
    private JLabel UNIT_VALUE;
    private JLabel TOTAL_PRODUCT;
    private JLabel TOTAL_PURCHASE_VALUE;
    private JLabel COD_SUPPLIER;
    private JLabel SUPPLIER_NAME;
    private JLabel ADDRESS;
    private JLabel PHONE_NUMBER;
    private JButton consultarComprasButton;
    private JScrollPane scrollPane;// nuevo botón


    public CompraView() {
        setContentPane(panelCompras);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTableHeader header = table1P.getTableHeader();
        header.setBackground(new Color(115, 229, 127)); // Color verde personalizado
        header.setForeground(Color.BLACK);             // Texto blanco
        header.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente opcional

        table1P.setSelectionBackground(UIManager.getColor("Table.selectionBackground"));
        table1P.setSelectionForeground(UIManager.getColor("Table.selectionForeground"));


        JTableHeader header1 = table1.getTableHeader();
        header1.setBackground(new Color(115, 229, 127)); // Color verde personalizado
        header1.setForeground(Color.BLACK);             // Texto blanco
        header1.setFont(new Font("Arial", Font.BOLD, 14));


        // Configurar fecha actual
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatofecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Fecha_Compra.setText(fechaActual.format(formatofecha));

        // Configurar campos no editables
        Cod_ProveedorC.setEditable(false);
        Total_Producto.setEditable(false);
        Total_Compra.setEditable(false);
        Fecha_Compra.setEditable(false);

        Cod_Usuario.setText("1");
        Cod_Usuario.setEditable(false);

        // Configuración de selección de tabla
        agregarSeleccionTabla();


        table1P.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table1P.getSelectedRow();
                if (selectedRow != -1) {
                    Cod_Proveedor.setText(table1P.getValueAt(selectedRow, 0).toString());
                    Nombre_Proveedor.setText(table1P.getValueAt(selectedRow, 1).toString());
                    Direccion.setText(table1P.getValueAt(selectedRow, 2).toString());
                    Telefono.setText(table1P.getValueAt(selectedRow, 3).toString());
                }
            }
        });


    }



    // Getters para los campos de texto
    public String getNombreProveedor() { return Nombre_Proveedor.getText(); }
    public String getDireccion() { return Direccion.getText(); }
    public String getTelefono() { return Telefono.getText(); }
    public String getCodProveedor() { return Cod_Proveedor.getText().trim(); }
    public String getCodProveedorC() { return Cod_ProveedorC.getText().trim(); }
    public String getCodUsuario() { return Cod_Usuario.getText().trim(); }
    public String getFechaCompra() { return Fecha_Compra.getText().trim(); }
    public String getProductoComprado() { return Producto_Comprado.getText(); }
    public String getCantidadProductoKg() { return Cantidad_Producto_Kg.getText().trim(); }
    public String getValorUnitario() { return Valor_Unitario.getText().trim(); }
    public String getTotalProducto() { return Total_Producto.getText().trim().replace(",","."); }
    public String getTotalCompra() { return Total_Compra.getText().trim().replace(",", "."); }
    public String getCodCompra() { return Cod_Compra.getText().trim(); }

    // Setters para los campos de texto
    public void setCodProveedor(String valor) { Cod_Proveedor.setText(valor); }
    public void setCodCompra(String valor) { Cod_Compra.setText(valor); }
    public void setTotalProducto(String valor) { Total_Producto.setText(valor); }
    public void setTotalCompra(String valor) { Total_Compra.setText(valor); }

    // Métodos para visualizar datos en tablas
    public void mostrarDatosProveedores(ResultSet rs) {
        try {
            DefaultTableModel model = (DefaultTableModel) table1P.getModel();
            model.setRowCount(0);

            if (model.getColumnCount() == 0) {
                model.addColumn("Cod_Proveedor");
                model.addColumn("Nombre_Proveedor");
                model.addColumn("Direccion");
                model.addColumn("Telefono");
            }

            while (rs.next()) {
                Object[] fila = {
                        rs.getString("COD_SUPPLIER"),
                        rs.getString("SUPPLIER_NAME"),
                        rs.getString("ADDRESS"),
                        rs.getString("PHONE_NUMBER")
                };
                model.addRow(fila);
            }

            table1P.setModel(model);
            ajustarAnchoColumnas(table1P);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void ajustarAnchoColumnas(JTable tabla) {
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Importante: desactiva auto-resize por defecto

        for (int col = 0; col < tabla.getColumnCount(); col++) {
            TableColumn column = tabla.getColumnModel().getColumn(col);
            int ancho = 150; // Ancho mínimo por si no hay datos

            for (int row = 0; row < tabla.getRowCount(); row++) {
                TableCellRenderer renderer = tabla.getCellRenderer(row, col);
                Component comp = tabla.prepareRenderer(renderer, row, col);
                ancho = Math.max(comp.getPreferredSize().width + 10, ancho);
            }

            column.setPreferredWidth(ancho);
        }
    }


    public void mostrarDatosCompras(ResultSet rs) {
        try {
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            model.setRowCount(0);

            if (model.getColumnCount() == 0) {
                model.addColumn("Cod_Compra");
                //model.addColumn("Cod_Administrador");
                model.addColumn("Cod_Proveedor");
                model.addColumn("Fecha_Compra");
                model.addColumn("Producto_Comprado");
                model.addColumn("Valor_Unitario");
                model.addColumn("Cantidad_Producto_Kg");
                model.addColumn("Total_Producto");
                model.addColumn("Total_Compra");
            }

            while (rs.next()) {
                Object[] fila = {
                        rs.getString("COD_PURCHASE"),
                        //rs.getString("COD_USER"),
                        rs.getString("COD_SUPPLIER"),
                        rs.getString("DATE_PURCHASE"),
                        rs.getString("PURCHASED_PRODUCT"),
                        rs.getString("UNIT_VALUE"),
                        rs.getString("QUANTITY_Kg"),
                        rs.getString("TOTAL_PRODUCT"),
                        rs.getString("TOTAL_PURCHASE_VALUE")
                };
                model.addRow(fila);
            }

            table1.setModel(model);

            ajustarAnchoColumnas(table1);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos de compra: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Métodos para manejadores de eventos
    private void agregarSeleccionTabla() {
        table1P.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && table1P.getSelectedRow() != -1) {
                    Cod_ProveedorC.setText(table1P.getValueAt(table1P.getSelectedRow(), 0).toString());
                }
            }
        });
    }

    // Métodos para agregar listeners a los botones
    public void agregarProveedorListener(ActionListener listener) {
        agregarButton.addActionListener(listener);
    }

    public void modificarProveedorListener(ActionListener listener) {
        modificarButton.addActionListener(listener);
    }

    public void eliminarProveedorListener(ActionListener listener) {
        eliminarButton.addActionListener(listener);
    }



    public void agregarProductoListener(ActionListener listener) {
        agregarProductoButton.addActionListener(listener);
    }

    public void agregarCompraListener(ActionListener listener) {
        agregarCompraButton.addActionListener(listener);
    }



    public void volverListener(ActionListener listener) {
        volverButton.addActionListener(listener);
    }

    public void calcularTotalProductoListener(KeyAdapter listener) {
        Cantidad_Producto_Kg.addKeyListener(listener);
        Valor_Unitario.addKeyListener(listener);
    }


    // Métodos para mostrar mensajes
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, "Error: " + mensaje);
    }

    // Método para mostrar la ventana
    public static void mostrarVentanaCompras() {
        CompraView vista = new CompraView();
        CompraModelo modelo = new CompraModelo();
        CompraControlador controlador = new CompraControlador(modelo, vista);

        vista.setVisible(true);
    }
}
