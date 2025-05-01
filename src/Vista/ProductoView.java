package Vista;

import javax.swing.*;

public class ProductoView extends JFrame {
    public JPanel panelProductos;
    public JTextField Producto1, NombreProducto1, Compra1, Descripcion1, PrecioProducto1, PrecioCompra1, Cantidad1, stock, CantidadFinal;
    public JButton agregarButton, eliminarButton, modificarButton, mostrarButton, volverButton, listaComprasButton;
    public JTable table1, table2;
    public JScrollPane scrollPane, scrollPaneCP;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;

    public ProductoView() {
        setSize(800, 700);
        setTitle("Gestión de Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        panelProductos = new JPanel();
        setContentPane(panelProductos);
        panelProductos.setLayout(null);

        Producto1 = new JTextField(); Producto1.setBounds(50, 30, 150, 20); panelProductos.add(Producto1);
        Compra1 = new JTextField(); Compra1.setBounds(50, 60, 150, 20); panelProductos.add(Compra1);
        NombreProducto1 = new JTextField(); NombreProducto1.setBounds(50, 90, 150, 20); panelProductos.add(NombreProducto1);
        stock = new JTextField(); stock.setBounds(50, 120, 150, 20); panelProductos.add(stock);
        Cantidad1 = new JTextField(); Cantidad1.setBounds(50, 150, 150, 20); panelProductos.add(Cantidad1);
        CantidadFinal = new JTextField(); CantidadFinal.setBounds(50, 180, 150, 20); panelProductos.add(CantidadFinal);
        PrecioCompra1 = new JTextField(); PrecioCompra1.setBounds(50, 210, 150, 20); panelProductos.add(PrecioCompra1);
        PrecioProducto1 = new JTextField(); PrecioProducto1.setBounds(50, 240, 150, 20); panelProductos.add(PrecioProducto1);
        Descripcion1 = new JTextField(); Descripcion1.setBounds(50, 270, 150, 20); panelProductos.add(Descripcion1);

        agregarButton = new JButton("Agregar"); agregarButton.setBounds(250, 30, 150, 25); panelProductos.add(agregarButton);
        modificarButton = new JButton("Modificar"); modificarButton.setBounds(250, 60, 150, 25); panelProductos.add(modificarButton);
        eliminarButton = new JButton("Eliminar"); eliminarButton.setBounds(250, 90, 150, 25); panelProductos.add(eliminarButton);
        mostrarButton = new JButton("Mostrar"); mostrarButton.setBounds(250, 120, 150, 25); panelProductos.add(mostrarButton);
        volverButton = new JButton("Volver"); volverButton.setBounds(250, 150, 150, 25); panelProductos.add(volverButton);
        listaComprasButton = new JButton("Lista Compras"); listaComprasButton.setBounds(250, 180, 150, 25); panelProductos.add(listaComprasButton);

        table1 = new JTable(new javax.swing.table.DefaultTableModel());
        scrollPane = new JScrollPane(table1); scrollPane.setBounds(50, 310, 300, 150); panelProductos.add(scrollPane);

        table2 = new JTable(new javax.swing.table.DefaultTableModel());
        scrollPaneCP = new JScrollPane(table2); scrollPaneCP.setBounds(400, 310, 300, 150); panelProductos.add(scrollPaneCP);
    }
}
