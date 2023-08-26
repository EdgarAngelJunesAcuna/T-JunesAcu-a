package controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Manager;
import service.CrudManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class GenerarXLSmanager extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CrudManager crudManager;

    public GenerarXLSmanager() {
        crudManager = new CrudManager();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los datos del formulario
    	String names = request.getParameter("names");
        String last_names = request.getParameter("last_names");
        String document_type = request.getParameter("document_type");
        String document_number = request.getParameter("document_number");
        String email = request.getParameter("email");
        String cell_phone = request.getParameter("cell_phone");
        String status = request.getParameter("status");

        // Crear el objeto Manager con los datos del formulario
        Manager manager = new Manager();
        manager.setNames(names);
        manager.setLast_names(last_names);
        manager.setDocument_type(document_type);
        manager.setDocument_number(document_number);
        manager.setEmail(email);
        manager.setCell_phone(cell_phone);
        manager.setStatus(status);

        // Obtener la lista de managers de la base de datos
        List<Manager> managers = crudManager.getAll();

        // Crear un nuevo libro de trabajo de Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Encargado");

        // Crear el encabezado
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Nombres", "Apellidos", "Tipo de documento", "Número de documento", "Correo electrónico", "Teléfono celular", "Estado"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Agregar los datos del manager del formulario
        Row dataRow = sheet.createRow(1);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = dataRow.createCell(i);
            cell.setCellValue(getManagerFieldValue(manager, i));
        }

        // Agregar una fila vacía para separar los datos
        sheet.createRow(2);

        // Crear una fila para los encabezados de la tabla de managers de la base de datos
        Row managersHeaderRow = sheet.createRow(3);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = managersHeaderRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Agregar los managers de la base de datos al archivo XLSX
        int rowIndex = 4;
        for (Manager s : managers) {
            Row row = sheet.createRow(rowIndex++);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(getManagerFieldValue(s, i));
            }
        }

        // Ajustar el ancho de las columnas automáticamente
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Configurar el tipo de contenido y el encabezado de respuesta
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=Encargados.xlsx");

        // Escribir el libro de trabajo en el flujo de salida
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private String getManagerFieldValue(Manager manager, int index) {
        switch (index) {
        case 0:
        	return manager.getNames();
        case 1:                
            return manager.getLast_names();
        case 2:
            return manager.getDocument_type();
        case 3:
        	return manager.getDocument_number();
            
        case 4:
        	return manager.getEmail();
        case 5:
            return manager.getCell_phone();
        case 6:
            return manager.getStatus();
        default:
            return "";
        }
    }
}
