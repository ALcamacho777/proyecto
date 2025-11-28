package controller;

import db.Conexion;
import model.Empleado;   // ← IMPORT NECESARIO

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    public boolean insertar(Empleado e) {
        String sql = "INSERT INTO empleado (IdEmpleado, nombreEmpleado, fechaInicio, fechaTermino, tipoContrato, planSalud, afp) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, e.getIdEmpleado());
            ps.setString(2, e.getNombreEmpleado());
            ps.setDate(3, e.getFechaInicio());
            ps.setDate(4, e.getFechaTermino());
            ps.setString(5, e.getTipoContrato());
            ps.setBoolean(6, e.isPlanSalud());
            ps.setBoolean(7, e.isAfp());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error insertar: " + ex.getMessage());
            return false;
        }
    }

    public boolean actualizar(Empleado e) {
        String sql = "UPDATE empleado SET nombreEmpleado=?, fechaInicio=?, fechaTermino=?, tipoContrato=?, planSalud=?, afp=? "
                + "WHERE IdEmpleado=?";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNombreEmpleado());
            ps.setDate(2, e.getFechaInicio());
            ps.setDate(3, e.getFechaTermino());
            ps.setString(4, e.getTipoContrato());
            ps.setBoolean(5, e.isPlanSalud());
            ps.setBoolean(6, e.isAfp());
            ps.setInt(7, e.getIdEmpleado());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error actualizar: " + ex.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM empleado WHERE IdEmpleado = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error eliminar: " + ex.getMessage());
            return false;
        }
    }

    public List<Empleado> listar() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleado";

        Connection con = Conexion.getConexion();
        if (con == null) {
            System.out.println("No hay conexión a la BD, se muestra tabla vacía.");
            return lista; // devolvemos lista vacía y NO rompemos la app
        }

        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Empleado e = new Empleado(
                        rs.getInt("IdEmpleado"),
                        rs.getString("nombreEmpleado"),
                        rs.getDate("fechaInicio"),
                        rs.getDate("fechaTermino"),
                        rs.getString("tipoContrato"),
                        rs.getBoolean("planSalud"),
                        rs.getBoolean("afp")
                );
                lista.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("Error listar: " + ex.getMessage());
        }

        return lista;
    }

}
