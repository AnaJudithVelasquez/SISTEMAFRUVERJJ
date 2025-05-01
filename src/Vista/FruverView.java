package Vista;

import javax.swing.*;

public class FruverView extends JFrame {
    JPanel panelOpciones;
    JButton productosButton;
    JButton ventasButton;
    JButton comprasButton;
    private JButton button1;
    private JButton button2;
    private JButton button3;

    public FruverView() {
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panelOpciones = new JPanel();
        productosButton = new JButton("Productos");
        ventasButton = new JButton("Ventas");
        comprasButton = new JButton("Compras");

        panelOpciones.add(productosButton);
        panelOpciones.add(ventasButton);
        panelOpciones.add(comprasButton);

        setContentPane(panelOpciones);
        pack();
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


}

