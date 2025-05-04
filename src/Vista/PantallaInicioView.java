package Vista;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PantallaInicioView extends JFrame {
    private JPanel panelInicio;
    private JLabel PASS;
    private JTextField USER1;
    private JLabel USER;
    private JPasswordField PASS1;
    private JButton INGRESAR;

    public PantallaInicioView() {
        setSize(600, 600);
        setLocationRelativeTo(null);
    }

    public void mostrarVentana() {
        setContentPane(panelInicio);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        pack();
    }

    public void addIngresarListener(ActionListener listener) {
        INGRESAR.addActionListener(listener);
    }

    public String getUsuario() {
        return USER1.getText();
    }

    public String getPassword() {
        return String.valueOf(PASS1.getText());
    }

    public JPanel getPanelFruver() {
        return panelInicio;
    }

    public JTextField getUSER1() {
        return USER1;
    }

    public JPasswordField getPASS1() {
        return PASS1;
    }

    public JButton getINGRESAR() {
        return INGRESAR;
    }
}


