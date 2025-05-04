package Vista;

import Controlador.FruverControlador;
import Modelo.FruverModelo;

import javax.swing.*;

public class FruverView extends JFrame {
    private JPanel panelOpciones;
    private JButton productosButton;
    private JButton ventasButton;
    private JButton comprasButton;

    public FruverView() {
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void mostrarVentana() {
        setContentPane(panelOpciones);
        setVisible(true);
        pack();
    }

    public JPanel getPanelOpciones() {
        return panelOpciones;
    }

    public JButton getProductosButton() {
        return productosButton;
    }

    public JButton getVentasButton() {
        return ventasButton;
    }

    public JButton getComprasButton() {
        return comprasButton;
    }

    public void setPanelOpciones(JPanel panelOpciones) {
        this.panelOpciones = panelOpciones;
    }

    public static void mostrarVentanaFruver() {
        FruverModelo modelo = new FruverModelo();
        FruverView vista = new FruverView();
        FruverControlador controlador = new FruverControlador(vista, modelo);
        vista.mostrarVentana();
    }
}

