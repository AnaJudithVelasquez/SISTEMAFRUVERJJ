package Modelo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class VentaModelo extends JFrame{
    private Connection conexion;
    private PreparedStatement ps;
    private  ResultSet rs;

    private void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/FruverAguacates", "root", "10j56yeyo");
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }


    public void agregarVenta(List<ProductoDetalle> productos, String codUsuario, String fechaVenta, String identificacionCliente, String totalVenta) throws SQLException {
        conectar();
        conexion.setAutoCommit(false);

        try {
            // Insertar venta
            String sqlSales = "INSERT INTO SALES (COD_USER, DATE_SALE, COSTUMER_IDENTIFICATION, TOTAL_SALE_VALUE) VALUES (?, ?, ?, ?)";
            ps = conexion.prepareStatement(sqlSales, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, codUsuario);
            ps.setString(2, fechaVenta);
            if(identificacionCliente.isEmpty()){
                ps.setNull(3, Types.VARCHAR);
            } else {
                ps.setString(3, identificacionCliente);
            }
            ps.setString(4, totalVenta);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int codVentaGenerado = 0;
            if (rs.next()) {
                codVentaGenerado = rs.getInt(1);
            }

            // Insertar detalles
            String sqlDetails = "INSERT INTO SALES_DETAILS (COD_SALE, COD_PRODUCT, PRODUCT_NAME, PRODUCT_QUANTITY_Kg, PRODUCT_PRICE, TOTAL_PRODUCT) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conexion.prepareStatement(sqlDetails);

            for (ProductoDetalle producto : productos) {
                ps.setInt(1, codVentaGenerado);
                ps.setString(2, producto.getCodProducto());
                ps.setString(3, producto.getNombreProducto());
                ps.setString(4, producto.getCantidadKg());
                ps.setString(5, producto.getPrecioProducto());
                ps.setString(6, producto.getTotalProducto());
                ps.addBatch();
            }

            ps.executeBatch();
            conexion.commit();

        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        } finally {
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        }
    }

    void mostrarProductos() {
        conectar();
        String sqlProducts = "SELECT * FROM PRODUCTS";

        try {
            ps = conexion.prepareStatement(sqlProducts);
            rs = ps.executeQuery();

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

            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getString("COD_PRODUCT");
                fila[1] = rs.getString("COD_PURCHASE");
                fila[2] = rs.getString("PRODUCT_NAME");
                fila[3] = rs.getString("QUANTITY_Kg");
                fila[4] = rs.getString("PURCHASE_VALUE");
                fila[5] = rs.getString("PRODUCT_PRICE");
                fila[6] = rs.getString("DESCRIPTION_PRODUCT_STATUS");

                model.addRow(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hay un error al mostrar los datos de productos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void generarFacturaPDF() {
        conectar();

        try {
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream("Factura_Venta.pdf"));
            documento.open();

            documento.add(new Paragraph("FACTURA DE VENTA FRUVER AGUACATES JJ"));
            documento.add(new Paragraph("Cod_Venta: " + Cod_Venta.getText()));
            documento.add(new Paragraph("Fecha_Venta: " + Fecha_Venta.getText()));
            documento.add(new Paragraph("Identificación_Cliente: " + Identificacion_Cliente.getText()));
            documento.add(new Paragraph("Total_Venta: " + Total_Venta.getText()));
            List<ProductoDetalle> copiaProductos = new ArrayList<>(productosVenta);
            for (ProductoDetalle producto : copiaProductos) {
                documento.add(new Paragraph("Cod_Producto: " + producto.COD_PRODUCT));
                documento.add(new Paragraph("Nombre_Producto: " + producto.PRODUCT_NAME));
                documento.add(new Paragraph("Cantidad_Producto_Kg: " + producto.PRODUCT_QUANTITY_Kg));
                documento.add(new Paragraph("Precio_Producto: " + producto.PRODUCT_PRICE));
                documento.add(new Paragraph("Total_Producto: " + producto.TOTAL_PRODUCT));
                documento.add(new Paragraph("\n"));
            }
            documento.close();
            JOptionPane.showMessageDialog(null, "Factura generada con exito y se guardo en Factura_Venta_pdf");
        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar la factura" + e.getMessage());
            e.printStackTrace();
        }

    }
    public static void mostrarVentanaVentas() {
        VentaModelo ventas = new VentaModelo();
        ventas.setVisible(true);
        ventas.pack();
    }

}

