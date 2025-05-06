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
    public Connection conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/FruverAguacates", "root", "10j56yeyo");
            return conexion;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    // Método para navegar a la ventana de productos
    public void abrirProductos() {
        ProductoView enlace = new ProductoView();
        enlace.mostrarVentanaProductos();
    }

    // Método para navegar a la ventana de ventas
    public void abrirVentas() {
        VentaView enlace = new VentaView();
        enlace.mostrarVentanaVentas();
    }

    // Método para navegar a la ventana de compras
    public void abrirCompras() {
        CompraView enlace = new CompraView();
        enlace.mostrarVentanaCompras();
    }

}
