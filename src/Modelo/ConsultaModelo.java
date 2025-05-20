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
        String sql = "SELECT s.COD_SALE, s.COD_USER, s.DATE_SALE, s.COSTUMER_IDENTIFICATION,\n" +
                "           d.COD_PRODUCT, d.PRODUCT_NAME, d.PRODUCT_QUANTITY_Kg, d.PRODUCT_PRICE,\n" +
                "           d.TOTAL_PRODUCT, s.TOTAL_SALE_VALUE\n" +
                "    FROM SALES s\n" +
                "    JOIN SALES_DETAILS d ON s.COD_SALE = d.COD_SALE WHERE DATE_SALE BETWEEN ? AND ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setString(1, fechaInicio);
        ps.setString(2, fechaFin);
        ResultSet rs = ps.executeQuery();

        return construirModeloDesdeResultSet(rs);
    }

    public DefaultTableModel consultarComprasPorFecha(String fechaInicio, String fechaFin) throws SQLException {
        String sql = "SELECT p.COD_PURCHASE, p.COD_USER, p.COD_SUPPLIER, p.DATE_PURCHASE, p.TOTAL_PURCHASE_VALUE, \" +\n" +
                "                \"pd.PURCHASED_PRODUCT, pd.UNIT_VALUE, pd.QUANTITY_Kg, pd.TOTAL_PRODUCT \" +\n" +
                "                \"FROM PURCHASES p JOIN PURCHASES_DETAILS pd ON p.COD_PURCHASE = pd.COD_PURCHASE WHERE DATE_PURCHASE BETWEEN ? AND ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setString(1, fechaInicio);
        ps.setString(2, fechaFin);
        ResultSet rs = ps.executeQuery();

        return construirModeloDesdeResultSet(rs);
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
        String sql = "SELECT SUM(TOTAL_PURHCASE_VALUE) FROM PURCHASE WHERE DATE_PURCHASE BETWEEN ? AND ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setString(1, fechaInicio);
        ps.setString(2, fechaFin);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getDouble(1);
        }
        return 0.0;
    }

    private DefaultTableModel construirModeloDesdeResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnas = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnas.add(metaData.getColumnName(column));
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
