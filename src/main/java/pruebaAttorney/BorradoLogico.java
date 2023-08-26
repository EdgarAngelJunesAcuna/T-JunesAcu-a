package pruebaAttorney;


import service.CrudAttorney;

public class BorradoLogico {

    public static void main(String[] args) {
        try {
            // Datos
            String id = "11"; // ID del apoderado a Activar o Desactivar
            // Proceso
            CrudAttorney crudAttorney = new CrudAttorney();
            crudAttorney.deleteByStatus(id);
            System.out.println("Apoderado activado/eliminado correctamente.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
