package Vista;

import javax.swing.*;

public class ProductoView extends JFrame {
    public JPanel panelProductos;
    public JTextField producto1, nombre, codCompra, descripcion, precioProducto, precioCompra, cantidad, stock, cantidadFinal;
    public JButton agregarButton, eliminarButton, modificarButton, mostrarButton, volverButton, listaComprasButton;
    public JTable table1, table2;
    public JScrollPane scrollPane, scrollPaneCP;
    private JLabel COD_PRODUCT;
    private JLabel COD_PURCHASE;
    private JLabel PRODUCT_NAME;
    private JLabel STOCK;
    private JLabel QUANTITY_Kg;
    private JLabel FINAL_QUANTITY_Kg;
    private JLabel PURCHASE_VALUE;
    private JLabel PRODUCT_PRICE;
    private JLabel DESCRIPTION_PRODUCT;

    public ProductoView() {
        setSize(800, 700);
        setTitle("Gestión de Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        panelProductos = new JPanel();
        setContentPane(panelProductos);
        panelProductos.setLayout(null);

        producto1 = new JTextField(); producto1.setBounds(50, 30, 150, 20); panelProductos.add(producto1);
        codCompra = new JTextField(); codCompra.setBounds(50, 60, 150, 20); panelProductos.add(codCompra);
        nombre = new JTextField(); nombre.setBounds(50, 90, 150, 20); panelProductos.add(nombre);
        stock = new JTextField(); stock.setBounds(50, 120, 150, 20); panelProductos.add(stock);
        cantidad = new JTextField(); cantidad.setBounds(50, 150, 150, 20); panelProductos.add(cantidad);
        cantidadFinal = new JTextField(); cantidadFinal.setBounds(50, 180, 150, 20); panelProductos.add(cantidadFinal);
        precioCompra = new JTextField(); precioCompra.setBounds(50, 210, 150, 20); panelProductos.add(precioCompra);
        precioProducto = new JTextField(); precioProducto.setBounds(50, 240, 150, 20); panelProductos.add(precioProducto);
        descripcion = new JTextField(); descripcion.setBounds(50, 270, 150, 20); panelProductos.add(descripcion);

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
