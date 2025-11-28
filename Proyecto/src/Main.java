
import controller.EmpleadoController;
import controller.EmpleadoDAO;
import view.FrmEmpleados;

public class Main {

    public static void main(String[] args) {
        FrmEmpleados vista = new FrmEmpleados();
        EmpleadoDAO dao = new EmpleadoDAO();
        new EmpleadoController(vista, dao);
        vista.setVisible(true);
    }
}
