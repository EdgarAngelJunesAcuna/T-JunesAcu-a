package pruebaOperation;

import model.Operation;
import service.CrudOperation;

public class BuscarID {

    public static void main(String[] args) {
        try {
            CrudOperation crudOperation = new CrudOperation();
            Operation operation = crudOperation.getForId("3");
            System.out.println(operation);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
