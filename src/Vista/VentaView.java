package Vista;

import Controlador.PantallaInicioControlador;
import Controlador.VentaControlador;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import Modelo.UsuarioModel;
import Modelo.VentaModelo;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

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
    private JButton agregarVentaButton;
    private JButton mostrarButton;
    private JButton listaProductosButton;
    private JButton volverButton;
    private JTable tableVentaActual;
    private JButton eliminarProductoButton;
    private JButton cancelarVentaButton;
    private JButton cerrarSesionButton;
    private JLabel COD_SALE;
    private JLabel COD_USER;
    private JLabel DATE_SALE;
    private JLabel COSTUMER_IDENTIFICATION;
    private JLabel COD_PRODUCT;
    private JLabel PRODUCT_NAME;
    private JScrollPane scrollPane2;
    private JScrollPane scrollPane;
    private JLabel PRODUCT_QUANTITY_Kg;
    private JLabel PRODUCT_PRICE;
    private JLabel TOTAL_PRODUCT;
    private JLabel TOTAL_SALE_VALUE;
    private JButton consultarVentasButton;
    private JButton mostrarProductosButton;
    private VentaModelo modelo = new VentaModelo();
    private Connection conexion;
    private PreparedStatement ps;
    private ResultSet rs;
    private List<VentaModelo.ProductoDetalle> productosVenta = new ArrayList<>();
    private Set<Integer> ventasCanceladas = new HashSet<>();
    private JTextField lblUsuario;

    public VentaView() {

        setTitle("Sistema de Ventas - Fruver Aguacates JJ");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(panelVentas);

         actualizarTablaVentas();
        yaMostroAlertaStock = false; //  Reinicia la alerta al abrir
        verificarStockMinimoConMargen();
         listarProductos();

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

        Cod_Usuario.setText("2");
        Cod_Usuario.setEditable(false);

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
                    Precio_Producto.setText(table1.getValueAt(selectedRow, 7).toString());
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




        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();

            }
        });


        agregarVentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codUsuario = getCodUsuario();
                String fechaVenta = getFechaVenta();
                String identificacionCliente = getIdentificacionCliente();
                String totalVenta = actualizarTotalVenta();

                if (productosVenta.isEmpty()) {
                    mostrarError("Agregue al menos un producto a la venta.");
                    return;
                }

                int codVenta = agregarVenta(codUsuario, fechaVenta, identificacionCliente, totalVenta, productosVenta);
                if (codVenta != -1) {
                    mostrarMensaje("Venta registrada exitosamente. Código: " + codVenta);
                    limpiarCamposVenta();
                    limpiarTablaVentaActual();
                } else {
                    mostrarError("Error al registrar la venta.");
                }
               actualizarTablaVentas();
                listarProductos();
            }
        });

        eliminarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tableVentaActual.getSelectedRow();
                eliminarProductoDeVenta(filaSeleccionada);
            }
        });
        cancelarVentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = table2.getSelectedRow();

                if (filaSeleccionada == -1) {
                    mostrarError("Selecciona una venta de la tabla.");
                    return;
                }

                try {
                    // Asegúrate de que la columna 0 sea donde está el COD_VENTA
                    int codVenta = Integer.parseInt(table2.getValueAt(filaSeleccionada, 0).toString());

                    int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas cancelar la venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        cancelarVenta(codVenta);
                    }
                } catch (NumberFormatException ex) {
                    mostrarError("Código de venta no válido.");
                }

                listarProductos();
            }
        });

        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                UsuarioModel modelo = new UsuarioModel();
                PantallaInicioView vista = new PantallaInicioView();
                PantallaInicioControlador controlador = new PantallaInicioControlador(modelo, vista);
                controlador.iniciar();
               dispose();
            }
        });

    }



    public void actualizarTablaProductos(DefaultTableModel modelo) {
        table1.setModel(modelo);
    }

    public void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/FruverAguacates", "root", "10j56yeyo");
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
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

    public void setMostrarProductosButtonListener(ActionListener listener) {
        mostrarProductosButton.addActionListener(listener);
    }

    public void agregarProductoListener(ActionListener listener) {
        agregarProductoButton.addActionListener(listener);
    }

    public void agregarVentaListener(ActionListener listener) {
        agregarVentaButton.addActionListener(listener);
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

    public JButton getMostrarProductosButton() {
        return mostrarProductosButton;
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

    public JTable getTable1() {
        return table1;
    }

    // Métodos para notificar eventos
    // Métodos para notificar eventos
    public void notificarCambioEnCantidad() {
        try {
            String cantidadText = Cantidad.getText().trim();
            String precioText = Precio_Producto.getText().trim();

            if (!cantidadText.isEmpty() && !precioText.isEmpty()) {
                double cantidad = Double.parseDouble(cantidadText.replace(",", "."));
                double precio = Double.parseDouble(precioText.replace(",", "."));

                double totalPorProducto = cantidad * precio;
                // Formatear el resultado a dos decimales y establecerlo en el campo correspondiente
                Total_Por_Producto.setText(String.format("%.2f", totalPorProducto));
            } else {
                Total_Por_Producto.setText("");
            }
        } catch (NumberFormatException e) {
            Total_Por_Producto.setText("Error");
        }
    }


    private void agregarProducto() {
        String codProducto = getCodProducto();
        String nombreProducto = getNombreProducto();
        String cantidad = getCantidad();
        String precioProducto = getPrecioProducto();
        String totalProducto = getTotalPorProducto().replace(",", ".");

        if (codProducto.isEmpty() || nombreProducto.isEmpty() || cantidad.isEmpty() ||
            precioProducto.isEmpty() || totalProducto.isEmpty()) {
            mostrarError("Debe completar todos los campos del producto");
            return;
        }

        // Verificar existencia del producto antes de agregarlo
        double existencia = 0.0;

        try {
            PreparedStatement ps = conexion.prepareStatement(
                    "SELECT FINAL_QUANTITY_KG FROM PRODUCTS WHERE COD_PRODUCT = ?");
            ps.setString(1, codProducto); // Usa String si tu codProducto es tipo String
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                existencia = rs.getDouble("FINAL_QUANTITY_KG");
            }

            if (existencia == 0.0) {
                mostrarError("No hay existencias de este producto. No se puede agregar a la venta.");
                return; // 🚫 No permite agregarlo
            }

        } catch (SQLException e) {
            mostrarError("Error al verificar existencias: " + e.getMessage());
            return; // 🚫 Detener si hay error
        }

        // Crear el objeto ProductoDetalle y agregarlo a la lista
        VentaModelo.ProductoDetalle producto = new VentaModelo.ProductoDetalle(
                codProducto, nombreProducto, cantidad, precioProducto, totalProducto
        );
        productosVenta.add(producto);

        // Recalcular el total de la venta
        actualizarTotalVenta();
        actualizarTablaVentaActual();
        mostrarMensaje("Producto agregado a la venta.");
        limpiarCamposProducto();
    }


    private void eliminarProductoSeleccionado() {
        int filaSeleccionada = tableVentaActual.getSelectedRow();
        if (filaSeleccionada != -1) {
            String codProducto = tableVentaActual.getValueAt(filaSeleccionada, 0).toString();
            int codProductoInt = Integer.parseInt(codProducto); // Conversión
            eliminarProductoDeVenta(codProductoInt); // Llamada correcta
            actualizarTablaVentaActual();
        } else {
            mostrarError("Selecciona una fila para eliminar.");
        }
    }


    public void eliminarProductoDeVenta(int indiceSeleccionado) {
        if (indiceSeleccionado >= 0 && indiceSeleccionado < productosVenta.size()) {
            productosVenta.remove(indiceSeleccionado);
            actualizarTablaVentaActual();  // Método que debes tener para refrescar la tabla
            actualizarTotalVenta();           // Recalcular total de la venta
            mostrarMensaje("Producto eliminado de la venta.");
        } else {
            mostrarError("Selecciona un producto válido para eliminar.");
        }
    }




    public int agregarVenta(String codUsuario, String fechaVenta, String identificacionCliente, String totalVenta, List<VentaModelo.ProductoDetalle> productosVenta) {
        conectar();
        int codVentaGenerado = -1;
        try {
            conexion.setAutoCommit(false);

            String sqlSales = "INSERT INTO SALES (COD_USER, DATE_SALE, COSTUMER_IDENTIFICATION, TOTAL_SALE_VALUE) VALUES (?, ?, ?, ?)";
            ps = conexion.prepareStatement(sqlSales, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, codUsuario);
            ps.setString(2, fechaVenta);
            if(identificacionCliente.isEmpty()){
                ps.setNull(3, java.sql.Types.VARCHAR);
            } else {
                ps.setString(3, identificacionCliente);
            }
            ps.setString(4, totalVenta.replace(",", "."));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                codVentaGenerado = rs.getInt(1);
            } else {
                throw new SQLException("Error al obtener el COD_SALE generado.");
            }

            String sqlSales_Details = "INSERT INTO SALES_DETAILS (COD_SALE, COD_PRODUCT, PRODUCT_NAME, PRODUCT_QUANTITY_Kg, PRODUCT_PRICE, TOTAL_PRODUCT) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conexion.prepareStatement(sqlSales_Details);

            for (VentaModelo.ProductoDetalle producto : productosVenta) {
                ps.setInt(1, codVentaGenerado);
                ps.setString(2, producto.getCodProducto());
                ps.setString(3, producto.getNombreProducto());
                ps.setString(4, producto.getCantidadKg());
                ps.setString(5, producto.getPrecioProducto());
                ps.setString(6, producto.getTotalProducto());
                ps.addBatch();

            }

            int[] filasInsertadas = ps.executeBatch();

            if (filasInsertadas.length > 0) {
                conexion.commit();
                int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas imprimir la factura?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    generarFacturaPDF(codVentaGenerado, fechaVenta, identificacionCliente, codUsuario, totalVenta, productosVenta);
                }

                return codVentaGenerado;
            } else {
                throw new SQLException("No se pudieron insertar los productos en la venta.");
            }

        } catch (SQLException e) {
            try {
                if (conexion != null) {
                    conexion.rollback();
                }
                e.printStackTrace();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return -1;
        } finally {
            cerrarConexiones();
        }
    }

    public void cancelarVenta(int codVenta) {
        conectar();
        try {
            conexion.setAutoCommit(false);  // Iniciar transacción

            // 1. Obtener detalles de productos vendidos
            PreparedStatement psDetalle = conexion.prepareStatement(
                    "SELECT COD_PRODUCT, PRODUCT_QUANTITY_Kg FROM SALES_DETAILS WHERE COD_SALE = ?");
            psDetalle.setInt(1, codVenta);
            ResultSet rsDetalle = psDetalle.executeQuery();

            while (rsDetalle.next()) {
                int codProducto = rsDetalle.getInt("COD_PRODUCT");
                double cantidadDevuelta = rsDetalle.getDouble("PRODUCT_QUANTITY_Kg");

                // 2. Devolver al inventario
                PreparedStatement psUpdate = conexion.prepareStatement(
                        "UPDATE PRODUCTS SET FINAL_QUANTITY_KG = FINAL_QUANTITY_KG + ? WHERE COD_PRODUCT = ?");
                psUpdate.setDouble(1, cantidadDevuelta);
                psUpdate.setInt(2, codProducto);
                psUpdate.executeUpdate();
            }

            // 3. Actualizar el estado de la venta a CANCELADA (Aquí va tu línea)
            PreparedStatement psCancelarVenta = conexion.prepareStatement(
                    "UPDATE SALES SET STATUS = 'CANCELADA' WHERE COD_SALE = ?");
            psCancelarVenta.setInt(1, codVenta);
            psCancelarVenta.executeUpdate();

            // 4. Confirmar cambios
            conexion.commit();

            // 5. Acciones de interfaz
            ventasCanceladas.add(codVenta); // Si llevas un registro en memoria
            mostrarMensaje("Venta cancelada y stock actualizado correctamente.");
            actualizarTablaVentas(); // Refrescar tabla en la interfaz

        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            mostrarError("Error al cancelar la venta: " + e.getMessage());
        } finally {
            cerrarConexiones();
        }
    }


    private void actualizarTablaVentaActual() {
        // Crear un nuevo modelo
        DefaultTableModel nuevoModelo = new DefaultTableModel();
        nuevoModelo.addColumn("Cod_Producto");
        nuevoModelo.addColumn("Nombre_Producto");
        nuevoModelo.addColumn("Cantidad (Kg)");
        nuevoModelo.addColumn("Precio");
        nuevoModelo.addColumn("Total");

        // Llenar con datos
        for (VentaModelo.ProductoDetalle producto : productosVenta) {
            Object[] fila = {
                    producto.getCodProducto(),
                    producto.getNombreProducto(),
                    producto.getCantidadKg(),
                    producto.getPrecioProducto(),
                    producto.getTotalProducto()
            };
            nuevoModelo.addRow(fila);
        }

        // Asignar el nuevo modelo a la tabla
        tableVentaActual.setModel(nuevoModelo);
        ajustarAnchoColumnas(tableVentaActual);
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


    public void listarProductos() {
      conectar();

      String sql = "SELECT * FROM PRODUCTS";

       try {
           ps = conexion.prepareStatement(sql);
           rs = ps.executeQuery();
        DefaultTableModel modelo = (DefaultTableModel) table1.getModel();
           modelo.setRowCount(0);
        // Definir columnas

           if (modelo.getColumnCount() == 0) {
               modelo.addColumn("Cod_Producto");
               modelo.addColumn("Cod_Compra");
               modelo.addColumn("Nombre_Producto");
               modelo.addColumn("Stock");
               modelo.addColumn("Cantidad_Kg");
               modelo.addColumn("Cantidad Final_KG");
               modelo.addColumn("Valor_Compra");
               modelo.addColumn("Precio_Producto");
               modelo.addColumn("Descripcion");
           }


            while (rs.next()) {
                Object[] fila = {
                        rs.getString("COD_PRODUCT"),
               rs.getString("COD_PURCHASE"),
                rs.getString("PRODUCT_NAME"),
                rs.getString("STOCK"),
                rs.getString("QUANTITY_Kg"),
                rs.getString("FINAL_QUANTITY_KG"),
               rs.getString("PURCHASE_VALUE"),
               rs.getString("PRODUCT_PRICE"),
                rs.getString("DESCRIPTION_PRODUCT_STATUS")
                };
                modelo.addRow(fila);
            }
            table1.setModel(modelo);
            ajustarAnchoColumnas(table1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hay un error al mostrar los datos: " + e.getMessage());
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

    // Métodos de utilidad para interactuar con el usuario
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }



    public String actualizarTotalVenta() {
        double total = 0.0;
        for (VentaModelo.ProductoDetalle producto : productosVenta) {
            try {
                double subtotal = Double.parseDouble(producto.getTotalProducto().replace(",", "."));
                total += subtotal;
            } catch (NumberFormatException e) {
                mostrarError("Error al calcular el total de la venta.");
                return "";
            }
        }
        String totalFormateado = String.format("%.2f", total);
        Total_Venta.setText(totalFormateado);
        return totalFormateado;
    }


    public void actualizarTablaVentas() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Cod_Venta");
        modelo.addColumn("Cod_Empleado");
        modelo.addColumn("Fecha_Venta");
        modelo.addColumn("Identificación_Cliente");
        modelo.addColumn("Cod_Producto");
        modelo.addColumn("Nombre_Producto");
        modelo.addColumn("Cantidad_Kg");
        modelo.addColumn("Precio_Producto");
        modelo.addColumn("Total_Por_Producto");
        modelo.addColumn("Total_Venta");
        modelo.addColumn("Estado");

        conectar();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("""
    SELECT s.COD_SALE, s.COD_USER, s.DATE_SALE, s.COSTUMER_IDENTIFICATION,
                                                                              d.COD_PRODUCT, d.PRODUCT_NAME, d.PRODUCT_QUANTITY_Kg, d.PRODUCT_PRICE,
                                                                              d.TOTAL_PRODUCT, s.TOTAL_SALE_VALUE, s.STATUS
                                                                       FROM SALES s
                                                                       JOIN SALES_DETAILS d ON s.COD_SALE = d.COD_SALE
             
""");

            while (rs.next()) {
                Object[] fila = new Object[11];
                fila[0] = rs.getInt("COD_SALE");
                fila[1] = rs.getInt("COD_USER");
                fila[2] = rs.getString("DATE_SALE");
                fila[3] = rs.getString("COSTUMER_IDENTIFICATION");
                fila[4] = rs.getInt("COD_PRODUCT");
                fila[5] = rs.getString("PRODUCT_NAME");
                fila[6] = rs.getString("PRODUCT_QUANTITY_Kg");
                fila[7] = rs.getDouble("PRODUCT_PRICE");
                fila[8] = rs.getInt("TOTAL_PRODUCT");
                fila[9] = rs.getString("TOTAL_SALE_VALUE");
                fila[10] = rs.getString("STATUS");
                modelo.addRow(fila);
            }

            table2.setModel(modelo);
            ajustarAnchoColumnas(table2);
            table2.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                                                               boolean isSelected, boolean hasFocus, int row, int column) {

                    Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    try {
                        int modelRow = table.convertRowIndexToModel(row); // importante si hay ordenamiento
                        String estado = modelo.getValueAt(modelRow, 10).toString(); // columna STATUS (posición 10)

                        if ("CANCELADA".equalsIgnoreCase(estado)) {
                            cell.setBackground(Color.RED);
                            cell.setForeground(Color.WHITE);
                        } else {
                            cell.setBackground(Color.WHITE);
                            cell.setForeground(Color.BLACK);
                        }
                    } catch (Exception ex) {
                        cell.setBackground(Color.WHITE);
                        cell.setForeground(Color.BLACK);
                    }

                    return cell;
                }
            });


        } catch (SQLException e) {
            mostrarError("Error al cargar ventas: " + e.getMessage());
        }
    }

    private static boolean yaMostroAlertaStock = false;

    public void verificarStockMinimoConMargen() {
        conectar();
        double margen = 1.0;
        StringBuilder alerta = new StringBuilder();

        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PRODUCTS");

            while (rs.next()) {
                String nombre = rs.getString("PRODUCT_NAME");
                double cantidadActual = rs.getDouble("FINAL_QUANTITY_KG");
                double stockMinimo = rs.getDouble("STOCK");

                if (cantidadActual == 0.0) {
                    alerta.append("🚫 El producto '").append(nombre)
                            .append("' NO tiene existencias.\n")
                            .append("⚠ Urgente reabastecer.\n\n");
                } else if (cantidadActual <= stockMinimo + margen) {
                    alerta.append("⚠ El producto '").append(nombre)
                            .append("' está por llegar al stock mínimo.\n")
                            .append("Cantidad actual: ").append(cantidadActual).append(" kg ")
                            .append("(mínimo: ").append(stockMinimo).append(" kg).\n\n");
                }
            }

            if (alerta.length() > 0) {
                JOptionPane.showMessageDialog(null,
                        alerta.toString(),
                        "⚠ Alerta de Stock", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            mostrarError("Error al verificar el stock mínimo: " + e.getMessage());
        }
    }





    public void limpiarCamposProducto() {

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
        Total_Venta.setText("");
    }
    private void cerrarConexiones() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generarFacturaPDF(int codVenta, String fechaVenta, String identificacionCliente, String codUsuario, String totalVenta, List<VentaModelo.ProductoDetalle> productosVenta) {
        Document document = new Document();

        try {
            File folder = new File("facturas");
            if (!folder.exists()) folder.mkdirs();

            // Nombre único para cada factura
            String nombreArchivo = "facturas/Factura_" + codVenta + "_" + System.currentTimeMillis() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            document.open();

            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font textoFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("FRUVER AGUACATES JJ", tituloFont));
            document.add(new Paragraph(" ", textoFont));
            document.add(new Paragraph("Factura N°: " + codVenta, textoFont));
            document.add(new Paragraph("Fecha: " + fechaVenta, textoFont));
            document.add(new Paragraph("Cliente (CC): " + identificacionCliente, textoFont));
            document.add(new Paragraph("Empleado (ID): " + codUsuario, textoFont));
            document.add(new Paragraph("\nDetalles de la venta:", textoFont));
            document.add(new Paragraph("--------------------------------------", textoFont));

            for (VentaModelo.ProductoDetalle detalle : productosVenta) {
                document.add(new Paragraph("Producto: " + detalle.getNombreProducto(), textoFont));
                document.add(new Paragraph("Cantidad (Kg): " + detalle.getCantidadKg(), textoFont));
                document.add(new Paragraph("Precio Unitario: $" + String.format("%.0f", Double.parseDouble(detalle.getPrecioProducto())), textoFont));
                document.add(new Paragraph("Total: $" + String.format("%.0f", Double.parseDouble(detalle.getTotalProducto())), textoFont));
                document.add(new Paragraph(" ", textoFont));
            }

            document.add(new Paragraph("--------------------------------------", textoFont));
            document.add(new Paragraph("Total venta: $" + totalVenta, tituloFont));
            document.add(new Paragraph("\nGracias por su compra.", textoFont));
            document.close();

            JOptionPane.showMessageDialog(null, "Factura generada exitosamente: " + nombreArchivo);
            Desktop.getDesktop().open(new File(nombreArchivo));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar PDF: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }



    private void limpiarTablaVentaActual() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Cod_Producto");
        modelo.addColumn("Nombre_Producto");
        modelo.addColumn("Cantidad (Kg)");
        modelo.addColumn("Precio");
        modelo.addColumn("Total");
        tableVentaActual.setModel(modelo); // ← esto limpia visualmente la tabla
    }

    public static void cerrarSesion(){

        PantallaInicioView pantallaInicioView = new PantallaInicioView();
        pantallaInicioView.mostrarVentana();
    }



    public static void mostrarVentanaVentas() {
        VentaView ventaView = new VentaView();
        ventaView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventaView.setVisible(true);
    }



}




