package Modelo;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ProductoModelo extends JFrame{
    private Connection conexion;
    private PreparedStatement ps;
    private ResultSet rs;

    public void conectar() throws SQLException {
        conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/FruverAguacates", "root", "10j56yeyo");
    }

    public void cerrar() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregarProducto(String codCompra, String nombre, String stock, String cantidad, String cantidadFinal, String precioCompra, String precioProducto, String descripcion) throws SQLException {
        conectar();
        String sql = "INSERT INTO PRODUCTS (COD_PURCHASE, PRODUCT_NAME, STOCK, QUANTITY_Kg, FINAL_QUANTITY_Kg, PURCHASE_VALUE, PRODUCT_PRICE, DESCRIPTION_PRODUCT_STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, codCompra);
        ps.setString(2, nombre);
        ps.setString(3, stock);
        ps.setString(4, cantidad);
        ps.setString(5, cantidadFinal);
        ps.setString(6, precioCompra);
        ps.setString(7, precioProducto);
        ps.setString(8, descripcion);

        ps.executeUpdate();
    }

    public void modificarProducto(String codProducto, String codCompra, String nombre, String stock, String cantidad, String precioCompra, String precioProducto, String descripcion) throws SQLException {
        conectar();
        String sql = "UPDATE PRODUCTS SET COD_PURCHASE=?, PRODUCT_NAME=?, STOCK=?, QUANTITY_Kg=?, PURCHASE_VALUE=?, PRODUCT_PRICE=?, DESCRIPTION_PRODUCT_STATUS=? WHERE COD_PRODUCT=?";
        ps = conexion.prepareStatement(sql);

        ps.setString(1, codCompra);
        ps.setString(2, nombre);
        ps.setString(3, stock);
        ps.setString(4, cantidad);
        ps.setString(5, precioCompra);
        ps.setString(6, precioProducto);
        ps.setString(7, descripcion);
        ps.setString(8, codProducto);

        ps.executeUpdate();
    }

    public void eliminarProducto(String codProducto) throws SQLException {
        conectar();
        String sql = "DELETE FROM PRODUCTS WHERE COD_PRODUCT=?";
        ps = conexion.prepareStatement(sql);
        ps.setString(1, codProducto);
        ps.executeUpdate();
    }

    public void mostrarProductos(DefaultTableModel modeloTabla) throws SQLException {
        conectar();
        String sql = "SELECT * FROM PRODUCTS";
        ps = conexion.prepareStatement(sql);
        rs = ps.executeQuery();

        modeloTabla.setRowCount(0);
        if (modeloTabla.getColumnCount() == 0) {
            modeloTabla.addColumn("Cod_Producto");
            modeloTabla.addColumn("Cod_Compra");
            modeloTabla.addColumn("Nombre_Producto");
            modeloTabla.addColumn("Stock");
            modeloTabla.addColumn("Cantidad_Kg");
            modeloTabla.addColumn("Cantidad Final_Kg");
            modeloTabla.addColumn("Valor_Compra");
            modeloTabla.addColumn("Precio_Producto");
            modeloTabla.addColumn("Descripcion");
        }

        while (rs.next()) {
            modeloTabla.addRow(new Object[]{
                    rs.getString("COD_PRODUCT"),
                    rs.getString("COD_PURCHASE"),
                    rs.getString("PRODUCT_NAME"),
                    rs.getString("STOCK"),
                    rs.getString("QUANTITY_Kg"),
                    rs.getString("FINAL_QUANTITY_KG"),
                    rs.getString("PURCHASE_VALUE"),
                    rs.getString("PRODUCT_PRICE"),
                    rs.getString("DESCRIPTION_PRODUCT_STATUS")
            });
        }
    }

    public void mostrarCompras(DefaultTableModel modeloTabla) throws SQLException {
        conectar();
        String sql = "SELECT p.COD_PURCHASE, p.COD_USER, p.COD_SUPPLIER, p.DATE_PURCHASE, pd.PURCHASED_PRODUCT, pd.QUANTITY_Kg, pd.UNIT_VALUE, pd.TOTAL_PRODUCT, p.TOTAL_PURCHASE_VALUE FROM PURCHASES p JOIN PURCHASES_DETAILS pd ON p.COD_PURCHASE = pd.COD_PURCHASE";
        ps = conexion.prepareStatement(sql);
        rs = ps.executeQuery();

        modeloTabla.setRowCount(0);
        if (modeloTabla.getColumnCount() == 0) {
            modeloTabla.addColumn("Cod_Compra");
            modeloTabla.addColumn("Cod_Admin");
            modeloTabla.addColumn("Cod_Proveedor");
            modeloTabla.addColumn("Fecha_Compra");
            modeloTabla.addColumn("Producto_Comprado");
            modeloTabla.addColumn("Cantidad_Kg");
            modeloTabla.addColumn("Valor_Unitario");
            modeloTabla.addColumn("Total_Producto");
            modeloTabla.addColumn("Total_Compra");
        }

        while (rs.next()) {
            modeloTabla.addRow(new Object[]{
                    rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6),
                    rs.getString(7), rs.getString(8), rs.getString(9)
            });
        }
    }

    public static void mostrarVentanaProductos() {
        ProductoModelo productos = new ProductoModelo();
        productos.setVisible(true);
        productos.pack();

    }
}

