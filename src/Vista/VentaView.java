package Vista;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class VentaView extends JFrame {
    public JPanel panelVentas;
    public JTable tableProductos;
    public JTable tableVentas;
    public JButton btnAgregarProducto;
    public JButton btnAgregarVenta;
    public JButton btnMostrar;
    public JButton btnVolver;
    private JTable table1;
    public JTextField txtCodVenta, txtCodUsuario, txtCodProducto, txtCantidad, txtPrecioProducto, txtTotalProducto, txtNombreProducto, txtTotalVenta, txtIdentificacionCliente, txtFechaVenta;
    private JTextField Cod_Venta;
    private JTextField Cod_Usuario;
    private JTextField Fecha_Venta;
    private JTextField Identificacion_Cliente;
    private JTextField Cod_Producto;
    private JTextField Cantidad;
    private JTextField Precio_Producto;
    private JTextField Total_Por_Producto;
    private JButton agregarProductoButton;
    private JButton agregarVentaButton;
    private JTextField Total_Venta;
    private JButton mostrarButton;
    private JTable table2;
    private JButton button4;
    private JLabel COD_SALE;
    private JLabel COD_USER;
    private JLabel DATE_SALE;
    private JLabel COSTUMER_IDENTIFICATIO;
    private JLabel COD_PRODUCT;
    private JLabel PRODUCT_NAME;
    private JTextField Nombre_Producto;
    private JLabel PRODUCT_QUANTITY_Kg;
    private JLabel PRODUCT_PRICE;
    private JLabel TOTAL_PRODUCT;
    private JLabel TOTAL_SALE_VALUE;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane2;


    public VentaView() {
        setTitle("Ventas");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    txtCodProducto.setText(table1.getValueAt(selectedRow, 0).toString());
                    txtNombreProducto.setText(table1.getValueAt(selectedRow, 2).toString());
                    txtPrecioProducto.setText(table1.getValueAt(selectedRow, 5).toString());

                }
            }
        });

        // Inicializar componentes
        panelVentas = new JPanel(new GridLayout(10, 2));

        txtCodVenta = new JTextField();
        txtCodUsuario = new JTextField();
        txtCodProducto = new JTextField();
        txtCantidad = new JTextField();
        txtPrecioProducto = new JTextField();
        txtTotalProducto = new JTextField();
        txtNombreProducto = new JTextField();
        txtTotalVenta = new JTextField();
        txtIdentificacionCliente = new JTextField();
        txtFechaVenta = new JTextField();

        tableProductos = new JTable();
        tableVentas = new JTable();
        btnAgregarProducto = new JButton("Agregar Producto");
        btnAgregarVenta = new JButton("Agregar Venta");
        btnMostrar = new JButton("Mostrar");
        btnVolver = new JButton("Volver");

        // Agregar componentes al panel
        panelVentas.add(new JLabel("Código Venta:"));
        panelVentas.add(txtCodVenta);
        panelVentas.add(new JLabel("Código Usuario:"));
        panelVentas.add(txtCodUsuario);
        panelVentas.add(new JLabel("Código Producto:"));
        panelVentas.add(txtCodProducto);
        panelVentas.add(new JLabel("Cantidad:"));
        panelVentas.add(txtCantidad);
        panelVentas.add(new JLabel("Precio Producto:"));
        panelVentas.add(txtPrecioProducto);
        panelVentas.add(new JLabel("Total Producto:"));
        panelVentas.add(txtTotalProducto);
        panelVentas.add(new JLabel("Nombre Producto:"));
        panelVentas.add(txtNombreProducto);
        panelVentas.add(new JLabel("Total Venta:"));
        panelVentas.add(txtTotalVenta);
        panelVentas.add(new JLabel("Identificación Cliente:"));
        panelVentas.add(txtIdentificacionCliente);
        panelVentas.add(new JLabel("Fecha Venta:"));
        panelVentas.add(txtFechaVenta);

        panelVentas.add(btnAgregarProducto);
        panelVentas.add(btnAgregarVenta);
        panelVentas.add(btnMostrar);
        panelVentas.add(btnVolver);

        add(panelVentas);
    }


}

