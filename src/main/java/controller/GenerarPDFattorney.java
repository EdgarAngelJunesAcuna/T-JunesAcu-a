package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Attorney;
import service.CrudAttorney;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class GenerarPDFattorney extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CrudAttorney crudAttorney;

    public GenerarPDFattorney() {
        crudAttorney = new CrudAttorney();
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

        // Crear el objeto Attorney con los datos del formulario
        Attorney attorney = new Attorney();
        attorney.setNames(names);
        attorney.setLast_names(lastNames);
        attorney.setDocument_type(documentType);
        attorney.setDocument_number(documentNumber);
        attorney.setEmail(email);
        attorney.setCell_phone(cellPhone);
        attorney.setStatus(status);

        // Generar el contenido del PDF
        byte[] pdfContent = generarContenidoPDF(attorney);

        // Configurar la respuesta HTTP
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=registros.pdf");

        // Enviar el contenido del PDF en la respuesta
        response.getOutputStream().write(pdfContent);
    }

    private byte[] generarContenidoPDF(Attorney attorney) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Crear el documento PDF en modo horizontal
        Document document = new Document(PageSize.A4.rotate());

        try {
            // Crear el escritor PDF y asociarlo con el flujo de salida
            PdfWriter.getInstance(document, baos);

            // Abrir el documento
            document.open();

            // Crear la tabla con las columnas
            PdfPTable table = new PdfPTable(7); // 7 columnas para los campos del estudiante

            // Configurar el ancho de las columnas
            float[] columnWidths = {2, 2, 1, 3, 5, 2, 1}; // Anchos relativos de las columnas
            table.setWidths(columnWidths);

            // Configurar el estilo de la tabla
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Crear la celda de encabezado y aplicar estilo
            PdfPCell headerCell = new PdfPCell(new Phrase("Lista de Apoderados"));
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
            table.addCell(createHeaderCell("Teléfono"));
            table.addCell(createHeaderCell("Estado"));

            // Obtener la lista de abogados de la base de datos
            List<Attorney> attorneys = crudAttorney.getAll();
            // Agregar los apoderados a la tabla
            for (Attorney a : attorneys) {
                table.addCell(a.getNames());
                table.addCell(a.getLast_names());
                table.addCell(a.getDocument_type());
                table.addCell(a.getDocument_number());
                table.addCell(a.getEmail());
                table.addCell(a.getCell_phone());
                table.addCell(a.getStatus());
                // Agrega aquí los campos adicionales según corresponda
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
