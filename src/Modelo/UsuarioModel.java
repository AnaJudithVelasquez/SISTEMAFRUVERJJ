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

    // Método para validar los datos del usuario metodo almacenado
    public int validarUsuario(String nombreUsuario, String password) {
        conectar();
        int posicion = -1; // Valor por defecto si no se encuentra el usuario

        try {
            String sql = "{CALL validarUsuario(?, ?)}";
            CallableStatement cs = conexion.prepareCall(sql);
            cs.setString(1, nombreUsuario);
            cs.setString(2, password);

            rs = cs.executeQuery();

            if (rs.next()) {
                posicion = rs.getInt("POSITION");  // Asegúrate de que el campo se llame así en el resultado del SP
                JOptionPane.showMessageDialog(null, "Las credenciales del usuario son correctas");
            } else {
                JOptionPane.showMessageDialog(null, "Las credenciales no son correctas");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cerrarConexion();
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


