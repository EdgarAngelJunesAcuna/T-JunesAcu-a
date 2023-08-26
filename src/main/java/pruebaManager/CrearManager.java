package pruebaManager;

import model.Manager;
import service.CrudManager;

public class CrearManager {

	public static void main(String[] args) {
		try {
			// Datos
			Manager bean = new Manager(0, "Juan","Raymondi","DNI","javierraymondi@gmail.com", "734567389", "934836098", "A");
			// Proceso
			CrudManager managerService = new CrudManager();
			managerService.insert(bean);
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
