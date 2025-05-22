package Vista;

import javax.swing.*;
import Modelo.FruverModelo;
import Controlador.FruverControlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FruverView extends JFrame {
    private JPanel panelOpciones;
    private JButton comprasButton;
    private JButton productosButton;
    private JButton ventasButton;
    private JButton consultarComprasYVentasButton;

    public FruverView() {
        setContentPane(panelOpciones);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        comprasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               FruverModelo.abrirCompras();
               dispose();
            }
        });


        productosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FruverModelo.abrirProductos();
                dispose();
            }
        });
        ventasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FruverModelo.abrirVentas();
                dispose();
            }
        });
        consultarComprasYVentasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConsultaView.mostrarVentanaConsulta();
                dispose();
            }
        });
    }

    // Getters para que el controlador pueda acceder a los componentes
    public JButton getProductosButton() {
        return productosButton;
    }

    public JButton getVentasButton() {
        return ventasButton;
    }

    public JButton getComprasButton() {
        return comprasButton;
    }

    // Método para mostrar la ventana
    public void mostrarVentana() {
        setVisible(true);
        pack();
    }


    // Método para cerrar la ventana
    public void cerrarVentana() {
        dispose();
    }

    public static void mostrarVentanaFruver() {
        FruverView vista = new FruverView();
        FruverModelo modelo = new FruverModelo();
        FruverControlador controlador = new FruverControlador(modelo, vista);
        controlador.iniciar();
    }

}

