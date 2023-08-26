package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.Manager;
import service.CrudManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class GenerarPDFmanager extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CrudManager crudmanager;

    public GenerarPDFmanager() {
        crudmanager = new CrudManager();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los datos del formulario
    	String names = request.getParameter("frmNames");
        String lastNames = request.getParameter("frmLast_names");
        String documentType = request.getParameter("frmDocument_type");
        String documentNumber = request.getParameter("frmDocument_number");
        String email = request.getParameter("frmEmail");
        String cellPhone = request.getParameter("frmCell_phone");
        String status = request.getParameter("frmStatus");

        // Agrega aquí los campos adicionales según sea necesario

        // Crear el objeto Encargado con los datos del formulario
        Manager manager = new Manager();
        manager.setNames(names);
        manager.setLast_names(lastNames);
        manager.setDocument_type(documentType);
        manager.setDocument_number(documentNumber);
        manager.setEmail(email);
        manager.setCell_phone(cellPhone);
        manager.setStatus(status);
        // Establece los campos adicionales según corresponda

        // Generar el contenido del PDF
        byte[] pdfContent = generarContenidoPDF(manager);

        // Configurar la respuesta HTTP
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=registros.pdf");

        // Enviar el contenido del PDF en la respuesta
        response.getOutputStream().write(pdfContent);
    }

    private byte[] generarContenidoPDF(Manager manager) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Crear el documento PDF
        Document document = new Document(PageSize.A4.rotate());

        try {
            // Crear el escritor PDF y asociarlo con el flujo de salida
            PdfWriter.getInstance(document, baos);

            // Abrir el documento
            document.open();

            // Crear la tabla con las columnas
            PdfPTable table = new PdfPTable(7); // 7 columnas para los campos del docente

            // Configurar el ancho de las columnas
            float[] columnWidths = {2, 2, 1, 3, 3, 2, 1}; // Anchos relativos de las columnas
            table.setWidths(columnWidths);

            // Configurar el estilo de la tabla
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Crear la celda de encabezado y aplicar estilo
            PdfPCell headerCell = new PdfPCell(new Phrase("Encargados"));
            headerCell.setColspan(7);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell);

            // Agregar los encabezados de columna
            table.addCell(createHeaderCell("Nombre"));
            table.addCell(createHeaderCell("Apellido"));
            table.addCell(createHeaderCell("Tipo de documento"));
            table.addCell(createHeaderCell("Número de documento"));
            table.addCell(createHeaderCell("Correo electrónico"));
            table.addCell(createHeaderCell("Teléfono celular"));
            table.addCell(createHeaderCell("Estado"));

            // Obtener la lista de Encargado de la base de datos
            List<Manager> Manager = crudmanager.getAll();
            // Agregar los Encargados a la tabla
            for (Manager t : Manager) {
            	table.addCell(t.getNames());
                table.addCell(t.getLast_names());
                table.addCell(t.getDocument_type());
                table.addCell(t.getDocument_number());
                table.addCell(t.getEmail());
                table.addCell(t.getCell_phone());
                table.addCell(t.getStatus());
                // Agrega aquí las celdas para los campos adicionales según corresponda
            }

            // Agregar la tabla al documento
            document.add(table);

            // Cerrar el documento
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // Obtener los bytes del PDF generado
        byte[] pdfBytes = baos.toByteArray();

        return pdfBytes;
    }

    private PdfPCell createHeaderCell(String headerText) {
        PdfPCell cell = new PdfPCell(new Phrase(headerText));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.GRAY);
        cell.setBorderWidth(1f);
        return cell;
    }
}
