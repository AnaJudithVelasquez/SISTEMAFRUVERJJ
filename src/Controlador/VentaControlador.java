package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;

import Vista.FruverView;
import Vista.ProductoView;
import com.itextpdf.text.*;
import java.util.ArrayList;
import com.itextpdf.text.pdf.PdfWriter;
import Modelo.VentaModelo.ProductoDetalle;


import Modelo.VentaModelo;
import Vista.VentaView;



public class VentaControlador {
    private VentaModelo modelo;
    private VentaView vista;
    private List<ProductoDetalle> productosVenta = new ArrayList<>();
    private ProductoView productoVista;

    public VentaControlador(ProductoView productoVista, VentaView vista, VentaModelo modelo) {
        this.productoVista = productoVista;
        this.vista = vista;
        this.modelo = modelo;


        // Inicializar la vista con datos
        cargarDatosVentas();
        mostrarDatos();
        // Configurar los listeners

        this.productoVista.configurarTablaProductos();

        this.vista.getMostrarProductosButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDatos();
            }
        });

        this.vista.setAgregarProductoButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
                actualizarTotalVenta();
            }
        });

        // Listener para el botón de agregar venta
        this.vista.setAgregarVentaButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarVenta();
            }
        });

        // Listener para el botón de mostrar datos
        this.vista.setMostrarButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosVentas();
            }
        });

        // Listener para el botón de volver


        // Listener para cambios en campos de cantidad o precio
        this.vista.Cantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calcularTotalPorProducto();
            }
        });

        this.vista.Precio_Producto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calcularTotalPorProducto();
            }
        });



    }

    private void cargarDatosVentas() {
        List<Object[]> datosVentas = modelo.obtenerDatosVentas();
        vista.actualizarTablaVentas(datosVentas);

    }

    private void mostrarDatos() {
        vista.actualizarTablaProductos(modelo.listarProductos());
    }

    private void agregarProducto() {
        String codProducto = vista.getCodProducto();
        String nombreProducto = vista.getNombreProducto();
        String cantidad = vista.getCantidad();
        String precioProducto = vista.getPrecioProducto();
        String totalProducto = vista.getTotalPorProducto().replace(",", ".");

        if (codProducto.isEmpty() || nombreProducto.isEmpty() || cantidad.isEmpty() ||
                precioProducto.isEmpty() || totalProducto.isEmpty()) {
            vista.mostrarError("Debe completar todos los campos del producto");
            return;
        }

        // Crear el objeto ProductoDetalle y agregarlo a la lista
        ProductoDetalle producto = new ProductoDetalle(codProducto, nombreProducto, cantidad, precioProducto, totalProducto);
        productosVenta.add(producto);

        vista.mostrarMensaje("Producto agregado a la venta.");
        vista.limpiarCamposProducto();
    }

    private void agregarVenta() {
        String codUsuario = vista.getCodUsuario();
        String fechaVenta = vista.getFechaVenta();
        String identificacionCliente = vista.getIdentificacionCliente();
        String totalVenta = vista.getTotalVenta();

        if (codUsuario.isEmpty() || fechaVenta.isEmpty() || totalVenta.isEmpty()) {
            vista.mostrarError("Debe completar los campos obligatorios de la venta");
            return;
        }

        if (productosVenta.isEmpty()) {
            vista.mostrarError("Debe agregar al menos un producto a la venta");
            return;
        }

        int codVentaGenerado = modelo.agregarVenta(codUsuario, fechaVenta, identificacionCliente, totalVenta, productosVenta);

        if (codVentaGenerado > 0) {
            vista.setCodVenta(String.valueOf(codVentaGenerado));
            vista.mostrarMensaje("La venta y los productos se insertaron correctamente.");
            cargarDatosVentas();
            agregarProducto();
        } else {
            vista.mostrarError("Error al registrar la venta");
        }
    }

    private void calcularTotalPorProducto() {
        try {
            String cantidadText = vista.getCantidad();
            String precioText = vista.getPrecioProducto();

            if (!cantidadText.isEmpty() && !precioText.isEmpty()) {
                double cantidad = Double.parseDouble(cantidadText);
                double precio = Double.parseDouble(precioText);
                double totalProducto = modelo.calcularTotalPorProducto(cantidad, precio);
                vista.setTotalPorProducto(String.format("%.2f", totalProducto));
            }
        } catch (NumberFormatException e) {
            // No hacer nada si hay error en la conversión
        }
    }

    private void actualizarTotalVenta() {
        double totalVenta = modelo.calcularTotalVenta(productosVenta);
        vista.setTotalVenta(String.format("%.2f", totalVenta));
        System.out.println("Total de venta actualizado: " + totalVenta);
    }

    public static void regresar() {
        FruverView enlace = new FruverView();
        enlace.mostrarVentanaFruver();
    }


}
