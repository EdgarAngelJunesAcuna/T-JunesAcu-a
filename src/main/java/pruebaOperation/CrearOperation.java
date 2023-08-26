package pruebaOperation;

import model.Operation;
import service.CrudOperation;

public class CrearOperation {

    public static void main(String[] args) {
        try {
            // Datos
            Operation operation = new Operation(0, "2023-03-06 09:16:00.0", "Withdrawal", "Alquiler de patio con toldo",
                    100.60, "1234565890", "1233567878", 1, 2, "Cash", "T545215");

            // Proceso
            CrudOperation crudOperation = new CrudOperation();
            crudOperation.insert(operation);
            System.out.println(operation);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
