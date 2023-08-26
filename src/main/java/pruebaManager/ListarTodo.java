package pruebaManager;

import java.util.List;

import model.Manager;
import service.CrudManager;

public class ListarTodo {
	
	public static void main(String[] args) {
		try {
			CrudManager managerService = new CrudManager();
			List<Manager> lista = managerService.getAll();
			for (Manager manager : lista) {
				if (manager.getStatus().equals("A")) {
					System.out.println(manager);
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
