package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public VentaControlador(VentaModelo modelo, VentaView vista) {
        this.modelo = modelo;
        this.vista = vista;

        // Inicializar la vista con datos
        cargarDatosVentas();
        cargarDatosProductos();

        // Configurar los listeners
        configurarListeners();
    }

    private void configurarListeners() {
        // Listener para el botón de agregar producto
        vista.setAgregarProductoButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
                actualizarTotalVenta();
            }
        });

        // Listener para el botón de agregar venta
        vista.setAgregarVentaButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarVenta();
                generarFacturaPDF();
            }
        });

        // Listener para el botón de mostrar datos
        vista.setMostrarButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosVentas();
            }
        });

        // Listener para el botón de volver
        vista.setVolverButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAMenuPrincipal();
            }
        });

        // Listener para cambios en campos de cantidad o precio
        vista.Cantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calcularTotalPorProducto();
            }
        });

        vista.Precio_Producto.addKeyListener(new KeyAdapter() {
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

    private void cargarDatosProductos() {
        List<Object[]> datosProductos = modelo.obtenerProductos();
        vista.actualizarTablaProductos(datosProductos);
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
            cargarDatosProductos();
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

    private void volverAMenuPrincipal() {
        vista.dispose();
        // Aquí se puede agregar código para volver al menú principal
        // Por ejemplo: new MenuPrincipalControlador(new MenuPrincipalModelo(), new MenuPrincipalVista());
    }

    public void generarFacturaPDF() {
        try {
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream("Factura_Venta.pdf"));
            documento.open();

            documento.add(new Paragraph("FACTURA DE VENTA FRUVER AGUACATES JJ"));
            documento.add(new Paragraph("Cod_Venta: " + vista.getCodVenta()));
            documento.add(new Paragraph("Fecha_Venta: " + vista.getFechaVenta()));
            documento.add(new Paragraph("Identificación_Cliente: " + vista.getIdentificacionCliente()));
            documento.add(new Paragraph("Total_Venta: " + vista.getTotalVenta()));

            List<ProductoDetalle> copiaProductos = new ArrayList<>(productosVenta);
            for (ProductoDetalle producto : copiaProductos) {
                documento.add(new Paragraph("Cod_Producto: " + producto.getCodProducto()));
                documento.add(new Paragraph("Nombre_Producto: " + producto.getNombreProducto()));
                documento.add(new Paragraph("Cantidad_Producto_Kg: " + producto.getCantidadKg()));
                documento.add(new Paragraph("Precio_Producto: " + producto.getPrecioProducto()));
                documento.add(new Paragraph("Total_Producto: " + producto.getTotalProducto()));
                documento.add(new Paragraph("\n"));
            }
            documento.close();
            vista.mostrarMensaje("Factura generada con éxito y se guardó en Factura_Venta.pdf");

            // Limpiar la lista de productos y los campos después de generar la factura
            productosVenta.clear();
            vista.limpiarCamposVenta();

        } catch (DocumentException | IOException e) {
            vista.mostrarError("Error al generar la factura: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
