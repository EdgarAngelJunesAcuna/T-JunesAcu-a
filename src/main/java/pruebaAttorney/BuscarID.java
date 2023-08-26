package pruebaAttorney;

import model.Attorney;
import service.CrudAttorney;

public class BuscarID {

	public static void main(String[] args) {
		try {
			CrudAttorney crudAttorney = new CrudAttorney();
			Attorney bean = crudAttorney.getForId("12");
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
