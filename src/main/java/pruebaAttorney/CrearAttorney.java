package pruebaAttorney;

import model.Attorney;
import service.CrudAttorney;

public class CrearAttorney {

	public static void main(String[] args) {
		try {
			// Datos
			Attorney bean = new Attorney(0, "Junes","Pevez","DNI","javierraymondi@gmail.com", "734567389", "934836098", "A");
			// Proceso
			CrudAttorney crudAttorney = new CrudAttorney();
			crudAttorney.insert(bean);
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
