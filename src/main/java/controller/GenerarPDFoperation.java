package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import model.Operation;
import service.CrudOperation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class GenerarPDFoperation extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CrudOperation crudCourse;

    public GenerarPDFoperation() {
        crudCourse = new CrudOperation();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los datos del formulario
        String name_course = request.getParameter("frmName_course");

        // Agrega aquí los campos adicionales según sea necesario

        // Crear el objeto Course con los datos del formulario
        Operation course = new Operation();
        course.setName_course(name_course);

        // Generar el contenido del PDF
        byte[] pdfContent = generarContenidoPDF(course);

        // Configurar la respuesta HTTP
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=registros.pdf");

        // Enviar el contenido del PDF en la respuesta
        response.getOutputStream().write(pdfContent);
    }

    private byte[] generarContenidoPDF(Operation course) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Crear el documento PDF
        Document document = new Document();

        try {
            // Crear el escritor PDF y asociarlo con el flujo de salida
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            // Agregar pie de página personalizado
            PdfPageEventHelper eventHelper = new PdfPageEventHelper() {
                public void onEndPage(PdfWriter writer, Document document) {
                    // Configurar el pie de página con el número de página
                    int pageNumber = writer.getPageNumber();
                    String footerText = "Página " + pageNumber;
                    Rectangle pageSize = document.getPageSize();
                    PdfContentByte canvas = writer.getDirectContent();
                    ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase(footerText), pageSize.getRight() / 2, pageSize.getBottom() + 30, 0);
                }
            };
            writer.setPageEvent(eventHelper);

            // Abrir el documento
            document.open();

            // Crear la tabla
            PdfPTable table = new PdfPTable(1); // 1 columna

            // Configurar el estilo de la tabla
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Crear las celdas de encabezado
            PdfPCell headerCell = new PdfPCell(new Phrase("Nombre de curso", getBoldFont()));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);

            // Agregar las celdas de encabezado a la tabla
            table.addCell(headerCell);

            // Obtener la lista de cursos de la base de datos
            List<Operation> courses = crudCourse.getAll();
            // Agregar los cursos a la tabla
            for (Operation s : courses) {
                // Agregar el nombre del curso como una celda en la tabla
                PdfPCell cell = new PdfPCell(new Phrase(s.getName_course()));
                table.addCell(cell);
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

    private Font getBoldFont() {
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        font.setColor(BaseColor.BLACK);
        return font;
    }
}
