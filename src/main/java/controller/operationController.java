package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Operation;
import service.CrudOperation;

@WebServlet({ "/courseBuscar", "/courseBuscar2", "/courseProcesar" })
public class operationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CrudOperation service = new CrudOperation();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		switch (path) {
		case "/courseBuscar":
			buscar(request, response);
			break;
		case "/courseBuscar2":
			buscar2(request, response);
			break;
		case "/courseProcesar":
			procesar(request, response);
			break;
		}
	}

	private void procesar(HttpServletRequest request, HttpServletResponse response) {
		// Datos
		String accion = request.getParameter("accion");
		Operation bean = new Operation();
		bean.setId(Integer.parseInt(request.getParameter("id")));
		bean.setName_course(request.getParameter("name_course"));
		// Proceso
		try {
			switch (accion) {
			case ControllerUtil.CRUD_NUEVO: 
				service.insert(bean);
				break;
			case ControllerUtil.CRUD_EDITAR: 
				service.update(bean);
				break;
			case ControllerUtil.CRUD_ELIMINAR: 
				service.delete(bean.getId().toString());
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + accion);
			}
			ControllerUtil.responseJson(response, "Proceso ok.");
		} catch (Exception e) {
			ControllerUtil.responseJson(response, e.getMessage());
		}
	}

	private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Datos
		String name_course = request.getParameter("name_course");
		// Proceso
		Operation bean = new Operation();
		bean.setName_course(name_course);
		List<Operation> lista = service.get(bean);
		// Reporte
		request.setAttribute("listado", lista);
		RequestDispatcher rd = request.getRequestDispatcher("course.jsp");
		rd.forward(request, response);
	}

	private void buscar2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Datos
		String name_course = request.getParameter("name_course");
		// Proceso
		Operation bean = new Operation();
		bean.setName_course(name_course);
		List<Operation> lista = service.get(bean);
		// Preparando el JSON
		Gson gson = new Gson();
		String data = gson.toJson(lista);
		// Reporte
		ControllerUtil.responseJson(response, data);
	}

}
