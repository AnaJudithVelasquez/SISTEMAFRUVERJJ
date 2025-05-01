package Controlador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import Modelo.ConexionModel;
import Modelo.UsuarioModel;
import Vista.FruverView;
import Vista.PantallaInicioView;
import Vista.VentaView;
import Modelo.VentaModelo;

public class PantallaInicioControlador {
    private UsuarioModel modelo;
    private PantallaInicioView vista;

    public PantallaInicioControlador(PantallaInicioView vista, UsuarioModel modelo) {
        this.vista = vista;
        this.modelo = modelo;

        this.vista.getBotonIngresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarUsuario();
            }
        });
    }

    private void validarUsuario() {
        String usuario = vista.getUsuario();
        String contraseña = vista.getContraseña();


        try {
            int posicion = modelo.validarUsuario(usuario, contraseña);

            if (posicion == 1) {
                JOptionPane.showMessageDialog(null, "Usuario Administrador Correcto");

                FruverView vistaFruver = new FruverView();
                ConexionModel modeloConexion = new ConexionModel();
                FruverControlador enlace = new FruverControlador(vistaFruver, modeloConexion);
                vistaFruver.setVisible(true);

                vista.dispose();

            } else if (posicion == 2) {
                JOptionPane.showMessageDialog(null, "Usuario Vendedor Correcto");

                VentaView vistaVenta = new VentaView();
                VentaModelo  modeloVenta  = new VentaModelo();
                VentaControlador enlace = new VentaControlador(vistaVenta, modeloVenta);
                vistaVenta.setVisible(true);

                vista.dispose();


        } else {
                JOptionPane.showMessageDialog(null, "Credenciales Incorrectas");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        PantallaInicioView vista = new PantallaInicioView();
        UsuarioModel modelo = new UsuarioModel();
        new PantallaInicioControlador(vista, modelo);

        vista.setVisible(true);
    }
}


