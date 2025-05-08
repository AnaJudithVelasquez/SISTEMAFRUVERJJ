package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modelo.FruverModelo;
import Vista.FruverView;


public class FruverControlador {
    private FruverModelo modelo;
    private FruverView vista;

    public FruverControlador(FruverModelo modelo, FruverView vista) {
        this.modelo = modelo;
        this.vista = vista;
    }


    // Método para iniciar la aplicación
    public void iniciar() {
        vista.mostrarVentana();
    }

}