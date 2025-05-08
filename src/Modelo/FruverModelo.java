package Modelo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Vista.VentaView;
import Vista.ProductoView;
import Vista.CompraView;


public class FruverModelo {
    private Connection conexion;

    // Método para establecer la conexión con la base de datos
    public void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/FruverAguacates", "root", "10j56yeyo");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void abrirProductos() {

        ProductoView productoView = new ProductoView();
        productoView.mostrarVentanaProductos();
    }

    // Método para navegar a la ventana de ventas
    public static void abrirVentas() {

        VentaView ventaView = new VentaView();
        ventaView.mostrarVentanaVentas();
    }

    // Método para navegar a la ventana de compras
    public static void abrirCompras() {

        CompraView compraView = new CompraView();
        compraView.mostrarVentanaCompras();
    }

    // Método para navegar a la ventana de productos


    // Método para cerrar la conexión
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
