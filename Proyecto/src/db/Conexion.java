package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/pagosbd?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConexion() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa al servidor MySQL.");
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el driver MySQL: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return con;
    }
}
