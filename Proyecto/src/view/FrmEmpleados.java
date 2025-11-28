package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmEmpleados extends JFrame {

    private JTextField txtIdEmpleado;
    private JTextField txtNombreEmpleado;
    private JTextField txtFechaInicio;
    private JTextField txtFechaTermino;
    private JTextField txtTipoContrato;

    private JCheckBox chkPlanSalud;
    private JCheckBox chkAfp;

    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnEditar;
    private JButton btnEliminar;

    private JTable tblEmpleados;
    private DefaultTableModel modeloTabla;

    public FrmEmpleados() {
        inicializarComponentes();
        setTitle("Sistema de Pago de Empleados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
    }

    private void inicializarComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ==========================
        // PANEL FORMULARIO (NORTE)
        // ==========================
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(
                BorderFactory.createTitledBorder("Datos del empleado")
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        txtIdEmpleado = new JTextField(10);
        txtNombreEmpleado = new JTextField(20);
        txtFechaInicio = new JTextField(10);
        txtFechaTermino = new JTextField(10);
        txtTipoContrato = new JTextField(15);

        chkPlanSalud = new JCheckBox("Plan de salud");
        chkAfp = new JCheckBox("AFP");

        int fila = 0;

        // Fila 0: ID
        gbc.gridx = 0; gbc.gridy = fila;
        panelFormulario.add(new JLabel("ID empleado:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtIdEmpleado, gbc);

        // Fila 1: Nombre
        fila++;
        gbc.gridx = 0; gbc.gridy = fila;
        panelFormulario.add(new JLabel("Nombre empleado:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        panelFormulario.add(txtNombreEmpleado, gbc);
        gbc.gridwidth = 1;

        // Fila 2: Fecha inicio / Fecha término
        fila++;
        gbc.gridx = 0; gbc.gridy = fila;
        panelFormulario.add(new JLabel("Fecha inicio (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtFechaInicio, gbc);

        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Fecha término (yyyy-MM-dd):"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtFechaTermino, gbc);

        // Fila 3: Tipo contrato
        fila++;
        gbc.gridx = 0; gbc.gridy = fila;
        panelFormulario.add(new JLabel("Tipo contrato:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        panelFormulario.add(txtTipoContrato, gbc);
        gbc.gridwidth = 1;

        // Fila 4: Beneficios
        fila++;
        gbc.gridx = 0; gbc.gridy = fila;
        panelFormulario.add(new JLabel("Beneficios:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(chkPlanSalud, gbc);
        gbc.gridx = 2;
        panelFormulario.add(chkAfp, gbc);

        // ==========================
        // PANEL BOTONES (CENTRO)
        // ==========================
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBorder(
                BorderFactory.createTitledBorder("Acciones")
        );

        btnNuevo = new JButton("Nuevo");
        btnGuardar = new JButton("Guardar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");

        btnNuevo.setPreferredSize(new Dimension(100, 28));
        btnGuardar.setPreferredSize(new Dimension(100, 28));
        btnEditar.setPreferredSize(new Dimension(100, 28));
        btnEliminar.setPreferredSize(new Dimension(100, 28));

        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        // ==========================
        // PANEL TABLA (SUR)
        // ==========================
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(
                BorderFactory.createTitledBorder("Listado de empleados")
        );

        String[] columnas = {
            "ID Empleado",
            "Nombre",
            "Fecha inicio",
            "Fecha término",
            "Tipo contrato",
            "Plan salud",
            "AFP"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblEmpleados = new JTable(modeloTabla);
        tblEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblEmpleados.setRowHeight(22);
        tblEmpleados.getTableHeader().setReorderingAllowed(false);

        // Ancho sugerido de columnas
        tblEmpleados.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID
        tblEmpleados.getColumnModel().getColumn(1).setPreferredWidth(160); // Nombre
        tblEmpleados.getColumnModel().getColumn(2).setPreferredWidth(110); // Fecha inicio
        tblEmpleados.getColumnModel().getColumn(3).setPreferredWidth(110); // Fecha término
        tblEmpleados.getColumnModel().getColumn(4).setPreferredWidth(120); // Tipo contrato
        tblEmpleados.getColumnModel().getColumn(5).setPreferredWidth(80);  // Plan salud
        tblEmpleados.getColumnModel().getColumn(6).setPreferredWidth(60);  // AFP

        JScrollPane scrollTabla = new JScrollPane(tblEmpleados);
        scrollTabla.setPreferredSize(new Dimension(650, 250));

        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        // ==========================
        // ARMAR VENTANA
        // ==========================
        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        panelPrincipal.add(panelTabla, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    // ==========================
    // GETTERS PARA EL CONTROLADOR
    // ==========================

    public JTextField getTxtIdEmpleado() {
        return txtIdEmpleado;
    }

    public JTextField getTxtNombreEmpleado() {
        return txtNombreEmpleado;
    }

    public JTextField getTxtFechaInicio() {
        return txtFechaInicio;
    }

    public JTextField getTxtFechaTermino() {
        return txtFechaTermino;
    }

    public JTextField getTxtTipoContrato() {
        return txtTipoContrato;
    }

    public JCheckBox getChkPlanSalud() {
        return chkPlanSalud;
    }

    public JCheckBox getChkAfp() {
        return chkAfp;
    }

    public JButton getBtnNuevo() {
        return btnNuevo;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JTable getTblEmpleados() {
        return tblEmpleados;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }
}
