package Vista;

import javax.swing.*;

public class PantallaInicioView extends JFrame {
    private JPanel panelFruver;
    private JLabel PASS;
    private JTextField USER1;
    private JLabel USER;
    private JPasswordField PASS1;
    private JButton INGRESAR;

    public PantallaInicioView() {
        setContentPane(panelFruver);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Métodos getter para obtener los valores de los componentes
    public String getUsuario() {
        return USER1.getText();
    }

    public String getPassword() {
        return String.valueOf(PASS1.getText());
    }

    public JButton getBotonIngresar() {
        return INGRESAR;
    }

    public void cerrar() {
        dispose();
    }

    // Método para mostrar la ventana principal
    public void mostrarVentana() {
        setVisible(true);
        pack();
    }

    // Método para mostrar la ventana de Fruver_Aguacates_JJ
    public void mostrarFruver() {
        FruverView enlace = new FruverView();
        enlace.mostrarVentanaFruver();
    }

    // Método para mostrar la ventana de Ventas
    public void mostrarVentas() {
        VentaView enlaceVentas = new VentaView();
        enlaceVentas.mostrarVentanaVentas();
    }

}
