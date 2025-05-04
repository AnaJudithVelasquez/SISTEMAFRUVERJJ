package Modelo;


import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraModelo extends JFrame {
    private Connection conexion;
    private PreparedStatement ps;
    private ResultSet rs;

    // Lista para almacenar los detalles de productos de una compra
    private List<CompraDetalle> productosCompra = new ArrayList<>();

    // Clase interna para representar un detalle de compra
    public static class CompraDetalle {
        String COD_PURCHASE;
        String PURCHASED_PRODUCT;
        String UNIT_VALUE;
        String QUANTITY_Kg;
        String TOTAL_PRODUCT;

        public CompraDetalle(String COD_PURCHASE, String PURCHASED_PRODUCT, String UNIT_VALUE, String QUANTITY_Kg, String TOTAL_PRODUCT) {
            this.COD_PURCHASE = COD_PURCHASE;
            this.PURCHASED_PRODUCT = PURCHASED_PRODUCT;
            this.UNIT_VALUE = UNIT_VALUE;
            this.QUANTITY_Kg = QUANTITY_Kg;
            this.TOTAL_PRODUCT = TOTAL_PRODUCT;
        }
    }

    // Método para conectar a la base de datos
    public void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/FruverAguacates", "root", "10j56yeyo");
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }

    // Método para cerrar la conexión y recursos
    public void cerrarConexion() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para agregar un proveedor
    public int agregarProveedor(String nombre, String direccion, String telefono) {
        conectar();
        int codProveedorGenerado = -1;
        String sql = "INSERT INTO SUPPLIERS (SUPPLIER_NAME, ADDRESS, PHONE_NUMBER) VALUES (?, ?, ?)";

        try {
            ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.setString(3, telefono);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                codProveedorGenerado = rs.getInt(1);
            } else {
                throw new SQLException("Error al obtener el COD_SUPPLIER generado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return codProveedorGenerado;
    }

    // Método para modificar un proveedor
    public boolean modificarProveedor(String codProveedor, String nombre, String direccion, String telefono) {
        conectar();
        boolean exito = false;
        String sql = "UPDATE SUPPLIERS SET SUPPLIER_NAME = ?, ADDRESS = ?, PHONE_NUMBER = ? WHERE COD_SUPPLIER = ?";

        try {
            ps = conexion.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.setString(3, telefono);
            ps.setString(4, codProveedor);

            int filasModificadas = ps.executeUpdate();
            if (filasModificadas > 0) {
                exito = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return exito;
    }

    // Método para eliminar un proveedor
    public boolean eliminarProveedor(String codProveedor) {
        conectar();
        boolean exito = false;
        String sql = "DELETE FROM SUPPLIERS WHERE COD_SUPPLIER = ?";

        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, codProveedor);

            int filasEliminadas = ps.executeUpdate();
            if (filasEliminadas > 0) {
                exito = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return exito;
    }

    // Método para obtener todos los proveedores
    public Object[][] obtenerProveedores() {
        conectar();
        List<Object[]> filas = new ArrayList<>();
        String sql = "SELECT * FROM SUPPLIERS";

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                        rs.getString("COD_SUPPLIER"),
                        rs.getString("SUPPLIER_NAME"),
                        rs.getString("ADDRESS"),
                        rs.getString("PHONE_NUMBER")
                };
                filas.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }

        Object[][] resultado = new Object[filas.size()][];
        for (int i = 0; i < filas.size(); i++) {
            resultado[i] = filas.get(i);
        }
        return resultado;
    }

    // Método para agregar un artículo a la compra actual
    public void agregarArticulo(String codCompra, String producto, String valorUnitario, String cantidad, String totalProducto) {
        CompraDetalle nuevoProducto = new CompraDetalle(codCompra, producto, valorUnitario, cantidad, totalProducto);
        productosCompra.add(nuevoProducto);
    }

    // Método para calcular el total por producto
    public double calcularTotalPorProducto(double cantidad, double precio) {
        return cantidad * precio;
    }

    // Método para calcular el total de la compra
    public double calcularTotalCompra() {
        double totalCompra = 0;
        for (CompraDetalle producto : productosCompra) {
            try {
                double totalProducto = Double.parseDouble(producto.TOTAL_PRODUCT.replace(",","."));
                totalCompra += totalProducto;
            } catch (NumberFormatException e) {
                System.out.println("Error al parsear el total del producto: " + producto.TOTAL_PRODUCT);
            }
        }
        return totalCompra;
    }

    // Método para realizar la compra
    public int agregarCompra(String codUsuario, String codProveedor, String fechaCompra, String totalCompra) {
        conectar();
        int codCompraGenerado = -1;

        try {
            conexion.setAutoCommit(false);
            String sqlCompras = "INSERT INTO PURCHASES (COD_USER, COD_SUPPLIER, DATE_PURCHASE, TOTAL_PURCHASE_VALUE) VALUES (?, ?, ?, ?)";
            ps = conexion.prepareStatement(sqlCompras, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, codUsuario);
            ps.setString(2, codProveedor);
            ps.setString(3, fechaCompra);
            ps.setString(4, totalCompra);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                codCompraGenerado = rs.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el COD_PURCHASE generado.");
            }

            String sqlDetalle = "INSERT INTO PURCHASES_DETAILS (COD_PURCHASE, PURCHASED_PRODUCT, UNIT_VALUE, QUANTITY_Kg, TOTAL_PRODUCT) VALUES (?, ?, ?, ?, ?)";
            ps = conexion.prepareStatement(sqlDetalle);

            for (CompraDetalle producto : productosCompra) {
                ps.setInt(1, codCompraGenerado);
                ps.setString(2, producto.PURCHASED_PRODUCT);
                ps.setString(3, producto.UNIT_VALUE);
                ps.setString(4, producto.QUANTITY_Kg);
                ps.setString(5, producto.TOTAL_PRODUCT);
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();

            // Actualización del inventario
            String sqlUpdate = "UPDATE PRODUCTS SET FINAL_QUANTITY_Kg = FINAL_QUANTITY_Kg + ? WHERE PRODUCT_NAME = ?";
            PreparedStatement psUpdate = conexion.prepareStatement(sqlUpdate);

            for (CompraDetalle producto : productosCompra) {
                double cantidad = Double.parseDouble(producto.QUANTITY_Kg);
                psUpdate.setDouble(1, cantidad);
                psUpdate.setString(2, producto.PURCHASED_PRODUCT);
                psUpdate.addBatch();
            }
            psUpdate.executeBatch();
            psUpdate.close();

            conexion.commit();
            productosCompra.clear();
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return codCompraGenerado;
    }

    // Método para obtener las compras realizadas
    public Object[][] obtenerCompras() {
        conectar();
        List<Object[]> filas = new ArrayList<>();
        String sqlPURCHASE = "SELECT p.COD_PURCHASE, p.COD_USER, p.COD_SUPPLIER, p.DATE_PURCHASE, p.TOTAL_PURCHASE_VALUE, " +
                "pd.PURCHASED_PRODUCT, pd.UNIT_VALUE, pd.QUANTITY_Kg, pd.TOTAL_PRODUCT " +
                "FROM PURCHASES p JOIN PURCHASES_DETAILS pd ON p.COD_PURCHASE = pd.COD_PURCHASE";

        try {
            ps = conexion.prepareStatement(sqlPURCHASE);
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                        rs.getString("COD_PURCHASE"),
                        rs.getString("COD_USER"),
                        rs.getString("COD_SUPPLIER"),
                        rs.getString("DATE_PURCHASE"),
                        rs.getString("PURCHASED_PRODUCT"),
                        rs.getString("UNIT_VALUE"),
                        rs.getString("QUANTITY_Kg"),
                        rs.getString("TOTAL_PRODUCT"),
                        rs.getString("TOTAL_PURCHASE_VALUE")
                };
                filas.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }

        Object[][] resultado = new Object[filas.size()][];
        for (int i = 0; i < filas.size(); i++) {
            resultado[i] = filas.get(i);
        }
        return resultado;
    }

    // Método para obtener la lista de productos de la compra
    public List<CompraDetalle> getProductosCompra() {
        return productosCompra;
    }

    // Método para limpiar la lista de productos
    public void limpiarProductosCompra() {
        productosCompra.clear();
    }
}
