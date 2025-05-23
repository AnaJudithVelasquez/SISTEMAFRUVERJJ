package Controlador;

import Modelo.ProductoModelo;
import Vista.ProductoView;
import Vista.FruverView;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
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


        // Configurar los listeners de la vista
        this.vista.configurarTablaProductos();

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
        ajustarAnchoColumnas(vista.getTablaProductos()); // <-- Aquí llamas al método para ajustar columnas
    }


    private void ajustarAnchoColumnas(JTable tabla) {
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Importante: desactiva auto-resize por defecto

        for (int col = 0; col < tabla.getColumnCount(); col++) {
            TableColumn column = tabla.getColumnModel().getColumn(col);
            int ancho = 150; // Ancho mínimo por si no hay datos

            for (int row = 0; row < tabla.getRowCount(); row++) {
                TableCellRenderer renderer = tabla.getCellRenderer(row, col);
                Component comp = tabla.prepareRenderer(renderer, row, col);
                ancho = Math.max(comp.getPreferredSize().width + 10, ancho);
            }

            column.setPreferredWidth(ancho);
        }
    }


    private void regresar() {
        vista.setVisible(false);
        vista.dispose();
        FruverView enlace = new FruverView();
        enlace.mostrarVentanaFruver();
    }

    public void iniciar() {
        vista.mostrarVentana();
    }
}
