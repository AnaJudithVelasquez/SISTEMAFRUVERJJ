package Controlador;

import Modelo.ProductoModelo;
import Vista.ProductoView;
import Vista.FruverView;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductoControlador {
    private ProductoView vista;
    private ProductoModelo modelo;

    public ProductoControlador(ProductoView vista, ProductoModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;

        // Inicializar la vista
        mostrarDatos();
        mostrarDatosCompra();

        // Configurar los listeners de la vista
        this.vista.configurarTablaProductos();
        this.vista.configurarTablaCompras();

        this.vista.agregarListenerAgregar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregar();
            }
        });

        this.vista.agregarListenerModificar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificar();
            }
        });

        this.vista.agregarListenerEliminar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar();
            }
        });

        this.vista.agregarListenerMostrar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDatos();
            }
        });

        this.vista.agregarListenerVolver(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }

    private void agregar() {
        String codCompra = vista.getCodigoCompra();
        String nombreProducto = vista.getNombreProducto();
        String stockVal = vista.getStock();
        String cantidad = vista.getCantidad();
        String cantidadFinal = vista.getCantidadFinal();
        String precioCompra = vista.getPrecioCompra();
        String precioProducto = vista.getPrecioProducto();
        String descripcion = vista.getDescripcion();

        int codProductoGenerado = modelo.agregar(codCompra, nombreProducto, stockVal, cantidad,
                cantidadFinal, precioCompra, precioProducto, descripcion);
        if (codProductoGenerado != -1) {
            vista.setCodigoProducto(codProductoGenerado);
            mostrarDatos();
        }
    }

    private void modificar() {
        String codProducto = vista.getCodigoProducto();
        String codCompra = vista.getCodigoCompra();
        String nombreProducto = vista.getNombreProducto();
        String stockVal = vista.getStock();
        String cantidad = vista.getCantidad();
        String cantidadFinal = vista.getCantidadFinal();
        String precioCompra = vista.getPrecioCompra();
        String precioProducto = vista.getPrecioProducto();
        String descripcion = vista.getDescripcion();

        boolean exito = modelo.modificar(codProducto, codCompra, nombreProducto, stockVal, cantidad,
                cantidadFinal, precioCompra, precioProducto, descripcion);
        if (exito) {
            mostrarDatos();
        }
    }

    private void eliminar() {
        String codProducto = vista.getCodigoProducto();
        boolean exito = modelo.eliminar(codProducto);
        if (exito) {
            mostrarDatos();
        }
    }

    private void mostrarDatos() {
        vista.actualizarTablaProductos(modelo.listarProductos());
    }

    private void mostrarDatosCompra() {
        vista.actualizarTablaCompras(modelo.listarCompras());
    }

    private void regresar() {
        vista.setVisible(false);
        vista.dispose();
        FruverView enlace = new FruverView();
        enlace.mostrarVentanaFruver();
    }
}
