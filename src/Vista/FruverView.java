package Vista;

import javax.swing.*;

import Controlador.PantallaInicioControlador;
import Modelo.FruverModelo;
import Controlador.FruverControlador;
import Modelo.UsuarioModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FruverView extends JFrame {
    private JPanel panelOpciones;
    private JButton comprasButton;
    private JButton productosButton;
    private JButton ventasButton;
    private JButton consultarComprasYVentasButton;
    private JButton cerrarSesionButton;

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
        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerramos esta ventana de forma segura
                JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(cerrarSesionButton);
                if (ventanaActual != null) {
                    ventanaActual.dispose();  // Esto cierra correctamente la ventana de FruverView
                }

                // Lanzamos la pantalla de login nuevamente
                SwingUtilities.invokeLater(() -> {
                    UsuarioModel modelo = new UsuarioModel();
                    PantallaInicioView vista = new PantallaInicioView();
                    PantallaInicioControlador controlador = new PantallaInicioControlador(modelo, vista);
                    controlador.iniciar();
                });
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

