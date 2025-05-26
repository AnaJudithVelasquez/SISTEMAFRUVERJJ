package Controlador;

import Modelo.ConsultaModelo;
import Vista.ConsultaView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ConsultaControlador {
    private ConsultaView vista;
    private ConsultaModelo modelo;

    public ConsultaControlador(ConsultaView vista) {
        this.vista = vista;

        Connection conexion = obtenerConexion();
        if (conexion != null) {
            modelo = new ConsultaModelo(conexion);

            vista.btnConsultarVentas.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    consultarVentas();
                }
            });

            vista.btnConsultarCompras.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    consultarCompras();
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
        }

        vista.btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
                Vista.FruverView.mostrarVentanaFruver();
            }
        });
    }

    private Connection obtenerConexion() {
        try {
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/FruverAguacates", "root", "10j56yeyo");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void consultarVentas() {
        try {
            String inicio = vista.getFechaInicio();
            String fin = vista.getFechaFin();
            vista.tablaResultados.setModel(modelo.consultarVentasPorFecha(inicio, fin));

            double total = modelo.sumarTotalVentas(inicio, fin);
            vista.setTotal(String.format("$%.2f", total));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al consultar ventas: " + e.getMessage());
        }
    }

    private void consultarCompras() {
        try {
            String inicio = vista.getFechaInicio();
            String fin = vista.getFechaFin();
            vista.tablaResultados.setModel(modelo.consultarComprasPorFecha(inicio, fin));

            double total = modelo.sumarTotalCompras(inicio, fin);
            vista.setTotal(String.format("$%.2f", total));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al consultar compras: " + e.getMessage());
        }
    }
}
