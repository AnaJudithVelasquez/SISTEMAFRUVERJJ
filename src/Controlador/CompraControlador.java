package Controlador;

import Modelo.CompraDetalle;
import Modelo.CompraModelo;
import Vista.CompraView;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CompraControlador {
    private CompraView vista;
    private CompraModelo modelo;
    private List<CompraDetalle> productosCompra;

    public CompraControlador(CompraView vista, CompraModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.productosCompra = new ArrayList<>();

        inicializar();
        agregarEventos();
    }

    private void inicializar() {
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        vista.Fecha_Compra.setText(fechaActual.format(formato));

        vista.Cod_ProveedorC.setEditable(false);
        vista.Total_Producto.setEditable(false);
        vista.Total_Compra.setEditable(false);
        vista.Fecha_Compra.setEditable(false);

        mostrarProveedores();
        mostrarCompras();
    }

    private void agregarEventos() {
        vista.agregarButton.addActionListener(e -> agregarProveedor());
        vista.modificarButton.addActionListener(e -> modificarProveedor());
        vista.eliminarButton.addActionListener(e -> eliminarProveedor());
        vista.mostrarButton.addActionListener(e -> mostrarProveedores());
        vista.agregarProductoButton.addActionListener(e -> {
            agregarProducto();
            actualizarTotalCompra();
        });
        vista.agregarCompraButton.addActionListener(e -> agregarCompra());
        vista.mostrarComprasButton.addActionListener(e -> mostrarCompras());
        vista.volverButton.addActionListener(e -> volver());

        vista.table1P.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && vista.table1P.getSelectedRow() != -1) {
                vista.Cod_ProveedorC.setText(vista.table1P.getValueAt(vista.table1P.getSelectedRow(), 0).toString());
            }
        });

        KeyAdapter recalcularTotal = new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                calcularTotalPorProducto();
            }
        };
        vista.Cantidad_Producto_Kg.addKeyListener(recalcularTotal);
        vista.Valor_Unitario.addKeyListener(recalcularTotal);
    }

    private void agregarProveedor() {
        try {
            int cod = modelo.agregarProveedor(
                    vista.Nombre_Proveedor.getText(),
                    vista.Direccion.getText(),
                    vista.Telefono.getText()
            );
            vista.Cod_Proveedor.setText(String.valueOf(cod));
            JOptionPane.showMessageDialog(vista, "Proveedor agregado.");
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void modificarProveedor() {
        try {
            if (modelo.modificarProveedor(
                    Integer.parseInt(vista.Cod_Proveedor.getText()),
                    vista.Nombre_Proveedor.getText(),
                    vista.Direccion.getText(),
                    vista.Telefono.getText()
            )) {
                JOptionPane.showMessageDialog(vista, "Proveedor modificado.");
            }
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void eliminarProveedor() {
        try {
            if (modelo.eliminarProveedor(Integer.parseInt(vista.Cod_Proveedor.getText()))) {
                JOptionPane.showMessageDialog(vista, "Proveedor eliminado.");
            }
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void mostrarProveedores() {
        try {
            List<String[]> proveedores = modelo.obtenerProveedores();
            DefaultTableModel model = (DefaultTableModel) vista.table1P.getModel();
            model.setRowCount(0);
            if (model.getColumnCount() == 0) {
                model.addColumn("Cod_Proveedor");
                model.addColumn("Nombre");
                model.addColumn("Direccion");
                model.addColumn("Telefono");
            }
            for (String[] p : proveedores) {
                model.addRow(p);
            }
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void agregarProducto() {
        CompraDetalle producto = new CompraDetalle(
                vista.Producto_Comprado.getText(),
                vista.Valor_Unitario.getText(),
                vista.Cantidad_Producto_Kg.getText(),
                vista.Total_Producto.getText()
        );
        productosCompra.add(producto);
        JOptionPane.showMessageDialog(vista, "Producto agregado.");
    }

    private void agregarCompra() {
        try {
            int codCompra = modelo.agregarCompra(
                    vista.Cod_Usuario.getText(),
                    vista.Cod_ProveedorC.getText(),
                    vista.Fecha_Compra.getText(),
                    vista.Total_Compra.getText()
            );
            modelo.agregarDetalleCompra(codCompra, productosCompra);
            modelo.actualizarInventario(productosCompra);

            productosCompra.clear();
            JOptionPane.showMessageDialog(vista, "Compra realizada.");
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void calcularTotalPorProducto() {
        try {
            double cantidad = Double.parseDouble(vista.Cantidad_Producto_Kg.getText());
            double precio = Double.parseDouble(vista.Valor_Unitario.getText());
            double total = cantidad * precio;
            vista.Total_Producto.setText(String.format("%.2f", total));
        } catch (NumberFormatException ignored) {}
    }

    private void actualizarTotalCompra() {
        double totalCompra = productosCompra.stream()
                .mapToDouble(p -> Double.parseDouble(p.getTotalProducto().replace(",", ".")))
                .sum();
        vista.Total_Compra.setText(String.format("%.2f", totalCompra));
    }

    private void mostrarCompras() {
        try {
            List<String[]> compras = modelo.obtenerCompras();
            DefaultTableModel model = (DefaultTableModel) vista.table1.getModel();
            model.setRowCount(0);
            if (model.getColumnCount() == 0) {
                model.addColumn("Cod_Compra");
                model.addColumn("Cod_Usuario");
                model.addColumn("Cod_Proveedor");
                model.addColumn("Fecha");
                model.addColumn("Producto");
                model.addColumn("Valor Unitario");
                model.addColumn("Cantidad");
                model.addColumn("Total Producto");
                model.addColumn("Total Compra");
            }
            for (String[] c : compras) {
                model.addRow(c);
            }
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void volver() {
        vista.dispose();
        JOptionPane.showMessageDialog(null, "Volviendo al menú principal...");
    }

    private void mostrarError(Exception e) {
        JOptionPane.showMessageDialog(vista, "Error: " + e.getMessage());
        e.printStackTrace();
    }
}

