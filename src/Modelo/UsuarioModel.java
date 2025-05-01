package Modelo;

import java.sql.*;

public class UsuarioModel {
    private Connection conexion;
    private Statement st;
    private ResultSet rs;

    public UsuarioModel() {
        conectar();
    }

    private void conectar() {
        try {
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/FruverAguacates", "root", "10j56yeyo"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int validarUsuario(String usuario, String contraseña) throws SQLException {
        st = conexion.createStatement();
        rs = st.executeQuery(
                "SELECT * FROM USERS WHERE NAME_U = '" + usuario + "' AND PASS_W = '" + contraseña + "'"
        );

        if (rs.next()) {
            return rs.getInt("POSITION");
        } else {
            return -1; // Usuario no encontrado
        }
    }
}

