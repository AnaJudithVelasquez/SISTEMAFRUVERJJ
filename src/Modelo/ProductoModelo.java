package Modelo;


import javax.swing.*;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ProductoModelo extends JFrame{
    Connection conexion;
    PreparedStatement ps;
    ResultSet rs;

    public ProductoModelo() {
    }

    public void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/FruverAguacates", "root", "10j56yeyo");
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }

    public int agregar(String codCompra, String nombreProducto, String stockVal, String cantidadKg,
                       String cantidadFinalKg, String valorCompra, String precioProducto, String descripcion) {
        int codProductoGenerado = -1;
        conectar();
        String sql = "INSERT INTO PRODUCTS (COD_PURCHASE, PRODUCT_NAME, STOCK, QUANTITY_Kg, FINAL_QUANTITY_Kg, PURCHASE_VALUE, PRODUCT_PRICE, DESCRIPTION_PRODUCT_STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, codCompra);
            ps.setString(2, nombreProducto);
            ps.setString(3, stockVal);
            ps.setString(4, cantidadKg);
            ps.setString(5, cantidadFinalKg);
            ps.setString(6, valorCompra);
            ps.setString(7, precioProducto);
            ps.setString(8, descripcion);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                codProductoGenerado = rs.getInt(1);
            } else {
                throw new SQLException("Error al obtener el COD_PRODUCT generado.");
            }
            JOptionPane.showMessageDialog(null, "Los datos se insertaron correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hay un error al insertar datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }

        return codProductoGenerado;
    }

    public boolean modificar(String codProducto, String codCompra, String nombreProducto, String stockVal,
                             String cantidadKg, String cantidadFinalKg, String valorCompra,
                             String precioProducto, String descripcion) {
        boolean exito = false;
        conectar();
        String sql = "UPDATE PRODUCTS SET COD_PURCHASE = ?, PRODUCT_NAME = ?, STOCK = ?, QUANTITY_Kg = ?, FINAL_QUANTITY_Kg = ?, PURCHASE_VALUE = ?, PRODUCT_PRICE = ?, DESCRIPTION_PRODUCT_STATUS = ? WHERE COD_PRODUCT = ?";

        try {
            ps = conexion.prepareStatement(sql);

            ps.setString(1, codCompra);
            ps.setString(2, nombreProducto);
            ps.setString(3, stockVal);
            ps.setString(4, cantidadKg);
            ps.setString(5, cantidadFinalKg);
            ps.setString(6, valorCompra);
            ps.setString(7, precioProducto);
            ps.setString(8, descripcion);
            ps.setString(9, codProducto);

            int filasModificadas = ps.executeUpdate();
            if (filasModificadas > 0) {
                exito = true;
                JOptionPane.showMessageDialog(null, "Los datos se actualizaron correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encuentra el producto con el código que especificastes.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hay un error al actualizar los datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }

        return exito;
    }

    public boolean eliminar(String codProducto) {
        boolean exito = false;
        conectar();
        String sql = "DELETE FROM PRODUCTS WHERE COD_PRODUCT = ?";

        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, codProducto);

            int filasEliminadas = ps.executeUpdate();
            if (filasEliminadas > 0) {
                exito = true;
                JOptionPane.showMessageDialog(null, "El producto se eliminó correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el producto con el código que especificastes.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hay un error al eliminar el producto: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }

        return exito;
    }

    public DefaultTableModel listarProductos() {
        DefaultTableModel modelo = new DefaultTableModel();

        // Definir columnas
        modelo.addColumn("Cod_Producto");
        modelo.addColumn("Cod_Compra");
        modelo.addColumn("Nombre_Producto");
        modelo.addColumn("Stock");
        modelo.addColumn("Cantidad_Kg");
        modelo.addColumn("Cantidad Final_KG");
        modelo.addColumn("Valor_Compra");
        modelo.addColumn("Precio_Producto");
        modelo.addColumn("Descripcion");

        conectar();
        String sql = "SELECT * FROM PRODUCTS";

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[9];
                fila[0] = rs.getString("COD_PRODUCT");
                fila[1] = rs.getString("COD_PURCHASE");
                fila[2] = rs.getString("PRODUCT_NAME");
                fila[3] = rs.getString("STOCK");
                fila[4] = rs.getString("QUANTITY_Kg");
                fila[5] = rs.getString("FINAL_QUANTITY_KG");
                fila[6] = rs.getString("PURCHASE_VALUE");
                fila[7] = rs.getString("PRODUCT_PRICE");
                fila[8] = rs.getString("DESCRIPTION_PRODUCT_STATUS");

                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hay un error al mostrar los datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }

        return modelo;
    }

    public DefaultTableModel listarCompras() {
        DefaultTableModel modelo = new DefaultTableModel();

        // Definir columnas
        modelo.addColumn("Cod_Compra");
        modelo.addColumn("Cod_Administrador");
        modelo.addColumn("Cod_Proveedor");
        modelo.addColumn("Fecha_Compra");
        modelo.addColumn("Producto_Comprado");
        modelo.addColumn("Cantidad_Producto_Kg");
        modelo.addColumn("Valor_Unitario");
        modelo.addColumn("Total_Producto");
        modelo.addColumn("Total_Compra");

        conectar();
        String sqlPURCHASE = "SELECT p.COD_PURCHASE, p.COD_USER, p.COD_SUPPLIER, p.DATE_PURCHASE, p.TOTAL_PURCHASE_VALUE, pd.PURCHASED_PRODUCT, pd.UNIT_VALUE, pd.QUANTITY_Kg, pd.TOTAL_PRODUCT FROM PURCHASES p JOIN PURCHASES_DETAILS pd ON p.COD_PURCHASE = pd.COD_PURCHASE";

        try {
            ps = conexion.prepareStatement(sqlPURCHASE);
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[9];
                fila[0] = rs.getString("COD_PURCHASE");
                fila[1] = rs.getString("COD_USER");
                fila[2] = rs.getString("COD_SUPPLIER");
                fila[3] = rs.getString("DATE_PURCHASE");
                fila[4] = rs.getString("PURCHASED_PRODUCT");
                fila[5] = rs.getString("QUANTITY_Kg");
                fila[6] = rs.getString("UNIT_VALUE");
                fila[7] = rs.getString("TOTAL_PRODUCT");
                fila[8] = rs.getString("TOTAL_PURCHASE_VALUE");

                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hay un error al mostrar los datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }

        return modelo;
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

    public ResultSet obtenerProductos() {
        ResultSet rs = null;
        try {
            conectar();
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM producto");
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
}
