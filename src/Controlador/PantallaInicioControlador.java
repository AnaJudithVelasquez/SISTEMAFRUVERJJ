package Controlador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modelo.UsuarioModel;
import Vista.PantallaInicioView;
import Vista.VentaView;
import Vista.FruverView;


public class PantallaInicioControlador {
    private UsuarioModel modelo;
    private PantallaInicioView vista;

    public PantallaInicioControlador(UsuarioModel modelo, PantallaInicioView vista) {
        this.modelo = modelo;
        this.vista = vista;

        this.vista.getBotonIngresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarUsuario();
            }
        });
    }


    private void validarUsuario() {
        String usuario = vista.getUsuario();
        String password = vista.getPassword();

        int posicion = modelo.validarUsuario(usuario, password);

        switch (posicion) {
            case 1:
                vista.cerrar();
                FruverView menuview = new FruverView();
                menuview.setVisible(true);
                break;
            case 2:
                vista.cerrar();
                VentaView ventaView = new VentaView();
                ventaView.setVisible(true);
                break;
            case -1:
                // Las credenciales ya se notificaron como incorrectas en el modelo
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
