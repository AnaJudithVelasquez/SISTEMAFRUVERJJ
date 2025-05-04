package Controlador;

import Modelo.CompraModelo;
import Vista.CompraView;
import Vista.FruverView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CompraControlador {
    private CompraModelo modelo;
    private CompraView vista;

    public CompraControlador(CompraModelo modelo, CompraView vista) {
        this.modelo = modelo;
        this.vista = vista;

        // Inicializar la vista con datos
        cargarDatosProveedores();
        cargarDatosCompras();

        // Añadir listeners a los componentes de la vista
        añadirEventListeners();
    }

    private void añadirEventListeners() {
        // Botones de proveedores
        vista.addAgregarButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProveedor();
            }
        });

        vista.addModificarButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarProveedor();
            }
        });

        vista.addEliminarButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProveedor();
            }
        });

        vista.addMostrarButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosProveedores();
            }
        });

        // Botones de compras
        vista.addAgregarProductoButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarArticulo();
                actualizarTotalCompra();
            }
        });

        vista.addAgregarCompraButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCompra();
            }
        });

        vista.addMostrarComprasButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosCompras();
            }
        });

        vista.addVolverButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });

        // KeyListeners para cálculos
        vista.addCantidadKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calcularTotalPorProducto();
            }
        });

        vista.addValorUnitarioKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calcularTotalPorProducto();
            }
        });

        // ListSelectionListener para la tabla de proveedores
        vista.addTableSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && vista.getFilaSeleccionadaProveedores() != -1) {
                    vista.setCodProveedorC(vista.getValorTablaProveedores(vista.getFilaSeleccionadaProveedores(), 0).toString());
                }
            }
        });
    }

    // Métodos de acción para proveedores
    private void agregarProveedor() {
        String nombre = vista.getNombreProveedor();
        String direccion = vista.getDireccion();
        String telefono = vista.getTelefono();

        int codProveedorGenerado = modelo.agregarProveedor(nombre, direccion, telefono);

        if (codProveedorGenerado != -1) {
            vista.setCodProveedor(String.valueOf(codProveedorGenerado));
            vista.mostrarMensaje("Los datos se insertaron correctamente.");
            cargarDatosProveedores();
        } else {
            vista.mostrarError("Hay un error al insertar datos.");
        }
    }

    private void modificarProveedor() {
        String codProveedor = vista.getCodProveedor();
        String nombre = vista.getNombreProveedor();
        String direccion = vista.getDireccion();
        String telefono = vista.getTelefono();

        boolean exito = modelo.modificarProveedor(codProveedor, nombre, direccion, telefono);

        if (exito) {
            vista.mostrarMensaje("Los datos se actualizaron correctamente.");
            cargarDatosProveedores();
        } else {
            vista.mostrarError("No se encuentra el proveedor con el código que especificaste.");
        }
    }

    private void eliminarProveedor() {
        String codProveedor = vista.getCodProveedor();

        boolean exito = modelo.eliminarProveedor(codProveedor);

        if (exito) {
            vista.mostrarMensaje("El proveedor se eliminó correctamente.");
            cargarDatosProveedores();
        } else {
            vista.mostrarError("No se encontró el proveedor con el código que especificaste.");
        }
    }

    // Métodos de acción para compras
    private void agregarArticulo() {
        String codCompra = vista.getCodCompra();
        String producto = vista.getProductoComprado();
        String valorUnitario = vista.getValorUnitario();
        String cantidad = vista.getCantidadProductoKg();
        String totalProducto = vista.getTotalProducto();

        modelo.agregarArticulo(codCompra, producto, valorUnitario, cantidad, totalProducto);
        vista.mostrarMensaje("Producto agregado a la compra.");
    }

    private void calcularTotalPorProducto() {
        try {
            double cantidad = Double.parseDouble(vista.getCantidadProductoKg());
            double precio = Double.parseDouble(vista.getValorUnitario());
            double totalProducto = modelo.calcularTotalPorProducto(cantidad, precio);
            vista.setTotalProducto(String.format("%.2f", totalProducto));
        } catch (NumberFormatException ex) {
            // Ignorar si los campos están vacíos o tienen formato incorrecto
        }
    }

    private void actualizarTotalCompra() {
        double totalCompra = modelo.calcularTotalCompra();
        vista.setTotalCompra(String.format("%.2f", totalCompra));
        System.out.println("Total de compra actualizado: " + totalCompra);
    }

    private void agregarCompra() {
        String codUsuario = vista.getCodUsuario();
        String codProveedor = vista.getCodProveedorC();
        String fechaCompra = vista.getFechaCompra();
        String totalCompra = vista.getTotalCompra().replace(",", ".");

        int codCompraGenerado = modelo.agregarCompra(codUsuario, codProveedor, fechaCompra, totalCompra);

        if (codCompraGenerado != -1) {
            vista.setCodCompra(String.valueOf(codCompraGenerado));
            vista.mostrarMensaje("Compra registrada correctamente y cantidades actualizadas.");
            cargarDatosCompras();
        } else {
            vista.mostrarError("Error al registrar la compra.");
        }
    }

    // Métodos para cargar datos en las tablas
    private void cargarDatosProveedores() {
        Object[][] datos = modelo.obtenerProveedores();
        vista.actualizarTablaProveedores(datos);
    }

    private void cargarDatosCompras() {
        Object[][] datos = modelo.obtenerCompras();
        vista.actualizarTablaCompras(datos);
    }

    // Método para regresar a la ventana principal
    private void regresar() {
        vista.setVisible(false);
        vista.dispose();
        // Aquí se llamaría a la ventana principal del sistema
        FruverView enlace = new FruverView();
        enlace.mostrarVentanaFruver();
    }


}

