package pruebaManager;


import service.CrudManager;

public class EliminarManager {

	public static void main(String[] args) {
		try {
			// Datos
			String id = "11";
			// Proceso
			CrudManager managerService = new CrudManager();
			managerService.delete(id);
			System.out.println("Registro eliminado.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
