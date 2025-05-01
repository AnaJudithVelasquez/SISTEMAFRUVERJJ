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
    public JTextField Cod_Compra;
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
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JButton button1;
    private JButton button2;
    private JTextField textField13;
    private JTable table2;
    private JButton button3;

    public CompraView() {
        setContentPane(panelCompras);
        setTitle("Gestión de Compras");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}

