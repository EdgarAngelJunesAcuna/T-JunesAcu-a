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
import com.google.gson.JsonObject;


import model.Manager;
import service.CrudManager;

@WebServlet({ "/managerBuscar", "/managerBuscar2", "/managerProcesar", "/managerToggleStatus"})
public class managerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CrudManager service = new CrudManager();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		switch (path) {
		case "/managerBuscar":
			buscar(request, response);
			break;
		case "/managerBuscar2":
			buscar2(request, response);
			break;
		case "/managerProcesar":
			procesar(request, response);
			break;
		case "/managerToggleStatus":
            toggleStatus(request, response); // Agregado el llamado al método toggleStatus
            break;
        case "/managerCancelar":
            cancelar(request, response); // Agregado el llamado al método cancelar
            break;
		}
	}

	private void procesar(HttpServletRequest request, HttpServletResponse response) {
		
		// Configurar la codificación de caracteres en UTF-8
	    response.setCharacterEncoding("UTF-8");
		
		
        // Datos
        String accion = request.getParameter("accion");
        Manager bean = new Manager();
        bean.setId(Integer.parseInt(request.getParameter("id")));
        bean.setLast_names(request.getParameter("last_names"));
        bean.setNames(request.getParameter("names"));
        bean.setDocument_type(request.getParameter("document_type"));
        bean.setEmail(request.getParameter("email"));
        bean.setDocument_number(request.getParameter("document_number"));
        bean.setCell_phone(request.getParameter("cell_phone"));
        bean.setStatus(request.getParameter("status"));
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
            
            // Crear un objeto JSON solo para incluir el mensaje de respuesta
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("message", "Proceso ok.");
            
            // Enviar la respuesta JSON
            ControllerUtil.responseJson(response, jsonResponse.toString());
        } catch (Exception e) {
            ControllerUtil.responseJson(response, e.getMessage());
        }
    }
	
	
	
	private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Datos
		String last_names = request.getParameter("last_names");
		String names = request.getParameter("names");
		// Proceso
		Manager bean = new Manager();
		bean.setLast_names(last_names);
		bean.setNames(names);
		List<Manager> lista = service.get(bean);
		// Reporte
		request.setAttribute("listado", lista);
		RequestDispatcher rd = request.getRequestDispatcher("manager2.jsp");
		rd.forward(request, response);
	}

	private void buscar2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Datos
		String last_names = request.getParameter("last_names");
		String names = request.getParameter("names");
		
		// Proceso
		Manager bean = new Manager();
		bean.setLast_names(last_names);
		bean.setNames(names);
		
		List<Manager> lista = service.get(bean);
		// Preparando el JSON
		Gson gson = new Gson();
		String data = gson.toJson(lista);
		// Reporte
		ControllerUtil.responseJson(response, data);
	}

	private void toggleStatus(HttpServletRequest request, HttpServletResponse response) {
		int managerId = Integer.parseInt(request.getParameter("managerId"));
		try {
			// Cambiar el estado del estudiante utilizando el servicio
			service.deleteByStatus(String.valueOf(managerId));

			ControllerUtil.responseJson(response, "Proceso exitoso.");
		} catch (Exception e) {
			ControllerUtil.responseJson(response, e.getMessage());
		}
	}

	private void cancelar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// Eliminar los datos ingresados
		eliminarDatos(request);

		// Redirigir a la página de búsqueda
		response.sendRedirect("managerBuscar");
	}

	private void eliminarDatos(HttpServletRequest request) {
		request.removeAttribute("id");
        request.removeAttribute("last_names");
        request.removeAttribute("names");
        request.removeAttribute("document_type");
        request.removeAttribute("email");
        request.removeAttribute("document_number");
        request.removeAttribute("cell_phone");
        request.removeAttribute("status");
	}

}
