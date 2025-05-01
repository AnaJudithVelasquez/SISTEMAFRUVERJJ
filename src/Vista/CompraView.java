package Vista;


import javax.swing.*;

public class CompraView extends JFrame {
    public JPanel panelCompras;
    public JButton agregarProductoButton;
    public JButton agregarCompraButton;
    public JButton agregarButton;
    public JButton modificarButton;
    public JButton eliminarButton;
    public JButton mostrarButton;
    public JButton mostrarComprasButton;
    public JButton volverButton;
    public JTextField Nombre_Proveedor;
    public JTextField Direccion;
    public JTextField Telefono;
    public JTextField Cod_Usuario;
    public JTextField Fecha_Compra;
    public JTextField Producto_Comprado;
    public JTextField Cantidad_Producto_Kg;
    public JTextField Valor_Unitario;
    public JTextField Total_Producto;
    public JTextField Total_Compra;
    public JTextField Cod_Proveedor;
    public JTextField Cod_ProveedorC;
    public JTable table1P;
    public JTable table1;
    public JScrollPane scrollPane1P;
    public JScrollPane scrollPane;
    private JTextField Cod_Compra;
    private JTextField textField10;
    private JTable table2;
    private JButton button3;
    private JLabel COD_SUPPLIER;
    private JLabel SUPPLIER_NAME;
    private JLabel ADDRESS;
    private JLabel PHONE_NUMBER;
    private JLabel COD_PURCHASE;
    private JLabel COD_USER;
    private JLabel COD_SUPPLIERC;
    private JLabel DATE_PURCHASE;
    private JLabel PURCHASED_PRODUCT;
    private JLabel QUANTITY_Kg;
    private JLabel UNIT_VALUE;
    private JLabel TOTAL_PRODUCT;
    private JLabel TOTAL_PURCHASE_VALUE;

    public CompraView() {
        setContentPane(panelCompras);
        setTitle("Gestión de Compras");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}

