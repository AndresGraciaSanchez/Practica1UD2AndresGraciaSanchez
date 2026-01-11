package gui;

import util.Util;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class Controlador implements ActionListener, ItemListener, ListSelectionListener, WindowListener {

    private Modelo modelo;
    private Vista vista;
    boolean refrescar;

    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;
        modelo.conectar();
        setOptions();
        addActionListeners(this);
        addItemListeners(this);
        addWindowListeners(this);
        refrescarTodo();
        iniciar();
        activarBusquedasAutomaticas();
    }

    private void refrescarTodo() {
        refrescarSoftware();
        refrescarDesarrollador();
        refrescarCategoria();
        refrescarLicencia();
        refrescar = false;
    }

    private void addActionListeners(ActionListener listener) {
        vista.anadirSoftwareButton.addActionListener(listener);
        vista.anadirSoftwareButton.setActionCommand("anadirSoftware");
        vista.modificarSoftwareButton.addActionListener(listener);
        vista.modificarSoftwareButton.setActionCommand("modificarSoftware");
        vista.eliminarSoftwareButton.addActionListener(listener);
        vista.eliminarSoftwareButton.setActionCommand("eliminarSoftware");

        vista.anadirDesarrolladorButton.addActionListener(listener);
        vista.anadirDesarrolladorButton.setActionCommand("anadirDesarrollador");
        vista.modificarDesarrolladorButton.addActionListener(listener);
        vista.modificarDesarrolladorButton.setActionCommand("modificarDesarrollador");
        vista.eliminarDesarrolladorButton.addActionListener(listener);
        vista.eliminarDesarrolladorButton.setActionCommand("eliminarDesarrollador");

        vista.anadirCategoriaButton.addActionListener(listener);
        vista.anadirCategoriaButton.setActionCommand("anadirCategoria");
        vista.modificarCategoriaButton.addActionListener(listener);
        vista.modificarCategoriaButton.setActionCommand("modificarCategoria");
        vista.eliminarCategoriaButton.addActionListener(listener);
        vista.eliminarCategoriaButton.setActionCommand("eliminarCategoria");

        vista.anadirLicenciaButton.addActionListener(listener);
        vista.anadirLicenciaButton.setActionCommand("anadirLicencia");
        vista.modificarLicenciaButton.addActionListener(listener);
        vista.modificarLicenciaButton.setActionCommand("modificarLicencia");
        vista.eliminarLicenciaButton.addActionListener(listener);
        vista.eliminarLicenciaButton.setActionCommand("eliminarLicencia");

        vista.itemOpciones.addActionListener(listener);
        vista.itemSalir.addActionListener(listener);
        vista.itemDesconectar.addActionListener(listener);
        vista.itemConectar.addActionListener(listener);
        vista.btnValidate.addActionListener(listener);
        vista.optionDialog.btnOpcionesGuardar.addActionListener(listener);
    }

    private void addWindowListeners(WindowListener listener) {
        vista.addWindowListener(listener);
    }

    void iniciar() {
        vista.tablaSoftware.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionSoftware = vista.tablaSoftware.getSelectionModel();
        cellSelectionSoftware.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionSoftware.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tablaSoftware.getSelectionModel())) {
                        int row = vista.tablaSoftware.getSelectedRow();
                        vista.textNombreSoftware.setText(String.valueOf(vista.tablaSoftware.getValueAt(row, 1)));
                        vista.textVersion.setText(String.valueOf(vista.tablaSoftware.getValueAt(row, 2)));
                        vista.textPrecio.setText(String.valueOf(vista.tablaSoftware.getValueAt(row, 3)));
                        vista.dateLanzamiento.setDate(Date.valueOf(String.valueOf(vista.tablaSoftware.getValueAt(row, 4))).toLocalDate());
                        vista.checkBoxActivo.setSelected((Boolean) vista.tablaSoftware.getValueAt(row, 5));
                        vista.comboDesarrollador.setSelectedItem(String.valueOf(vista.tablaSoftware.getValueAt(row, 6)));
                        vista.comboCategoria.setSelectedItem(String.valueOf(vista.tablaSoftware.getValueAt(row, 7)));
                        vista.comboLicencia.setSelectedItem(String.valueOf(vista.tablaSoftware.getValueAt(row, 8)));
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.tablaSoftware.getSelectionModel())) {
                            borrarCamposSoftware();
                        } else if (e.getSource().equals(vista.tablaDesarrollador.getSelectionModel())) {
                            borrarCamposDesarrollador();
                        } else if (e.getSource().equals(vista.tablaCategoria.getSelectionModel())) {
                            borrarCamposCategoria();
                        } else if (e.getSource().equals(vista.tablaLicencia.getSelectionModel())) {
                            borrarCamposLicencia();
                        }
                    }
                }
            }
        });

        vista.tablaDesarrollador.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionDesarrollador = vista.tablaDesarrollador.getSelectionModel();
        cellSelectionDesarrollador.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionDesarrollador.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tablaDesarrollador.getSelectionModel())) {
                        int row = vista.tablaDesarrollador.getSelectedRow();
                        vista.textNombreDesarrollador.setText(String.valueOf(vista.tablaDesarrollador.getValueAt(row, 1)));
                        vista.textEmailDesarrollador.setText(String.valueOf(vista.tablaDesarrollador.getValueAt(row, 2)));
                        vista.textPaisDesarrollador.setText(String.valueOf(vista.tablaDesarrollador.getValueAt(row, 3)));
                        vista.dateRegistroDesarrollador.setDate((Date.valueOf(String.valueOf(vista.tablaDesarrollador.getValueAt(row, 4)))).toLocalDate());
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.tablaSoftware.getSelectionModel())) {
                            borrarCamposSoftware();
                        } else if (e.getSource().equals(vista.tablaDesarrollador.getSelectionModel())) {
                            borrarCamposDesarrollador();
                        } else if (e.getSource().equals(vista.tablaCategoria.getSelectionModel())) {
                            borrarCamposCategoria();
                        } else if (e.getSource().equals(vista.tablaLicencia.getSelectionModel())) {
                            borrarCamposLicencia();
                        }
                    }
                }
            }
        });

        vista.tablaCategoria.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionCategoria = vista.tablaCategoria.getSelectionModel();
        cellSelectionCategoria.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionCategoria.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tablaCategoria.getSelectionModel())) {
                        int row = vista.tablaCategoria.getSelectedRow();
                        vista.textNombreCategoria.setText(String.valueOf(vista.tablaCategoria.getValueAt(row, 1)));
                        vista.textDescripcionCategoria.setText(String.valueOf(vista.tablaCategoria.getValueAt(row, 2)));
                        vista.comboNivelCategoria.setSelectedItem(String.valueOf(vista.tablaCategoria.getValueAt(row, 3)));
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.tablaSoftware.getSelectionModel())) {
                            borrarCamposSoftware();
                        } else if (e.getSource().equals(vista.tablaDesarrollador.getSelectionModel())) {
                            borrarCamposDesarrollador();
                        } else if (e.getSource().equals(vista.tablaCategoria.getSelectionModel())) {
                            borrarCamposCategoria();
                        } else if (e.getSource().equals(vista.tablaLicencia.getSelectionModel())) {
                            borrarCamposLicencia();
                        }
                    }
                }
            }
        });

        vista.tablaLicencia.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionLicencia = vista.tablaLicencia.getSelectionModel();
        cellSelectionLicencia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionLicencia.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tablaLicencia.getSelectionModel())) {
                        int row = vista.tablaLicencia.getSelectedRow();
                        vista.textNombreLicencia.setText(String.valueOf(vista.tablaLicencia.getValueAt(row, 1)));
                        vista.comboTipoLicencia.setSelectedItem(String.valueOf(vista.tablaLicencia.getValueAt(row, 2)));
                        vista.textUrlLicencia.setText(String.valueOf(vista.tablaLicencia.getValueAt(row, 3)));
                        vista.textCosteLicencia.setText(String.valueOf(vista.tablaLicencia.getValueAt(row, 4)));
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.tablaSoftware.getSelectionModel())) {
                            borrarCamposSoftware();
                        } else if (e.getSource().equals(vista.tablaDesarrollador.getSelectionModel())) {
                            borrarCamposDesarrollador();
                        } else if (e.getSource().equals(vista.tablaCategoria.getSelectionModel())) {
                            borrarCamposCategoria();
                        } else if (e.getSource().equals(vista.tablaLicencia.getSelectionModel())) {
                            borrarCamposLicencia();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Opciones":
                vista.adminPasswordDialog.setVisible(true);
                break;
            case "Desconectar":
                if (modelo.desconectar()) {
                    JOptionPane.showMessageDialog(
                            vista,
                            "Te has desconectado correctamente de la base de datos",
                            "Desconexión",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            vista,
                            "No había conexión activa",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                break;
            case "Conectar":
                try {
                    modelo.conectar();
                    JOptionPane.showMessageDialog(
                            vista,
                            "Conectado correctamente a la base de datos",
                            "Conexión",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    refrescarTodo();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            vista,
                            "Error al conectar",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                break;
            case "Salir":
                System.exit(0);
                break;
            case "abrirOpciones":
                if (String.valueOf(vista.adminPassword.getPassword()).equals(modelo.getAdminPassword())) {
                    vista.adminPassword.setText("");
                    vista.adminPasswordDialog.dispose();
                    vista.optionDialog.setVisible(true);
                } else {
                    Util.showErrorAlert("La contraseña introducida no es correcta.");
                }
                break;
            case "guardarOpciones":
                modelo.setPropValues(
                        vista.optionDialog.txtIP.getText(),
                        vista.optionDialog.txtUsuario.getText(),
                        String.valueOf(vista.optionDialog.pfPass.getPassword()),
                        String.valueOf(vista.optionDialog.pfAdmin.getPassword())
                );
                vista.optionDialog.dispose();
                vista.dispose();
                new Controlador(new Modelo(), new Vista());
                break;

            case "anadirSoftware": {
                try {
                    if (comprobarSoftwareVacio()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.tablaSoftware.clearSelection();
                    } else if (modelo.softwareNombreYaExiste(vista.textNombreSoftware.getText())) {
                        Util.showErrorAlert("Ese software ya existe.\nIntroduce un software diferente");
                        vista.tablaSoftware.clearSelection();
                    } else {
                        modelo.insertarSoftware(
                                vista.textNombreSoftware.getText(),
                                vista.textVersion.getText(),
                                Float.parseFloat(vista.textPrecio.getText()),
                                vista.dateLanzamiento.getDate(),
                                vista.checkBoxActivo.isSelected(),
                                String.valueOf(vista.comboDesarrollador.getSelectedItem()),
                                String.valueOf(vista.comboCategoria.getSelectedItem()),
                                String.valueOf(vista.comboLicencia.getSelectedItem())
                        );

                        refrescarSoftware();
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.tablaSoftware.clearSelection();
                }
                borrarCamposSoftware();
            }
            break;
            case "modificarSoftware": {
                try {
                    if (comprobarSoftwareVacio()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.tablaSoftware.clearSelection();
                    } else {
                        modelo.modificarSoftware(
                                vista.textNombreSoftware.getText(),
                                vista.textVersion.getText(),
                                Float.parseFloat(vista.textPrecio.getText()),
                                vista.dateLanzamiento.getDate(),
                                vista.checkBoxActivo.isSelected(),
                                (Integer) vista.tablaSoftware.getValueAt(vista.tablaSoftware.getSelectedRow(), 0)
                        );
                        refrescarSoftware();
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.tablaSoftware.clearSelection();
                }
                borrarCamposSoftware();
            }
            break;
            case "eliminarSoftware": {
                int fila = vista.tablaSoftware.getSelectedRow();

                if (fila == -1) {
                    JOptionPane.showMessageDialog(
                            vista,
                            "Selecciona un software",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE
                    );
                    break;
                }

                int id = (Integer) vista.tablaSoftware.getValueAt(fila, 0);

                modelo.eliminarSoftware(id);
                borrarCamposSoftware();
                refrescarSoftware();
                break;
            }


            case "anadirDesarrollador": {
                try {
                    if (comprobarDesarrolladorVacio()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.tablaDesarrollador.clearSelection();
                    } else if (modelo.desarrolladorNombreYaExiste(vista.textNombreDesarrollador.getText())) {
                        Util.showErrorAlert("Ese desarrollador ya existe.\nIntroduce un desarrollador diferente");
                        vista.tablaDesarrollador.clearSelection();
                    } else {
                        modelo.insertarDesarrollador(
                                vista.textNombreDesarrollador.getText(),
                                vista.textEmailDesarrollador.getText(),
                                vista.textPaisDesarrollador.getText(),
                                vista.dateRegistroDesarrollador.getDate()
                        );
                        refrescarDesarrollador();
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.tablaDesarrollador.clearSelection();
                }
                borrarCamposDesarrollador();
            }
            break;
            case "modificarDesarrollador": {
                try {
                    if (comprobarDesarrolladorVacio()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.tablaDesarrollador.clearSelection();
                    } else {
                        modelo.modificarDesarrollador(
                                vista.textNombreDesarrollador.getText(),
                                vista.textEmailDesarrollador.getText(),
                                vista.textPaisDesarrollador.getText(),
                                vista.dateRegistroDesarrollador.getDate(),
                                (Integer) vista.tablaDesarrollador.getValueAt(vista.tablaDesarrollador.getSelectedRow(), 0)
                        );
                        refrescarDesarrollador();
                        refrescarSoftware();
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.tablaDesarrollador.clearSelection();
                }
                borrarCamposDesarrollador();
            }
            break;
            case "eliminarDesarrollador": {
                int fila = vista.tablaDesarrollador.getSelectedRow();

                if (fila == -1) {
                    JOptionPane.showMessageDialog(vista, "Selecciona un desarrollador", "Aviso", JOptionPane.WARNING_MESSAGE);
                    break;
                }

                int id = (Integer) vista.tablaDesarrollador.getValueAt(fila, 0);

                try {
                    if (modelo.desarrolladorEnUso(id)) {
                        JOptionPane.showMessageDialog(
                                vista,
                                "No se puede eliminar el desarrollador porque está asociado a un software",
                                "Advertencia",
                                JOptionPane.WARNING_MESSAGE
                        );
                        break;
                    }

                    modelo.eliminarDesarrollador(id);
                    borrarCamposDesarrollador();
                    refrescarDesarrollador();

                } catch (SQLException errr) {
                    errr.printStackTrace();
                }
                break;
            }

            case "anadirCategoria": {
                try {
                    if (comprobarCategoriaVacia()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.tablaCategoria.clearSelection();
                    } else if (modelo.categoriaNombreYaExiste(vista.textNombreCategoria.getText())) {
                        Util.showErrorAlert("Esa categoría ya existe.\nIntroduce una diferente");
                        vista.tablaCategoria.clearSelection();
                    } else {
                        modelo.insertarCategoria(
                                vista.textNombreCategoria.getText(),
                                vista.textDescripcionCategoria.getText(),
                                String.valueOf(vista.comboNivelCategoria.getSelectedItem())
                        );
                        refrescarCategoria();
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.tablaCategoria.clearSelection();
                }
                borrarCamposCategoria();
            }
            break;
            case "modificarCategoria": {
                try {
                    if (comprobarCategoriaVacia()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.tablaCategoria.clearSelection();
                    } else {
                        modelo.modificarCategoria(
                                vista.textNombreCategoria.getText(),
                                vista.textDescripcionCategoria.getText(),
                                String.valueOf(vista.comboNivelCategoria.getSelectedItem()),
                                (Integer) vista.tablaCategoria.getValueAt(vista.tablaCategoria.getSelectedRow(), 0)
                        );
                        refrescarCategoria();
                        refrescarSoftware();
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.tablaCategoria.clearSelection();
                }
                borrarCamposCategoria();
            }
            break;
            case "eliminarCategoria": {
                int fila = vista.tablaCategoria.getSelectedRow();

                if (fila == -1) {
                    JOptionPane.showMessageDialog(vista, "Selecciona una categoría", "Aviso", JOptionPane.WARNING_MESSAGE);
                    break;
                }

                int id = (Integer) vista.tablaCategoria.getValueAt(fila, 0);

                try {
                    if (modelo.categoriaEnUso(id)) {
                        JOptionPane.showMessageDialog(
                                vista,
                                "No se puede eliminar la categoría porque está asociada a un software",
                                "Advertencia",
                                JOptionPane.WARNING_MESSAGE
                        );
                        break;
                    }

                    modelo.eliminarCategoria(id);
                    borrarCamposCategoria();
                    refrescarCategoria();

                } catch (SQLException err) {
                    err.printStackTrace();
                }
                break;
            }

            case "anadirLicencia": {
                try {
                    if (comprobarLicenciaVacia()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.tablaLicencia.clearSelection();
                    } else if (modelo.licenciaNombreYaExiste(vista.textNombreLicencia.getText())) {
                        Util.showErrorAlert("Esa licencia ya existe.\nIntroduce una diferente");
                        vista.tablaLicencia.clearSelection();
                    } else {
                        modelo.insertarLicencia(
                                vista.textNombreLicencia.getText(),
                                String.valueOf(vista.comboTipoLicencia.getSelectedItem()),
                                vista.textUrlLicencia.getText(),
                                Float.parseFloat(vista.textCosteLicencia.getText())
                        );
                        refrescarLicencia();
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.tablaLicencia.clearSelection();
                }
                borrarCamposLicencia();
            }
            break;
            case "modificarLicencia": {
                try {
                    if (comprobarLicenciaVacia()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.tablaLicencia.clearSelection();
                    } else {
                        modelo.modificarLicencia(
                                vista.textNombreLicencia.getText(),
                                String.valueOf(vista.comboTipoLicencia.getSelectedItem()),
                                vista.textUrlLicencia.getText(),
                                Float.parseFloat(vista.textCosteLicencia.getText()),
                                (Integer) vista.tablaLicencia.getValueAt(vista.tablaLicencia.getSelectedRow(), 0)
                        );
                        refrescarLicencia();
                        refrescarSoftware();
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.tablaLicencia.clearSelection();
                }
                borrarCamposLicencia();
            }
            break;
            case "eliminarLicencia": {
                int fila = vista.tablaLicencia.getSelectedRow();

                if (fila == -1) {
                    JOptionPane.showMessageDialog(vista, "Selecciona una licencia", "Aviso", JOptionPane.WARNING_MESSAGE);
                    break;
                }

                int id = (Integer) vista.tablaLicencia.getValueAt(fila, 0);

                try {
                    if (modelo.licenciaEnUso(id)) {
                        JOptionPane.showMessageDialog(
                                vista,
                                "No se puede eliminar la licencia porque está asociada a un software",
                                "Advertencia",
                                JOptionPane.WARNING_MESSAGE
                        );
                        break;
                    }

                    modelo.eliminarLicencia(id);
                    borrarCamposLicencia();
                    refrescarLicencia();

                } catch (SQLException er) {
                    er.printStackTrace();
                }
                break;
            }

        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    private void refrescarSoftware() {
        try {
            vista.tablaSoftware.setModel(construirTableModelSoftware(modelo.consultarSoftware()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refrescarDesarrollador() {
        try {
            vista.tablaDesarrollador.setModel(construirTableModelDesarrollador(modelo.consultarDesarrollador()));
            vista.comboDesarrollador.removeAllItems();
            for (int i = 0; i < vista.dtmDesarrolladores.getRowCount(); i++) {
                vista.comboDesarrollador.addItem(vista.dtmDesarrolladores.getValueAt(i, 0) + " - " +
                        vista.dtmDesarrolladores.getValueAt(i, 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refrescarCategoria() {
        try {
            vista.tablaCategoria.setModel(construirTableModelCategoria(modelo.consultarCategoria()));
            vista.comboCategoria.removeAllItems();
            for (int i = 0; i < vista.dtmCategorias.getRowCount(); i++) {
                vista.comboCategoria.addItem(vista.dtmCategorias.getValueAt(i, 0) + " - " +
                        vista.dtmCategorias.getValueAt(i, 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refrescarLicencia() {
        try {
            vista.tablaLicencia.setModel(construirTableModelLicencia(modelo.consultarLicencia()));
            vista.comboLicencia.removeAllItems();
            for (int i = 0; i < vista.dtmLicencias.getRowCount(); i++) {
                vista.comboLicencia.addItem(vista.dtmLicencias.getValueAt(i, 0) + " - " +
                        vista.dtmLicencias.getValueAt(i, 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DefaultTableModel construirTableModelSoftware(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, columnCount, data);

        vista.dtmSoftware.setDataVector(data, columnNames);

        return vista.dtmSoftware;
    }

    private DefaultTableModel construirTableModelDesarrollador(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, columnCount, data);

        vista.dtmDesarrolladores.setDataVector(data, columnNames);

        return vista.dtmDesarrolladores;
    }

    private DefaultTableModel construirTableModelCategoria(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, columnCount, data);

        vista.dtmCategorias.setDataVector(data, columnNames);

        return vista.dtmCategorias;
    }

    private DefaultTableModel construirTableModelLicencia(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, columnCount, data);

        vista.dtmLicencias.setDataVector(data, columnNames);

        return vista.dtmLicencias;
    }

    private void setDataVector(ResultSet rs, int columnCount, Vector<Vector<Object>> data) throws SQLException {
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
    }

    private void setOptions() {
        vista.optionDialog.txtIP.setText(modelo.getIp());
        vista.optionDialog.txtUsuario.setText(modelo.getUser());
        vista.optionDialog.pfPass.setText(modelo.getPassword());
        vista.optionDialog.pfAdmin.setText(modelo.getAdminPassword());
    }

    private void borrarCamposSoftware() {
        vista.textNombreSoftware.setText("");
        vista.textVersion.setText("");
        vista.textPrecio.setText("");
        vista.comboDesarrollador.setSelectedIndex(-1);
        vista.comboCategoria.setSelectedIndex(-1);
        vista.comboLicencia.setSelectedIndex(-1);
        vista.dateLanzamiento.setDate(null);
        vista.checkBoxActivo.setSelected(false);
    }

    private void borrarCamposDesarrollador() {
        vista.textNombreDesarrollador.setText("");
        vista.textEmailDesarrollador.setText("");
        vista.textPaisDesarrollador.setText("");
        vista.dateRegistroDesarrollador.setDate(null);
    }

    private void borrarCamposCategoria() {
        vista.textNombreCategoria.setText("");
        vista.textDescripcionCategoria.setText("");
        vista.comboNivelCategoria.setSelectedIndex(-1);
    }

    private void borrarCamposLicencia() {
        vista.textNombreLicencia.setText("");
        vista.textUrlLicencia.setText("");
        vista.textCosteLicencia.setText("");
        vista.comboTipoLicencia.setSelectedIndex(-1);
    }

    private boolean comprobarSoftwareVacio() {
        return vista.textNombreSoftware.getText().isEmpty() ||
                vista.textVersion.getText().isEmpty() ||
                vista.textPrecio.getText().isEmpty() ||
                vista.comboDesarrollador.getSelectedIndex() == -1 ||
                vista.comboCategoria.getSelectedIndex() == -1 ||
                vista.comboLicencia.getSelectedIndex() == -1 ||
                vista.dateLanzamiento.getDate() == null;
    }

    private boolean comprobarDesarrolladorVacio() {
        return vista.textNombreDesarrollador.getText().isEmpty() ||
                vista.textEmailDesarrollador.getText().isEmpty() ||
                vista.textPaisDesarrollador.getText().isEmpty() ||
                vista.dateRegistroDesarrollador.getDate() == null;
    }

    private boolean comprobarCategoriaVacia() {
        return vista.textNombreCategoria.getText().isEmpty() ||
                vista.textDescripcionCategoria.getText().isEmpty() ||
                vista.comboNivelCategoria.getSelectedIndex() == -1;
    }

    private boolean comprobarLicenciaVacia() {
        return vista.textNombreLicencia.getText().isEmpty() ||
                vista.textUrlLicencia.getText().isEmpty() ||
                vista.textCosteLicencia.getText().isEmpty() ||
                vista.comboTipoLicencia.getSelectedIndex() == -1;
    }

    private void activarBusquedasAutomaticas() {

        vista.textBuscarSoftware.getDocument().addDocumentListener(new DocumentListener() {
            private void filtrar() {
                try {
                    String texto = vista.textBuscarSoftware.getText().trim();
                    if (texto.isEmpty()) {
                        refrescarSoftware();
                    } else {
                        vista.tablaSoftware.setModel(
                                construirTableModelSoftware(
                                        modelo.buscarSoftwarePorNombre(texto)
                                )
                        );
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }

            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            public void changedUpdate(DocumentEvent e) {
            }
        });

        vista.textBuscarDesarrollador.getDocument().addDocumentListener(new DocumentListener() {
            private void filtrar() {
                try {
                    String texto = vista.textBuscarDesarrollador.getText().trim();
                    if (texto.isEmpty()) {
                        refrescarDesarrollador();
                    } else {
                        vista.tablaDesarrollador.setModel(
                                construirTableModelDesarrollador(
                                        modelo.buscarDesarrolladorPorNombre(texto)
                                )
                        );
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }

            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            public void changedUpdate(DocumentEvent e) {
            }
        });

        vista.textBuscarCategoria.getDocument().addDocumentListener(new DocumentListener() {
            private void filtrar() {
                try {
                    String texto = vista.textBuscarCategoria.getText().trim();
                    if (texto.isEmpty()) {
                        refrescarCategoria();
                    } else {
                        vista.tablaCategoria.setModel(
                                construirTableModelCategoria(
                                        modelo.buscarCategoriaPorNombre(texto)
                                )
                        );
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }

            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            public void changedUpdate(DocumentEvent e) {
            }
        });

        vista.textBuscarLicencia.getDocument().addDocumentListener(new DocumentListener() {
            private void filtrar() {
                try {
                    String texto = vista.textBuscarLicencia.getText().trim();
                    if (texto.isEmpty()) {
                        refrescarLicencia();
                    } else {
                        vista.tablaLicencia.setModel(
                                construirTableModelLicencia(
                                        modelo.buscarLicenciaPorNombre(texto)
                                )
                        );
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }

            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            public void changedUpdate(DocumentEvent e) {
            }
        });
    }


    private void addItemListeners(Controlador controlador) {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}