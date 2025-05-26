package Modelo;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class ConsultaModelo {
    private Connection conexion;

    public ConsultaModelo(Connection conexion) {
        this.conexion = conexion;
    }

    public DefaultTableModel consultarVentasPorFecha(String fechaInicio, String fechaFin) throws SQLException {
        String sql = "SELECT COD_SALE, DATE_SALE, COSTUMER_IDENTIFICATION, TOTAL_SALE_VALUE " +
                "FROM SALES WHERE DATE_SALE BETWEEN ? AND ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setString(1, fechaInicio);
        ps.setString(2, fechaFin);
        ResultSet rs = ps.executeQuery();

        // Títulos personalizados en español
        String[] titulos = {"Código Venta", "Fecha de Venta", "Identificación del Cliente", "Valor Total de la Venta"};
        return construirModeloDesdeResultSet(rs, titulos);
    }

    public DefaultTableModel consultarComprasPorFecha(String fechaInicio, String fechaFin) throws SQLException {
        String sql = "SELECT COD_PURCHASE, DATE_PURCHASE, TOTAL_PURCHASE_VALUE " +
                "FROM PURCHASES WHERE DATE_PURCHASE BETWEEN ? AND ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setString(1, fechaInicio);
        ps.setString(2, fechaFin);
        ResultSet rs = ps.executeQuery();

        // Títulos personalizados en español
        String[] titulos = {"Código Compra", "Fecha de Compra", "Valor Total de la Compra"};
        return construirModeloDesdeResultSet(rs, titulos);
    }

    public double sumarTotalVentas(String fechaInicio, String fechaFin) throws SQLException {
        String sql = "SELECT SUM(TOTAL_SALE_VALUE) FROM SALES WHERE DATE_SALE BETWEEN ? AND ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setString(1, fechaInicio);
        ps.setString(2, fechaFin);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getDouble(1);
        }
        return 0.0;
    }

    public double sumarTotalCompras(String fechaInicio, String fechaFin) throws SQLException {
        String sql = "SELECT SUM(TOTAL_PURCHASE_VALUE) FROM PURCHASES WHERE DATE_PURCHASE BETWEEN ? AND ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setString(1, fechaInicio);
        ps.setString(2, fechaFin);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getDouble(1);
        }
        return 0.0;
    }

    private DefaultTableModel construirModeloDesdeResultSet(ResultSet rs, String[] titulos) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        Vector<String> columnas = new Vector<>();
        for (String titulo : titulos) {
            columnas.add(titulo);
        }

        Vector<Vector<Object>> datos = new Vector<>();
        while (rs.next()) {
            Vector<Object> fila = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                fila.add(rs.getObject(i));
            }
            datos.add(fila);
        }

        return new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}
