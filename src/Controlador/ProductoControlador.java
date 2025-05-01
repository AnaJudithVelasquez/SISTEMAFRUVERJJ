package Controlador;

import Modelo.ProductoModelo;
import Vista.ProductoView;

import javax.swing.*;
import java.awt.event.*;

public class ProductoControlador {
    private ProductoModelo modelo;
    private ProductoView vista;

    public ProductoControlador(ProductoModelo modelo, ProductoView vista) {
        this.modelo = modelo;
        this.vista = vista;

        iniciar();
    }

    private void iniciar() {
        vista.agregarButton.addActionListener(e -> agregarProducto());
        vista.modificarButton.addActionListener(e -> modificarProducto());
        vista.eliminarButton.addActionListener(e -> eliminarProducto());
        vista.mostrarButton.addActionListener(e -> mostrarProductos());
        vista.listaComprasButton.addActionListener(e -> mostrarCompras());
        vista.volverButton.addActionListener(e -> volver());

        vista.table1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = vista.table1.getSelectedRow();
                if (row != -1) llenarFormularioProducto(row);
            }
        });

        vista.table2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = vista.table2.getSelectedRow();
                if (row != -1) llenarFormularioCompra(row);
            }
        });
    }

    private void agregarProducto() {
        try {
            modelo.agregarProducto(
                    vista.codCompra.getText(), vista.nombre.getText(), vista.stock.getText(),
                    vista.cantidad.getText(), vista.cantidadFinal.getText(),
                    vista.precioCompra.getText(), vista.precioProducto.getText(), vista.descripcion.getText()
            );
            JOptionPane.showMessageDialog(vista, "Producto agregado correctamente.");
            mostrarProductos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al agregar producto: " + ex.getMessage());
        } finally {
            modelo.cerrar();
        }
    }

    private void modificarProducto() {
        try {
            modelo.modificarProducto(
                    vista.producto1.getText(), vista.codCompra.getText(), vista.nombre.getText(),
                    vista.stock.getText(), vista.cantidad.getText(),
                    vista.precioCompra.getText(), vista.precioProducto.getText(), vista.descripcion.getText()
            );
            JOptionPane.showMessageDialog(vista, "Producto modificado correctamente.");
            mostrarProductos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al modificar producto: " + ex.getMessage());
        } finally {
            modelo.cerrar();
        }
    }

    private void eliminarProducto() {
        try {
            modelo.eliminarProducto(vista.producto1.getText());
            JOptionPane.showMessageDialog(vista, "Producto eliminado correctamente.");
            mostrarProductos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar producto: " + ex.getMessage());
        } finally {
            modelo.cerrar();
        }
    }

    private void mostrarProductos() {
        try {
            modelo.mostrarProductos((javax.swing.table.DefaultTableModel) vista.table1.getModel());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al mostrar productos: " + ex.getMessage());
        } finally {
            modelo.cerrar();
        }
    }

    private void mostrarCompras() {
        try {
            modelo.mostrarCompras((javax.swing.table.DefaultTableModel) vista.table2.getModel());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al mostrar compras: " + ex.getMessage());
        } finally {
            modelo.cerrar();
        }
    }

    private void llenarFormularioProducto(int row) {
        vista.producto1.setText(vista.table1.getValueAt(row, 0).toString());
        vista.codCompra.setText(vista.table1.getValueAt(row, 1).toString());
        vista.nombre.setText(vista.table1.getValueAt(row, 2).toString());
        vista.stock.setText(vista.table1.getValueAt(row, 3).toString());
        vista.cantidad.setText(vista.table1.getValueAt(row, 4).toString());
        vista.cantidadFinal.setText(vista.table1.getValueAt(row, 5).toString());
        vista.precioCompra.setText(vista.table1.getValueAt(row, 6).toString());
        vista.precioProducto.setText(vista.table1.getValueAt(row, 7).toString());
        vista.descripcion.setText(vista.table1.getValueAt(row, 8).toString());
    }

    private void llenarFormularioCompra(int row) {
        vista.codCompra.setText(vista.table2.getValueAt(row, 0).toString());
        vista.nombre.setText(vista.table2.getValueAt(row, 4).toString());
        vista.precioCompra.setText(vista.table2.getValueAt(row, 6).toString());
    }

    private void volver() {
        vista.dispose();
        JOptionPane.showMessageDialog(null, "Volviendo al menú principal...");
    }


}
