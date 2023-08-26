package controller;

import com.opencsv.CSVWriter;

import model.Manager;
import service.CrudManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class GenerarCSVmanager extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CrudManager crudmanager;

    public GenerarCSVmanager() {
        crudmanager = new CrudManager();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los datos del formulario
        String lastNames = request.getParameter("frmLast_names");
        String names = request.getParameter("frmNames");
        String documentType = request.getParameter("frmDocument_type");
        String email = request.getParameter("frmEmail");
        String documentNumber = request.getParameter("frmDocument_number");
        String cellPhone = request.getParameter("frmCell_phone");
        String status = request.getParameter("frmStatus");

        // Agrega aquí los campos adicionales según sea necesario

        // Crear el objeto Encargado con los datos del formulario
        Manager manager = new Manager();
        manager.setLast_names(lastNames);
        manager.setNames(names);
        manager.setDocument_type(documentType);
        manager.setEmail(email);
        manager.setDocument_number(documentNumber);
        manager.setCell_phone(cellPhone);
        manager.setStatus(status);
        // Establece los campos adicionales según corresponda

        // Generar el contenido del CSV
        String csvContent = generarContenidoCSV(manager);

        // Configurar la respuesta HTTP
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=registros.csv");

        // Enviar el contenido del CSV en la respuesta
        PrintWriter writer = response.getWriter();
        writer.write(csvContent);
        writer.flush();
    }

    private String generarContenidoCSV(Manager manager) {
        StringWriter stringWriter = new StringWriter();

        try (CSVWriter writer = new CSVWriter(stringWriter)) {
            // Agregar el encabezado del CSV
            String[] header = {"Apellido", "Nombre", "Tipo de documento", "Correo electrónico", "Número de documento", "Teléfono celular", "Estado"};
            writer.writeNext(header);

            // Agregar los datos del Encargado al CSV
            String[] data = {manager.getLast_names(), manager.getNames(), manager.getDocument_type(), manager.getEmail(),
            		manager.getDocument_number(), manager.getCell_phone(), manager.getStatus()};
            writer.writeNext(data);

            // Obtener la lista de Encargado de la base de datos
            List<Manager> Manager = crudmanager.getAll();
            // Agregar los estudiantes al CSV
            for (Manager s : Manager) {
                String[] managerData = {s.getLast_names(), s.getNames(), s.getDocument_type(), s.getEmail(),
                        s.getDocument_number(), s.getCell_phone(), s.getStatus()};
                writer.writeNext(managerData);
                // Agrega aquí los campos adicionales según corresponda
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringWriter.toString();
    }

}
