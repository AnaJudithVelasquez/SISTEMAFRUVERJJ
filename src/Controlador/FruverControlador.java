package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Vista.FruverView;
import Modelo.FruverModelo;


public class FruverControlador {
    private FruverModelo modelo;
    private FruverView vista;

    public FruverControlador(FruverModelo modelo, FruverView vista) {
        this.modelo = modelo;
        this.vista = vista;

        // Agregar los listeners a los botones
        this.vista.getProductosButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botón Productos presionado");
                modelo.abrirProductos();
                vista.cerrarVentana();
            }
        });

        this.vista.getVentasButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.abrirVentas();
                vista.cerrarVentana(); // Opcional
            }
        });

        this.vista.getComprasButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.abrirCompras();
                vista.cerrarVentana(); // Opcional
            }
        });
    }

    // Método para iniciar la aplicación
    public void iniciar() {
        vista.mostrarVentana();
    }

}
