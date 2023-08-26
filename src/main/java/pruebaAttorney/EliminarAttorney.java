package pruebaAttorney;


import service.CrudAttorney;

public class EliminarAttorney {

	public static void main(String[] args) {
		try {
			// Datos
			String id = "12";
			// Proceso
			CrudAttorney crudAttorney = new CrudAttorney();
			crudAttorney.delete(id);
			System.out.println("Registro apoderado eliminado.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
