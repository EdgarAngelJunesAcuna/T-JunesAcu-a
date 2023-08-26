package pruebaAttorney;

import java.util.List;

import model.Attorney;
import service.CrudAttorney;

public class ListarTodo {
	
	public static void main(String[] args) {
		try {
			CrudAttorney crudattorney = new CrudAttorney();
			List<Attorney> lista = crudattorney.getAll();
			for (Attorney attorney : lista) {
				if (attorney.getStatus().equals("A")) {
					System.out.println(attorney);
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
