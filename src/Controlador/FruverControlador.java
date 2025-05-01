package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modelo.ConexionModel;
import Modelo.VentaModelo;
import Vista.FruverView;
import Modelo.ProductoModelo;
import Modelo.CompraModelo;

public class FruverControlador {
    private FruverView vista;
    private ConexionModel modelo;

    public FruverControlador(FruverView vista, ConexionModel modelo) {
        this.vista = vista;
        this.modelo = modelo;

        this.vista.getProductosButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirProductos();
            }
        });

        this.vista.getVentasButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentas();
            }
        });

        this.vista.getComprasButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCompras();
            }
        });
    }

    private void abrirProductos() {
        modelo.conectar();
        ProductoModelo productos = new ProductoModelo();
        productos.mostrarVentanaProductos();
        vista.dispose();
    }

    private void abrirVentas() {
        modelo.conectar();
        VentaModelo ventas = new VentaModelo();
        ventas.mostrarVentanaVentas();
        vista.dispose();
    }

    private void abrirCompras() {
        modelo.conectar();
        CompraModelo compras = new CompraModelo();
        compras.mostrarVentanaCompras();
        vista.dispose();
    }

    public static void main(String[] args) {
        FruverView vista = new FruverView();
        ConexionModel modelo = new ConexionModel();
        new FruverControlador(vista, modelo);

        vista.setVisible(true);
    }
}

