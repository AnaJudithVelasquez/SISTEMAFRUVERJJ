package Modelo;


import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraModelo extends JFrame {
    private Connection conexion;
    private PreparedStatement ps;
    private ResultSet rs;

    public CompraModelo() {
        conectar();
    }

    private void conectar() {
        try {
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/FruverAguacates", "root", "10j56yeyo"
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }

    public void cerrarConexion() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int agregarProveedor(String nombre, String direccion, String telefono) throws SQLException {
        String sql = "INSERT INTO SUPPLIERS (SUPPLIER_NAME, ADDRESS, PHONE_NUMBER) VALUES (?, ?, ?)";
        ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, nombre);
        ps.setString(2, direccion);
        ps.setString(3, telefono);
        ps.executeUpdate();

        rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("Error al obtener el COD_SUPPLIER generado.");
        }
    }

    public boolean modificarProveedor(int codProveedor, String nombre, String direccion, String telefono) throws SQLException {
        String sql = "UPDATE SUPPLIERS SET SUPPLIER_NAME = ?, ADDRESS = ?, PHONE_NUMBER = ? WHERE COD_SUPPLIER = ?";
        ps = conexion.prepareStatement(sql);
        ps.setString(1, nombre);
        ps.setString(2, direccion);
        ps.setString(3, telefono);
        ps.setInt(4, codProveedor);

        return ps.executeUpdate() > 0;
    }

    public boolean eliminarProveedor(int codProveedor) throws SQLException {
        String sql = "DELETE FROM SUPPLIERS WHERE COD_SUPPLIER = ?";
        ps = conexion.prepareStatement(sql);
        ps.setInt(1, codProveedor);

        return ps.executeUpdate() > 0;
    }

    public List<String[]> obtenerProveedores() throws SQLException {
        List<String[]> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM SUPPLIERS";
        ps = conexion.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            proveedores.add(new String[]{
                    rs.getString("COD_SUPPLIER"),
                    rs.getString("SUPPLIER_NAME"),
                    rs.getString("ADDRESS"),
                    rs.getString("PHONE_NUMBER")
            });
        }
        return proveedores;
    }

    public int agregarCompra(String codUser, String codSupplier, String datePurchase, String totalPurchaseValue) throws SQLException {
        conexion.setAutoCommit(false);
        String sql = "INSERT INTO PURCHASES (COD_USER, COD_SUPPLIER, DATE_PURCHASE, TOTAL_PURCHASE_VALUE) VALUES (?, ?, ?, ?)";
        ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, codUser);
        ps.setString(2, codSupplier);
        ps.setString(3, datePurchase);
        ps.setString(4, totalPurchaseValue);
        ps.executeUpdate();

        rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("No se pudo obtener el COD_PURCHASE generado.");
        }
    }

    public void agregarDetalleCompra(int codCompra, List<CompraDetalle> productos) throws SQLException {
        String sqlDetalle = "INSERT INTO PURCHASES_DETAILS (COD_PURCHASE, PURCHASED_PRODUCT, UNIT_VALUE, QUANTITY_Kg, TOTAL_PRODUCT) VALUES (?, ?, ?, ?, ?)";
        ps = conexion.prepareStatement(sqlDetalle);

        for (CompraDetalle producto : productos) {
            ps.setInt(1, codCompra);
            ps.setString(2, producto.getProducto());
            ps.setString(3, producto.getValorUnitario());
            ps.setString(4, producto.getCantidadKg());
            ps.setString(5, producto.getTotalProducto());
            ps.addBatch();
        }
        ps.executeBatch();
    }

    public void actualizarInventario(List<CompraDetalle> productos) throws SQLException {
        String sqlUpdate = "UPDATE PRODUCTS SET FINAL_QUANTITY_Kg = FINAL_QUANTITY_Kg + ? WHERE PRODUCT_NAME = ?";
        ps = conexion.prepareStatement(sqlUpdate);

        for (CompraDetalle producto : productos) {
            double cantidad = Double.parseDouble(producto.getCantidadKg());
            ps.setDouble(1, cantidad);
            ps.setString(2, producto.getProducto());
            ps.addBatch();
        }
        ps.executeBatch();
        conexion.commit();
    }

    public List<String[]> obtenerCompras() throws SQLException {
        List<String[]> compras = new ArrayList<>();
        String sql = "SELECT p.COD_PURCHASE, p.COD_USER, p.COD_SUPPLIER, p.DATE_PURCHASE, p.TOTAL_PURCHASE_VALUE, pd.PURCHASED_PRODUCT, pd.UNIT_VALUE, pd.QUANTITY_Kg, pd.TOTAL_PRODUCT FROM PURCHASES p JOIN PURCHASES_DETAILS pd ON p.COD_PURCHASE = pd.COD_PURCHASE";
        ps = conexion.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            compras.add(new String[]{
                    rs.getString("COD_PURCHASE"),
                    rs.getString("COD_USER"),
                    rs.getString("COD_SUPPLIER"),
                    rs.getString("DATE_PURCHASE"),
                    rs.getString("PURCHASED_PRODUCT"),
                    rs.getString("UNIT_VALUE"),
                    rs.getString("QUANTITY_Kg"),
                    rs.getString("TOTAL_PRODUCT"),
                    rs.getString("TOTAL_PURCHASE_VALUE")
            });
        }
        return compras;
    }

    public static void mostrarVentanaCompras() {
        CompraModelo compras = new CompraModelo();
        compras.setVisible(true);
        compras.pack();
    }
}
