package Modelo;


import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraModelo extends JFrame {
    private Connection conexion;
    private PreparedStatement ps;
    private ResultSet rs;

    // Clase interna para manejar detalles de compra
    public class CompraDetalle {
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

    // Lista para almacenar los productos de una compra
    private List<CompraDetalle> productosCompra = new ArrayList<>();

    public List<CompraDetalle> getProductosCompra() {
        return productosCompra;
    }

    public void limpiarProductosCompra() {
        productosCompra.clear();
    }

    public void agregarProductoACompra(CompraDetalle producto) {
        productosCompra.add(producto);
    }

    // Métodos para conexión a base de datos
    public void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/FruverAguacates", "root", "10j56yeyo");
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

    // Operaciones CRUD para proveedores
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

    public boolean modificarProveedor(String codProveedor, String nombre, String direccion, String telefono) {
        conectar();
        boolean exito = false;
        String sql = "update SUPPLIERS set SUPPLIER_NAME = ?, ADDRESS = ?, PHONE_NUMBER = ? WHERE COD_SUPPLIER = ?";

        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.setString(3, telefono);
            ps.setString(4, codProveedor);

            int filasModificadas = ps.executeUpdate();
            exito = filasModificadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return exito;
    }

    public boolean eliminarProveedor(String codProveedor) {
        conectar();
        boolean exito = false;
        String sql = "delete from SUPPLIERS where COD_SUPPLIER = ?";

        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, codProveedor);
            int filasEliminadas = ps.executeUpdate();
            exito = filasEliminadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return exito;
    }

    public ResultSet obtenerProveedores() {
        conectar();
        ResultSet resultado = null;
        String sql = "SELECT * FROM SUPPLIERS";

        try {
            ps = conexion.prepareStatement(sql);
            resultado = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            cerrarConexion();
        }
        return resultado;
    }

    // Operaciones para compras
    public int agregarCompra(String codUsuario, String codProveedor, String fechaCompra, String totalCompra, List<CompraDetalle> productos) {
        conectar();
        int codCompraGenerado = -1;

        try {
            conexion.setAutoCommit(false);
            String sqlCompras = "INSERT INTO PURCHASES (COD_USER, COD_SUPPLIER, DATE_PURCHASE, TOTAL_PURCHASE_VALUE) VALUES (?, ?, ?, ?)";
            ps = conexion.prepareStatement(sqlCompras, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, codUsuario);
            ps.setString(2, codProveedor);
            ps.setString(3, fechaCompra);
            ps.setString(4, totalCompra.replace(",", "."));

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                codCompraGenerado = rs.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el COD_PURCHASE generado.");
            }

            String sqlDetalle = "INSERT INTO PURCHASES_DETAILS (COD_PURCHASE, PURCHASED_PRODUCT, UNIT_VALUE, QUANTITY_Kg, TOTAL_PRODUCT) VALUES (?, ?, ?, ?, ?)";
            ps = conexion.prepareStatement(sqlDetalle);

            for (CompraDetalle producto : productos) {
                ps.setInt(1, codCompraGenerado);
                ps.setString(2, producto.PURCHASED_PRODUCT);
                ps.setString(3, producto.UNIT_VALUE);
                ps.setString(4, producto.QUANTITY_Kg);
                ps.setString(5, producto.TOTAL_PRODUCT);
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();

            conexion.commit();
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return codCompraGenerado;
    }

    public double calcularTotalProducto(double cantidad, double precioUnitario) {
        return cantidad * precioUnitario;
    }

    public double calcularTotalCompra(List<CompraDetalle> productos) {
        double totalCompra = 0;
        for (CompraDetalle producto : productos) {
            try {
                double totalProducto = Double.parseDouble(producto.TOTAL_PRODUCT.replace(",","."));
                totalCompra += totalProducto;
            } catch (NumberFormatException e) {
                System.out.println("Error al parsear el total del producto: " + producto.TOTAL_PRODUCT);
            }
        }
        return totalCompra;
    }

    public ResultSet obtenerCompras() {
        conectar();
        ResultSet resultado = null;
        String sqlPURCHASE = "SELECT p.COD_PURCHASE, p.COD_USER, p.COD_SUPPLIER, p.DATE_PURCHASE, p.TOTAL_PURCHASE_VALUE, " +
                "pd.PURCHASED_PRODUCT, pd.UNIT_VALUE, pd.QUANTITY_Kg, pd.TOTAL_PRODUCT " +
                "FROM PURCHASES p JOIN PURCHASES_DETAILS pd ON p.COD_PURCHASE = pd.COD_PURCHASE";

        try {
            ps = conexion.prepareStatement(sqlPURCHASE);
            resultado = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            cerrarConexion();
        }
        return resultado;
    }
}
