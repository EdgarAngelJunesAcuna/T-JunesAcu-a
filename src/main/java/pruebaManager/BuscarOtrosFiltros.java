package pruebaManager;

import java.util.List;

import model.Manager;
import service.CrudManager;

public class BuscarOtrosFiltros {

	public static void main(String[] args) {
		try {
			// Datos
			Manager bean = new Manager();
			bean.setLast_names("");
			bean.setNames("");
			// Proceso
			CrudManager managerService = new CrudManager();
			List<Manager> lista = managerService.get(bean);
			for (Manager manager : lista) {
				System.out.println(manager);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
