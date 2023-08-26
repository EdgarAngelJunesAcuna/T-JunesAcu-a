package pruebaManager;


import service.CrudManager;

public class BorradoLogico {

    public static void main(String[] args) {
        try {
            // Datos
            String id = "3"; // ID del estudiante a Activar o Desactivar
            // Proceso
            CrudManager managerService = new CrudManager();
            managerService.deleteByStatus(id);
            System.out.println("Encargado activado/eliminado correctamente.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
