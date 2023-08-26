package controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Operation;
import service.CrudOperation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class GenerarXLSoperation extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CrudOperation crudCourse;

    public GenerarXLSoperation() {
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

        // Crear un nuevo libro de trabajo de Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hoja1");

        // Crear el encabezado
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Nombre del curso"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Agregar los datos del curso
        Row dataRow = sheet.createRow(1);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = dataRow.createCell(i);
            cell.setCellValue(getCourseFieldValue(course, i));
        }

        // Obtener la lista de estudiantes de la base de datos
        List<Operation> courses = crudCourse.getAll();

        // Agregar los estudiantes al archivo XLSX
        int rowIndex = 2;
        for (Operation s : courses) {
            Row row = sheet.createRow(rowIndex++);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(getCourseFieldValue(s, i));
            }
        }

        // Ajustar el ancho de las columnas automáticamente
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Configurar el tipo de contenido y el encabezado de respuesta
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=registros.xlsx");

        // Escribir el libro de trabajo en el flujo de salida
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private String getCourseFieldValue(Operation course, int index) {
        switch (index) {
            case 0:
                return course.getName_course();
         
            default:
                return "";
        }
    }
}
