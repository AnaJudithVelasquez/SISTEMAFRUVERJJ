package Vista;


import javax.swing.*;
import Modelo.FruverModelo;
import Controlador.FruverControlador;
import java.awt.*;

public class FruverView extends JFrame {
    private JPanel panelOpciones;
    private JButton productosButton;
    private JButton ventasButton;
    private JButton comprasButton;

    public FruverView() {
        setSize(600, 600);
        setLocationRelativeTo(null);
        setContentPane(panelOpciones);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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