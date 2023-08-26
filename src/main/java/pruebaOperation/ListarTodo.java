package pruebaOperation;

import java.util.List;

import model.Operation;
import service.CrudOperation;

public class ListarTodo {
    
    public static void main(String[] args) {
        try {
            CrudOperation crudOperation = new CrudOperation();
            List<Operation> lista = crudOperation.getAll();
            for (Operation operation : lista) {
                System.out.println(operation);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
