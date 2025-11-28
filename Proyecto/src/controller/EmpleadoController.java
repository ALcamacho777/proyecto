package controller;

import model.Empleado;   // ← IMPORT NECESARIO
import view.FrmEmpleados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class EmpleadoController implements ActionListener {

    private FrmEmpleados vista;
    private EmpleadoDAO dao;

    public EmpleadoController(FrmEmpleados vista, EmpleadoDAO dao) {
        this.vista = vista;
        this.dao = dao;

        // Enlazar botones con el controlador
        this.vista.getBtnNuevo().addActionListener(this);
        this.vista.getBtnGuardar().addActionListener(this);
        this.vista.getBtnEditar().addActionListener(this);
        this.vista.getBtnEliminar().addActionListener(this);

        // Cargar datos en la tabla al iniciar
        cargarTabla();
        agregarListenerTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnNuevo()) {
            limpiarCampos();
        } else if (e.getSource() == vista.getBtnGuardar()) {
            guardarEmpleado();
        } else if (e.getSource() == vista.getBtnEditar()) {
            editarEmpleado();
        } else if (e.getSource() == vista.getBtnEliminar()) {
            eliminarEmpleado();
        }
    }

    // ==========================
    // VALIDACIONES Y CONVERSIÓN
    // ==========================
    private boolean validarCamposObligatorios() {
        if (vista.getTxtIdEmpleado().getText().trim().isEmpty()
                || vista.getTxtNombreEmpleado().getText().trim().isEmpty()
                || vista.getTxtFechaInicio().getText().trim().isEmpty()
                || vista.getTxtFechaTermino().getText().trim().isEmpty()
                || vista.getTxtTipoContrato().getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(vista,
                    "ID, Nombre, Fechas y Tipo de contrato son obligatorios.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private Integer obtenerIdDesdeFormulario() {
        try {
            return Integer.parseInt(vista.getTxtIdEmpleado().getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista,
                    "El ID de empleado debe ser numérico.",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private Date parseFecha(String texto) {
        try {
            // Formato esperado: yyyy-MM-dd
            return Date.valueOf(texto.trim());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(vista,
                    "La fecha debe tener el formato yyyy-MM-dd, por ejemplo 2024-11-23.",
                    "Error de formato de fecha",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // ==========================
    // ACCIONES CRUD
    // ==========================
    private void guardarEmpleado() {
        if (!validarCamposObligatorios()) {
            return;
        }

        Integer id = obtenerIdDesdeFormulario();
        if (id == null) {
            return;
        }

        Date fechaInicio = parseFecha(vista.getTxtFechaInicio().getText());
        Date fechaTermino = parseFecha(vista.getTxtFechaTermino().getText());
        if (fechaInicio == null || fechaTermino == null) {
            return;
        }

        String nombre = vista.getTxtNombreEmpleado().getText().trim();
        String tipoContrato = vista.getTxtTipoContrato().getText().trim();
        boolean planSalud = vista.getChkPlanSalud().isSelected();
        boolean afp = vista.getChkAfp().isSelected();

        Empleado emp = new Empleado(id, nombre, fechaInicio, fechaTermino, tipoContrato, planSalud, afp);

        if (dao.insertar(emp)) {
            JOptionPane.showMessageDialog(vista, "Empleado agregado correctamente.");
            cargarTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vista,
                    "No se pudo agregar el empleado (revisa si el ID ya existe).",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarEmpleado() {
        if (!validarCamposObligatorios()) {
            return;
        }

        Integer id = obtenerIdDesdeFormulario();
        if (id == null) {
            return;
        }

        Date fechaInicio = parseFecha(vista.getTxtFechaInicio().getText());
        Date fechaTermino = parseFecha(vista.getTxtFechaTermino().getText());
        if (fechaInicio == null || fechaTermino == null) {
            return;
        }

        String nombre = vista.getTxtNombreEmpleado().getText().trim();
        String tipoContrato = vista.getTxtTipoContrato().getText().trim();
        boolean planSalud = vista.getChkPlanSalud().isSelected();
        boolean afp = vista.getChkAfp().isSelected();

        Empleado emp = new Empleado(id, nombre, fechaInicio, fechaTermino, tipoContrato, planSalud, afp);

        if (dao.actualizar(emp)) {
            JOptionPane.showMessageDialog(vista, "Empleado actualizado correctamente.");
            cargarTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vista,
                    "No se pudo actualizar el empleado.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEmpleado() {
        String idTexto = vista.getTxtIdEmpleado().getText().trim();
        if (idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                    "Debes ingresar o seleccionar un ID de empleado para eliminar.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer id = obtenerIdDesdeFormulario();
        if (id == null) {
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(vista,
                "¿Estás seguro de eliminar este empleado?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            if (dao.eliminar(id)) {
                JOptionPane.showMessageDialog(vista, "Empleado eliminado correctamente.");
                cargarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista,
                        "No se pudo eliminar el empleado.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ==========================
    // TABLA Y FORMULARIO
    // ==========================
    private void cargarTabla() {
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0); // limpiar

        List<Empleado> empleados = dao.listar();
        for (Empleado e : empleados) {
            Object[] fila = new Object[7];
            fila[0] = e.getIdEmpleado();
            fila[1] = e.getNombreEmpleado();
            fila[2] = e.getFechaInicio();
            fila[3] = e.getFechaTermino();
            fila[4] = e.getTipoContrato();
            fila[5] = e.isPlanSalud() ? "Sí" : "No";
            fila[6] = e.isAfp() ? "Sí" : "No";
            modelo.addRow(fila);
        }
    }

    private void agregarListenerTabla() {
        vista.getTblEmpleados().getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int fila = vista.getTblEmpleados().getSelectedRow();
                if (fila != -1) {
                    DefaultTableModel modelo = vista.getModeloTabla();
                    vista.getTxtIdEmpleado().setText(modelo.getValueAt(fila, 0).toString());
                    vista.getTxtNombreEmpleado().setText(modelo.getValueAt(fila, 1).toString());
                    vista.getTxtFechaInicio().setText(modelo.getValueAt(fila, 2).toString());
                    vista.getTxtFechaTermino().setText(modelo.getValueAt(fila, 3).toString());
                    vista.getTxtTipoContrato().setText(modelo.getValueAt(fila, 4).toString());
                    vista.getChkPlanSalud().setSelected("Sí".equals(modelo.getValueAt(fila, 5)));
                    vista.getChkAfp().setSelected("Sí".equals(modelo.getValueAt(fila, 6)));
                }
            }
        });
    }

    private void limpiarCampos() {
        vista.getTxtIdEmpleado().setText("");
        vista.getTxtNombreEmpleado().setText("");
        vista.getTxtFechaInicio().setText("");
        vista.getTxtFechaTermino().setText("");
        vista.getTxtTipoContrato().setText("");
        vista.getChkPlanSalud().setSelected(false);
        vista.getChkAfp().setSelected(false);
        vista.getTblEmpleados().clearSelection();
    }
}
