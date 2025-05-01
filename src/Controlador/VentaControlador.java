package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;



import Modelo.VentaModelo;
import Vista.VentaView;
import Modelo.ProductoDetalle;

public class VentaControlador {
    private VentaView vista;
    private VentaModelo modelo;
    private List<ProductoDetalle> productosVenta = new ArrayList<>();

    public VentaControlador(VentaView vista, VentaModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;

        this.vista.btnAgregarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        this.vista.btnAgregarVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarVenta();
            }
        });
    }

    private void agregarProducto() {
        ProductoDetalle producto = new ProductoDetalle(
                vista.txtCodProducto.getText(),
                vista.txtNombreProducto.getText(),
                vista.txtCantidad.getText(),
                vista.txtPrecioProducto.getText(),
                vista.txtTotalProducto.getText()
        );
        productosVenta.add(producto);
        System.out.println("Producto agregado");
    }

    private void agregarVenta() {
        try {
            modelo.agregarVenta(
                    productosVenta,
                    vista.txtCodUsuario.getText(),
                    vista.txtFechaVenta.getText(),
                    vista.txtIdentificacionCliente.getText(),
                    vista.txtTotalVenta.getText()
            );
            JOptionPane.showMessageDialog(null, "Venta agregada correctamente.");
            productosVenta.clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar venta: " + e.getMessage());
        }
    }



}

