package Controlador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modelo.UsuarioModel;
import Vista.FruverView;
import Vista.PantallaInicioView;
import Vista.VentaView;


public class PantallaInicioControlador {
    private UsuarioModel modelo;
    private PantallaInicioView vista;

    public PantallaInicioControlador(UsuarioModel modelo, PantallaInicioView vista) {
        this.modelo = modelo;
        this.vista = vista;

        this.vista.addIngresarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarUSER();
                vista.dispose();
            }
        });
    }

    private void validarUSER() {
        modelo.conectar();
        String NAME_U = vista.getUsuario();
        String PASS_W = vista.getPassword();

        if (modelo.validarUsuario(NAME_U, PASS_W)) {
            int POSITION = modelo.obtenerPosicion();
            JOptionPane.showMessageDialog(null, "Las credenciales del usuario son correctas");

            if (POSITION == 1) {
                FruverView enlace = new FruverView();
                enlace.mostrarVentanaFruver();
            } else if (POSITION == 2) {
                VentaView enlaceVentas = new VentaView();
                enlaceVentas.mostrarVentanaVentas();
            } else {
                JOptionPane.showMessageDialog(null, "El rol del usuario no está definido correctamente.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Las credenciales no son correctas");
        }
    }

    public static void main(String[] args) {
        UsuarioModel modelo = new UsuarioModel();
        PantallaInicioView vista = new PantallaInicioView();
        PantallaInicioControlador controlador = new PantallaInicioControlador(modelo, vista);
        vista.mostrarVentana();
    }
}


