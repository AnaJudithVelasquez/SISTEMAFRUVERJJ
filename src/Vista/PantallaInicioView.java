package Vista;

import javax.swing.*;

public class PantallaInicioView extends JFrame {
    JPanel panelFruver;
    JTextField USER1;
    JPasswordField PASS1;
    JButton INGRESAR;
    JLabel USER;
    JLabel PASS;
    private JTextField textField1;
    private JTextField textField2;
    private JButton button1;

    public PantallaInicioView() {
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panelFruver = new JPanel();
        USER = new JLabel("Usuario:");
        PASS = new JLabel("Contraseña:");
        USER1 = new JTextField(15);
        PASS1 = new JPasswordField(15);
        INGRESAR = new JButton("Ingresar");

        panelFruver.add(USER);
        panelFruver.add(USER1);
        panelFruver.add(PASS);
        panelFruver.add(PASS1);
        panelFruver.add(INGRESAR);

        setContentPane(panelFruver);
        pack();
    }

    public JButton getBotonIngresar() {
        return INGRESAR;
    }

    public String getUsuario() {
        return USER1.getText();
    }

    public String getContraseña() {
        return new String(PASS1.getPassword());
    }
}


