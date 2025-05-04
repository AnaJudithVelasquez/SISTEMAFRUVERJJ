package Modelo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class FruverModelo {

    private static Connection conexion;

    public static Connection conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/FruverAguacates", "root", "10j56yeyo");
            return conexion;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
