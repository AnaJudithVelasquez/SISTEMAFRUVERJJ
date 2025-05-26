package Controlador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modelo.UsuarioModel;
import Modelo.UsuarioSesion; // <--- Asegúrate de importar esta clase
import Vista.PantallaInicioView;
import Vista.VentaView;
import Vista.FruverView;

public class PantallaInicioControlador {
    private UsuarioModel modelo;
    private PantallaInicioView vista;

    public PantallaInicioControlador(UsuarioModel modelo, PantallaInicioView vista) {
        this.modelo = modelo;
        this.vista = vista;

        // Asociar botón con ActionListener
        this.vista.getCampoPassword().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarUsuario();
            }
        });

        this.vista.getBotonIngresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarUsuario();
            }
        });
    }

    // Método que valida al usuario
    private void validarUsuario() {
        String usuario = vista.getUsuario();
        String password = vista.getPassword();

        int posicion = modelo.validarUsuario(usuario, password);

        if (posicion != -1) {
            // Guarda el rol en la sesión
            UsuarioSesion.setPosicion(posicion);
        }

        switch (posicion) {
            case 1: // Administrador
                vista.cerrar();
                FruverView menuview = new FruverView();
                menuview.setVisible(true);
                break;
            case 2: // Empleado
                vista.cerrar();
                VentaView.mostrarVentanaVentas(); // Usamos el método estático
                break;
            case -1:
                // Ya se notificó el error en el modelo
                break;
            default:
                JOptionPane.showMessageDialog(null, "El rol del usuario no está definido correctamente.");
                break;
        }
    }

    public void iniciar() {
        vista.mostrarVentana();
    }

    public static void main(String[] args) {
        UsuarioModel modelo = new UsuarioModel();
        PantallaInicioView vista = new PantallaInicioView();
        PantallaInicioControlador controlador = new PantallaInicioControlador(modelo, vista);
        controlador.iniciar();
    }
}
