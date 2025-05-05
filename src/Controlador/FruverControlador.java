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
                llamarBotonProducto();
            }
        });

        this.vista.getVentasButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                llamarBotonVenta();
            }
        });

        this.vista.getComprasButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                llamarBotonCompra();
            }
        });
    }

    // Método para iniciar la aplicación
    public void iniciar() {
        vista.mostrarVentana();
    }

    // Método para manejar la acción del botón de productos
    private void llamarBotonProducto() {
        modelo.conectar();
        modelo.abrirProductos();
        vista.cerrarVentana();
    }

    // Método para manejar la acción del botón de ventas
    private void llamarBotonVenta() {
        modelo.conectar();
        modelo.abrirVentas();
        vista.cerrarVentana();
    }

    // Método para manejar la acción del botón de compras
    private void llamarBotonCompra() {
        modelo.conectar();
        modelo.abrirCompras();
        vista.cerrarVentana();
    }
}
