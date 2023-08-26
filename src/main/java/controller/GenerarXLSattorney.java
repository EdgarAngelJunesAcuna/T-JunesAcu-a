package controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import model.Attorney;
import service.CrudAttorney;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class GenerarXLSattorney extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CrudAttorney crudAttorney;

    public GenerarXLSattorney() {
        crudAttorney = new CrudAttorney();
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

        // Crear el objeto Attorney con los datos del formulario
        Attorney attorney = new Attorney();
        attorney.setNames(names);
        attorney.setLast_names(last_names);
        attorney.setDocument_type(document_type);
        attorney.setDocument_number(document_number);
        attorney.setEmail(email);
        attorney.setCell_phone(cell_phone);
        attorney.setStatus(status);

        // Obtener la lista de abogados de la base de datos
        List<Attorney> attorneys = crudAttorney.getAll();

        // Crear un nuevo libro de trabajo de Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Apoderado");

        // Crear el encabezado
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Nombres", "Apellidos", "Tipo de documento", "Número de documento", "Correo electrónico", "Teléfono celular", "Estado"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Agregar los datos del abogado del formulario
        Row dataRow = sheet.createRow(1);
        int columnIndex = 0;
        for (int i = 0; i < headers.length; i++) {
            Cell cell = dataRow.createCell(columnIndex++);
            cell.setCellValue(getAttorneyFieldValue(attorney, i));
        }

        // Agregar los abogados al archivo XLSX
        int rowIndex = 2;
        for (Attorney s : attorneys) {
            Row row = sheet.createRow(rowIndex++);
            columnIndex = 0;  // Reiniciar el contador de columnas para cada fila
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(columnIndex++);
                cell.setCellValue(getAttorneyFieldValue(s, i));
            }
        }

        // Ajustar el ancho de las columnas manualmente
        columnIndex = 0;
        for (int i = 0; i < headers.length; i++) {
            sheet.setColumnWidth(columnIndex++, 15 * 256);  // Establecer tamaño de 15 caracteres para cada columna
        }

        // Configurar el tipo de contenido y el encabezado de respuesta
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=apoderados.xlsx");
        // Escribir el libro de trabajo en el flujo de salida
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private String getAttorneyFieldValue(Attorney attorney, int index) {
        switch (index) {
            case 0:
            	return attorney.getNames();
            case 1:                
                return attorney.getLast_names();
            case 2:
                return attorney.getDocument_type();
            case 3:
            	return attorney.getDocument_number();
                
            case 4:
            	return attorney.getEmail();
            case 5:
                return attorney.getCell_phone();
            case 6:
                return attorney.getStatus();
            default:
                return "";
        }
    }
}
