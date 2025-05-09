package Controlador;

import Modelo.CompraModelo;
import Vista.CompraView;
import Vista.FruverView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CompraControlador {
    private CompraModelo modelo;
    private CompraView vista;

    public CompraControlador(CompraModelo modelo, CompraView vista) {
        this.modelo = modelo;
        this.vista = vista;

        // Agregar listeners a los botones
        this.vista.consultarTotalListener(new ConsultarTotalListener());
        this.vista.agregarProveedorListener(new AgregarProveedorListener());
        this.vista.modificarProveedorListener(new ModificarProveedorListener());
        this.vista.eliminarProveedorListener(new EliminarProveedorListener());
        this.vista.agregarProductoListener(new AgregarProductoListener());
        this.vista.agregarCompraListener(new AgregarCompraListener());
        this.vista.volverListener(new VolverListener());
        this.vista.calcularTotalProductoListener(new CalcularTotalProductoKeyListener());

        // Mostrar datos iniciales
        mostrarDatosProveedores();
        mostrarDatosCompras();
    }

    // Listener para agregar proveedor
    class AgregarProveedorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nombre = vista.getNombreProveedor();
            String direccion = vista.getDireccion();
            String telefono = vista.getTelefono();

            int codProveedor = modelo.agregarProveedor(nombre, direccion, telefono);

            if (codProveedor != -1) {
                vista.setCodProveedor(String.valueOf(codProveedor));
                vista.mostrarMensaje("Los datos se insertaron correctamente.");
                mostrarDatosProveedores();
            } else {
                vista.mostrarError("Hay un error al insertar datos.");
            }
        }
    }

    // Listener para modificar proveedor
    class ModificarProveedorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String codProveedor = vista.getCodProveedor();
            String nombre = vista.getNombreProveedor();
            String direccion = vista.getDireccion();
            String telefono = vista.getTelefono();

            boolean exito = modelo.modificarProveedor(codProveedor, nombre, direccion, telefono);

            if (exito) {
                vista.mostrarMensaje("Los datos se actualizaron correctamente.");
                mostrarDatosProveedores();
            } else {
                vista.mostrarError("No se encuentra el proveedor con el código que especificastes.");
            }
        }
    }

    // Listener para eliminar proveedor
    class EliminarProveedorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String codProveedor = vista.getCodProveedor();

            boolean exito = modelo.eliminarProveedor(codProveedor);

            if (exito) {
                vista.mostrarMensaje("El proveedor se eliminó correctamente.");
                mostrarDatosProveedores();
            } else {
                vista.mostrarError("No se encontró el proveedor con el código que especificastes.");
            }
        }
    }

    // Listener para mostrar proveedores


    // Listener para agregar producto a la compra
    class AgregarProductoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String codCompra = vista.getCodCompra();
            String productoComprado = vista.getProductoComprado();
            String valorUnitario = vista.getValorUnitario();
            String cantidadProductoKg = vista.getCantidadProductoKg();
            String totalProducto = vista.getTotalProducto();

            CompraModelo.CompraDetalle producto = modelo.new CompraDetalle(
                    codCompra, productoComprado, valorUnitario, cantidadProductoKg, totalProducto);

            modelo.agregarProductoACompra(producto);
            vista.mostrarMensaje("Producto agregado a la compra.");

            actualizarTotalCompra();
        }
    }

    // Listener para agregar compra completa
    class AgregarCompraListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String codUsuario = vista.getCodUsuario();
            String codProveedor = vista.getCodProveedorC();
            String fechaCompra = vista.getFechaCompra();
            String totalCompra = vista.getTotalCompra();

            int codCompraGenerado = modelo.agregarCompra(
                    codUsuario, codProveedor, fechaCompra, totalCompra, modelo.getProductosCompra());

            if (codCompraGenerado != -1) {
                vista.setCodCompra(String.valueOf(codCompraGenerado));
                vista.mostrarMensaje("Compra registrada correctamente y cantidades actualizadas.");
                modelo.limpiarProductosCompra();
                mostrarDatosCompras();
            } else {
                vista.mostrarError("Error al registrar la compra.");
            }
        }
    }

    // Listener para mostrar compras

    // Listener para volver a la ventana principal
    class VolverListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            vista.setVisible(false);
            FruverView enlace = new FruverView();
            enlace.mostrarVentanaFruver();
            vista.dispose();
        }
    }

    // KeyListener para calcular total por producto
    class CalcularTotalProductoKeyListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            try {
                double cantidad = Double.parseDouble(vista.getCantidadProductoKg());
                double precio = Double.parseDouble(vista.getValorUnitario());
                double totalProducto = modelo.calcularTotalProducto(cantidad, precio);
                vista.setTotalProducto(String.format("%.2f", totalProducto));
            } catch (NumberFormatException ex) {
                // Ignorar si no se pueden convertir los valores
            }
        }
    }

    // Métodos para actualizar la interfaz
    private void mostrarDatosProveedores() {
        ResultSet rs = modelo.obtenerProveedores();
        vista.mostrarDatosProveedores(rs);
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarDatosCompras() {
        ResultSet rs = modelo.obtenerCompras();
        vista.mostrarDatosCompras(rs);
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualizarTotalCompra() {
        double totalCompra = modelo.calcularTotalCompra(modelo.getProductosCompra());
        vista.setTotalCompra(String.format("%.2f", totalCompra));
    }

    class ConsultarTotalListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fechaInicio = vista.getFechaInicio();
            String fechaFin = vista.getFechaFin();

            try {
                double total = modelo.obtenerTotalComprasPorPeriodo(fechaInicio, fechaFin);
                vista.mostrarMensaje("Total de compras entre " + fechaInicio + " y " + fechaFin + ": $" + String.format("%.2f", total));
            } catch (Exception ex) {
                vista.mostrarError("Error al consultar total de compras: " + ex.getMessage());
            }
        }
    }
}
