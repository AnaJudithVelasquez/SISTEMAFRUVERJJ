package Modelo;

import java.sql.*;
import javax.swing.*;

public class UsuarioModel {
    private Connection conexion;
    private Statement st;
    private ResultSet rs;

    public void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/FruverAguacates", "root", "10j56yeyo");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validarUsuario(String NAME_U, String PASS_W) {
        try {
            st = conexion.createStatement();
            rs = st.executeQuery("SELECT * FROM USERS WHERE NAME_U = '" + NAME_U + "' AND PASS_W = '" + PASS_W + "'");

            return rs.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }

    public int obtenerPosicion() {
        try {
            return rs.getInt("POSITION");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener la posición: " + e.getMessage());
            return 0;
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public Statement getSt() {
        return st;
    }

    public ResultSet getRs() {
        return rs;
    }
}

