package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionModel {
    private Connection conexion;

    public Connection conectar() {
        try {
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/FruverAguacates", "root", "10j56yeyo"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conexion;
    }
}

