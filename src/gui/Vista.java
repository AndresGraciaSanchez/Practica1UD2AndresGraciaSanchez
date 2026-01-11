package gui;

import com.github.lgooddatepicker.components.DatePicker;
import gui.enumerados.NivelCategoria;
import gui.enumerados.TipoLicencia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Vista extends JFrame {
    private JPanel panelBase;
    private JTabbedPane tabbedPane1;
    private final static String TITULO_FRAME = "Aplicacion de tienda de software y herramientas de desarrollo open-source";

    public JPanel JPanelSoftware;
    public JTextField textNombreSoftware;
    public JTextField textVersion;
    public JTextField textPrecio;
    public JComboBox comboDesarrollador;
    public JComboBox comboCategoria;
    public JComboBox comboLicencia;
    public DatePicker dateLanzamiento;
    public JCheckBox checkBoxActivo;
    public JTable tablaSoftware;
    public JButton anadirSoftwareButton;
    public JButton modificarSoftwareButton;
    public JButton eliminarSoftwareButton;

    public JPanel JPanelDesarrollador;
    public JTextField textNombreDesarrollador;
    public JTextField textEmailDesarrollador;
    public JTextField textPaisDesarrollador;
    public DatePicker dateRegistroDesarrollador;
    public JButton modificarDesarrolladorButton;
    public JButton anadirDesarrolladorButton;
    public JButton eliminarDesarrolladorButton;
    public JTable tablaDesarrollador;

    public JPanel JPanelCategoria;
    public JTextField textNombreCategoria;
    public JTextArea textDescripcionCategoria;
    public JComboBox comboNivelCategoria;
    public JButton eliminarCategoriaButton;
    public JTable tablaCategoria;
    public JButton modificarCategoriaButton;
    public JButton anadirCategoriaButton;

    public JPanel JPanelLicencia;
    public JTextField textNombreLicencia;
    public JTextField textUrlLicencia;
    public JTextField textCosteLicencia;
    public JComboBox comboTipoLicencia;
    public JButton eliminarLicenciaButton;
    public JTable tablaLicencia;
    public JButton modificarLicenciaButton;
    public JButton anadirLicenciaButton;

    public JTextField textBuscarSoftware;
    public JTextField textBuscarDesarrollador;
    public JTextField textBuscarCategoria;
    public JTextField textBuscarLicencia;

    public DefaultTableModel dtmSoftware;
    public DefaultTableModel dtmDesarrolladores;
    public DefaultTableModel dtmCategorias;
    public DefaultTableModel dtmLicencias;

    public JMenuItem itemOpciones;
    public JMenuItem itemDesconectar;
    public JMenuItem itemConectar;
    public JMenuItem itemSalir;

    public OptionDialog optionDialog;
    public JDialog adminPasswordDialog;
    public JButton btnValidate;
    public JPasswordField adminPassword;

    public Vista() {
        super(TITULO_FRAME);
        initFrame();
    }

    public void initFrame() {
        this.setContentPane(panelBase);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setSize(new Dimension(this.getWidth() + 100, this.getHeight()));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        optionDialog = new OptionDialog(this);
        setMenu();
        setAdminDialog();
        setEnumComboBox();
        setTableModels();
    }

    private void setMenu() {
        JMenuBar mbBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        itemOpciones = new JMenuItem("Opciones");
        itemOpciones.setActionCommand("Opciones");
        itemDesconectar = new JMenuItem("Desconectar");
        itemDesconectar.setActionCommand("Desconectar");
        itemConectar = new JMenuItem("Conectar");
        itemConectar.setActionCommand("Conectar");
        itemSalir = new JMenuItem("Salir");
        itemSalir.setActionCommand("Salir");
        menu.add(itemOpciones);
        menu.add(itemDesconectar);
        menu.add(itemConectar);
        menu.add(itemSalir);
        mbBar.add(menu);
        mbBar.add(Box.createHorizontalGlue());
        this.setJMenuBar(mbBar);
    }

    private void setAdminDialog() {
        btnValidate = new JButton("Validar");
        btnValidate.setActionCommand("abrirOpciones");
        adminPassword = new JPasswordField();
        adminPassword.setPreferredSize(new Dimension(100, 26));
        Object[] options = new Object[]{adminPassword, btnValidate};
        JOptionPane jop = new JOptionPane("Introduce la contraseña", JOptionPane.WARNING_MESSAGE,
                JOptionPane.YES_NO_OPTION, null, options);
        adminPasswordDialog = new JDialog(this, "Opciones", true);
        adminPasswordDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        adminPasswordDialog.setContentPane(jop);
        adminPasswordDialog.pack();
        adminPasswordDialog.setLocationRelativeTo(this);
    }

    private void setEnumComboBox() {

        for (NivelCategoria nivel : NivelCategoria.values()) {
            comboNivelCategoria.addItem(nivel.getValor());
        }
        comboNivelCategoria.setSelectedIndex(-1);

        for (TipoLicencia tipo : TipoLicencia.values()) {
            comboTipoLicencia.addItem(tipo.getValor());
        }
        comboTipoLicencia.setSelectedIndex(-1);
    }

    private void setTableModels() {

        this.dtmSoftware = new DefaultTableModel();
        this.tablaSoftware.setModel(dtmSoftware);

        this.dtmDesarrolladores = new DefaultTableModel();
        this.tablaDesarrollador.setModel(dtmDesarrolladores);

        this.dtmCategorias = new DefaultTableModel();
        this.tablaCategoria.setModel(dtmCategorias);

        this.dtmLicencias = new DefaultTableModel();
        this.tablaLicencia.setModel(dtmLicencias);
    }

}
