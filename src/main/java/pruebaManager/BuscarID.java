package pruebaManager;

import model.Manager;
import service.CrudManager;

public class BuscarID {

	public static void main(String[] args) {
		try {
			CrudManager managerService = new CrudManager();
			Manager bean = managerService.getForId("3");
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
