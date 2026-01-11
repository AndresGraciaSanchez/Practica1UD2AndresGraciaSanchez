package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class OptionDialog extends JDialog {
    private JPanel panel1;
    public JTextField txtIP;
    public JTextField txtUsuario;
    public JButton btnOpcionesGuardar;
    public JPasswordField pfPass;
    public JPasswordField pfAdmin;
    private Frame owner;

    public OptionDialog(Frame owner) {
        super(owner, "Opciones", true);
        this.owner = owner;
        initDialog();
        cargarConfiguracion();
    }

    private void cargarConfiguracion() {
        Properties p = new Properties();

        try (FileInputStream fis = new FileInputStream("config.properties")) {
            p.load(fis);
        } catch (IOException e) {
        }

        txtIP.setText(p.getProperty("ip", "localhost"));
        txtUsuario.setText(p.getProperty("user", "root"));
        pfPass.setText(p.getProperty("pass", ""));
        pfAdmin.setText(p.getProperty("admin", ""));
    }


    private void initDialog() {
        this.setContentPane(panel1);
        this.panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setSize(new Dimension(this.getWidth() + 200, this.getHeight()));
        this.setLocationRelativeTo(owner);

        btnOpcionesGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarConfiguracion();
            }
        });
    }


    private void guardarConfiguracion() {
        Properties p = new Properties();

        p.setProperty("ip", txtIP.getText());
        p.setProperty("user", txtUsuario.getText());
        p.setProperty("pass", new String(pfPass.getPassword()));
        p.setProperty("admin", new String(pfAdmin.getPassword()));

        try (FileOutputStream fos = new FileOutputStream("config.properties")) {
            p.store(fos, null);

            JOptionPane.showMessageDialog(
                    this,
                    "Configuración guardada correctamente",
                    "Opciones",
                    JOptionPane.INFORMATION_MESSAGE
            );
            dispose();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar la configuración",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

}
