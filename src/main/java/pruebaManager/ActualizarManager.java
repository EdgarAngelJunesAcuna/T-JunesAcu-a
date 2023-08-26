package pruebaManager;

import model.Manager;
import service.CrudManager;

public class ActualizarManager {

	public static void main(String[] args) {
		try {
			// Datos
			Manager bean = new Manager(5, "Carlos","Nivel","DNI","vamosvolar@gmail.com", "754567645", "932836321", "A");
			// Proceso
			CrudManager managerService = new CrudManager();
			managerService.update(bean);
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
