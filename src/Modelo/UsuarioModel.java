package Modelo;

import java.sql.*;
import javax.swing.*;

public class UsuarioModel {
    private Connection conexion;
    private Statement st;
    private ResultSet rs;

    // Método para establecer conexión con la base de datos
    public void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/FruverAguacates", "root", "10j56yeyo");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para validar los datos del usuario
    public int validarUsuario(String nombreUsuario, String password) {
        conectar();
        int posicion = -1; // Valor por defecto si no se encuentra el usuario

        try {
            st = conexion.createStatement();
            rs = st.executeQuery("SELECT * FROM USERS WHERE NAME_U = '" + nombreUsuario + "' AND PASS_W = '" + password + "'");

            if (rs.next()) {
                posicion = rs.getInt("POSITION");
                JOptionPane.showMessageDialog(null, "Las credenciales del usuario son correctas");
            } else {
                JOptionPane.showMessageDialog(null, "Las credenciales no son correctas");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

        return posicion;
    }

    // Método para cerrar la conexión a la base de datos
    public void cerrarConexion() {
        try {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (conexion != null) conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage());
        }
    }
}


