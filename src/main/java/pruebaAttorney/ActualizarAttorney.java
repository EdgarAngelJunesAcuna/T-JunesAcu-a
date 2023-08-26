package pruebaAttorney;

import model.Attorney;
import service.CrudAttorney;

public class ActualizarAttorney {

	public static void main(String[] args) {
		try {
			// Datos
			Attorney bean = new Attorney(12, "Junes","Raziel", "DNI", "e.junes@gmail.com", "734567389", "932836098", "A");
			// Proceso
			CrudAttorney crudAttorney = new CrudAttorney();
			crudAttorney.update(bean);
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
