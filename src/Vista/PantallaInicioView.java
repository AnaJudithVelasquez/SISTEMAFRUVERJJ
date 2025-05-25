package Vista;

import javax.swing.*;
import java.awt.*;

public class PantallaInicioView extends JFrame {
    private JPanel panelFruver;
    private JLabel PASS;
    private JTextField USER1;
    private JLabel USER;
    private JPasswordField PASS1;
    private JButton INGRESAR;
    private JLabel jLabel1;

    public PantallaInicioView() {
        setContentPane(panelFruver);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
        setLocationRelativeTo(null); // Centrar ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // Cierra solo esta ventana
    }

    // Obtener el texto del campo de usuario
    public String getUsuario() {
        return USER1.getText();
    }

    // Obtener la contraseña
    public String getPassword() {
        return String.valueOf(PASS1.getPassword());
    }

    // Obtener el botón de ingreso
    public JButton getBotonIngresar() {
        return INGRESAR;
    }

    // Obtener el campo de contraseña (para usar Enter)
    public JPasswordField getCampoPassword() {
        return PASS1;
    }

    // Cierra la ventana
    public void cerrar() {
        dispose();
    }

    // Mostrar la ventana
    public void mostrarVentana() {
        setVisible(true);// Ajustar tamaño a los componentes
    }
}
