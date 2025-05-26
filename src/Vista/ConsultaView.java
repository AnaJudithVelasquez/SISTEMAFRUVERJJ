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
    public JTextField txtTotal;
    public JButton btnVolver;

    public ConsultaView() {
        setTitle("Consulta de Ventas y Compras");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fuentes
        Font fuenteRegular = new Font("Times New Roman", Font.PLAIN, 16);
        Font fuenteBold = new Font("Times New Roman", Font.BOLD, 16);

        // Fondo general
        getContentPane().setBackground(new Color(109, 149, 99)); // Alice Blue

        // Panel superior general
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(109, 149, 99)); // Light Cyan

        // Panel izquierdo para botón Volver
        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelIzquierdo.setBackground(new Color(186, 229, 177));
        btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(100, 30));
        btnVolver.setBackground(new Color(246, 202, 136)); // Crimson
        btnVolver.setForeground(Color.BLACK); // Texto negro
        btnVolver.setFont(fuenteBold);
        panelIzquierdo.add(btnVolver);
        panelSuperior.add(panelIzquierdo, BorderLayout.WEST);

        // Panel central para filtros y botones
        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelCentro.setBackground(new Color(186, 229, 177));

        JLabel lblInicio = new JLabel("Fecha Inicio:");
        lblInicio.setFont(fuenteRegular);
        lblInicio.setForeground(Color.BLACK);
        panelCentro.add(lblInicio);

        fechaInicioChooser = new JDateChooser();
        fechaInicioChooser.setPreferredSize(new Dimension(190, 25));
        fechaInicioChooser.setFont(fuenteRegular);
        fechaInicioChooser.setForeground(Color.BLACK);
        panelCentro.add(fechaInicioChooser);

        JLabel lblFin = new JLabel("Fecha Fin:");
        lblFin.setFont(fuenteRegular);
        lblFin.setForeground(Color.BLACK);
        panelCentro.add(lblFin);

        fechaFinChooser = new JDateChooser();
        fechaFinChooser.setPreferredSize(new Dimension(190, 25));
        fechaFinChooser.setFont(fuenteRegular);
        fechaFinChooser.setForeground(Color.BLACK);
        panelCentro.add(fechaFinChooser);

        btnConsultarVentas = new JButton("Consultar Ventas");
        btnConsultarVentas.setPreferredSize(new Dimension(160, 30));
        btnConsultarVentas.setBackground(new Color(109, 149, 99)); // Verde
        btnConsultarVentas.setForeground(Color.BLACK);
        btnConsultarVentas.setFont(fuenteBold);
        panelCentro.add(btnConsultarVentas);

        btnConsultarCompras = new JButton("Consultar Compras");
        btnConsultarCompras.setPreferredSize(new Dimension(180, 30));
        btnConsultarCompras.setBackground(new Color(109, 149, 99)); // Azul
        btnConsultarCompras.setForeground(Color.BLACK);
        btnConsultarCompras.setFont(fuenteBold);
        panelCentro.add(btnConsultarCompras);

        JLabel lblTotal = new JLabel("Total:");
        lblTotal.setFont(fuenteRegular);
        lblTotal.setForeground(Color.BLACK);
        panelCentro.add(lblTotal);

        txtTotal = new JTextField(20);
        txtTotal.setEditable(false);
        txtTotal.setFont(fuenteRegular);
        txtTotal.setForeground(Color.BLACK);
        panelCentro.add(txtTotal);

        panelSuperior.add(panelCentro, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);

        // Tabla de resultados en el centro
        tablaResultados = new JTable();
        tablaResultados.setFont(fuenteRegular);
        tablaResultados.setForeground(Color.BLACK);
        tablaResultados.setRowHeight(24);

        tablaResultados.getTableHeader().setFont(fuenteBold);
        tablaResultados.getTableHeader().setForeground(Color.BLACK); // Encabezado negro
        tablaResultados.getTableHeader().setBackground(new Color(236, 161, 158)); // Gris claro

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

    public void setTotal(String total) {
        txtTotal.setText(total);
    }

    public static void mostrarVentanaConsulta() {
        ConsultaView ventana = new ConsultaView();
        new ConsultaControlador(ventana);
        ventana.setVisible(true);
    }
}
