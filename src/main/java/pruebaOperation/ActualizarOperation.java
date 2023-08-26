package pruebaOperation;

import model.Operation;
import service.CrudOperation;

public class ActualizarOperation {

    public static void main(String[] args) {
        try {
            // Datos
            Operation operation = new Operation(4, "2023-06-29 10:30:00", "Tipo de transacción", "Descripción de la operación",
                    100.50, "Cuenta de origen", "Cuenta de destino", 1, 2, "Método de pago", "Número de comprobante");

            // Proceso
            CrudOperation crudOperation = new CrudOperation();
            crudOperation.update(operation);
            System.out.println(operation);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
