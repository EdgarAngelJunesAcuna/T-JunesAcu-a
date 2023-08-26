package controller;

import com.opencsv.CSVWriter;

import model.Operation;
import service.CrudOperation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class GenerarCSVoperation extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CrudOperation crudCourse;

    public GenerarCSVoperation() {
        crudCourse = new CrudOperation();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los datos del formulario
        String name_course = request.getParameter("frmName_course");


        // Agrega aquí los campos adicionales según sea necesario

        // Crear el objeto Student con los datos del formulario
        Operation course = new Operation();
        course.setName_course(name_course);

        // Establece los campos adicionales según corresponda

        // Generar el contenido del CSV
        String csvContent = generarContenidoCSV(course);

        // Configurar la respuesta HTTP
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=registros.csv");

        // Enviar el contenido del CSV en la respuesta
        PrintWriter writer = response.getWriter();
        writer.write(csvContent);
        writer.flush();
    }

    private String generarContenidoCSV(Operation course) {
        StringWriter stringWriter = new StringWriter();

        try (CSVWriter writer = new CSVWriter(stringWriter)) {
            // Agregar el encabezado del CSV
            String[] header = {"Nombre del curso"};
            writer.writeNext(header);

            // Agregar los datos del estudiante al CSV
            String[] data = {course.getName_course()};
            writer.writeNext(data);

            // Obtener la lista de estudiantes de la base de datos
            List<Operation> courses = crudCourse.getAll();
            // Agregar los estudiantes al CSV
            for (Operation s : courses) {
                String[] courseData = {s.getName_course()};
                writer.writeNext(courseData);
                // Agrega aquí los campos adicionales según corresponda
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringWriter.toString();
    }

}
