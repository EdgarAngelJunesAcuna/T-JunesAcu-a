package controller;

import com.opencsv.CSVWriter;

import model.Attorney;
import service.CrudAttorney;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class GenerarCSVattorney extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CrudAttorney crudAttorney;

    public GenerarCSVattorney() {
        crudAttorney = new CrudAttorney();
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

        // Crear el objeto Student con los datos del formulario
        Attorney attorney = new Attorney();
        attorney.setLast_names(lastNames);
        attorney.setNames(names);
        attorney.setDocument_type(documentType);
        attorney.setEmail(email);
        attorney.setDocument_number(documentNumber);
        attorney.setCell_phone(cellPhone);
        attorney.setStatus(status);
        // Establece los campos adicionales según corresponda

        // Generar el contenido del CSV
        String csvContent = generarContenidoCSV(attorney);

        // Configurar la respuesta HTTP
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=registros.csv");

        // Enviar el contenido del CSV en la respuesta
        PrintWriter writer = response.getWriter();
        writer.write(csvContent);
        writer.flush();
    }

    private String generarContenidoCSV(Attorney attorney) {
        StringWriter stringWriter = new StringWriter();

        try (CSVWriter writer = new CSVWriter(stringWriter)) {
            // Agregar el encabezado del CSV
            String[] header = {"Apellido", "Nombre", "Tipo de documento", "Correo electrónico", "Número de documento", "Teléfono celular", "Estado"};
            writer.writeNext(header);

            // Agregar los datos del estudiante al CSV
            String[] data = {attorney.getLast_names(), attorney.getNames(), attorney.getDocument_type(), attorney.getEmail(),
            		attorney.getDocument_number(), attorney.getCell_phone(), attorney.getStatus()};
            writer.writeNext(data);

            // Obtener la lista de estudiantes de la base de datos
            List<Attorney> attorneys = crudAttorney.getAll();
            // Agregar los estudiantes al CSV
            for (Attorney s : attorneys) {
                String[] attorneyData = {s.getLast_names(), s.getNames(), s.getDocument_type(), s.getEmail(),
                        s.getDocument_number(), s.getCell_phone(), s.getStatus()};
                writer.writeNext(attorneyData);
                // Agrega aquí los campos adicionales según corresponda
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringWriter.toString();
    }

}
