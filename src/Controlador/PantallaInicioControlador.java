package Controlador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modelo.UsuarioModel;
import Vista.PantallaInicioView;



public class PantallaInicioControlador {
    private UsuarioModel modelo;
    private PantallaInicioView vista;

    public PantallaInicioControlador(UsuarioModel modelo, PantallaInicioView vista) {
        this.modelo = modelo;
        this.vista = vista;

        // Agregar ActionListener al botón INGRESAR
        this.vista.getBotonIngresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarUsuario();
            }
        });
    }

    // Método para validar el usuario y redirigir según su posición
    private void validarUsuario() {
        String usuario = vista.getUsuario();
        String password = vista.getPassword();

        int posicion = modelo.validarUsuario(usuario, password);

        switch (posicion) {
            case 1:
                vista.mostrarFruver();
                vista.cerrar();
                break;
            case 2:
                vista.mostrarVentas();
                vista.cerrar();
                break;
            case -1:
                // Las credenciales ya se notificaron como incorrectas en el modelo
                break;
            default:
                JOptionPane.showMessageDialog(null, "El rol del usuario no está definido correctamente.");
                break;
        }
    }

    // Método para iniciar la aplicación
    public void iniciar() {
        vista.mostrarVentana();
    }

    // Método main que inicia la aplicación
    public static void main(String[] args) {
        UsuarioModel modelo = new UsuarioModel();
        PantallaInicioView vista = new PantallaInicioView();
        PantallaInicioControlador controlador = new PantallaInicioControlador(modelo, vista);
        controlador.iniciar();
    }
}
