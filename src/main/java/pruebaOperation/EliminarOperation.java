package pruebaOperation;

import service.CrudOperation;

public class EliminarOperation {

    public static void main(String[] args) {
        try {
            // Datos
            String id = "4";
            // Proceso
            CrudOperation crudOperation = new CrudOperation();
            crudOperation.delete(id);
            System.out.println("Registro eliminado.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}

