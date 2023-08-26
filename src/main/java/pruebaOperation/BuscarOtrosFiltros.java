package pruebaOperation;

import java.util.List;

import model.Operation;
import service.CrudOperation;

public class BuscarOtrosFiltros {

    public static void main(String[] args) {
        try {
            // Datos
            Operation operation = new Operation();
            operation.setTransaction_Type("");
            operation.setPayment_Method("Credit Card");

            // Proceso
            CrudOperation crudOperation = new CrudOperation();
            List<Operation> lista = crudOperation.get(operation);
            for (Operation op : lista) {
                System.out.println(op);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
