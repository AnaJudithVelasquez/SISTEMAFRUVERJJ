package Modelo;

import javax.swing.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class VentaModelo extends JFrame{
    private Connection conexion;
    private PreparedStatement ps;
    private ResultSet rs;

    // Clase para almacenar los detalles de producto
    public static class ProductoDetalle {
        private String codProducto;
        private String nombreProducto;
        private String cantidadKg;
        private String precioProducto;
        private String totalProducto;

        public ProductoDetalle(String codProducto, String nombreProducto, String cantidadKg, String precioProducto, String totalProducto) {
            this.codProducto = codProducto;
            this.nombreProducto = nombreProducto;
            this.cantidadKg = cantidadKg;
            this.precioProducto = precioProducto;
            this.totalProducto = totalProducto;
        }

        public String getCodProducto() {
            return codProducto;
        }

        public String getNombreProducto() {
            return nombreProducto;
        }

        public String getCantidadKg() {
            return cantidadKg;
        }

        public String getPrecioProducto() {
            return precioProducto;
        }

        public String getTotalProducto() {
            return totalProducto;
        }
    }

    public void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/FruverAguacates", "root", "10j56yeyo");
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }

    public void cerrarRecursos() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> obtenerDatosVentas() {
        List<Object[]> datos = new ArrayList<>();
        conectar();
        String sqlSALES = "SELECT s.COD_SALE, s.COD_USER, s.DATE_SALE, s.COSTUMER_IDENTIFICATION, s.TOTAL_SALE_VALUE, sd.COD_PRODUCT, sd.PRODUCT_NAME, sd.PRODUCT_QUANTITY_Kg, sd.PRODUCT_PRICE, sd.TOTAL_PRODUCT " +
                "FROM SALES s JOIN SALES_DETAILS sd ON s.COD_SALE = sd.COD_SALE";

        try {
            ps = conexion.prepareStatement(sqlSALES);
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[10];
                fila[0] = rs.getString("COD_SALE");
                fila[1] = rs.getString("COD_USER");
                fila[2] = rs.getString("DATE_SALE");
                fila[3] = rs.getString("COSTUMER_IDENTIFICATION");
                fila[4] = rs.getString("COD_PRODUCT");
                fila[5] = rs.getString("PRODUCT_NAME");
                fila[6] = rs.getString("PRODUCT_QUANTITY_Kg");
                fila[7] = rs.getString("PRODUCT_PRICE");
                fila[8] = rs.getString("TOTAL_PRODUCT");
                fila[9] = rs.getString("TOTAL_SALE_VALUE");

                datos.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos();
        }
        return datos;
    }

    public List<Object[]> obtenerProductos() {
        List<Object[]> productos = new ArrayList<>();
        conectar();
        String sqlProducts = "SELECT * FROM PRODUCTS";

        try {
            ps = conexion.prepareStatement(sqlProducts);
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getString("COD_PRODUCT");
                fila[1] = rs.getString("COD_PURCHASE");
                fila[2] = rs.getString("PRODUCT_NAME");
                fila[3] = rs.getString("QUANTITY_Kg");
                fila[4] = rs.getString("PURCHASE_VALUE");
                fila[5] = rs.getString("PRODUCT_PRICE");
                fila[6] = rs.getString("DESCRIPTION_PRODUCT_STATUS");

                productos.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos();
        }
        return productos;
    }

    public int agregarVenta(String codUsuario, String fechaVenta, String identificacionCliente, String totalVenta, List<ProductoDetalle> productosVenta) {
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

            for (ProductoDetalle producto : productosVenta) {
                ps.setInt(1, codVentaGenerado);
                ps.setString(2, producto.getCodProducto());
                ps.setString(3, producto.getNombreProducto());
                ps.setString(4, producto.getCantidadKg());
                ps.setString(5, producto.getPrecioProducto());
                ps.setString(6, producto.getTotalProducto());
                ps.addBatch();

                // Actualizar la cantidad en el inventario después de cada venta
                String cantidadVendida = producto.getCantidadKg();
                String codProducto = producto.getCodProducto();

                // Actualizamos la cantidad del producto en el inventario (disminuir QUANTITY_Kg y FINAL_QUANTITY_Kg)
                String actualizarCantidadSQL = "UPDATE PRODUCTS SET FINAL_QUANTITY_Kg = FINAL_QUANTITY_Kg - ? WHERE COD_PRODUCT = ?";
                PreparedStatement actualizarCantidadPS = conexion.prepareStatement(actualizarCantidadSQL);
                actualizarCantidadPS.setString(1, cantidadVendida);
                actualizarCantidadPS.setString(2, codProducto);
                actualizarCantidadPS.executeUpdate();
                actualizarCantidadPS.close();
            }

            int[] filasInsertadas = ps.executeBatch();

            if (filasInsertadas.length > 0) {
                conexion.commit();
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
            cerrarRecursos();
        }
    }

    public double calcularTotalPorProducto(double cantidad, double precio) {
        return cantidad * precio;
    }

    public double calcularTotalVenta(List<ProductoDetalle> productosVenta) {
        double totalVenta = 0;
        for (ProductoDetalle producto : productosVenta) {
            try {
                double totalProducto = Double.parseDouble(producto.getTotalProducto().replace(",", "."));
                totalVenta += totalProducto;
            } catch (NumberFormatException e) {
                System.out.println("Error al parsear el total del producto: " + producto.getTotalProducto());
            }
        }
        return totalVenta;
    }
}



