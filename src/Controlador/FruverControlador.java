package Controlador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;



import Vista.FruverView;
import Modelo.FruverModelo;
import Vista.ProductoView;
import Vista.CompraView;
import Vista.VentaView;

public class FruverControlador {
    private FruverView vista;
    private FruverModelo modelo;

    public FruverControlador(FruverView vista, FruverModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;

        inicializarControlador();
    }

    private void inicializarControlador() {
        // Configurar los listeners de los botones
        vista.getProductosButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                llamarBotonProducto();
            }
        });

        vista.getVentasButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                llamarBotonVenta();
            }
        });

        vista.getComprasButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                llamarBotonCompra();
            }
        });
    }

    void llamarBotonCompra() {
        modelo.conectar();
        CompraView enlace = new CompraView();
        enlace.mostrarVentanaCompras();
        vista.dispose();
    }

    void llamarBotonProducto() {
        modelo.conectar();
        ProductoView enlace = new ProductoView();
        enlace.mostrarVentanaProductos();
        vista.dispose();
    }

    void llamarBotonVenta() {
        modelo.conectar();
        VentaView enlace = new VentaView();
        enlace.mostrarVentanaVentas();
        vista.dispose();
    }

}

