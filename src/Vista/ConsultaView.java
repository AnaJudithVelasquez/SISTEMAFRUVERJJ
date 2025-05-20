package Vista;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.text.SimpleDateFormat;
import Controlador.ConsultaControlador;

public class ConsultaView extends JFrame {
    public JButton btnConsultarVentas;
    public JButton btnConsultarCompras;
    public JTable tablaResultados;
    public JDateChooser fechaInicioChooser;
    public JDateChooser fechaFinChooser;

    public ConsultaView() {
        setTitle("Consulta de Ventas y Compras");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior para filtros
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Fecha Inicio:"));
        fechaInicioChooser = new JDateChooser();
        panelSuperior.add(fechaInicioChooser);

        panelSuperior.add(new JLabel("Fecha Fin:"));
        fechaFinChooser = new JDateChooser();
        panelSuperior.add(fechaFinChooser);

        btnConsultarVentas = new JButton("Consultar Ventas");
        btnConsultarCompras = new JButton("Consultar Compras");
        panelSuperior.add(btnConsultarVentas);
        panelSuperior.add(btnConsultarCompras);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla en el centro
        tablaResultados = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public String getFechaInicio() {
        return new SimpleDateFormat("yyyy-MM-dd").format(fechaInicioChooser.getDate());
    }

    public String getFechaFin() {
        return new SimpleDateFormat("yyyy-MM-dd").format(fechaFinChooser.getDate());
    }

    public static void mostrarVentanaConsulta() {
        ConsultaView ventana = new ConsultaView();
        new ConsultaControlador(ventana); // <--- AÑADE ESTO
        ventana.setVisible(true);
    }
}
