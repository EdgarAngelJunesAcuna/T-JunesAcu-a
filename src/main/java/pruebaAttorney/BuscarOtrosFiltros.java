package pruebaAttorney;

import java.util.List;

import model.Attorney;
import service.CrudAttorney;

public class BuscarOtrosFiltros {

	public static void main(String[] args) {
		try {
			// Datos
			Attorney bean = new Attorney();
			bean.setLast_names("     ");
			bean.setNames("Junes");
			// Proceso
			CrudAttorney crudAttorney = new CrudAttorney();
			List<Attorney> lista = crudAttorney.get(bean);
			for (Attorney attorney : lista) {
				System.out.println(attorney);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
